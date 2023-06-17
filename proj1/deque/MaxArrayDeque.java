package deque;

import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque<T> {
    Comparator<T> defaultComparator;
    public MaxArrayDeque(Comparator<T> c){
        super();
        defaultComparator = c;
    }
    public T max() {
        return max(defaultComparator);
    }
    public T max(Comparator<T> c) {
        if (isEmpty()) {
            return null;
        }
        T max = get(0);
        for (int i = 1; i < size(); i ++) {
            if (c.compare(get(i), max) > 0) {
                max = get(i);
            }
        }
        return max;
    }
}
