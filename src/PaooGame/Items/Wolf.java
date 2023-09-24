package PaooGame.Items;


import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

import PaooGame.Camera.Camera;
import PaooGame.Graphics.ImageLoader;
import PaooGame.Graphics.SpriteSheet;
import PaooGame.RefLinks;

import static PaooGame.Utilz.Constants.PlayerConstants.*;
import static PaooGame.Utilz.HelpMethods.Intersects;

/*! \class public class Hero extends Character
    \brief Implementeaza notiunea de erou/player (caracterul controlat de jucator).

    Elementele suplimentare pe care le aduce fata de clasa de baza sunt:
        imaginea (acest atribut poate fi ridicat si in clasa de baza)
        deplasarea
        atacul (nu este implementat momentan)
        dreptunghiul de coliziune
 */
public class Wolf extends Enemy
{
    private BufferedImage image;    /*!< Referinta catre imaginea curenta a eroului.*/
    private BufferedImage animations [][] ;
    private BufferedImage attackAnimations [][];
    private BufferedImage currentAnimations [][];

    private int aniTick, aniIndex, aniSpeed = 8;
    private int playerAction = UP_IDLE;

    private boolean moving = false;
    private Random random;
    private int range = 4;
    private int direction;
    private long directionChangeTime; // Timestamp of the last direction change
    private long directionChangeDelayTime; // Timestamp of the last direction change delay
    private boolean shouldChangeDirection = true;
    private long timeMoved ;
    private int hlp_aniTick;
    private boolean attackMode=false;
    private long attackCooldown;
    private boolean canGetStun= true;




    /*! \fn public Hero(RefLinks refLink, float x, float y)
        \brief Constructorul de initializare al clasei Hero.

        \param refLink Referinta catre obiectul shortcut (obiect ce retine o serie de referinte din program).
        \param x Pozitia initiala pe axa X a eroului.
        \param y Pozitia initiala pe axa Y a eroului.
     */
    public Wolf(RefLinks refLink, float x, float y)
    {
        ///Apel al constructorului clasei de baza
        super(refLink, x,y, Character.DEFAULT_CREATURE_WIDTH, Character.DEFAULT_CREATURE_HEIGHT);

        //refLink.GetGame().getCamera().Update((double)x,(double)y,refLink.GetWidth(),refLink.GetHeight(),refLink.GetMap().GetMapWidth(),refLink.GetMap().GetMapHeight());
        ///Seteaza imaginea de start a eroului

        ///Stabilieste pozitia relativa si dimensiunea dreptunghiului de coliziune, starea implicita(normala)
        SetHeight(32);
        SetWidth(32);
        normalBounds.setLocation(0,0);
        normalBounds.setSize(32,32);

        visualBounds.setLocation(13,20);
        visualBounds.setSize(5,5);

        ///Stabilieste pozitia relativa si dimensiunea dreptunghiului de coliziune, starea de atac
        attackBounds.setLocation(0,0);
        attackBounds.setSize(32,32);

        InitAnimations();
        image = animations[0][0];
        directionChangeTime = System.currentTimeMillis();
        directionChangeDelayTime = System.currentTimeMillis();
        cooldown=System.currentTimeMillis();
        timeMoved=System.currentTimeMillis();
        attackCooldown=System.currentTimeMillis();
        random = new Random();
    }

    private void InitAnimations() {
        animations=new BufferedImage[16][4];
        SpriteSheet spriteSheet= new SpriteSheet(ImageLoader.LoadImage("/Wolf.png"));
        for(int x=0;x<14;x++)
            for(int y=0;y<4;y++)
            {
                animations[x][y]=spriteSheet.crop(y,x,16,16);
            }
        attackAnimations= new BufferedImage[4][4];
        spriteSheet= new SpriteSheet(ImageLoader.LoadImage("/Wolf_Attack.png"));
        for(int x=0;x<4;x++)
            for(int y=0;y<4;y++)
            {
                attackAnimations[x][y]=spriteSheet.crop(y,x,24,24);
            }
        currentAnimations=animations;
    }

    private void updateAnimationTick() {

        //System.out.println(playerAction);
        aniTick ++;
        if(aniTick>=aniSpeed) {
            aniTick =0;
            aniIndex++;
            if(aniIndex>=4)
                aniIndex=0;
        }
    }
    /*! \fn public void Update()
        \brief Actualizeaza pozitia si imaginea eroului.
     */
    @Override
    public void Update()
    {
        //x =x+(int)refLink.GetGame().getCamera().GetX();
        //y=y+(int)refLink.GetGame().getCamera().GetY();
        ///Verifica daca a fost apasata o tasta
        long currentTime = System.currentTimeMillis();
        long timeSinceDirectionChange = currentTime - directionChangeTime;
        long timeSinceLastMove = currentTime-timeMoved;
        //long timeSinceDirectionChangeDelay = currentTime - directionChangeDelayTime;

        if(((isAttacked==true && (currentTime-cooldown)>=3000) || isAttacked==false || canGetStun == false)) {
            if (Intersects(refLink.GetHero(), this, refLink)) {
                if (canAttack()) {
                    refLink.GetHero().SetLife(refLink.GetHero().GetLife()-2);
                    System.out.println("viata -2");
                }
                SetAttackAnimation();
            } else {
                SetNormalAnimation();
                attackCooldown = 0;
                IsNotAttacked();
                if (timeSinceDirectionChange >= 2000) { // If 2 seconds have passed since last direction change
                    shouldChangeDirection = true;
                    directionChangeTime = currentTime;
                }

//        if (timeSinceDirectionChangeDelay >= 1000) { // If 1 second has passed since last direction change delay
//            shouldChangeDirection = true;
//            directionChangeDelayTime = currentTime;
//        }

                if (shouldChangeDirection) {
                    direction = GetInput();
                    shouldChangeDirection = false;
                }
                ///Actualizeaza pozitia
                if (timeSinceLastMove < 500) {
                    Move();
                } else if (timeSinceLastMove > 2000)
                    timeMoved = currentTime;


                ///Actualizeaza imaginea
                //playerAction=IDLE;
                if (direction == 2) {
                    playerAction = 1;
                } else if (direction == 0) {
                    playerAction = 3;
                } else if (direction == 1) {
                    playerAction = 2;
                } else if (direction == 3) {
                    playerAction = 0;
                }
            }
        }
        else
            SetNormalAnimation();

        //else playerAction=PlayerIdle(playerAction);

        //moving=refLink.GetKeyManager().moving;
        //if(moving == false)
        //layerAction=PlayerIdle(playerAction);
    }

    /*! \fn private void GetInput()
        \brief Verifica daca a fost apasata o tasta din cele stabilite pentru controlul eroului.
     */
    private int GetInput()
    {
        ///Implicit eroul nu trebuie sa se deplaseze daca nu este apasata o tasta
        xMove = 0;
        yMove = 0;
        int randomNumber = random.nextInt(range)+0;
        //randomNumber=0;
        //       yMove = -speed;
//            return 0;
        //Verificare apasare tasta "sus"
        if(randomNumber == 0)
        {
            yMove = -speed;
            return 0;
        }
        ///Verificare apasare tasta "jos"
        if(randomNumber == 1)
        {
            yMove = speed;
            return 1;
        }
        ///Verificare apasare tasta "left"
        if(randomNumber == 2)
        {
            xMove = -speed;
            return 2;
        }
        ///Verificare apasare tasta "dreapta"
        if(randomNumber == 3)
        {
            xMove = speed;
            return 3;
        }
        return 0;

    }

    /*! \fn public void Draw(Graphics g)
        \brief Randeaza/deseneaza eroul in noua pozitie.

        \brief g Contextul grafi in care trebuie efectuata desenarea eroului.
     */
    @Override
    public void Draw(Graphics g)
    {
        updateAnimationTick();
        if(currentAnimations == attackAnimations)
            g.drawImage(attackAnimations[playerAction][aniIndex], (int)x-(int)refLink.GetCamera().GetX()-8, (int)y-(int)refLink.GetCamera().GetY()-4, (int)(width*1.50), (int)(height*1.50), null);
        else
            g.drawImage(currentAnimations[playerAction][aniIndex], (int)x-(int)refLink.GetCamera().GetX(), (int)y-(int)refLink.GetCamera().GetY(), width, height, null);

        ///doar pentru debug daca se doreste vizualizarea dreptunghiului de coliziune altfel se vor comenta urmatoarele doua linii
        g.setColor(Color.blue);
        //g.drawRect((int)(x + bounds.x-(int)refLink.GetCamera().GetX()), (int)(y + bounds.y-(int)refLink.GetCamera().GetY()), width, height);
    }

    @Override
    public void SetAttackAnimation(){

        int playerX = (int) refLink.GetHero().GetX() +(int) refLink.GetCamera().GetX();
        int playerY = (int) refLink.GetHero().GetY() +(int) refLink.GetCamera().GetY();
        int wolfX = (int) x;
        int wolfY = (int) y;

        int dx = playerX - wolfX;
        int dy = playerY - wolfY;

        // Adjust the wolf's position based on the direction
        if (Math.abs(dx) > Math.abs(dy)) {
            // Move horizontally
            if (dx > 0) {
                playerAction = 0;
            } else {
                playerAction = 1;
            }
        } else {
            // Move vertically
            if (dy > 0) {
                playerAction = 2;
            } else {
                playerAction = 3;
            }
        }
//        if (refLink.GetHero().GetPlayerAction() == LEFT || refLink.GetHero().GetPlayerAction() == LEFT_ATTACK) {
//            playerAction = 0;
//        } else if (refLink.GetHero().GetPlayerAction() == RIGHT || refLink.GetHero().GetPlayerAction() == RIGHT_ATTACK) {
//            playerAction = 1;
//        } else if (refLink.GetHero().GetPlayerAction() == UP || refLink.GetHero().GetPlayerAction() == UP_ATTACK) {
//            playerAction = 3;
//        } else if (refLink.GetHero().GetPlayerAction() == DOWN || refLink.GetHero().GetPlayerAction() == DOWN_ATTACK) {
//            playerAction = 2;
//        }
        currentAnimations=attackAnimations;
        attackCooldown++;
        attackMode=true;
    }

    @Override
    public void SetNormalAnimation(){
        attackMode=false;
        currentAnimations=animations;
    }

    @Override
    public boolean canAttack() {
        if(attackCooldown>=100) {
            attackCooldown=0;
            return true;
        }
        else
            return false;
    }


}
