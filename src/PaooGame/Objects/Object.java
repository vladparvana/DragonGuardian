package PaooGame.Objects;

import PaooGame.RefLinks;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Object {
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected RefLinks refLinks;
    protected Rectangle bounds;
    protected int life;
    protected int alive=1;

    public static final int OBJECT_WIDTH=16;
    public static final int OBJECT_HEIGHT=16;


    public Object(RefLinks refLinks,int x,int y,int width,int height,int life)
    {
        this.refLinks=refLinks;
        this.x=x;
        this.y=y;
        this.width=width;
        this.height=height;
        this.life = life;
        this.alive=1;
    }

    public int isAlive() {return alive;}

    public abstract void Draw(Graphics g);

    public double GetBoundsHeight() {
        return (double) bounds.height;
    }

    public double GetBoundsWidth() {
        return (double) bounds.width;
    }

    public double GetX() {
        return (double)x;
    }

    public double GetY() {
        return (double) y;
    }

    public void NotAlive() {
        this.alive=0;
    }

    public double GetBoundsY() {
        return bounds.y;
    }

    public double GetBoundsX() {
        return bounds.x;
    }

    public int GetLife() {
        return life;
    }
}
