package PaooGame.Maps;

import PaooGame.Arrows.*;
import PaooGame.DBManager.DBManager;
import PaooGame.Items.Archer;
import PaooGame.Items.Enemy;
import PaooGame.Items.Warrior;
import PaooGame.Items.Wolf;
import PaooGame.Objects.*;
import PaooGame.Objects.Object;
import PaooGame.RefLinks;
import PaooGame.States.PlayState;
import PaooGame.Tiles.Tile;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/*! \class public class Map
    \brief Implementeaza notiunea de harta a jocului.
 */
public class Map
{
    private RefLinks refLink;   /*!< O referinte catre un obiect "shortcut", obiect ce contine o serie de referinte utile in program.*/
    private int width;          /*!< Latimea hartii in numar de dale.*/
    private int height;         /*!< Inaltimea hartii in numar de dale.*/
    private int [][] tiles;     /*!< Referinta catre o matrice cu codurile dalelor ce vor construi harta.*/
    //private ArrayList<Building> buildings;
    private ArrayList<Enemy> enemies;
    private int enemiesAlive =0;

    private ArrayList<Object> objects;
    private int objectsAlive =0;
    private boolean levelCompleted=false;
    private int level;

    private ArrayList<Arrow> arrows;

    private Random random;
   // private Chicken chicken;

    /*! \fn public Map(RefLinks refLink)
        \brief Constructorul de initializare al clasei.

        \param refLink O referinte catre un obiect "shortcut", obiect ce contine o serie de referinte utile in program.
     */
    public Map(RefLinks refLink,int level) {
        this.refLink = refLink;
        this.level=level;
        arrows=new ArrayList<>();
        random=new Random();
        switch(this.level) {
            case 1:
                LoadWorld("res/map1.txt");
                LoadEnemies("res/enemies1.txt");
                LoadObjects("res/objects1.txt");
                break;
            case 2:
                LoadWorld("res/map2.txt");
                LoadEnemies("res/enemies2.txt");
                LoadObjects("res/objects2.txt");
                break;
            case 3:
                LoadWorld("res/map3.txt");
                LoadEnemies("res/enemies3.txt");
                LoadObjects("res/objects3.txt");
                break;
        }
        //objects=new ArrayList<>();
        //chicken= new Chicken(refLink,192,192);
        //buildings = new ArrayList<>();
        //loadBuildings();
        //wolf = new Wolf(refLink,300,600);
    }



    public void AddArrow(String type,int x,int y) {
        arrows.add(createArrow(type,x,y));
    }

    private Arrow createArrow(String type,int x,int y) {
        switch (type) {

            case "left" :
                return new ArrowLeft(refLink,x,y);
            case "right" :
                return new ArrowRight(refLink,x,y);
            case "down" :
                return new ArrowDown(refLink,x,y);
            case "up" :
                return new ArrowUp(refLink, x, y);

        }
        return null;
    }
    private void LoadEnemies(String pathToFile) {
        enemies = new ArrayList<>();

        try (Scanner scanner = new Scanner(new File(pathToFile))) {
            while (scanner.hasNext()) {
                String type = scanner.next();
                int x = scanner.nextInt();
                int y = scanner.nextInt();
                Enemy enemy = createEnemy(type, x, y);
                enemies.add(enemy);
                enemiesAlive++;
                System.out.println(enemiesAlive);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private Enemy createEnemy(String type, int x, int y) {
        if ("wolf".equalsIgnoreCase(type)) {
            return new Wolf(refLink, x, y); // Assuming you have a Wolf class that extends Enemy
        }
        if ("warrior".equalsIgnoreCase(type)) {
            return new Warrior(refLink, x, y); // Assuming you have a Wolf class that extends Enemy
        }
        if("archer".equalsIgnoreCase(type)){
            return new Archer(refLink,x,y);
        }
        // Add more cases for different enemy types

        return null; // Return null or throw an exception if the type is unsupported
    }

    private void LoadObjects(String pathToFile) {
        objects = new ArrayList<>();

        try (Scanner scanner = new Scanner(new File(pathToFile))) {
            while (scanner.hasNext()) {
                String type = scanner.next();
                int x = scanner.nextInt();
                int y = scanner.nextInt();
                Object enemy = createObject(type, x, y);
                objects.add(enemy);
                objectsAlive++;
                System.out.println(objectsAlive);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private Object createObject(String type, int x, int y) {
        if ("chicken".equalsIgnoreCase(type)) {
            return new Chicken(refLink, x, y); // Assuming you have a Wolf class that extends Enemy
        }
        if ("potion".equalsIgnoreCase(type)) {
            return new Potion(refLink, x, y); // Assuming you have a Wolf class that extends Enemy
        }
        if ("poisonPotion".equalsIgnoreCase(type)) {
            return new PoisonPotion(refLink, x, y); // Assuming you have a Wolf class that extends Enemy
        }
        if ("dragon".equalsIgnoreCase(type)) {
            return new Dragon(refLink, x, y); // Assuming you have a Wolf class that extends Enemy
        }
        // Add more cases for different enemy types

        return null; // Return null or throw an exception if the type is unsupported
    }


//    private void drawBuildings(Graphics g) {
//        for (Building building : buildings) {
//            building.draw(g,(int)refLink.GetGame().getCamera().GetX(),(int)refLink.GetGame().getCamera().GetY());
//        }
//    }
    /*! \fn public  void Update()
        \brief Actualizarea hartii in functie de evenimente (un copac a fost taiat)
     */
    public  void Update()
    {
        ArrayList<Enemy> copy = new ArrayList<>(enemies);

        for(Enemy enemy: copy) {
            if (enemy.GetLife() > 0)
                enemy.Update();
            if (enemy.GetLife() < 1) {
                int randomNumber = random.nextInt(4);
                if(randomNumber==1 && refLink.GetHero().GetLife() < 10)
                    refLink.GetHero().SetLife(refLink.GetHero().GetLife()+2);
                refLink.GetHero().AddScore(50);
                enemies.remove(enemy);
                enemiesAlive--;
                System.out.println(enemiesAlive);
            }
        }

        ArrayList<Object> copy2 = new ArrayList<>(objects);
        for(Object object: copy2) {
            if(object.isAlive()==0)
            {
                objects.remove(object);
                objectsAlive--;
                System.out.println(objectsAlive);
            }
        }

        ArrayList<Arrow> copy3 =new ArrayList<>(arrows);
        for(Arrow arrow:copy3) {
            arrow.Update();
            if(arrow.alive==false)
                arrows.remove(arrow);
        }


//        if(wolf.GetLife()>0)
//            wolf.Update();
    }

    /*! \fn public void Draw(Graphics g)
        \brief Functia de desenare a hartii.

        \param g Contextl grafi in care se realizeaza desenarea.
     */
    public void Draw(Graphics g)
    {
        int startX = (int) (refLink.GetCamera().GetX() / Tile.TILE_WIDTH);
        int startY = (int) (refLink.GetCamera().GetY() / Tile.TILE_HEIGHT);
        int endX = startX + (int)((refLink.GetGame().GetWidth() +refLink.GetHero().GetX())/ Tile.TILE_WIDTH) + 1;
        int endY = startY + (int)((refLink.GetGame().GetHeight() +refLink.GetHero().GetY())/ Tile.TILE_HEIGHT) + 1;
        ///Se parcurge matricea de dale (codurile aferente) si se deseneaza harta respectiva
        for(int y = startY; y < endY; y++)
        {
            for(int x = startX; x < endX; x++)
            {

                //int tileIndex = y * (refLink.GetGame().GetWidth()/ 48)+ (x/48);
                //int tileIndex=6;
                //if (x >= 0 && x < width && y >= 0 && y < height) {
                    int tileIndex = getTileIndex(x, y);
                    if(((PlayState)refLink.GetGame().GetPlayState()).GetCurrentLevel()==1) {
                        if (refLink.GetHero().GetScore() >= 300 && tileIndex == 131)
                            setTileIntex(x, y, 130);
                    }
                    else
                    if(((PlayState)refLink.GetGame().GetPlayState()).GetCurrentLevel()==2)
                    {
                        if (refLink.GetHero().GetScore() >= 1600 && tileIndex == 131)
                            setTileIntex(x, y, 130);
                    }
                    else
                    if(((PlayState)refLink.GetGame().GetPlayState()).GetCurrentLevel()==3)
                    {
                        if (refLink.GetHero().GetScore() >= 3700 && tileIndex == 131)
                            setTileIntex(x, y, 0);
                    }
                    if(tileIndex != 0)
                        tileIndex--;
                    Tile tile = refLink.GetGame().GetTiles().getTile(tileIndex);
                    tile.draw(g, (int) (x * Tile.TILE_HEIGHT - refLink.GetCamera().GetX()), (int) (y * Tile.TILE_WIDTH - refLink.GetCamera().GetY()));

                    //}
            }
        }

        for(Enemy enemy: enemies)
            if(enemy.GetLife()>0)
                enemy.Draw(g);
        for(Object object: objects)
                object.Draw(g);
        for(Arrow arrow:arrows)
            arrow.Draw(g);
//        if(wolf.GetLife()>0)
//            wolf.Draw(g);
        //drawBuildings(g);
    }

    /*! \fn public Tile GetTile(int x, int y)
        \brief Intoarce o referinta catre dala aferenta codului din matrice de dale.

        In situatia in care dala nu este gasita datorita unei erori ce tine de cod dala, coordonate gresite etc se
        intoarce o dala predefinita (ex. grassTile, mountainTile)
     */

    public int getTileIndex(int x,int y)
    {
        if(x<0 || y<0 || x >= width || y >= height)
        {
            return 0;
        }
        else
            return tiles[x][y];
    }

    public void setTileIntex(int x, int y, int id)
    {
        tiles[x][y]=id;
    }
// public Tile GetTile(int x, int y)
//    {
//        if(x < 0 || y < 0 || x >= width || y >= height)
//        {
//            return Tile.grassTile;
//        }
//        Tile t = Tile.tiles[tiles[x][y]];
//        if(t == null)
//        {
//            return Tile.mountainTile;
//        }
//        return t;
//    }

    /*! \fn private void LoadWorld()
        \brief Functie de incarcare a hartii jocului.
        Aici se poate genera sau incarca din fisier harta. Momentan este incarcata static.
     */
//    private void LoadWorld() {
//        //atentie latimea si inaltimea trebuiesc corelate cu dimensiunile ferestrei sau
//        //se poate implementa notiunea de camera/cadru de vizualizare al hartii
//        ///Se stabileste latimea hartii in numar de dale.
//        width = 22;
//        ///Se stabileste inaltimea hartii in numar de dale
//        height = 15;
//        ///Se construieste matricea de coduri de dale
//        tiles = new int[width][height];
//        //Se incarca matricea cu coduri
//        for (int y = 0; y < height; y++) {
//            for (int x = 0; x < width; x++) {
//                tiles[x][y] = MiddleEastMap(y, x);
//            }
//        }
//    }
        private void LoadWorld(String pathToFile) {
        // Read the map data from the text file
        try (Scanner scanner = new Scanner(new File(pathToFile))) {
            // Read the width and height of the map
            width = scanner.nextInt();
            height = scanner.nextInt();

            // Initialize the tiles array
            tiles = new int[width][height];

            // Read the tile data and populate the tiles array
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    tiles[x][y] = scanner.nextInt();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /*! \fn private int MiddleEastMap(int x ,int y)
        \brief O harta incarcata static.

        \param x linia pe care se afla codul dalei de interes.
        \param y coloana pe care se afla codul dalei de interes.
     */
//    private int MiddleEastMap(int x ,int y)
//    {
//            ///Definire statica a matricei de coduri de dale.
//        final int map[][] = {
//                {192, 193, 193, 193, 193, 193, 193, 193, 193, 193, 193, 193, 193, 193, 193, 193, 193, 193, 193, 193, 193, 194},
//                {219, 220, 220, 220, 220, 220, 220, 220, 220, 220, 220, 220, 220, 220, 220, 220, 220, 220, 220, 220, 220, 221},
//                {246, 247, 247, 247, 247, 247, 247, 247, 247, 247, 247, 247, 247, 247, 248, 247, 247, 247, 219, 220, 220, 221},
//                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 706, 219, 220, 220, 221},
//                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 219, 220, 220, 221},
//                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 219, 220, 220, 221},
//                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 219, 220, 220, 221},
//                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 219, 220, 220, 221},
//                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 219, 220, 220, 221},
//                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 219, 220, 220, 221},
//                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 219, 220, 220, 221},
//                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 219, 220, 220, 221},
//                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 219, 220, 220, 221},
//                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 219, 220, 220, 221}
//        };
//
//        return map[x][y];
//    }

    public int GetMapWidth() {return width*48;}

    public int GetMapHeight() {return height*48;}

    public ArrayList<Enemy> GetEnemies() {return enemies;}

    public ArrayList<Object> GetObjects() {return objects;}

    public void SetLevelCompleted(){ levelCompleted=true;}
    public void SetLevelOngoing(){levelCompleted=false;}
    public boolean GetLevelProgress() {return levelCompleted;}

    public void SaveObjects() {
        int i=1;
        for(Object object:objects)
        {
            if(object instanceof Chicken)
                DBManager.loadObject(i,"chicken",(int)object.GetX(),(int)object.GetY());
            if(object instanceof Potion)
                DBManager.loadObject(i,"potion",(int)object.GetX(),(int)object.GetY());
            if(object instanceof PoisonPotion)
                DBManager.loadObject(i,"poisonPotion",(int)object.GetX(),(int)object.GetY());
            if(object instanceof Dragon)
                DBManager.loadObject(i,"dragon",(int)object.GetX(),(int)object.GetY());
            i++;
        }
    }

    public void LoadObjectsFromDatabase() {
        objects=new ArrayList<>();
        for(int i=1;i<=DBManager.getObjectsCount();i++)
        {
            objects.add(createObject(DBManager.getObjectType(i),DBManager.getObjectX(i),DBManager.getObjectY(i)));
        }
    }

    public void SaveEnemies() {
        int i=1;
        for(Enemy enemy:enemies)
        {
            if(enemy instanceof Wolf)
                DBManager.loadEnemies(i,"wolf",(int)enemy.GetX(),(int)enemy.GetY(),(int)enemy.GetLife());
            if(enemy instanceof Warrior)
                DBManager.loadEnemies(i,"warrior",(int)enemy.GetX(),(int)enemy.GetY(),(int)enemy.GetLife());
            if(enemy instanceof Archer)
                DBManager.loadEnemies(i,"archer",(int)enemy.GetX(),(int)enemy.GetY(),(int)enemy.GetLife());
            i++;
        }
    }

    public void LoadEnemiesFromDatabase() {
        enemies= new ArrayList<>();
        for(int i=1;i<=DBManager.getEnemiesCount();i++)
        {
            Enemy enemy=createEnemy(DBManager.getEnemiesType(i),DBManager.getEnemiesX(i),DBManager.getEnemiesY(i));
            enemy.SetLife(DBManager.getEnemiesLife(i));
            enemies.add(enemy);
        }
    }

}