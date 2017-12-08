package adminPackage;

import java.awt.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;


import RMIPackage.*;
import com.sun.org.apache.regexp.internal.RE;

public class Admin {
    private static Scanner input;

    //Metodo Main
    public static void main(String[] args){
        String hostname;
        int rmiPort;
        input = new Scanner(System.in);
        String choice, choice2;

        if (args.length == 2){
            hostname = args[0];
            rmiPort = Integer.parseInt(args[1]);
        }
        else{
            hostname = "localhost";
            rmiPort = 6500;
        }

        System.out.println("RMI Hostname: ");
        hostname = input.nextLine();

        System.out.println("RMI Port");
        rmiPort = input.nextInt();


        try{
        	//Ligacao ao RMI
            VotingAdminInterface vote = (VotingAdminInterface) LocateRegistry.getRegistry(hostname,rmiPort).lookup("vote_booth");
            
            //Criacao e iniciacao da thread que verifica quando acabam as eleicoes
            elecCheck checkThread = new elecCheck(vote,hostname,rmiPort);
            checkThread.start();

            //Criacao e iniciacao da thread que verifica o estado das mesas de voto
            boothCheck boothThread = new boothCheck(vote);
            boothThread.start();

            int electionID, type, ID, profession, faculdadeID, facID, depID;
            String title, description, startDate, endDate, facName, depName, name, myDate, phone, password;

            //Menu
            while(true) {
                try {
                    System.out.println("Consola ADMIN. O que quer fazer\n1-User\n"
                            + "2-Faculdade e Departamentos\n3-Eleicoes\n4-Listas de candidatos"
                            + "\n5-Mesas de Voto");
                    choice = input.nextLine();

                    switch (choice) {
                        // USER
                        case "1":
                            System.out.println("1-Registar User\n2-Apagar User");
                            choice2 = input.nextLine();
                            switch (choice2) {
                                // REGISTAR
                                case "1":
                                    System.out.print("\nNovo user");

                                    System.out.print("\nNome: ");
                                    name = input.nextLine();

                                    System.out.print("\nID: ");
                                    ID = Integer.parseInt(input.nextLine());

                                    System.out.print("\nData de validade(yyyy-MM-dd hh:mm:ss):");
                                    myDate = input.nextLine();

                                    System.out.print("\nTelemovel: ");
                                    phone = input.nextLine();

                                    System.out.print("\nPassword: ");
                                    password = input.nextLine();

                                    System.out.print("\nTipo de User (1-Estudante, 2-Professor, 3- Funcionario): ");
                                    profession = Integer.parseInt(input.nextLine());

                                    if (vote.registerUser(ID, name, password, phone, myDate, profession) && (profession == 1 || profession == 2 || profession == 3))
                                        System.out.println("\nRegisto efetuado!");
                                    else
                                        System.err.println("\nRegisto falhado");

                                    System.out.println("\nInsira faculdades a associar (0 para parar): ");
                                    faculdadeID = Integer.parseInt(input.nextLine());
                                    while (faculdadeID != 0) {
                                        if (vote.addUserFac(ID, faculdadeID))
                                            System.out.println("\nFaculdade Inserida");
                                        else
                                            System.out.println("\nFaculdade não existe");
                                        faculdadeID = Integer.parseInt(input.nextLine());
                                    }
                                    Thread.sleep(500);
                                    break;
                                // APAGAR
                                case "2":
                                    System.out.println("\nApagar User");

                                    // No. ID
                                    System.out.print("\nID: ");
                                    ID = Integer.parseInt(input.nextLine());

                                    if (vote.deleteUser(ID))
                                        System.out.println("\nEliminacao efetuada!");
                                    else
                                        System.err.println("\nEliminacao falhada");
                                    Thread.sleep(500);
                                    break;
                                default:
                                    System.out.println("\nOpcao Invalida");
                                    break;
                            }
                            Thread.sleep(500);
                            break;
                        // FACULDADE & DEPARTAMENTOS
                        case "2":

                            System.out.println("\n1-Adicionar Faculdade\n2-Adicionar Departamento\n3-Adicionar Unidade Organica\n4-Editar Faculdade\n5-Editar Departamento\n6-Apagar Faculdade\n7-Apagar Departamento\n8-Apgar Unidade Organica");
                            choice2 = input.nextLine();

                            switch (choice2) {
                                case "1":
                                    System.out.println("\nNova Faculdade");

                                    System.out.println("\nNome: ");
                                    facName = input.nextLine();

                                    if (vote.registerFac(facName))
                                        System.out.println("\nRegisto efetuado!");
                                    else
                                        System.err.println("\nRegisto falhado");
                                    Thread.sleep(500);
                                    break;
                                case "2":
                                    System.out.println("\nNovo Departamento (Necessita de uma faculdade já criada)");

                                    System.out.println("\nNome: ");
                                    depName = input.nextLine();

                                    System.out.println("\nID da faculdade a associar: ");
                                    facID = Integer.parseInt(input.nextLine());

                                    if (vote.registerDep(depName, facID))
                                        System.out.println("\nRegisto efetuado!");
                                    else
                                        System.err.println("\nRegisto falhado");
                                    Thread.sleep(500);
                                    break;
                                case "3":
                                    System.out.println("\nNova Unidade Organica (Necessita de uma faculdade já criada)");

                                    System.out.println("\nID da faculdade a associar: ");
                                    facID = Integer.parseInt(input.nextLine());

                                    if (vote.registerUnit(facID))
                                        System.out.println("\nRegisto efetuado!");
                                    else
                                        System.err.println("\nRegisto falhado");
                                    Thread.sleep(500);
                                    break;
                                case "4":
                                    System.out.println("\nEditar Faculdade");

                                    System.out.println("\nID da faculdade: ");
                                    facID = Integer.parseInt(input.nextLine());

                                    System.out.println("\nNovo nome:");
                                    facName = input.nextLine();

                                    if (vote.editFac(facID, facName))
                                        System.out.println("\nEdicao efetuada!");
                                    else
                                        System.err.println("\nEdicao falhada");
                                    Thread.sleep(500);
                                    break;
                                case "5":
                                    System.out.println("\nEditar Departamento");

                                    System.out.println("\nID do departamento:");
                                    depID = Integer.parseInt(input.nextLine());

                                    System.out.println("\nNovo nome:");
                                    depName = input.nextLine();

                                    if (vote.editDep(depID, depName))
                                        System.out.println("\nEdicao efetuada!");
                                    else
                                        System.err.println("\nEdicao falhada");
                                    Thread.sleep(500);
                                    break;
                                case "6":
                                    System.out.println("\nApagar Faculdade");

                                    System.out.println("\nID da Faculdade: ");
                                    facID = Integer.parseInt(input.nextLine());

                                    if (vote.deleteFac(facID))
                                        System.out.println("\nEliminicao efetuada!");
                                    else
                                        System.err.println("\nEliminicao falhada");
                                    Thread.sleep(500);
                                    break;
                                case "7":
                                    System.out.println("\nApagar Departamento");

                                    System.out.println("\nID do Departamento: ");
                                    depID = Integer.parseInt(input.nextLine());

                                    if (vote.deleteDep(depID))
                                        System.out.println("\nEliminicao efetuada!");
                                    else
                                        System.err.println("\nEliminicao falhada");
                                    Thread.sleep(500);
                                    break;
                                case "8":
                                    System.out.println("\nApagar Unidade Organica");

                                    System.out.println("\nID da Faculdade: ");
                                    facID = Integer.parseInt(input.nextLine());

                                    if (vote.deleteUnit(facID))
                                        System.out.println("\nEliminicao efetuada!");
                                    else
                                        System.err.println("\nEliminicao falhada");
                                    Thread.sleep(500);
                                    break;

                                default:
                                    System.out.println("\nOpcao Invalida");
                                    Thread.sleep(500);
                                    break;
                            }
                            break;

                        // ELEICAO
                        case "3":
                            System.out.println("\n1-Criar eleicao\n2-Editar texto eleicoes\n3-Editar data eleicoes\n4-Apagar eleicao");

                            choice2 = input.nextLine();

                            switch (choice2) {
                                case "1":
                                    System.out.println("\nID: ");
                                    electionID = Integer.parseInt(input.nextLine());

                                    System.out.println("\nNome: ");
                                    title = input.nextLine();

                                    System.out.println("\nDescricao: ");
                                    description = input.nextLine();

                                    System.out.print("\nData de inicio(yyyy-MM-dd hh:mm:ss):");
                                    startDate = input.nextLine();

                                    System.out.print("\nData de termino(yyyy-MM-dd hh:mm:ss):");
                                    endDate = input.nextLine();

                                    System.out.print("\nTipo de Eleicao (1-Conselho geral, 2-Nucleos): ");
                                    type = Integer.parseInt(input.nextLine());
                                    if (type == 1) {
                                        if (vote.addEl(electionID, title, description, type, startDate, endDate, 0))
                                            System.out.println("\nCriacao efetuada!");
                                        else
                                            System.err.println("\nCriacao falhada");
                                    } else if (type == 2) {
                                        System.out.println("\nFaculdade onde decorre: ");
                                        facID = Integer.parseInt(input.nextLine());
                                        if (vote.addEl(electionID, title, description, type, startDate, endDate, facID))
                                            System.out.println("\nCriacao efetuada!");
                                        else
                                            System.err.println("\nCriacao falhada");
                                    }
                                    Thread.sleep(500);
                                    break;
                                case "2":
                                    System.out.println("\nID: ");
                                    electionID = Integer.parseInt(input.nextLine());

                                    System.out.println("\nNome: ");
                                    title = input.nextLine();

                                    System.out.println("\nDescricao: ");
                                    description = input.nextLine();

                                    if (vote.editELText(electionID,title,description))
                                        System.out.println("\nEdicao efetuada!");
                                    else
                                        System.err.println("\nEdicao falhada");
                                    Thread.sleep(500);
                                    break;
                                case "3":
                                    System.out.println("\nID: ");
                                    electionID = Integer.parseInt(input.nextLine());

                                    System.out.print("\nData de inicio(yyyy-MM-dd hh:mm:ss):");
                                    startDate = input.nextLine();

                                    System.out.print("\nData de termino(yyyy-MM-dd hh:mm:ss):");
                                    endDate = input.nextLine();

                                    if (vote.editElDate(electionID,startDate,endDate))
                                        System.out.println("\nEdicao efetuada!");
                                    else
                                        System.err.println("\nEdicao falhada");
                                    Thread.sleep(500);
                                    break;
                                case "4":
                                    System.out.println("\nID: ");
                                    electionID = Integer.parseInt(input.nextLine());

                                    if (vote.deleteEL(electionID))
                                        System.out.println("\nEliminacao efetuada!");
                                    else
                                        System.err.println("\nEliminacao falhada");
                                    Thread.sleep(500);
                                    break;
                                default:
                                    System.out.println("\nOpcao Invalida");
                                    Thread.sleep(500);
                                    break;
                            }
                            Thread.sleep(500);
                            break;
                        // LISTAS CANDIDATOS
                        case "4":
                            System.out.println("\n1-Criar lista\n2-Editar lista\n3-Apagar lista");
                            choice2 = input.nextLine();

                            switch (choice2) {
                                case "1":
                                    System.out.println("\nCriar lista");

                                    System.out.println("\nNome: ");
                                    title = input.nextLine();

                                    System.out.println("\nTipo (1-Conselho geral, 2-Nucleos): ");
                                    type = Integer.parseInt(input.nextLine());

                                    System.out.println("\nID eleicao a associar: ");
                                    electionID = Integer.parseInt(input.nextLine());

                                    System.out.println(vote.getElectionType(electionID));

                                    if (vote.getElectionType(electionID) == type) {
                                        if (vote.addLista(title, type, 0, electionID))
                                            System.out.println("\nCriacao efetuada!");
                                        else
                                            System.err.println("\nCriacao falhada");
                                    } else {
                                        System.err.println("\nTipo de eleicao e lista nao coincidem");
                                    }

                                    System.out.println("\nInsira users a associar à lista (0 para parar): ");
                                    ID = Integer.parseInt(input.nextLine());
                                    while (ID != 0) {
                                        if (type != 2) {
                                            if (vote.addUserLista(vote.getListID(title, electionID), ID))
                                                System.out.println("\nUser Inserido");
                                            else
                                                System.out.println("\nUser não existe");
                                        } else if (type == 2) {
                                            if (vote.getUserType(ID) == 1) {
                                                if (vote.addUserLista(vote.getListID(title, electionID), ID))
                                                    System.out.println("\nUser Inserido");
                                                else
                                                    System.out.println("\nUser não existe");
                                            } else {
                                                System.out.println("\nUser não é estudante");
                                            }
                                        }
                                        ID = Integer.parseInt(input.nextLine());
                                    }
                                    Thread.sleep(500);
                                    break;

                                default:
                                    System.out.println("\nOpcao Invalida");
                                    Thread.sleep(500);
                                    break;
                            }
                            break;
                        // MESAS DE VOTO
                        case "5":
                            System.out.println("\n1-Criar mesa\n2-Apagar mesa");
                            choice2 = input.nextLine();

                            switch (choice2) {
                                case "1":
                                    System.out.println("\nFaculdade: ");
                                    facID = Integer.parseInt(input.nextLine());

                                    System.out.println("\nEleicao: ");
                                    electionID = Integer.parseInt(input.nextLine());

                                    if(vote.addBooth(facID,electionID))
                                        System.out.println("\nUser Inserido");
                                    else
                                        System.out.println("\nUser não existe");

                                    Thread.sleep(500);
                                    break;
                                case "2":
                                    System.out.println("\nFaculdade: ");
                                    facID = Integer.parseInt(input.nextLine());

                                    System.out.println("\nEleicao: ");
                                    electionID = Integer.parseInt(input.nextLine());

                                    if(vote.deleteBooth(facID,electionID))
                                        System.out.println("\nCriacao efetuada!");
                                    else
                                        System.out.println("\nCriacao falhada");

                                    Thread.sleep(500);
                                    break;
                                default:
                                    System.out.println("\nOpcao Invalida");
                                    Thread.sleep(500);
                                    break;
                            }

                        default:
                            System.out.println("\nOpcao Invalida");
                            Thread.sleep(500);
                            break;

                    }
                        /*
                        //Menu para gerir lista de candidatos
                        case "4":
                            System.out.println("Manage candidate lists");

                            System.out.print("1-Create new list\n2-Delete a list\n3-Edit a list\nInput: ");

                            choice = input.nextLine();

                            switch (choice) {
                            	//Criar lista de candidatos novos
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

                                        boolean ackCandidate = vote.createList(cl);

                                        if (ackCandidate) {
                                            System.out.println("List successfully created!");
                                        } else {
                                            System.out.println("Problem creating the list...");
                                        }
                                    } 
                                    //Criacao de lista de docentes
                                    else if (listType == 2) {

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
                                        break;

                                    } 
                                    //Criacao de lista de empregados
                                    else {

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
                                //Apagar uma lista de candidatos
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
                                //Editar uma lista de candidatos
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
                                default:
                                    System.out.println("That option doesnt exist");
                                    break;
                            }
                            Thread.sleep(2000);
                            break;

                        //Editar uma eleicao
                        case "5":

                            System.out.println("Edit election");
                            System.out.print("Insert the title of the election to edit: ");
                            String oldElecName = input.nextLine();
                            Election oldElec = vote.getElection(oldElecName);
                            if (!oldElec.getTitle().equals(null)) {


                                //Verifica se esta a decorrer
                                if (oldElec.getStartDate().before(Calendar.getInstance())) {
                                    System.out.println("Election already ongoing (or finished), cannot edit");
                                    break;
                                } else {
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
                                    boolean editElec = vote.editElec(oldElec,oldElecName);

                                    if (editElec) {
                                        System.out.println("Election edited successfully");
                                    } else {
                                        System.out.println("Error editing the election...");
                                    }
                                }

                            } else {
                                System.out.println("Election with that title doesn't exist");
                                break;
                            }
                            Thread.sleep(2000);
                        break;

                        //Adicionar e remover mesas de voto
                        case "6":
                            System.out.println("1 - Add tables,2- Remove tables: ");
                            choice = input.nextLine();
                            switch(choice){
                            	//Adicionar mesas de voto
                                case "1":
                                    System.out.println("Adding voting tables,please choose election(by title):");
                                    String elecTitle = input.nextLine();
                                    System.out.print("Add voting tables (by dep ID) to the election (0 to stop): ");
                                    ArrayList<String> depTables = new ArrayList<String>();
                                    String depId = input.nextLine();
                                    while (!depId.equals("0")) {
                                        depTables.add(depId);
                                        depId = input.nextLine();
                                    }
                                    boolean boothAck = vote.addBooth(elecTitle, depTables);
                                    if (boothAck) {
                                        System.out.println("Booths added successfully");
                                    } else {
                                        System.out.println("Error adding booths.");
                                    }
                                     break;
                                //Remover mesas de voto
                                case "2":

                                    System.out.println("Removing voting tables,please choose election(by title):");
                                    String elecTitle2 = input.nextLine();
                                    System.out.print("Remove voting tables by dep ID (0 to stop): ");
                                    ArrayList<String> depTables2 = new ArrayList<String>();
                                    String depId2 = input.nextLine();
                                    while (!depId2.equals("0")) {
                                        depTables2.add(depId2);
                                        depId2 = input.nextLine();
                                    }
                                    boolean boothDelAck = vote.removeBooth(elecTitle2, depTables2);
                                    if (boothDelAck) {
                                        System.out.println("Tables removed successfully");
                                    } else {
                                        System.out.println("Error removing tables.");
                                    }
                                    break;
                            }
                            Thread.sleep(2000);
                            break;
                        //Verificar historico de votos de um eleitor
                        case "7":
                            System.out.println("Input the id of the user you want to check");
                            String id = input.nextLine();
                            User checkUser = vote.getUser(id);
                            if(checkUser == null){
                                System.out.println("Error getting user with that ID");
                            }
                            else {
                                if (checkUser.getVotes().equals(" ")) {
                                    System.out.println("No votes by this user yet");
                                } else {
                                    System.out.println(checkUser.getVotes());
                                }
                            }
                            Thread.sleep(2000);
                            break;
                        //Verificar estado actual das mesas de voto
                        case "8":
                            System.out.println("These are the departments with tables that are online right now:");
                            ArrayList <Department> activeBooths = new ArrayList<Department>();
                            activeBooths = vote.checkTables();
                            if(activeBooths.size()>0) {
                                for (int i = 0; i < activeBooths.size();i++){
                                    System.out.println("Dep name: "+activeBooths.get(i).getDep()+" Dep ID: "+activeBooths.get(i).getID()+" Area: "+ activeBooths.get(i).getFac());
                                }
                            }
                            else{
                                System.out.println("None");
                            }
                            Thread.sleep(2000);
                            break;
                        default:
                            System.out.println("Invalid choice, going back to menu");
                            Thread.sleep(2000);
                            break;
                            */
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        }catch (Exception e) {
            System.out.println("Exception in main: " + e);
            e.printStackTrace();
        }

    }
}


//Thread para verificar em real time quando uma eleicao expira
class elecCheck extends Thread{
    private VotingAdminInterface vote;
    //private ArrayList <Election> seen = new ArrayList <Election>();
    private ArrayList<Integer> printable = new ArrayList<Integer>();

    private String hostname;
    private int defPort;

    public elecCheck(VotingAdminInterface vote, String hostname, int defPort){
        this.vote = vote;
        this.hostname = hostname;
        this.defPort = defPort;
    }

    public void run() {
        System.out.println("ELECTION THREAD: Checking for expired elections");
        int failed=0;
        /*while (true) {
            try {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                //Recebe lista de eleicoes que ja acabaram
                ArrayList<Election> expired = vote.checkElecDate();
                boolean checked=false;
                Election toAdd = null;
                //Verifica se ja viu que expirou
                for (int i = 0; i < expired.size(); i++) {
                    for (int j = 0; j < seen.size(); j++) {
                        if (expired.get(i).getTitle().equals(seen.get(j).getTitle())) {
                            checked = true;
                        }
                    }
                    //Se nao, acrescenta a a lista
                    if(checked==false){
                        toAdd = expired.get(i);
                        printable.add(0);
                        seen.add(toAdd);
                    }
                    checked = false;
                }

                //Imprime a informacao da eleicao nova
                for(int i=0;i <seen.size();i++){
                    if(printable.get(i)==0){
                        printable.set(i,1);
                        System.out.println("Election expired - " + toAdd.getTitle());
                    }
                }

                failed = 0;

            } catch (RemoteException e) {
            	//Caso falhe a coneccao ao RMI, tenta ligar outra vez, se falhar durante 30 segundos tenta ligar ao secundario
                failed++;
                if(failed == 6){
                    System.out.println("Timeout - RMI didn't respond for 30 seconds, trying to reconnect...");
                    try{
                        this.vote = (VotingAdminInterface) LocateRegistry.getRegistry(this.hostname,this.defPort).lookup("vote_booth");
                    } catch (Exception e2){
                        System.out.println("RMI server not responding, shutting down thread");
                        break;
                    }
                }
            }
        }
        */
    }
}

//Thread para controlar mesas de voto removidas/adicionadas
class boothCheck extends Thread {
    private VotingAdminInterface vote;

    //private ArrayList <Department> seen2 = new ArrayList <Department>();
    //private ArrayList<Integer> printable2 = new ArrayList<Integer>();

    boothCheck(){

    }

    public boothCheck(VotingAdminInterface vote) {
        this.vote = vote;
    }

    public void run() {
        System.out.println("VOTING TABLE THREAD: Checking for updates on voting tables");
        int failed = 0;
        /*
        while(true){
            try {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                ArrayList<Department> tables = vote.checkTables();
                boolean checked=false;
                Department toAdd = null;
                //Check for new tables
                for (int i = 0; i < tables.size(); i++) {
                    for (int j = 0; j < seen2.size(); j++) {
                        if (tables.get(i).getID().equals(seen2.get(j).getID())) {
                            checked = true;
                        }
                    }
                    if(!checked){
                        toAdd = tables.get(i);
                        printable2.add(0);
                        seen2.add(toAdd);
                    }
                    checked = false;
                }

                for(int i=0;i<seen2.size();i++){
                    if(printable2.get(i)==0){
                        printable2.set(i,1);
                        System.out.println("Voting table in " + seen2.get(i).getDep());
                    }
                }

                //Check for deleted tables
                checked = false;
                for(int i=0;i<seen2.size();i++){
                    for(int j=0;j<tables.size();j++){
                        if(seen2.get(i).getID().equals(tables.get(j).getID())) {
                            checked = true;
                        }
                    }
                    if(!checked){
                        System.out.println("Voting table removed in "+seen2.get(i).getDep());
                        seen2.remove(i);
                        printable2.remove(i);
                    }
                    checked = false;
                }
                failed = 0;
            }catch(RemoteException e){
                failed++;
                if(failed == 6){
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
        */
    }
}