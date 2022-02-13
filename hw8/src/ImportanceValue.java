import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class ImportanceValue {

    /**
     * Graph list
     */
    LinkedList<Integer>[] graph;
    /**
     * Visited value
     */
    boolean[] visited;
    /**
     * Parent array
     */
    int[] parent;
    double[] nodeSigmaValue;
    int[] shortestPathIncludeIntermediateNodes;
    int[] nodeColor;
    int nodes;

    /** Constructor
     * @param n
     */
    public ImportanceValue(int n) {
        nodes = n;
        graph = new LinkedList[n];
        parent = new int[n];
        shortestPathIncludeIntermediateNodes = new int[n];
        visited = new boolean[n];
        nodeColor = new int[n];
        nodeSigmaValue = new double[n];
        for( int i = 0 ; i < n ; i++ ) {
            graph[i] = new LinkedList<>();
            shortestPathIncludeIntermediateNodes[i] = 0;
            nodeSigmaValue[i] = 0;
            nodeColor[i] = -1;
        }
    }

    /** Adds edge
     * @param from
     * @param to
     */
    public void addEdge(int from, int to) {
        graph[from].add(to);
        graph[to].add(from);
    }

    /**
     * Clears graph's information
     */
    public void clearGraphInformation() {
        for( int i = 0 ; i < nodes ; i++ ) {
            parent[i] = -1;
            visited[i] = false;
        }
    }

    /** Depth first search
     * @param node
     * @param color
     */
    public void dfs(int node, int color) {
        nodeColor[node] = color;
        for( int nxtNode: graph[node] ) {
            if( nodeColor[nxtNode] == -1 ) {
                dfs(nxtNode, color);
            }
        }
    }

    /** Breadth first search
     * @param source
     * @param destination
     * @return
     */
    public int bfs(int source, int destination) {
        clearGraphInformation();
        Queue< Integer > q = new LinkedList<>();
        Queue< ArrayList<Integer>> pathNodes = new LinkedList<ArrayList<Integer>>();
        ArrayList<Integer> arrList = new ArrayList<Integer>();
        arrList.add(source);
        q.add(source);
        visited[source] = true;
        boolean foundDestination = false;
        pathNodes.add(arrList);
        int shortestPathCount = 0;
        while( q.size() > 0 && !foundDestination) {
            int levelSz = q.size();
            HashSet <Integer> levelNodes = new HashSet<>();
            while( levelSz-- > 0 ) {
                int currentNode = q.poll();
                ArrayList<Integer> currentPathNodes = pathNodes.poll();
                int currentPathNodesSz = currentPathNodes.size();
              
                levelNodes.add(currentNode);
                if( currentNode == destination ) {
                    foundDestination = true;
                    shortestPathCount++;
                    for( int node: currentPathNodes ) {
                        if( node != source || node != destination ) {
                            shortestPathIncludeIntermediateNodes[node]++;
                        }
                    }
                }
                for( int nxtNode: graph[currentNode] ) {
                    if( visited[nxtNode] == true ) {
                        continue;
                    }
                    q.add(nxtNode);
                    currentPathNodes.add(nxtNode); // add new node to the path
                    pathNodes.add(currentPathNodes);
                    currentPathNodes.remove(currentPathNodesSz); // remove the new node

                }
            }

            if( foundDestination == false ) {
                for( int node: levelNodes) {
                    visited[node] = true;
                }
            }
        }
        return shortestPathCount;
    }

    /**
     * Finds importance values of vertices
     */
    public void calculateAllNodesImportanceValue() {

        Map<Integer, List<Integer>> groups = new HashMap<Integer, List<Integer>>();
        Map<Integer, Double > nodeImportanceValue = new HashMap<Integer, Double>();

        int color = 1;
        // figure out number of connected components in the graph
        for( int node = 0 ; node < nodes ; node++ ) {
            if( nodeColor[node] == -1 ) {
                dfs(node, color);
                color++;
            }
        }

        // grouping nodes by their color ( same color nodes are in same connected component group)
        for( int node = 0 ; node < nodes ; node++ ) {
            if( groups.get(nodeColor[node]) == null ) {
                groups.put( nodeColor[node], new ArrayList<>());
            }
            groups.get(nodeColor[node]).add(node);
        }

        // iterate through groups and find out Importance value of each vertex
        for( List<Integer> groupNodes: groups.values() ) {
            int groupSize = groupNodes.size();
            for( int i = 0 ; i < groupSize ; i++ ) {
                for( int j = i + 1 ; j < groupSize ; j++ ) {
                    int source = groupNodes.get(i);
                    int destination = groupNodes.get(j);
                    double totalShortestPath = (double) bfs(source, destination);
                    for( int node: groupNodes ) {
                        if( source == node || destination == node ) {
                            continue;
                        }
                        nodeSigmaValue[node] += ( (double) shortestPathIncludeIntermediateNodes[node] / totalShortestPath );
                        shortestPathIncludeIntermediateNodes[node] = 0; // clear the value
                    }
                }
            }
            for( int node: groupNodes ) {
                double value = ( nodeSigmaValue[node] / ( double ) ( groupSize * groupSize ) );
                nodeImportanceValue.put(node, value);
            }
        }
        for( int vertex = 0 ; vertex < nodes; vertex++ ) {
            System.out.printf("Importance Value of vertex %d is %f\n", vertex,  nodeImportanceValue.get(vertex));
        }
    }

}
