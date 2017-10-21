package RMIPackage;

import java.io.Serializable;
import java.util.ArrayList;

public class candidateList implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String name;
	private int nVotes = 0;
	private ArrayList <User> candidates = new ArrayList <User>();
	private String listId;
	private int type = 0;
	
	
	public candidateList(String name,String listID,int type,ArrayList <User> candidates){
		this.name = name;
		this.listId = listID;
		this.type = type;
		this.candidates = candidates;
	}
	
	public void setType(int type){
		this.type = type;
	}
	
	public void setName(String title){
		this.name = title;
	}
	
	public int getType(){
		return type;
	}
	
	public int getVotes(){
		return this.nVotes;
	}
	
	public String getID(){
		return this.listId;
	}
	
	public String getName(){
		return this.name;
	}
	public void Vote(){
		this.nVotes++;
	}
	
	public void setID(String id){
		this.listId = id;
	}
	
	public void addCandidate(User user){
		this.candidates.add(user);
	}

	public String getInfo() { return "Name: "+this.name+"\nlistID: "+this.listId+"\nType: "+this.type;}
}
