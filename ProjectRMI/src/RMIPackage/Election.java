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
	private ArrayList <Department> viableDeps = new ArrayList<Department>();
	private ArrayList <candidateList> candidates = new ArrayList<candidateList>();
	private boolean closed;
	public int nVotos;
	public candidateList winner;

	public Election(String title,String description,Calendar startDate,Calendar endDate,int type,ArrayList <candidateList> candidates){
		this.title=title;
		this.description =description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.type = type;
		this.closed = false;
		this.candidates = candidates;
	}

	public void incrementVotes(){
		this.nVotos++;
	}

	public String getTitle(){
		return this.title;
	}
	
	public void addDep(Department dep){
		if(this.viableDeps == null) {
			viableDeps = new ArrayList<Department>();
			this.viableDeps.add(dep);
		}
		else
			this.viableDeps.add(dep);
	}

	public ArrayList<Department> getViableDeps() { return this.viableDeps; }

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
	
	public void setClosed(){
		this.closed = true;
	}

	public String getInfo() {
		return "Title: "+this.title+"\nDescription: "+this.description+"\nType: "+this.type+"\nClosed: "+this.closed;
	}
}
