import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SeatInformation extends HttpServlet {
	
		 public void doGet(HttpServletRequest request,HttpServletResponse response)
	     throws ServletException{
			 try{
			 
				 
			 String library=request.getParameter("l");
			 String part=request.getParameter("p");
			 String floor=request.getParameter("f");
			 String seat=request.getParameter("s");
			 String id=request.getParameter("i");
			 String column=request.getParameter("c");
			 
			 
			Booksql db=new Booksql();
			String flag=db.found(library);
			
			int i=flag.indexOf("&"),j=flag.indexOf("|"),k=flag.indexOf("/");

			if(flag.substring(0,i).equals(part)&&flag.substring(i+1,j).equals(floor)
						 	&&flag.substring(j+1,k).equals(seat)&&flag.substring(k+1,flag.length()).equals(column))
				response.getWriter().print("1");
			else
				response.getWriter().print("2");
			
			 }catch(Exception e){}
		 }
		 public void doPost(HttpServletRequest request,HttpServletResponse response){
			 try {
				doGet(request,response);
			} catch (ServletException e) {}
			 
		 }

}