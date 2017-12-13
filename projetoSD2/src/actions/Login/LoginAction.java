package actions.Login;


import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;

import java.util.Map;

public class LoginAction extends ActionSupport implements SessionAware {
	private static final long serialVersionUID = 4L;
	private Map<String, Object> session;
	private String username = null, password = null;

	@Override
	public String execute() {

		// any username is accepted without confirmation (should check using RMI)
		if(this.username != null && !username.equals("")) {
			this.getLoginBean().setUsername(this.username);
			this.getLoginBean().setPassword(this.password);
			session.put("username", username);
			session.put("loggedin", true); // this marks the user as logged in
			return "loginSuccess";
		}
		else
			return "loginFail";
	}

	public LoginBean getLoginBean() {
		if(!session.containsKey("loginBean"))
			this.setLoginBean(new LoginBean());
		return (LoginBean) session.get("loginBean");
	}

	public void setLoginBean(LoginBean loginBean) {
		this.session.put("loginBean", loginBean);
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
}
