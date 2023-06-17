package MemoryGame;

import byowTools.RandomUtils;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;

public class MemoryGame {
    /** The width of the window of this game. */
    private int width;
    /** The height of the window of this game. */
    private int height;
    /** The current round the user is on. */
    private int round;
    /** The Random object used to randomly generate Strings. */
    private Random rand;
    /** Whether or not the game is over. */
    private boolean gameOver;
    /** Whether or not it is the player's turn. Used in the last section of the
     * spec, 'Helpful UI'. */
    private boolean playerTurn;
    /** The characters we generate random Strings from. */
    private static final char[] CHARACTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    /** Encouraging phrases. Used in the last section of the spec, 'Helpful UI'. */
    private static final String[] ENCOURAGEMENT = {"You can do this!", "I believe in you!",
                                                   "You got this!", "You're a star!", "Go Bears!",
                                                   "Too easy for you!", "Wow, so impressive!"};

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please enter a seed");
            return;
        }

        long seed = Long.parseLong(args[0]);
        MemoryGame game = new MemoryGame(40, 40, seed);
        game.startGame();
    }

    public MemoryGame(int width, int height, long seed) {
        /* Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
         */
        this.width = width;
        this.height = height;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();

        this.rand = new Random(seed);
    }

    public String generateRandomString(int n) {
        String randStr = "";

        while (n > 0) {
            int index = rand.nextInt(26);
            randStr += CHARACTERS[index];
            n -= 1;
        }

        // System.out.println(randStr);
        return randStr;
    }

    /**
     * Draw all the element each time when calling this methods.
     * It should be called by each character of a string.
     */
    public void drawFrame(String s, boolean watch) {
        /* Take the input string S and display it at the center of the screen,
        * with the pen settings given below. */
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.WHITE);
        Font fontBig = new Font("Monaco", Font.BOLD, 20);
        StdDraw.setFont(fontBig);
        StdDraw.text(this.width / 2, this.height / 2, s);

        StdDraw.line(0, this.height - 2, this.width, this.height - 2);
        String roundText = "Round: " + Integer.toString(this.round);
        StdDraw.textLeft(0, this.height - 1, roundText);

        // If the game is not over, display encouragement, and let the user know if they
        // should be typing their answer or watching for the next round.
        if (!this.gameOver) {
            String enc = ENCOURAGEMENT[rand.nextInt(7)];
            StdDraw.textRight(this.width, this.height - 1, enc);
        }
        if (watch) {
            StdDraw.text(this.width / 2, this.height - 1, "Watch!");
        } else {
            StdDraw.text(this.width / 2, this.height - 1, "Type!");
        }
        StdDraw.show();
    }

    public void flashSequence(String letters) {
        // Display each character in letters, making sure to blank the screen between letters
        for (Character c : letters.toCharArray()) {
            drawFrame(c.toString(), true);
            StdDraw.pause(1000);
            drawFrame("", true);
            StdDraw.pause(500);
        }
        drawFrame("", false);
    }

    public String solicitNCharsInput(int n) {
        // Read n letters of player input
        String input = "";
        while (n > 0) {
            while (!StdDraw.hasNextKeyTyped()) {
                StdDraw.pause(500);
            }
            input += StdDraw.nextKeyTyped();
            drawFrame("", false);
            drawFrame(input, false);
            StdDraw.pause(500);
            n -= 1;
        }
        return input;
    }

    public void startGame() {
        // Set any relevant variables before the game starts
        this.gameOver = false;
        this.round = 1;

        // Establish Engine loop
        while (!gameOver) {
            String genStr = generateRandomString(round);
            flashSequence(genStr);
            if (solicitNCharsInput(round).equals(genStr)) {
                this.round += 1;
            } else {
                gameOver = true;
                this.drawFrame("Game Over! You made it to round: " + this.round, true);
            }
        }
    }
}
