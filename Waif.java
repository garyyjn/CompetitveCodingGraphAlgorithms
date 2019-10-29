import java.util.ArrayList;
import java.util.*;
public class Waif {
    public static int V;
    public static int cgraph[][];
    public static int fgraph[][];
    public static int s;
    public static int t;
    static long maxflow;

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int children = sc.nextInt();
        int toys = sc.nextInt();
        int catagories = sc.nextInt();
        V = children + toys + catagories + 2;//v-2 is s v -1 is t
        s = V - 2;
        t = V - 1;
        cgraph = new int[V][V];//0-children, children - children + toys
        fgraph = new int[V][V];
        boolean[] catagorized = new boolean[toys];
        for(int i = 0; i<children; i++){
            int k = sc.nextInt();
            cgraph[i][V-2] = 1;
            for(int j = 0; j < k;j++){
                int a = sc.nextInt();
                cgraph[i][children+a] = 1;
            }
        }
        for(int i = 0; i < catagories; i++){
            int num = sc.nextInt();
            for(int j = 0; j < num; j++){
                int toy = sc.nextInt();
                cgraph[toy + children][children+catagories + i] = 1;
            }
            int limit = sc.nextInt();
            cgraph[children + catagories + i][V - 1] = limit;
        }
        maxflow = maxflow();//Network 1 The bandwidth is 25.
        //System.out.println("Network " + testcount);
        //System.out.println("The bandwidth is " + maxflow + ".");
        display();
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
            visited = new boolean[V];
            pi = new int[V];
            deadend = new boolean[V];
            while(dfs(s,t)){
                int bottleNeck = Integer.MAX_VALUE;
                int current = t;
                pi[s] = -1;
                while(pi[current]!=-1){
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
        //System.out.println("Running BFS");
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
