package adminPackage;

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
		input = new Scanner(System.in);
		String choice;
		
		try{
			VotingAdminInterface vote = (VotingAdminInterface) LocateRegistry.getRegistry(6500).lookup("vote_booth");
			
			while(true){
				System.out.println("Admin console ready.\nWhat do you want to do?\n1-Register a new user\n"
						+ "2-Manage departments and faculties\n3- Create an election\n4-Manage candidate lists\n5-Edit an election");
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
						System.out.print("Username: ");
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
							// Nome departamento
							System.out.print("\nID of the department to edit: ");
							depID = input.nextLine();
							
							// ID
							System.out.print("\nNew department name: ");
							depName = input.nextLine();
							
							// Faculdade
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
							// Nome departamento
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
									if(input.nextLine().equals(available.get(i).getID())){
										toAdd.add(available.get(i));
									}
								}
							}
							
							Election election = new Election(title,description,cal2,cal3,type,toAdd);
							
							boolean studElecAdd = vote.newElection(election);
							
							if(studElecAdd){
								System.out.println("Successfully created the election!");
								System.out.print("Add voting tables (by dep ID) to the election (0 to stop): ");
								String depId = input.nextLine();
								ArrayList <String> depTables = new ArrayList<String>();
								while(!depId.equals("0")){
									depTables.add(depId);
									depId = input.nextLine();
								}
								
								boolean boothAck = vote.addBooth(election.getTitle(),depTables);
								
								if(boothAck){
									System.out.println("Booths added successfully");
								}
								
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
									if(input.nextLine().equals(available.get(i).getID())){
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
					case "4":
						System.out.println("Manage candidate lists");
						
						System.out.print("1 - Create new list\n2-Delete a list\n3-Edit a list\nInput: ");
						
						choice = input.nextLine();
						
						switch(choice){
						
						case "1":
							
							System.out.println("Create a new list");
							System.out.println("List type? 1- Student,2 - Teacher,3 - Employees");
							int listType = Integer.parseInt(input.nextLine());
							
							if(listType == 1){
								System.out.println("Student list creation\nPlease input the IDs of students to add to the list(0 to exit):");
								ArrayList <User> studentList = new ArrayList <User>();
								String studentId = input.nextLine();
								while(!studentId.equals("0")){
									User newStudent = vote.findId(studentId,1);
									if(newStudent.getID().equals("Bad id")){
										System.out.println("No student with such id");
									}
									else{
										studentList.add(newStudent);
									}
									studentId = input.nextLine();
								}
								
								System.out.print("List Name: ");
								String listName = input.nextLine();
								System.out.print("\nList ID: ");
								String listID = input.nextLine();
								
								candidateList cl = new candidateList(listName,listID,1,studentList);
								
								boolean ackCandidate = vote.createList(cl);
								
								if(ackCandidate){
									System.out.println("List successfully created!");
								}
								else{
									System.out.println("Problem creating the list...");
								}
								
							}
							else if(listType == 2){
								
								System.out.println("Teacher list creation\nPlease input the IDs of teachers to add to the list(0 to exit):");
								ArrayList <User> teacherList = new ArrayList <User>();
								String teacherId = input.nextLine();
								while(!teacherId.equals("0")){
									User newTeacher = vote.findId(teacherId,2);
									if(newTeacher.getID().equals("Bad id")){
										System.out.println("No student with such id");
									}
									else{
										teacherList.add(newTeacher);
									}
									teacherId = input.nextLine();
								}
								
								System.out.print("List Name: ");
								String listName = input.nextLine();
								System.out.print("\nList ID: ");
								String listID = input.nextLine();
								
								candidateList cl = new candidateList(listName,listID,2,teacherList);
								
								boolean ackCandidate = vote.createList(cl);
								
								if(ackCandidate){
									System.out.println("List successfully created!");
								}
								else{
									System.out.println("Problem creating the list...");
								}
								
							}
							else{
								
								System.out.println("Employee list creation\nPlease input the IDs of employees to add to the list(0 to exit):");
								ArrayList <User> employeeList = new ArrayList <User>();
								String employeeId = input.nextLine();
								while(!employeeId.equals("0")){
									User newEmployee = vote.findId(employeeId,3);
									if(newEmployee.getID().equals("Bad id")){
										System.out.println("No employee with such id");
									}
									else{
										employeeList.add(newEmployee);
									}
									employeeId = input.nextLine();
								}
								
								System.out.print("List Name: ");
								String listName = input.nextLine();
								System.out.print("\nList ID: ");
								String listID = input.nextLine();
								
								candidateList cl = new candidateList(listName,listID,3,employeeList);
								
								boolean ackCandidate = vote.createList(cl);
								
								if(ackCandidate){
									System.out.println("List successfully created!");
								}
								else{
									System.out.println("Problem creating the list...");
								}
								
							}
							break;
						
						case "2":
							
							System.out.println("Delete a candidate list");
							System.out.print("Input the ID of the list to delete: ");
							String listID = input.nextLine();
							
							boolean deleteAck = vote.deleteList(listID);
							
							if(deleteAck){
								System.out.println("List deleted successfully!");
							}
							else{
								System.out.println("Error deleting list");
							}
						
							break;
						case "3":
							System.out.println("Edit a candidate list name");
							System.out.print("Input the ID of the list to edit: ");
							String listId = input.nextLine();
							System.out.print("Input new list name: ");
							String newName = input.nextLine();
							boolean editAck = vote.editList(listId,newName);
							if(editAck){
								System.out.println("List name edited successfully!");
							}
							else{
								System.out.println("Error editing name");
							}
							
						case "5":
							/*
							System.out.println("Edit election");
							System.out.print("Insert the title of the election to edit: ");
							String oldElecName = input.nextLine();
							//Election oldElec = vote.getElection(oldElecName);
							
							//Verifica se esta a decorrer
							//Se nao
							
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
							//boolean editElec = vote.editElec(oldElec);
							
							if(editElec){
								System.out.println("Election edited successfully");
							}
							else{
								System.out.println("Error editing the election...");
							}
							
							*/
						default: 
							System.out.println("Invalid choice, going back to menu");
							break;
						}
				}
			}
		} catch (Exception e) {
			System.out.println("Exception in main: " + e);
			e.printStackTrace();
		}
	}
}
