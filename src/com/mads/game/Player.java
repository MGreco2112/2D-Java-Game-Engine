package com.mads.game;

import com.mads.engine.GameContainer;
import com.mads.engine.Renderer;

import java.awt.event.KeyEvent;

public class Player extends GameObject {

    public int speed = 50;

    public Player(int posX, int posY) {
        tag = "player";
        this.posX = posX * 16;
        this.posY = posY * 16;
        width = 16;
        height = 16;
    }

    @Override
    public void update(GameContainer gc, float dt) {
        if (gc.getInput().isKey(KeyEvent.VK_W)) {
            posY -= dt * speed;
        }
        if (gc.getInput().isKey(KeyEvent.VK_S)) {
            posY += dt * speed;
        }
        if (gc.getInput().isKey(KeyEvent.VK_A)) {
            posX -= dt * speed;
        }
        if (gc.getInput().isKey(KeyEvent.VK_D)) {
            posX += dt * speed;
        }

    }

    @Override
    public void render(GameContainer gc, Renderer r) {
        r.drawFillRect((int) posX, (int) posY, width, height, 0xff00ff00);
    }
}
