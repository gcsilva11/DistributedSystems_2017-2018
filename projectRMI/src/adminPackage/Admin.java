package adminPackage;

import java.awt.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

import RMIPackage.*;

public class Admin {
	private static Scanner input;

	public static void main(String[] args){
		String hostname;
		int def_port;
		input = new Scanner(System.in);
		String choice;

        if (args.length == 2){
            hostname = args[0];
            def_port = Integer.parseInt(args[1]);
        }
        else{
            hostname = "localhost";
            def_port = 6500;
        }
		
		try{
			VotingAdminInterface vote = (VotingAdminInterface) LocateRegistry.getRegistry(hostname,def_port).lookup("vote_booth");
			elecCheck checkThread = new elecCheck(vote);
			checkThread.start();
			
			while(true){
				System.out.println("Admin console ready.What do you want to do?\n1-Register a new user\n"
						+ "2-Add a new department\n3-Create an election\n4-Manage candidate lists"
						+ "\n5-Edit an election\n6-Voting table status");
				choice = input.nextLine();
				
				switch(choice){
					case "1":					
						// Informação user
						String name;
						String ID;
						String myDate;
						String phone;
						int profession;
						String department;
						String password;
						
						System.out.print("Register requested.\n");
						
						// Nome
						System.out.print("Name: ");
						name = input.nextLine();

                        // No. ID
						System.out.print("\nID: ");
						ID = input.nextLine();
						
						// Data expiração ID
						System.out.print("\nExpiration Date(dd-MM-yyy hh:mm:ss):");
						myDate = input.nextLine();
						SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
						Date expDate = sdf.parse(myDate);
						Calendar cal = Calendar.getInstance();
						cal.setTime(expDate);

                        // No. Telefone
						System.out.print("\nPhone number: ");
						phone = input.nextLine();

						// Profissão
						System.out.print("\nProfession (1-Student, 2-Professor, 3- Employee): ");
						profession = Integer.parseInt(input.nextLine());
						
						// Departamento
						System.out.print("\nDepartment: ");
						department = input.nextLine();
	
						// Password
						System.out.print("\nPassword: ");
						password = input.nextLine();
					
						User user = new User(name,ID,cal,phone,profession,department,password);

                        int failed = 0;
						for(int i=0;i<10;i++) {
                            try {
                                boolean ack = vote.registerUser(user);
                                if (ack) {
                                    System.out.println("Successfully registered!");
                                } else {
                                    System.out.println("Error: Couldn't register new user...");
                                }
                                break;
                            } catch (Exception e) {
                                failed++;
                                if(failed== 10){
                                    System.out.println("RMI Timeout on User registry, trying to reconnect");
                                    try{
                                        Thread.sleep(1000);
                                        vote = (VotingAdminInterface) LocateRegistry.getRegistry(6500).lookup("vote_booth");
                                    } catch (Exception e2){
                                        System.out.println("RMI server not responding.");
                                        break;
                                    }
                                }
                                Thread.sleep(3000);
                            }
                        }
						break;
						
					case "2":

						String depName;
						String depID;
						String facName;

						System.out.println("\nAdd a new department:");
						// Nome departamento
						System.out.print("\nDepartment Name: ");
						depName = input.nextLine();

						// ID
						System.out.print("\nDepartment ID: ");
						depID = input.nextLine();

						// Faculdade
						System.out.print("\nFaculty name: ");
						facName = input.nextLine();

						Department dep = new Department(depName,depID,facName);

						failed =0;
						for(int i=0;i<10;i++) {
                            try {
                                boolean newDepAck = vote.registerDep(dep);
                                if (newDepAck) {
                                    System.out.println("New department added!");
                                } else {
                                    System.out.println("Error adding the new department...");
                                }
                                failed = 0;
                                break;
                            } catch (Exception e) {
                                failed++;
                                if (failed == 10) {
                                    System.out.println("RMI Timeout on department creation, trying to reconnect");
                                    try {
                                        Thread.sleep(1000);
                                        vote = (VotingAdminInterface) LocateRegistry.getRegistry(6500).lookup("vote_booth");
                                    } catch (Exception e2) {
                                        System.out.println("RMI server not responding.");
                                        break;
                                    }
                                }
                                Thread.sleep(3000);
                            }
                        }
						break;

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
							
							System.out.print("Student election - Department(id):  ");
							depID = input.nextLine();
							System.out.print("\nViable lists: \n");
							
							ArrayList <candidateList> available;

                            available = vote.getList(1);
							
							for(int i=0;i<available.size();i++){
								System.out.println("Title: " + available.get(i).getName() + " ID: " + available.get(i).getID());
							}
							
							System.out.println("Input the ID of the lists you want to add (0 to stop):");
							
							ArrayList <candidateList> toAdd = new ArrayList <candidateList>();
							String IDchoice = input.nextLine();
							while(!IDchoice.equals("0")){
								for(int i=0;i<available.size();i++){
									if(IDchoice.equals(available.get(i).getID())){
										toAdd.add(available.get(i));
									}
								}
								IDchoice = input.nextLine();
							}


                            Election election = new Election(title,description,cal2,cal3,type,toAdd);

							failed = 0;
							for(int i=0;i<10;i++){
							try{
							    boolean studElecAdd = vote.newElection(election);
							    if(studElecAdd){
							        System.out.println("Successfully created the election!");
                                    System.out.print("Add voting tables (by dep ID) to the election (0 to stop): ");
									ArrayList <String> depTables = new ArrayList<String>();
                                    String depId = input.nextLine();
                                    while(!depId.equals("0")){
                                        depTables.add(depId);
										depId = input.nextLine();
									}

                                    boolean boothAck = vote.addBooth(election.getTitle(),depTables);

                                    if(boothAck){
                                        System.out.println("Booths added successfully");
                                    }
                                    else{
										System.out.println("Error adding booths.");
									}
									break;
                                }
                                else{
                                    System.out.println("Error creating election...");
                                    break;
								}
                                } catch (Exception e) {
                                failed++;
                                    if (failed == 10) {
                                        System.out.println("RMI Timeout on election creation, trying to reconnect");
                                        try {
                                            Thread.sleep(1000);
                                            vote = (VotingAdminInterface) LocateRegistry.getRegistry(6500).lookup("vote_booth");
                                        } catch (Exception e2) {
                                            System.out.println("RMI server not responding.");
                                            break;
                                        }
                                    }
                                Thread.sleep(3000);
                            }
							}
							break;
                        }
						else{
							
							System.out.println("Council election");
							System.out.println("Viable lists: ");
							
							ArrayList <candidateList> available = new ArrayList <candidateList>();
							
							available = vote.getList(2);
							
							for(int i=0;i<available.size();i++){
								System.out.println("Title: " + available.get(i).getName() + " ID: " + available.get(i).getID());
							}
							
							System.out.println("Input the ID of the lists you want to add:");
							
							ArrayList <candidateList> toAdd = new ArrayList <candidateList>();

                            String IDchoice = input.nextLine();
                            while(!IDchoice.equals("0")){
                                for(int i=0;i<available.size();i++){
                                    if(IDchoice.equals(available.get(i).getID())){
                                        toAdd.add(available.get(i));
                                    }
                                }
                                IDchoice = input.nextLine();
                            }
							
							Election election = new Election(title,description,cal2,cal3,type,toAdd);
							failed = 0;
							try{
							boolean genElecAdd = vote.newElection(election);

							if(genElecAdd){
								System.out.println("Successfully created the election!");
                                System.out.print("Add voting tables (by dep ID) to the election (0 to stop): ");
                                ArrayList <String> depTables = new ArrayList<String>();
                                String depId = input.nextLine();
                                while(!depId.equals("0")){
                                    depTables.add(depId);
                                    depId = input.nextLine();
                                }

                                boolean boothAck = vote.addBooth(election.getTitle(),depTables);

                                if(boothAck){
                                    System.out.println("Booths added successfully");
                                }
                                else{
                                    System.out.println("Error adding booths.");
                                }
                                break;
                            }
                            else{
                                System.out.println("Error creating election...");
                                break;
                            }
                            } catch (Exception e) {
                            failed++;
                            if (failed == 10) {
                                System.out.println("RMI Timeout on election creation, trying to reconnect");
                                try {
                                    Thread.sleep(1000);
                                    vote = (VotingAdminInterface) LocateRegistry.getRegistry(6500).lookup("vote_booth");
                                } catch (Exception e2) {
                                    System.out.println("RMI server not responding.");
                                    break;
                                }
                            }
                            Thread.sleep(3000);
                        }
						}
						break;
					case "4":
						System.out.println("Manage candidate lists");

						System.out.print("1-Create new list\n2-Delete a list\n3-Edit a list\nInput: ");

						choice = input.nextLine();

						switch(choice) {

                            case "1":

                                System.out.println("Create a new list");
                                System.out.println("List type? 1- Student,2 - Teacher,3 - Employees");
                                int listType = Integer.parseInt(input.nextLine());

                                if (listType == 1) {
                                    System.out.println("Student list creation\nPlease input the IDs of students to add to the list(0 to exit):");
                                    ArrayList<User> studentList = new ArrayList<User>();
                                    String studentId = input.nextLine();
                                    while (!studentId.equals("0")) {
                                        User newStudent = vote.findId(studentId, 1);
                                        if (newStudent.getID().equals("Bad id")) {
                                            System.out.println("No student with such id");
                                        } else {
                                            studentList.add(newStudent);
                                        }
                                        studentId = input.nextLine();
                                    }

                                    System.out.print("List Name: ");
                                    String listName = input.nextLine();
                                    System.out.print("\nList ID: ");
                                    String listID = input.nextLine();

                                    candidateList cl = new candidateList(listName, listID, 1, studentList);

                                    failed = 0;
                                    for (int i = 0; i < 10; i++) {
                                        try {

                                            boolean ackCandidate = vote.createList(cl);

                                            if (ackCandidate) {
                                                System.out.println("List successfully created!");
                                            } else {
                                                System.out.println("Problem creating the list...");
                                            }
                                        } catch (Exception e) {
                                            failed++;
                                            if (failed == 10) {
                                                System.out.println("RMI Timeout on election creation, trying to reconnect");
                                                try {
                                                    Thread.sleep(1000);
                                                    vote = (VotingAdminInterface) LocateRegistry.getRegistry(6500).lookup("vote_booth");
                                                } catch (Exception e2) {
                                                    System.out.println("RMI server not responding.");
                                                    break;
                                                }
                                            }
                                            Thread.sleep(3000);
                                        }
                                        break;
                                    }
                                    break;
                                } else if (listType == 2) {

                                    System.out.println("Teacher list creation\nPlease input the IDs of teachers to add to the list(0 to exit):");
                                    ArrayList<User> teacherList = new ArrayList<User>();
                                    String teacherId = input.nextLine();
                                    while (!teacherId.equals("0")) {
                                        User newTeacher = vote.findId(teacherId, 2);
                                        if (newTeacher.getID().equals("Bad id")) {
                                            System.out.println("No teacher with such id");
                                        } else {
                                            teacherList.add(newTeacher);
                                        }
                                        teacherId = input.nextLine();
                                    }

                                    System.out.print("List Name: ");
                                    String listName = input.nextLine();
                                    System.out.print("\nList ID: ");
                                    String listID = input.nextLine();

                                    candidateList cl = new candidateList(listName, listID, 2, teacherList);

                                    boolean ackCandidate = vote.createList(cl);

                                    if (ackCandidate) {
                                        System.out.println("List successfully created!");
                                    } else {
                                        System.out.println("Problem creating the list...");
                                    }

                                } else {

                                    System.out.println("Employee list creation\nPlease input the IDs of employees to add to the list(0 to exit):");
                                    ArrayList<User> employeeList = new ArrayList<User>();
                                    String employeeId = input.nextLine();
                                    while (!employeeId.equals("0")) {
                                        User newEmployee = vote.findId(employeeId, 3);
                                        if (newEmployee.getID().equals("Bad id")) {
                                            System.out.println("No employee with such id");
                                        } else {
                                            employeeList.add(newEmployee);
                                        }
                                        employeeId = input.nextLine();
                                    }

                                    System.out.print("List Name: ");
                                    String listName = input.nextLine();
                                    System.out.print("\nList ID: ");
                                    String listID = input.nextLine();

                                    candidateList cl = new candidateList(listName, listID, 3, employeeList);

                                    boolean ackCandidate = vote.createList(cl);

                                    if (ackCandidate) {
                                        System.out.println("List successfully created!");
                                    } else {
                                        System.out.println("Problem creating the list...");
                                    }

                                }
                                break;

                            case "2":

                                System.out.println("Delete a candidate list");
                                System.out.print("Input the ID of the list to delete: ");
                                String listID = input.nextLine();

                                boolean deleteAck = vote.deleteList(listID);

                                if (deleteAck) {
                                    System.out.println("List deleted successfully!");
                                } else {
                                    System.out.println("Error deleting list");
                                }

                                break;
                            case "3":
                                System.out.println("Edit a candidate list name");
                                System.out.print("Input the ID of the list to edit: ");
                                String listId = input.nextLine();
                                System.out.print("Input new list name: ");
                                String newName = input.nextLine();
                                boolean editAck = vote.editList(listId, newName);
                                if (editAck) {
                                    System.out.println("List name edited successfully!");
                                } else {
                                    System.out.println("Error editing name");
                                }
                                break;
                        }
						case "5":
							
							System.out.println("Edit election");
							System.out.print("Insert the title of the election to edit: ");
							String oldElecName = input.nextLine();
							Election oldElec = vote.getElection(oldElecName);
							if(!oldElec.getTitle().equals(null)){
								
								
								//Verifica se esta a decorrer
								if(oldElec.getStartDate().before(Calendar.getInstance())){
									System.out.println("Election already ongoing, cannot edit");
									break;
								}
								
								else{
									System.out.print("\nInsert new election title: ");
									oldElec.setTitle(input.nextLine());

									System.out.print("\nInsert new election description: ");
									oldElec.setDescription(input.nextLine());

									System.out.print("\nSet new start date: ");
									date = input.nextLine();
									SimpleDateFormat sdf4 = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
									startDate = sdf4.parse(date);
									Calendar cal4 = Calendar.getInstance();
									cal4.setTime(startDate);
									oldElec.setStartDate(cal4);

									System.out.print("\nSet new end date: ");
									date = input.nextLine();
									SimpleDateFormat sdf5 = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
									endDate = sdf5.parse(date);
									Calendar cal5 = Calendar.getInstance();
									cal5.setTime(endDate);
									oldElec.setEndDate(cal5);

									//Substitui no rmi
									boolean editElec = vote.editElec(oldElec);

									if(editElec){
										System.out.println("Election edited successfully");
									}
									else{
										System.out.println("Error editing the election...");
									}
								}
								
							}
							else{
								System.out.println("Election with that title doesn't exist");
								break;
							}
						default: 
							System.out.println("Invalid choice, going back to menu");
							break;
						}
				}
			}catch (Exception e) {
                System.out.println("Exception in main: " + e);
                e.printStackTrace();
		}
	}
}


class elecCheck extends Thread{
	private VotingAdminInterface vote;
	private ArrayList <Election> seen = new ArrayList <Election>();
    private ArrayList<Integer> printable = new ArrayList<Integer>();

	public elecCheck(VotingAdminInterface vote){
		this.vote = vote;
	}
	
	public void run() {
        System.out.println("ELECTION THREAD: Checking for expired elections");
        int failed=0;
        while (true) {
            try {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                ArrayList<Election> expired = vote.checkElecDate();
                boolean checked=false;
                Election toAdd = null;
                for (int i = 0; i < expired.size(); i++) {
                    for (int j = 0; j < seen.size(); j++) {
                        if (expired.get(i).getTitle().equals(seen.get(j).getTitle())) {
                            checked = true;
                        }
                    }
                    if(checked==false){
                        toAdd = expired.get(i);
                        printable.add(0);
                        seen.add(toAdd);
                    }
                    checked = false;
                }

                for(int i=0;i <seen.size();i++){
                    if(printable.get(i)==0){
                        printable.set(i,1);
                        System.out.println("Election expired - " + toAdd.getTitle());
                    }
                }

                failed = 0;
            } catch (RemoteException e) {
                failed++;
                if(failed == 3){
                    System.out.println("Timeout - RMI didn't respond for 30 seconds, trying to reconnect...");
                    try{
                        this.vote = (VotingAdminInterface) LocateRegistry.getRegistry(6500).lookup("vote_booth");
                    } catch (Exception e2){
                        System.out.println("RMI server not responding, shutting down thread");
                        break;
                    }
                }
            }
        }
    }
}