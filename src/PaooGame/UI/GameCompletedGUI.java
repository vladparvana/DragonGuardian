package PaooGame.UI;

import PaooGame.Graphics.ImageLoader;
import PaooGame.RefLinks;
import PaooGame.States.PlayState;

import java.awt.*;
import java.awt.image.BufferedImage;

public class GameCompletedGUI {
    BufferedImage menu;
    BufferedImage gameCompletedLogo;
    BufferedImage gameCompletedLogoRo;
    Button buttonExit;
    RefLinks refLinks;
    public GameCompletedGUI(RefLinks refLinks) {
        this.refLinks = refLinks;
        menu=ImageLoader.LoadImage("/menu.png");
        gameCompletedLogo=ImageLoader.LoadImage("/gameCompletedLogo.png");
        buttonExit=new Button("exitButton",refLinks,430,370,62,65);
        gameCompletedLogoRo=ImageLoader.LoadImage("/gameCompletedLogoRo.png");
    }
    public void update() {
        if (buttonExit.ButtonClicked()){
            refLinks.GetMouseManager().ResetClick();
            ((PlayState) refLinks.GetGame().GetPlayState()).SetPauseFalse();
            ((PlayState) refLinks.GetGame().GetPlayState()).GameNotCompleted();
            refLinks.GetGame().SetMenuState();
        }
    }
    public void draw(Graphics g)
    {
        g.drawImage(menu,288,80,null);
        if(refLinks.GetGame().GetLanguage()=="english")
            g.drawImage(gameCompletedLogo,230,0,null);
        else
            g.drawImage(gameCompletedLogoRo,230,0,null);
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
