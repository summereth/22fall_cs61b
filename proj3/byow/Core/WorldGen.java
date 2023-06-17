package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * By inputting a seed, this class can randomly generate a world of rooms and hallways.
 * All rooms and hallways are connected. Each room or hallway is composed of walls and floors.
 *
 * Created by Qian (Summer) Li
 */

public class WorldGen {
    public static final int WIDTH = 100;
    public static final int HEIGHT = 50;
    private long seed;
    public Random r;
    TETile[][] world;
    private static final int MAXROOM = 10;
    private static final int MAXHALL = 10;
    private List<Chamber> chambers = new ArrayList<>();
    private Graph graphOfChamber;
    public WorldGen(long seed) {
        this.seed = seed;
        r = new Random(seed);
        this.world = new TETile[WIDTH][HEIGHT];
    }

    /**
     * Generate random number of chambers (room or hallway) of
     * random width and length. Add them to the 2D array of world,
     * and create a graph of those chambers to better connect them.
     */
    private void genChamber() {
        int roomNum = r.nextInt(MAXROOM);
        int hallNum = r.nextInt(MAXHALL);
        for (int i = 0; i < roomNum; i ++) {
            Room ranRoom = new Room(this);
            ranRoom.draw();
//            TETile testTile = new TETile((char) (i+48), Color.black, Color.white,
//                    "floor");
//            ranRoom.draw(testTile);
            chambers.add(ranRoom);
            System.out.println("Add a room of " + ranRoom.width + "*" + ranRoom.len);
        }
        for (int i = 0; i < hallNum; i ++) {
            Hall ranHall = new Hall(this);
            ranHall.draw();
//            TETile testTile = new TETile((char) (i+48+roomNum), Color.red, Color.white,
//                    "floor");
//            ranHall.draw(testTile);
            chambers.add(ranHall);
            System.out.println("Add a hall of " + ranHall.width + "*" + ranHall.len);
        }
        // After generate all Chamber, initial the graph of the chambers
        graphOfChamber = new Graph(chambers.size());

        System.out.println("room number: " + roomNum);
        System.out.println("hall number: " + hallNum);
    }
    private void connect(Chamber c1, Chamber c2) {
        graphOfChamber.addEdge(chambers.indexOf(c1), chambers.indexOf(c2));
        c1.drawHallTo(c2);
    }
    private void connectAll() {
        // utilize graphOfChamber.isConnected()
        while (!graphOfChamber.isConnected()) {
            int index1 = r.nextInt(chambers.size());
            int index2 = r.nextInt(chambers.size());
            Chamber randC1 = chambers.get(index1);
            Chamber randC2 = chambers.get(index2);
            if (randC1 != randC2 &&
                    !graphOfChamber.isAdj(index1, index2)) {
                connect(randC1, randC2);
            }
        }
        graphOfChamber.printAdj();
    }
    /**
     * Initialize the world with Tileset.NOTHING
     */
    private void initialWorld(TETile[][] w) {
        for (int i = 0; i < WIDTH; i ++) {
            for (int j = 0; j < HEIGHT; j ++) {
                w[i][j] = Tileset.NOTHING;
            }
        }
    }
    private void randAddLockedDoor() {
        int randChamIndex = r.nextInt(chambers.size());
        Chamber doorRoom = chambers.get(randChamIndex);
        doorRoom.addLockedDoor();
    }
    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        long seed = 680364905;
        WorldGen wg = new WorldGen(seed);

        wg.initialWorld(wg.world);
        wg.genChamber();
        wg.connectAll();
        wg.randAddLockedDoor();
        ter.renderFrame(wg.world);
    }
}
