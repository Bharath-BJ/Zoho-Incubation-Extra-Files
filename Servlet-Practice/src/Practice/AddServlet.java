package Practice;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//@WebServlet("/add")
public class AddServlet extends HttpServlet 
{
	// Here I used get method only, every line code will work for post method as well
	// There are 2 ways to call servlet from servlet (i)RequestDispatcher (ii) sendRedirect
	// There are 3 ways to pass parameters to the redirected servlets, when calling servlet using sendRedirect
    // 1. Using URL rewriting
	// 2. Using session management 
	// 3. Using cookies 
	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		int num1= Integer.parseInt(req.getParameter("num1"));
		int num2= Integer.parseInt(req.getParameter("num2"));
		int sum=num1+num2;
		
		/* Calling another Servlet using sendRedirect (slash is not needed here)  */
		/* 3) Input parameters are sent using Cookies */
		Cookie cookie = new Cookie("sum",sum+"");
		res.addCookie(cookie);
		res.sendRedirect("CallServletUsingSR3");
		
		/* Calling another Servlet using sendRedirect (slash is not needed here)  */
		/* 2) Input parameters are sent using session */
//		HttpSession session = req.getSession();
//		session.setAttribute("sum", sum);	
//		res.sendRedirect("CallServletUsingSR2");
		
		/* Calling another Servlet using sendRedirect (slash is not needed here)  */
		/* 1) Input parameters are sent using URL Rewriting */
//		res.sendRedirect("callingSR?sum="+sum);
//		res.sendRedirect("CallServletUsingSR?sum="+sum);
		
	
		/* Calling another Servlet using RequestDispatcher (slash is mandatory here)  */
//		req.setAttribute("sum", sum);
//		RequestDispatcher rd = req.getRequestDispatcher("/CallServletUsingRD");
//		RequestDispatcher rd = req.getRequestDispatcher("/callingRD");
//		rd.forward(req, res);
		
		/* Without calling another Servlet   */
//		PrintWriter out=res.getWriter();
//		out.println(sum);
	}

	
//	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		// TODO Auto-generated method stub
//		doGet(request, response);
//	}

}
