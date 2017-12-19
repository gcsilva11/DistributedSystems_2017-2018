package Web.Actions;

import Web.Beans.CreateElectionBean;
import Web.Beans.EditElectionBean;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;

import java.util.Map;

public class EditElectionAction extends ActionSupport implements SessionAware {
	private static final long serialVersionUID = 4L;
	private Map<String, Object> session;
    private String eleID = null;
    private String eleTitle = null, eleDesc=null ,startDate=null ,endDate= null;

	public String executeText() throws Exception {
        this.getEditElectionBean().setEleID(this.eleID);
        this.getEditElectionBean().setEleTitle(this.eleTitle);
        this.getEditElectionBean().setEleDesc(this.eleDesc);
        if(this.getEditElectionBean().getEleTextEditSuccess()){
            addActionMessage("Eleição editada com sucesso");
            return "ELE_TEXT_SUCCESS";
        }
        addActionMessage("Erro na edição da eleição");
        return "ELE_TEXT_FAIL";
	}

    public String executeDate() throws Exception {
        this.getEditElectionBean().setEleID(this.eleID);
        this.getEditElectionBean().setEleStartDate(this.startDate);
        this.getEditElectionBean().setEleEndDate(this.endDate);
        if(this.getEditElectionBean().getEleDateEditSuccess()){
            addActionMessage("Data editada com sucesso");
            return "ELE_DATE_SUCCESS";
        }
        addActionMessage("Erro na edição da eleição");
        return "ELE_DATE_FAIL";
    }

    public EditElectionBean getEditElectionBean() {
        if(!session.containsKey("editElectionBean"))
            this.setEditElectionBean(new EditElectionBean());
        return (EditElectionBean) session.get("editElectionBean");
    }

    public void setEditElectionBean(EditElectionBean editElectionBean) {
        this.session.put("editElectionBean", editElectionBean);
    }

	@Override
	public void setSession(Map<String, Object> map) {
		this.session = map;
	}

    public void setElecID(String elecID) {
        this.eleID = elecID;
    }

    public void setNewElecName(String newElecName) {
        this.eleTitle = newElecName;
    }

    public void setNewElecDescription(String newElecDescription) {
        this.eleDesc = newElecDescription;
    }

    public void setNewStartDate(String newStartDate) {
        this.startDate = newStartDate;
    }

    public void setNewEndDate(String newEndDate) {
        this.endDate = newEndDate;
    }
}
