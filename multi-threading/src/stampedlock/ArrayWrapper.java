package stampedlock;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.StampedLock;

import util.Util;

public class ArrayWrapper<T> implements Iterable<T> {
	private List<T> list = new ArrayList<>();
	/*
	 * StampedLock are more suited than only ReentrantLock for cases where read and
	 * like write(update,delete) are handled differently e.g example in this case
	 * get is a read operation where delete and add are write operations
	 *
	 * the writeLock,tryWriteLock methods will return a "long" value which has the
	 * mode and version data writeLock is like calling lock in a Lock object and it
	 * will wait till the lock is acquired tryWriteLock will return if the lock is
	 * not available immediately
	 * 
	 * readLock is acquire non exclusive lock,i.e multiple threads can share a read
	 * lock and it will wait (if any other thread has a write lock) till the lock is
	 * acquired
	 * 
	 * StampedLock is an upgrade of ReadWriteLock as it also provides facility of
	 * optimistic read via tryOptimisticRead() tryOptimisticRead returns 0 if any
	 * other thread has the write lock that means failed to acquire lock otherwise
	 * returns a stamp validate(long) returns true if the no write lock is acquired
	 * by any other threads since obtaining above stamp
	 * 
	 * optimistic read is preferable for read heavy use otherwise it would lower
	 * performance
	 * 
	 * StampedLock ARE NOT reentrant
	 */
	private StampedLock stampedLock = new StampedLock();


	public void add(T item) {
		long startTime = System.currentTimeMillis();
		long stamp = stampedLock.writeLock();
		long endTime = System.currentTimeMillis();
		System.out
				.println("Acquired write lock in add,adding item " + item + " by " + Thread.currentThread().getName() +" in "+(endTime - startTime)+ " ms");
		try {
			Util.busyFor(500, TimeUnit.MILLISECONDS);
			this.list.add(item);
		} finally {
			stampedLock.unlockWrite(stamp);
			System.out.println("released write lock in add by " + Thread.currentThread().getName());
		}
	}

	public void delete(T item) {
		long startTime = System.currentTimeMillis();
		long stamp = stampedLock.writeLock();
		long endTime = System.currentTimeMillis();
		System.out.println("Acquired write lock in delete " + Thread.currentThread().getName() +" in "+(endTime - startTime)+ " ms");
		try {
			this.list.remove(item);
		} finally {
			stampedLock.unlockWrite(stamp);
		}
	}

	public T get(int index) {
		long startTime = System.currentTimeMillis();
		long stamp = stampedLock.readLock();
		long endTime = System.currentTimeMillis();
		System.out.println(
				"Acquired read lock in get,reading index " + index + " by " + Thread.currentThread().getName()+" in "+(endTime - startTime)+ " ms");
		try {
			Thread.sleep(500);
			return this.list.get(index);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			stampedLock.unlockRead(stamp);
			System.out.println("released read lock in get by" + Thread.currentThread().getName());
		}
		return null;
	}

	public int size() {
		long stamp = stampedLock.tryOptimisticRead();// non zero if no other threads has write lock
		int size = this.list.size();
		if (!stampedLock.validate(stamp)) {
			System.out.println("optimistic locking failed");
			try {
				stamp = stampedLock.readLock();
				System.out.println("acquired read lock in size() by " + Thread.currentThread().getName());
				size = this.list.size();
			} finally {
				stampedLock.unlockRead(stamp);
				System.out.println("released read lock in size() by " + Thread.currentThread().getName());
			}
		}else {
			System.out.println("optimistic locking worked");
		}

		return size;
	}

	@Override
	public Iterator<T> iterator() {
		long stamp = stampedLock.readLock();
		try {
			return new CustomIterator(this.list.iterator());
		} finally {
			stampedLock.unlockRead(stamp);
		}
	}

	class CustomIterator implements Iterator<T> {

		Iterator<T> it;

		public CustomIterator(Iterator<T> it) {
			super();
			this.it = it;
		}

		@Override
		public boolean hasNext() {
			return it.hasNext();
		}

		@Override
		public T next() {
			return it.next();
		}

		@Override
		public void remove() throws NotSupportedOperation {
			// we wont let other remove items
		}

	}

}
