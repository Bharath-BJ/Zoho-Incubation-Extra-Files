package SecondRound;

import java.util.*;

public class SplitTheArray {
    public static void main(String[] args) {
        int[] arr= {1, 2, 5, 7, 2, 3, 5, 7, 8};
        int k=3;
        int splits=0;
        while(arr.length>0 && splits<k)
        {
            int n=arr.length;
            int n1=n/2;
            int[] arr1=new int[n1];
            int n2=(n%2==1)? n/2+1:n/2;
            int[] arr2=new int[n2];
            // 1st array
            for(int i=0;i<n/2;i++)
                arr1[i]=arr[i];
            // 2nd array
            for(int i=n/2;i<n;i++)
                arr2[i-n/2]=arr[i];
            // System.out.println(Arrays.toString(arr1));
            // System.out.println(Arrays.toString(arr2));
            int temp[]=new int[Math.max(n1,n2)];
            // Arrays addition
            int x=temp.length-1;
            int i,j;
            for(i=n1-1,j=n2-1;i>=0 && j>=0;i--,j--)
            {
                temp[x--]=arr1[i]+arr2[j];
            }
            // System.out.println(i+" "+j);
            if(j==0)
                temp[x]=arr2[j];
            arr=temp;
            splits++;
            System.out.println(splits);
            System.out.println(Arrays.toString(arr));
        }
    }
}
