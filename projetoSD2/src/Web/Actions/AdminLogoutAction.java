package Web.Actions;

import Web.Beans.BoothBean;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;

import java.util.Map;

public class AdminLogoutAction extends ActionSupport implements SessionAware {
    private static final long serialVersionUID = 4L;
    private Map<String, Object> session;

    @Override
    public String execute() throws Exception{
        if((boolean)this.session.get("loggedin"))
            this.session.put("loggedin",false);
        return "LOGOUT";
    }

    @Override
    public void setSession(Map<String, Object> map) {
        this.session = map;
    }
}