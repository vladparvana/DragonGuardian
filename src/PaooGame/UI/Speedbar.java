package PaooGame.UI;

import PaooGame.Graphics.ImageLoader;
import PaooGame.Graphics.SpriteSheet;
import PaooGame.RefLinks;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Speedbar {

    private int x;
    private int y;
    private int width;
    private int height;
    BufferedImage image[];
    BufferedImage currentImage;
    RefLinks refLinks;

    public Speedbar(int x, int y, RefLinks refLinks) {
        this.refLinks = refLinks;
        this.x = x;
        this.y = y;
        width = 270;
        height = 38;
        image = new BufferedImage[6];
        SpriteSheet spriteSheet = new SpriteSheet(ImageLoader.LoadImage("/speedbar.png"));
        for (int j = 0; j < 6; j++)
            image[j] = spriteSheet.crop(0, j, width, height);
    }

    public void Draw(Graphics g) {

        if(currentImage!= image[5])
        g.drawImage(currentImage, x, y, width, height, null);

    }

    public void Update() {
        if(refLinks.GetHero().GetSpeedPotionTime()<1000)
                currentImage = image[0];
        else if(refLinks.GetHero().GetSpeedPotionTime()<2000)
            currentImage = image[1];
        else if (refLinks.GetHero().GetSpeedPotionTime()<3000)
                currentImage = image[2];
        else if (refLinks.GetHero().GetSpeedPotionTime()<4000)
                currentImage = image[3];
        else if (refLinks.GetHero().GetSpeedPotionTime()<5000)
                currentImage = image[4];
        else {
            refLinks.GetHero().SetDefaultSpeed();
            currentImage = image[5];
        }
    }
}
