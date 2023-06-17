package ngordnet.main;

import edu.princeton.cs.algs4.In;
import java.util.*;

public class WordNGraph {
    public HashMap<Integer, List<String>> wordMap;
    public Graph G;
    public WordNGraph(String synsetsFilename, String hyponymsFilename) {

        // Create a map to connect key & words
        wordMap = new HashMap<>();
        In synsetsFile = new In(synsetsFilename);
        while (synsetsFile.hasNextLine()) {
            String line = synsetsFile.readString();
            while (!line.contains("dummy")) {
                line = line + " " + synsetsFile.readString();
            }
            //System.out.println(line);  // to debug
            String[] strInLine = line.split(",", 3);
            int key = Integer.parseInt(strInLine[0]);
            //List<String> words = new LinkedList<>();
            String[] words = strInLine[1].split(" ");
            List<String> wordsLst = new ArrayList<>();
            for (String w : words) {
                wordsLst.add(w);
            }
            wordMap.put(key, wordsLst);
        }

        // To create the graph
        int N = wordMap.size();
        G = new Graph(N);
        In hyponymsFile = new In(hyponymsFilename);
        while (hyponymsFile.hasNextLine()) {
            String line = hyponymsFile.readLine();
            // System.out.println(line);  // to debug
            String[] strInLine = line.split(",");
            int k1 = Integer.parseInt(strInLine[0]);
            for (int i = 1; i < strInLine.length; i ++) {
                int k2 = Integer.parseInt(strInLine[i]);
                G.addEdge(k1, k2);
            }
        }
    }
    public List<String> gethypo(String word) {
        Set<String> hyposet = new HashSet<>();
        List<String> hyponyms = new ArrayList<>();
        List<Integer> hypoKeys = new ArrayList<>();

        // Get all vertices that are the child of any vertices with input word, and add them to the list hypoKeys
        for (int i : wordMap.keySet()){
            if (wordMap.get(i).contains(word)) {
                dfs(G, i, hypoKeys);
            }
        }

        // Map vertices to words, reduce the duplicates and sort the list hyponyms
        for (int k : hypoKeys) {
            hyposet.addAll(wordMap.get(k));
        }
        hyponyms.addAll(hyposet);
        Collections.sort(hyponyms);

        return hyponyms;
    }
    private void dfs(Graph G, int v, List<Integer> lst) {
        lst.add(v);
        for (int w : G.adj(v)) {
            if (!lst.contains(w)) {
                dfs(G, w, lst);
            }
        }
    }
    public static void main(String[] args) {
        String synsetsFilename = "./data/wordnet/synsets11.txt";
        String hyponymsFilename = "./data/wordnet/hyponyms11.txt";
        WordNGraph wng = new WordNGraph(synsetsFilename, hyponymsFilename);

        for (int i : wng.wordMap.keySet()) {
            System.out.print(i + ":");
            for (String x : wng.wordMap.get(i)) {
                System.out.print(" " + x);
            }
            System.out.println();
        }

        for (int v = 0; v < wng.G.V(); v ++) {
            for (int w : wng.G.adj(v)) {
                System.out.println(wng.wordMap.get(v) + "-" + wng.wordMap.get(w));
            }
        }
    }
}
