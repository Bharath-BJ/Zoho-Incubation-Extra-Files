package SecondRound;

import java.util.ArrayList;

public class FormatStringArrayToMaxWidth {

	public static void main(String[] args) {
//		String[] words= {"This","is","an","example","of","text","justification"};
		String[] words= {"What","must","be","acknowledgement","shall","be"};
//		String[] words= {"Science","is","what","we","understand","well","enough","to","explain",
//				       "to","a","Computer","Art","is","everything","else","we","do"};
		int maxWidth=20;
		int n=words.length;
		int i=0,j=0;
		ArrayList<String> output= new ArrayList<>();
		while(i<n)
		{
			int noOfSpaces=0;
			int wordlen=0;
			int wordSpaceLen=0;
			while(i<n)
			{
				if(wordSpaceLen+words[i].length()>maxWidth) 
				{
					noOfSpaces=maxWidth-wordlen;
					break;
				}
				wordlen+=words[i].length();
				wordSpaceLen+=words[i].length()+1;
				
//				System.out.println("Length "+wordlen);
//				System.out.println("Templen "+wordSpaceLen);
				i++;
			}
//			System.out.println("No of spaces "+noOfSpaces);
			String temp="";
//			System.out.println("j value & i value "+j +" "+i);
			while(j<i)
			{
				if(j==n-1)
				{
					temp+=words[j]+spaces(noOfSpaces); 
//					System.out.println("Last word");
					j++;
					break;
				}
				if(j==i-1) {
					temp+=words[j]; 
					j++;
					break;
				}
				temp+=words[j]+spaces(noOfSpaces/2);		
//				System.out.println(temp);
				j++;
			}
			output.add(temp);
//			System.out.println("**********Up for next cycle*********");
		}
		System.out.println(output);
	}
	private static int noOfSpaces(String[] words,int maxWidth,int i)
	{
		int n=words.length;
		int noOfSpaces=0;
		int wordlen=0;
		int wordSpaceLen=0;
		while(i<n)
		{
			if(wordSpaceLen+words[i].length()>maxWidth) 
			{
				noOfSpaces=maxWidth-wordlen;
				break;
			}
			wordlen+=words[i].length();
			wordSpaceLen+=words[i].length()+1;
			
//			System.out.println("Length "+wordlen);
//			System.out.println("Templen "+wordSpaceLen);
			i++;
		}
	return noOfSpaces;
	}
	private static String spaces(int n)
	{
		if(n==0)
			return " ";
		String temp="";
		for(int i=0;i<n;i++)
		{
			temp+=" ";
		}
	return temp;
	}
	
}
