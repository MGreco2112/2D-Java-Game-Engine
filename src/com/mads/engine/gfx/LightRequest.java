package com.mads.engine.gfx;

public class LightRequest {

    public Light light;
    public int posX, posY;

    public LightRequest(Light light, int posX, int posY) {
        this.light = light;
        this.posX = posX;
        this.posY = posY;
    }
}
