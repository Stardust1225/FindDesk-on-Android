
public class Person {
	String account,password,id,email=null,tele=null,name=null;
	
	public Person(String acc,String pass,String id){
		account=acc;
		password=pass;
		this.id=id;
	}
	
	public Person(String acc,String pass,String id,String email,String tele,String name){
		account=acc;
		password=pass;
		this.id=id;
		this.email=email;
		this.tele=tele;
		this.name=name;
	}
	
	public String getAccount(){
		return account;
	}
	
	public String getPassword(){
		return password;
	}
	
	public String getId(){
		return id;
	}
	
	public String getEmail(){
		return email;
	}
	
	public String getTele(){
		return tele;
	}
	
	public String getName(){
		return name;
	}
	
}
