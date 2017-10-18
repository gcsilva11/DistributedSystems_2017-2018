package projectRMI;

import java.io.Serializable;
import java.util.ArrayList;

public class candidateList implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String name;
	private int nVotes = 0;
	private ArrayList <User> candidates = new ArrayList <User>();
	private int listId = 0;
	private int type = 0;
	
	
	public candidateList(String name,int listID,int type){
		this.name = name;
		this.listId = listID;
		this.type = type;
		this.candidates = null;
	}
	
	public void setType(int type){
		this.type = type;
	}
	
	public int getType(){
		return type;
	}
	
	public int getVotes(){
		return this.nVotes;
	}
	
	public int getID(){
		return this.listId;
	}
	
	public String getName(){
		return this.name;
	}
	public void Vote(){
		this.nVotes++;
	}
	
	public void setID(int id){
		this.listId = id;
	}
	
	public void addCandidate(User user){
		this.candidates.add(user);
	}
}
