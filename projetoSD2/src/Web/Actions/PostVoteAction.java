package Web.Actions;

import com.github.scribejava.apis.FacebookApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;
import Web.Beans.*;
import java.util.*;

public class PostVoteAction extends ActionSupport implements SessionAware {
    private static final long serialVersionUID = 4L;
    private static final String apiKey = "512274325811129", apiSecret = "83172050c3bb8239b8f11d6c0a785a1f";
    private Map<String, Object> session;
    OAuth20Service service = null;
    private String code, state;
    private String username;

    @Override
    public String execute() throws Exception {
        this.getUserBean().setUsername((String)this.session.get("username"));
        this.getUserBean().setIdElection((int)this.session.get("eleicao"));
        this.service = (OAuth20Service)this.session.get("service");

        if(this.getUserBean().getIdFacebook()!=null) {
            OAuth20Service service = (OAuth20Service) session.get("service");
            OAuth2AccessToken accessToken = service.getAccessToken(this.code);
            String postString = "https://graph.facebook.com/" + this.getUserBean().getIdFacebook() + "/feed?message=Acabei%20de%20votar%20na%20eleicao%20" + this.getUserBean().getElectionName().replace(" ","%20") + "%20atraves%20do%20iVotas!";
            final OAuthRequest request = new OAuthRequest(Verb.POST, postString);
            this.service.signRequest(accessToken, request);
            final Response response = service.execute(request);
            return "PUBLISH_SUCCESS";
        }
        addActionMessage("User não tem conta facebook associada");
        return "PUBLISH_FAIL";
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

    public UserBean getUserBean() {
        if(!session.containsKey("userBean"))
            this.setUserBean(new UserBean());
        return (UserBean) session.get("userBean");
    }

    public void setUserBean(UserBean userBean) {
        this.session.put("userBean", userBean);
    }

    @Override
    public void setSession(Map<String, Object> map) {
        this.session = map;
    }
}