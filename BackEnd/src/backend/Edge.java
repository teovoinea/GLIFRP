package backend;

public class Edge{
  Node v1, v2;
  int weight;

  public Edge(int weight, Node v1, Node v2){
    this.weight = weight;
    this.v1 = v1;
    this.v2 = v2;
  }

  public Node[] getVertices(){
    return new Node[]{v1,v2};
  }

  public boolean hasVertex(Node v){
    return v1 == v || v2 == v;
  }

  public int getWeight(){
    return weight;
  }

}
