package col106.a3.ta;

import col106.a3.BTree;
import col106.a3.DuplicateBTree;
import col106.a3.ta.StringFormatChecker;

import java.util.Random;

public class Insert_toString_Test {
    public static void main(String argv[]) throws Exception{
        DuplicateBTree<Integer, Integer> tree = new BTree<>(4);
        Random r = new Random();
        for (int i = 0; i < 10; i++) {
            StringFormatChecker checker = new StringFormatChecker(tree.toString(), 4);
            if (!checker.verify()) {
                System.out.println("Failed on \"" + checker.argument+"\"");
                return;
            }
            for (int j = 0; j < 4; j++)
                tree.insert(r.nextInt(10), r.nextInt());
        }
        System.out.println("Passed");
    }
}
