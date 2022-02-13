import java.lang.System;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

public class ShortestPath {

     /**
     *   responsible to count the number of components in the graph by bfs algorithm
     *   @param nodes - node list
     *   @param graph
     */
    public static <T> void numberOfConnectedComponentsByBfs(List<T> nodes, Map<T, List<T>> graph) {
        long startTime = System.nanoTime();
        int n = nodes.size();
        Set< T > visited = new HashSet<>();
        int componentNumber = 0;
        for( T node: nodes) {
            if( visited.contains(node) ) { 
                continue;
            }
            componentNumber += 1;
            if(!graph.containsKey(node)) { // node with no edge
                continue;
            }
            Queue< T > q = new LinkedList<>();
            q.add(node);
            while( q.size() > 0 ) {
                T currentNode = q.poll();
                for( T nxtNode: graph.get(currentNode)) {
                    if( visited.contains(nxtNode)) {
                        continue;
                    }
                    visited.add(nxtNode);
                    q.add(nxtNode);
                }

            }
        }
        
        long endTime = System.nanoTime();  
        long timeElapsed = endTime - startTime;
        System.out.println("Finding number of components with bfs. Execution time in nanoseconds: " + timeElapsed );
        System.out.printf("Here for %d vertices number of connected components is %d\n", n, componentNumber);

    }

     /**
     *   Responsible to count the number of components in the graph by dfs algorithm
     *   @param nodes - node list
     *   @param graph
     */
    public static <E> void connectedComponentsByDfs(List<E> nodes,  Map<E, List<E>> graph) {
        long startTime = System.nanoTime();
        int n = nodes.size();
        Set<E> visited = new HashSet<>();
        int componentNumber = 0;
        for( E node: nodes) {
            if( visited.contains(node) ) { 
                continue;
            }
            componentNumber += 1;
            if(!graph.containsKey(node)) { // node with no edge
                continue;
            }
            Stack<E> st = new Stack<>();
            st.push(node);
            visited.add(node);
          
            while( st.size() > 0 ) {
                E currentNode = st.pop();
                for( E nxtNode: graph.get(currentNode)) {
                   if( visited.contains(nxtNode)) {
                       continue;
                    }
                    st.push(nxtNode);
                    visited.add(nxtNode);
                }

            }

        }
        long endTime = System.nanoTime();  
        long timeElapsed = endTime - startTime;
        System.out.println("Finding number of components with dfs. Execution time in nanoseconds: " + timeElapsed );
        System.out.printf("Here for %d vertices number of connected components is %d\n", n, componentNumber);
    }
}
