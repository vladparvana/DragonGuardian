package PaooGame.UI;

import PaooGame.DBManager.DBManager;
import PaooGame.Graphics.ImageLoader;
import PaooGame.RefLinks;
import PaooGame.States.PlayState;

import java.awt.*;
import java.awt.image.BufferedImage;

public class MenuGUI {
    private BufferedImage background ;
    private BufferedImage menu;
    private BufferedImage logo;
    private Button buttonNewGame;
    private Button buttonExit;
    private  Button buttonLoadGame;

    private Button buttonSettings;
    private Button buttonAbout;
    private Button buttonNewGameRo;
    private  Button buttonLoadGameRo;
    private Button buttonSettingsRo;
    private RefLinks refLinks;
    public MenuGUI(RefLinks refLinks){
        this.refLinks=refLinks;
        menu=ImageLoader.LoadImage("/menu.png");
        logo=ImageLoader.LoadImage("/logo.png");
        background=ImageLoader.LoadImage("/background.png");
        buttonNewGame=new Button("newGameButton",refLinks,335,170,290,64 );
        buttonLoadGame=new Button("loadGameButton",refLinks,335,240,290,64);
        buttonSettings=new Button("settingsButton",refLinks,335,310,290,64);
        buttonExit=new Button("exitButton",refLinks,495,370,62,65);
        buttonAbout=new Button("aboutButton",refLinks,400,370,62,65);

        buttonNewGameRo=new Button("newGameButtonRo",refLinks,335,170,290,64 );
        buttonLoadGameRo=new Button("loadGameButtonRo",refLinks,335,240,290,64);
        buttonSettingsRo=new Button("settingsButtonRo",refLinks,335,310,290,64);
    }

    public void update() {
        if (buttonNewGame.ButtonClicked()) {
            refLinks.GetGame().LoadNewGame();
            refLinks.GetGame().SetPlayState();
            refLinks.GetMouseManager().ResetClick();
        }

        if (buttonLoadGame.ButtonClicked()) {
            refLinks.GetMouseManager().ResetClick();
            //refLinks.GetGame().LoadNewGame();
            ((PlayState) refLinks.GetGame().GetPlayState()).SetCurrentLevel(DBManager.getLevel());
            ((PlayState) refLinks.GetGame().GetPlayState()).LoadHero();
            refLinks.GetHero().SetX(DBManager.getPlayerX());
            refLinks.GetHero().SetY(DBManager.getPlayerY());
            refLinks.GetHero().SetScore(DBManager.getPlayerScore());
            refLinks.GetHero().SetLife(DBManager.getPlayerLife());
            refLinks.GetHero().SetXMove(0);
            refLinks.GetHero().SetYMove(0);
            ((PlayState) refLinks.GetGame().GetPlayState()).LoadMap();
            refLinks.GetMap().LoadObjectsFromDatabase();
            refLinks.GetMap().LoadEnemiesFromDatabase();
            refLinks.GetGame().SetPlayState();
            refLinks.GetCamera().setPosition(DBManager.getCameraX(), DBManager.getCameraY());
            System.out.println(DBManager.getLevel());

        }

        if (buttonExit.ButtonClicked()) {
            //refLinks.GetGame().GetGameWindow().GetWndFrame().dispose();
            refLinks.GetGame().StopGame();
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

        g.drawImage(background,0,0,refLinks.GetWidth(),refLinks.GetHeight(),null);
        g.drawImage(menu,288,80,null);
        g.drawImage(logo,230,0,null);
        if(refLinks.GetGame().GetLanguage()=="english") {
            buttonNewGame.draw(g);
            buttonSettings.draw(g);
            buttonLoadGame.draw(g);
        }
        else
        {
            buttonNewGameRo.draw(g);
            buttonSettingsRo.draw(g);
            buttonLoadGameRo.draw(g);
        }
        buttonAbout.draw(g);
        buttonExit.draw(g);
    }
}
