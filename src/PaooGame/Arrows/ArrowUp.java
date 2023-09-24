package PaooGame.Arrows;

import PaooGame.Graphics.ImageLoader;
import PaooGame.Graphics.SpriteSheet;
import PaooGame.Items.Enemy;
import PaooGame.RefLinks;

import java.awt.*;
import java.awt.image.BufferedImage;

import static PaooGame.Utilz.HelpMethods.*;

public class ArrowUp extends Arrow{
    private BufferedImage image;
    public ArrowUp(RefLinks refLinks, int x, int y) {
        super(refLinks, x, y);
        SpriteSheet spriteSheet = new SpriteSheet(ImageLoader.LoadImage("/Arrow_up.png"));
        image = spriteSheet.crop(0, 0, 32, 32);
        bounds = new Rectangle(0, 0, 0, 0);
        bounds.setLocation(16, 16);
        bounds.setSize(OBJECT_WIDTH, OBJECT_HEIGHT);
    }

    @Override
    public  void Update() {
        if(Intersects(refLinks.GetHero(),this,refLinks)) {
            refLinks.GetHero().SetLife(refLinks.GetHero().GetLife() - 2);
            alive=false;
        }
        for(Enemy enemy: refLinks.GetMap().GetEnemies())
        {
            if(Intersects(enemy,this))
            {
                enemy.SetLife(enemy.GetLife()-2);
                System.out.println(enemy.GetLife());
                alive=false;
            }
        }
        if(!CanArrowMoveHere(x+bounds.x,y-8+bounds.y,bounds.width,bounds.height,refLinks))
            alive=false;
        y=y-8;
    }
    public  void Draw(Graphics g){
        g.drawImage(image,x+bounds.x-(int)refLinks.GetCamera().GetX(),y+bounds.y-(int)refLinks.GetCamera().GetY(),16,16,null);
        g.setColor(Color.blue);
       // g.drawRect( x+bounds.x -(int)refLinks.GetCamera().GetX(),  y+bounds.y-(int)refLinks.GetCamera().GetY()  , 16, 16);
    }
}
