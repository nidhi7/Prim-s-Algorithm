
/**
 * This class implements Prim's algorithm using:
 * 1) Simple scheme: 			primsBySimple()
 * 2) Fibonacci Heap scheme:	primsByFibonacci()
 * @author Nidhi
 *
 */

public class PrimsImplementaion {
	
	/**
	 * To calculate minimum cost using Simple Scheme
	 */
	int[] minW;				
	/**
	 * To calculate minimum cost using Fibonacci Heap
	 */
	int minWSum;			
	/**
	 * To keep track of whether a vertex has been identified to be in the MST
	 */
	boolean traversed[];	
	/**
	 * Keeps track of the vertex which is reached by another vertex in minimum
							cost. Value at an index is the vertex reached by and index is the vertex 
							through which it is reached. Output of the vertex and and its index gives
							an edge in the MST
	 */
	int edge[];				
	
	/**
	 * This method calculates the minimum cost and the MST for an input Graph using simple scheme
	 * @param g
	 */
	public void primsBySimple(Graph g){
		
		edge=new int[g.adjlists.length];
		
		System.out.println();
		
		minW=new int[g.adjlists.length];
		traversed=new boolean[g.adjlists.length];
		
		for(int k=0;k<g.adjlists.length;k++) //initialise minW[] to a large value
		{
			minW[k]=9999;
			
		}		
		int u=0;
		
			for(int i=0; i<g.adjlists.length;i++){
		
			for(Neighbour nb=g.adjlists[u].adjlist;nb!=null;nb=nb.next){	//traverse neighbours of the vertex chosen to have minimuw weight among the ones traversed
				
				if(traversed[nb.vertexNum]==false)
				
				{
					int vn=nb.vertexNum;	
					int wt=nb.weight;
				
					if(wt<minW[vn])
					{
						minW[vn]=wt;
					
						edge[vn]=u;  
					
					}
				
				}
			}
			int temp=9999;
			int t = u;
			
			for(int j=0;j<g.adjlists.length;j++)   // to find the next vertex to be traversed
			{
				if(temp>minW[j] && traversed[j]!=true)
				{
					temp=minW[j];
					
					t=j;	
				}
		
				traversed[u]=true;		
		
			}
			u=t;	
		}
		
	}

	/**
	 * This method calculates minimum cost and MST using Fibonacci Heap
	 * @param gr
	 */
	public void primsByFibonacci(Graph gr){
		int w;
		
		FibHeapNode[] fnode = new FibHeapNode[gr.adjlists.length]; //initilise nodes of the fib heap
		
		traversed=new boolean[gr.adjlists.length];
		edge=new int[gr.adjlists.length];
		
		int u=0;
		FibHeap heap = new FibHeap();	//instantiate a fib heap
			fnode[0] = heap.insert(0, 0);  //insert vertex 0 with weight 0
			for(int i1=1;i1<gr.adjlists.length;i1++)  //insert all other vertices into heap with some max weight
			{
				
				fnode[i1]=heap.insert(10000,i1);
			}
			
			
			for(int j=0;j<gr.adjlists.length;j++){ 
				
				w=heap.deleteMin();  //delete min from the heap

				minWSum+=fnode[w].key;  //keep adding min cost
				
	          
	            for (Neighbour nbr = gr.adjlists[w].adjlist; nbr != null; nbr = nbr.next) { //traverse adjacency list of the vertex removed from the heap
					{
						
							if(traversed[nbr.vertexNum]==false){
								int vn=nbr.vertexNum;	
								int wt=nbr.weight;
								if(wt<fnode[vn].key)  //if current weight of vertex in heap is higer than new wt, then call decreasekey()
								{
									
				                    
									heap.decrease(fnode[vn], wt);
				                   			 edge[vn]=w;   //for storing the MST
								}								
							}
							
										
	        }
			
			
			
		
	}
	            traversed[w]=true;
					
		}
	}	

}
