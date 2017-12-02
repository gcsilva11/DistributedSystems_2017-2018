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
	boolean registerUser(int numberID, String name, String password, String phone, String date, int profession) throws java.rmi.RemoteException;

	/**
	 * Regista um novo departamento
	 * @param dep Departamento
	 * @return boolean consoante o sucesso da operacao
	 * @throws java.rmi.RemoteException
	 */
	boolean registerFac(String facName, String depName) throws java.rmi.RemoteException;

	/**
	 * Edita um departamento ja existente
	 * @param dep Departamento
	 * @return boolean consoante o sucesso da operacao
	 * @throws java.rmi.RemoteException
	 */
	//boolean editDep(Department dep) throws java.rmi.RemoteException;

	/**
	 * Apaga um departamento ja existente
	 * @param dep Departamento
	 * @return boolean consoante o sucesso da operacao
	 * @throws java.rmi.RemoteException
	 */
	//boolean deleteDep(Department dep) throws java.rmi.RemoteException;

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