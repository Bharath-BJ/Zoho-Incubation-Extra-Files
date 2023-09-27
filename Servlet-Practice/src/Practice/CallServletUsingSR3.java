package Practice;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.Cookie;

/**
 * Servlet implementation class CallServletUsingSR3
 */
@WebServlet("/CallServletUsingSR3")
public class CallServletUsingSR3 extends HttpServlet {
	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		Cookie[] cookies = req.getCookies();
		int sum=0;
		for(Cookie c:cookies)
		{
			if(c.getName().equals("sum"))
			{
				sum=Integer.parseInt(c.getValue());
			}
		}
		PrintWriter out = res.getWriter();
    	out.println(sum);
			
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
