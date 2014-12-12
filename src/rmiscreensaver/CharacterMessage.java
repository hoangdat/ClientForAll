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
public class CharacterMessage implements Serializable{
    private String id;
    private String clientID;
    private float x,y;
    private boolean isUp, isLeft;
    private float speedX, speedY;
    private int characterWidth, characterHeight;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public boolean isIsUp() {
        return isUp;
    }

    public void setIsUp(boolean isUp) {
        this.isUp = isUp;
    }

    public boolean isIsLeft() {
        return isLeft;
    }

    public void setIsLeft(boolean isLeft) {
        this.isLeft = isLeft;
    }

    public float getSpeedX() {
        return speedX;
    }

    public void setSpeedX(float speedX) {
        this.speedX = speedX;
    }

    public float getSpeedY() {
        return speedY;
    }

    public void setSpeedY(float speedY) {
        this.speedY = speedY;
    }

    public int getCharacterWidth() {
        return characterWidth;
    }

    public void setCharacterWidth(int characterWidth) {
        this.characterWidth = characterWidth;
    }

    public int getCharacterHeight() {
        return characterHeight;
    }

    public void setCharacterHeight(int characterHeight) {
        this.characterHeight = characterHeight;
    }

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    public CharacterMessage(String id, float x, float y, boolean isUp, boolean isLeft) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.isUp = isUp;
        this.isLeft = isLeft;
    }

    public CharacterMessage(String id, float x, float y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }

    public CharacterMessage(String id, String clientID, float x, float y, boolean isUp, boolean isLeft) {
        this.id = id;
        this.clientID = clientID;
        this.x = x;
        this.y = y;
        this.isUp = isUp;
        this.isLeft = isLeft;
    }
    
    public CharacterMessage(String id, String clientID, float x, float y, 
                             boolean isUp, boolean isLeft, float speedX, float speedY) {
        this.id = id;
        this.clientID = clientID;
        this.x = x;
        this.y = y;
        this.isUp = isUp;
        this.isLeft = isLeft;
        this.speedX = speedX;
        this.speedY = speedY;
    }

    public CharacterMessage() {
    }
    
    
}
