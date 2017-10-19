package RMIPackage;

import java.io.Serializable;
import java.util.ArrayList;

public class candidateListList implements Serializable{
	

	private static final long serialVersionUID = 1L;
	private ArrayList <candidateList> candidateList = new ArrayList <candidateList>();

    candidateListList(){};
    candidateListList(ArrayList <candidateList> cl){
        this.candidateList = cl;
    }
    
    public void addCandidateList(candidateList cl){
        this.candidateList.add(cl);
        
    }
    
    public ArrayList <candidateList> getCandidateList() {
        return this.candidateList;
    }
    
}
