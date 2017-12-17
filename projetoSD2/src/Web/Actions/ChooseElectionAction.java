package Web.Actions;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;

import java.util.Map;

public class ChooseElectionAction extends ActionSupport implements SessionAware {
    private static final long serialVersionUID = 4L;
    private Map<String, Object> session;

    private String username = null, idEleicao = null;

    @Override
    public String execute() throws Exception {
        //this.idEleicao = Integer.parseInt(this.session.get("ideleicao"));

        return "";
    }

    @Override
    public void setSession(Map<String, Object> map) {
        this.session = map;
    }
}
