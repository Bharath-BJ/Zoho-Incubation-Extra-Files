package ThirdRound;

import java.util.*;
public class Main {
	static {
		try {BankingServices.openBank();} catch (Exception e) {System.out.println("Tables are already created");}
	}
	public static void main(String[] args) throws Exception 
	{
		Scanner s = new Scanner(System.in);
		int checker;
		String userType="";
		do {
			System.out.println("---------------------------------------------------");
			System.out.println("Banking Application");
			System.out.println("You are in the MainMenu");
			System.out.println("Enter the type of user");
			System.out.println("1 - Admin Console");
			System.out.println("2 - Customer Profile");
			System.out.println("0 - Quit");
			System.out.println("---------------------------------------------------");
			checker=s.nextInt();
			switch(checker)
			{
				case 1:
				{
					userType="admin";
					adminConsole(userType);
					break;
				}
				case 2:
				{
					userType="customer";
					customerConsole(userType);
					break;
				}
				case 0:
				{
					BankingServices.closeBank();
					break;
				}
				default:
				{
					System.out.println("You have entered a invalid key");
					main(args);
				}
			}
			
		}while(checker!=0);
		
	}
	private static void customerConsole(String userType) throws Exception
	{
		Scanner s = new Scanner(System.in);
		int checker;
		BankingServices bs = new BankingServices();
		do 
		{
			System.out.println("---------------------------------------------------");
			System.out.println("Enter one of the following key for services");
			System.out.println("1 - To Sign in");
			System.out.println("0 - Back to mainmenu");
			System.out.println("---------------------------------------------------");
			checker = s.nextInt();
			switch(checker)
			{
				case 1:
				{
					bs.signIn(userType);
					break;
				}
				case 0:
				{
					System.out.println("Redirecting to the mainmenu.....");
					Thread.sleep(1000);
					break;
				}
				default:
				{
					System.out.println("You have entered a invalid key");
					customerConsole(userType);
				}
			}
		}while(checker!=0);
	}
	private static void adminConsole(String userType) throws Exception
	{
		Scanner s = new Scanner(System.in);
		int checker;
		BankingServices bs = new BankingServices();
		do 
		{
			System.out.println("---------------------------------------------------");
			System.out.println("Enter one of the following key for services");
			System.out.println("1 - To Sign in");
			System.out.println("0 - Back to mainmenu");
			System.out.println("---------------------------------------------------");
			checker = s.nextInt();
			switch(checker)
			{
				case 1:
				{
					bs.signIn(userType);
					break;
				}
				case 0:
				{
					System.out.println("Redirecting to the mainmenu....");
					Thread.sleep(1000);
					break;
				}
				default:
				{
					System.out.println("You have entered a invalid key");
					adminConsole(userType);
				}
			}
		}while(checker!=0);
	}
	
}
