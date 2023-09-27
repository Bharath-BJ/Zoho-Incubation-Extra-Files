package SecondRound;

public class StairCase {

	public static void main(String[] args) {
		int top=4;
		System.out.println(helper(top));

	}
	private static int helper(int n)
	{
		if(n==0)
			return 1;
		if(n<0)
			return 0;
		return helper(n-1)+helper(n-2);
	}
}
