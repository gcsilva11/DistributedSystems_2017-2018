package Web.Actions;

import Web.Beans.AdminBean;
import Web.Beans.CreateElectionBean;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;

import java.util.Map;

public class CreateElectionAction extends ActionSupport implements SessionAware {
	private static final long serialVersionUID = 4L;
	private Map<String, Object> session;
    private String eleID = null,eleType = null,elecFacID = null;
    private String eleTitle = null, eleDesc=null ,startDate=null ,endDate= null;

    @Override
	public String execute() throws Exception {
        this.getCreateElectionBean().setEleID(this.eleID);
        this.getCreateElectionBean().setEleTitle(this.eleTitle);
        this.getCreateElectionBean().setEleType(this.eleType);
        this.getCreateElectionBean().setEleDesc(this.eleDesc);
        this.getCreateElectionBean().setEleStartDate(this.startDate);
        this.getCreateElectionBean().setEleEndDate(this.endDate);
        this.getCreateElectionBean().setElecFacID(this.elecFacID);
        if(this.getCreateElectionBean().getEleCreateSuccess()){
            return "ELECREATE_SUCCESS";
        }
        return "ELECREATE_FAIL";
	}

    public CreateElectionBean getCreateElectionBean() {
        if(!session.containsKey("createElectionBean"))
            this.setCreateElectionBean(new CreateElectionBean());
        return (CreateElectionBean) session.get("createElectionBean");
    }

    public void setCreateElectionBean(CreateElectionBean createElectionBean) {
        this.session.put("createElectionBean", createElectionBean);
    }

	@Override
	public void setSession(Map<String, Object> map) {
		this.session = map;
	}


    public void setElecID(String elecID) {
        this.eleID = elecID;
    }

    public void setElecName(String elecName) {
        this.eleTitle = elecName;
    }

    public void setElecDescription(String elecDescription) {
        this.eleDesc= elecDescription;
    }

    public void setElecStartDate(String elecStartDate) {
        this.startDate=elecStartDate;
    }

    public void setElecEndDate(String elecEndDate) {
        this.endDate=elecEndDate;
    }

    public void setElecFacID(String elecFacID) {
        this.elecFacID = elecFacID;
    }

    public void setElecType(String elecType) {
        this.eleType = elecType;
    }
}
