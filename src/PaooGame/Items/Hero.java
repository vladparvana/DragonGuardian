package PaooGame.Items;

import java.awt.*;
import java.awt.image.BufferedImage;

import PaooGame.Camera.Camera;
import PaooGame.Exceptions.BowNotAvailable;
import PaooGame.Graphics.ImageLoader;
import PaooGame.Graphics.SpriteSheet;
import PaooGame.Objects.*;
import PaooGame.Objects.Object;
import PaooGame.RefLinks;
import PaooGame.States.PlayState;

import static PaooGame.Utilz.Constants.PlayerConstants.*;
import static PaooGame.Utilz.HelpMethods.Collision;
import static PaooGame.Utilz.HelpMethods.Intersects;

/*! \class public class Hero extends Character
    \brief Implementeaza notiunea de erou/player (caracterul controlat de jucator).

    Elementele suplimentare pe care le aduce fata de clasa de baza sunt:
        imaginea (acest atribut poate fi ridicat si in clasa de baza)
        deplasarea
        atacul (nu este implementat momentan)
        dreptunghiul de coliziune
 */
public class Hero extends Character
{
    private BufferedImage image;    /*!< Referinta catre imaginea curenta a eroului.*/
    private BufferedImage animations [][] ;

    private int aniTick, aniIndex, aniSpeed = 8;
    private int playerAction = UP_IDLE;

    private boolean moving = false;

    private long lastAttackTime;  // Variable to store the time of the last attack
    private long lastShootTime;
    private long ATTACK_DELAY = 1000; // Delay in milliseconds (1 second)

    private long ATTACK_DELAY_SPEED = 500;
    private long currentAttackDelay;
    private int attackAniIndex;
    private boolean attackAnimationComplete = true;

    private boolean shootAnimationComplete=true;
    private int shootAniIndex;

    private long speedPotionTime=0;
    private int score;

    private long speedPotion;





    /*! \fn public Hero(RefLinks refLink, float x, float y)
        \brief Constructorul de initializare al clasei Hero.

        \param refLink Referinta catre obiectul shortcut (obiect ce retine o serie de referinte din program).
        \param x Pozitia initiala pe axa X a eroului.
        \param y Pozitia initiala pe axa Y a eroului.
     */
    public Hero(RefLinks refLink, float x, float y)
    {
            ///Apel al constructorului clasei de baza\
        super(refLink, x,y, Character.DEFAULT_CREATURE_WIDTH, Character.DEFAULT_CREATURE_HEIGHT);

        this.score=0;
        //refLink.GetGame().getCamera().Update((double)x,(double)y,refLink.GetWidth(),refLink.GetHeight(),refLink.GetMap().GetMapWidth(),refLink.GetMap().GetMapHeight());
            ///Seteaza imaginea de start a eroului

            ///Stabilieste pozitia relativa si dimensiunea dreptunghiului de coliziune, starea implicita(normala)
        //SetHeight(32);
        //SetWidth(32);
        normalBounds.setLocation(10,10);
        normalBounds.setSize(28,30);

        visualBounds.setLocation(20,20);
        visualBounds.setSize(8,8);


            ///Stabilieste pozitia relativa si dimensiunea dreptunghiului de coliziune, starea de atac
//        attackBounds.x = 20;
//        attackBounds.y = 30;
//        attackBounds.width = 38;
//        attackBounds.height = 60;
        attackBounds.setLocation(0,0);
        attackBounds.setSize(48,48);
        InitAnimations();
        image = animations[0][0];
        speedPotion=System.currentTimeMillis();
        currentAttackDelay=ATTACK_DELAY;
    }

    private void InitAnimations() {
        animations=new BufferedImage[16][4];
        SpriteSheet spriteSheet= new SpriteSheet(ImageLoader.LoadImage("/player_final.png"));
        for(int x=0;x<16;x++)
            for(int y=0;y<4;y++)
            {
                animations[x][y]=spriteSheet.crop(y,x,32,32);
            }
    }

    private void updateAnimationTick() {

        //System.out.println(playerAction);

        aniTick ++;
        if(aniTick>=aniSpeed) {
            aniTick =0;
            aniIndex++;
            if(aniIndex>=GetSpriteAmount(playerAction,moving))
                aniIndex=0;
            if(attackAnimationComplete==false)
                attackAniIndex++;
            else
            if(shootAnimationComplete==false)
                shootAniIndex++;
            if(attackAniIndex==4) {
                attackAniIndex=0;
                playerAction=PlayerNoAttack(playerAction);
                attackAnimationComplete = true;
            }
            else
            if(shootAniIndex==4) {
                shootAniIndex=0;
                playerAction=PlayerNoAttack(playerAction);
                shootAnimationComplete=true;
                aniSpeed=15;
            }
        }

    }
    /*! \fn public void Update()
        \brief Actualizeaza pozitia si imaginea eroului.
     */
    @Override
    public void Update()
    {
        if(life<=0)
        {
            ((PlayState)refLink.GetGame().GetPlayState()).GameLost();
        }

        else {
            if(refLink.GetKeyManager().u == true)
                SetLife(10);
            if(refLink.GetKeyManager().i == true)
                AddScore(100);
            if (refLink.GetKeyManager().g == true && canShoot()) {
                try {
                    if (((PlayState) refLink.GetGame().GetPlayState()).GetCurrentLevel() < 2)
                        throw new BowNotAvailable();
                    shootAnimationComplete = false;
                    lastShootTime = System.currentTimeMillis();
                    playerAction = PlayerShoot(playerAction);
                    shootAniIndex = 0;
                    aniSpeed = 5;
                    switch (playerAction) {
                        case LEFT_SHOOT:
                            refLink.GetMap().AddArrow("left", (int) (GetX() + refLink.GetCamera().GetX() - 30), (int) (GetY() + refLink.GetCamera().GetY()));
                            break;
                        case RIGHT_SHOOT:
                            refLink.GetMap().AddArrow("right", (int) (GetX() + refLink.GetCamera().GetX() + 30), (int) (GetY() + refLink.GetCamera().GetY()));
                            break;
                        case DOWN_SHOOT:
                            refLink.GetMap().AddArrow("up", (int) (GetX() + refLink.GetCamera().GetX()), (int) (GetY() + refLink.GetCamera().GetY() - 30));
                            break;
                        case UP_SHOOT:
                            refLink.GetMap().AddArrow("down", (int) (GetX() + refLink.GetCamera().GetX()), (int) (GetY() + refLink.GetCamera().GetY() + 30));
                            break;
                    }
                } catch (BowNotAvailable e) {
                    System.out.println(e.getMessage());
                }
            }


            long currentTime = System.currentTimeMillis();
            if (speedPotionTime != 0)
                speedPotionTime = currentTime - speedPotion;
            if (speedPotionTime > 5000) {
                currentAttackDelay = ATTACK_DELAY;
                aniSpeed = 8;
            }
            //SetAttackMode();
            ///Verifica daca a fost apasata o tasta
            GetInput();

            ///Actualizeaza pozitia

            ///Actualizeaza imaginea
            //playerAction=IDLE;
            if (attackAnimationComplete == true && shootAnimationComplete == true) {
                if (refLink.GetKeyManager().left == true) {
                    playerAction = LEFT;
                } else if (refLink.GetKeyManager().up == true) {
                    playerAction = DOWN;
                } else if (refLink.GetKeyManager().down == true) {
                    playerAction = UP;
                } else if (refLink.GetKeyManager().right == true) {
                    playerAction = RIGHT;
                } //else playerAction=PlayerIdle(playerAction);

                moving = refLink.GetKeyManager().moving;
            }
            //if(moving == false)
            //playerAction=PlayerIdle(playerAction);
            if (refLink.GetKeyManager().space == true && canAttack()) {
                attackAnimationComplete = false;
                lastAttackTime = System.currentTimeMillis();
                attackAniIndex = 0;
                SetAttackMode();
                playerAction = PlayerAttack(playerAction);
                for (Enemy enemy : refLink.GetMap().GetEnemies()) {
                    if (Intersects(this, enemy, refLink)) {
                        enemy.IsAttacked();
                        enemy.SetLife(enemy.GetLife() - 2);
                        System.out.println(enemy.GetLife());
                    } else
                        enemy.IsNotAttacked();
                }

            } else {

                SetNormalMode();
            }
            for (Object object : refLink.GetMap().GetObjects())
                if (Intersects(this, object, refLink)) {
                    if (object instanceof Chicken) {
                        life += object.GetLife();
                        if (life > 10)
                            life = 10;
                    }
                    if (object instanceof Potion) {
                        if (speed != 4) {
                            AddSpeed(1);
                            aniSpeed -= 3;
                        }
                        speedPotionTime = 1;
                        speedPotion = System.currentTimeMillis();
                        currentAttackDelay = ATTACK_DELAY_SPEED;
                    }
                    if (object instanceof PoisonPotion) {
                        life -= object.GetLife();
                        object.NotAlive();
                    }
                    if (object instanceof Dragon) {
                        ((PlayState)refLink.GetGame().GetPlayState()).GameCompleted();
                    }
                    object.NotAlive();
                    System.out.println("Obbject deleted");
                }
//        for(Enemy enemy: refLink.GetMap().GetEnemies())
//            if(Intersects(this,enemy,refLink) && enemy.isAttacked == false){
//                if(enemy.canAttack())
//                    System.out.println("viata -2");
//                enemy.SetAttackAnimation();
//            }
//            else
//                enemy.SetNormalAnimation();
            //System.out.println(y+bounds.y);
            //System.out.println(refLink.GetCamera().GetY());
            //System.out.println(bounds.getHeight());


            float backupX = x;
            float backupY = y;
            Move();
            refLink.GetCamera().Update(x, y, refLink.GetWidth(), refLink.GetHeight(), refLink.GetMap().GetMapWidth(), refLink.GetMap().GetMapHeight());

            for (Enemy enemy : refLink.GetMap().GetEnemies()) {
                if (Collision(this, enemy, refLink)) {
                    SetXMove(0);
                    SetYMove(0);
                    enemy.SetCollision(true);
                    System.out.println("Collision");
                    x = backupX;
                    y = backupY;
                } else {
                    enemy.SetCollision(false);
                }
            }
        }


    }
    private boolean canAttack() {
        long currentTime = System.currentTimeMillis();
        return currentTime - lastAttackTime >= currentAttackDelay;
    }

    private boolean canShoot() {
        long currentTime = System.currentTimeMillis();
        return currentTime - lastShootTime >= currentAttackDelay;
    }

    private int PlayerIdle(int playerAction) {

        switch (playerAction) {
            case LEFT:
                return LEFT_IDLE;
            case RIGHT:
                return RIGHT_IDLE;
            case DOWN:
                return DOWN_IDLE;
            case UP:
                return UP_IDLE;
            default:
                return playerAction;
        }
    }

    private int PlayerNoAttack(int playerAction) {

        switch (playerAction) {
            case LEFT_ATTACK:
            case LEFT_SHOOT:
                return LEFT;
            case RIGHT_ATTACK:
            case RIGHT_SHOOT:
                return RIGHT;
            case DOWN_ATTACK:
            case DOWN_SHOOT:
                return DOWN;
            case UP_ATTACK:
            case UP_SHOOT:
                return UP;
            default:
                return playerAction;
        }
    }
    private int PlayerAttack(int playerAction) {

        switch (playerAction) {
            case LEFT:
                return LEFT_ATTACK;
            case RIGHT:
                return RIGHT_ATTACK;
            case DOWN:
                return DOWN_ATTACK;
            case UP:
                return UP_ATTACK;
            default:
                return playerAction;
        }
    }
    private int PlayerShoot(int playerAction) {

        switch (playerAction) {
            case LEFT:
                return LEFT_SHOOT;
            case RIGHT:
                return RIGHT_SHOOT;
            case DOWN:
                return DOWN_SHOOT;
            case UP:
                return UP_SHOOT;
            default:
                return playerAction;
        }
    }


    /*! \fn private void GetInput()
        \brief Verifica daca a fost apasata o tasta din cele stabilite pentru controlul eroului.
     */
    private void GetInput()
    {
            ///Implicit eroul nu trebuie sa se deplaseze daca nu este apasata o tasta
        xMove = 0;
        yMove = 0;
            ///Verificare apasare tasta "sus"
        if (refLink.GetKeyManager().up)
                yMove = -speed;
        else if (refLink.GetKeyManager().down  )
                yMove = speed;
        else if (refLink.GetKeyManager().right)
                xMove = speed;
        else if (refLink.GetKeyManager().left)
                xMove = -speed;
            ///Verificare apasare tasta "jos"
//        if(refLink.GetKeyManager().down)
//        {
//            yMove = speed;
//        }
//            ///Verificare apasare tasta "left"
//        if(refLink.GetKeyManager().left)
//        {
//            xMove = -speed;
//        }
//            ///Verificare apasare tasta "dreapta"
//        if(refLink.GetKeyManager().right)
//        {
//            xMove = speed;
//        }

    }

    /*! \fn public void Draw(Graphics g)
        \brief Randeaza/deseneaza eroul in noua pozitie.

        \brief g Contextul grafi in care trebuie efectuata desenarea eroului.
     */
    @Override
    public void Draw(Graphics g) {
        if (this.life > 0) {
            updateAnimationTick();
        g.drawImage(animations[playerAction][aniIndex], (int) x, (int) y, width, height, null);

        ///doar pentru debug daca se doreste vizualizarea dreptunghiului de coliziune altfel se vor comenta urmatoarele doua linii
        g.setColor(Color.blue);
        //g.drawRect((int) (x + visualBounds.x), (int) (y + visualBounds.y), visualBounds.width, visualBounds.height);
              //g.drawRect((int) (x + visualBounds.x), (int) (y + visualBounds.y), visualBounds.width, visualBounds.height);
              //g.drawRect((int) (x + bounds.x), (int) (y + bounds.y), bounds.width, bounds.height);
              g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 10));
        if (System.currentTimeMillis() - lastAttackTime < currentAttackDelay)
            g.drawString(Double.toString(Math.max(1000 - Math.min(System.currentTimeMillis() - lastAttackTime, 1000), 0)), (int) x - 10, (int) y - 10);
        }
    }



    public int GetPlayerAction() {return playerAction;}

    public void AddSpeed(int speedMore) { speed+=speedMore;}

    public long GetSpeedPotionTime() {
        if(speedPotionTime==0)
            return 6000;
        else
            return speedPotionTime;}

    public void SetDefaultSpeed() {speed=DEFAULT_SPEED;}

    public int GetScore() {return score;}
    public void AddScore(int score) {this.score+=score;}

    public void SetScore(int score) {this.score=score;}


}
