package adminPackage;

import java.awt.*;
import java.lang.reflect.Array;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


import RMIPackage.*;
import com.sun.org.apache.regexp.internal.RE;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

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

            int electionID, type, ID, profession, faculdadeID, facID, depID, listID;
            String title, description, startDate, endDate, facName, depName, name, myDate, phone, password, address;

            //Menu
            while(true) {
                try {
                    System.out.println("\nConsola ADMIN. O que quer fazer\n1-Utlizador\n"
                            + "2-Faculdade e Departamentos\n3-Eleicoes\n4-Listas de candidatos"
                            + "\n5-Mesas de Voto\n6-Voto Antecipado\n7-Resultados eleicoes passadas"
                            + "\n8-Local de voto de cada utilizador");
                    choice = input.nextLine();

                    switch (choice) {
                        // USER
                        case "1":
                            System.out.println("1-Registar User\n2-Editar User\n3-Apagar User");
                            choice2 = input.nextLine();
                            switch (choice2) {
                                // registar
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

                                    System.out.print("\nMorada: ");
                                    address = input.nextLine();

                                    System.out.print("\nPassword: ");
                                    password = input.nextLine();

                                    System.out.print("\nTipo de User (1-Estudante, 2-Professor, 3- Funcionario): ");
                                    profession = Integer.parseInt(input.nextLine());

                                    if (vote.registerUser(ID, name, password, phone, address, myDate, profession) && (profession == 1 || profession == 2 || profession == 3))
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
                                // editar
                                case "2":
                                    System.out.println("Editar User");

                                    System.out.print("\nID: ");
                                    ID = Integer.parseInt(input.nextLine());

                                    System.out.println("\n\t\t*String vazia para nao alterar");

                                    System.out.print("\nNovo nome: ");
                                    name = input.nextLine();

                                    System.out.print("\nNova data de validade(yyyy-MM-dd hh:mm:ss):");
                                    myDate = input.nextLine();

                                    System.out.print("\nNovo telemovel: ");
                                    phone = input.nextLine();

                                    System.out.print("\nMorada: ");
                                    address = input.nextLine();

                                    System.out.print("\nNova password: ");
                                    password = input.nextLine();

                                    vote.editUser(ID, name, password, phone, myDate, address);
                                    System.out.println("\nEdicao efetuada!");
                                    break;
                                // apagar
                                case "3":
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
                                // nova fac
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
                                // novo dep
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
                                // nova unidade
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
                                // editar fac
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
                                // editar dep
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
                                // apagar fac
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
                                // apagar dep
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
                                // apagar unidade
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
                                //
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
                                // criar
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
                                // editar texto
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
                                // editar datas
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
                                // apagar
                                case "4":
                                    System.out.println("\nID: ");
                                    electionID = Integer.parseInt(input.nextLine());

                                    if (vote.deleteEL(electionID))
                                        System.out.println("\nEliminacao efetuada!");
                                    else
                                        System.err.println("\nEliminacao falhada");
                                    Thread.sleep(500);
                                    break;
                                //
                                default:
                                    System.out.println("\nOpcao Invalida");
                                    Thread.sleep(500);
                                    break;
                            }
                            Thread.sleep(500);
                            break;
                        // LISTAS CANDIDATOS
                        case "4":
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
                        // MESAS DE VOTO
                        case "5":
                            System.out.println("\n1-Criar mesa\n2-Apagar mesa\n3-Adicionar User\n4-Remover User");
                            choice2 = input.nextLine();

                            switch (choice2) {
                                // criar
                                case "1":
                                    System.out.println("\nID Faculdade: ");
                                    facID = Integer.parseInt(input.nextLine());

                                    System.out.println("\nID Eleicao: ");
                                    electionID = Integer.parseInt(input.nextLine());

                                    if(vote.addBooth(facID,electionID))
                                        System.out.println("\nCriacao efetuada!");
                                    else
                                        System.out.println("\nCriacao falhada");
                                    break;
                                // apagar
                                case "2":
                                    System.out.println("\nID Faculdade: ");
                                    facID = Integer.parseInt(input.nextLine());

                                    System.out.println("\nID Eleicao: ");
                                    electionID = Integer.parseInt(input.nextLine());

                                    if(vote.deleteBooth(facID,electionID))
                                        System.out.println("\nEliminacao efetuada!");
                                    else
                                        System.out.println("\nEliminacao falhada");
                                    break;
                                case "3":
                                    System.out.println("\nUser ID: ");
                                    ID = Integer.parseInt(input.nextLine());

                                    System.out.println("\nID Faculdade: ");
                                    facID = Integer.parseInt(input.nextLine());

                                    System.out.println("\nID Eleicao: ");
                                    electionID = Integer.parseInt(input.nextLine());

                                    if(vote.addUserBooth(ID,facID,electionID))
                                        System.out.println("\nAdicionou User");
                                    else
                                        System.out.println("\nJá existem 3 users associados a esta Mesa de Voto");
                                    break;
                                case "4":
                                    System.out.println("\nUser ID: ");
                                    ID = Integer.parseInt(input.nextLine());

                                    System.out.println("\nID Faculdade: ");
                                    facID = Integer.parseInt(input.nextLine());

                                    System.out.println("\nID Eleicao: ");
                                    electionID = Integer.parseInt(input.nextLine());

                                    if(vote.removeUserBooth(ID,facID,electionID))
                                        System.out.println("\nEliminacao efetuada!");
                                    else
                                        System.out.println("\nEliminacao falhada");
                                    break;
                                //
                                default:
                                    System.out.println("\nOpcao Invalida");
                                    break;
                            }
                            Thread.sleep(500);
                            break;
                        // VOTO ANTECIPADO
                        case "6":
                            System.out.println("Voto Antecipado");

                            System.out.println("\nID: ");
                            ID = Integer.parseInt(input.nextLine());

                            int[] eleicoes = vote.getEls();
                            System.out.println("\nEscolha eleicao: ");
                            for (int i = 0; i < eleicoes.length; i++) {
                                if (eleicoes[i] != -1 && !vote.getElName(eleicoes[i]).equals("") && !vote.hasVoted(ID,eleicoes[i]) && vote.userCanVote(ID,eleicoes[i]) && vote.elAntecipated(eleicoes[i]))
                                    System.out.println(eleicoes[i] + ". " + vote.getElName(eleicoes[i]));
                            }
                            electionID = Integer.parseInt(input.nextLine());

                            int[] listas = vote.getElectionLists(electionID);
                            System.out.println("\nEscolha lista (ID nao imprimido e considerado voto nulo): ");
                            for (int i = 0; i < listas.length; i++) {
                                if (!vote.getListName(listas[i]).equals("") && !vote.getListName(listas[i]).equals("NULLVOTE")) {
                                    if (listas[i] != -1 && !vote.getListName(listas[i]).equals("BLANKVOTE"))
                                        System.out.println(listas[i] + ". " + vote.getListName(listas[i]));
                                    else
                                        System.out.println(listas[i] + ". ");
                                }
                            }
                            listID = Integer.parseInt(input.nextLine());

                            boolean contains = false;
                            for (int i = 0;i<listas.length;i++)
                                if(listas[i] == listID) contains = true;

                            if (contains) {
                                if (vote.antecipatedVote(ID, electionID, listID))
                                    System.out.println("\nVoto registado!");
                                else
                                    System.out.println("\nErro");
                            }
                            else {
                                for (int i = 0 ;i<listas.length;i++){
                                    if(vote.getListName(listas[i]).equals("NULLVOTE"))
                                        listID = listas[i];
                                }
                                if (vote.antecipatedVote(ID, electionID, listID))
                                    System.out.println("\nVoto registado!");
                                else
                                    System.out.println("\nVoto registado!");
                            }
                            Thread.sleep(500);
                            break;
                        // CONSULTAR ELEICOES
                        case "7":
                            eleicoes = vote.getEls();
                            System.out.println("Consultar resultados eleicoes passadas");
                            for (int i = 0; i < eleicoes.length; i++) {
                                if (eleicoes[i] != -1 && !vote.getElName(eleicoes[i]).equals("") && vote.elTerminated(eleicoes[i])) {
                                    System.out.println("Eleicao: " + vote.getElName(eleicoes[i]));
                                    listas = vote.getElectionLists(eleicoes[i]);
                                    int percent = vote.getTotalVotes(eleicoes[i]);
                                    System.out.println("\tTotal de votos: "+percent);
                                    for (int j = 0; j < listas.length; j++) {
                                        double aux=0;
                                        if(listas[j] != -1 && !vote.getListName(listas[j]).equals("") && !vote.getListName(listas[j]).equals("BLANKVOTE") && !vote.getListName(listas[j]).equals("NULLVOTE")) {
                                            if(percent!=0) aux = (vote.getVotes(listas[j]) / (double) percent) * 100;
                                            System.out.println(String.format("\t" + vote.getListName(listas[j]) + ": " + vote.getVotes(listas[j]) + " votos; %.2f %%",aux));
                                        }
                                        else if(vote.getListName(listas[j]).equals("BLANKVOTE")) {
                                            if(percent!=0) aux = (vote.getVotes(listas[j])/ (double) percent) * 100;
                                            System.out.println(String.format("\tVotos em branco: " + vote.getVotes(listas[j]) + " votos; %.2f %%",aux));
                                        }
                                    }
                                }
                            }
                            Thread.sleep(500);
                            break;
                        // LOCAL DE VOTO
                        case "8":
                            System.out.println("\nID do user: ");
                            ID = Integer.parseInt(input.nextLine());

                            System.out.println("\nID Eleicao: ");
                            electionID = Integer.parseInt(input.nextLine());

                            String facVote = vote.getFaculdadeVoted(ID,electionID);

                            if(facVote.equals("")) {
                                if(vote.hasVoted(ID,electionID))
                                    System.out.println("\nUser votou atraves da consola de administracao");
                                else
                                    System.out.println("\nUser nao votou para esta eleicao");
                            }
                            else
                                System.out.println("\nUser voutou na faculdade: "+facVote);

                            Thread.sleep(500);
                            break;
                        default:
                            System.out.println("\nOpcao Invalida");
                            Thread.sleep(500);
                            break;

                    }
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