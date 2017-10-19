package adminPackage;
import java.rmi.registry.LocateRegistry;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

import RMIPackage.Department;
import RMIPackage.Election;
import RMIPackage.User;
import RMIPackage.candidateList;

public class Admin {
	
	private static Scanner input;

	public static void main(String[] args){
		
		input = new Scanner(System.in);
		String choice;
		
		try{
			
			VotingAdminInterface vote = (VotingAdminInterface) LocateRegistry.getRegistry(6500).lookup("vote_booth");
			
			while(true){
				System.out.println("Admin console ready.\nWhat do you want to do?\n1-Register a new user\n"
						+ "2-Manage departments and faculties\n3- Create an election\n4-Manage candidate lists\n5-Manage voting booths");
				choice = input.nextLine();
				
				switch(choice){
				
					case "1":					
						//Voter Data
						String name;
						String ID;
						String myDate;
						String phone;
						int profession;
						String department;
						String password;
						
						System.out.print("Register requested.\n");
						
						//Name
						System.out.print("Username: ");
						name = input.nextLine();
	
						//ID Number
						System.out.print("\nID: ");
						ID = input.nextLine();
						
						//ID Expiration Date
						System.out.print("\nExpiration Date(dd-MM-yyy hh:mm:ss):");
						myDate = input.nextLine();
						SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
						Date expDate = sdf.parse(myDate);
						Calendar cal = Calendar.getInstance();
						cal.setTime(expDate);
						
						//Phone Number
						System.out.print("\nPhone number: ");
						phone = input.nextLine();
						
						//Profession
						System.out.print("\nProfession (1-Student, 2-Professor, 3- Employee): ");
						profession = Integer.parseInt(input.nextLine());
						
						//Department
						System.out.print("\nDepartment: ");
						department = input.nextLine();
	
						//Password
						System.out.print("\nPassword: ");
						password = input.nextLine();
					
						User user = new User(name,ID,cal,phone,profession,department,password);
						
						boolean ack = vote.registerUser(user);
						
						if(ack){
							System.out.println("Successfully registered!");
						}
						else{
							System.out.println("Error: Couldn't register new user...");
						}
						
						break;
						
					case "2":
						
						System.out.print("Manage departments/faculties.\n");
						
						String depName;
						String depID;
						String facName;
						
						System.out.println("1-Add New Department\n2-Delete a deparment\n3-Edit department info");
						choice = input.nextLine();
						
						switch(choice){
						case "1":
							
							System.out.println("\nAdd a new department:");
							//Department name
							System.out.print("\nDepartment Name: ");
							depName = input.nextLine();
							
							//ID
							System.out.print("\nDepartment ID: ");
							depID = input.nextLine();
							
							//Faculty
							System.out.print("\nFaculty name: ");
							facName = input.nextLine();
							
							Department dep = new Department(depName,depID,facName);
							
							boolean newDepAck = vote.registerDep(dep);
							
							if(newDepAck){
								System.out.println("New department added!");
							}
							else{
								System.out.println("Error adding the new department...");
							}
							break;
							
						case "2":
							
							System.out.println("\nEdit a department");
							//Department name
							System.out.print("\nID of the department to edit: ");
							depID = input.nextLine();
							
							//ID
							System.out.print("\nNew department name: ");
							depName = input.nextLine();
							
							//Faculty
							System.out.print("\nNew faculty name: ");
							facName = input.nextLine();
							
							Department editDep = new Department(depName,depID,facName);
							
							boolean editDepAck = vote.registerDep(editDep);
							
							if(editDepAck){
								System.out.println("Department edited!");
							}
							else{
								System.out.println("Error editing the new department...");
							}
							break;
							
						case "3":
							
							System.out.println("\nEdit a department");
							//Department name
							System.out.print("\nID of the department to delete: ");
							depID = input.nextLine();
							
							Department deleteDep = new Department(depID);
							
							boolean deleteDepAck = vote.registerDep(deleteDep);
							
							if(deleteDepAck){
								System.out.println("Department edited!");
							}
							else{
								System.out.println("Error editing the new department...");
							}
							break;

						default:
							System.out.println("Not a valid choice, back to menu...");
							break;
						}
						
					case "3":
						
						int type;
						String title;
						String description;
						String date;
						Date startDate;
						Date endDate;
						
						System.out.println("\nCreate an election");
						
						System.out.print("\nElection type(1-Student Association 2- General Council: ");
						type = Integer.parseInt(input.nextLine());
						
						System.out.print("\nTitle: ");
						title = input.nextLine();
						
						System.out.print("\nDescription: ");
						description = input.nextLine();
						
						System.out.print("\nStart date (dd-MM-yyy hh:mm:ss): ");
						date = input.nextLine();
						SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
						startDate = sdf2.parse(date);
						Calendar cal2 = Calendar.getInstance();
						cal2.setTime(startDate);
						
						System.out.print("\nEnd date (dd-MM-yyy hh:mm:ss): ");
						date = input.nextLine();
						SimpleDateFormat sdf3 = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
						endDate = sdf3.parse(date);
						Calendar cal3 = Calendar.getInstance();
						cal3.setTime(endDate);
						
						if(type == 1){
							
							System.out.println("Student election - What department? - ");
							depID = input.nextLine();
							System.out.print("\nViable lists: ");
							
							ArrayList <candidateList> available = new ArrayList <candidateList>();
							
							available = vote.getList(1);
							
							for(int i=0;i<available.size();i++){
								System.out.println("Title:" + available.get(i).getName() + " ID:" + available.get(i).getID());
							}
							
							System.out.println("Input the ID of the ones you want to add:");
							
							ArrayList <candidateList> toAdd = new ArrayList <candidateList>();
							
							while(!input.nextLine().equals("0")){
								for(int i=0;i<available.size();i++){
									if(Integer.parseInt(input.nextLine())==available.get(i).getID()){
										toAdd.add(available.get(i));
									}
								}
							}
							
							Election election = new Election(title,description,cal2,cal3,type,depID,toAdd);
							
							boolean studElecAdd = vote.newElection(election);
							
							if(studElecAdd){
								System.out.println("Successfully created the election!");
							}
							else{
								System.out.println("Error creating election...");
							}
						}
						else{
							
							System.out.println("Council election");
							System.out.print("Viable lists: ");
							
							ArrayList <candidateList> available = new ArrayList <candidateList>();
							
							available = vote.getList(2);
							
							for(int i=0;i<available.size();i++){
								System.out.println("Title:" + available.get(i).getName() + " ID:" + available.get(i).getID());
							}
							
							System.out.println("Input the ID of the ones you want to add:");
							
							ArrayList <candidateList> toAdd = new ArrayList <candidateList>();
							
							while(!input.nextLine().equals("0")){
								for(int i=0;i<available.size();i++){
									if(Integer.parseInt(input.nextLine())==available.get(i).getID()){
										toAdd.add(available.get(i));
									}
								}
							}
							
							Election election = new Election(title,description,cal2,cal3,type,toAdd);
							
							boolean genElecAdd = vote.newElection(election);
							
							if(genElecAdd){
								System.out.println("Successfully created the election!");
							}
							else{
								System.out.println("Error creating election...");
							}
						}
						
						
					/*case 3:
						
					
						
						if(type==1){
							auxSplit = splitCommand[7].split("\\|");
							String depID = auxSplit[1];
							int i = 8;
							while(!splitCommand[i].split("\\|").equals(" end")){
								auxSplit = splitCommand[i].split("\\|");
								candidateListID.add(auxSplit[1]);
								i++;
							}
							
							ArrayList <candidateList> candidates = vote.getList(candidateListID);
							
							if(!candidates.isEmpty()){
								Election election = new Election(title,description,cal1,cal2,type,depID,candidates);
								boolean ack = vote.newElection(election);
								if(ack){
									System.out.println("Election successfully created!");
								}
								else{
									System.out.println("Error creating election...");
								}
							}
							else{
								System.out.println("No valid candidate list ID inputed");
							}
							
						}
						else{
							int i = 7;
							while(!splitCommand[i].split("\\|").equals(" end")){
								auxSplit = splitCommand[i].split("\\|");
								candidateListID.add(auxSplit[1]);
								i++;
							}
							
							ArrayList <candidateList> candidates = vote.getList(candidateListID);
							
							if(!candidates.isEmpty()){
								Election election = new Election(title,description,cal1,cal2,type,candidates);
								boolean ack = vote.newElection(election);
								if(ack){
									System.out.println("Election successfully created!");
								}
								else{
									System.out.println("Error creating election...");
								}
							}
							else{
								System.out.println("No valid candidate list ID inputed");
							}
							
						}*/
				}
			}
		} catch (Exception e) {
			System.out.println("Exception in main: " + e);
			e.printStackTrace();
		}
	}
}
