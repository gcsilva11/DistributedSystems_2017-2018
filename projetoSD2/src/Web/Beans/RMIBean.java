package Web.Beans;

import RMI.RMIServerInterface;
import java.io.*;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class RMIBean implements Serializable {
    protected RMIServerInterface server = null;
    private String hostname = "localhost";
    private int rmiPort = 6500;

    public RMIBean() {
        try {
            server = (RMIServerInterface) Naming.lookup("rmi://" + hostname + ":" + rmiPort + "/" + "vote_booth");
        } catch(MalformedURLException |NotBoundException |RemoteException e) {
            e.printStackTrace();
        }
    }
}
