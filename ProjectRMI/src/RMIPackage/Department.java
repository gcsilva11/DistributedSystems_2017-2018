package RMIPackage;

import java.io.Serializable;

public class Department implements Serializable{

	private static final long serialVersionUID = 1L;
	private String depName;
	private String facName;
	private String depID;
	
	public Department(String dep,String depID,String fac){
		this.depName = dep;
		this.facName = fac;
		this.depID = depID;
	}
	
	public Department(String depID){
		this.depName = null;
		this.facName = null;
		this.depID = depID;
	}
	
	public String getDep(){
		return this.depName;
	}
	
	public String getFac(){
		return this.facName;
	}
	
	public String getID(){
		return this.depID;
	}
	
	public void setDep(String dep){
		this.depName = dep;
	}
	
	public void setFac(String fac){
		this.facName = fac;
	}

	public String getInfo() { return "Dep. Name: "+this.depName+"\nFac. Name: "+this.facName+"\nDep ID: "+this.depID; }
}
