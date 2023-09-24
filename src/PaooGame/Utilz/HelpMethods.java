package PaooGame.Utilz;

import PaooGame.Items.Character;
import PaooGame.Items.Enemy;
import PaooGame.Items.Hero;
import PaooGame.Items.Wolf;
import PaooGame.Maps.Map;
import PaooGame.Game;
import PaooGame.Objects.Object;
import PaooGame.RefLinks;
import PaooGame.Tiles.Tile;

public class HelpMethods {
    //static int i=0;
    public static boolean CanMoveHere(int x, int y,int  width, int height, RefLinks refLinks,int cameraX,int cameraY)
    {


        //System.out.println((x+(int)refLinks.GetCamera().GetX()));
        //System.out.println((y+(int)refLinks.GetCamera().GetY()));
        refLinks.GetCamera().Update(cameraX, cameraY, refLinks.GetWidth(), refLinks.GetHeight(),refLinks.GetMap().GetMapWidth(), refLinks.GetMap().GetMapHeight());
        if(!IsSolid(x,y,refLinks))
            if(!IsSolid(x+width,y+height,refLinks))
                if(!IsSolid(x+width,y,refLinks))
                    if(!IsSolid(x,y+height,refLinks)) {
                        refLinks.GetCamera().RevertCamera();
                        //System.out.println("Se POATE");
                        return true;
                    }
        refLinks.GetCamera().RevertCamera();
        return false;
    }


    private static boolean IsSolid(int x, int y, RefLinks refLinks)
    {
        if(x<0 || x >= refLinks.GetMap().GetMapWidth()- refLinks.GetCamera().GetX()) {
            return true;
        }
        if(y<0 || y >= refLinks.GetMap().GetMapHeight() - refLinks.GetCamera().GetY()) {

            return true;
        }

        float xIndex = (x+(int)refLinks.GetCamera().GetX())/ Tile.TILE_HEIGHT ;
        float yIndex = (y+(int)refLinks.GetCamera().GetY())/Tile.TILE_WIDTH ;
        //System.out.println(xIndex);
        //System.out.println(yIndex);




        int value = refLinks.GetMap().getTileIndex((int)xIndex, (int)yIndex);
        //System.out.println(value);
            if (value > 100) {
                //System.out.println(value);
                if(value==786) {
                    refLinks.GetMap().setTileIntex((int) xIndex, (int) yIndex, 787);
                    refLinks.GetHero().AddScore(100);
                }
                if(value == 130)
                    refLinks.GetMap().SetLevelCompleted();
                if(value == 848)
                    return false;
                return true;

            }
            else
                return false;


    }


    public static boolean CanEntityMoveHere(int x, int y,int  width, int height, RefLinks refLinks)
    {

        if(!IsSolidEntity(x,y,refLinks))
            if(!IsSolidEntity(x+width,y+height,refLinks))
                if(!IsSolidEntity(x+width,y,refLinks))
                    if(!IsSolidEntity(x,y+height,refLinks))
                        return true;
        return false;
    }


    private static boolean IsSolidEntity(int x, int y, RefLinks refLinks)
    {
        if(x<0 || x >= refLinks.GetMap().GetMapWidth()) {

            return true;
        }
        if(y<0 || y >= refLinks.GetMap().GetMapHeight()) {

            return true;
        }
        int xIndex = (x)/ Tile.TILE_HEIGHT;
        int yIndex = (y)/Tile.TILE_WIDTH;

        int value =refLinks.GetMap().getTileIndex(xIndex,yIndex);
        if (value > 100)
        {
            if(value==848)
                return false;
            return true;

        }
        else return false;
    }

    public static boolean CanArrowMoveHere(int x, int y,int  width, int height, RefLinks refLinks)
    {

        if(!IsSolidArrow(x,y,refLinks))
            if(!IsSolidArrow(x+width,y+height,refLinks))
                if(!IsSolidArrow(x+width,y,refLinks))
                    if(!IsSolidArrow(x,y+height,refLinks))
                        return true;
        return false;
    }
    private static boolean IsSolidArrow(int x, int y, RefLinks refLinks)
    {
        if(x<0 || x >= refLinks.GetMap().GetMapWidth()) {

            return true;
        }
        if(y<0 || y >= refLinks.GetMap().GetMapHeight()) {

            return true;
        }
        int xIndex = (x)/ Tile.TILE_HEIGHT;
        int yIndex = (y)/Tile.TILE_WIDTH;

        int value =refLinks.GetMap().getTileIndex(xIndex,yIndex);
        if (value > 100)
        {
            if(value>270 && value < 684)
                return false;
            if(value==848)
                return false;
            return true;

        }
        else return false;
    }


    public static boolean Intersects(Character hero, Enemy enemy, RefLinks refLinks)
    {
        return hero.GetX()+refLinks.GetCamera().GetX() + hero.GetBoundsX()< enemy.GetX() + enemy.GetBoundsWidth() +enemy.GetBoundsX()&&
                hero.GetX()+refLinks.GetCamera().GetX()+ hero.GetBoundsX()+ hero.GetBoundsWidth() > enemy.GetX()+enemy.GetBoundsX() &&
                hero.GetY()+refLinks.GetCamera().GetY()+hero.GetBoundsY()  < enemy.GetY() + enemy.GetBoundsHeight() +enemy.GetBoundsY() &&
                hero.GetY()+refLinks.GetCamera().GetY()-hero.GetBoundsY()+  hero.GetHeight() > enemy.GetY()+enemy.GetBoundsY();
    }

    public static boolean Intersects(Hero hero, Object object, RefLinks refLinks)
    {
        return hero.GetX()+refLinks.GetCamera().GetX() + hero.GetBoundsX()< object.GetX() + object.GetBoundsWidth()+object.GetBoundsX() &&
                hero.GetX()+refLinks.GetCamera().GetX()+ hero.GetBoundsX()+ hero.GetBoundsWidth() > object.GetX()+object.GetBoundsX()  &&
                hero.GetY()+refLinks.GetCamera().GetY()+hero.GetBoundsY() < object.GetY()  +object.GetBoundsHeight()  +object.GetBoundsY()&&
                hero.GetY()+refLinks.GetCamera().GetY()-hero.GetBoundsY()+ hero.GetHeight() > object.GetY() +object.GetBoundsY();
    }

    public static boolean Intersects(Enemy enemy, Object object)
    {
        return enemy.GetX()+ enemy.GetBoundsX()< object.GetX() + object.GetBoundsWidth()+object.GetBoundsX() &&
                enemy.GetX()+ enemy.GetBoundsX()+ enemy.GetBoundsWidth() > object.GetX()+object.GetBoundsX()  &&
                enemy.GetY()+enemy.GetBoundsY() < object.GetY()  +object.GetBoundsHeight()  +object.GetBoundsY()&&
                enemy.GetY()-enemy.GetBoundsY()+ enemy.GetHeight() > object.GetY() +object.GetBoundsY();
    }

    public static boolean Collision(Character hero, Enemy enemy, RefLinks refLinks)
    {

        return hero.GetX()+refLinks.GetCamera().GetX() + hero.GetBoundsX() < enemy.GetX() + enemy.GetVisualBounds().width +enemy.GetVisualBounds().x &&
                hero.GetX()+refLinks.GetCamera().GetX()+ hero.GetBoundsX()+ hero.GetBoundsWidth() > enemy.GetX()+enemy.GetVisualBounds().x &&
                hero.GetY()+refLinks.GetCamera().GetY()+hero.GetBoundsY()  < enemy.GetY() + enemy.GetVisualBounds().height +enemy.GetVisualBounds().y &&
                hero.GetY()+refLinks.GetCamera().GetY()-hero.GetBoundsY()+ hero.GetHeight() > enemy.GetY()+enemy.GetVisualBounds().y;
    }

}
