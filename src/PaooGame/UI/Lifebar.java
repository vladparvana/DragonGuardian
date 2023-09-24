package PaooGame.UI;

import PaooGame.Graphics.ImageLoader;
import PaooGame.Graphics.SpriteSheet;
import PaooGame.RefLinks;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Lifebar {
    private int x;
    private int y;
    private int width;
    private int height;
    BufferedImage image[];
    BufferedImage currentImage;
    RefLinks refLinks;

    public Lifebar(int x ,int y,RefLinks refLinks) {
        this.refLinks = refLinks;
        this.x=x;
        this.y=y;
        width= 270;
        height = 38;
        image= new BufferedImage[6];
        SpriteSheet spriteSheet= new SpriteSheet( ImageLoader.LoadImage("/lifebar2.png"));
        for(int j=0;j<6;j++)
            image[j]=spriteSheet.crop(0,j,width,height);
    }

    public void Draw(Graphics g) {

        g.drawImage(currentImage,x,y,width,height,null);

    }

    public void Update() {
        switch(refLinks.GetHero().GetLife()) {
            case 10 : currentImage = image[0];
                break;
            case 8 : currentImage = image[1];
                break;
            case 6 : currentImage = image[2];
                break;
            case 4 : currentImage = image[3];
                break;
            case 2 : currentImage = image[4];
                break;
            case 0 : currentImage = image[5];
                break;
        }

    }
}
