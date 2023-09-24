package PaooGame.DBManager;

import java.sql.*;

public class DBManager {
    public static int score;
    public static int keys;
    private Connection con;
    private Statement statement;
    public static void create_table()
    {
        try
        {
            Class.forName("org.sqlite.JDBC");

        } catch (Exception e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        //C:\Users\Aso\IdeaProjects\Game2DProiect_PAOO\out\production\Game2DProiect_PAOO

        //String url = "jdbc:sqlite:C:\\Users\\Aso\\IdeaProjects\\Game2DProiect_PAOO\\out\\production\\Game2DProiect_PAOO\\tiobe1.db";
        String url = "jdbc:sqlite:database.db";


        try (Connection conn = DriverManager.getConnection(url))
        {
            String sql = "CREATE TABLE IF NOT EXISTS GAMESTATS (\n"
                    + "	id integer PRIMARY KEY,\n"
                    + " LEVEL integer NOT NULL\n"
                    + ");";
            Statement stmt = conn.createStatement();
            // create a new table
            stmt.execute(sql);
            sql = "INSERT OR IGNORE INTO GAMESTATS(ID,LEVEL) " +
                    "VALUES (1, 1);";

            stmt.execute(sql);

            sql = "CREATE TABLE IF NOT EXISTS PLAYER (\n"
                    + "	id integer PRIMARY KEY,\n"
                    + "	X integer NOT NULL,\n"
                    + "	Y integer NOT NULL,\n"
                    + "	LIFE integer NOT NULL,\n"
                    + " SCORE integer NOT NULL\n"
                    + ");";
            stmt.execute(sql);

            sql = "INSERT OR IGNORE INTO PLAYER(ID,X,Y,SCORE,LIFE) " +
                    "VALUES (1, 0,0,0,0);";

            stmt.execute(sql);
            sql = "CREATE TABLE IF NOT EXISTS OBJECTS (\n"
                    + "	id integer PRIMARY KEY,\n"
                    + "	TYPE text NOT NULL,\n"
                    + "	X integer NOT NULL,\n"
                    + "	Y integer NOT NULL\n"
                    + ");";
            stmt.execute(sql);



            sql = "CREATE TABLE IF NOT EXISTS ENEMIES (\n"
                    + "	id integer PRIMARY KEY,\n"
                    + " TYPE text NOT NULL,\n"
                    + "	X integer NOT NULL,\n"
                    + "	Y integer NOT NULL,\n"
                    + "	LIFE integer NOT NULL\n"
                    + ");";
            stmt.execute(sql);

            sql = "CREATE TABLE IF NOT EXISTS CAMERA (\n"
                    + "	id integer PRIMARY KEY,\n"
                    + "	X integer NOT NULL,\n"
                    + "	Y integer NOT NULL\n"
                    + ");";
            stmt.execute(sql);

            sql = "INSERT OR IGNORE INTO CAMERA(ID,X,Y) " +
                    "VALUES (1, 0,0);";

            stmt.execute(sql);

            stmt.close();
            conn.close();

            System.out.println("Tabel creat cu succes");

        } catch (SQLException e) {
            System.out.println(e);
        }

    }

    public static void setLevel(int level) {
        String url = "jdbc:sqlite:database.db";

        try (Connection conn = DriverManager.getConnection(url)) {
            Statement stmt = conn.createStatement();
            conn.setAutoCommit(false);

            String sql = "UPDATE GAMESTATS set LEVEL =" + level + " where ID=1;";
            stmt.executeUpdate(sql);
            conn.commit();
            stmt.close();
            conn.close();

            System.out.println("Update level cu succes\n");

        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public static int getLevel() {
        String url = "jdbc:sqlite:database.db";
        try(Connection conn = DriverManager.getConnection(url)) {
            Statement stmt = conn.createStatement();
            conn.setAutoCommit(false);

            String sql = "SELECT LEVEL FROM GAMESTATS WHERE ID=1";
            ResultSet rs=stmt.executeQuery(sql);
            int level=rs.getInt("LEVEL");
            rs.close();
            stmt.close();
            conn.close();
            return level;
        }
        catch (SQLException e) {
            System.out.println(e);
        }
        return -1;
    }


    public static void setPlayerData(int X,int Y, int SCORE,int LIFE) {
        String url = "jdbc:sqlite:database.db";

        try (Connection conn = DriverManager.getConnection(url)) {
            Statement stmt = conn.createStatement();
            conn.setAutoCommit(false);

            String sql = "UPDATE PLAYER set X =" + X + " where ID=1;";
            stmt.executeUpdate(sql);

            sql = "UPDATE PLAYER set Y =" + Y + " where ID=1;";
            stmt.executeUpdate(sql);

            sql = "UPDATE PLAYER set SCORE =" + SCORE + " where ID=1;";
            stmt.executeUpdate(sql);

            sql = "UPDATE PLAYER set LIFE =" + LIFE + " where ID=1;";
            stmt.executeUpdate(sql);

            conn.commit();
            stmt.close();
            conn.close();

            System.out.println("Update level cu succes\n");

        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public static int getPlayerX() {
        String url = "jdbc:sqlite:database.db";
        try(Connection conn = DriverManager.getConnection(url)) {
            Statement stmt = conn.createStatement();
            conn.setAutoCommit(false);

            String sql = "SELECT X FROM PLAYER WHERE ID=1";
            ResultSet rs=stmt.executeQuery(sql);
            int level=rs.getInt("X");
            rs.close();
            stmt.close();
            conn.close();
            return level;
        }
        catch (SQLException e) {
            System.out.println(e);
        }
        return -1;
    }

    public static int getPlayerY() {
        String url = "jdbc:sqlite:database.db";
        try(Connection conn = DriverManager.getConnection(url)) {
            Statement stmt = conn.createStatement();
            conn.setAutoCommit(false);

            String sql = "SELECT Y FROM PLAYER WHERE ID=1";
            ResultSet rs=stmt.executeQuery(sql);
            int level=rs.getInt("Y");
            rs.close();
            stmt.close();
            conn.close();
            return level;
        }
        catch (SQLException e) {
            System.out.println(e);
        }
        return -1;
    }

    public static int getPlayerScore() {
        String url = "jdbc:sqlite:database.db";
        try(Connection conn = DriverManager.getConnection(url)) {
            Statement stmt = conn.createStatement();
            conn.setAutoCommit(false);

            String sql = "SELECT SCORE FROM PLAYER WHERE ID=1";
            ResultSet rs=stmt.executeQuery(sql);
            int level=rs.getInt("SCORE");
            rs.close();
            stmt.close();
            conn.close();
            return level;
        }
        catch (SQLException e) {
            System.out.println(e);
        }
        return -1;
    }

    public static int getPlayerLife() {
        String url = "jdbc:sqlite:database.db";
        try(Connection conn = DriverManager.getConnection(url)) {
            Statement stmt = conn.createStatement();
            conn.setAutoCommit(false);

            String sql = "SELECT LIFE FROM PLAYER WHERE ID=1";
            ResultSet rs=stmt.executeQuery(sql);
            int level=rs.getInt("LIFE");
            rs.close();
            stmt.close();
            conn.close();
            return level;
        }
        catch (SQLException e) {
            System.out.println(e);
        }
        return -1;
    }
    public static void deleteObjects() {
        String url = "jdbc:sqlite:database.db";
        try(Connection conn = DriverManager.getConnection(url)) {
            Statement stmt = conn.createStatement();
            conn.setAutoCommit(false);

            String sql = "DELETE FROM OBJECTS";
            stmt.execute(sql);
            stmt.close();
            conn.commit();
            conn.close();
        }
        catch (SQLException e) {
            System.out.println(e);
        }
    }

    public static void loadObject(int _id,String _type, int _x,int _y)
    {
        String url = "jdbc:sqlite:database.db";


        try (Connection conn = DriverManager.getConnection(url))
        {
            String sql = "INSERT INTO OBJECTS (id, TYPE, X, Y) VALUES (?, ?, ?, ?)";

            PreparedStatement pstmt= conn.prepareStatement(sql);
////            Statement stmt = conn.createStatement();
////            stmt.executeUpdate(sql);
//            stmt.close();
            pstmt.setInt(1,_id);
            pstmt.setString(2,_type);
            pstmt.setInt(3,_x);
            pstmt.setInt(4,_y);
            pstmt.executeUpdate();
            pstmt.close();
            conn.close();

            System.out.println("Tabel creat cu succes");

        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public static int getObjectsCount() {
        String url = "jdbc:sqlite:database.db";
        try(Connection conn = DriverManager.getConnection(url)) {
            Statement stmt = conn.createStatement();
            conn.setAutoCommit(false);

            String sql = "SELECT COUNT(*) FROM OBJECTS";
            ResultSet rs=stmt.executeQuery(sql);
            int count=rs.getInt(1);
            System.out.println("OBIECTE"+count);
            rs.close();
            stmt.close();
            conn.close();
            return count;
        }
        catch (SQLException e) {
            System.out.println(e);
        }
        return -1;
    }

    public static String getObjectType(int id) {
        String url = "jdbc:sqlite:database.db";
        try(Connection conn = DriverManager.getConnection(url)) {
            Statement stmt = conn.createStatement();
            conn.setAutoCommit(false);

            String sql = "SELECT TYPE FROM OBJECTS WHERE ID="+id;
            ResultSet rs=stmt.executeQuery(sql);
            String type=rs.getString("TYPE");
            rs.close();
            stmt.close();
            conn.close();
            return type;
        }
        catch (SQLException e) {
            System.out.println(e);
        }
        return "";
    }

    public static int getObjectX(int id) {
        String url = "jdbc:sqlite:database.db";
        try(Connection conn = DriverManager.getConnection(url)) {
            Statement stmt = conn.createStatement();
            conn.setAutoCommit(false);

            String sql = "SELECT X FROM OBJECTS WHERE ID="+id;
            ResultSet rs=stmt.executeQuery(sql);
            int x=rs.getInt("X");
            rs.close();
            stmt.close();
            conn.close();
            return x;
        }
        catch (SQLException e) {
            System.out.println(e);
        }
        return 0;
    }

    public static int getObjectY(int id) {
        String url = "jdbc:sqlite:database.db";
        try(Connection conn = DriverManager.getConnection(url)) {
            Statement stmt = conn.createStatement();
            conn.setAutoCommit(false);

            String sql = "SELECT Y FROM OBJECTS WHERE ID="+id;
            ResultSet rs=stmt.executeQuery(sql);
            int y=rs.getInt("Y");
            rs.close();
            stmt.close();
            conn.close();
            return y;
        }
        catch (SQLException e) {
            System.out.println(e);
        }
        return 0;
    }

    public static void deleteEnemies() {
        String url = "jdbc:sqlite:database.db";
        try(Connection conn = DriverManager.getConnection(url)) {
            Statement stmt = conn.createStatement();
            conn.setAutoCommit(false);

            String sql = "DELETE FROM ENEMIES";
            stmt.execute(sql);
            stmt.close();
            conn.commit();
            conn.close();
        }
        catch (SQLException e) {
            System.out.println(e);
        }
    }

    public static void loadEnemies(int id,String type, int x,int y,int life)
    {
        String url = "jdbc:sqlite:database.db";


        try (Connection conn = DriverManager.getConnection(url))
        {
            String sql = "INSERT INTO ENEMIES (id, TYPE, X, Y, LIFE) VALUES (?, ?, ?, ?, ?)";

            PreparedStatement pstmt= conn.prepareStatement(sql);
////            Statement stmt = conn.createStatement();
////            stmt.executeUpdate(sql);
//            stmt.close();
            pstmt.setInt(1,id);
            pstmt.setString(2,type);
            pstmt.setInt(3,x);
            pstmt.setInt(4,y);
            pstmt.setInt(5,life);
            pstmt.executeUpdate();
            pstmt.close();
            conn.close();

            System.out.println("Tabel creat cu succes");

        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public static int getEnemiesCount() {
        String url = "jdbc:sqlite:database.db";
        try(Connection conn = DriverManager.getConnection(url)) {
            Statement stmt = conn.createStatement();
            conn.setAutoCommit(false);

            String sql = "SELECT COUNT(*) FROM ENEMIES";
            ResultSet rs=stmt.executeQuery(sql);
            int count=rs.getInt(1);
            System.out.println("INAMICI"+count);
            rs.close();
            stmt.close();
            conn.close();
            return count;
        }
        catch (SQLException e) {
            System.out.println(e);
        }
        return -1;
    }

    public static String getEnemiesType(int id) {
        String url = "jdbc:sqlite:database.db";
        try(Connection conn = DriverManager.getConnection(url)) {
            Statement stmt = conn.createStatement();
            conn.setAutoCommit(false);

            String sql = "SELECT TYPE FROM ENEMIES WHERE ID="+id;
            ResultSet rs=stmt.executeQuery(sql);
            String type=rs.getString("TYPE");
            rs.close();
            stmt.close();
            conn.close();
            return type;
        }
        catch (SQLException e) {
            System.out.println(e);
        }
        return "";
    }

    public static int getEnemiesX(int id) {
        String url = "jdbc:sqlite:database.db";
        try(Connection conn = DriverManager.getConnection(url)) {
            Statement stmt = conn.createStatement();
            conn.setAutoCommit(false);

            String sql = "SELECT X FROM ENEMIES WHERE ID="+id;
            ResultSet rs=stmt.executeQuery(sql);
            int x=rs.getInt("X");
            rs.close();
            stmt.close();
            conn.close();
            return x;
        }
        catch (SQLException e) {
            System.out.println(e);
        }
        return 0;
    }

    public static int getEnemiesY(int id) {
        String url = "jdbc:sqlite:database.db";
        try(Connection conn = DriverManager.getConnection(url)) {
            Statement stmt = conn.createStatement();
            conn.setAutoCommit(false);

            String sql = "SELECT Y FROM ENEMIES WHERE ID="+id;
            ResultSet rs=stmt.executeQuery(sql);
            int y=rs.getInt("Y");
            rs.close();
            stmt.close();
            conn.close();
            return y;
        }
        catch (SQLException e) {
            System.out.println(e);
        }
        return 0;
    }

    public static int getEnemiesLife(int id) {
        String url = "jdbc:sqlite:database.db";
        try(Connection conn = DriverManager.getConnection(url)) {
            Statement stmt = conn.createStatement();
            conn.setAutoCommit(false);

            String sql = "SELECT LIFE FROM ENEMIES WHERE ID="+id;
            ResultSet rs=stmt.executeQuery(sql);
            int y=rs.getInt("LIFE");
            rs.close();
            stmt.close();
            conn.close();
            return y;
        }
        catch (SQLException e) {
            System.out.println(e);
        }
        return 0;
    }


    public static void setCameraX(int cameraX) {
        String url = "jdbc:sqlite:database.db";

        try (Connection conn = DriverManager.getConnection(url)) {
            Statement stmt = conn.createStatement();
            conn.setAutoCommit(false);

            String sql = "UPDATE CAMERA set X =" + cameraX + " where ID=1;";
            stmt.executeUpdate(sql);
            conn.commit();
            stmt.close();
            conn.close();

            System.out.println("Update level cu succes\n");

        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public static void setCameraY(int cameraY) {
        String url = "jdbc:sqlite:database.db";

        try (Connection conn = DriverManager.getConnection(url)) {
            Statement stmt = conn.createStatement();
            conn.setAutoCommit(false);

            String sql = "UPDATE CAMERA set Y =" + cameraY + " where ID=1;";
            stmt.executeUpdate(sql);
            conn.commit();
            stmt.close();
            conn.close();

            System.out.println("Update level cu succes\n");

        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public static int getCameraX() {
        String url = "jdbc:sqlite:database.db";
        try(Connection conn = DriverManager.getConnection(url)) {
            Statement stmt = conn.createStatement();
            conn.setAutoCommit(false);

            String sql = "SELECT X FROM CAMERA WHERE ID=1";
            ResultSet rs=stmt.executeQuery(sql);
            int level=rs.getInt("X");
            rs.close();
            stmt.close();
            conn.close();
            return level;
        }
        catch (SQLException e) {
            System.out.println(e);
        }
        return -1;
    }

    public static int getCameraY() {
        String url = "jdbc:sqlite:database.db";
        try(Connection conn = DriverManager.getConnection(url)) {
            Statement stmt = conn.createStatement();
            conn.setAutoCommit(false);

            String sql = "SELECT Y FROM CAMERA WHERE ID=1";
            ResultSet rs=stmt.executeQuery(sql);
            int level=rs.getInt("Y");
            rs.close();
            stmt.close();
            conn.close();
            return level;
        }
        catch (SQLException e) {
            System.out.println(e);
        }
        return -1;
    }
}
