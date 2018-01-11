package Web.Actions;

import com.github.scribejava.apis.FacebookApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;
import Web.Beans.*;
import java.util.*;

public class VoteAction extends ActionSupport implements SessionAware {
    private static final long serialVersionUID = 4L;
    private static final String apiKey = "512274325811129", apiSecret = "83172050c3bb8239b8f11d6c0a785a1f";
    private Map<String, Object> session;
    public String nomeLista, authorization;
    OAuth20Service service = null;
    public boolean postFacebook;

    @Override
    public String execute() throws Exception {
        if((boolean)this.session.get("loggedin") && this.session.get("username") != null) {
            this.getUserBean().setUsername((String) this.session.get("username"));
            this.getUserBean().setIdUser(this.getUserBean().getIdUser());

            this.getUserBean().setIdElection((int)this.session.get("eleicao"));
            this.getUserBean().setIdList(this.getUserBean().getListID(this.nomeLista));
            this.session.put("lista", this.getUserBean().getListID(this.nomeLista));

            this.getUserBean().getVote();

            if(this.postFacebook){
                String secretState = "secret" + new Random().nextInt(999_999);
                this.service = new ServiceBuilder(apiKey)
                        .apiSecret(apiSecret)
                        .callback("http://169.254.236.23:8080/postVote") // Do not change this.
                        .state(secretState)
                        .scope("publish_actions")
                        .build(FacebookApi.instance());
                this.authorization = this.service.getAuthorizationUrl();
                this.session.put("service",this.service);
                return "REDIRECT";
            }
            return "VOTE_SUCCESS";
        } else return "LOGIN_FAIL";
    }

    public void setNomeLista(String nomeLista) {
        this.nomeLista = nomeLista;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }

    public UserBean getUserBean() {
        if(!session.containsKey("userBean"))
            this.setUserBean(new UserBean());
        return (UserBean) session.get("userBean");
    }

    public void setPostFacebook(boolean postFacebook) {
        this.postFacebook = postFacebook;
    }

    public void setUserBean(UserBean userBean) {
        this.session.put("userBean", userBean);
    }

    @Override
    public void setSession(Map<String, Object> map) {
        this.session = map;
    }
}