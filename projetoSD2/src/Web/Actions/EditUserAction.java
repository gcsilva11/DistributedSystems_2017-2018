package Web.Actions;

import Web.Beans.AdminBean;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;

import java.util.Map;

public class EditUserAction extends ActionSupport implements SessionAware {
    private static final long serialVersionUID = 4L;
    private Map<String, Object> session;
    private String name=null, password=null,phone=null,address=null,expDate=null,faculdade=null,id=null,profType=null;


    @Override
    public String execute() throws Exception {
        this.getAdminBean().setUsername(this.name);
        this.getAdminBean().setId(this.id);
        this.getAdminBean().setExpDate(this.expDate);
        this.getAdminBean().setPhone(this.phone);
        this.getAdminBean().setAddress(this.address);
        this.getAdminBean().setPassword(this.password);
        this.getAdminBean().getEditUser();
        return "success";
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

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setExpDate(String exp){this.expDate=exp;}

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
