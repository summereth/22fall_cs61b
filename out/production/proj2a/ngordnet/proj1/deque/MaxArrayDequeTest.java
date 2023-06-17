package deque;

import org.junit.Test;

import java.util.Comparator;

import static java.lang.Float.sum;
import static java.lang.Math.pow;
import static java.lang.Math.random;
import static org.junit.Assert.*;
public class MaxArrayDequeTest {
    public class compareBy<Integer> implements Comparator<Integer> {
        int a;
        int b;
        public compareBy(int a, int b) {
           this.a = a;
           this.b = b;
        }
        @Override
        public int compare(Integer o1, Integer o2) {
            double x1 = pow((((int) o1) - a), 2) - b;
            double x2 = pow((((int) o2) - a), 2) - b;
            if (x1 > x2) {
                return 1;
            }
            else if (x1 == x2) {
                return 0;
            }
            return -1;
        }
    }

    public class alwaysYesComparator<T> implements Comparator<T> {
        public alwaysYesComparator(){

        }
        @Override
        public int compare(T o1, T o2) {
            return 1;
        }
    }

    @Test
    public void testConstructor() {
        Comparator<Integer> cmp = new alwaysYesComparator<>();
        MaxArrayDeque<Integer> al = new MaxArrayDeque<>(cmp);
        al.addFirst(2);
        al.addFirst(1);
        al.addLast(3);
        assertEquals("Wrong value",al.get(al.size()-1), al.max());
    }
    @Test
    public void testComparator1() {
        Comparator<Integer> cmp1 = new compareBy<>(7, 7);
        Comparator<Integer> cmp2 = new compareBy<>(3, 8);
        MaxArrayDeque<Integer> al = new MaxArrayDeque<>(cmp1);
        for (int i = 1; i < 10; i ++) {
            al.addLast(i);
        }
        assertEquals("Wrong value",al.get(al.size()-1), al.max(cmp2));
    }
    @Test
    public void testComparator2() {
        Comparator<Integer> cmp1 = new compareBy<>(7, 7);
        Comparator<Integer> cmp2 = new compareBy<>(3, 8);
        MaxArrayDeque<Integer> al = new MaxArrayDeque<>(cmp1);
        for (int i = 1; i < 10; i ++) {
            al.addLast(i);
        }
        assertEquals("Wrong value",al.get(0), al.max(cmp1));
    }

}
