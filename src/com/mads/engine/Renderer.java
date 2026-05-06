package com.mads.engine;

import com.mads.engine.gfx.Font;
import com.mads.engine.gfx.Image;
import com.mads.engine.gfx.ImageTile;

import java.awt.image.DataBufferInt;

public class Renderer {

    private int pW, pH;
    private int[] p;

    private Font font = Font.STANDARD;

    public Renderer(GameContainer gc) {
        pW = gc.getWidth();
        pH = gc.getHeight();
        p = (
                (DataBufferInt) gc.getWindow()
                        .getImage()
                        .getRaster()
                        .getDataBuffer()
        ).getData();
    }

    public void clear() {
        for (int i = 0; i < p.length; i++) {
            p[i] = 0xff000000;
        }
    }

    public void setPixel(int x, int y, int value) {

        if ((x < 0 || x >= pW || y < 0 || y >= pH) || ((value >> 24) & 0xff) == 0) {
            //last conditional explanation
                //if the value shifted by 24 bits then bitwise `and` with hex value 255 is equal to 0
                //then the color is an Alpha Color and can be ignored
                    //ALPHA: the degree of opacity in a color. 0 opacity represents a transparent pixel

            return;
        }

        p[x + y * pW] = value;
    }

    public void drawText(String text, int offX, int offY, int color) {
        text = text.toUpperCase();

        int offset = 0;

        for (int i = 0; i < text.length(); i++) {
            int unicode = text.codePointAt(i) - 32; //account for internal codes differing from Unicode standard
            for (int y = 0; y < font.getFontImage().getH(); y++) {
                for (int x = 0; x < font.getWidths()[unicode]; x++) {

                    //if pixels in font image are white in color, they will be drawn
                    if (font.getFontImage().getP()[(x + font.getOffsets()[unicode]) + y * font.getFontImage().getW()] == 0xffffffff) {

                        setPixel(x + offX + offset, y + offY, color);
                    }
                }
            }
            offset += font.getWidths()[unicode];
        }
    }

    public void drawImage(Image image, int offX, int offY) {

        if (offX < -image.getW()) {
            return;
        }
        if (offY < -image.getH()) {
            return;
        }
        if (offX >= pW) {
            return;
        }
        if (offY >= pH) {
            return;
        }

        int newX = 0;
        int newY = 0;
        int newWidth = image.getW();
        int newHeight = image.getH();

        //clipping code
        if (offX < 0) {
            newX -= offX;
        }
        if (offY < 0) {
            newY -=offY;
        }
        if(newWidth + offX > pW) {
            newWidth -= newWidth + offX - pW;
        }
        if (newHeight + offY > pH) {
            newHeight -= newHeight + offY - pH;
        }

        //draw image
        for (int y = newY; y < newHeight; y++) {
            for (int x = newX; x < newWidth; x++) {
                setPixel(
                        (x + offX),
                        (y + offY),
                        image.getP()[x + y * image.getW()]
                );
            }
        }
    }

    public void drawImageTile(ImageTile image, int offX, int offY, int tileX, int tileY) {

        if (offX < -image.getTileW()) {
            return;
        }
        if (offY < -image.getTileH()) {
            return;
        }
        if (offX >= pW) {
            return;
        }
        if (offY >= pH) {
            return;
        }

        int newX = 0;
        int newY = 0;
        int newWidth = image.getTileW();
        int newHeight = image.getTileH();

        //clipping code
        if (offX < 0) {
            newX -= offX;
        }
        if (offY < 0) {
            newY -=offY;
        }
        if(newWidth + offX > pW) {
            newWidth -= newWidth + offX - pW;
        }
        if (newHeight + offY > pH) {
            newHeight -= newHeight + offY - pH;
        }

        //draw image
        for (int y = newY; y < newHeight; y++) {
            for (int x = newX; x < newWidth; x++) {
                setPixel(
                        (x + offX),
                        (y + offY),
                        image.getP()[
                                  (x + tileX * image.getTileW())
                                + (y + tileY * image.getTileH())
                                * image.getW()
                        ]
                );
            }
        }
    }

    public void drawRect(int offX, int offY, int width, int height, int color) {

        for (int y = 0; y <= height; y++) {
            setPixel(offX, y + offY, color);
            setPixel(offX + width, y + offY, color);
        }

        for (int x = 0; x < width; x++) {
            setPixel(x + offX, offY, color);
            setPixel(x + offX, offY + height, color);
        }
    }

    public void drawFillRect(int offX, int offY, int width, int height, int color) {


        //Don't render code
        if (offX < -width) {return;}
        if (offY < -height) {return;}
        if (offX >= pW) {return;}
        if (offY >= pH) {return;}

        int newX = 0;
        int newY = 0;
        int newWidth = width;
        int newHeight = height;

        //clipping code
        if (offX < 0) {newX -= offX;}
        if (offY < 0) {newY -= offY;}
        if (newWidth + offX >= pW) {newWidth -= newWidth + offX - pW;}
        if (newHeight + offY >= pH) {newHeight -= newHeight + offY - pH;}

        for (int y = newY; y <= newHeight; y++) {
            for (int x = newX; x < newWidth; x++) {
                setPixel(x + offX, y + offY, color);
            }
        }

    }
}
