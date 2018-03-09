package col106.a3.ta;

import col106.a3.BTree;
import col106.a3.DuplicateBTree;

import java.util.*;

public class Insert_Delete_Search_Test_Small {
    static boolean test(DuplicateBTree<Integer, Integer> impl, Map<Integer, Integer> correct, Set<Integer> deleted) throws Exception {
        for (Map.Entry<Integer, Integer> entry : correct.entrySet()) {
            if (!Collections.min(impl.search(entry.getKey())).equals(entry.getValue()))
                return false;
        }
        for (Integer i:deleted){
            try{
                List<Integer> res=impl.search(i);
                if(res.size()>0)
                    return false;
            }catch(Exception exc){}
        }
        return true;
    }

    static void step(DuplicateBTree<Integer, Integer> impl, Map<Integer, Integer> correct, Set<Integer> deleted, Random r) throws Exception {
        for (Iterator<Map.Entry<Integer, Integer>> it = correct.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<Integer, Integer> entry = it.next();
            if (Math.random() < 0.1 || entry.getValue() < 10) {
                impl.delete(entry.getKey());
                it.remove();
                deleted.add(entry.getKey());
            } else {
                entry.setValue(r.nextInt(entry.getValue()));
                impl.insert(entry.getKey(), entry.getValue());
            }
        }
    }

    public static void main(String[] argv) {
        try {
            DuplicateBTree<Integer, Integer> distance = new BTree<>(6);
            Map<Integer, Integer> correct = new HashMap<>();
            Set<Integer> deleted = new TreeSet<>();
            int V = 200;
            int MD = 1000;
            int NSTEPS = 200;
            Random r = new Random();
            for (int i = 0; i < V; i++) {
                int d = r.nextInt(MD);
                correct.put(i, d);
                distance.insert(i, d);
            }
            for (int i = 0; i < NSTEPS; i++) {
                if (!test(distance, correct, deleted)) {
                    System.out.println("Failed");
                    return;
                }
                step(distance, correct, deleted, r);
            }
            System.out.println("Pass");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed due to exception NO EXCPETION SHOULD OCCUR IN THIS TEST CASE, AS WE ONLY SEARCH AND DELETE FOR EXISTING KEYS");
            return;
        }
    }
}
