

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class mst {

	public static void main(String[] args) throws FileNotFoundException{
		
		Graph g = new Graph(); //instance of Graph class used to create adjacency lists
		PrimsImplementaion p=new PrimsImplementaion(); //instance of PrimsImplementaion to call Prims methods
		 
		 long start=0; //start of clock for random mode
		 long stop=0;  //stop of clock for random mode
			if(args.length==3) //for random mode
			{
				if(args[0].equals("-r")) //random mode
				{
					Graph.vertNo=Integer.parseInt(args[1]); //second argument is number of nodes
					Graph gra=g.prodGraph(Graph.vertNo,Integer.parseInt(args[2])); //generating random graph
					start=System.currentTimeMillis();  //start of clock for simple scheme
					p.primsBySimple(gra);			   //Prims MST by simple scheme
					stop=System.currentTimeMillis();   //stop of clock for simple scheme
					System.out.println("Time for Prims by Simple Scheme:");
					System.out.println("n="+Graph.vertNo+",den="+Integer.parseInt(args[2])+", time="+(stop-start)+"ms");
					
					System.out.println();
					
					start=System.currentTimeMillis(); //start of clock for Fibonacci scheme
					 p.primsByFibonacci(gra);		  //Prims MST by Fibonacci scheme
					 stop=System.currentTimeMillis(); //stop of clock for Fibonacci
					 System.out.println("Time for Prims by Fibonacci Heap:");
					 System.out.println("n="+Graph.vertNo+",den="+Integer.parseInt(args[2])+", time="+(stop-start)+"ms");
					 
				}
			}
			
			if(args.length==2) //for user input
			{
				Scanner s=new Scanner(new File(args[1])); //read file
				Graph.vertNo=s.nextInt(); 				  //number of vertices
				if(args[0].equals("-s"))	//if simple scheme
				{
					try {
						Graph gr=g.graph_User_Input(args[1]); //generate adjacency list
						p.primsBySimple(gr);				  //call Prims for simple scheme
						int cost=0;				
						for(int i=1;i<Graph.vertNo;i++)       //sum up the cost stored in minW[]
						{				
							cost+=p.minW[i];
						}
						System.out.println(cost);
						for(int i=1;i<Graph.vertNo;i++)		  //output the edges of the MST
						{				
							System.out.println(p.edge[i]+" "+i);
							
						
						}
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				if(args[0].equals("-f"))  //if fibonacci scheme
				{
					try {
						Graph gr=g.graph_User_Input(args[1]); //generate adjacency list
						p.primsByFibonacci(gr);				  //call prims for fibonacci
						
						System.out.println(p.minWSum);		  //display minimum cost
						for(int i=1;i<Graph.vertNo;i++)		  //display edges of MST
						{				
							System.out.println(p.edge[i]+" "+i);
							
						
						}
						
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				//System.out.println(p.minWSum);
			
			}
			
			
	}
}
