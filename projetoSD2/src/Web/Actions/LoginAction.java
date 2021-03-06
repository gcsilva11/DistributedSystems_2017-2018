package Web.Actions;

import com.github.scribejava.apis.FacebookApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.Token;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.github.scribejava.core.oauth.OAuthService;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;

import Web.Beans.*;
import java.util.*;

public class LoginAction extends ActionSupport implements SessionAware {
	private static final long serialVersionUID = 4L;
	private Map<String, Object> session;
	private String username = null, password = null;

	@Override
	public String execute() throws Exception{

		if(this.username != null && !this.username.equals("") && this.password != null && !this.password.equals("") && !this.username.equals("admin")) {
			this.getLoginBean().setUsername(this.username);
			this.getLoginBean().setPassword(this.password);
			if (this.getLoginBean().getAuthenticateUser()) {
				this.session.put("username", this.username);
				this.session.put("loggedin", true); // this marks the user as logged in
				this.session.put("isUser",true);

				return "LOGIN_SUCCESS";
			} else {
				this.session.put("message","Credenciais incorretas");
			}
		} else if(this.username.equals("admin") && this.password.equals("admin")){
			session.put("username","Admin");
			this.session.put("loggedin", true);
			this.session.put("isUser",false);
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

	@Override
	public void setSession(Map<String, Object> map) {
		this.session = map;
	}
}
