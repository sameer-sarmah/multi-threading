package reentrant_read_write_lock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ArrayWrapper<T> implements Iterable<T> {
	private List<T> list = new ArrayList<>();
	private ReadWriteLock rwLock = new ReentrantReadWriteLock();
	private Lock readLock = rwLock.readLock();
	private Lock writeLock = rwLock.writeLock();

	public void addAllItems(List<T> itemList)  {
		
		writeLock.lock();
		System.out.println("Acquired write lock in addAllItems by "+Thread.currentThread().getName());
		try {
			for(T item:itemList) {
				this.add(item);
			}
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} finally {
			writeLock.unlock();
			System.out.println("released write lock in addAllItems by "+Thread.currentThread().getName());
		}
	}
	
	public Map<Integer,T> getAllItems(List<Integer> indexList)  {
		Map<Integer,T> map=new HashMap<>();
		readLock.lock();
		System.out.println("Acquired read lock in getAllItems by "+Thread.currentThread().getName());
		try {
			for(int index:indexList) {
				map.put(index, this.get(index));
			}
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return map;
		} finally {
			readLock.unlock();
			System.out.println("released read lock in getAllItems by "+Thread.currentThread().getName());
		}
	}
	
	public void add(T item) {
		writeLock.lock();
		System.out.println("Acquired write lock in add,adding item "+item +" by "+Thread.currentThread().getName());
		try {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			this.list.add(item);
		} finally {
			writeLock.unlock();
			System.out.println("released write lock in add by "+Thread.currentThread().getName());
		}
	}

	public void delete(T item) {
		writeLock.lock();
		try {
			this.list.remove(item);
		} finally {
			writeLock.unlock();
		}
	}

	public T get(int index) {
		readLock.lock();
		System.out.println("Acquired read lock in get,reading index "+index +" by "+Thread.currentThread().getName());
		try {
			Thread.sleep(500);
			return this.list.get(index);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			readLock.unlock();
			System.out.println("released read lock in get by"+Thread.currentThread().getName());
		}
		return null;
	}

	@Override
	public Iterator<T> iterator() {
		readLock.lock();
		try {
			return new CustomIterator(this.list.iterator());
		} finally {
			readLock.unlock();
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
