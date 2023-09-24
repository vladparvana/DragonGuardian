package PaooGame.UI;

import PaooGame.DBManager.DBManager;
import PaooGame.Graphics.ImageLoader;
import PaooGame.RefLinks;
import PaooGame.States.PlayState;

import java.awt.*;
import java.awt.image.BufferedImage;

public class GameLostGUI {
    BufferedImage menu;
    BufferedImage gameLostLogo;
    BufferedImage gameLostLogoRo;
    Button buttonExit;
    RefLinks refLinks;
    public GameLostGUI(RefLinks refLinks) {
        this.refLinks = refLinks;
        menu=ImageLoader.LoadImage("/menu.png");
        gameLostLogo=ImageLoader.LoadImage("/gameLostLogo.png");
        buttonExit=new Button("exitButton",refLinks,430,370,62,65);
        gameLostLogoRo=ImageLoader.LoadImage("/gameLostLogoRo.png");
    }
    public void update() {
        if (buttonExit.ButtonClicked()){
            refLinks.GetMouseManager().ResetClick();
            ((PlayState) refLinks.GetGame().GetPlayState()).SetPauseFalse();
            ((PlayState) refLinks.GetGame().GetPlayState()).GameNotLost();
            refLinks.GetGame().SetMenuState();
        }
    }
    public void draw(Graphics g)
    {
        g.drawImage(menu,288,80,null);
        if(refLinks.GetGame().GetLanguage()=="english")
            g.drawImage(gameLostLogo,230,0,null);
        else
            g.drawImage(gameLostLogoRo,230,0,null);
        if(refLinks.GetGame().GetLanguage()=="english") {
            g.setColor(Color.yellow);
            g.drawString("Score: "+ Integer.toString(refLinks.GetHero().GetScore()), 451, 231);
            g.setColor(Color.BLACK);
            g.drawString("Score: "+ Integer.toString(refLinks.GetHero().GetScore()), 450, 230);
        }
        else {
            g.setColor(Color.yellow);
            g.drawString("Scor: "+ Integer.toString(refLinks.GetHero().GetScore()), 451, 231);
            g.setColor(Color.BLACK);
            g.drawString("Scor: "+ Integer.toString(refLinks.GetHero().GetScore()), 450, 230);
        }
        buttonExit.draw(g);
    }
}
