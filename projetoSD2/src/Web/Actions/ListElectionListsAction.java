package Web.Actions;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;
import Web.Beans.*;
import java.util.*;

public class ListElectionListsAction extends ActionSupport implements SessionAware {
    private static final long serialVersionUID = 4L;
    private Map<String, Object> session;
    public ArrayList<String> listas = new ArrayList<>();

    private String nomeEleicao;


    @Override
    public String execute() throws Exception {
        if((boolean)this.session.get("loggedin") && this.session.get("username") != null){
            this.getUserBean().setUsername((String) this.session.get("username"));
            this.getUserBean().setIdFac(Integer.parseInt((String) this.session.get("votebooth")));
            this.getUserBean().setIdUser(this.getUserBean().getIdUser());

            this.getUserBean().setIdElection(this.getUserBean().getElectionID(this.nomeEleicao));
            this.session.put("eleicao", this.getUserBean().getElectionID(this.nomeEleicao));

            for (int i = 0; i < this.getUserBean().getListas().size(); i++) {
                this.getUserBean().setIdList(this.getUserBean().getListas().get(i));
                if(!this.getUserBean().getListName().equals("NULLVOTE")) {
                    this.getUserBean().setIdList(this.getUserBean().getListas().get(i));
                    this.listas.add(this.getUserBean().getListName());
                }
            }
            this.getUserBean().setLNames(this.listas);
            return "success";
        } else return "errorLogin";
    }

    public void setNomeEleicao(String nomeEleicao) {
        this.nomeEleicao = nomeEleicao;
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
