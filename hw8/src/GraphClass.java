import java.util.ArrayList;
import java.util.Iterator;

interface Graph {

    // Accessor Methods
    /** Return the number of vertices.
        @return The number of vertices
     */
    int getNumV();
  
    /** Determine whether this is a directed graph or not.
        @return true if this is a directed graph
     */
    boolean isDirected();
  
    /** Insert a new edge into the graph.
        @param edge The new edge
     */
    void insert(Edge edge);
  
    /** Determine whether an edge exists.
        @param source The source vertex
        @param dest The destination vertex
        @return true if there is an edge from source to dest
     */
    boolean isEdge(int source, int dest);
  
    /** Get the edge between two vertices.
        @param source The source vertex
        @param dest The destination vertex
        @return The Edge between these two vertices
                or an Edge with a weight of
                Double.POSITIVE_INFINITY if there is no edge
     */
    Edge getEdge(int source, int dest);
  
    /** Return an iterator to the edges connected
        to a given vertex.
        @param source The source vertex
        @return An Iterator<Edge> to the vertices
                connected to source
     */
    Iterator < Edge > edgeIterator(int source);
}


class ListGraph implements Graph{
    private int v;
    /**
     * directed value
     */
    private boolean directed;
    /**
     * adjacency list
     */
    private ArrayList<ArrayList<Edge>> adj;

    /** Constructor
     * @param nodes
     * @param directed
     */
    ListGraph(int nodes, boolean directed) {
      this.v = nodes;
      this.directed = directed;
      this.adj = new ArrayList<ArrayList<Edge>>(nodes);
      for( int i = 0 ; i < nodes; i++ ) {
        this.adj.add(new ArrayList<Edge>());
      }
    }

    @Override
    public int getNumV() {
      return this.v;
    }

    /** Says directed or not
     * @return boolean directed or not
     */
    @Override
    public boolean isDirected() {
      return this.directed;
    }

    /** Inserts edge
     * @param edge The new edge
     */
    @Override
    public void insert(Edge edge) {
      int source = (int) edge.getSource();
      int destination = (int) edge.getDest();
      adj.get(source).add(edge);
      if(!this.directed) {
        adj.get(destination).add(new Edge(destination, source, edge.getWeight()));
      }
    }

    /** Check if there is an edge between given source and dest
     * @param source The source vertex
     * @param dest   The destination vertex
     * @return
     */
    @Override
    public boolean isEdge(int source, int dest) {
      Edge edg = new Edge(source, dest, (int)Double.POSITIVE_INFINITY);
      for( Edge e: adj.get(source)) {
        if(e.equals(edg)) {
          return true;
        }
      }
      return false;
    }

    /** Method to get edge
     * @param source The source vertex
     * @param dest   The destination vertex
     * @return edge
     */
    @Override
    public Edge getEdge(int source, int dest) {
      Edge edg = new Edge(source, dest, (int)Double.POSITIVE_INFINITY);
      for( Edge e: adj.get(source)) {
        if(e.equals(edg)) {
          return e;
        }
      }
      return edg;
    }

    /** Returns edge iterator 
    * @param source 
    * @return iterator
    */
    @Override
    public Iterator < Edge > edgeIterator(int source) {
      return adj.get(source).iterator();
    }

}

class MatrixGraph implements Graph{
    private int v;
    /**
     * directed value
     */
    private boolean directed;
    /**
     * adjacency matrix
     */
    private Edge[][] adj;

    /** Constructor
     * @param nodes
     * @param directed
     */
    MatrixGraph(int nodes, boolean directed) {
      this.v = nodes;
      this.directed = directed;
      this.adj = new Edge[nodes][nodes];
      for( int i = 0 ; i < nodes; i++ ) {
        for( int j = 0 ; j < nodes ; j++ ) {
          if( i == j ) continue;
          adj[i][j] = new Edge(i, j, (int)Double.POSITIVE_INFINITY);
        }
      }
    }

    /**
     * @return
     */
    @Override
    public int getNumV() {
      return this.v;
    }

    /**
     * Says directed or not
     * @return boolean directed or not
     */
    @Override
    public boolean isDirected() {
      return this.directed;
    }

    /**
     * Inserts edge
     * @param edge The new edge
     */
    @Override
    public void insert(Edge edge) {
      int source = (int) edge.getSource();
      int destination = (int) edge.getDest();
      adj[source][destination] = edge;
      if(!this.directed) {
        adj[destination][source] = new Edge(destination, source, edge.getWeight());
      }
    }

    /** Check if there is an edge between given source and dest
     * @param source The source vertex
     * @param dest   The destination vertex
     * @return boolean
     */
    @Override
    public boolean isEdge(int source, int dest) {
      if( adj[source][dest].getWeight() == (int)Double.POSITIVE_INFINITY ) {
        return false;
      }
      return true;
    }

    /**
     * Method to get edge
     * @param source The source vertex
     * @param dest   The destination vertex
     * @return edge value
     */
    @Override
    public Edge getEdge(int source, int dest) {
      Edge edg = new Edge(source, dest, (int)Double.POSITIVE_INFINITY);
      if( adj[source][dest].getWeight() == (int)Double.POSITIVE_INFINITY ) {
        return edg;
      }
      return adj[source][dest];
    }


    /** Returns edge iterator 
    * @param source 
    * @return iterator
    */
    @Override
    public Iterator<Edge> edgeIterator(int source) {
      ArrayList<Edge> ans = new ArrayList<Edge>();
      for( int node = 0 ; node < this.v ; node++ ) {
        if(  adj[source][node].getWeight() != (int)Double.POSITIVE_INFINITY ) {
          ans.add(adj[source][node]);
        }
      }
      return ans.iterator();
    }
}

public class GraphClass{
    /** Creates graph
     * @param type
     * @param nodes
     * @param isDirected
     * @return graph or null
     */
    public Graph createGraph(String type, int nodes, boolean isDirected) {
      if (type == null || type.isEmpty())
              return null;
          if ("ListGraph".equals(type)) {
              return new ListGraph(nodes, isDirected);
          }
          else if ("MatrixGraph".equals(type)) {
              return new MatrixGraph(nodes, isDirected);
          }
          return null;
    }
  }
  