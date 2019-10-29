import java.util.ArrayList;
import java.util.*;
public class SpojDinics {
    public static int V;
    public static int cgraph[][];
    public static int fgraph[][];
    public static int s;
    public static int t;
    static long maxflow;

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        V = sc.nextInt();
        s = 0;
        t = V-1;
        int testcount = 1;
        cgraph = new int[V][V];
        fgraph = new int[V][V];
        int c = sc.nextInt();
        for(int i = 0; i<c; i++){
            int a,b;
            int cap;
            a = sc.nextInt() - 1;
            b = sc.nextInt() - 1;
            cap = sc.nextInt();
            cgraph[a][b] += cap;
            cgraph[b][a] += cap;
        }
        maxflow = maxflow();//Network 1 The bandwidth is 25.
        //System.out.println("Network " + testcount);
        //System.out.println("The bandwidth is " + maxflow + ".");
        //display();
        System.out.println(maxflow);
    }
    static boolean visited[];
    static int pi[];
    static int level[];
    static boolean deadend[];
    public static long maxflow(){
        int flow = 0;
        visited = new boolean[V];
        pi = new int[V];
        level = new int[V];
        while(bfs(s,t)){
           // System.out.println("Running BFS");
            visited = new boolean[V];
            pi = new int[V];
            deadend = new boolean[V];
            while(dfs(s,t)){
                int bottleNeck = Integer.MAX_VALUE;
                int current = t;
                pi[s] = -1;
                while(pi[current]!=-1){
             //       System.out.println("Running DFS");
                    int edgeCap = cgraph[pi[current]][current];
                    int edgeFlow = fgraph[pi[current]][current];
                    // System.out.println(edgeFlow + " / " + edgeCap + "between " + pi[current] + " and " + current);
                    if(bottleNeck>edgeCap-edgeFlow){
                        bottleNeck = edgeCap - edgeFlow;
                    }
                    current = pi[current];
                    //System.out.print("1");
                }
                current = t;
                while(pi[current]!=-1){
                    fgraph[pi[current]][current] += bottleNeck;
                    fgraph[current][pi[current]] -= bottleNeck;
                    current = pi[current];
                    //   System.out.print("2");
                }
                flow+=bottleNeck;
                // System.out.println("Total flow rn "+ flow);
                visited = new boolean[V];
                pi = new int[V];
                pi[s] = -1;
            }
            visited = new boolean[V];
            pi = new int[V];
            level = new int[V];
        }
        return flow;
    }
    public static boolean bfs(int s, int t){
        boolean flag = false;
        ArrayList<Integer> queue = new ArrayList<Integer>();
        queue.add(s);
        pi[s] = -1;
        int previous = s;
        level[s] = 0;
        while(!queue.isEmpty()){
            int current = queue.remove(0);
            visited[current] = true;
            if(current == t){
                flag = true;
                // System.out.println("Flow found");
            }
            for(int i = 0; i < V;i++){
                if(cgraph[current][i] > fgraph[current][i] && !visited[i]){
                    pi[i] = current;
                    level[i] = level[current] + 1;
                   // System.out.println(i + "at level" + level[i]);
                    queue.add(i);
                }
            }
        }
        //System.out.println("Flow not found");
        return flag;
    }
    static int dfsBN;

    public static boolean dfs(int s, int t){
        return(dfsUtil(s,t));
    }
    public static boolean dfsUtil(int current, int t){
        //System.out.println("Running DFS");
        //System.out.print(current + " ");
        boolean flag = false;
        if(current == t)
            return true;
        visited[current] = true;
        for(int i = 0; i < V;i++){
            if(cgraph[current][i] > fgraph[current][i] && !visited[i] && level[i] == level[current] + 1 && !deadend[i]){
                pi[i] = current;
                if(dfsUtil(i,t)){
                    return true;
                }
            }
        }
        deadend[current] = true;
        return flag;
    }
    public static void display(){
        int useCount = 0;
        for(int i = 0; i < V; i++){
            for(int j = 0; j < V; j++){
                if(fgraph[i][j] > 0) useCount++;
            }
        }
        System.out.println(V + " " + maxflow + " " + useCount);
        for(int i = 0; i < V; i++){
            for(int j = 0; j < V; j++){
                if(fgraph[i][j] > 0)
                    System.out.println(i + " " + j + " " + fgraph[i][j]);
            }
        }
    }
}
