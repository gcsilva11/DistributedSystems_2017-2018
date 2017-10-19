package RMIPackage;


import java.io.Serializable;
import java.util.ArrayList;


public class ElectionList implements Serializable{
     

	private static final long serialVersionUID = 1L;
	private ArrayList <Election> elections = new ArrayList <Election>();

    ElectionList(){};
    ElectionList(ArrayList <Election> el){
        this.elections = el;
    }
    
    public void addELection(Election el){
        this.elections.add(el);
        
    }
    
    public ArrayList <Election> getElections() {
        return this.elections;
    }
    
}