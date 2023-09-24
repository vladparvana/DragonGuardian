package PaooGame.Camera;

import PaooGame.RefLinks;

import java.awt.*;

public class Camera {
    private Rectangle bounds;
    private double x;
    private double y;
    private double lastX;
    private double lastY;

    public Camera(double x, double y, int width, int height){
        this.bounds=new Rectangle((int)x,(int)y,width,height);
        this.x =x;
        this.y =y;
    }

    public void setPosition(double x,double y) {
        this.x=x;
        this.y=y;
    }

    public void Update(double targetX, double targetY, int screenWidth, int screenHeight,int mapWidth, int mapHeight) {
        // Calculate new camera position based on the target (character) position
        lastX=x;
        if(targetX +x +screenWidth/2 < mapWidth) {

            x = targetX - screenWidth / 2;

        }
        lastY=y;
        if(targetY +y +screenHeight/2  < mapHeight) {

            y = targetY - screenHeight / 2;
        }





        // Clamp camera position to stay within map bounds
        x = Math.max(0, Math.min(x, (mapWidth - bounds.width)));
        y = Math.max(0, Math.min(y, (mapHeight - bounds.height)));
        //System.out.println(" Actual x: "+ Math.min(0, Math.max(x, (mapWidth - bounds.width))));
        //System.out.println(" x: "+ x);



//        if (this.x < 0) {
//            this.x = 0;
//        } else if (this.x > mapWidth - screenWidth) {
//            this.x = mapWidth - screenWidth;
//        }
//
//        if (this.y < 0) {
//            this.y = 0;
//        } else if (this.y > mapHeight - screenHeight) {
//            this.y = mapHeight - screenHeight;
//        }

        //System.out.println("Camera x: " +  x);
        //System.out.println("Camera y: " + y);
    }

    public void Apply(Graphics g) {
        Graphics2D g2d= (Graphics2D) g;
        g2d.translate(-x,-y);
    }

    public void Reset(Graphics g) {
        Graphics2D g2d= (Graphics2D) g;
        g2d.translate(x,y);
    }

    public double GetX() {return x;}
    public double GetY() {return y;}

    public void RevertCamera() {
        x=lastX;
        y=lastY;
    }
}
