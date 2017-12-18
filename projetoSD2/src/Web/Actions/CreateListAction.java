package Web.Actions;

import Web.Beans.CreateListBean;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;

import java.util.Map;

public class CreateListAction extends ActionSupport implements SessionAware {
	private static final long serialVersionUID = 4L;
	private Map<String, Object> session;
	private String listName = null, elecID = null, listType = null,userID = null;

    @Override
	public String execute() throws Exception {
        this.getCreateListBean().setListName(this.listName);
        this.getCreateListBean().setListType(this.listType);
        this.getCreateListBean().setElecID(this.elecID);
        if(this.getCreateListBean().getListCreateSuccess()) {
            return "LISTCREATE_SUCCESS";
        }
        return "LISTCREATE_FAIL";
	}

	public String executeAdd() throws Exception {
        this.getCreateListBean().setUserID(this.userID);
        if(this.getCreateListBean().getUserAddSuccess()){
            this.session.put("message","User acrescentado com sucesso");
            return "USER_ADD";
        }
        else {
            this.session.put("message", "Erro a acrescentar user!");
            return "USER_ADD";
        }
    }

    public CreateListBean getCreateListBean() {
        if(!session.containsKey("createListBean"))
            this.setCreateListBean(new CreateListBean());
        return (CreateListBean) session.get("createListBean");
    }

    public void setCreateListBean(CreateListBean createListBean) {
        this.session.put("createListBean", createListBean);
    }

	@Override
	public void setSession(Map<String, Object> map) {
		this.session = map;
	}

    public void setElecID(String elecID) {
        this.elecID = elecID;
    }

    public void setListName(String listName) {
        this.listName=listName;
    }

    public void setListType(String listType) {
        this.listType = listType;
    }

    public void setUserID(String userID){
        this.userID = userID;
    }
}
