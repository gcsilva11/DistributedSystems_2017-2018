package RMIPackage;

import sun.awt.geom.AreaOp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private String idNumber;
    private Calendar idExpDate;
    private String phone;
    private int profession;
    private String department;
    private String password;
    private ArrayList<Election> votedIn;
    private ArrayList<candidateList> listVoted;
    private ArrayList<Calendar> whenVoted;
    private ArrayList<Department> whereVoted;

    public User(String name, String idNumber, Calendar expDate, String phone, int profession, String department, String password) {
        this.name = name;
        this.idNumber = idNumber;
        this.idExpDate = expDate;
        this.phone = phone;
        this.profession = profession;
        this.department = department;
        this.password = password;
    }

    public User() {
        this.name = null;
        this.idNumber = "Bad id";
        this.idExpDate = null;
        this.phone = null;
        this.profession = 0;
        this.department = null;
        this.password = null;
    }

    public String getName() { return this.name; }

    public String getID() { return this.idNumber; }

    public Calendar getExpDate() { return this.idExpDate; }

    public String getPhone() { return this.phone; }

    public int getProfession() { return this.profession; }

    public String getDepartment() { return this.department; }

    public String getPassword() { return this.password; }

    public String getInfo() {
        return "Name: " + this.getName() + "\nID: " + this.idNumber + "\nPhone: " + this.getPhone() + "\nProfession: " + this.getProfession() + "\nDepartment: " + this.getDepartment() + "\nPassword: " + this.getPassword();
    }

    public void setVotes(Election e, candidateList cl) {

        // Sets time voted
        if(this.whenVoted == null){
            this.whenVoted = new ArrayList<Calendar>();
            this.whenVoted.add(Calendar.getInstance());
        }
        else {
            this.whenVoted.add(Calendar.getInstance());
        }

        // Sets Election voted
        if(this.votedIn == null) {
            this.votedIn = new ArrayList<Election>();
            this.votedIn.add(e);
        }
        else {
            this.votedIn.add(e);
        }

        // Sets Lists voted
        if(this.listVoted ==null){
            this.listVoted = new ArrayList<candidateList>();
            this.listVoted.add(cl);
        }
        else{
            this.listVoted.add(cl);
        }
    }

    public boolean hasVoted(Election e){
        if(this.votedIn != null){
            for(int i = 0;i<this.votedIn.size();i++){
                if(this.votedIn.get(i).getTitle().equals(e.getTitle()) || this.votedIn.get(i).getTitle().equals("NULLVOTE") || this.votedIn.get(i).getTitle().equals("WHITEVOTE")) {
                    return true;
                }
            }
        }
        return false;
    }

    public String getVotes(){
        String aux="";
        for (int i = 0;i<this.votedIn.size();i++){
            aux = aux + "\t\t" + "eleição : " + this.votedIn.get(i).getTitle() + " - lista: " + this.listVoted.get(i).getName() + "\n";
        }
        return aux;
    }

}

