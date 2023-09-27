package SecondRound;

import java.util.Arrays;

public class MoveZerosToEnd {

	public static void main(String[] args) {
		int[] arr= {1, 9, 8, 4, 0, 0, 2, 7, 0, 6, 0};
		System.out.println(Arrays.toString(arr));
		Approach1(arr);
		Approach2(arr);
	}
	private static void Approach1(int[] arr) // O(n2)
	{
		int n=arr.length;
		int temp;
		for(int i=0;i<n;i++)
		{
			for(int j=i;j<n-1;j++)
			{
				if(arr[j]==0)
				{
					temp=arr[j];
					arr[j]=arr[j+1];
					arr[j+1]=temp;
				}	
			}
		}
		System.out.println(Arrays.toString(arr));
	}
	private static void Approach2(int[] arr) // O(n)
	{
		int n=arr.length;
		int temp;
		int nonZeroIndex=0;
		for(int i=0;i<n;i++)
		{
			if(arr[i]!=0)
			{
				temp=arr[i];
				arr[i]=arr[nonZeroIndex];
				arr[nonZeroIndex]=temp;
				nonZeroIndex++;
			}
		}
		System.out.println(Arrays.toString(arr));
	}
}
