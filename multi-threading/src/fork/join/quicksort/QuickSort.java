package fork.join.quicksort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Collectors;

public class QuickSort extends RecursiveTask<int[]>{

	
	private static final long serialVersionUID = 1L;
	private int[] arr;
	public QuickSort(int[] unsorted) {
		arr=unsorted;
	}
	
	private int[] merge(int[] left,int [] right,int pivot) {
		int leftIndex=0,rightIndex=0,index=0;
		int[] cumulative=new int[left.length+right.length+1];
		
		while(leftIndex<left.length) {
			cumulative[index]=left[leftIndex];
			leftIndex++;
			index++;
		}
		
		cumulative[index]=pivot;
		index++;
		
		while(rightIndex<right.length) {
			cumulative[index]=right[rightIndex];
			rightIndex++;
			index++;
		}
		
		return cumulative;
	}
	
	public int[] partition(int[] arr) {
		if(arr.length<=1) {
			return arr;
		}
		else {
			int mid=arr.length/2;
			int pivot=arr[mid];
			Collection<List<Integer>> partitionedCollection =Arrays.stream(arr)
			.boxed()
			.collect(Collectors.partitioningBy((Integer element)->{
				if(element>pivot)
					return true;
				else
					return false;
					
			})).values();
			List<List<Integer>> partitionedList = new ArrayList<>(partitionedCollection);
			List<Integer> lessThanPivotList=partitionedList.get(0);
			if(lessThanPivotList.contains(pivot)) {
				int indexOfPivot=lessThanPivotList.indexOf(pivot);
				lessThanPivotList.remove(indexOfPivot);
			}
			List<Integer> greaterThanPivotList=partitionedList.get(1);
			int[] lessThanPivotArr=lessThanPivotList.stream()
					.mapToInt(Integer::intValue)
					.toArray();
			int[] greaterThanPivotArr=greaterThanPivotList.stream()
					.mapToInt(Integer::intValue)
					.toArray();
			int[] sortedLeft=partition(lessThanPivotArr);
			int[] sortedRight=partition(greaterThanPivotArr);
			return merge(sortedLeft,sortedRight,pivot);

		}
			
	}

	@Override
	protected int[] compute() {
		if(arr.length<=1) {
			return arr;
		}
		else {
			int mid=arr.length/2;
			int pivot=arr[mid];
			Collection<List<Integer>> partitionedCollection =Arrays.stream(arr)
			.boxed()
			.collect(Collectors.partitioningBy((Integer element)->{
				if(element>pivot)
					return true;
				else
					return false;
					
			})).values();
			List<List<Integer>> partitionedList = new ArrayList<>(partitionedCollection);
			List<Integer> lessThanPivotList=partitionedList.get(0);
			if(lessThanPivotList.contains(pivot)) {
				int indexOfPivot=lessThanPivotList.indexOf(pivot);
				lessThanPivotList.remove(indexOfPivot);
			}
			List<Integer> greaterThanPivotList=partitionedList.get(1);
			int[] lessThanPivotArr=lessThanPivotList.stream()
					.mapToInt(Integer::intValue)
					.toArray();
			int[] greaterThanPivotArr=greaterThanPivotList.stream()
					.mapToInt(Integer::intValue)
					.toArray();
			QuickSort sortLessThanPivotArrTask=new QuickSort(lessThanPivotArr);
			sortLessThanPivotArrTask.fork();
			QuickSort sortGreaterThanPivotArrTask=new QuickSort(greaterThanPivotArr);
			sortGreaterThanPivotArrTask.fork();
			int[] sortedLeft=sortLessThanPivotArrTask.join();
			int[] sortedRight=sortGreaterThanPivotArrTask.join();
			return merge(sortedLeft,sortedRight,pivot);

		}
	}
	
	
}
