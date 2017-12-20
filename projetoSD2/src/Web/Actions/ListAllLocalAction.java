package Web.Actions;

import Web.Beans.*;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;

import java.util.ArrayList;
import java.util.Map;

public class ListAllLocalAction extends ActionSupport implements SessionAware {
    private static final long serialVersionUID = 4L;
    private Map<String, Object> session;
    private ArrayList<ArrayList<String>> lista = new ArrayList<>();
    private String userID = null;


    @Override
    public String execute() throws Exception {
        this.getListAllLocalBean().setIdUser(this.userID);

        ArrayList<Integer> elecIds = this.getListAllLocalBean().getEls();
        for (int i = 0; i < elecIds.size(); i++) {
            ArrayList<String> aux = new ArrayList<>();
            this.getListAllLocalBean().setIdElection(elecIds.get(i));
            if(this.getListAllLocalBean().getJaVotou() && this.getListAllLocalBean().getUserPodeVotar() ){
                if(!this.getListAllLocalBean().getNameFac().equals("")) {
                    aux.add(Integer.toString(elecIds.get(i)));
                    aux.add(this.getListAllLocalBean().getNameFac());
                    this.lista.add(aux);
                } else{
                    aux.add(Integer.toString(elecIds.get(i)));
                    aux.add("Web");
                    this.lista.add(aux);
                }
            }
        }
        this.getListAllLocalBean().setPlacesVoted(this.lista);

        return "LIST_ALL_LOCAL_SUCCESS";
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public ListAllLocalBean getListAllLocalBean() {
        if(!session.containsKey("listAllLocalBean"))
            this.setListAllLocalBean(new ListAllLocalBean());
        return (ListAllLocalBean) session.get("listAllLocalBean");
    }

    public void setListAllLocalBean(ListAllLocalBean listAllLocalBean) {
        this.session.put("listAllLocalBean", listAllLocalBean);
    }

    @Override
    public void setSession(Map<String, Object> map) {
        this.session = map;
    }
}