package actions.Login;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;

import java.rmi.RemoteException;
import java.util.Map;

public class LoginAction extends ActionSupport implements SessionAware {
	private static final long serialVersionUID = 4L;
	private Map<String, Object> session;
	private String username = null, password = null;

	@Override
	public String execute() throws Exception{

		if(this.username != null && !username.equals("")) {
			this.getLoginBean().setUsername(this.username);
			this.getLoginBean().setPassword(this.password);

			if(this.getLoginBean().getAuthenticateUser()) {
				session.put("username", username);
				session.put("loggedin", true); // this marks the user as logged in
				return "loginSuccess";
			}
			else
				return "loginFail";
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

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
}
