package PaooGame.Items;

import PaooGame.Graphics.ImageLoader;
import PaooGame.Graphics.SpriteSheet;
import PaooGame.RefLinks;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

import static PaooGame.Utilz.Constants.PlayerConstants.*;
import static PaooGame.Utilz.HelpMethods.Intersects;

public class Archer extends Enemy{

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
    private boolean canGetStun= false;
    private int mooving=0;




    /*! \fn public Hero(RefLinks refLink, float x, float y)
        \brief Constructorul de initializare al clasei Hero.

        \param refLink Referinta catre obiectul shortcut (obiect ce retine o serie de referinte din program).
        \param x Pozitia initiala pe axa X a eroului.
        \param y Pozitia initiala pe axa Y a eroului.
     */
    public Archer(RefLinks refLink, float x, float y)
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
        SpriteSheet spriteSheet= new SpriteSheet(ImageLoader.LoadImage("/Archer.png"));
        for(int x=0;x<4;x++)
            for(int y=0;y<4;y++)
            {
                animations[x][y]=spriteSheet.crop(y,x,32,32);
            }
        attackAnimations= new BufferedImage[4][4];
        spriteSheet= new SpriteSheet(ImageLoader.LoadImage("/Archer_Attack.png"));
        for(int x=0;x<4;x++)
            for(int y=0;y<4;y++)
            {
                attackAnimations[x][y]=spriteSheet.crop(y,x,32,32);
            }
        currentAnimations=animations;
    }

    private void updateAnimationTick() {

        //System.out.println(playerAction);
        aniTick ++;
        if(aniTick>=aniSpeed) {
            aniTick =0;
            aniIndex++;
            if(currentAnimations==attackAnimations)
                mooving=0;
            if(aniIndex>=4-mooving)
            {
                if(currentAnimations==attackAnimations)
                {
                    SetNormalAnimation();
                }
                aniIndex = 0;

            }

        }
    }
    /*! \fn public void Update()
        \brief Actualizeaza pozitia si imaginea eroului.
     */
    @Override
    public void Update() {
        long currentTime = System.currentTimeMillis();
        long timeSinceLastMove = currentTime - timeMoved;
        int playerX = (int) refLink.GetHero().GetX() + (int) refLink.GetCamera().GetX();
        int playerY = (int) refLink.GetHero().GetY() + (int) refLink.GetCamera().GetY();
        int wolfX = (int) x;
        int wolfY = (int) y;

        int dx = playerX - wolfX;
        int dy = playerY - wolfY;


            if (Math.abs(dx) < 200 && Math.abs(dy) < 200) {
                mooving=2;
                attackCooldown++;
                if (canAttack()) {
                    SetAttackAnimation();
                    switch (playerAction) {
                        case 1:
                            refLink.GetMap().AddArrow("left", (int) (GetX()  - 32), (int) (GetY() ));
                            break;
                        case 0:
                            refLink.GetMap().AddArrow("right", (int) (GetX()  + 16), (int) (GetY() ));
                            break;
                        case 3:
                            refLink.GetMap().AddArrow("up", (int) (GetX()), (int) (GetY()  - 32));
                            break;
                        case 2:
                            refLink.GetMap().AddArrow("down", (int) (GetX() ), (int) (GetY()  + 16));
                            break;
                    }
                }
            }

         else {
            SetNormalAnimation();
            attackCooldown = 0;
            IsNotAttacked();



            if (Math.abs(dx) < 400 && Math.abs(dy) < 400) {
                // Adjust the wolf's position based on the direction
                mooving = 0;
                if (Math.abs(dx) > Math.abs(dy)) {
                    // Move horizontally
                    if (dx > 0) {
                        xMove = speed;
                        playerAction = 0;
                    } else {
                        xMove = -speed;
                        playerAction = 1;
                    }
                    yMove = 0;
                } else {
                    // Move vertically
                    if (dy > 0) {
                        yMove = speed;
                        playerAction = 2;
                    } else {
                        yMove = -speed;
                        playerAction = 3;
                    }
                    xMove = 0;
                }

            } else {
                mooving = 2;
                xMove = 0;
                yMove = 0;
            }
            // Update the wolf's position
            if (timeSinceLastMove < 1000) {
                Move();
            } else if (timeSinceLastMove > 1000) {
                timeMoved = currentTime;
            }

        }

    }

    /*! \fn private void GetInput()
        \brief Verifica daca a fost apasata o tasta din cele stabilite pentru controlul eroului.
     */


    /*! \fn public void Draw(Graphics g)
        \brief Randeaza/deseneaza eroul in noua pozitie.

        \brief g Contextul grafi in care trebuie efectuata desenarea eroului.
     */
    @Override
    public void Draw(Graphics g)
    {
        updateAnimationTick();
        //if(currentAnimations == attackAnimations)
         //   g.drawImage(attackAnimations[playerAction][aniIndex], (int)x-(int)refLink.GetCamera().GetX()-8, (int)y-(int)refLink.GetCamera().GetY()-4, (int)(width*1.50), (int)(height*1.50), null);
        //else
            g.drawImage(currentAnimations[playerAction][aniIndex], (int)x-(int)refLink.GetCamera().GetX()-8, (int)y-(int)refLink.GetCamera().GetY()-4, (int)(width*1.50), (int)(height*1.50), null);

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
        attackMode=true;
    }

    @Override
    public void SetNormalAnimation(){
        attackMode=false;
        currentAnimations=animations;
    }

    @Override
    public boolean canAttack() {
        if(attackCooldown>=50) {
            attackCooldown=0;
            return true;
        }
        else
            return false;
    }


}


