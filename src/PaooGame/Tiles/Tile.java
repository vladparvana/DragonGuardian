package PaooGame.Tiles;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Tile {
    public static final int TILE_HEIGHT = 48;
    public static final int TILE_WIDTH = 48;

    private Rectangle bound;
    private BufferedImage image;

    public Tile(BufferedImage image) {
        this.image=image;
        this.bound=new Rectangle(8,8,32,32);
    }

    public int GetBoundX() {return bound.x;}
    public int GetBoundY() {return bound.y;}
    public int GetBoundWidth() {return bound.width;}
    public int GetBoundHeight() {return bound.height;}
    public void draw(Graphics g, int x, int y) {
        g.drawImage(image,x,y,TILE_WIDTH,TILE_WIDTH,null);
        g.setColor(Color.blue);
        //g.drawRect( x ,  y  ,Tile.TILE_WIDTH, Tile.TILE_HEIGHT);
    }
}
