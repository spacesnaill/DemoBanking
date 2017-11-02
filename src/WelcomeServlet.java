

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class WelcomeServlet
 */
public class WelcomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WelcomeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
    
    Connection con;
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		
try{
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1522:xe","SYSTEM","Frostburg");
			System.out.println("Database connection established successfully");
			
		}
		
		catch(Exception e){
			System.err.println(e);
			
		}
	}

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
		//String s1=request.getParameter("uname");
		//String s2=request.getParameter("pword");
		
		
		HttpSession hs=request.getSession();
		
		String un=(String)hs.getAttribute("uname");
		String pwd=(String)hs.getAttribute("pword");
		
		
		try{
		PreparedStatement pstmt=con.prepareStatement("select * from customer where userid=? and pword=?");
		
		if( ! un.equals(null) && ! pwd.equals(null))
		{
			pstmt.setString(1, un);
			pstmt.setString(2, pwd);
		}
		
		
		PrintWriter pw=response.getWriter();
		
		
		
		ResultSet rs=pstmt.executeQuery();
	
		rs.next();
		
		
		
		
		Cookie c1=new Cookie("lname",rs.getString(2));
		Cookie c2=new Cookie("AccountNoC",rs.getString(6));
		Cookie c3=new Cookie("bal",rs.getString(8));
		
		response.addCookie(c1);
		response.addCookie(c2);
		response.addCookie(c3);
		
	
		
		
		
		pw.println("<strong><a href=aboutus.html>About Us</a></strong> &nbsp;&nbsp;&nbsp;&nbsp;");
		pw.println("<strong><a href=contactus.html>Contact Us</a></strong> &nbsp;&nbsp;&nbsp;&nbsp;");
		pw.println("<strong><a href=careers.html>Careers</a></strong> &nbsp;&nbsp;&nbsp;&nbsp;");
		pw.println("<strong><a href=changecredentials.html >Change User Credentials</a></strong>&nbsp;&nbsp;&nbsp;&nbsp;");
		pw.println("<strong><a href=logout>Log out</a></strong> <br />");
		
		
		pw.println("<hr />");
		pw.println("<html> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<h4>Welcome <em>"+rs.getString(2)+"</em></h4>");
		pw.println("<h5>Account Number  : &nbsp;"+rs.getString(6));
		pw.println("<br />Your current Balance  : &nbsp;USD &nbsp;"+rs.getString(8)+"</h5>");
		pw.println("<h4>Menu</h4>");
		pw.println("<br /> <a href=FTWithin.html>Funds Transfer With in Bank</a>");
		pw.println("<br /> <a href=Ftother.html>Funds Transfer to Other Bank</a>");
		pw.println("<br /> <a href=complaint.html>Register a Complaint</a>");
		pw.println("<br /> <a href=viewcomplaint>View Complaints' status</a>");
		pw.println("<br /> <a href=viewtransactions>View Latest Transactions</a>");
		pw.println("<br /> <a href=changecredentials.html >Change User Credentials</a>");
		pw.println("<br /> <a href=logout>Log out</a>");
		
		
		}
		catch(Exception e){
			System.err.println(e);
		}
		
	}

}
