package backend;

public class Edge{
  Node v1, v2;
  int weight;

  /**
   * The constructor for one edge
   * @param weight - edge weight
   * @param v1 - start node 
   * @param v2 - end node
   */
  public Edge(int weight, Node v1, Node v2){
    this.weight = weight;
    this.v1 = v1;
    this.v2 = v2;
  }

  /**
   * Get the two verticies this edge connects
   * @return Node[] - the nodes this edge connects
   */
  public Node[] getVertices(){
    return new Node[]{v1,v2};
  }

  /**
   * Check whether this edge contains a certain node
   * @param v - node you are checking for
   * @return boolean - whether one of this edges nodes is this node
   */
  public boolean hasVertex(Node v){
    return v1 == v || v2 == v;
  }

  /**
   * Get the weight of the edge
   * @return int - edge weight
   */
  public int getWeight(){
    return weight;
  }

}
