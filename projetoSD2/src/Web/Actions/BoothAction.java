package Web.Actions;

import Web.Beans.BoothBean;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;

import java.util.Map;

public class BoothAction extends ActionSupport implements SessionAware {
    private static final long serialVersionUID = 4L;
    private Map<String, Object> session;
    String facID = null, elecID = null;

    public String executeAdd() throws Exception {
        this.getBoothBean().setElecID(elecID);
        this.getBoothBean().setFacID(facID);
        if(this.getBoothBean().getAddBoothSuccess()){
            addActionMessage("Mesa acrescentada com sucesso");
            return "BOOTH_ADD_SUCCESS";
        }
        addActionMessage("Erro a acrescentar a mesa...");
        return "BOOTH_ADD_FAIL";
    }

    public String executeRemove() throws Exception {
        this.getBoothBean().setElecID(elecID);
        this.getBoothBean().setFacID(facID);
        if(this.getBoothBean().getRemoveBoothSuccess()){
            addActionMessage("Mesa removida com sucesso");
            return "BOOTH_REMOVE_SUCCESS";
        }
        addActionMessage("Erro a remover mesa de voto...");
        return "BOOTH_REMOVE_FAIL";
    }

    public BoothBean getBoothBean() {
        if (!session.containsKey("boothBean"))
            this.setBoothBean(new BoothBean());
        return (BoothBean) session.get("boothBean");
    }

    public void setBoothBean(BoothBean boothBean) {
        this.session.put("boothBean", boothBean);
    }

    @Override
    public void setSession(Map<String, Object> map) {
        this.session = map;
    }

    public void setFacID(String facID) {
        this.facID = facID;
    }

    public void setElecID(String elecID){
        this.elecID = elecID;
    }
}
