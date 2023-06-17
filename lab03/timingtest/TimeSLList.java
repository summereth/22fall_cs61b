package timingtest;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * Created by hug.
 */
public class TimeSLList {
    private static void printTimingTable(AList<Integer> Ns, AList<Double> times, AList<Integer> opCounts) {
        System.out.printf("%12s %12s %12s %12s\n", "N", "time (s)", "# ops", "microsec/op");
        System.out.printf("------------------------------------------------------------\n");
        for (int i = 0; i < Ns.size(); i += 1) {
            int N = Ns.get(i);
            double time = times.get(i);
            int opCount = opCounts.get(i);
            double timePerOp = time / opCount * 1e6;
            System.out.printf("%12d %12.2f %12d %12.2f\n", N, time, opCount, timePerOp);
        }
    }

    public static void main(String[] args) {
        timeGetLast();
    }

    public static void timeGetLast() {
        // TODO: YOUR CODE HERE
        int M = 10000;
        int[] ns = new int[]{1000, 2000, 4000, 8000, 16000, 32000, 64000, 128000}; //, 256000, 512000, 1024000, 2048000
        SLList<Integer> testLst = new SLList<>();
        AList<Integer> Ns = new AList<>();
        AList<Double> times = new AList<>();
        AList<Integer> opCounts = new AList<>();
        for (int n: ns) {
            int i = 0;
            Ns.addLast(n);
            while (i < n) {
                testLst.addLast(i);
                i ++;
            }
            Stopwatch sw = new Stopwatch();
            int cnt = 0;
            for (int j = 0; j < M; j ++) {
                testLst.getLast();
                cnt ++;
            }
            double timeInSeconds = sw.elapsedTime();
            times.addLast(timeInSeconds);
            opCounts.addLast(cnt);
        }
        printTimingTable(Ns, times, opCounts);
    }

}
