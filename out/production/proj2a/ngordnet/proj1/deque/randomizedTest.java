package deque;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import static org.junit.Assert.*;

public class randomizedTest {

    public static void main(String[] args) {
        LinkedListDeque<Integer> L = new LinkedListDeque<>();
        ArrayDeque<Integer> A = new ArrayDeque<>();
        int N = 5000;
        for (int i = 0; i < N; i ++) {
            int operationNumber = StdRandom.uniform(0, 4);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                L.addLast(randVal);
                A.addLast(randVal);
            } else if (operationNumber == 1) {
                // size
                int Lsize = L.size();
                int Bsize = A.size();
                assertEquals(Lsize, Bsize);
            } else if (operationNumber == 2 && L.size() > 0) {
                // getLast
                int Llast = L.get(L.size()-1);
                int Blast = A.get(A.size()-1);
                assertEquals(Llast, Blast);
            } else if (operationNumber == 3 && L.size() > 0) {
                // removeLast
                int LtoRemove = L.removeLast();
                int BtoRemove = A.removeLast();
                assertEquals(LtoRemove, BtoRemove);
            }
        }
    }

}
