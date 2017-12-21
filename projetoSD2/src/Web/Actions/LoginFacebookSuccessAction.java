package Web.Actions;

import Web.Beans.*;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;

import java.util.Map;

public class LoginFacebookSuccessAction extends ActionSupport implements SessionAware {
    private static final long serialVersionUID = 4L;
    private Map<String, Object> session;

    private static final String PROTECTED_RESOURCE_URL = "https://graph.facebook.com/me";

    private String code, state;
    @Override
    public String execute() throws Exception {
        OAuth20Service service = (OAuth20Service) session.get("service");
        OAuth2AccessToken accessToken = service.getAccessToken(this.code);
        final OAuthRequest request = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL);
        service.signRequest(accessToken,request);
        final Response response = service.execute(request);
        String[] split =response.getBody().split("\"");
        String responseID = split[(split.length)-2];
        this.getLoginBean().setIdFacebook(responseID);
        String name = this.getLoginBean().getAuthenticateFacebook();
        if(!name.equals("")) {
            this.session.put("username",name);
            this.session.put("loggedin",true);
            return "LOGIN_SUCCESS";
        }
        else
            return "ASSOCIATE_ERROR";
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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
    public void setSession(Map<String, Object> map) {
        this.session = map;
    }
}