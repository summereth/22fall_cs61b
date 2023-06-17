package PlusWorld;
import org.junit.Test;
import static org.junit.Assert.*;

import byowTools.TileEngine.TERenderer;
import byowTools.TileEngine.TETile;
import byowTools.TileEngine.Tileset;

import java.util.HashMap;
import java.util.Random;

/**
 * Draws a world consisting of plus shaped regions.
 */
public class PlusWorld {
    private static final int WIDTH = 40;
    private static final int HEIGHT = 40;
    private static Random r = new Random();
    private static final int SIZE = 4;

    /**
     * Add a plus of size s to a given position in the world.
     * The position is the bottom left of the center of the plus.
     */

    private static void addPlus(TETile[][] w, int x, int y, TETile tile) {
        int rowXmin = Math.min(Math.max(0, x - SIZE), WIDTH);
        int rowXmax = Math.min(x + 2 * SIZE, WIDTH);
        int rowYmin = Math.min(Math.max(0, y - SIZE), HEIGHT);
        int rowYmax = Math.min(y, HEIGHT);
        int colXmin = Math.min(x, WIDTH);
        int colXmax = Math.min(x + SIZE, WIDTH);
        int colYmin = Math.min(Math.max(0, y - 2 * SIZE), HEIGHT);
        int colYmax = Math.min(y + SIZE, HEIGHT);
        // Add the row
        for (int i = rowXmin; i < rowXmax; i ++) {
            for (int j = rowYmin; j < rowYmax; j ++) {
                w[i][j] = tile;
            }
        }
        // Add the column
        for (int i = colXmin; i < colXmax; i ++) {
            for (int j = colYmin; j < colYmax; j ++) {
                w[i][j] = tile;
            }
        }
    }

    /**
     * Randomly add plus starting with an initial pair of position X and Y.
     * @param w the 2D array of tile world to modify
     * @param x initial pair of position X
     * @param y initial pair of position Y
     */
    private static void randomAddPlus(TETile[][] w, int x, int y) {
        //TODO unfinished algorithm of how to draw A Tesselation of Plusses
        if (x > 0 && y > 0) {
            addPlus(w, x, y, getRanTile());
            randomAddPlus(w, x + SIZE, y + 2 * SIZE);
            randomAddPlus(w, x - 2 * SIZE, y + SIZE);
            randomAddPlus(w, x - SIZE, y - 2 * SIZE);
            randomAddPlus(w, x + 2 * SIZE, y - SIZE);
        }
    }

    private static TETile getRanTile() {
        int tileNum = r.nextInt(6);
        switch (tileNum) {
            case 0: return Tileset.FLOWER;
            case 1: return Tileset.GRASS;
            case 2: return Tileset.MOUNTAIN;
            case 3: return Tileset.SAND;
            case 4: return Tileset.TREE;
            case 5: return Tileset.WATER;
            default:return Tileset.NOTHING;
        }
    }
    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        TETile[][] plusWorld = new TETile[WIDTH][HEIGHT];
        int xOfInitial = WIDTH / 2 + r.nextInt(5);
        int yOfInitial = HEIGHT / 2 + r.nextInt(5);
        randomAddPlus(plusWorld, xOfInitial, yOfInitial);

        ter.renderFrame(plusWorld);
    }
}
