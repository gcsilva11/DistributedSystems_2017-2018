package Web.Actions;

import Web.Beans.*;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;

import java.util.ArrayList;
import java.util.Map;

public class ListLocalAction extends ActionSupport implements SessionAware {
    private static final long serialVersionUID = 4L;
    private Map<String, Object> session;
    private ArrayList<ArrayList<String>> lista = new ArrayList<>();

    @Override
    public String execute() throws Exception {
        if((boolean)this.session.get("loggedin") && this.session.get("username") != null) {
            this.getUserBean().setUsername((String) this.session.get("username"));
            this.getUserBean().setIdUser(this.getUserBean().getIdUser());
            ArrayList<Integer> elecIds = this.getUserBean().getEls();

            for (int i = 0; i < elecIds.size(); i++) {
                ArrayList<String> aux = new ArrayList<>();
                this.getUserBean().setIdElection(elecIds.get(i));
                if(this.getUserBean().getJaVotou() && this.getUserBean().getUserPodeVotar() ){
                    if(!this.getUserBean().getNameFac().equals("")) {
                        aux.add(Integer.toString(elecIds.get(i)));
                        aux.add(this.getUserBean().getNameFac());
                        this.lista.add(aux);
                    } else{
                        aux.add(Integer.toString(elecIds.get(i)));
                        aux.add("Web");
                        this.lista.add(aux);
                    }
                }
            }
            this.getUserBean().setPlacesVoted(this.lista);
            return "LIST_LOCAL_ELECTIONS_SUCCESS";
        } else return "errorLogin";
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
