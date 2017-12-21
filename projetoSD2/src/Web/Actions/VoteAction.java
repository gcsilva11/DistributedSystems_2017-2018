package Web.Actions;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;
import Web.Beans.*;
import java.util.*;

public class VoteAction extends ActionSupport implements SessionAware {
    private static final long serialVersionUID = 4L;
    private Map<String, Object> session;
    public String nomeLista;
    public boolean postFacebook;

    @Override
    public String execute() throws Exception {
        if((boolean)this.session.get("loggedin") && this.session.get("username") != null) {
            this.getUserBean().setUsername((String) this.session.get("username"));
            this.getUserBean().setIdUser(this.getUserBean().getIdUser());

            this.getUserBean().setIdElection((int)this.session.get("eleicao"));
            this.getUserBean().setIdList(this.getUserBean().getListID(this.nomeLista));
            this.session.put("lista", this.getUserBean().getListID(this.nomeLista));

            this.getUserBean().getVote();

            if(this.postFacebook){

            }


            return "VOTE_SUCCESS";
        } else return "LOGIN_FAIL";
    }

    public void setNomeLista(String nomeLista) {
        this.nomeLista = nomeLista;
    }

    public UserBean getUserBean() {
        if(!session.containsKey("userBean"))
            this.setUserBean(new UserBean());
        return (UserBean) session.get("userBean");
    }

    public void setPostFacebook(boolean postFacebook) {
        this.postFacebook = postFacebook;
    }

    public void setUserBean(UserBean userBean) {
        this.session.put("userBean", userBean);
    }

    @Override
    public void setSession(Map<String, Object> map) {
        this.session = map;
    }
}