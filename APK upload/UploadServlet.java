import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import javax.servlet.annotation.*;

@WebServlet(
	name = "UploadServlet",
	urlPatterns = {"/UploadServlet"}
)

@MultipartConfig

public class UploadServlet extends HttpServlet
{

	public void doPost(HttpServletRequest req, HttpServletResponse res)
	{
		try
		{
			//Get the file that was sent by the client
		   Part part  = req.getPart("apk");
			
		   String header = part.getHeader("Content-disposition");
			
			//Get the file name
			
			String filename = header.split(";")[2];
			
			String file = filename.split("=")[1];
			String temp = file.substring(1,file.length()-1);
			
			//Save the file into the harddisk on the server
			String path = "D:\\TEMP\\";
			
			//Write or save the file into the specified path
			part.write(path + temp);
			
			PrintWriter pw = res.getWriter();
			res.setContentType("text/html");
			
			pw.println("<!DOCTYPE html><html><head><title>RESPONSE</title></head>");
			pw.println("<body><h2>Saved file:" + temp + "</h2></body></html>");
			pw.close();
		
		}
		catch(IOException ex)
		{
		
		}
		catch(ServletException ex)
		{
		
		}

	}
}
