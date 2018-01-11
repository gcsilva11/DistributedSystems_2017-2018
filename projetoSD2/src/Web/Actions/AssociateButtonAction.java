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

public class AssociateButtonAction extends ActionSupport implements SessionAware {
    private static final long serialVersionUID = 4L;
    private static final String apiKey = "512274325811129", apiSecret = "83172050c3bb8239b8f11d6c0a785a1f";
    private Map<String, Object> session;

    private String authorization;

    @Override
    public String execute() throws Exception {
        final String secretState = "secret" + new Random().nextInt(999_999);
        final OAuth20Service service = new ServiceBuilder(apiKey)
                .apiSecret(apiSecret)
                .callback("http://169.254.236.23:8080/associateFacebook") // Do not change this.
                .state(secretState)
                .scope("publish_actions")
                .build(FacebookApi.instance());
        this.authorization = service.getAuthorizationUrl();
        this.session.put("service",service);
        this.session.put("authURL",this.authorization);
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