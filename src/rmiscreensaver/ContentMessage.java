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
public class ContentMessage implements Serializable{
    private String backgroundName;
    private String characterName;
    private int characterCount;

    public ContentMessage() {
    }

    public ContentMessage(String backgroundName, String characterName) {
        this.backgroundName = backgroundName;
        this.characterName = characterName;
    }

    public String getBackgroundName() {
        return backgroundName;
    }

    public void setBackgroundName(String backgroundName) {
        this.backgroundName = backgroundName;
    }

    public String getCharacterName() {
        return characterName;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }

    public int getCharacterCount() {
        return characterCount;
    }

    public void setCharacterCount(int characterCount) {
        this.characterCount = characterCount;
    }
    
}
