import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Register extends HttpServlet 
{

	 public void doGet(HttpServletRequest request,HttpServletResponse response)
     throws ServletException{
		 try{
			 String acc=request.getParameter("a");
			 String pass=request.getParameter("p");
			 String id=request.getParameter("i");
			 String email=request.getParameter("e");
			 String tele=request.getParameter("t");
			 String name=request.getParameter("n");
			 
			 Mysql db=new Mysql();
			 
			 String flag=db.found(acc);
			 if(flag.charAt(0)=='3'){
				 response.getWriter().print("1");
				 db.insert(new Person(acc,pass,id,email,tele,name));
			 }
				 	
			 else{
				 	response.getWriter().print("2");
				 	
			 }
		 
	 }catch(Exception e){}
	 }
	 
	 public void doPost(HttpServletRequest request,HttpServletResponse response){
		 try {
			doGet(request,response);
		} catch (ServletException e) {}
		 
	 }

}