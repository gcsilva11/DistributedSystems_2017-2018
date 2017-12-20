package Web.Actions;

import Web.Beans.BoothBean;
import Web.Beans.ListAllElectionsBean;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;

import java.util.Map;

public class ListAllElectionsAction extends ActionSupport implements SessionAware {
    private static final long serialVersionUID = 4L;
    private Map<String, Object> session;

    @Override
    public String execute(){
        return "LIST_ALL_ELECTIONS_SUCCESS";
    }

    public ListAllElectionsBean getListAllElectionsBean() {
        if (!session.containsKey("listAllElectionsBean"))
            this.setListAllElectionsBean(new ListAllElectionsBean());
        return (ListAllElectionsBean) session.get("listAllElectionsBean");
    }

    public void setListAllElectionsBean(ListAllElectionsBean listAllElectionsBean) {
        this.session.put("listAllElectionsBean", listAllElectionsBean);
    }

    @Override
    public void setSession(Map<String, Object> map) {
        this.session = map;
    }

}
