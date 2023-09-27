package SecondRound;

import java.util.Arrays;

public class DuplicateInArray {

	public static void main(String[] args) {
		int[] arr= {4,1,3,1,8,7,6,5};
//		approach1(arr);
//		approach2(arr);
		approach3(arr);
	}
	private static void approach1(int[] arr)
	{
		int n=arr.length;
		for(int i=0;i<n;i++)
		{
			for(int j=i+1;j<n;j++)
			{
				if(arr[i]==arr[j])
					System.out.println(arr[i]);
			}
		}
	}
	private static void approach2(int[] arr)
	{
		int n=arr.length;
		int sum=0;
		int sumOfNRange=n*(n+1)/2;
		for(int i=0;i<n;i++)
		{
			sum+=arr[i];
		}
		System.out.println(n-(sumOfNRange-sum));
	}
	private static void approach3(int[] arr)
	{
		int n=arr.length;
		for(int i=0;i<n;i++)
		{
			if(arr[i]!=i+1)
			{
				int temp=arr[i];
				arr[i]=arr[i+1];
				arr[i+1]=temp;
			}
		}
		System.out.println(Arrays.toString(arr));
	}
}
