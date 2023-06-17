package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

public class Hall extends Room implements Chamber{
    public int len;
    public int width;
    public int x;
    public int y;
    private static final int MAXLEN = 10;
    private static final int MAXWIDTH = 2;
    private static final int MINLEN = 1;
    private static final int MINWIDTH = 1;
    TETile[][] world;
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
    public Hall(WorldGen wg) {
        super(wg);
        len = MINLEN + wg.r.nextInt(MAXLEN);
        width = MINWIDTH + wg.r.nextInt(MAXWIDTH);
        x = wg.r.nextInt(wg.WIDTH - MAXWIDTH - 2);
        y = wg.r.nextInt(wg.HEIGHT - MAXLEN - 2);
    }
}