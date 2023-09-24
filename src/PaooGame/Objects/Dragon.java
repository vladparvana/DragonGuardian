package PaooGame.Objects;

import PaooGame.Graphics.ImageLoader;
import PaooGame.Graphics.SpriteSheet;
import PaooGame.RefLinks;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Dragon extends Object{

    private BufferedImage image;
    public Dragon(RefLinks refLinks, int x, int y) {
        super(refLinks,x,y,OBJECT_WIDTH,OBJECT_HEIGHT,2);
        SpriteSheet spriteSheet= new SpriteSheet(ImageLoader.LoadImage("/dragon.png"));
        image=spriteSheet.crop(0,0,16,16);
        bounds=new Rectangle(0,0,0,0);
        bounds.setLocation(16,16);
        bounds.setSize(OBJECT_WIDTH,OBJECT_HEIGHT);
    }

    public void Draw(Graphics g){
        g.drawImage(image,x+bounds.x-(int)refLinks.GetCamera().GetX(),y+bounds.y-(int)refLinks.GetCamera().GetY(),48,48,null);
        g.setColor(Color.blue);
        //g.drawRect( x+bounds.x -(int)refLinks.GetCamera().GetX(),  y+bounds.y-(int)refLinks.GetCamera().GetY()  , 48, 48);
    }
}
