package PaooGame.Input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseManager implements MouseListener, MouseMotionListener {

        private int hoverX,hoverY;
        private int clickX, clickY;
        @Override
        public void mouseClicked(MouseEvent e) {
                System.out.println("Mouse clicked");
                clickX = e.getX();
                clickY = e.getY();
        }
        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }

        @Override
        public void mouseDragged(MouseEvent e) {

        }

        @Override
        public void mouseMoved(MouseEvent e) {
                hoverX=e.getX();
                hoverY=e.getY();
        }

        public  int GetHoverMouseX() { return hoverX;}
        public int GetHoverMouseY() { return hoverY;}

        public int GetClickMouseX() { return clickX;}
        public int GetClickMouseY() {return clickY;}

        public void ResetClick() {clickY=0; clickX=0;}


}
