import java.util.*;
public class KattisFlow {
    public static int V;
    public static int cgraph[][];
    public static int fgraph[][];
    public static int s;
    public static int t;
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        V = sc.nextInt();
        cgraph = new int[V][V];
        fgraph = new int[V][V];
        int c = sc.nextInt();
        s = sc.nextInt();
        t = sc.nextInt();
        for(int i = 0; i<c; i++){
                int a,b;
                int cap;
                a = sc.nextInt();
                b = sc.nextInt();
                cap = sc.nextInt();
                cgraph[a][b] = cap;
        }
        int maxflow = maxflow();//Network 1 The bandwidth is 25.
        System.out.println("The bandwidth is " + maxflow + ".");

    }
    static boolean visited[];
    static int pi[];
    public static int maxflow(){
        int flow = 0;
        visited = new boolean[V];
        pi = new int[V];
        while(bfs(s,t)){
            int bottleNeck = Integer.MAX_VALUE;
            int current = t;
            while(pi[current]!=-1){
                int edgeCap = cgraph[pi[current]][current];
                int edgeFlow = fgraph[pi[current]][current];
                System.out.println(edgeFlow + " / " + edgeCap + "between " + pi[current] + " and " + current);
                if(bottleNeck>edgeCap-edgeFlow){
                    bottleNeck = edgeCap - edgeFlow;
                }
                current = pi[current];
            }
            current = t;
            while(pi[current]!=-1){
                fgraph[pi[current]][current] += bottleNeck;
                fgraph[current][pi[current]] -= bottleNeck;
                current = pi[current];
            }
            flow+=bottleNeck;
            System.out.println("Total flow rn "+ flow);
            visited = new boolean[V];
            pi = new int[V];
        }
        return flow;
    }
    public static boolean bfs(int s, int t){
        boolean flag = false;
        ArrayList<Integer> queue = new ArrayList<Integer>();
        queue.add(s);
        pi[s] = -1;
        int previous = s;
        while(!queue.isEmpty()){
            int current = queue.remove(0);
            visited[current] = true;
            if(current == t){
                flag = true;
                // System.out.println("Flow found");
                return flag;
            }
            for(int i = 0; i < V;i++){
                if(cgraph[current][i] > fgraph[current][i] && !visited[i]){
                    pi[i] = current;
                    queue.add(i);
                }
            }
        }
        //System.out.println("Flow not found");
        return flag;
    }
}
