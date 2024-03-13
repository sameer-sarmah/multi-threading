package fork.join.mergesort;

import java.util.Arrays;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class MergeSort extends RecursiveTask<int[]>{

	
	private static final long serialVersionUID = 1L;
	private int[] arr;
	public MergeSort(int[] unsorted) {
		arr=unsorted;
	}
	
	private int[] merge(int[] left,int [] right) {
		int leftIndex=0,rightIndex=0,index=0;
		int[] cumulative=new int[left.length+right.length];
		
		while(leftIndex<left.length && rightIndex<right.length) {
			if(left[leftIndex]<right[rightIndex]) {
				cumulative[index]=left[leftIndex];
				leftIndex++;
				index++;
			}
			else if(left[leftIndex]>right[rightIndex]) {
				cumulative[index]=right[rightIndex];
				rightIndex++;
				index++;
			}
			else {
				cumulative[index]=left[leftIndex];
				leftIndex++;
				index++;
				cumulative[index]=right[rightIndex];
				rightIndex++;
				index++;
			}
		}
	    	
		while(leftIndex<left.length) {
			cumulative[index]=left[leftIndex];
			leftIndex++;
			index++;
		}
		
		while(rightIndex<right.length) {
			cumulative[index]=right[rightIndex];
			rightIndex++;
			index++;
		}
		
		return cumulative;
	}
	
	public int[] partition(int[] arr) {
		if(arr.length==1) {
			return arr;
		}
		else {
			int mid=arr.length/2;
			int[] leftArr=Arrays.copyOfRange(arr,0,mid);
			int[] rightArr=Arrays.copyOfRange(arr,mid,arr.length);
			int[] sortedLeft=partition(leftArr); 
			int[] sortedRight=partition(rightArr); 
			int[] cumulative=merge(sortedLeft, sortedRight);
			return cumulative;
		}
			
	}

	@Override
	protected int[] compute() {
		if(arr.length==1) {
			return arr;
		}
		else {
			int mid=arr.length/2;
			int[] leftArr=Arrays.copyOfRange(arr,0,mid);
			int[] rightArr=Arrays.copyOfRange(arr,mid,arr.length);
			 ForkJoinTask<int []> leftArrSortingTask=new MergeSort(leftArr);
			 ForkJoinTask<int []> rightArrSortingTask=new MergeSort(rightArr);
			 
			leftArrSortingTask.fork();
			rightArrSortingTask.fork();
			int[] sortedLeft=leftArrSortingTask.join();
			int[] sortedRight=rightArrSortingTask.join();
			int[] cumulative=merge(sortedLeft, sortedRight);
			return cumulative;
		}
	}
	
	
}
