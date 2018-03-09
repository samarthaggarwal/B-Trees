package col106.a3;

import java.util.List;
import java.util.Vector;

class node<Key extends Comparable<Key>,Value>{
	int m;
	Vector<Key> keys;
	Vector<Value> values;
	Vector<node> children;
	node parent;
	
	public node(int n,node p){
		m=n;
		//i=0;//tells the index which is to be filled next, can be eliminated if we use v.size();
		keys = new Vector<Key>();
		values = new Vector<Value>();
		children = new Vector<node>();
		parent = p;
	}
	
	public int size(){//returns size of the subtree rooted at this node
		int n=keys.size();
		int s=0;
		
		if(children.isEmpty()==false){
			for(int i=0;i<=n;i++)
				s+=((node)children.get(i)).size();
		}
		s+=n;
		return s;
	}
	
	public void /*List<Value>*/ search(Key /*extends Comparable<Key>*/ key,Vector<Value> v){
		//System.out.println("received search for node with keys " + keys.get(0));
		int i=0,j=0;
		
		while(i<keys.size() && (/*Comparable<Key>*/keys.get(i)).compareTo(key)<0)//WHY WAS TYPECASTING NOT REQUIRED HERE BECAUSE GET METHOD RETURN AN OBJECT?
			i++;
		while(j<keys.size() && (/*Comparable<Key>*/keys.get(j)).compareTo(key)<=0)
			j++;
		j--;
		
		/*if(i>=keys.size())
			i--;
		if(j<0)
			j++;
		*/
		
		if(j==-1){//all keys are > given key
			if(children.isEmpty()==false)
				((node)children.get(0)).search(key,v);
			
			return;
		}
		
		else if(i==keys.size()){
			if(children.isEmpty()==false)
				((node)children.get(i)).search(key,v);
			
			return;
		}
		
		else{
			if(i>j){
				if(children.isEmpty()==false)
					((node)children.get(i)).search(key,v);
					
				return;
			}
			else{
				for(int k=i;k<=j;k++){
					v.addElement(values.get(k));
					//System.out.println("adding " + values.get(k) + " to vector");
					if(children.isEmpty()==false)
						((node)children.get(k)).search(key,v);
				}
				if(children.isEmpty()==false)
					((node)children.get(j+1)).search(key,v);
			}
		}
	}
	
	/*public node delete(node root){
		node temp_root = root;
		node temp = root;
		
		int i=0;
		while(temp.keys.get(i)!=)
		
		
		return temp_root;
	}
	*/
	public String toString(){
		//if(values.isEmpty()==false)
		//	System.out.println("called toString for node " + values.get(0) );
		String s = "[";
		int x = keys.size();
		
		//System.out.println("q2");
		if(children.isEmpty()){//when n is a leaf node
			for(int i=0;i<x;i++){
				if(i!=0)
					s+=",";
				s+=keys.get(i)+"="+values.get(i);
			}
		}
		else{
			for(int i=0;i<=x;i++){
				s+=((node)children.get(i)).toString();
				
				if(i<x){
					s+=",";
					
					s+=keys.get(i)+"="+values.get(i);
					
					s+=",";					
				}
			}
		}
		//System.out.println("q3");
		
		s+= "]";
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
    			root = new node(m,null);
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
        //throw new RuntimeException("Not Implemented");
    		if(this.isEmpty()==true)
    			return -1;
    		else{
    			int h=0;
    			node temp=root;
    			while(temp.children.isEmpty()==false){
    				h++;
    				temp=(node)temp.children.get(0);
    			}
    			
    			return h;
    		}
    }

    @Override
    public List<Value> search(Key key) throws IllegalKeyException {
        //throw new RuntimeException("Not Implemented");
    
    	//System.out.println("search called");
    	Vector<Value> v = new Vector<>();
    	
    	root.search(key,v);
    	
    	if(v.isEmpty())
    		throw new IllegalKeyException();
    	else
    		return v;
    }

    @Override
    public void insert(Key key, Value val) {
        //throw new RuntimeException("Not Implemented");
    
    		//empty tree
    		if(isEmpty()){//removed this from here
    			root.keys.insertElementAt(key,0);
    			root.values.insertElementAt(val,0);
    			return;
    		}
    		
    		else{
    			node temp = root;
    			int i=-1;
    			while(i<0){
    				i=0;
    				while( i<temp.keys.size() && ((Comparable<Key>)temp.keys.get(i)).compareTo(key)<0)
    					i++;
    					
    				if(temp.children.isEmpty()==false){
    					temp = (node)temp.children.get(i);
    					i=-1;
    				}
    			}//finally temp is a leaf node
    			
    			temp.keys.insertElementAt(key,i);
    			temp.values.insertElementAt(val,i);
    			
    			if(temp.keys.size()==m)//to correct overflow
    				splitnode(temp);
    		}
    		
    		//System.out.println("insert successful in function");
    		
    		/*//tree with only root node ie. root, consider overflow possibility
    		else if(root.children.isEmpty()){
    			int i=0;
    			while(((Comparable<Key>)root.keys.get(i)).compareTo(key)<0)
    				i++;
    			
    			root.keys.insertElementAt(key,i);
    			root.values.insertElementAt(val,i);
    			
    			//check for overflow CONTINUE FROM HERE, 19/10, 5:15PM
    			if(root.keys.size()==m){
    				splitnode(root);
    			}
    		}
    		
    		//tree with multiple nodes
    		else{
    		
    		}*/
    }
    
    private void splitnode(node n){
    	//System.out.println("called splitnode");
    	node p;
    	int i=0;
    	if(n.parent==null){
    		//System.out.println("height of tree is increasing, creating a new root\n");
    		p=new node(m,null);
    		root=p;
    		n.parent=p;
    		p.children.insertElementAt(n,i);
    	}
    	else{
    		p=n.parent;
    		i=p.children.indexOf(n);
    	}
    	
    	//System.out.println("hi1");
    	node temp = new node(m,p);
    	p.children.insertElementAt(temp,i+1);
    	p.keys.insertElementAt((Key)n.keys.get(m/2),i);
    	p.values.insertElementAt((Value)n.values.get(m/2),i);
    	//System.out.println("hi2");
    	
    	for(int j=0;j<(m/2)-1;j++){
    		temp.keys.insertElementAt((Key)n.keys.get(j+t+1),j);
    		temp.values.insertElementAt((Value)n.values.get(j+t+1),j);
    		//temp.children.insertElementAt((node)n.children.get(j+t+1),j);
    	}
    	for(int j=0;j<t;j++){
    		n.keys.remove(t);
    		n.values.remove(t);
    		//n.children.remove(t+1);
    	}
    	//System.out.println("hi3");
    	
    	if(n.children.isEmpty()==false){//when n is not a leaf node
    		for(int j=0;j<t;j++)
    			temp.children.insertElementAt((node)n.children.get(j+t+1),j);
    			
    		for(int j=0;j<t;j++)
    			n.children.remove(t+1);
    			
    		for(int j=0;j<t;j++)
    			((node)temp.children.get(j)).parent=temp;
    	}
    	//System.out.println("hi4");
    	//System.out.println(toString());
    	//System.out.println("hi5");
    	
    	if(p.keys.size()==m)
    		splitnode(p);
    }

    @Override
    public void delete(Key key) throws IllegalKeyException {
    	//System.out.println("called delete");
        //throw new RuntimeException("Not Implemented");
    	
    	//try{
    		List<Value> v = search(key);
    	/*}
    	catch(IllegalKeyException e){
    		throw new IllegalKeyException();
    		return;
    	}*/
    	
    	node temp,temp2;
    	int i;
    	
    	for(;v.isEmpty()==false;v.remove(0)){
		
		/*System.out.println("deleting occurence");

			if(par())
				System.out.println("parents set correctly");
			else
				System.out.println("parents set wrongly");
		*/
		if(underflow(root) && root.children.isEmpty()==false){
			root = (node)root.children.get(0);
			root.parent = null;
		}
		
		temp=root;
		i=0;
		
		while( i<temp.keys.size() && ((Key)temp.keys.get(i)).compareTo(key)<0 )
			i++;
		
		while(i>=temp.keys.size() || ((Key)temp.keys.get(i)).compareTo(key)!=0/*temp.keys.get(i)!=key*/){
			/*if(i>=(temp.keys.size()))
				System.out.println("first true" + " i= " + i + " temp.keys.size() = " + temp.keys.size());
			else if(((Key)temp.keys.get(i)).compareTo(key)!=0)
				System.out.println("second true");
			else
				System.out.println("both false");
			
			System.out.println("in while");
			*/
			temp = (node)temp.children.get(i);//no need to check if children.isEmpty()==false

			int z=temp.parent.children.indexOf(temp);
			//System.out.println(toString());
			underflow(temp);
			//System.out.println(toString());
			//System.out.println("hi3");
			
			if(temp.keys.size()==0){
				temp = (node)temp.parent.children.get(z-1);
				i=0;
				while( i<temp.keys.size() && ((Key)temp.keys.get(i)).compareTo(key)<0 )
					i++;
				continue;
			}
			
			i=0;
			//System.out.println("in i increaser with size = " + temp.keys.size());
			
			while( i<temp.keys.size() && ((Key)temp.keys.get(i)).compareTo(key)<0 ){
				i++;
			}
				
			//System.out.println("hi4");
		}
		
		/*while(temp.keys.get(i)!=key){
			System.out.println("in while loop");
			
			underflow(temp);
			i=0;
			while( i<temp.keys.size() && ((Key)temp.keys.get(i)).compareTo(key)<0 )
				i++;
				
			System.out.println("hi");
			if( i<temp.keys.size() && temp.keys.get(i)==key){//INDEX OUT OF BOUND ERROR HERE
				System.out.println("hi3");		
				break;
			}
			else{
				System.out.println("hi2");
				temp = (node)temp.children.get(i);//no need to check if children.isEmpty()==false
			}
			
			System.out.println("hi");
		}*/
		
		//System.out.println("hi5");
		
		if(temp.children.isEmpty()==false){//if temp is not a leaf node
			temp2 = (node)temp.children.get(i+1);
			//underflow(temp2);
			while(temp2.children.isEmpty()==false){
				temp2 = (node)temp2.children.get(0);
				//underflow(temp2);
			}
		
			temp.keys.set(i,temp2.keys.get(0));
			temp.values.set(i,temp2.values.get(0));
		
			temp2.keys.remove(0);
			temp2.values.remove(0);
			//underflow(temp2);
			maintain(temp2);//to check if temp2.keys.size()<t-1 and if so corrects it.
		}
		else{
			temp.keys.remove(i);
			temp.values.remove(i);
			
			//underflow(temp);
			maintain(temp);
		}

		//System.out.println(toString());
	}
	
	/*System.out.println("hisammy");
	if(underflow(root))
		System.out.println("update root");
	else
		System.out.println("dont update root");
	*/
	
	
	if(underflow(root) && root.children.isEmpty()==false){
		//System.out.println("hisam");
		root = (node)root.children.get(0);
		root.parent = null;
	}
    }
    
    public void maintain(node n){//to check if n.keys.size()<t-1 and if so corrects it.
    	/*System.out.println("called maintain");
			if(par())
				System.out.println("parents set correctly");
			else
				System.out.println("parents set wrongly");
    	*/
    	if(n.parent!=null && n.keys.size()<t-1){
	    	int i,j,k;
	    	node p,sib;
	    	
	    	//else if(n.keys.size()<t && n.parent!=null){//general case
	    		p = n.parent;
	    		j=p.children.indexOf(n);
	    		
	    		if(j>0 && ((node)p.children.get(j-1)).keys.size()>=t){//right rotate, borrow from left sibling
	    			sib = ((node)p.children.get(j-1));//sib = left sibling of n
	    			k=sib.keys.size();
	    			
	    			n.keys.insertElementAt(p.keys.get(j-1),0);
	    			n.values.insertElementAt(p.values.get(j-1),0);
	    			
	    			p.keys.set(j-1,sib.keys.get(k-1));
	    			p.values.set(j-1,sib.values.get(k-1));
	    			
	    			sib.keys.remove(k-1);
	    			sib.values.remove(k-1);
	    			
	    			if(sib.children.isEmpty()==false){
		    			n.children.insertElementAt(sib.children.get(k),0);
	    				sib.children.remove(k);
	    				((node)n.children.get(0)).parent = n;
	    			}
	    			
	    			return;
	    		}
	    		else if( (j<p.children.size()-1) && ((node)p.children.get(j+1)).keys.size()>=t){//left rotate, borrow from right sibling
	    			sib = ((node)p.children.get(j+1));//sib = right sibling of n
	    			k=n.keys.size();
	    			
	    			n.keys.insertElementAt(p.keys.get(j),k);
	    			n.values.insertElementAt(p.values.get(j),k);
	    			
	    			p.keys.set(j,sib.keys.get(0));
	    			p.values.set(j,sib.values.get(0));
	    			
	    			sib.keys.remove(0);
	    			sib.values.remove(0);
	    			
	    			if(sib.children.isEmpty()==false){
		    			n.children.insertElementAt(sib.children.get(0),k+1);
	    				sib.children.remove(0);
	    				((node)n.children.get(k+1)).parent = n;
	    			}
	    			
	    			return;
	    		}
	    		else{//borrow from parent and merge
	    			if(j>0){//merge with left sibling
	    				sib = ((node)p.children.get(j-1));
	    				k=sib.keys.size();
	    				
	    				sib.keys.addElement(p.keys.get(j-1));
	    				sib.values.addElement(p.values.get(j-1));
	    				
	    				p.keys.remove(j-1);
	    				p.values.remove(j-1);
	    				
	    				while(n.keys.isEmpty()==false){
	    					sib.keys.addElement(n.keys.get(0));
	    					sib.values.addElement(n.values.get(0));
	    					
	    					n.keys.remove(0);
	    					n.values.remove(0);    					
	    				}
	    				
					while(n.children.isEmpty()==false){
	  					sib.children.addElement(n.children.get(0));
	  					((node)n.children.get(0)).parent = sib;
	  					n.children.remove(0);
					}
	    				
	    				p.children.remove(j);
	    				
	    				//if(sib.keys.size()>=2*t || sib.children.size()>2*t)//REMOVE AFTERWARDS
	    				//	System.out.println("ERROR IN MERGING WITH LEFT SIBLING IN MAINTAIN");
	    				
	    			}
	    			else if(j<p.children.size()-1){//merge with right sibling
					sib = ((node)p.children.get(j+1));
	    				k=n.keys.size();
	    				
	    				n.keys.addElement(p.keys.get(j));
	    				n.values.addElement(p.values.get(j));
	    				
	    				p.keys.remove(j);
	    				p.values.remove(j);
	    				
	    				while(sib.keys.isEmpty()==false){
	    					n.keys.addElement(sib.keys.get(0));
	    					n.values.addElement(sib.values.get(0));
	    					
	    					sib.keys.remove(0);
	    					sib.values.remove(0);    					
	    				}
	    				
					while(sib.children.isEmpty()==false){
	  					n.children.addElement(sib.children.get(0));
	  					((node)sib.children.get(0)).parent = n;
	  					sib.children.remove(0);
					}
	    				
	    				p.children.remove(j+1);
	    				
	    				//if(n.keys.size()>=2*t || n.children.size()>2*t)//REMOVE AFTERWARDS
	    				//	System.out.println("ERROR IN MERGING WITH RIGHT SIBLING IN MAINTAIN");    				
	    			}
	    			else
	    				//System.out.println("ERROR IN MAINTAIN IN MERGING, case not addressed j = " + j);
	    				
	    			maintain(p);
	    		}
	    	//}
    	}//end brack
    }
    
    public boolean par(){
    	return parentcheck(root);
    }
    
    public boolean parentcheck(node n){
    	boolean ans = true;
    	for(int i=0;i<n.children.size();i++){
    		ans = ans && ((node)n.children.get(i)).parent==n;
    	}
    
    	for(int i=0;i<n.children.size();i++){
    		ans = ans && parentcheck((node)n.children.get(i));
    	}
    	
    	return ans;
    }
    
    public boolean underflow(node n){//checks and repairs underflow at a node. returns true if root is to be updated to root.children else returns false.
    	/*System.out.print("called underflow for node with key ");    	
    	if(n.keys.isEmpty()==false)
    		System.out.println(n.keys.get(0));

			if(par())
				System.out.println("parents set correctly");
			else
				System.out.println("parents set wrongly");	
    	*/
    	int i,j,k;
    	node p,sib;
    	
    	if(n.parent==null && n.keys.isEmpty()){//if root is empty, merge and return true
    		return true;
    	}
    	
    	else if(n.keys.size()<t && n.parent!=null){//general case
    		p = n.parent;
    		j=p.children.indexOf(n);
    		//System.out.println("j = " + j);
   		//System.out.println(p.keys.size());
    		
    		if(j>0 && ((node)p.children.get(j-1)).keys.size()>=t){//right rotate, borrow from left sibling
    			sib = ((node)p.children.get(j-1));//sib = left sibling of n
    			k=sib.keys.size();
    			
    			n.keys.insertElementAt(p.keys.get(j-1),0);
    			n.values.insertElementAt(p.values.get(j-1),0);
    			
    			p.keys.set(j-1,sib.keys.get(k-1));
    			p.values.set(j-1,sib.values.get(k-1));
    			
    			sib.keys.remove(k-1);
    			sib.values.remove(k-1);
    			
    			if(sib.children.isEmpty()==false){
	    			n.children.insertElementAt(sib.children.get(k),0);
    				sib.children.remove(k);
    				((node)n.children.get(0)).parent = n;
    			}
    			
    			return false;
    		}
    		else if( (j<p.children.size()-1) && ((node)p.children.get(j+1)).keys.size()>=t){//left rotate, borrow from right sibling
    			sib = ((node)p.children.get(j+1));//sib = right sibling of n
    			k=n.keys.size();
    			
    			n.keys.insertElementAt(p.keys.get(j),k);
    			n.values.insertElementAt(p.values.get(j),k);
    			
    			p.keys.set(j,sib.keys.get(0));
    			p.values.set(j,sib.values.get(0));
    			
    			sib.keys.remove(0);
    			sib.values.remove(0);
    			
    			if(sib.children.isEmpty()==false){
	    			n.children.insertElementAt(sib.children.get(0),k+1);
    				sib.children.remove(0);
    				((node)n.children.get(k+1)).parent = n;
    			}
    			
    			return false;
    		}
    		else{//borrow from parent and merge
    			if(j>0){//merge with left sibling
    				//System.out.println("merging with left sibling");
    				sib = ((node)p.children.get(j-1));
    				k=sib.keys.size();
    				
    				sib.keys.addElement(p.keys.get(j-1));
    				sib.values.addElement(p.values.get(j-1));
    				
    				p.keys.remove(j-1);
    				p.values.remove(j-1);
    				
    				while(n.keys.isEmpty()==false){
    					sib.keys.addElement(n.keys.get(0));
    					sib.values.addElement(n.values.get(0));
    					
    					n.keys.remove(0);
    					n.values.remove(0);    					
    				}
    				
				while(n.children.isEmpty()==false){
  					sib.children.addElement(n.children.get(0));
  					((node)n.children.get(0)).parent = sib;
  					n.children.remove(0);
				}
    				
    				p.children.remove(j);
    				
    				//if(sib.keys.size()>=2*t || sib.children.size()>2*t)//REMOVE AFTERWARDS
    				//	System.out.println("ERROR IN MERGING WITH LEFT SIBLING");
    				
    			}
    			else if(j<p.children.size()-1){//merge with right sibling
				sib = ((node)p.children.get(j+1));
    				k=n.keys.size();
    				
    				n.keys.addElement(p.keys.get(j));
    				n.values.addElement(p.values.get(j));
    				
    				p.keys.remove(j);
    				p.values.remove(j);
    				
    				while(sib.keys.isEmpty()==false){
    					n.keys.addElement(sib.keys.get(0));
    					n.values.addElement(sib.values.get(0));
    					
    					sib.keys.remove(0);
    					sib.values.remove(0);    					
    				}
    				
				while(sib.children.isEmpty()==false){
  					n.children.addElement(sib.children.get(0));
  					((node)sib.children.get(0)).parent = n;
  					sib.children.remove(0);
				}
    				
    				p.children.remove(j+1);
    				
    				//if(n.keys.size()>=2*t || n.children.size()>2*t)//REMOVE AFTERWARDS
    				//	System.out.println("ERROR IN MERGING WITH RIGHT SIBLING");    				
    			}
    		
    		}
    		return false;
    	}
    	
    	else{//no change
    		return false;
    	}
    	
    	//return false;
    }
    
    public String toString(){
    	/*System.out.println("no of keys in root = " + root.keys.size());
    	if(root.children.isEmpty() && root.parent==null)
    		System.out.println("tree has only one node");
    	else if(root.children.isEmpty())
    		System.out.println("this node is a leaf");
    	else if(root.parent==null)
    		System.out.println("this node is a root");
    	*/
    	return root.toString();
    }
    
}
