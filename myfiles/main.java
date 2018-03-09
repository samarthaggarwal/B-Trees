import java.util.Scanner;
import java.util.List;

public class main{
	public static void main(String args[]){
		Scanner s = new Scanner(System.in);
		System.out.println("Enter b for constructing B tree");
		int b = s.nextInt();
		try{
			DuplicateBTree<String,String> tree = new BTree<>(b);
			
			for(int k=0;k<1;k++){
			System.out.println("size = " + tree.size());
			System.out.println("height = " + tree.height());
			System.out.println(tree.toString());
			
			
			tree.insert("4","four");
			//System.out.println(tree.isEmpty());
			System.out.println(tree.toString());
			System.out.println("size = " + tree.size());
			System.out.println("height = " + tree.height());
			
			tree.insert("-5","minusfive");
			System.out.println(tree.toString());
			System.out.println("size = " + tree.size());
			System.out.println("height = " + tree.height());
			
			tree.insert("-5","minusfiveb");
			System.out.println(tree.toString());
			System.out.println("size = " + tree.size());
			System.out.println("height = " + tree.height());
			
			tree.insert("10","ten");
			//System.out.println(tree.isEmpty());
			System.out.println(tree.toString());
			System.out.println("size = " + tree.size());
			System.out.println("height = " + tree.height());
			
			tree.insert("16","sixteen");
			System.out.println(tree.toString());
			System.out.println("size = " + tree.size());
			System.out.println("height = " + tree.height());
			
			tree.insert("4","fourb");
			System.out.println(tree.toString());
			System.out.println("size = " + tree.size());
			System.out.println("height = " + tree.height());
			
			
			tree.insert("10","tenb");
			System.out.println(tree.toString());
			System.out.println("size = " + tree.size());
			System.out.println("height = " + tree.height());
			
			System.out.println("******************************************************************************************************* inserting 16b");
			if(tree.par())
				System.out.println("parents set correctly");
			else
				System.out.println("parents set wrongly");
			
			tree.insert("16","sixteenb");			
			System.out.println(tree.toString());
			System.out.println("size = " + tree.size());
			System.out.println("height = " + tree.height());
			
			tree.insert("03","three");
			System.out.println(tree.toString());
			System.out.println("size = " + tree.size());
			System.out.println("height = " + tree.height());
			
			tree.insert("0","zero");
			System.out.println(tree.toString());
			System.out.println("size = " + tree.size());
			System.out.println("height = " + tree.height());
		
			System.out.println("******************************************************************************************************* inserting -3c");
			if(tree.par())
				System.out.println("parents set correctly");
			else
				System.out.println("parents set wrongly");
			//System.out.println("insert attempting");
			tree.insert("-3","minusthreec");
			//System.out.println("insert successful");
			System.out.println(tree.toString());
			System.out.println("size = " + tree.size());
			System.out.println("height = " + tree.height());
			
			tree.insert("-5","minusfivec");
			System.out.println(tree.toString());
			System.out.println("size = " + tree.size());
			System.out.println("height = " + tree.height());
			
			List<String> v = tree.search("03");
			while(v.isEmpty()==false){
				//System.out.println("entered while loop");
				System.out.print(v.get(0) + " ");
				v.remove(0);
			}
			System.out.println("\nVector is empty now");
			}
			
			System.out.println("******************************************************************************************************* deleting 4");
			if(tree.par())
				System.out.println("parents set correctly");
			else
				System.out.println("parents set wrongly");
			tree.delete("4");
			System.out.println(tree.toString());
			System.out.println("size = " + tree.size());
			System.out.println("height = " + tree.height());
			
			System.out.println("******************************************************************************************************* deleting -3");
			if(tree.par())
				System.out.println("parents set correctly");
			else
				System.out.println("parents set wrongly");
			tree.delete("-3");
			System.out.println(tree.toString());
			System.out.println("size = " + tree.size());
			System.out.println("height = " + tree.height());
			System.out.println(tree.toString());
			if(tree.par())
				System.out.println("parents set correctly");
			else
				System.out.println("parents set wrongly");
			
			System.out.println("\n******************************************************************************************************* deleting 16");
			if(tree.par())
				System.out.println("parents set correctly");
			else
				System.out.println("parents set wrongly");
			tree.delete("16");
			System.out.println(tree.toString());
			System.out.println("size = " + tree.size());
			System.out.println("height = " + tree.height());
		
			if(tree.par())
				System.out.println("parents set correctly");
			else
				System.out.println("parents set wrongly");
				
			System.out.println("\n******************************************************************************************************* deleting 0");
			if(tree.par())
				System.out.println("parents set correctly");
			else
				System.out.println("parents set wrongly");
			tree.delete("0");
			System.out.println(tree.toString());
			System.out.println("size = " + tree.size());
			System.out.println("height = " + tree.height());
			
			System.out.println("\n******************************************************************************************************* deleting -5");
			if(tree.par())
				System.out.println("parents set correctly");
			else
				System.out.println("parents set wrongly");
			tree.delete("-5");
			System.out.println(tree.toString());
			System.out.println("size = " + tree.size());
			System.out.println("height = " + tree.height());
			
			System.out.println("\n******************************************************************************************************* deleting 10");
			if(tree.par())
				System.out.println("parents set correctly");
			else
				System.out.println("parents set wrongly");
			tree.delete("10");
			System.out.println(tree.toString());
			System.out.println("size = " + tree.size());
			System.out.println("height = " + tree.height());
			
			System.out.println(tree.toString());
			
			
			System.out.println("\n******************************************************************************************************* deleting 03");
			if(tree.par())
				System.out.println("parents set correctly");
			else
				System.out.println("parents set wrongly");
			tree.delete("03");
			System.out.println(tree.toString());
			System.out.println("size = " + tree.size());
			System.out.println("height = " + tree.height());
			
			System.out.println(tree.toString());
		
			/*System.out.println("\n******************************************************************************************************* deleting 03");
			if(tree.par())
				System.out.println("parents set correctly");
			else
				System.out.println("parents set wrongly");
			tree.delete("03");
			System.out.println(tree.toString());
			System.out.println("size = " + tree.size());
			System.out.println("height = " + tree.height());
			
			System.out.println(tree.toString());*/
		}
		catch(Exception e){
			System.out.println(e);
		}
	}
}
