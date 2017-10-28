package ServerPackage;

import RMIPackage.*;
import com.sun.org.apache.regexp.internal.RE;

import java.rmi.*;
import java.util.*;

public interface TCPServerInterface extends Remote {
    /**
     * Retorna lista de user
     * @return ArrayList de users
     * @throws RemoteException
     */
    ArrayList<User> getUsers() throws RemoteException;

    /**
     * Retorna lista de candidatos
     * @return ArrayList de lista de candidatos
     * @throws RemoteException
     */
    ArrayList<candidateList> getCandidateList() throws RemoteException;

    /**
     * Retorna lista de departamentos
     * @return ArrayList de departamentos
     * @throws RemoteException
     */
    ArrayList<Department> getDepList() throws RemoteException;

    /**
     * Retorna lista de eleicoes
     * @return ArrayList de eleicoes
     * @throws RemoteException
     */
    ArrayList<Election> getElList() throws RemoteException;

    /**
     * Retorna eleicao por titulo
     * @param title Titulo da eleicao
     * @return Eleicao
     * @throws RemoteException
     */
    Election getElection(String title) throws RemoteException;

    /**
     * Efetua acto de votar
     * @param u User
     * @param e Eleicao
     * @param cl Lista de candidatos
     * @throws RemoteException
     */
    void voteElection(User u, Election e, candidateList cl) throws RemoteException;

    /**
     * Verifica se user ja votou em determinada eleicao
     * @param u User
     * @param e Eleicao
     * @return boolean consoante o sucesso da operacao
     * @throws RemoteException
     */
    boolean hasVoted(User u, Election e) throws RemoteException;
}
