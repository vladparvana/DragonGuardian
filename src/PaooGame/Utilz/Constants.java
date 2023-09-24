package PaooGame.Utilz;

public class Constants {

    public static class PlayerConstants {
        public static final int UP= 0;
        public static final int UP_IDLE = 1;
        public static final int LEFT_IDLE = 7;
        public static final int LEFT = 6;
        public static final int RIGHT_IDLE = 3;
        public static final int RIGHT = 2;
        public static final int DOWN_IDLE = 5;
        public static final int DOWN = 4;
        public static final int LEFT_ATTACK=14;
        public static final int RIGHT_ATTACK=10;
        public static final int DOWN_ATTACK=12;
        public static final int UP_ATTACK=8;
        public static final int LEFT_SHOOT=15;
        public static final int RIGHT_SHOOT=11;
        public static final int DOWN_SHOOT=13;
        public static final int UP_SHOOT=9;

        public static int GetSpriteAmount(int player_action,boolean moving) {
            switch(player_action) {
                case UP :
                    if(moving)
                        return 4;
                    else
                        return 2;
                case UP_IDLE:
                    return 2;
                case LEFT :
                    if(moving)
                        return 4;
                    else
                        return 2;
                case RIGHT :
                    if(moving)
                        return 4;
                    else
                        return 2;
                case DOWN:
                    if(moving)
                        return 4;
                    else
                        return 2;
                case LEFT_IDLE:
                    return 2;
                case RIGHT_IDLE:
                    return 2;
                case DOWN_IDLE:
                    return 2;


            }
            return 4;
        }

    }
}
