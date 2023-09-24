package PaooGame.Arrows;

import PaooGame.Graphics.ImageLoader;
import PaooGame.Graphics.SpriteSheet;
import PaooGame.RefLinks;

import java.awt.*;
import java.awt.image.BufferedImage;
import PaooGame.Objects.Object;

public abstract class Arrow extends Object{
    public boolean alive=true;
    public Arrow(RefLinks refLinks, int x, int y) {
        super(refLinks,x,y,OBJECT_WIDTH,OBJECT_HEIGHT,2);
    }

    public abstract void Update();
    public abstract void Draw(Graphics g);
}
