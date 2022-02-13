import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void testPart1(Scanner in) {
      String selectedGraphOption = "";
      while(true) {
        System.out.println("Enter 1 for Adjacency List Graph and 2 for Adjacency Matrix Graph ");
         int option = in.nextInt();
         if( option == 1 ) {
          selectedGraphOption = "ListGraph";
          break;
         } else if( option == 2 ) {
           selectedGraphOption = "MatrixGraph";
           break;
         } else {
           System.out.println("Invalid Entry");
         }
      }
      boolean isGraphDirected = false;
      while(true) {
        System.out.println("Enter 1 if edges are directed and 2 edges are undirected");
         int option = in.nextInt();
         if( option == 1 ) {
           isGraphDirected = true;
          break;
         } else if( option == 2 ) {
           break;
         } else {
           System.out.println("Invalid Entry");
         }
      }
      String operatorOption = "";
      while(true) {
        System.out.printf("Enter 1 to combine an edge weight with a path weight with addition operation\n");
        System.out.printf("Enter 2 to combine an edge weight with a path weight with subtraction operation\n");
        System.out.printf("Enter 3 to combine an edge weight with a path weight with multiplication operation\n");
         int option = in.nextInt();
         if( option == 1 ) {
          operatorOption = "addition";
          break;
         } else if( option == 2 ) {
           operatorOption = "subtraction";
           break;
         } else if( option == 3 ) {
          operatorOption = "multiplication";
           break;
         }else {
           System.out.println("Invalid Entry");
         }
      }

      System.out.println("Enter number of nodes in the graph");
      int nodes = in.nextInt();
      System.out.println("Enter the start node in the graph");
      int startNode = in.nextInt();
      System.out.println("Enter number of edges in the graph");
      int NumberOfEdges = in.nextInt();
    
      GraphClass graphFactory = new GraphClass();
      OperatorClass operatorFactory = new OperatorClass();
      Graph graph = graphFactory.createGraph(selectedGraphOption, nodes, isGraphDirected);
      Operator operator = operatorFactory.createOperator(operatorOption);
      int choice;
      while(true) {
          System.out.println("Please enter property of edge:\n1-Distance\n2-Time\n3-Quality");
          choice = in.nextInt();
          if (choice < 1 || choice > 3) {
              System.out.println("Invalid choice\n");
              continue;
          }
          break;
      }
      if(choice == 1){
          System.out.println("Please enter edge information in a line as such u v and parameter where u and v are nodes and parameter is distance");
      }
      else if(choice == 2){
          System.out.println("Please enter edge information in a line as such u v and parameter where u and v are nodes and parameter is time");
      }
      else if(choice == 3){
          System.out.println("Please enter edge information in a line as such u v and parameter where u and v are nodes and parameter is quality");
      }
      Scanner sc = new Scanner(System.in);
      for( int edge = 0 ; edge < NumberOfEdges ;  ) {
          String s = sc.nextLine();
          String [] ln  = s.split(" ");
          int x = Integer.parseInt(ln[0]);
          int y = Integer.parseInt(ln[1]);
          int w = Integer.parseInt(ln[2]);
          if( x < 0 || x >= nodes || y < 0 || y >= nodes ) {
            System.out.printf("Invalid Entry\n");
          }  else {
            graph.insert(new Edge(x, y, w));
            edge++;
          }
      }
      int[] pred = new int[nodes];
      double[] dist = new double[nodes];
      System.out.println("\n");
      DijkstrasAlgorithm.dijkstrasAlgorithm(graph, startNode, pred, dist, operator, choice);

    }

    public static void testPart2(Scanner in) {
        int[] vertices = new int[]{500, 1000, 2000, 5000, 10000};
        int totalGraph = 10;
        for( int graphNumber = 0 ; graphNumber < totalGraph ; graphNumber++ ) {
            int totalNode = vertices[graphNumber % 5 ];
            Map< Integer, List<Integer> > graph = new HashMap<>();
            // generate random graph
            Random rd = new Random();
            int totalEdges = rd.nextInt( ( totalNode + totalNode + rd.nextInt(totalNode) ) - 1 );
            for( int i = 0 ; i < totalEdges ; i++ ) {
                int u = rd.nextInt(totalNode);
                int v = rd.nextInt(totalNode);
                if(!graph.containsKey(u)) { // node u is not yet present
                    graph.put(u, new LinkedList<Integer>());
                }
                if(!graph.containsKey(v)) { // node v is not yet present
                    graph.put(v, new LinkedList<Integer>());
                }
                graph.get(u).add(v);
                graph.get(v).add(u);
            }

            List<Integer> nodeList = new ArrayList<>();
            for(int  i = 0 ; i < totalNode; i++ ) {
                nodeList.add(i);
            }
            ShortestPath.numberOfConnectedComponentsByBfs(nodeList, graph);
            ShortestPath.connectedComponentsByDfs(nodeList, graph);
            System.out.printf("This graph number %d is with %d vertices and %d edges\n", graphNumber + 1, totalNode, totalEdges );
            System.out.println("\n");
        }
    }

    public static void testPart3(Scanner in) {
        System.out.println("Enter number of nodes in the graph");
        int nodes = in.nextInt();
        ImportanceValue IGraph = new ImportanceValue(nodes); // 10 nodes, 0 - 9
        System.out.println("Enter number of edges in the graph");
        int totalEdges = in.nextInt();
        System.out.println("Enter edges information in a line as such u v where u and v are nodes ");
        for( int i = 0 ; i < totalEdges ;  ) {
            int u = in.nextInt();
            int v = in.nextInt();
            if( u < 0 || u >= nodes || v < 0 || v >= nodes ) {
                System.out.printf("Invalid Entry\n");
             } else {
                IGraph.addEdge(u, v);
                i++;
            }
        }
        IGraph.calculateAllNodesImportanceValue();
    }
    
    public static void main(String[] args) {
       Scanner in = new Scanner(System.in);
       System.out.println("----------------------------------------Part 1 ----------------------------------------------");
       String selectedGraphOption = "ListGraph";
       boolean isGraphDirected = false;
       String operatorOption = "addition";
       int nodes = 5;
       int startNode = 1;
       int NumberOfEdges = 7;

       GraphClass graphFactory = new GraphClass();
       OperatorClass operatorFactory = new OperatorClass();
       Graph listGraph = graphFactory.createGraph(selectedGraphOption, nodes, isGraphDirected);
       Operator operator = operatorFactory.createOperator(operatorOption);
       listGraph.insert(new Edge(0, 1, 3));
       listGraph.insert(new Edge(0, 2, 2));
       listGraph.insert(new Edge(2, 1, 7));
       listGraph.insert(new Edge(3, 2, 2));
       listGraph.insert(new Edge(4, 1, 4));
       listGraph.insert(new Edge(4, 3, 7));
       listGraph.insert(new Edge(3, 1, 5));
       int[] pred = new int[nodes];
       double[] dist = new double[nodes];
       System.out.println("Graph representation is adjacency list");
       DijkstrasAlgorithm.dijkstrasAlgorithm(listGraph, startNode, pred, dist, operator, 1);

       selectedGraphOption = "MatrixGraph";
       isGraphDirected = false;
       operatorOption = "addition";
       nodes = 7;
       startNode = 0;
       NumberOfEdges = 10;

       Graph matrixGraph = graphFactory.createGraph(selectedGraphOption, nodes, isGraphDirected);

       matrixGraph.insert(new Edge(0, 1, 3));
       matrixGraph.insert(new Edge(0, 2, 2));
       matrixGraph.insert(new Edge(2, 1, 7));
       matrixGraph.insert(new Edge(3, 2, 2));
       matrixGraph.insert(new Edge(4, 1, 4));
       matrixGraph.insert(new Edge(4, 3, 7));
       matrixGraph.insert(new Edge(3, 1, 5));
       matrixGraph.insert(new Edge(5, 6, 2));
       matrixGraph.insert(new Edge(1, 6, 9));
       matrixGraph.insert(new Edge(2, 5, 4));
       pred = new int[nodes];
       dist = new double[nodes];
       System.out.println("\nGraph representation is adjacency matrix");
       DijkstrasAlgorithm.dijkstrasAlgorithm(matrixGraph, startNode, pred, dist, operator, 1);


       System.out.printf("\n\n\n");
       System.out.println("----------------------------------------Part 2 ----------------------------------------------");
       int[] vertices = new int[]{500, 1000, 2000, 5000, 10000};
       int totalGraph = 10;
       for( int graphNumber = 0 ; graphNumber < totalGraph ; graphNumber++ ) {
           int totalNode = vertices[graphNumber % 5 ];
           Map< Integer, List<Integer> > graph = new HashMap<>();
           // generate random graph
           Random rd = new Random();
           int totalEdges = rd.nextInt( ( totalNode + totalNode + rd.nextInt(totalNode) ) - 1 );
           for( int i = 0 ; i < totalEdges ; i++ ) {
               int u = rd.nextInt(totalNode);
               int v = rd.nextInt(totalNode);
               if(!graph.containsKey(u)) { // node u is not yet present
                   graph.put(u, new LinkedList<Integer>());
               }
               if(!graph.containsKey(v)) { // node v is not yet present
                   graph.put(v, new LinkedList<Integer>());
               }
               graph.get(u).add(v);
               graph.get(v).add(u);
           }

           List<Integer> nodeList = new ArrayList<>();
           for(int  i = 0 ; i < totalNode; i++ ) {
            nodeList.add(i);
           }
           ShortestPath.numberOfConnectedComponentsByBfs(nodeList, graph);
           ShortestPath.connectedComponentsByDfs(nodeList, graph);
           System.out.printf("This graph number %d is with %d vertices and %d edges\n", graphNumber + 1, totalNode, totalEdges );
           System.out.println("\n");
       }


       System.out.printf("\n\n\n");
       System.out.println("----------------------------------------Part 3----------------------------------------------");
       ImportanceValue IGraph = new ImportanceValue(10); 
       IGraph.addEdge(0, 1 );
       IGraph.addEdge(0, 4 );
       IGraph.addEdge(0, 8 );
       IGraph.addEdge(0, 3 );
       IGraph.addEdge(0, 2 );
       IGraph.addEdge(8, 9 );
       IGraph.addEdge(7, 1 );
       IGraph.addEdge(2, 1 );
       IGraph.addEdge(4, 1 );
       IGraph.addEdge(5, 6 );
       IGraph.addEdge(8, 6 );
       IGraph.addEdge(9, 4 );
       IGraph.addEdge(2, 5 );
       IGraph.addEdge(6, 9 );
       IGraph.addEdge(1, 8 );
       IGraph.addEdge(7, 2 );
       IGraph.calculateAllNodesImportanceValue();

       System.out.printf("\n--------------------------------------------Test with User Input -----------------------------\n");

       while(true) {
        System.out.printf("\nWELCOME\n");
        System.out.printf("Please enter 1 to test Part-1\n");
        System.out.printf("Please enter 2 to test Part-2\n");
        System.out.printf("Please enter 3 to test Part-3\n");
        System.out.printf("Please enter 4 to exit the program\n");
        int option = in.nextInt();
        if( option < 1 || option > 4 ) {
            System.out.printf("Invalid Entry\n");
        } else if( option == 1 ) {
            testPart1(in);
        } else if( option == 2 ) {
            testPart2(in);
        } else if( option == 3 ) {
            testPart3(in);
        } else if( option == 4 ) {
            System.out.println("Exitting!");
            break;
        }
    }
    }
}


