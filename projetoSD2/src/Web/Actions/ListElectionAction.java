package Web.Actions;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;
import Web.Beans.*;
import java.util.*;

public class ListElectionAction extends ActionSupport implements SessionAware {
    private static final long serialVersionUID = 4L;
    private Map<String, Object> session;

    public ArrayList<String> eleicoes = new ArrayList<>();

    @Override
    public String execute() throws Exception {
        if((boolean)this.session.get("loggedin") && this.session.get("username") != null){
            this.getUserBean().setUsername((String)this.session.get("username"));
            this.getUserBean().setIdUser(this.getUserBean().getIdUser());

            for (int i = 0; i < this.getUserBean().getEleicoes().size();i++){
                this.getUserBean().setIdElection(this.getUserBean().getEleicoes().get(i));
                if(!this.getUserBean().getJaVotou() && this.getUserBean().getUserPodeVotar() && this.getUserBean().getEleicaoEstaAtiva()) {
                    this.eleicoes.add(this.getUserBean().getElectionName());
                }
            }
            this.getUserBean().setElNames(this.eleicoes);
            return "LIST_SUCCESS";
        } else return "LOGIN_FAIL";
    }

    public UserBean getUserBean() {
        if(!session.containsKey("userBean"))
            this.setUserBean(new UserBean());
        return (UserBean) session.get("userBean");
    }

    public void setUserBean(UserBean userBean) {
        this.session.put("userBean", userBean);
    }

    @Override
    public void setSession(Map<String, Object> map) {
        this.session = map;
    }
}
