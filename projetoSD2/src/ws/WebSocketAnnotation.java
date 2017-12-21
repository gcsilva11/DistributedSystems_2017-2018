package ws;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

import javax.websocket.server.ServerEndpoint;
import javax.websocket.OnOpen;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnError;
import javax.websocket.Session;

@ServerEndpoint(value = "/ws")
public class WebSocketAnnotation{
    private static final AtomicInteger sequence = new AtomicInteger(1);
    private String username;
    private Session session;
    private static final Set<WebSocketAnnotation> users = new CopyOnWriteArraySet<>();
    private static HashMap<String, Set<WebSocketAnnotation>> salasList = (HashMap<String, Set<WebSocketAnnotation>>) new HashMap<String, Set<WebSocketAnnotation>>();

    public WebSocketAnnotation(){
    }

    @OnOpen
    public void start(Session session) {
        this.session = session;
        users.add(this);
    }

    @OnClose
    public void end() {
        ArrayList<String> usernames = new ArrayList<String>();
        usernames = retrieveUsers();
        for(int i=0;i<usernames.size();i++){
            if(this.username.equals(usernames.get(i))){
                usernames.remove(i);
            }
        }
        users.remove(this);
        // clean up once the WebSocket connection is closed
    }

    @OnMessage
    public void receiveMessage(String message){
        ArrayList<String> usernames = new ArrayList<String>();
        if(message.equals("bye")){
            this.username = "";
        }
        else{
            this.username = message;
        }
        usernames = retrieveUsers();
        String toSend = "";
        if(message.equals("bye")){
            for(int i=0;i<usernames.size();i++){
                if(this.username.equals(usernames.get(i))){
                    usernames.remove(i);
                }
            }
            for(int i=0;i<usernames.size();i++){
                toSend = toSend + "  " + usernames.get(i);
            }
        }
        else{
            for(int i=0;i<usernames.size();i++){
                toSend = toSend + "  " + usernames.get(i);
            }
        }
        sendMessage(toSend);
    }

    @OnError
    public void handleError(Throwable t) {
        t.printStackTrace();
    }

    private ArrayList<String> retrieveUsers(){
        // uses *this* object's session to call sendText()
        ArrayList<String> usernames = new ArrayList<String>();
        Iterator <WebSocketAnnotation> iterator= users.iterator();
        while(iterator.hasNext()){
            usernames.add(iterator.next().getUsername());
        }
        return usernames;

    }

    private void sendMessage(String text) {
        // uses *this* object's session to call sendText()
        try {
            Iterator <WebSocketAnnotation> iterator= users.iterator();
            while(iterator.hasNext()){
                iterator.next().session.getBasicRemote().sendText(text);
            }

        } catch (IOException e) {
            // clean up once the WebSocket connection is closed
            try {
                this.session.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    private String getUsername(){
        return this.username;
    }

}