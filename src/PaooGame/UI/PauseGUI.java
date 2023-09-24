package PaooGame.UI;

import PaooGame.DBManager.DBManager;
import PaooGame.Graphics.ImageLoader;
import PaooGame.RefLinks;
import PaooGame.States.PlayState;

import java.awt.*;
import java.awt.image.BufferedImage;

public class PauseGUI {
    BufferedImage menu;
    BufferedImage pauseLogo;
    Button buttonContinue;
    Button buttonSave;
    Button buttonSettings;
    BufferedImage pauseLogoRo;
    Button buttonContinueRo;
    Button buttonSaveRo;
    Button buttonSettingsRo;
    Button buttonAbout;
    Button buttonExit;
    RefLinks refLinks;
    public PauseGUI(RefLinks refLinks) {
        this.refLinks = refLinks;
        menu=ImageLoader.LoadImage("/menu.png");
        pauseLogo=ImageLoader.LoadImage("/pauseLogo.png");
        buttonContinue = new Button("continueButton",refLinks,335,170,290,64 );;
        buttonSave = new Button("saveButton",refLinks,335,240,290,64);
        buttonSettings=new Button("settingsButton",refLinks,335,310,290,64);
        buttonExit=new Button("exitButton",refLinks,495,370,62,65);
        buttonAbout=new Button("aboutButton",refLinks,400,370,62,65);
        pauseLogoRo=ImageLoader.LoadImage("/pauseLogoRo.png");
        buttonContinueRo = new Button("continueButtonRo",refLinks,335,170,290,64 );;
        buttonSaveRo = new Button("saveButtonRo",refLinks,335,240,290,64);
        buttonSettingsRo=new Button("settingsButtonRo",refLinks,335,310,290,64);
    }
    public void update() {
        if (buttonContinue.ButtonClicked()) {
            refLinks.GetMouseManager().ResetClick();
            ((PlayState) refLinks.GetGame().GetPlayState()).SetPauseFalse();
        }
        if(buttonSave.ButtonClicked()) {
            refLinks.GetMouseManager().ResetClick();
            DBManager.setLevel(((PlayState) refLinks.GetGame().GetPlayState()).GetCurrentLevel());
            DBManager.setPlayerData((int)refLinks.GetHero().GetX(),(int)refLinks.GetHero().GetY(),(int)refLinks.GetHero().GetScore(),refLinks.GetHero().GetLife());
            DBManager.deleteObjects();
            DBManager.deleteEnemies();
            DBManager.setCameraX((int)refLinks.GetCamera().GetX());
            DBManager.setCameraY((int)refLinks.GetCamera().GetY());
            refLinks.GetMap().SaveObjects();
            refLinks.GetMap().SaveEnemies();
        }
        if (buttonExit.ButtonClicked()){
            refLinks.GetMouseManager().ResetClick();
            ((PlayState) refLinks.GetGame().GetPlayState()).SetPauseFalse();
            refLinks.GetGame().SetMenuState();
        }

        if (buttonAbout.ButtonClicked()){
            refLinks.GetMouseManager().ResetClick();
            refLinks.GetGame().SetAboutState();
        }

        if(buttonSettings.ButtonClicked()) {
            refLinks.GetMouseManager().ResetClick();
            refLinks.GetGame().SetSettingsState();
        }
    }
    public void draw(Graphics g)
    {
        g.drawImage(menu,288,80,null);
        if(refLinks.GetGame().GetLanguage()=="english") {
            g.drawImage(pauseLogo, 230, 0, null);
            buttonContinue.draw(g);
            buttonSettings.draw(g);
            buttonSave.draw(g);
        }
        else
        {
            g.drawImage(pauseLogoRo, 230, 0, null);
            buttonContinueRo.draw(g);
            buttonSettingsRo.draw(g);
            buttonSaveRo.draw(g);
        }
        buttonAbout.draw(g);
        buttonExit.draw(g);
    }
}
