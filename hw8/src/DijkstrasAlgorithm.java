import java.util.HashSet;

public class DijkstrasAlgorithm {

  /** Dijkstras Shortest-Path algorithm.
      @param graph The weighted graph to be searched
      @param start The start vertex
      @param pred Output array to contain the predecessors
                  in the shortest path
      @param dist Output array to contain the distance
                  in the shortest path
   */
  public static void dijkstrasAlgorithm(Graph graph,
                                        int start,
                                        int[] pred,
                                        double[] dist,
                                        Operator op,
                                        int choice) {
    int numV = graph.getNumV();
    double val = -1;
    HashSet < Integer > vMinusS = new HashSet<>(numV);
    // Initialize V-S.
    for (int i = 0; i < numV; i++) {
      if (i != start) {
        vMinusS.add(i);
      }
    }
    // Initialize pred and dist.
    for (int v : vMinusS) {
      pred[v] = start;
      if(choice == 1) {
        val = graph.getEdge(start, v).getDistance();
      }
      else if(choice == 2){
        val = graph.getEdge(start, v).getTime();
      }
      else if(choice == 3){
        val = graph.getEdge(start, v).getQuality();
      }
      dist[v] = val;
    }
    // Main loop
    while (vMinusS.size() != 0) {
      // Find the value u in V-S with the smallest dist[u].
      double minDist = Double.POSITIVE_INFINITY;
      int u = -1;
      for (int v : vMinusS) {
        if (dist[v] < minDist) {
          minDist = dist[v];
          u = v;
        }
      }
      // Remove u from vMinusS.
      if( u == -1 ) {
        break;
      }
      vMinusS.remove(u);
      // Update the distances.
      for (int v : vMinusS) {
        if (graph.isEdge(u, v)) {
          double property = -1;
          if(choice == 1) {
            property = graph.getEdge(u, v).getDistance();
          }
          else if(choice == 2){
            property = graph.getEdge(u, v).getTime();
          }
          else if(choice == 3){
            property = graph.getEdge(u, v).getQuality();
          }
          if ( op.getValue( dist[u], property) < dist[v]) {
            dist[v] = op.getValue( dist[u], property);
            pred[v] = u;
          }
        }
      }
    }
    for( int i = 0 ; i < numV ; i++ ) {
    	if(choice == 1){
          if(dist[i] == 2147483647){
            System.out.format("Distance from %d to %d is %s\n", start, i, "Infinity");
          }
          else {
            System.out.format("Distance from %d to %d is %f\n", start, i, dist[i]);
          }
    	}
    	else if(choice == 2){
    	  if(dist[i] == 2147483647){
            System.out.format("Time from %d to %d is %s\n", start, i, "Infinity");
          }
    	  else {
            System.out.format("Time from %d to %d is %f\n", start, i, dist[i]);
          }
    	}
    	else if(choice == 3){
    	  if(dist[i] == 2147483647){
            System.out.format("Quality from %d to %d is %s\n", start, i, "Infinity");
          }
    	  else {
            System.out.format("Quality from %d to %d is %f\n", start, i, dist[i]);
          }
    	}
    }
  }
}
