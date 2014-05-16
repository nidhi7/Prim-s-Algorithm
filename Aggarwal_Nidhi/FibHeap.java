/**
 * This class implements operations of fibonacci heap required for prims
 * @author Nidhi
 *
 */

public class FibHeap {
	
    private FibHeapNode root;
    private FibHeapNode[] tree= new FibHeapNode[20];
    
    //insert new node. Set parent to be null if there is no node in fibheap. root is sentinel, whether 
    private void insert(FibHeapNode x) { // add to root doubly linked circular list
        x.parent = null;
        if (root == null) {   //if root null point the node to itself else change pointers
            root = x.left = x.right = x;
            return;
        }
        // add x to circular list circular list, inserted right of root
        x.left = root.left;
        x.right = root;
        root.left.right = x;
        root.left = x;
        if (root.key > x.key) { // point to minimun node (root is always min node)
            root = x;
        }
    }

	//assigning key value, call insert()
    public synchronized FibHeapNode insert(int key, int val) {
        FibHeapNode tmp = new FibHeapNode();
        tmp.key = key;
        tmp.val = val;
        insert(tmp);
        return tmp;
    }


    public synchronized void decrease(FibHeapNode tmp, int newKey) {
        FibHeapNode parent = tmp.parent;
        tmp.key = newKey;   	//change key
        if (parent == null) { //  if tmp doesn't have a parent it is a root 
            if (root.key > tmp.key) { //if less than root set this as root
                root = tmp;
            }
            return;
        }
        if (tmp.key < parent.key) { // cut off (if tmp's weight is less than parent)
            remove(tmp);	//remove child since violates min tree property
            insert(tmp);	//insert into top level list
            
//cascade cut, whether parent has lost child earlier remove and insert in top level list, check for all till
//you find a node where cascade cut is false or you reach the top level circular list
for (tmp = parent, parent = tmp.parent; tmp.cut == true && parent != null; tmp = parent, parent = parent.parent) {
                remove(tmp);
                insert(tmp);
            }
            tmp.cut = true;
        }
    }
    
//remove from parent and sibling list, in the end decrease degree of parent
    private void remove(FibHeapNode x) {
        FibHeapNode parent = x.parent;
        if (x.right == x) {
            parent.child = null;
        } else {
            // remove x from sibling list
            x.left.right = x.right;
            x.right.left = x.left;
            if (parent.child == x) {
                parent.child = x.right;
            }
        }
        parent.degree--;
    }


    public synchronized void delete(FibHeapNode x) {
        if (root == x) {
            deleteMin();
            return;
        }
        FibHeapNode scan, pre;
        // move children of x to root
        if ((scan = x.child) != null) {
            do {
                pre = scan;
                scan = scan.right;
                insert(pre);
            } while (scan != x.child);
        }
        // delete x from parent
        FibHeapNode parent = x.parent;
        if (parent != null) {
            if (parent.child == x) {
                if (parent.degree == 1) {
                    parent.child = null;
                } else {
                    parent.child = x.right;
                }
            }
            parent.degree--;
            FibHeapNode tmp;
            for (tmp = parent, parent = tmp.parent; tmp.cut == true && parent != null; tmp = parent, parent = parent.parent) {
                remove(tmp);
                insert(tmp);
            }
            tmp.cut = true;
        }
        // remove x from sibling circular list
        x.left.right = x.right;
        x.right.left = x.left;
    }

//
    public synchronized int deleteMin() {
        if (root == null) { //heap is empty
            return -1;
        }
        int x = root.val; //get value of root
        FibHeapNode scan, pre;
        // join children of root
        if ((scan = root.child) != null) {//gets a child and join (join all children)
            do {
                pre = scan;
                scan = scan.right;
                join(pre);
            } while (scan != root.child);
        }
        // join all trees except root
        for (scan = root.right; scan != root;) { //remove childern of root and consolidate together(join all trees except root)
            pre = scan;
            scan = scan.right;
            join(pre);
        }
        root = null;
        // link all trees
        for (int i = 0; i < tree.length; i++) { //same degree have been combined, each tree will be taken and put in top level doubly linked list
            if (tree[i] != null) {
                insert(tree[i]);
                tree[i] = null;
            }
        }
        return x;
    }
    private void join(FibHeapNode tmp) { 
        while (tree[tmp.degree] != null) { //
            // join newRoot & newChild
            FibHeapNode newRoot, newChild;
            if (tree[tmp.degree].key < tmp.key) { //if node in tree array is lighter, this will become parent otherwise opposite 
                newRoot = tree[tmp.degree];
                newChild = tmp;
            } else {
                newRoot = tmp;
                newChild = tree[tmp.degree];
            }
            newChild.parent = newRoot;
            newChild.cut = false;
            if (newRoot.child == null) { //if parent has a child,  u will follow child pointer to reach list of siblings and insert into the sibling list
                newRoot.child = newChild.left = newChild.right = newChild;
            } else {			//otherwise point to itself
                // add newChild to new doubly linked circular list
                newChild.left = newRoot.child.left;
                newChild.right = newRoot.child;
                newRoot.child.left.right = newChild;
                newRoot.child.left = newChild;
            }
            tmp = newRoot;
            tree[tmp.degree++] = null;
        }
        tree[tmp.degree] = tmp;
    }
  
 
}
