import java.io.IOException;
import java.net.*;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.*;
import java.util.*;
import java.sql.*;

public class RMIServer extends UnicastRemoteObject implements RMIServerInterface {
    private static final long serialVersionUID = 1L;

    private static Connection connection = null;

    public RMIServer() throws RemoteException {
        super();
    }

    // =================================================================================================================

    // Verfica o departamento
    public boolean checkFaculdade(int facID) throws RemoteException{
        try {
            ResultSet rs = queryDB("SELECT faculdade_facid FROM mesa_de_voto WHERE faculdade_facid = "+facID+";");
            if(rs.next())
                return true;
        } catch (SQLException e) { }
        return false;
    }

    // Adiciona User na BD
    public boolean registerUser(int numberID, String name, String password, String phone, String address, String expDate, int profession) throws RemoteException {
        if(updateDB("CALL add_user("+numberID+",'"+name+"','"+password+"','"+phone+"','"+address+"','"+expDate+"',"+profession+");"))
            return true;
        return false;
    }

    // Associa Faculdade a User
    public boolean addUserFac(int numberID, int faculdadeID) throws RemoteException{
        if(updateDB("CALL add_user_faculdade("+numberID+","+faculdadeID+");"))
            return true;
        return false;
    }

    // Edita dados User
    public void editUser(int numberID, String name, String password, String phone, String expDate, String address) throws RemoteException {
        if(!name.equals(""))
            updateDB("CALL edit_name("+numberID+",'"+name+"');");
        if(!password.equals(""))
            updateDB("CALL edit_password("+numberID+",'"+password+"');");
        if(!phone.equals(""))
            updateDB("CALL edit_phone("+numberID+",'"+phone+"');");
        if(!expDate.equals(""))
            updateDB("CALL edit_expdate("+numberID+",'"+expDate+"');");
        if(!address.equals(""))
            updateDB("CALL edit_address("+numberID+",'"+address+"');");
    }

    // Apaga User e todos os seus dados
    public boolean deleteUser(int numberID) throws RemoteException{
        if(updateDB("CALL delete_user("+numberID+");"))
            return true;
        return false;
    }

    // Adiciona Faculdade
    public boolean registerFac(String facName) throws RemoteException {
        if(updateDB("CALL add_faculdade('"+facName+"');"))
            return true;
        return false;
    }

    // Adiciona Departamento
    public boolean registerDep(String depName, int facID) throws RemoteException{
        if(updateDB("CALL add_departamento('"+depName+"',"+facID+");"))
            return true;
        return false;
    }

    // Adiciona Unidade Orgânica
    public boolean registerUnit(int facID) throws RemoteException {
        if(updateDB("CALL add_unit("+facID+");"))
            return true;
        return false;
    }

    // Edita nome de uma Faculdade
    public boolean editFac(int facID, String facName) throws RemoteException{
        if(updateDB("CALL edit_faculdade("+facID+",'"+facName+"');"))
            return true;
        return false;
    }

    // Edita nome de um Departamento
    public boolean editDep(int depID, String depName) throws RemoteException{
        if(updateDB("CALL edit_departamento("+depID+",'"+depName+"');"))
            return true;
        return false;
    }

    // Elimina Faculdade
    public boolean deleteFac(int facID) throws RemoteException {
        if(updateDB("CALL delete_faculdade("+facID+");"))
            return true;
        return false;
    }

    // Elimina Departamento
    public boolean deleteDep(int depID) throws RemoteException {
        if(updateDB("CALL delete_departamento("+depID+");"))
            return true;
        return false;
    }

    // Elimina Unidade Organica
    public boolean deleteUnit(int facID) throws RemoteException {
        if(updateDB("CALL delete_unit("+facID+");"))
            return true;
        return false;
    }

    // Adiciona Eleicao(Conselho Geral: adiciona uma mesa de voto em cada faculdade, Nucleos: adiciona uma mesa de voto na faculdade em questão)
    public boolean addEl(int eleicaoID, String title, String description, int type, String startDate, String endDate,int faculdadeID) throws RemoteException{
        if(updateDB("CALL add_eleicao("+eleicaoID+",'"+title+"','"+description+"',"+type+",'"+startDate+"','"+endDate+"',"+faculdadeID+");"))
            return true;
        return false;
    }

    // Edita titulo e descricao de uma Eleicao por ID
    public boolean editELText(int eleicaoID, String title, String description) throws RemoteException{
        if(updateDB("CALL edit_eleicao_text("+eleicaoID+",'"+title+"','"+description+"');"))
            return true;
        return false;
    }

    // Edita datas de uma Eleicao por ID
    public boolean editElDate(int eleicaoID, String startdate, String enddate) throws RemoteException{
        if(updateDB("CALL edit_eleicao_date("+eleicaoID+",'"+startdate+"','"+enddate+"');"))
            return true;
        return false;
    }

    // Apaga Eleicao por ID
    public boolean deleteEL(int eleicaoID) throws RemoteException{
        if(updateDB("CALL delete_eleicao("+eleicaoID+");"))
            return true;
        return false;
    }

    // Adiciona uma Lista Candidata a uma determinada eleicao
    public boolean addLista(String name, int type, int numvotes, int eleicaoID) throws RemoteException{
        if(updateDB("CALL add_lista_candidata('"+name+"',"+type+","+numvotes+","+eleicaoID+");"))
            return true;
        return false;
    }

    // Associa User a uma lista
    public boolean addUserLista(int listid, int userid) throws RemoteException{
        if(updateDB("CALL add_lista_candidata_user("+listid+","+userid+");"))
            return true;
        return false;

    }

    // Elimina uma Lista Candidata por ID
    public boolean deleteLista(int listid) throws RemoteException{
        if(updateDB("CALL delete_lista_candidata("+listid+");"))
            return true;
        return false;
    }

    // Adiciona Mesa de Voto
    public boolean addBooth(int facid, int electionid) throws RemoteException{
        if(updateDB("CALL add_mesa_de_voto("+facid+","+electionid+");"))
            return true;
        return false;
    }

    // Apaga Mesa de Voto
    public boolean deleteBooth(int facid, int electionid) throws RemoteException{
        if(updateDB("CALL delete_mesa_de_voto("+facid+","+electionid+");"))
            return true;
        return false;
    }

    // Acresenta User a uma Mesa de Voto
    public boolean addUserBooth(int userID, int facID , int electionID) throws RemoteException{
        try{
            ResultSet rs = queryDB("SELECT COUNT(*) FROM mesa_de_voto_user WHERE mesa_de_voto_faculdade_facid = "+facID+" AND mesa_de_voto_eleicao_electionid = "+electionID+";");
            if(rs.next()){
                if(rs.getInt("COUNT(*)")<3) {
                    if (updateDB("call add_mesa_de_voto_user(" + userID + "," + facID + "," + electionID + ");"))
                        return true;
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    // Remove User de uma Mesa de Voto
    public boolean removeUserBooth(int userID, int facID , int electionID) throws RemoteException{
        if(updateDB("call delete_mesa_de_voto_user("+userID+","+facID+","+electionID+");"))
            return true;
        return false;
    }

    // Retorna tipo de Eleicao por ID
    public int getElectionType(int id) throws RemoteException{
        try {
            ResultSet rs = queryDB("SELECT type FROM eleicao WHERE electionid = "+id+";");
            if(rs.next())
                return rs.getInt("type");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Retorna tipo de Lista por ID
    public int getListType(int id) throws RemoteException{
        try {
            ResultSet rs = queryDB("SELECT type FROM lista_candidata WHERE electionid = "+id+";");
            if(rs.next())
                return rs.getInt("type");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Retorna tipo de User por ID
    public int getUserType(int id) throws RemoteException{
        ResultSet rs;
        try {
            rs = queryDB("SELECT * FROM estudante WHERE user_numberid = "+id+";");
            if(rs.next())
                return 1;
            rs = queryDB("SELECT * FROM professor WHERE user_numberid = "+id+";");
            if(rs.next())
                return 2;
            rs = queryDB("SELECT * FROM funcionario WHERE user_numberid = "+id+";");
            if(rs.next())
                return 3;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Retorna ID da lista por nome
    public int getListID(String name, int electionID) throws RemoteException{
        try {
            ResultSet rs = queryDB("SELECT listid FROM lista_candidata WHERE name = '"+name+"' AND eleicao_electionid = "+electionID+";");
            if(rs.next())
                return rs.getInt("listid");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Verifica se eleicao ainda nao acabou
    public boolean elAntecipated(int electionID) throws RemoteException{
        try{
            ResultSet rs = queryDB("SELECT electionid FROM eleicao WHERE (enddate > CURRENT_TIMESTAMP) AND (startdate > CURRENT_TIMESTAMP) AND electionid = "+electionID+";");
            if(rs.next()){
                return true;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    // Verifica se eleicao ainda nao acabou
    public boolean elTerminated(int electionID) throws RemoteException{
        try{
            ResultSet rs = queryDB("SELECT electionid FROM eleicao WHERE (enddate < CURRENT_TIMESTAMP) AND electionid = "+electionID+";");
            if(rs.next()){
                return true;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    // Voto antecipado
    public boolean antecipatedVote(int userID, int electionID, int listID) throws RemoteException{
        if(updateDB("CALL antecipated_vote("+userID+","+electionID+","+listID+");"))
            return true;
        return false;
    }

    // Retorna id maximo das eleicoes
    public int[] getEls() throws RemoteException{
        int[] aux = new int[100];
        Arrays.fill(aux,0);
        try{
            ResultSet rs = queryDB("SELECT electionid FROM eleicao;");
            int i = 0;
            while (rs.next()){
                aux[i] = rs.getInt("electionid");
                i++;
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return aux;
    }

    // Verifica se a eleicao ja acabou
    public boolean pastEl(int electionID) throws RemoteException{
        try {
            ResultSet rs = queryDB("SELECT electionid FROM eleicao WHERE enddate < CURRENT_TIMESTAMP AND electionid = "+electionID+";");
            if(rs.next())
                return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Retorna numero de votos numa lista
    public int getVotes(int listID) throws RemoteException{
        try {
            ResultSet rs = queryDB("SELECT numvotes FROM lista_candidata WHERE listid = "+listID+";");
            if(rs.next())
                return rs.getInt("numvotes");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Retorna numero total de votos de uma eleicao
    public int getTotalVotes(int electionID) throws RemoteException{
        try {
            ResultSet rs = queryDB("SELECT SUM(numvotes) FROM lista_candidata WHERE eleicao_electionid = "+electionID+";");
            if(rs.next())
                return rs.getInt("SUM(numvotes)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;

    }

    // Retorna ID da faculdade onde utilizador votou
    public String getFaculdadeVoted(int userID, int electionID) throws RemoteException{
        try {
            ResultSet rs = queryDB("SELECT faculdade_facid FROM eleicao_user WHERE eleicao_electionid = "+electionID+" AND user_numberid = "+userID+";");
            if(rs.next()) {
                int aux = rs.getInt("faculdade_facid");
                return getFacName(aux);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    // Retorna nome de faculade por ID
    public String getFacName(int facID){
        try {
            ResultSet rs = queryDB("SELECT name FROM faculdade WHERE facid = "+facID+";");
            if(rs.next())
                return rs.getString("name");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    // Log in User
    public boolean authenticateUser(int id, String name, String password) throws RemoteException{
        try{
            ResultSet rs = queryDB("SELECT password FROM user WHERE name = '"+name+"' AND numberid = "+id+";");
            if(rs.next()){
                if(rs.getString("password").equals(password))
                    return true;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    // Retorna faculdades a que user pertence
    public boolean identifyID(int userID, int facID) throws RemoteException{
        try{
            ResultSet rs = queryDB("SELECT user_numberid,faculdade_facid FROM user_faculdade WHERE user_numberid = "+userID+" AND faculdade_facid = "+facID+";");
            if(rs.next()){
                return true;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    // Retorna eleicoes elegiveis para determinada faculdade
    public int[] getMesaDeVotoEls(int facid) throws RemoteException{
        int[] aux = new int[100];
        Arrays.fill(aux,0);
        try{
            ResultSet rs = queryDB("SELECT eleicao_electionid FROM mesa_de_voto WHERE faculdade_facid = "+facid+";");
            int i = 0;
            while (rs.next()){
                aux[i] = rs.getInt("eleicao_electionid");
                i++;
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return aux;
    }

    // Verifica se a eleicao está ativa
    public boolean isElActive (int electionID) throws RemoteException {
        try{
            ResultSet rs = queryDB("SELECT electionid FROM eleicao WHERE (startdate < CURRENT_TIMESTAMP) AND (enddate > CURRENT_TIMESTAMP) AND electionid = "+electionID+";");
            if(rs.next()){
                return true;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    // Regista Voto
    public boolean voteElection(int userID, int electionID, int listID, int facID) throws RemoteException {
        if(updateDB("CALL vote("+userID+","+electionID+","+listID+","+facID+");"))
            return true;
        return false;
    }

    // Verifica se user já votou em eleição
    public boolean hasVoted(int userID, int electionID) throws RemoteException {
        try {
            ResultSet rs = queryDB("SELECT * FROM eleicao_user WHERE eleicao_electionid = "+electionID+" AND user_numberid = "+userID+";");
            if(rs.next())
                return true;
        } catch (SQLException e) { }
        return false;
    }

    // Verifica se user já pode votar em determinada eleicao
    public boolean userCanVote (int userID, int electionID) throws RemoteException{
        int user = -1, eleicaoType = -1;
        try {
            ResultSet rs = queryDB("SELECT type FROM eleicao WHERE electionid = "+electionID+";");
            if(rs.next()) {
                eleicaoType = (rs.getInt("type"));
                if (eleicaoType == 2){
                    rs = queryDB("SELECT user_numberid FROM estudante WHERE user_numberid= "+userID+";");
                    if(rs.next()) return true;
                }
                else return true;
            }
        } catch (SQLException e) { }
        return false;
    }

    // Retorna nome eleicao por ID
    public String getElName(int id) throws RemoteException{
        try{
            ResultSet rs = queryDB("SELECT title FROM eleicao WHERE electionid = "+id+";");
            if(rs.next()){
                return rs.getString("title");
            }
        }catch (SQLException e){ }
        return "";
    }

    // Retorna listas elegiveis para determinada eleicao
    public int[] getElectionLists(int electionid) throws RemoteException{
        int[] aux = new int[100];
        Arrays.fill(aux,-1);
        try{
            ResultSet rs = queryDB("SELECT listid FROM lista_candidata WHERE eleicao_electionid = "+electionid+";");
            int i = 0;
            while (rs.next()){
                aux[i] = rs.getInt("listid");
                i++;
            }
        } catch (SQLException e){ }
        return aux;
    }

    // Retorna nome lista por ID
    public String getListName(int id) throws RemoteException{
        try{
            ResultSet rs = queryDB("SELECT name FROM lista_candidata WHERE listid = "+id+";");
            if(rs.next()){
                return rs.getString("name");
            }
        }catch (SQLException e){ }
        return "";
    }

    // =================================================================================================================
    // Main
    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        RMIFailover UDPConn;

        String hostname;
        int rmiPort, defPort;

        System.out.println("RMI Hostname: ");
        hostname = sc.nextLine();

        System.out.println("RMI Port");
        rmiPort = sc.nextInt();

        System.out.println("UPDSocket Port");
        defPort = sc.nextInt();

        // Inicia thread que lida com a conexão UDP
        UDPConn = new RMIFailover(hostname, defPort, rmiPort);
        UDPConn.start();
    }

    // Seleciona se vai ser Main ou Backup RMI
    public void startRMI(int rmiPort) {
        if (connectDB()) {
            try {
                // Criação RMI
                RMIServer server = new RMIServer();
                Registry reg = LocateRegistry.createRegistry(rmiPort);
                reg.rebind("vote_booth", server);
                System.out.println("RMI Server connected");
            } catch(RemoteException e){
                System.out.println("Could not bind RMI registry");
            }
        }
    }

    // Cria conneção à base de dados
    public static boolean connectDB(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC Driver not found");
            return false;
        }
        connection = null;
        try{
            connection = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/sdProjectDatabase?autoReconnect=true&useSSL=false",
                    "bd_user",
                    "password");
        } catch (SQLException e) {
            System.err.println("Database connection failed");
            return false;
        }
        System.out.println("Connected to database");
        return true;
    }

    // Executa update à base de dados
    public static boolean updateDB(String str){
        try {
            Statement stmt;
            if (connection.createStatement() == null) {
                connection = DriverManager.getConnection(
                        "jdbc:mysql://127.0.0.1:3306/sdProjectDatabase",
                        "bd_user",
                        "password");
            }
            if ((stmt = connection.createStatement()) == null) {
                System.err.println("Error creating statement");
            }
            stmt.executeUpdate(str);
        }catch (SQLException e){
            System.err.println("Error executing "+str);
            e.printStackTrace();
            return false;
        }
        System.out.println("Success executing "+str);
        return true;
    }

    // Executa query à base de dados
    public static ResultSet queryDB(String str){
        try {
            Statement stmt;
            if (connection.createStatement() == null) {
                connection = DriverManager.getConnection(
                        "jdbc:mysql://127.0.0.1:3306/sdProjectDatabase",
                        "bd_user",
                        "password");
            }
            if ((stmt = connection.createStatement()) == null) {
                System.err.println("Error creating statement");
            }
            return stmt.executeQuery(str);
        }catch (SQLException e){
            System.err.println("Error executing "+str);
        }
        System.out.println("Success executing "+str);
        return null;
    }
}

// Thread que trata do Failover
class RMIFailover extends Thread {
    private DatagramSocket aSocket = null;

    private String hostname;
    private int serverPort, rmiPort;

    public RMIFailover(String hostname, int serverPort, int rmiPort) {
        this.hostname = hostname;
        this.serverPort = serverPort;
        this.rmiPort = rmiPort;
    }

    public void run() {
        RMIFailover UDPConn;
        try {
            // Abre socket UDP
            this.aSocket = new DatagramSocket(this.serverPort);

            RMIServer RMIServer = new RMIServer();
            RMIServer.startRMI(this.rmiPort);

            while (true) {
                String texto = "";
                byte[] m = texto.getBytes();

                // Cria e envia pacote UDP
                InetAddress aHost = InetAddress.getByName(this.hostname);
                DatagramPacket request = new DatagramPacket(m, m.length, aHost, this.serverPort);
                aSocket.send(request);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException i) {
                }
            }

        } catch (SocketException e) {
            System.out.println("Backup RMI Server started");
            int heartbeatsFailed = 0, maxHeartbeats = 5;
            byte[] buffer = new byte[1000];

            try {
                // Abre receiver socket UDP
                this.aSocket = new DatagramSocket(null);
                this.aSocket.setReuseAddress(true);
                this.aSocket.bind(new InetSocketAddress(this.hostname, this.serverPort));
                while (heartbeatsFailed < maxHeartbeats) {
                    // Define timeout de recepção de heartbeat
                    this.aSocket.setSoTimeout(1500);

                    InetAddress aHost = InetAddress.getByName(this.hostname);

                    // Cria e recebe pacote UDP
                    DatagramPacket request = new DatagramPacket(buffer, buffer.length, aHost, this.serverPort);
                    try {
                        aSocket.receive(request);
                        System.out.println("Received heartbeat from Main RMI server");
                    } catch (SocketTimeoutException i){
                        heartbeatsFailed++;
                        System.err.println("Did not receive heartbeat from Main RMI server\tRetrying... "+heartbeatsFailed+"/"+maxHeartbeats);
                    }
                }

                if(heartbeatsFailed >= maxHeartbeats) {
                    this.aSocket.close();
                    UDPConn = new RMIFailover(this.hostname, this.serverPort, this.rmiPort);
                    UDPConn.start();
                    try {
                        Thread.currentThread().join();
                    } catch (InterruptedException i) {
                    }
                }

            } catch (SocketException i) { System.out.println("Socket: " + e.getMessage());
            } catch (IOException i) { System.out.println("IO: " + e.getMessage());
            } finally { if (this.aSocket != null) this.aSocket.close(); }

        } catch (IOException e) { System.out.println("IO: " + e.getMessage());
        } finally { if (this.aSocket != null) this.aSocket.close(); }
    }
}