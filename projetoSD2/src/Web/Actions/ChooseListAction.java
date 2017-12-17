package Web.Actions;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;

import java.util.Map;

public class ChooseListAction extends ActionSupport implements SessionAware {
    private static final long serialVersionUID = 4L;
    private Map<String, Object> session;

    private String username = null, idLista = null;

    @Override
    public String execute() throws Exception {
        this.session.get("");



        return "";
    }

    @Override
    public void setSession(Map<String, Object> map) {
        this.session = map;
    }
}
