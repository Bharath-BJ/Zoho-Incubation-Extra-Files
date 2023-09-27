package ThirdRound;

public class Account 
{
	Customer cus;
	private int balance=10000;
	private int cusId;
	
	public Account(String name,String password,int cusId,int accountNo)
	{	
		this.cus = new Customer(name,password,accountNo);
		this.cusId = cusId;
	}
	public void setBalance(int balance)
	{
		this.balance=balance;
	}
	public int getBalance()
	{
		return balance;
	}
	public int getCusId()
	{
		return cusId;
	}
}
