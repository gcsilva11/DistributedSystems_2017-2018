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
	private String depID;
	private ArrayList <candidateList> candidates;
	private boolean closed;
	
	public Election(String title,String description,Calendar startDate,Calendar endDate,int type,String depID,ArrayList <candidateList> candidates){
		this.title=title;
		this.description =description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.type = type;
		this.depID = depID;
		this.candidates = candidates;
		this.closed = false;
	}
	
	public Election(String title,String description,Calendar startDate,Calendar endDate,int type,ArrayList <candidateList> candidates){
		this.title=title;
		this.description =description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.type = type;
		this.depID = null;
		this.closed = false;
		this.candidates = candidates;
	}
	
	public String getTitle(){
		return this.title;
	}
	
	public boolean getClosed(){
		return this.closed;
	}
	
	public String getDescription(){
		return this.description;
	}
	
	public String getDepID(){
		return this.depID;
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
}
