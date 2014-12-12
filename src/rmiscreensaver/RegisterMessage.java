/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmiscreensaver;

import java.io.Serializable;

/**
 *
 * @author hoangdat
 */
public class RegisterMessage implements Serializable {
    
    //for register
    private int frameWidth;
    private int frameHeight;
    private CenterController client;
    
    
    //for return register
    private String id;
    private boolean isAdmin;
    private int serverState;

    public RegisterMessage() {
    }

    public RegisterMessage(int frameWidth, int frameHeight, CenterController client) {
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
        this.client = client;
    }
    
    public RegisterMessage(String id, boolean isAdmin, int serverState) {
        this.id = id;
        this.isAdmin = isAdmin;
        this.serverState = serverState;
    }
    

    public int getFrameWidth() {
        return frameWidth;
    }

    public void setFrameWidth(int frameWidth) {
        this.frameWidth = frameWidth;
    }

    public int getFrameHeight() {
        return frameHeight;
    }

    public void setFrameHeight(int frameHeight) {
        this.frameHeight = frameHeight;
    }

    public CenterController getClient() {
        return client;
    }

    public void setClient(CenterController client) {
        this.client = client;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public int getServerState() {
        return serverState;
    }

    public void setServerState(int serverState) {
        this.serverState = serverState;
    }
}
