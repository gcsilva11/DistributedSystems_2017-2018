package projectRMI;

import java.io.Serializable;
import java.util.ArrayList;


public class DepList implements Serializable{
     

	private static final long serialVersionUID = 1L;
	private ArrayList <Department> departments = new ArrayList <Department>();

    DepList(){};
    DepList(ArrayList <Department> a){
        this.departments = a;
    }
    
    public void addDep(Department u){
        this.departments.add(u);
        
    }
    
    public ArrayList <Department> getDeps() {
        return this.departments;
    }
    
}
