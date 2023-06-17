package byow.Core;

import byow.TileEngine.TETile;

/**
 * Space with a certain width and length. It can be drawn into a world
 * with specific tile as the floor. You can also add a pathway/hallway
 * between 2 chambers.
 *
 * Created by Qian (Summer) Li.
 */

public interface Chamber {
    int len = 1;
    int width = 1;
    int x = 0;
    int y = 0;
    int MAXLEN = 7;
    int MINLEN = 2;
    int MAXWIDTH = 7;
    int MINWIDTH = 2;
    int getLen();
    int getWidth();
    int getX();
    int getY();
    /**
     * Draw the chamber into an 2D array of world. The position X,Y are
     * the position of bottom left floor of the room.
     */
    void draw();
    void draw(TETile tile);
    void drawHallTo(Chamber other);
    int[] getEntrance(int direction);
    void addLockedDoor();
}
