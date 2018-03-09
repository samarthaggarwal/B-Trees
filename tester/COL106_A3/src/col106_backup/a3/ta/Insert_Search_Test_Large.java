package col106.a3.ta;

import col106.a3.BTree;
import col106.a3.DuplicateBTree;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Insert_Search_Test_Large {
    public static void main(String argv[]) throws Exception {
        try {
            long startTime = System.currentTimeMillis();
            DuplicateBTree<Integer, Integer> graph = new BTree<>(4);
            int V = 1000;
            int E = 10000;
            ArrayList<ArrayList<Integer>> g = new ArrayList<>(V);
            Random r = new Random();
            for (int i = 0; i < V; i++)
                g.add(new ArrayList<>());
            int e = 0;
            for (int i = 0; i < E; i++) {
                int v1 = r.nextInt(V);
                int v2 = r.nextInt(V);
                if (v1 != v2) {
                    e++;
                    g.get(v1).add(v2);
                    graph.insert(v1, v2);
                }
            }
            boolean failed = false;
            for (int i = 0; i < V; i++) {
                List<Integer> neighbourhood;
                try{
                    neighbourhood = graph.search(i);
                }catch(Exception exc){
                    neighbourhood = new ArrayList<>();
                }
                neighbourhood.sort(Integer::compareTo);
                ArrayList<Integer> correctAnswer = g.get(i);
                correctAnswer.sort(Integer::compareTo);
                if (!neighbourhood.equals(correctAnswer)) {
                    failed = true;
                    System.out.println("Incorrect search result for " + i);
                    System.out.println("Correct answer:" + correctAnswer);
                    System.out.println("Your answer:   " + neighbourhood);
                    break;
                }
            }
            long time = System.currentTimeMillis() - startTime;
            System.out.println(failed ? "Failed" : "Pass");
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Failed due to Exception in Insert");
        }
    }
}
