package PaooGame.UI;

import PaooGame.RefLinks;
import PaooGame.States.PlayState;

import java.awt.*;

public class PlayGUI {
    private RefLinks refLinks;
    private Lifebar lifebar;
    private Speedbar speedbar;

    public PlayGUI(RefLinks refLinks) {
        this.refLinks=refLinks;
        lifebar = new Lifebar(20,20,refLinks);
        speedbar=new Speedbar(refLinks.GetWidth()-290,20,refLinks);
    }

    public void Update() {
        lifebar.Update();
        speedbar.Update();
    }
    public void Draw(Graphics g) {
        lifebar.Draw(g);
        speedbar.Draw(g);
        g.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        g.setColor(Color.RED);
        g.drawString("Score: " + Integer.toString(refLinks.GetHero().GetScore()),32,72);
        g.setColor(Color.blue);
        g.drawString("Score: " + Integer.toString(refLinks.GetHero().GetScore()),31,71);
        g.setColor(Color.BLACK);
        g.drawString("Score: " + Integer.toString(refLinks.GetHero().GetScore()),30,70);
        g.setColor(Color.BLACK);
        if(((PlayState)refLinks.GetGame().GetPlayState()).GetCurrentLevel() == 1) {
            if (refLinks.GetGame().GetLanguage() == "english") {
                g.setColor(Color.blue);
                g.drawString("You need to have score 300 to unlock next level.", 251, 81);
                g.setColor(Color.BLACK);
                g.drawString("You need to have score 300 to unlock next level.", 250, 80);
            } else {
                g.setColor(Color.blue);
                g.drawString("Ai nevoie de scor 300 pentru a debloca nivelul urmator.", 251, 81);
                g.setColor(Color.BLACK);
                g.drawString("Ai nevoie de scor 300 pentru a debloca nivelul urmator.", 250, 80);
            }
        }
        else if(((PlayState)refLinks.GetGame().GetPlayState()).GetCurrentLevel() == 2) {
            if (refLinks.GetGame().GetLanguage() == "english") {
                g.setColor(Color.blue);
                g.drawString("You need to have score 1600 to unlock next level.", 251, 81);
                g.setColor(Color.BLACK);
                g.drawString("You need to have score 1600 to unlock next level.", 250, 80);
            } else {
                g.setColor(Color.blue);
                g.drawString("Ai nevoie de scor 1600 pentru a debloca nivelul urmator.", 251, 81);
                g.setColor(Color.BLACK);
                g.drawString("Ai nevoie de scor 1600 pentru a debloca nivelul urmator.", 250, 80);
            }
        }
        else if(((PlayState)refLinks.GetGame().GetPlayState()).GetCurrentLevel() == 3) {
            if (refLinks.GetGame().GetLanguage() == "english") {
                g.setColor(Color.blue);
                g.drawString("You need to have score 3900 to unlock the dragon's island.", 251, 81);
                g.setColor(Color.BLACK);
                g.drawString("You need to have score 3900 to unlock the dragon's island.", 250, 80);
            } else {
                g.setColor(Color.blue);
                g.drawString("Ai nevoie de scor 3900 pentru a debloca insula dragonului.", 251, 81);
                g.setColor(Color.BLACK);
                g.drawString("Ai nevoie de scor 3900 pentru a debloca insula dragonului.", 250, 80);
            }
        }

    }
}
