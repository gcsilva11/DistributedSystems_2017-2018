package Web.Actions;

import Web.Beans.UserBean;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;

import java.util.Map;

public class ListElectionAction extends ActionSupport implements SessionAware {
    private static final long serialVersionUID = 4L;
    private Map<String, Object> session;

    private int userID,electionID,listID,facID;
    private String username;

    @Override
    public String execute() throws Exception {
        if((boolean)this.session.get("loggedind")) {
            this.username = (String) this.session.get("username");
            this.facID = (int)this.session.get("votebooth");
            this.userID = this.getUserBean().getUserID(this.username);

            this.session.put("eleicoes",this.getUserBean().getMesaDeVotoEls(this.facID));


            this.electionID = (int)this.session.get("electionid");
            this.listID = (int)this.session.get("listid");

        }
        return "sessionLogInFail";
    }

    public UserBean getUserBean() {
        if(!session.containsKey("userBean"))
            this.setLoginBean(new UserBean());
        return (UserBean) session.get("userBean");
    }

    public void setLoginBean(UserBean userBean) {
        this.session.put("userBean", userBean);
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setElectionID(int electionID) {
        this.electionID = electionID;
    }

    public void setListID(int listID) {
        this.listID = listID;
    }

    public void setFacID(int facID) {
        this.facID = facID;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public void setSession(Map<String, Object> map) {
        this.session = map;
    }
}
