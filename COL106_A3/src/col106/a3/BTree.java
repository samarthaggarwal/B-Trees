package col106.a3;

import java.util.List;
import java.util.Vector;

class node{
	int m,i;
	Vector<Key> keys;
	Vector<Value> values;
	Vector<node> children;
	
	public node(int n){
		m=n;
		//i=0;//tells the index which is to be filled next, can be eliminated if we use v.size();
		keys = new Vector<Key>();
		values = new Vector<Value>();
		children = new Vector<node>();
	}
	
	public int size(){//returns size of the subtree rooted at this node
		int n=keys.size();
		int s=0;
		for(int i=0;i<=n;i++)
			s+=children.get(i).size();
		
		s+=n;
		return s;
	}
}

public class BTree<Key extends Comparable<Key>,Value> implements DuplicateBTree<Key,Value> {

	int m,t;
	node root;

    public BTree(int b) throws bNotEvenException {  /* Initializes an empty b-tree. Assume b is even. */
        //throw new RuntimeException("Not Implemented");
    
    		if(b%2==1)
    			throw new bNotEvenException();
    		else{
	    		m=b;
    			t=m/2;
    			root = new node(m);
    		}
    }


    @Override			
    public boolean isEmpty() {
        //throw new RuntimeException("Not Implemented");
    
    		return root.keys.isEmpty();
    }

    @Override
    public int size() {
        //throw new RuntimeException("Not Implemented");
    
    		/*int n=root.keys.size();
    		int sum=0;
    		for(int i=0;i<=n;i++)
	    		sum+=root.children.get(i).size();
    		sum += n;    		
    		return sum;
    		*/
    		return root.size();
    }

    @Override
    public int height() {
        throw new RuntimeException("Not Implemented");
    }

    @Override
    public List<Value> search(Key key) throws IllegalKeyException {
        throw new RuntimeException("Not Implemented");
    }

    @Override
    public void insert(Key key, Value val) {
        throw new RuntimeException("Not Implemented");
    }

    @Override
    public void delete(Key key) throws IllegalKeyException {
        throw new RuntimeException("Not Implemented");
    }
    
    
}
