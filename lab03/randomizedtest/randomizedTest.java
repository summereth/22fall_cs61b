package randomizedtest;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import static org.junit.Assert.*;

public class randomizedTest {


    public static void main(String[] args) {
        AListNoResizing<Integer> L = new AListNoResizing<>();
        BuggyAList<Integer> B = new BuggyAList<>();
        int N = 5000;
        for (int i = 0; i < N; i ++) {
            int operationNumber = StdRandom.uniform(0, 4);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                L.addLast(randVal);
                B.addLast(randVal);
            } else if (operationNumber == 1) {
                // size
                int Lsize = L.size();
                int Bsize = B.size();
                assertEquals(Lsize, Bsize);
            } else if (operationNumber == 2 && L.size() > 0) {
                // getLast
                int Llast = L.getLast();
                int Blast = B.getLast();
                assertEquals(Llast, Blast);
            } else if (operationNumber == 3 && L.size() > 0) {
                // removeLast
                int LtoRemove = L.removeLast();
                int BtoRemove = B.removeLast();
                assertEquals(LtoRemove, BtoRemove);
            }
        }
    }

}
