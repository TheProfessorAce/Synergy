package com.cookies.synergy.game.box2d;

import com.cookies.synergy.game.enums.userDataType;

public abstract class userData {

    protected userDataType userDataType;
    protected float width, height;
    protected int mode;
    protected boolean isActive;

    public userData() {

    }

    public userData(float width, float height) {
        this.width = width;
        this.height = height;
    }

    public userData(boolean isActive) {
        this.isActive = isActive;
    }

    public userData(int mode) {
        this.mode = mode;
    }

    public userDataType getUserDataType() {
        return userDataType;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public boolean isItActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

}
