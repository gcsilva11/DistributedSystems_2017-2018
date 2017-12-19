package Web.Actions;

import Web.Beans.ListAllElectionsBean;
import Web.Beans.*;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;

import java.util.Map;

public class ListLocalAction extends ActionSupport implements SessionAware {
    private static final long serialVersionUID = 4L;
    private Map<String, Object> session;

    @Override
    public String execute() throws Exception {
        return "LIST_LOCAL_ELECTIONS_SUCCESS";
    }

    public ListUserVotePlacesBean getListUserVotePlacesBean() {
        if (!session.containsKey("lListUserVotePlacesBean"))
            this.setListUserVotePlacesBean(new ListUserVotePlacesBean());
        return (ListUserVotePlacesBean) session.get("listUserVotePlacesBean");
    }

    public void setListUserVotePlacesBean(ListUserVotePlacesBean listUserVotePlacesBean) {
        this.session.put("listUserVotePlacesBean", listUserVotePlacesBean);
    }

    @Override
    public void setSession(Map<String, Object> map) {
        this.session = map;
    }
}
