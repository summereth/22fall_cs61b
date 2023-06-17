package randomizedtest;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by hug.
 */
public class TestBuggyAList {
  // YOUR TESTS HERE

    public static void main(String[] args) {
        //testThreeAddThreeRemove();
        randomizedTest();
    }
    public static void testThreeAddThreeRemove() {
        AListNoResizing<Integer> ALst = new AListNoResizing<>();
        BuggyAList<Integer> BLst = new BuggyAList<>();
        int[] lst = new int[]{1, 2, 3};
        for (int n : lst) {
            ALst.addLast(n);
            BLst.addLast(n);
        }
        int size = ALst.size();
        for (int i = size - 1; i >= 0; i --) {
            ALst.removeLast();
            BLst.removeLast();
            System.out.println("Remove" + i + "th item:");
            for (int j = 0; j < ALst.size(); j ++) {
                if (ALst.get(j) != BLst.get(j)) {
                    System.out.println("The " + j + "th item of ALst: " + ALst.get(j) + ", but the " + j + "th item of BLst: " + BLst.get(j));
                }
            }
            System.out.println("End");
        }
    }

    public static void randomizedTest() {
        BuggyAList<Integer> L = new BuggyAList<>();
        int N = 500;
        for (int i = 0; i < N; i ++) {
            int operationNumber = StdRandom.uniform(0, 4);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                L.addLast(randVal);
                System.out.println("addLast(" + randVal + ")");
            } else if (operationNumber == 1) {
                // size
                int size = L.size();
                System.out.println("size: " + size);
            } else if (operationNumber == 2 && L.size() > 0) {
                // getLast
                int last = L.getLast();
                System.out.println("getLast:" + last);
            } else if (operationNumber == 3 && L.size() > 0) {
                // removeLast
                int toRemove = L.removeLast();
                System.out.println("removeLast(" + toRemove + ")");
            }
        }
    }

}
