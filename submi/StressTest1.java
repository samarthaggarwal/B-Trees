//package col106.a3.ta;

//import col106.a3.BTree;
//import col106.a3.DuplicateBTree;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StressTest1 {
    public static void main(String argv[]) throws Exception {
        long count = 0L;
        long total_time = 0L;
        int prob1 = 0;
        boolean flag = false;
        String inp = "";
        String prob = "";
        while (count <= 1000L) {
        long startTime=System.currentTimeMillis();
        Random b = new Random();
        int b1 = 2*b.nextInt(50);
        //int b1 = 4;
        int V = b.nextInt(5000)+10;
        int E = b.nextInt(40000)+1000;
        DuplicateBTree<Integer, Integer> graph = new BTree<>(b1+4);
        DuplicateBTree<Integer, Integer> graph1 = new BTree<>(b1+4);
        graph = new BTree<>(b1+4);
        graph1 = new BTree<>(b1+4);
        ArrayList<ArrayList<Integer>> g = new ArrayList<>(V);
        Random r = new Random();
        for (int i = 0; i < V; i++)
            g.add(new ArrayList<>());
        for (int i = 0; i < E; i++) {
            int v1 = r.nextInt(V);
            int v2 = r.nextInt(V);
            if (v1 != v2) {
                g.get(v1).add(v2);
                graph.insert(v1, v2);
                //System.out.println(graph);
                graph1.insert(v1, v2);
            }
        }
        //System.out.println("\nNew Graph wih b = "+(b1+4)+"! \n" + graph.toString());
        //inp = graph.toString();
        for (int i = 0; i < V; i++) {
try{
            List<Integer> neighbourhood = graph.search(i);
            //System.out.println("Search result for "+i+neighbourhood);
            neighbourhood.sort(Integer::compareTo);
            ArrayList<Integer> correctAnswer = g.get(i);
            correctAnswer.sort(Integer::compareTo);
            if (!neighbourhood.equals(correctAnswer)) {
                System.out.println("Incorrect search result for " + i);
                System.out.println(correctAnswer);
                System.out.println(neighbourhood);
                break;
            }
}
catch(Exception e){;}
        }
        for (int i = 0; i < V; i++) {
            //prob = graph.toString();
            //System.out.println(graph.toString());
            //System.out.println("Deleting " + i);
            try {
                graph.delete(i);
                if (graph.search(i).size() > 0) {
                    flag = true;System.out.println("Not deleted "+i+" completely!");System.out.println(graph);
                    break;
                }
                for (int j = i + 1; j < V; j++) {
                    if (g.get(j).size() > 0) {
                        if (graph.search(j).size() == 0) {
                            flag = true;System.out.println("Some other element lost!");
                            break;
                        }
                    }
                }
            }
            catch (IllegalKeyException e) {
                continue;
            }
            catch(Exception e){
                flag=true;
                System.out.println("Graph with problem!!: deleting: "+i+"\n"+graph1);
                for(int k=0; k<i; k++){
                    try{graph1.delete(k);}
                    catch(IllegalKeyException f){continue;}
                }
                prob1 = i;
                System.out.println(e);
            }

        }
        //System.out.println(graph.toString());

        long time=System.currentTimeMillis()-startTime;
        total_time+=time;
        System.out.println(count/10 + "%"+"\tt:"+time+"ms"+"\tb:"+(b1+4)+"\tV:"+V+"\tE="+E+" ");
        count++;
        //System.out.println((float) count/10000 + "%");
        if (!graph.toString().equals("[]")) {
            flag = true;

        }
        if(count % 1 == 0) System.gc();
        if (flag && count != 100L + 1) {
        //System.out.println(graph1.toString());
        System.out.println("\nGraph after Deletion " + graph.toString());
        System.out.println("Problem? "+ flag);
        System.out.println("Error at count " + count);
        System.out.println("Value of b " + (b1+4));
        System.out.println("Error");
        System.out.println("Graph with problem!!: at " + prob1 + "\n"+graph1);
        for(int k=0; k<=prob1; k++){
                    try{graph1.delete(k);}
                    catch(IllegalKeyException f){continue;}
               }
        System.exit(0);

        for (int i = 0; i < V; i++) {
            System.out.println(graph1.toString());
            System.out.println("" + i);
            try {
                graph1.delete(i);
                
            }
            catch (IllegalKeyException e) {
                continue;
            }

        }

        
        }
        }
        System.out.println("\n\nDONE!!");
        System.out.println("Average time = "+total_time/1000L);

    }
}
