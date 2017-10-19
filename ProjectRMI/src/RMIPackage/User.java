package RMIPackage;

import java.io.Serializable;
import java.util.Calendar;

public class User implements Serializable{
    

	private static final long serialVersionUID = 1L;
    private String name;
    private String idNumber;
    private Calendar idExpDate;
    private String phone;
    private int profession;
    private String department;
    private String password;
    private boolean hasVoted = false;

    public User(String name,  String idNumber, Calendar expDate,String phone, int profession, String department, String password){
        this.name = name;
        this.idNumber = idNumber;
        this.idExpDate = expDate;
        this.phone = phone;
        this.profession = profession;
        this.department = department;
        this.password = password;
        
    }
    
    public boolean getVote(){
    	return this.hasVoted;
    }
    
    public String getName(){
    	return this.name;
    }
    
    public Calendar getExpDate(){
    	return this.idExpDate;
    }
    
    public String getID(){
    	return this.idNumber;
    }
    public String getPhone(){
    	return this.phone;
    }
    
    public String getProfession(){
    	if(this.profession==1){
    		return "Student";
    	}
    	else if(this.profession==2){
    		return "Professor";
    	}
    	else{
    		return "Employee";
    	}
    }
    
    public String getDepartment(){
    	return this.department;
    }
    
    public String getPassword(){
    	return this.password;
    }
    
    public void Vote(){
    	this.hasVoted = true;
    }
    
    
}
