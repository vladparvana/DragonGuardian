package PaooGame.UI;

import PaooGame.Graphics.ImageLoader;
import PaooGame.RefLinks;
import PaooGame.States.State;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class AboutGUI {
    private BufferedImage background ;
    private BufferedImage menu;
    private BufferedImage logo;
    private BufferedImage logoRo;

    private Button buttonBack;
    private Button buttonExit;

    RefLinks refLinks;
    public AboutGUI(RefLinks refLinks){
        this.refLinks=refLinks;
        menu= ImageLoader.LoadImage("/menu.png");
        logo=ImageLoader.LoadImage("/aboutLogo.png");
        logoRo=ImageLoader.LoadImage("/aboutLogoRo.png");
        background=ImageLoader.LoadImage("/background.png");
        buttonBack=new Button("backButton",refLinks,400,370,62,65);
        buttonExit=new Button("exitButton",refLinks,495,370,62,65);
    }

    public void update() {


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
        if(refLinks.GetGame().GetLanguage()=="english")
            g.drawImage(logo,230,0,null);
        else
            g.drawImage(logoRo,230,0,null);
        buttonBack.draw(g);
        buttonExit.draw(g);

    }
}

