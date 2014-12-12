/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.Random;
import rmiscreensaver.CharacterMessage;

/**
 *
 * @author hoangdat
 */
public class Character {
    private String id;
    private float x,y;
    private float S_x, S_y;
    private boolean isUp, isLeft;
    private float speedX, speedY;
    private float S_speedX, S_speedY;
    private BufferedImage characterImg;
    private int characterWidth, characterHeight;
    private boolean isDraw;
    
    private float timeToChange;
    private float timeUpdate;

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

    public float getS_speedX() {
        return S_speedX;
    }

    public void setS_speedX(float S_speedX) {
        this.S_speedX = S_speedX;
    }

    public float getS_speedY() {
        return S_speedY;
    }

    public void setS_speedY(float S_speedY) {
        this.S_speedY = S_speedY;
    }

    public BufferedImage getCharacterImg() {
        return characterImg;
    }

    public void setCharacterImg(BufferedImage characterImg) {
        this.characterImg = characterImg;
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

    public boolean isIsDraw() {
        return isDraw;
    }

    public void setIsDraw(boolean isDraw) {
        this.isDraw = isDraw;
    }

    public float getS_x() {
        return S_x;
    }

    public void setS_x(float S_x) {
        this.S_x = S_x;
    }

    public float getS_y() {
        return S_y;
    }

    public void setS_y(float S_y) {
        this.S_y = S_y;
    }

    public Character(String id, BufferedImage characterImg, int characterWidth,
                                          int characterHeight, boolean isDraw) {
        
        this.id = id;
//        this.characterImg = scale(characterImg, characterWidth, characterHeight);
        this.characterImg = characterImg;
        this.characterWidth = characterWidth;
        this.characterHeight = characterHeight;
        this.isDraw = isDraw;
    }

    public Character() {
    }
    
    public void Update(CharacterMessage mes) {
        System.out.println("Update: " + getId() + ",x: " + mes.getX() + ", y: " + mes.getY());
        
        setX(mes.getX());
        setY(mes.getY());
        setIsUp(mes.isIsUp());
        setIsLeft(mes.isIsLeft());
    }
    
    public void Update(CharacterMessage mes, long elapsedTime, long elapsedTimeStd) {
        float deltaStd = elapsedTimeStd * 1.0f / Framework.secInNanosec;
        float delta    = elapsedTime * 1.0f / Framework.secInNanosec;
        speedX = (mes.getX() - S_x) / delta;
        S_x = mes.getX();
        x += speedX * deltaStd;
        speedY = (mes.getY() - S_y) / delta;
        S_y = mes.getY();
        y += speedY * deltaStd;
        System.out.println("delta: " + delta + " speedX: " + speedX + " deltaStd: " + deltaStd);
        setIsUp(mes.isIsUp());
        setIsLeft(mes.isIsLeft());
    }
    
    public void Update(float x, float y, boolean isUp, boolean isLeft) {
        System.out.println("Update: " + getId() + ",x: " + x + ", y: " + y);
        
        setX(x);
        setY(y);
        setIsUp(isUp);
        setIsLeft(isLeft);
    }
    
    public void Draw(Graphics2D g2d) {
        if (isDraw) {
            if (isLeft) {
                System.out.println("Ve: " + getId() + " tai: " + x + ", " + y);
                //System.out.println("Rong: " + characterWidth + " rong: " + characterHeight);
                g2d.drawImage(characterImg, (int)Math.floor(x), (int)Math.floor(y), 
                    characterWidth, characterHeight, null);
            } else {
                g2d.drawImage(flipImagePH(characterImg), (int)Math.floor(x), (int)Math.floor(y), 
                    characterWidth, characterHeight, null);
            }
            
            
        } else {
            System.out.println("khong ve id: " + getId() + " tai: " + x + ", " + y);
            return;
        }
    }
    
    private void GenerateState() {
        int state = generateRandom(1, 2);
        
        if (state == 1) {
            speedX = generateRandom(20, 40);
            speedY = generateRandom(10, 20);
        } else {
            speedX = -generateRandom(20, 40);
            speedY = -generateRandom(10, 20);
        }
         
        if (speedX > 0) isLeft = true;
        else            isLeft = false;
        
        if (speedY > 0) isUp = false;
        else            isUp = true;
        
        timeToChange = generateRandom(10, 30);
        timeUpdate = 0;
    }
    
    private int generateRandom(int min, int max) {
        return new Random().nextInt(max - min + 1) + min;
    }
    
    private static BufferedImage scale(BufferedImage bI, int destWidth, int destHeight) {
        
        BufferedImage buffImg = new BufferedImage(
                destWidth, destHeight, BufferedImage.TYPE_INT_ARGB);
        
        Graphics2D g2d = buffImg.createGraphics();
        g2d.drawImage(bI, 0, 0, destWidth, destHeight, null);
        g2d.dispose();
        
        return buffImg;
    }
    
    private static BufferedImage flipImagePH(BufferedImage bImg) {
        AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
        tx.translate(-bImg.getWidth(null), 0);
        AffineTransformOp Op = new AffineTransformOp(tx, 
                AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        return Op.filter(bImg, null);
    }
}
