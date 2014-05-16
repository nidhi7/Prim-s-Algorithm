

/**
 * This class defines one vertex object of an array of all vertices each having an adjacency list
 * @author Nidhi
 *
 */
class Vertex{
	
	int name;			//Start vertex number (vertex from which you create your adjacency list)
	Neighbour adjlist;  //Pointer to the adjacency list
	Vertex(int name,Neighbour neighbours) //Constructor
	{ 
		this.name=name;
		this.adjlist=neighbours;
	}
}
