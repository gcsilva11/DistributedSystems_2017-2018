package Web.Actions;

import com.github.scribejava.apis.FacebookApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;

import java.util.*;

public class LoginFacebookAction extends ActionSupport implements SessionAware {
    private static final long serialVersionUID = 4L;
    private static final String apiKey = "512274325811129", apiSecret = "83172050c3bb8239b8f11d6c0a785a1f";
    private Map<String, Object> session;
    OAuth20Service service = null;
    private String authorization = null;

    @Override
    public String execute() throws Exception {
        String secretState = "secret" + new Random().nextInt(999_999);
        this.service = new ServiceBuilder(apiKey)
                .apiSecret(apiSecret)
                .callback("http://localhost:8080/loginFacebookSuccess") // Do not change this.
                .state(secretState)
                .scope("publish_actions")
                .build(FacebookApi.instance());
        this.authorization = this.service.getAuthorizationUrl();
        this.session.put("service",this.service);
        return "REDIRECT";
    }

    public String getAuthorization() {
        return authorization;
    }

    @Override
    public void setSession(Map<String, Object> map) {
        this.session = map;
    }
}