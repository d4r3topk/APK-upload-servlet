import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.*;

@WebServlet(
	name="LoginServlet",
	urlPatterns = {"/LoginServlet"}
)

public class LoginServlet extends HttpServlet
{
	HttpSession sess;
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException
	{
	   // Check if he has sent 
		//  if authentication fails, 
		// redirect to the login page. If login succeeds, then add two
		// attributes to the session object and keep them.
		PrintWriter pw = res.getWriter();
		
		String usr = req.getParameter("usr");
		String pwd = req.getParameter("pwd");
		
		if(usr == null || pwd == null || usr.equals("") || pwd.equals(""))
		{
			//Nothing was sent. We need to redirect him to the Main page
			pw.println("<h2>Sorry..Could not log you in. Will redirect to main login page.Please wait</h2>");
			res.setHeader("Refresh","5;http://localhost:8080/login.html");
		}
		
		else
		{
			//We got parameters. But we don't know if the user is valid
			if(usr.equals("user1") && pwd.equals("blah"))
			{
				sess = req.getSession(true); //If I repeat the login, then session will be old
				
				sess.setAttribute("login", "foobar");
				sess.setAttribute("pwd", "foo");
				
				pw.println("<h2>LOGIN SUCCESSFUL..WELCOME TO THE MAIN PAGE..</h2>");
				pw.println("<h2>Session details:</h2>");
				pw.println("<h3>Sessionid: " + sess.getId() + "</h3>");
				pw.println("<h3>Session inactive period: " + sess.getMaxInactiveInterval() + "</h3>");
				pw.println("<h3>Sessionid: " + sess.isNew() + "</h3>");
			}
			else // Login failed.
			{
				pw.println("<h2>Sorry..Could not log you in. Will redirect to main login page.Please wait</h2>");
				res.setHeader("Refresh","5;http://localhost:8080/login.html");
			}
		}
		pw.close();
	}
	
	//The method for GET.
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException
	{
			
		//Get the session parameters. If there are session attributes set
		// then user logged in. 
		
		sess = req.getSession(true);
		
		//Get the login and password attributes
		String username = (String)sess.getAttribute("login");
		String password = (String)sess.getAttribute("pwd");
		
		//Get the PrintWriter object to write to the client.
		PrintWriter pw = res.getWriter();

		if(username != null && password != null && username.equals("user1") && password.equals("blah"))
		{
			//Session already established. user must have logged in from some other
			// page. 
			
			pw.println("<!DOCTYPE html><html><head>");
			pw.println("<title>WELCOME..ALREADY LOGGED IN</title></head><body>");
			pw.println("<h2>YOU ARE ALREADY LOGGED IN.. WELCOME..</h2>");
			pw.println("</body></html>");
		}
		
		// Else, did not find the attributes.
	
		else // Login failed.
		{
			pw.println("<h2>Sorry..Not logged in. Will redirect to main login page.Please wait</h2>");
			res.setHeader("Refresh","5;http://localhost:8080/login.html");
		}
	}
	
	public void destroy()
	{
		if(sess != null)
		{
			sess.invalidate();
		}
	}
}
