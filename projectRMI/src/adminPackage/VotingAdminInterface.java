package adminPackage;

import RMIPackage.*;

import java.rmi.*;
import java.util.*;

public interface VotingAdminInterface extends Remote {
	/**
	 * Regista um novo user
	 * @param user User
	 * @return boolean consoante o sucesso da operacao
	 * @throws java.rmi.RemoteException
	 */
	boolean registerUser(int numberID, String name, String password, String phone, String date, int profession) throws RemoteException;
	boolean addUserFac(int numberID, int faculdadeID) throws RemoteException;
	boolean addUserLista(int listid, int userid) throws RemoteException;

	public boolean deleteUser(int numberID) throws RemoteException;

	/**
	 * Regista um novo departamento
	 * @param dep Departamento
	 * @return boolean consoante o sucesso da operacao
	 * @throws java.rmi.RemoteException
	 */
	public boolean registerFac(String facName) throws RemoteException;

	public boolean registerDep(String depName, int facID) throws RemoteException;

	public boolean registerUnit(int facID) throws RemoteException;

	/**
	 * Edita um departamento ja existente
	 * @param dep Departamento
	 * @return boolean consoante o sucesso da operacao
	 * @throws java.rmi.RemoteException
	 */
	public boolean editFac(int facID, String facName) throws RemoteException;

	boolean editDep(int depID, String depName) throws RemoteException;

	/**
	 * Apaga um departamento ja existente
	 * @param dep Departamento
	 * @return boolean consoante o sucesso da operacao
	 * @throws java.rmi.RemoteException
	 */
	public boolean deleteFac(int facID) throws RemoteException;
	public boolean deleteDep(int depID) throws RemoteException;
	public boolean deleteUnit(int facID) throws RemoteException;

	boolean addEl(int eleicaoID, String title, String description, int type, String startDate, String endDate, int faculdadeID) throws RemoteException;
	boolean deleteEL(int eleicaoID) throws RemoteException;
	boolean editELText(int eleicaoID, String title, String description) throws RemoteException;
	boolean editElDate(int eleicaoID, String startdate, String enddate) throws RemoteException;

	boolean addLista(String name, int type, int numvotes, int eleicaoID) throws RemoteException;
	boolean deleteLista(int listid) throws RemoteException;

	boolean addBooth(int facid, int electionid) throws RemoteException;
	boolean deleteBooth(int facid, int electionid) throws RemoteException;

	int getElectionType(int id) throws RemoteException;
	int getListType(int id) throws RemoteException;
	int getListID(String name, int electionID) throws RemoteException;
	int getUserType(int id) throws RemoteException;
	/**
	 * Retorna todas as listas de um tipo
	 * @param type Tipo de lista
	 * @return ArrayList de listas candidatas
	 * @throws java.rmi.RemoteException
	 */
	//ArrayList <candidateList> getList(int type) throws java.rmi.RemoteException;

	/**
	 * Regista uma nova eleicao
	 * @param election Eleicao
	 * @return boolean consoante o sucesso da operacao
	 * @throws java.rmi.RemoteException
	 */
	//boolean newElection(Election election) throws java.rmi.RemoteException;

	/**
	 * Regista uma nova mesa de voto
	 * @param elTitle Titulo da eleicao
	 * @param depId Id do departamento
	 * @return boolean consoante o sucesso da operacao
	 * @throws java.rmi.RemoteException
	 */
	//boolean addBooth(String elTitle,ArrayList <String> depId)throws java.rmi.RemoteException;

	/**
	 * Remove uma mesa de voto
	 * @param elTitle Titulo da eleicao
	 * @param depId Id do departamento
	 * @return boolean consoante o sucesso da operacao
	 * @throws java.rmi.RemoteException
	 */
	//boolean removeBooth(String elTitle,ArrayList <String> depId)throws java.rmi.RemoteException;

	/**
	 * Encontra um user pelo ID e profissao
	 * @param s User ID
	 * @param type Profissao
	 * @return User
	 * @throws java.rmi.RemoteException
	 */
	//User findId(String s,int type) throws java.rmi.RemoteException;

	/**
	 * Regista uma lista de candidatos
	 * @param cl Lista de candidatos
	 * @return boolean consoante o sucesso da operacao
	 * @throws java.rmi.RemoteException
	 */
	//boolean createList(candidateList cl) throws java.rmi.RemoteException;

	/**
	 * Apaga uma lista de candidatos
	 * @param id ID da lista
	 * @return boolean consoante o sucesso da operacao
	 * @throws java.rmi.RemoteException
	 */
	//boolean deleteList(String id) throws java.rmi.RemoteException;

	/**
	 * Edita uma lista de candidatos
	 * @param id ID da lista
	 * @param title Titulo da lista
	 * @return boolean consoante o sucesso da operacao
	 * @throws java.rmi.RemoteException
	 */
	//boolean editList(String id,String title)throws java.rmi.RemoteException;

	/**
	 * Edita uma eleicao
	 * @param el Eleicao
	 * @param oldElecDate Nome de eleicao a editar
	 * @return boolean consoante o sucesso da operacao
	 * @throws java.rmi.RemoteException
	 */
	//boolean editElec(Election el,String oldElecDate)throws java.rmi.RemoteException;

	/**
	 * Procura uma eleicao por titulo
	 * @param title Titulo da eleicao
	 * @return Eleicao
	 * @throws java.rmi.RemoteException
	 */
	//Election getElection(String title)throws java.rmi.RemoteException;

	/**
	 * Fecha eleicao caso data ja tenha passado
	 * @return ArrayList de Eleicoes
	 * @throws java.rmi.RemoteException
	 */
	//ArrayList <Election> checkElecDate()throws java.rmi.RemoteException;

	/**
	 * Retorna departamentos que tem mesas de voto ativas
	 * @return ArrayList de Departamentos
	 * @throws java.rmi.RemoteException
	 */
	//ArrayList <Department> checkTables()throws java.rmi.RemoteException;

	/**
	 * Retorna user por ID
	 * @param userID User ID
	 * @return User
	 * @throws java.rmi.RemoteException
	 */

	//User getUser(String userID) throws java.rmi.RemoteException;
}