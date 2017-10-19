package RMIPackage;

import java.io.Serializable;
import java.util.ArrayList;


public class UserList implements Serializable{
	private static final long serialVersionUID = 1L;
	private ArrayList <User> users = new ArrayList <>();

    UserList(){};
    /*UserList(ArrayList <User> a){
        this.users = a;
    }*/
    
    public void addUser(User u){
        this.users.add(u);
    }
    
    public ArrayList <User> getUsers() {
        return this.users;
    }
}
