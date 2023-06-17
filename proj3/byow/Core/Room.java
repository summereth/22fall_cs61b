package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Room implements Chamber{
    public int len;
    public int width;
    public int x;
    public int y;
    private int MAXLEN = 7;
    private int MINLEN = 2;
    private int MAXWIDTH = 7;
    private int MINWIDTH = 2;
    TETile[][] world;
    public Room(WorldGen wg) {
        len = MINLEN + wg.r.nextInt(MAXLEN);
        width = MINWIDTH + wg.r.nextInt(MAXWIDTH);
        x = 1 + wg.r.nextInt(wg.WIDTH - MAXWIDTH - 2);
        y = 1 + wg.r.nextInt(wg.HEIGHT - MAXLEN - 2);
        this.world = wg.world;
    }

    @Override
    public int getLen() {
        return len;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public void draw() {
        draw(Tileset.MY_FLOOR);
    }
    @Override
    public void draw(TETile tile) {
        int width = getWidth();
        int len = getLen();
        int x = getX();
        int y = getY();
        // Draw the floor
        for (int i = x; i < x + width; i ++) {
            for (int j = y; j < y + len; j ++) {
                world[i][j] = tile;
            }
        }
        // Draw the wall
        for (int i = x - 1; i < x + width + 1; i += width + 1) {
            for (int j = y - 1; j < y + len + 1; j ++) {
                world[i][j] = Tileset.MY_WALL;
            }
        }
        for (int i = x - 1; i < x + width + 1; i ++) {
            for (int j = y - 1; j < y + len + 1; j += len + 1) {
                world[i][j] = Tileset.MY_WALL;
            }
        }
    }
    @Override
    public void drawHallTo(Chamber other) {
        int[][] entrance = findEntranceTo(other);
        int xStart = entrance[0][0];
        int yStart = entrance[0][1];
        int xEnd = entrance[1][0];
        int yEnd = entrance[1][1];
        int xDist = xStart - xEnd;
        int yDist = yStart - yEnd;
        int cmp = Integer.compare(Math.abs(xDist), Math.abs(yDist));
        int xDrct = 0;
        int yDrct = 0;
        if (xDist != 0) {
            xDrct = xDist / Math.abs(xDist);
        }
        if (yDist != 0) {
            yDrct = yDist / Math.abs(yDist);
        }

        if (cmp >= 0) {
            // Go horizontally first and then vertically
            for (int i = 0; Math.abs(i) < Math.abs(xDist / 2); i += xDrct) {
                world[xStart - i][yStart] = Tileset.MY_FLOOR;
                addWall(xStart - i, yStart);
            }
            for (int i = 0; Math.abs(i) < Math.abs(yDist); i += yDrct) {
                world[xStart - xDist / 2][yStart - i] = Tileset.MY_FLOOR;
                addWall(xStart - xDist / 2, yStart - i);
            }
            for (int i = xDist / 2; Math.abs(i) <= Math.abs(xDist); i += xDrct) {
                world[xStart - i][yStart - yDist] = Tileset.MY_FLOOR;
                addWall(xStart - i, yStart - yDist);
            }
        } else {
            // Go vertically first and then horizontally
            for (int i = 0; Math.abs(i) < Math.abs(yDist / 2); i += yDrct) {
                world[xStart][yStart - i] = Tileset.MY_FLOOR;
                addWall(xStart, yStart - i);
            }
            for (int i = 0; Math.abs(i) < Math.abs(xDist); i += xDrct) {
                world[xStart - i][yStart - yDist / 2] = Tileset.MY_FLOOR;
                addWall(xStart - i, yStart - yDist / 2);
            }
            for (int i = yDist / 2; Math.abs(i) < Math.abs(yDist); i += yDrct) {
                world[xStart - xDist][yStart - i] = Tileset.MY_FLOOR;
                addWall(xStart - xDist, yStart - i);
            }
        }

    }

    private void addWall(int x, int y) {
        for (int i = y - 1; i < y + 2; i += 2 ) {
            if (!world[x][i].equals(Tileset.MY_FLOOR)) {
                world[x][i] = Tileset.MY_WALL;
            }
        }
        for (int i = x - 1; i < x + 2; i += 2 ) {
            if (!world[i][y].equals(Tileset.MY_FLOOR)) {
                world[i][y] = Tileset.MY_WALL;
            }
        }
    }

    /**
     * Find the entrance on the wall if we want to connect the
     * room with the other chamber. Return an array of positions.
     * arr[0]: X position of the entrance to the room;
     * arr[1]: Y position of the entrance to the room;
     * arr[2]: X position of the entrance to the other chamber;
     * arr[3]: Y position of the entrance to the other chamber;
     */
    private int[][] findEntranceTo(Chamber other) {
        int [][] arr = new int[2][2];

        int xDist = this.getX() - other.getX();
        int yDist = this.getY() - other.getY();
        int cmp = Integer.compare(Math.abs(xDist), Math.abs(yDist));

        if (cmp >= 0 && xDist >= 0) {
            arr[0] = this.getEntrance(0);
            arr[1] = other.getEntrance(1);
        } else if (cmp >= 0 && xDist < 0) {
            arr[0] = this.getEntrance(1);
            arr[1] = other.getEntrance(0);
        } else if (cmp < 0 && yDist >= 0) {
            arr[0] = this.getEntrance(3);
            arr[1] = other.getEntrance(2);
        } else {
            arr[0] = this.getEntrance(2);
            arr[1] = other.getEntrance(3);
        }

        return arr;
    }
    public int[] getEntrance(int direction) {
        int[] wEnt = new int[]{this.getX() - 1, this.getY() + getLen() / 2};
        int[] eEnt = new int[]{this.getX() + this.getWidth(), this.getY() + getLen() / 2};
        int[] nEnt = new int[]{this.getX() + getWidth() / 2, this.getY() + this.getLen()};
        int[] sEnt = new int[]{this.getX() + getWidth() / 2, this.getY() - 1};
        switch(direction) {
            case 0: return wEnt;
            case 1: return eEnt;
            case 2: return nEnt;
            case 3: return sEnt;
            default: return wEnt;
        }
    }
    public void addLockedDoor() {
        int randNum = (int)(getWall().size() * Math.random());
        int wallX = getWall().get(randNum)[0];
        int wallY = getWall().get(randNum)[1];
        world[wallX][wallY] = Tileset.MY_LOCKED_DOOR;
    }

    /**
     * Return a list of array of wall's position
     */
    private List<int[]> getWall() {
        int x = getX();
        int y = getY();
        int width = getWidth();
        int len = getLen();

        List<int[]> wall = new ArrayList<>();
        for (int i = x - 1; i <= x + width; i ++) {
            if (world[i][y - 1] == Tileset.MY_WALL) {
                int[] pos = new int[]{i, y - 1};
                wall.add(pos);
            }
            if (world[i][y + len] == Tileset.MY_WALL) {
                int[] pos = new int[]{i, y + len};
                wall.add(pos);
            }
        }
        for (int i = y - 1; i <= y + len; i ++) {
            if (world[x - 1][i] == Tileset.MY_WALL) {
                int[] pos = new int[]{x - 1, i};
                wall.add(pos);
            }
            if (world[x + width][i] == Tileset.MY_WALL) {
                int[] pos = new int[]{x + width, i};
                wall.add(pos);
            }
        }

        return wall;
    }
}
