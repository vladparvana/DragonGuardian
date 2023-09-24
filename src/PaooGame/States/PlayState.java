package PaooGame.States;

import PaooGame.Camera.Camera;
import PaooGame.Items.Hero;
import PaooGame.RefLinks;
import PaooGame.Maps.Map;
import PaooGame.UI.*;

import java.awt.*;

/*! \class public class PlayState extends State
    \brief Implementeaza/controleaza jocul.
 */
public class PlayState extends State
{
    private Hero hero;  /*!< Referinta catre obiectul animat erou (controlat de utilizator).*/
    private Map map;    /*!< Referinta catre harta curenta.*/

    private PlayGUI playGUI;
    private PauseGUI pauseGUI;
    private LevelCompletedGUI levelCompletedGUI;
    private GameLostGUI gameLostGUI;
    private boolean pause;

    private boolean levelCompleted;
    private int currentLevel;
    private boolean updateLevel=false;

    private boolean gameLost=false;

    private boolean gameCompleted= false;
    private GameCompletedGUI gameCompletedGUI;
    /*! \fn public PlayState(RefLinks refLink)
        \brief Constructorul de initializare al clasei

        \param refLink O referinta catre un obiect "shortcut", obiect ce contine o serie de referinte utile in program.
     */
    public PlayState(RefLinks refLink)
    {
            ///Apel al constructorului clasei de baza
        super(refLink);
        currentLevel=1;
        //Construieste harta jocului

        LoadMap();
        LoadHero();

        playGUI= new PlayGUI(refLink);
            ///Referinta catre harta construita este setata si in obiectul shortcut pentru a fi accesibila si in alte clase ale programului.
        refLink.SetMap(map);
        refLink.SetHero(hero);
        pauseGUI= new PauseGUI(refLink);
        levelCompletedGUI=new LevelCompletedGUI(refLink);
        gameLostGUI=new GameLostGUI(refLink);
        gameCompletedGUI=new GameCompletedGUI(refLink);
        pause=false;
        levelCompleted=false;
        gameLost=false;
        gameCompleted=false;
            ///Construieste eroul


    }

    /*! \fn public void Update()
        \brief Actualizeaza starea curenta a jocului.
     */
    @Override
    public void Update()
    {

        if(updateLevel==true)
        {
            refLink.GetMap().SetLevelOngoing();
            LoadNextLevel();
            updateLevel=false;
        }
        levelCompleted=refLink.GetMap().GetLevelProgress();
        if(refLink.GetKeyManager().escape==true && pause == false) {
            System.out.println("pause");
            pause=true;
        }
        if(pause==true) {
            pauseGUI.update();
        }
        else if(levelCompleted==true)
        {
            levelCompletedGUI.update();
        }
        else if(gameLost==true)
            {
                gameLostGUI.update();
            }
        else if (gameCompleted==true) {
            gameCompletedGUI.update();
        }
        else
        {
            refLink.GetCamera().Update((double) hero.GetX(), (double) hero.GetY(), refLink.GetWidth(), refLink.GetHeight(), map.GetMapWidth(), map.GetMapHeight());
            hero.Update();
            playGUI.Update();
            map.Update();
        }

    }

    private void LoadNextLevel() {
        int temp=hero.GetScore();
        hero.SetX(100);
        hero.SetY(100);
        //hero = new Hero(refLink, 48, 48);
        map = new Map(refLink, currentLevel+1);
        currentLevel++;
        refLink.SetMap(map);
        refLink.SetHero(hero);
        hero.SetScore(temp);
    }

    /*! \fn public void Draw(Graphics g)
        \brief Deseneaza (randeaza) pe ecran starea curenta a jocului.

        \param g Contextul grafic in care trebuie sa deseneze starea jocului pe ecran.
     */
    @Override
    public void Draw(Graphics g)
    {

        refLink.GetCamera().Apply(g);
        map.Draw(g);
        hero.Draw(g);
        refLink.GetCamera().Reset(g);
        playGUI.Draw(g);
        if(pause==true) {
            pauseGUI.draw(g);
        }
        if(levelCompleted==true)
        {
            levelCompletedGUI.draw(g);
        }
        if(gameLost==true)
        {
            gameLostGUI.draw(g);
        }
        if(gameCompleted==true)
        {
            gameCompletedGUI.draw(g);
        }
    }

    public void SetPauseFalse() { pause=false;}

    public void UpdateLevel() {updateLevel=true;}

    public int GetCurrentLevel() {return currentLevel;}

    public void SetCurrentLevel(int level) { this.currentLevel=level;}

    public void LoadMap() {map = new Map(refLink, currentLevel);
        refLink.SetMap(map);
    }

    public void LoadHero() {hero = new Hero(refLink, 48, 48);
        refLink.SetHero(hero);}

    public void GameLost(){gameLost=true;}

    public void GameNotLost() {gameLost=false;}

    public void GameNotCompleted() {gameCompleted=false;}

    public void GameCompleted() {gameCompleted=true;}

}
