package Web.Actions;

import Web.Beans.*;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;
import java.util.Map;

public class ListAllLocalAction extends ActionSupport implements SessionAware {
    private static final long serialVersionUID = 4L;
    private Map<String, Object> session;



    @Override
    public String execute() throws Exception {
        return super.execute();
    }

    @Override
    public void setSession(Map<String, Object> map) {
        this.session = map;
    }
}