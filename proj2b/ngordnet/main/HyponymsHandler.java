package ngordnet.main;

import ngordnet.hugbrowsermagic.NgordnetQuery;
import ngordnet.hugbrowsermagic.NgordnetQueryHandler;
import ngordnet.ngrams.NGramMap;
import ngordnet.ngrams.TimeSeries;
import org.eclipse.jetty.util.ArrayUtil;

import java.util.*;

public class HyponymsHandler extends NgordnetQueryHandler{
    private WordNGraph wng;
    private NGramMap ngm;
    public HyponymsHandler(WordNGraph wng, NGramMap ngm) {
        this.wng = wng;
        this.ngm = ngm;
    }
    @Override
    public String handle(NgordnetQuery q) {
        System.out.println("Got query that looks like:");
        System.out.println("Words: " + q.words());

        // Generate common hyponyms of input words;
        int wdnum = q.words().size();
        List<String>[] arrHypo = new List[wdnum];
        List<String> hyponyms;
        if (wdnum == 1) {
            hyponyms = wng.gethypo(q.words().get(0));
        } else {
            for (int i = 0; i < wdnum; i ++) {
                arrHypo[i] = wng.gethypo(q.words().get(i));
            }
            hyponyms = findCommon(arrHypo);
        }

        // Generate Top K hyponyms according to popularity;
        int k = q.k();
        if (k != 0) {
            HashMap<String, Double> popularity = calPopu(hyponyms,q.startYear(),q.endYear());
            hyponyms = generateTopK(hyponyms, popularity, k);
        }

        System.out.println("Hyponyms: " + hyponyms.toString());
        return hyponyms.toString();
    }

    private List<String> generateTopK(List<String> lst, HashMap<String, Double> map, int k) {
        List<String> sorted = new ArrayList<>();
        List<Double> sortValue = new ArrayList<>();
        for (String w : map.keySet()) {
            if (map.get(w) != 0) {
                sortValue.add(map.get(w));
            }
        }
        Collections.sort(sortValue);
        Collections.reverse(sortValue);
        int count = 0;
        for (int i = 0; i < sortValue.size(); i ++) {
            for (String w : lst) {
                if (map.get(w) == sortValue.get(i)) {
                    sorted.add(w);
                    count += 1;
                    break;
                }
            }
            if (count == k) {
                break;
            }
        }
        return sorted;
    }

    // Calculate the total number of times the word appears over the entire time period (a~b) and return a map
    private HashMap<String, Double> calPopu(List<String> lst, int a, int b) {
        HashMap<String, Double> result = new HashMap<>();
        for (String w : lst) {
            TimeSeries w_ts = ngm.countHistory(w, a, b);
            List<Double> w_ts_v = w_ts.data();
            double sum = w_ts_v.stream().mapToDouble(Double::doubleValue).sum();
            result.put(w, sum);
        }
        return result;
    }
    private List<String> findCommon(List<String>[] arr) {
        List<String> result = new ArrayList<>();
        int size = arr.length;
        for (String w : arr[0]) {
            boolean common = true;
            for (int i = 1; i < size; i ++) {
                if (!arr[i].contains(w)) {
                    common = false;
                    break;
                }
            }
            if (common) {
                result.add(w);
            }
        }
        return result;
    }
    // main for debug
    public static void main(String[] args) {
        List<String> qword = new ArrayList<>();
        qword.add("change");
        qword.add("occurrence");
        NgordnetQuery q = new NgordnetQuery(qword,2000,2020, 2);

        String synsetFile = "./data/wordnet/synsets16.txt";
        String hyponymFile = "./data/wordnet/hyponyms16.txt";
        String wordFile = "./data/ngrams/top_14377_words.csv";
        String countFile = "./data/ngrams/total_counts.csv";

        WordNGraph wng = new WordNGraph(synsetFile, hyponymFile);
        NGramMap ngm = new NGramMap(wordFile,countFile);
        HyponymsHandler handler = new HyponymsHandler(wng,ngm);
        handler.handle(q);
    }
}
