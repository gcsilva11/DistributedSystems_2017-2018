package Web.Actions;

import Web.Beans.DetailElectionBean;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;

import java.rmi.RemoteException;
import java.util.Map;

public class DetailElectionAction extends ActionSupport implements SessionAware {
    private static final long serialVersionUID = 4L;
    private Map<String, Object> session;
    private String eleID = null;

    @Override
    public String execute() throws RemoteException {
        this.getDetailElectionBean().setEleID(this.eleID);
        this.getDetailElectionBean().setElection(this.getDetailElectionBean().getElection());
        return "DETAIL_SUCCESS";
    }

    public DetailElectionBean getDetailElectionBean() {
        if (!session.containsKey("detailElectionBean"))
            this.setDetailElectionBean(new DetailElectionBean());
        return (DetailElectionBean) session.get("detailElectionBean");
    }

    private void setDetailElectionBean(DetailElectionBean detailElectionBean) {
        this.session.put("detailElectionBean", detailElectionBean);
    }

    @Override
    public void setSession(Map<String, Object> map) {
        this.session = map;
    }

    public void setElecID(String elecID) {
        this.eleID = elecID;
    }
}
