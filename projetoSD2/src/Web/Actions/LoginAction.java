package Web.Actions;

import Web.Beans.LoginBean;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;

import java.util.Map;

public class LoginAction extends ActionSupport implements SessionAware {
	private static final long serialVersionUID = 4L;
	private Map<String, Object> session;
	private String username = null, password = null, faculdade = null;

	@Override
	public String execute() throws Exception{

		if(this.username != null && !this.username.equals("") && this.password != null && !this.password.equals("") && this.faculdade!= null && !this.faculdade.equals("") && !this.username.equals("admin")) {
			this.getLoginBean().setFaculdade(this.faculdade);

			if (this.getLoginBean().getCheckFaculdade()) {
				session.put("votebooth", this.faculdade);

				this.getLoginBean().setUsername(this.username);
				this.getLoginBean().setPassword(this.password);

				if (this.getLoginBean().getIdentifyName()) {
					if (this.getLoginBean().getAuthenticateUser()) {
						session.put("username", this.username);
						session.put("loggedin", true); // this marks the user as logged in
						return "loginSuccess";
					} else {
						session.put("message","Credenciais incorretas");
					}
				} else {
					session.put("message","User não pertence à faculdade");
				}
			} else {
				session.put("message","Faculdade não existe");
			}
		} else if(this.username.equals("admin") && this.password.equals("admin") && this.faculdade.equals("")){
			return "loginAdmin";
		} else {
			session.put("message","Fail no login");
		}
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

	public void setVotebooth(String faculdade) {
		this.faculdade = faculdade;
	}

	@Override
	public void setSession(Map<String, Object> map) {
		this.session = map;
	}
}
