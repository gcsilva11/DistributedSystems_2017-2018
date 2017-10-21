package RMIPackage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

public class Election implements Serializable{

	private static final long serialVersionUID = 1L;
	private String title;
	private String description;
	private Calendar startDate;
	private Calendar endDate;
	private int type;
	private ArrayList <Department> viableDeps = null;
	private ArrayList <candidateList> candidates;
	private boolean closed;

	public Election(String title,String description,Calendar startDate,Calendar endDate,int type,ArrayList <candidateList> candidates){
		this.title=title;
		this.description =description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.type = type;
		this.closed = false;
		this.candidates = candidates;
	}
	
	public String getTitle(){
		return this.title;
	}
	
	public void addDep(Department dep){
		this.viableDeps.add(dep);
	}
	
	public boolean getClosed(){
		return this.closed;
	}
	
	public String getDescription(){
		return this.description;
	}
	
	public Calendar getStartDate(){
		return this.startDate;
	}
	
	public Calendar getEndDate(){
		return this.endDate;
	}
	
	public int type(){
		return this.type;
	}
	
	public ArrayList<candidateList> getCandidates(){
		return this.candidates;
	}
	
	public void closeElection(){
		this.closed = true;
	}
	
	public void setTitle(String s){
		this.title = s;
	}
	
	public void setDescription(String s){
		this.description = s;
	}

	public void setStartDate(Calendar cal){
		this.startDate = cal;
	}

	public void setEndDate(Calendar cal){
		this.endDate = cal;
	}

	public String getInfo() {
		return "Title: "+this.title+"\nDescription: "+this.description+"\nType: "+this.type+"\nClosed: "+this.closed;
	}
}
