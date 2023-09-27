package ThirdRound;

import java.util.*;
import java.util.regex.*;
import java.sql.*;
public class BankingServices 
{
	static Scanner s = new Scanner(System.in);
	static Connection con;
	static Statement st;
	static CallableStatement cst;
	static ResultSet rs;
	// EXTERNAL SERVICES
	public static void openBank() throws Exception
	{
		Class.forName("com.mysql.cj.jdbc.Driver");
		con=DriverManager.getConnection("jdbc:mysql://localhost/bankingapp","root","Bharath07*");
		String query="{CALL InitialDB()}";
		cst=con.prepareCall(query);
		cst.execute(query);
		con.close();
		cst.close();
	}
	public static void closeBank() throws Exception
	{
		Class.forName("com.mysql.cj.jdbc.Driver");
		con=DriverManager.getConnection("jdbc:mysql://localhost/bankingapp","root","Bharath07*");
		String query="DROP TABLE BANKDB";
		st=con.createStatement();
		st.execute(query);
		query="DROP TABLE ADMIN";
		st.execute(query);
		query="	DROP TABLE TRANSACTION";
		st.execute(query);
		query="    DROP TABLE PASSWORDHISTORY";
		st.execute(query);
		con.close();
		st.close();
	}
	public void signIn(String userType) throws Exception
	{
		
		System.out.println("Enter your ID");
		int id=s.nextInt();
		s.nextLine();
		System.out.println("Enter your password");
		String password=s.nextLine();
		password=passwordEncruption(password);
//		System.out.println(password);
//		System.out.println("Authentication "+authenticate(id,password,userType));
//		System.out.println("Usertype "+userType.equals("admin"));
		if(authenticate(id,password,userType) && userType.equals("customer"))
		{
			customerServices(id);
		}
		else if(authenticate(id,password,userType) && userType.equals("admin") )
		{
			adminServices(id);
		}
		else
		{
			System.out.println("Invalid ID or password");
			signIn(userType);
		}
			
	}

	// OVERALL INTERNAL SERVICES
	// Connection functions
	private void connect() throws Exception
	{
		Class.forName("com.mysql.cj.jdbc.Driver");
		con=DriverManager.getConnection("jdbc:mysql://localhost/bankingapp","root","Bharath07*");
		st=con.createStatement();
	}
	private void closeConnection()throws Exception
	{
		if(!(con==null))
			con.close();
		if(!(st==null))
			st.close();
		if(!(cst==null))
			cst.close();
	}
	// Admin Services
	private void createAccount() throws Exception
		{
			connect();
			s.nextLine();
			System.out.println("Enter the customer name");
			String name=s.nextLine();
			System.out.println("Enter a password");
			System.out.println("Note: Password must have 2 upper case characters,2 lower case characters and 2 numeric characters");
			String password = s.nextLine();
			while(!validatePassword(password))
			{
				System.out.println("Entered password is not of invalid format, set another password");
				password=s.nextLine();
			}
			System.out.println("Re-enter the password to confirm");
			String passwordSame = s.nextLine();
			confirmPassword(password,passwordSame);
			password=passwordEncruption(password);
			int id=getId()+11;
			int accountNo=getAccountNo()+11011;
			Account acc = new Account(name,password,id,accountNo);	
			String query = "INSERT INTO BANKDB VALUES ("+ acc.getCusId()+","+acc.cus.accountNo+",'"+acc.cus.name+"',"+acc.getBalance()+",'"+acc.cus.getPassword()+"')";
			st.execute(query);
			query="INSERT INTO PASSWORDHISTORY (ID,CURRPASSWORD) VALUES("+id+",'"+password+"')";
			st.execute(query);
			query="INSERT INTO TRANSACTION VALUES("+id+",1,NOW(),'OPENING',10000,10000)";
			st.execute(query);
			System.out.println("Your Account No is "+accountNo);
			System.out.println("Your ID is "+id);
			closeConnection();
		}
	private int getId() throws Exception
		{
			String query="SELECT * FROM BANKDB ORDER BY ID DESC LIMIT 1";
			rs=st.executeQuery(query);
			int id;
			if(rs.next())
				id=rs.getInt(1);
			else
				id=0;
		return id;
		}
	private int getAccountNo() throws Exception
		{
			String query="SELECT * FROM BANKDB ORDER BY ID DESC LIMIT 1";
			rs=st.executeQuery(query);
			int accountNo;
			if(rs.next())
				accountNo=rs.getInt(2);
			else
				accountNo=0;
		return accountNo;
		}
	private void topNCustomers() throws Exception
		{
			connect();
			System.out.println("Enter the no of customers to display");
			int n=s.nextInt();
			String query="SELECT ID,ACCOUNTNO,NAME,BALANCE FROM BANKDB ORDER BY BALANCE DESC LIMIT "+n;
			rs=st.executeQuery(query);
			System.out.println("------------------------------------------------");
			System.out.println("ID  ACCOUNT NO   NAME \t  BALANCE");
			System.out.println("------------------------------------------------");
			while(rs.next())
			{
				System.out.println(rs.getInt(1)+"\t"+rs.getInt(2)+"\t"+rs.getString(3)+"\t\t"+rs.getDouble(4));
				System.out.println("------------------------------------------------");
			}
			closeConnection();
		}
	private void viewCustomers() throws Exception
	{
		connect();
		String query="SELECT ID,ACCOUNTNO,NAME,BALANCE FROM BANKDB";
		rs=st.executeQuery(query);
		System.out.println("------------------------------------------------");
		System.out.println("ID   ACCOUNTNO\t NAME\t BALANCE");
		System.out.println("------------------------------------------------");
		while(rs.next())
		{
			System.out.println(rs.getInt(1)+"\t"+rs.getInt(2)+"\t"+rs.getString(3)+"\t"+rs.getDouble(4));
			System.out.println("------------------------------------------------");
		}
		closeConnection();
	}
	// Helper Services
	private boolean validatePassword(String pass) {

		String pattern1="[a-z]";
		String pattern2="[A-Z]";
		String pattern3="[0-9]";
		String pattern4="\u0020";
		int total=0;
		for(int i=1;i<=4;i++) {
			String pattern = null;
			switch(i) {
			case 1:
				pattern=pattern1;
				break;
			case 2:
				pattern=pattern2;
				break;
			case 3:
				pattern=pattern3;
				break;
			case 4:
				pattern=pattern4;
				break;
			}
			Pattern p =Pattern.compile(pattern);
			Matcher m=p.matcher(pass);
			int count=0;
			while(m.find()) {
				if(m.group().equals(" ")) {
					return false;
				}
				count+=1;
				if(count==2) {
					total+=count;
					break;
				}	
			}
		}
		if(total==6) {
			return true;
			}
		else {
			return false;
		}
	}
	private boolean passwordFormat(String password)
	{
//		System.out.println("Enter a password");
//		String password=s.nextLine();
		String pattern1="[a-z] {2,}";
		String pattern2="[A-Z] {2,}";
		String pattern3="[0-9] {2,}";
		String pattern4="\u0020";
		int total=0;
//		boolean check=true;
		for(int i=1;i<=4;i++) 
		{
			String pattern = null;
			switch(i) {
			case 1:{pattern=pattern1; break;}
			case 2:{pattern=pattern2; break;}
			case 3:{pattern=pattern3; break;}
			case 4:{pattern=pattern3; break;}
			}
			Pattern p =Pattern.compile(pattern);
			Matcher m=p.matcher(password);
			int count=0;
			while(m.find()) {
				if(m.group()==" ")
					return false;
				count+=1;
				if(count==2) {
					total+=count;
					System.out.println("t "+total+" c"+count);
					break;
				}
			}
		}if(total==6) {return true;}
//			boolean patternChecker= m.find();
//			System.out.println("pattern "+ i +" "+patternChecker);
//			if(!(check && patternChecker))
//				check=false;
//		}
//      If the control of reached here then password is not ok 
//		System.out.println("Invalid password format");
		return false;
	}
	private void confirmPassword(String password,String passwordSame)
	{
		if(password.equals(passwordSame))
			return;
		else
		{
			System.out.println("You have entered the incorrect password,enter the same password to re-confirm");
			passwordSame = s.nextLine();
			confirmPassword(password,passwordSame); 
		}
	}
	private String passwordEncruption(String password)
	{
		String encrupted = "";
		for(int i=0;i<password.length();i++)
		{
			encrupted+=(char)(password.charAt(i)+1);
		}
	return encrupted;
	}
	private boolean authenticate(int id,String password,String userType) throws Exception
	{
		connect();
		if(userType.equals("customer"))
		{
			String query="SELECT * FROM BANKDB WHERE ID="+id;
			rs=st.executeQuery(query);
			if(rs.next() && password.equals(rs.getString(5)))
			{
				return true;
			}
		}
		else if(userType.equals("admin"))
		{
			String query="SELECT * FROM ADMIN WHERE ID="+id;
			rs=st.executeQuery(query);
			if(rs.next() && password.equals(rs.getString(3)))
			{
				return true;
			}
		}
		closeConnection();
	return false;
	}
	private void customerServices(int id) throws Exception
	{
		connect();
		int checker;
		do 
		{
			System.out.println("---------------------------------------------------");
			System.out.println("Enter one of the following key for services");
			System.out.println("1 - To Withdraw amount");
			System.out.println("2 - To Deposit Cash");
			System.out.println("3 - To Transfer amount to another account");
			System.out.println("4 - To print passbook");
			System.out.println("5 - To Check Balance");
			System.out.println("6 - To set new password");
			System.out.println("7 - To display password history");
			System.out.println("0 - Back to SignIn");
			System.out.println("---------------------------------------------------");
			checker = s.nextInt();
			switch(checker)
			{
				case 1:
				{
					withdraw(id);
					break;
				}
				case 2:
				{
					deposit(id);
					break;
				}
				case 3:
				{
					transfer(id);
					break;
				}
				case 4:
				{
					printPassbook(id);
					break;
				}
				case 5:
				{
					checkBalance(id);
					break;
				}
				case 6:
				{
					s.nextLine(); // This is to avoid taking enter as input for new password in changepassword()
					changePassword(id);
					break;
				}
				case 7:
				{
					passwordHistory(id);
					break;
				}
				case 0:
				{
					System.out.println("Redirecting to the SignIn");
					break;
				}
				default:
				{
					System.out.println("You have entered a invalid key");
					customerServices(id);
				}
			}
				
		}while(checker!=0);
	closeConnection();
	}
	private void adminServices(int id) throws Exception
	{
		connect();
		int checker;
		do 
		{
			System.out.println("---------------------------------------------------");
			System.out.println("Enter one of the following key for services");
			System.out.println("1 - To Create Account");
			System.out.println("2 - To Display N Top Customer");
			System.out.println("3 - To display the all Customers");
			System.out.println("0 - Back to SignIn");
			System.out.println("---------------------------------------------------");
			checker = s.nextInt();
			switch(checker)
			{
				case 1:
				{
					createAccount();
					break;
				}
				case 2:
				{
					topNCustomers();
					break;
				}
				case 3:
				{
					viewCustomers();
				}
				
				case 0:
				{
					System.out.println("Redirecting to the SignIn");
					break;
				}
				default:
				{
					System.out.println("You have entered a invalid key");
					adminServices(id);
				}
			}
				
		}while(checker!=0);
		closeConnection();
	}
	// Customer Services
	private int getLastTransID(int id) throws Exception
	{
		String query="SELECT * FROM TRANSACTION WHERE ID="+id+" ORDER BY TRANS_TIME DESC";
		rs=st.executeQuery(query);
		rs.next();
		int lastTransId=rs.getInt(2);
	return lastTransId;
	}
	private void withdraw(int id) throws Exception
	{
		connect();
		System.out.println("Enter the amount to withdraw");
		double amount=s.nextDouble();		
		String query="SELECT * FROM BANKDB WHERE ID="+id;
		rs=st.executeQuery(query);
		rs.next();
		double balance=rs.getDouble(4);
		double maintanenceFee=100;
		double updateBalance=balance;
		while(updateBalance<0)
		{
			System.out.println("Insufficient balance, So reduce the withdrawal amount");
			amount=s.nextDouble();
			updateBalance=balance-amount;
		}
		while(updateBalance<1000)
		{
			System.out.println("You cannot maintain minimum balance Rs.1000 if you withdraw Rs."+amount+" So reduce the withdrawal amount");
			amount=s.nextDouble();
			updateBalance=balance-amount;
		}
		// Have to find No of Transactions first,to check whether maintenance fee applicable or not
		query="SELECT COUNT(*) FROM TRANSACTION WHERE ID="+id+";";
		rs=st.executeQuery(query);
		rs.next();
		int noOfTransactions=rs.getInt(1);
		int currTransId=getLastTransID(id)+1;
//		System.out.println(noOfTransactions);
		if(noOfTransactions%10==0)
		{
			updateBalance=updateBalance-maintanenceFee;
			query="INSERT INTO TRANSACTION VALUES("+id+","+currTransId+",NOW(),'MAINTANINENCE FEE',"+maintanenceFee+","+updateBalance+");";
			st.execute(query);
		}
		updateBalance=updateBalance-amount;
		String updatequery="UPDATE BANKDB SET BALANCE="+updateBalance+" WHERE ID ="+id+";";
		st.executeUpdate(updatequery);
		System.out.println("Collect before it returns");
		query="INSERT INTO TRANSACTION VALUES("+id+","+currTransId+",NOW(),'WITHDRAW',"+amount+","+updateBalance+");";
		st.execute(query);
		if(noOfTransactions%5==0)
		{
			System.out.println("Your password has expired, set new password");
			s.nextLine();
			changePassword(id);
		}
		
		closeConnection();
	}
	private void deposit(int id) throws Exception
	{
		connect();
		System.out.println("Enter the amount to deposit");
		double amount=s.nextDouble();		
		String query="SELECT * FROM BANKDB WHERE ID="+id+";";
		rs=st.executeQuery(query);
		rs.next();
		double balance=rs.getDouble(4);
		double maintanenceFee=100;
		double updateBalance=balance;
//		while(updateBalance<0)
//		{
//			System.out.println("Insufficient balance, So reduce the withdrawal amount");
//			amount=s.nextDouble();
//			updateBalance=balance+amount;
//		}
//		while(updateBalance<1000)
//		{
//			System.out.println("You cannot maintain minimum balance Rs.1000 if you withdraw Rs."+amount+" So reduce the withdrawal amount");
//			amount=s.nextDouble();
//			updateBalance=balance+amount;
//		}
		query="SELECT COUNT(*) FROM TRANSACTION WHERE ID="+id+";";
		rs=st.executeQuery(query);
		rs.next();
		int noOfTransactions=rs.getInt(1);
		int currTransId=getLastTransID(id)+1;
//		System.out.println(noOfTransactions);
		if(noOfTransactions%10==0)
		{
			updateBalance=updateBalance-maintanenceFee;
			query="INSERT INTO TRANSACTION VALUES("+id+","+currTransId+",NOW(),'MAINTANINENCE FEE',"+maintanenceFee+","+updateBalance+");";
			st.execute(query);
		}
		updateBalance=updateBalance+amount;
		String updatequery="UPDATE BANKDB SET BALANCE="+updateBalance+" WHERE ID ="+id+";";
		st.executeUpdate(updatequery);
		System.out.println("Your cash has been successfully deposited");
		query="INSERT INTO TRANSACTION VALUES("+id+","+currTransId+",NOW(),'DEPOSIT',"+amount+","+updateBalance+");";
		st.execute(query);
		if(noOfTransactions%5==0)
		{
			System.out.println("Your password has expired, set new password");
			changePassword(id);
		}
		closeConnection();
	}
	private void transfer(int id) throws Exception
	{
		connect();
		System.out.println("Enter the amount to transfer");
		double amount=s.nextDouble();		
		String query="SELECT * FROM BANKDB WHERE ID="+id;
		rs=st.executeQuery(query);
		rs.next();
		int senderAccountNo=rs.getInt(2);
		double balance1=rs.getDouble(4);
		double maintanenceFee=100;
		double updateBalance1=balance1;
		int senderTransId=getLastTransID(id)+1;
		while(updateBalance1<0)
		{
			System.out.println("Insufficient balance, So reduce the withdrawal amount");
			amount=s.nextDouble();
			updateBalance1=balance1-amount;
		}
		while(updateBalance1<1000)
		{
			System.out.println("You cannot maintain minimum balance Rs.1000 if you withdraw Rs."+amount+" So reduce the withdrawal amount");
			amount=s.nextDouble();
			updateBalance1=balance1-amount;
		}
		query="SELECT COUNT(*) FROM TRANSACTION WHERE ID="+id+";";
		rs=st.executeQuery(query);
		rs.next();
		int noOfTransactions=rs.getInt(1);
//		System.out.println(noOfTransactions);
		if(noOfTransactions%10==0)
		{
			updateBalance1=updateBalance1-maintanenceFee;
			query="INSERT INTO TRANSACTION VALUES("+id+","+senderTransId+",NOW(),'MAINTANINENCE FEE',"+maintanenceFee+","+updateBalance1+");";
			st.execute(query);
		}
		System.out.println("Enter the transfer Account Number");
		int recipientAccountNo=s.nextInt();
		query="SELECT * FROM BANKDB WHERE ACCOUNTNO="+recipientAccountNo+";";
		rs=st.executeQuery(query);
		rs.next();
//		System.out.println(rs.getInt(1)+" "+rs.getInt(2)+" "+rs.getString(3)+" "+rs.getDouble(4)+" "+rs.getString(5));
		int recipientId=rs.getInt(1);
		double balance2=rs.getDouble(4);
		int recipientTransId=getLastTransID(recipientId)+1;
		double updateBalance2=balance2+amount;
		int operationalFee=10;
		if(amount>5000)// Checking for operational fee
		{
			updateBalance1=updateBalance1-operationalFee;
		}
		updateBalance1=updateBalance1-amount;
		String updatequery1="UPDATE BANKDB SET BALANCE="+updateBalance1+" WHERE ID ="+id+";";
		st.executeUpdate(updatequery1);
		updateBalance2=updateBalance2+amount;
		String updatequery2="UPDATE BANKDB SET BALANCE="+updateBalance2+" WHERE ID ="+recipientId+";";
		st.executeUpdate(updatequery2);
		System.out.println("Transaction Successfully completed");
		query="INSERT INTO TRANSACTION VALUES("+id+","+senderTransId+",NOW(),'TRANSFER TO "+recipientAccountNo+"',"+amount+","+updateBalance1+");";
		st.executeUpdate(query);
		query="INSERT INTO TRANSACTION VALUES("+recipientId+","+recipientTransId+",NOW(),'TRANFER FROM "+senderAccountNo+"',"+amount+","+updateBalance2+");";
		st.executeUpdate(query);
		senderTransId+=1;
		if(amount>5000)
		{
			query="INSERT INTO TRANSACTION VALUES ("+id+","+senderTransId+",NOW(),'OPERATION FEE',"+operationalFee+","+updateBalance1+");";
			st.executeUpdate(query);
		}
		
		if(noOfTransactions%5==0)
		{
			System.out.println("Your password has expired, set new password");
			changePassword(id);
		}
		closeConnection();
	}
	private void printPassbook(int id) throws Exception
	{
		connect();
		String query="SELECT * FROM TRANSACTION WHERE ID="+id;
		rs=st.executeQuery(query);
		System.out.println("-----------------------------------------------------------------------------------------------");
		System.out.println("ID  TRANS_ID\t TRANS_TIME\t\t TRANS_TYPE\t AMOUNT\t BALANCE");
		System.out.println("-----------------------------------------------------------------------------------------------");
		while(rs.next())
		{
			// To convert sql timestamp to java timestamp
			java.sql.Timestamp timestampObj = rs.getTimestamp("TRANS_TIME");
//			System.out.print(timestampObj.toString() + "\t");
			System.out.println(rs.getInt(1)+"\t"+rs.getInt(2)+"\t"+timestampObj.toString()+"\t"+rs.getString(4)+"\t"+rs.getDouble(5)+"\t"+rs.getDouble(6));
			System.out.println("-----------------------------------------------------------------------------------------------");
		}
		closeConnection();
	}
	private void checkBalance(int id) throws Exception
	{
		connect();
		String query="SELECT BALANCE FROM BANKDB WHERE ID="+id;
		rs=st.executeQuery(query);
		rs.next();
		System.out.println("Account balance is "+rs.getInt(1));
		closeConnection();
	}
	private void changePassword(int id) throws Exception
	{
		connect();
		System.out.println("Enter the new password");
		String newPassword=s.nextLine();
		newPassword=passwordEncruption(newPassword);
		String query="SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME='PASSWORDHISTORY'";
		rs=st.executeQuery(query);
		rs.next();
		int noOfColumns=rs.getInt(1);
//		System.out.println(noOfColumns);
		query="SELECT * FROM PASSWORDHISTORY WHERE ID="+id;
		rs=st.executeQuery(query);
		rs.next();
		boolean passOk=true;
		// iterating through the columns of 1st row,we start from 3 to ignore the id and currpassword column
		for(int i=2;i<noOfColumns;i++)
		{
			String iterPassword=rs.getString(i);
			if(newPassword.equals(iterPassword) && passOk)// passOk will be true until it find 1st password match
				passOk=false;				
		}
//		System.out.println("passOK "+passOk);
//		System.out.println(rs.getString(1)+" "+rs.getString(2)+" "+rs.getString(3)+" "+rs.getString(4)+" "+rs.getString(5));
		if(passOk)
		{
			updatePasswordHistory(newPassword,id,noOfColumns);
			System.out.println("Your password has been changed Successfully");
		}
		else
		{
			System.out.println("You cannot set the current and previously used 3 passwords");
			System.out.println("Attempt different password");
			changePassword(id);
		}
		
		closeConnection();
	}
	private void updatePasswordHistory(String newPassword,int id,int noOfColumns) throws Exception
	{
		String query;
		int passwordColumn=noOfColumns-2;
		for(int i=noOfColumns-1;i>=2;i--)// To update the password except the currpassword
		{
			query="SELECT * FROM PASSWORDHISTORY WHERE ID="+id;
			rs=st.executeQuery(query);
			rs.next();
			String shiftPassword=rs.getString(i);
			query="UPDATE PASSWORDHISTORY SET PASSWORD"+passwordColumn+"='"+shiftPassword+"' WHERE ID="+id+";";
//			System.out.println(query);
			passwordColumn--;
			st.execute(query);
		}
		query="UPDATE PASSWORDHISTORY SET CURRPASSWORD='"+newPassword+"' WHERE ID="+id+";";
		st.executeUpdate(query);
		query="UPDATE BANKDB SET PASSWORD='"+newPassword+"' WHERE ID="+id+";";
		st.executeUpdate(query);
	}
	private void passwordHistory(int id) throws Exception
	{
		connect();
		String query="SELECT * FROM PASSWORDHISTORY WHERE ID="+id+";";
		rs=st.executeQuery(query);
		System.out.println("-----------------------------------------------------------------------------------");
		System.out.println("ID  CURRPASSWORD  PASSWORD1    PASSWORD2     PASSWORD3");
		System.out.println("-----------------------------------------------------------------------------------");
		while(rs.next())
		{
			System.out.println(rs.getInt(1)+"\t"+rs.getString(2)+"\t"+rs.getString(3)+"\t\t"+rs.getString(4)+"\t"+rs.getString(5));
		}
		closeConnection();
	}
}
