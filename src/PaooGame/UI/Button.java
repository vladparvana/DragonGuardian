package PaooGame.UI;

import PaooGame.Graphics.ImageLoader;
import PaooGame.Graphics.SpriteSheet;
import PaooGame.RefLinks;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Button {
    protected int WIDTH = 337;
    protected int HEIGHT = 123;
    private int x,y;
    BufferedImage image[];
    RefLinks refLinks;

    public boolean ButtonClicked()
    {
        if(refLinks.GetMouseManager().GetClickMouseX() >= x && refLinks.GetMouseManager().GetClickMouseX() <= x + WIDTH
                && refLinks.GetMouseManager().GetClickMouseY() >= y && refLinks.GetMouseManager().GetClickMouseY()<= y+ HEIGHT)
            return true;
        else
            return false;
    }
//    public Button(String type,RefLinks refLinks,int x, int y)
//    {
//        this.x=x;
//        this.y=y;
//        this.refLinks=refLinks;
//        image= new BufferedImage[2];
//        SpriteSheet spriteSheet= new SpriteSheet( ImageLoader.LoadImage("/"+type+".png"));
//        for(int j=0;j<2;j++)
//            image[j]=spriteSheet.crop(j,0,WIDTH,HEIGHT);
//
//    }

    public Button(String type,RefLinks refLinks,int x,int y,int width,int height)
    {
        //this.WIDTH=width;
        //this.HEIGHT=height;
        this.x=x;
        this.y=y;
        this.refLinks=refLinks;
        image= new BufferedImage[2];
        SpriteSheet spriteSheet= new SpriteSheet( ImageLoader.LoadImage("/"+type+".png"));
        for(int j=0;j<2;j++)
            image[j]=spriteSheet.crop(j,0,width,height);
        this.WIDTH=width;
        this.HEIGHT=height;

    }

    public void draw(Graphics g)
    {
        if(refLinks.GetMouseManager().GetHoverMouseX() >= x && refLinks.GetMouseManager().GetHoverMouseX() <= x + WIDTH
        && refLinks.GetMouseManager().GetHoverMouseY() >= y && refLinks.GetMouseManager().GetHoverMouseY()<= y+ HEIGHT)
        g.drawImage(image[1],x,y,WIDTH,HEIGHT,null);
        else
            g.drawImage(image[0],x,y,WIDTH,HEIGHT,null);
    }


}
