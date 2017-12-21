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

public class AssociateFacebookAction extends ActionSupport implements SessionAware {
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

        this.getUserBean().setUsername((String) this.session.get("username"));
        this.getUserBean().setIdUser(this.getUserBean().getIdUser());
        this.getUserBean().setIdFacebook(responseID);
        if(this.getUserBean().getAssociateUser())
            return "ASSOCIATE_SUCCESS";
        else{
            addActionMessage("Não foi possivel associar conta de facebook");
            return "ASSOCIATE_ERROR";
        }

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