package SecondRound;

import java.util.Arrays;

public class DupInArray {

	public static void main(String[] args) {
		int[] arr= {1,3,4,1,2,2,3,1};
		int j=0,n=arr.length;
		for(int i=0;i<n-1;i++)
		{
			if(arr[i]!=arr[i+1])
				arr[j++]=arr[i];
		}
		arr[j++]=arr[n-1];
		for(;j<n;j++)
			arr[j]=0;
		System.out.println(Arrays.toString(arr));
	}

}
