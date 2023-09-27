package ThirdRound;

public class Customer 
{
	String name;
	int accountNo;
	private String password;
	
	
	public Customer(String name,String password,int accountNo)
	{
		this.name = name;
		this.accountNo=accountNo;
		this.password=password;
	}
	public void setPassword(String password)
	{
		this.password=password;
	}
	public String getPassword()
	{
		return password;
	}
	
}
