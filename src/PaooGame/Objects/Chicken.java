package PaooGame.Objects;

import PaooGame.Graphics.ImageLoader;
import PaooGame.Graphics.SpriteSheet;
import PaooGame.RefLinks;
import PaooGame.Tiles.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Chicken extends Object{

    private BufferedImage image;
    public Chicken(RefLinks refLinks,int x,int y) {
        super(refLinks,x,y,OBJECT_WIDTH,OBJECT_HEIGHT,2);
        SpriteSheet spriteSheet= new SpriteSheet(ImageLoader.LoadImage("/chicken.png"));
        image=spriteSheet.crop(0,0,32,32);
        bounds=new Rectangle(0,0,0,0);
        bounds.setLocation(16,16);
        bounds.setSize(OBJECT_WIDTH,OBJECT_HEIGHT);
    }

    public void Draw(Graphics g){
        g.drawImage(image,x+bounds.x-(int)refLinks.GetCamera().GetX(),y+bounds.y-(int)refLinks.GetCamera().GetY(),16,16,null);
        g.setColor(Color.blue);
        //g.drawRect( x+bounds.x -(int)refLinks.GetCamera().GetX(),  y+bounds.y-(int)refLinks.GetCamera().GetY()  , 16, 16);
    }
}
