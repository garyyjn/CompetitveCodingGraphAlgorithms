import java.util.*;

public class Dijistra {//codeforce 20c
     public static class Edge{
         int s;
         int t;
         int weight;
         public Edge(int s, int t, int weight){
             this.s = s;
             this.t = t;
             this.weight = weight;
         }
    }
     public static class Node{
         boolean visited;
         int id;
         int d;
         ArrayList<Edge> adjList;
         Node pi;
         public Node(int id, int d){
             this.id = id;
             this.d = d;
             adjList = new ArrayList<Edge>();
             pi = null;
         }
     }
     public static void main(String[] args) {
         Scanner sc = new Scanner(System.in);
         int n = sc.nextInt();
         int m = sc.nextInt();
         ArrayList<ArrayList<Integer>> g = new ArrayList<ArrayList<Integer>>();
         for (int i = 0; i < n; i++)
             g.add(new ArrayList<Integer>());
         ArrayList<ArrayList<Integer>> e = new ArrayList<ArrayList<Integer>>();
         for (int i = 0; i < n; i++)
             e.add(new ArrayList<Integer>());

         while (m-- > 0) {
             int a = sc.nextInt() - 1, b = sc.nextInt() - 1, c = sc.nextInt();
             g.get(a).add(b);
             e.get(a).add(c);
             g.get(b).add(a);
             e.get(b).add(c);
         }

         final long[] d = new long[n];
         int[] prev = new int[n];
     }
}
