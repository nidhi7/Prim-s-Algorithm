
/**
 * This class creates adjacency list for both user input and random generated undirected connected graph.
 * 
 * For User Input graph: This code assumes that the user always gives correct input format and valid data in a "text file". 
 * (weights/costs are always integer type)
 * The method graph_User_Input() generates adjacency list for user input data
 * 
 * For random generated graph: The user inputs "number of vertices" and "density" of the graph
 * (weights/costs are always integer type)
 * 
 * The method prodGraph() generates adjacency list of valid connected graph by checking connectivity using Depth first Search
 * 
 * Depth First Search: DFS() takes a graph and a starting vertex number as input and returns a boolean array showing false
 * at indices which are equal to vertex numbers not reachable by some other vertex
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class Graph {
	
	
	Vertex[] adjlists; //Array of adjacency list for each vertex object
	public static int vertNo; //number of vertices
	
	
	public Graph() {
		// TODO Auto-generated constructor stub
	}
	

//This method returns adjacency list for user input graph
	public Graph graph_User_Input(String file) throws FileNotFoundException{
		int noOfEdges=0;
		
		File f=new File(file);
		
		Graph gr=new Graph();
		
		Scanner sc=new Scanner(f);
		gr.adjlists=new Vertex[sc.nextInt()];
		noOfEdges=sc.nextInt();
		int v1;
		int v2;
		int w;
		
		for(int v=0; v<gr.adjlists.length;v++){
			gr.adjlists[v]=new Vertex(v,null);
		}
		
		while(sc.hasNextLine()){
			String line=sc.nextLine();
			if(line.length()>0){
				String[] columns=line.split(" ");
				v1=Integer.parseInt(columns[0]);
				v2=Integer.parseInt(columns[1]);
				w=Integer.parseInt(columns[2]);
				
				gr.adjlists[v1].adjlist=new Neighbour(v2,w,gr.adjlists[v1].adjlist);
				gr.adjlists[v2].adjlist=new Neighbour(v1,w,gr.adjlists[v2].adjlist);
				
			}
		}
		return gr;
	}
	
	
	boolean[] vis; //To keep track of connectivity of vertices for random generated graphs
	
	/*This method returns adjacency list for random generated graph iff the graph is valid and connected
	 *Connectivity is checked by calling DFS()*
	 */
	public Graph prodGraph(int n, int density) {
		vis=new boolean[n];
		//System.out.print(vis[0]);
		for(int i=0;i<vertNo;i++){ 
			//System.out.print(vis[i]);
			vis[i]=false;
		}
		
		Graph g=new Graph();
        int totEgs=density*n*(n-1)/200;
       try{
        if(totEgs<n-1){System.out.println("The number of edges is less than n-1, so the graph can never be connected");
        System.exit(-1); 
        }
       }
       catch(Exception e){System.out.println(e.getMessage());}
        //initialise the vertex array of objects named adjlists
		g.adjlists=new Vertex[n];
		vertNo=n;
		for(int v=0; v<n;v++){
			g.adjlists[v]=new Vertex(v,null);
		}
		int chkV = 0;
		
        //generate random edges and weights
		for(int i=0;i<totEgs;)
		{
			boolean do_add=false; 
			int first=(int)(Math.random()*n); // random 0 to n -1
			int sec = (int)(Math.random()*n); // random 0 to n -1
            int weight = (int)(Math.random()*1000) + 1; // random Cost
            //if edge i,j is not in graph, add it to the graph
            if (sec != first) {
            	if(g.adjlists[first].adjlist==null){do_add=true;}
            	for (Neighbour nbr = g.adjlists[first].adjlist; nbr != null; nbr = nbr.next) {
            		
            		if(nbr.vertexNum==sec) {do_add=false; break;}
            		else do_add=true;
            }
            }
            if(do_add==true)
            {
            	g.adjlists[first].adjlist=new Neighbour(sec,weight,g.adjlists[first].adjlist);
				g.adjlists[sec].adjlist=new Neighbour(first,weight,g.adjlists[sec].adjlist);
                i++;
            chkV=first;
            }
		
}
       
		 boolean isConnected=false;
		vis=DFS(g,chkV);
		for(int i=0;i<n;i++)
        {
        	if(vis[i]==true) 
        	{isConnected=true; continue; }
        	else {isConnected=false;break;}
        }
        if(isConnected==false) {
        	{
        	java.lang.System.gc();
        
        	prodGraph(n,density);
        	}
          }
        return g;
	}
	
	//This method checks for connectivity of the random generated graph
	
	public boolean[] DFS(Graph chk, int v){
		
		vis[v]=true;
		if((chk.adjlists[v].adjlist)!=null){
		for (Neighbour nbr = chk.adjlists[v].adjlist; nbr != null; nbr = nbr.next)
		{
			if (nbr!=null && vis[nbr.vertexNum]==false)
			{	
				DFS(chk,nbr.vertexNum);
				
			}
		}
		}
		
		return vis;
	}
}
	
