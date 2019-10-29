import java.util.*;
public class adjMatrix{
    static int V;
    static int E;
    static int[][] graph;
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        V = sc.nextInt();
        E = sc.nextInt();
        graph = new int[V][V];
        for(int i  = 0; i < E; i++){
            int a = sc.nextInt();
            int b = sc.nextInt();
            graph[a - 1][b - 1] = 1;
            graph[b - 1][a - 1] = 1;
        }

    }
}
