package com.mads.game;

import com.mads.engine.game_handler.GameContainer;
import com.mads.engine.screen.Renderer;

import java.awt.event.KeyEvent;

public class Player extends GameObject {

    private int tileX, tileY;
    private int offX, offY;

    public int speed = 100;
    private float fallSpeed = 10;
    private float fallDistance = 0;
    private float jump = -4;
    private boolean ground = false;

    public Player(int posX, int posY) {
        tag = "player";
        this.posX = posX * GameManager.TS;
        this.posY = posY * GameManager.TS;
        width = GameManager.TS;
        height = GameManager.TS;
        tileX = posX;
        tileY = posY;
        offX = 0;
        offY = 0;
    }

    @Override
    public void update(GameContainer gc, GameManager gm, float dt) {
        //left right movement
        if (gc.getInput().isKey(KeyEvent.VK_D)) {
            if (gm.getCollision(tileX + 1, tileY) || gm.getCollision(tileX + 1, tileY + (int) Math.signum((int)offY))) {
                if (offX < 0) {
                    offX += dt * speed;
                    if (offX > 0) {
                        offX = 0;
                    }
                } else {
                    offX = 0;
                }
            } else {
                offX += dt * speed;
            }
        }
        if (gc.getInput().isKey(KeyEvent.VK_A)) {
            if (gm.getCollision(tileX - 1, tileY) || gm.getCollision(tileX - 1, tileY + (int) Math.signum((int)offY))) {
                if (offX > 0) {
                    offX -= dt * speed;
                    if (offX < 0) {
                        offX = 0;
                    }
                } else {
                    offX = 0;
                }
            } else {
                offX -= dt * speed;
            }
        }

        //jump and gravity
        fallDistance += dt * fallSpeed;

        if (gc.getInput().isKeyDown(KeyEvent.VK_W) && ground) {
            fallDistance = jump;
            ground = false;
        }

        offY += fallDistance;

        if (fallDistance < 0) {
            if ((gm.getCollision(tileX, tileY - 1) || gm.getCollision(tileX + (int) Math.signum((int)offX), tileY - 1)) && offY < 0) {
                fallDistance = 0;
                offY = 0;
            }
        }

        if (fallDistance > 0) {
            if ((gm.getCollision(tileX, tileY + 1) || gm.getCollision(tileX + (int) Math.signum((int)offX), tileY + 1)) && offY > 0) {
                fallDistance = 0;
                offY = 0;
                ground = true;
            }
        }


        //final position
        if (offY > GameManager.TS / 2) {
            tileY++;
            offY -= GameManager.TS;
        }
        if (offY < -GameManager.TS / 2) {
            tileY--;
            offY += GameManager.TS;
        }
        if (offX > GameManager.TS / 2) {
            tileX++;
            offX -= GameManager.TS;
        }
        if (offX < -GameManager.TS / 2) {
            tileX--;
            offX += GameManager.TS;
        }

        posX = tileX * GameManager.TS + offX;
        posY = tileY * GameManager.TS + offY;
    }

    @Override
    public void render(GameContainer gc, Renderer r) {
        r.drawFillRect((int) posX, (int) posY, width, height, 0xff00ff00);
    }
}
