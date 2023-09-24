package PaooGame.UI;


import PaooGame.Graphics.ImageLoader;
import PaooGame.RefLinks;
import PaooGame.States.State;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SettingGUI {
    private BufferedImage background ;
    private BufferedImage menu;
    private BufferedImage logo;
    private BufferedImage logoRo;
    private Button buttonBack;
    private Button buttonExit;
    private Button buttonEnglishLanguage;
    private Button buttonRomanianLanguage;

    RefLinks refLinks;
    public SettingGUI(RefLinks refLinks){
        this.refLinks=refLinks;
        menu= ImageLoader.LoadImage("/menu.png");
        logo=ImageLoader.LoadImage("/settingsLogo.png");
        logoRo=ImageLoader.LoadImage("/settingsLogoRo.png");
        background=ImageLoader.LoadImage("/background.png");
        buttonBack=new Button("backButton",refLinks,400,370,62,65);
        buttonExit=new Button("exitButton",refLinks,495,370,62,65);
        buttonEnglishLanguage= new Button("english",refLinks,335,170,290,64 );
        buttonRomanianLanguage= new Button("romanian",refLinks,335,170,290,64 );
    }

    public void update() {
        if(buttonRomanianLanguage.ButtonClicked() || buttonEnglishLanguage.ButtonClicked()) {
            refLinks.GetMouseManager().ResetClick();
            refLinks.GetGame().SetDifferentLanguage();
        }

        if(buttonBack.ButtonClicked())
        {
            refLinks.GetMouseManager().ResetClick();
            State.SetState(State.getPreviousState());
        }

        if (buttonExit.ButtonClicked()){
            refLinks.GetMouseManager().ResetClick();
            refLinks.GetGame().SetMenuState();
        }
    }

    public void draw(Graphics g)
    {
        g.drawImage(background,0,0,refLinks.GetWidth(),refLinks.GetHeight(),null);
        g.drawImage(menu,288,80,null);

        buttonBack.draw(g);
        buttonExit.draw(g);
        if(refLinks.GetGame().GetLanguage()=="english") {
            buttonRomanianLanguage.draw(g);
            g.drawImage(logo,230,0,null);
        }
        else {
            g.drawImage(logoRo,230,0,null);
            buttonEnglishLanguage.draw(g);
        }
    }
}

