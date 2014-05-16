
/**
 * This class defines each node of each vertex's adjacency list
 */

class Neighbour{
	
	public int vertexNum;  //vertex number of the right vertex of the edge
	public int weight;	   //weight of the edge to the right vertex
	public Neighbour next; /*pointer to the next vertex which is connected to the source vertex
							in the adjacency list*/
	
	//the below constructor initialises the Neighbour object
	public Neighbour(int vnum,int wght,Neighbour nbr)
	{
		this.vertexNum=vnum;
		this.weight=wght;
		next=nbr;
	}
}