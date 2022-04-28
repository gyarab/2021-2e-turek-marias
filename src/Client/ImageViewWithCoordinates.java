/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author jakub
 */
public class ImageViewWithCoordinates extends ImageView {

    private double defaultX;
    private double defaultY;
    private double cursorX;
    private double cursorY;
    private boolean inactive;
    private String cardInfo;

    public ImageViewWithCoordinates(Image image) {
        super(image);
        inactive = false;
    }
    
    public void setInactive(boolean inactive) {
        this.inactive = inactive;
    }

    public boolean isInactive() {
        return inactive;
    }

    public void setCursorX(double cursorX) {
        this.cursorX = cursorX;
    }

    public void setDefaultX(double defaultX) {
        this.defaultX = defaultX;
    }

    public void setCursorY(double cursorY) {
        this.cursorY = cursorY;
    }

    public void setDefaultY(double defaultY) {
        this.defaultY = defaultY;
    }

    public double getDefaultX() {
        return defaultX;
    }

    public double getCursorX() {
        return cursorX;
    }

    public double getDefaultY() {
        return defaultY;
    }

    public double getCursorY() {
        return cursorY;
    }

    public String getCardInfo() {
        return cardInfo;
    }

    public void setCardInfo(String cardInfo) {
        this.cardInfo = cardInfo;
    }
    
    
}
