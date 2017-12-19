package Web.Actions;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;

import Web.Beans.*;
import java.util.*;

public class LoginAction extends ActionSupport implements SessionAware {
	private static final long serialVersionUID = 4L;
	private Map<String, Object> session;
	private String username = null, password = null, faculdade = null;

	@Override
	public String execute() throws Exception{

		if(this.username != null && !this.username.equals("") && this.password != null && !this.password.equals("") && this.faculdade!= null && !this.faculdade.equals("") && !this.username.equals("admin")) {
			this.getLoginBean().setFaculdade(this.faculdade);

			if (this.getLoginBean().getCheckFaculdade()) {
				this.session.put("votebooth", this.faculdade);

				this.getLoginBean().setUsername(this.username);
				this.getLoginBean().setPassword(this.password);

				if (this.getLoginBean().getIdentifyName()) {
					if (this.getLoginBean().getAuthenticateUser()) {
						this.session.put("username", this.username);
						this.session.put("loggedin", true); // this marks the user as logged in

						return "LOGIN_SUCCESS";
					} else {
						this.session.put("message","Credenciais incorretas");
					}
				} else {
					this.session.put("message","User não pertence à faculdade");
				}
			} else {
				this.session.put("message","Faculdade não existe");
			}
		} else if(this.username.equals("admin") && this.password.equals("admin") && this.faculdade.equals("")){
			session.put("Admin",true);
			return "LOGIN_ADMIN";
		} else {
			this.session.put("message","Fail no login");
		}
		return "LOGIN_FAIL";
	}

	public LoginBean getLoginBean() {
		if(!session.containsKey("loginBean"))
			this.setLoginBean(new LoginBean());
		return (LoginBean) session.get("loginBean");
	}

	public void setLoginBean(LoginBean loginBean) {
		this.session.put("loginBean", loginBean);
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setVotebooth(String faculdade) {
		this.faculdade = faculdade;
	}

	@Override
	public void setSession(Map<String, Object> map) {
		this.session = map;
	}
}
