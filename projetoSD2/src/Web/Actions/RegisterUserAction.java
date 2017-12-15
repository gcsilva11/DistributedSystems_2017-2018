package Web.Actions;

import Web.Beans.AdminBean;
import Web.Beans.LoginBean;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;

import java.util.Map;

public class RegisterUserAction extends ActionSupport implements SessionAware {
	private static final long serialVersionUID = 4L;
	private Map<String, Object> session;
    private String username=null, password=null,phone=null,address=null,expDate=null,faculdade=null,id=null,profType=null;


    @Override
	public String execute() throws Exception {
	    this.getAdminBean().setUsername(this.username);
	    this.getAdminBean().setPassword(this.password);
        this.getAdminBean().setPhone(this.phone);
        this.getAdminBean().setAddress(this.address);
        this.getAdminBean().setExpDate(this.expDate);
        this.getAdminBean().setFaculdade(this.faculdade);
        this.getAdminBean().setId(this.id);
        this.getAdminBean().setProfType(this.profType);
	    if(this.getAdminBean().getRegistrationSucess()){
	        return "registerSuccess";
        }
        return "registerFail";
	}

    public AdminBean getAdminBean() {
        if(!session.containsKey("adminBean"))
            this.setAdminBean(new AdminBean());
        return (AdminBean) session.get("adminBean");
    }

    public void setAdminBean(AdminBean adminBean) {
        this.session.put("adminBean", adminBean);
    }

	@Override
	public void setSession(Map<String, Object> map) {
		this.session = map;
	}
}
