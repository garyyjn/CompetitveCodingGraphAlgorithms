import java.util.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.StringTokenizer;
public class FFA {
    public static int V;
    public static int cgraph[][];
    public static int fgraph[][];
    public static int s;
    public static int t;
    static long maxflow;

    public static void main(String[] args){
        FastReader sc = new FastReader();
        V = sc.nextInt();
        int testcount = 1;
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
        maxflow = maxflow();//Network 1 The bandwidth is 25.
            //System.out.println("Network " + testcount);
            //System.out.println("The bandwidth is " + maxflow + ".");
        display();

    }
    static boolean visited[];
    static int pi[];
    public static long maxflow(){
        int flow = 0;
        visited = new boolean[V];
        pi = new int[V];
        while(bfs(s,t)){
            int bottleNeck = Integer.MAX_VALUE;
            int current = t;
            while(pi[current]!=-1){
                int edgeCap = cgraph[pi[current]][current];
                int edgeFlow = fgraph[pi[current]][current];
               // System.out.println(edgeFlow + " / " + edgeCap + "between " + pi[current] + " and " + current);
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
           // System.out.println("Total flow rn "+ flow);
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
    static class FastReader
    {
        BufferedReader br;
        StringTokenizer st;

        public FastReader()
        {
            br = new BufferedReader(new
                    InputStreamReader(System.in));
        }

        String next()
        {
            while (st == null || !st.hasMoreElements())
            {
                try
                {
                    st = new StringTokenizer(br.readLine());
                }
                catch (IOException  e)
                {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt()
        {
            return Integer.parseInt(next());
        }

        long nextLong()
        {
            return Long.parseLong(next());
        }

        double nextDouble()
        {
            return Double.parseDouble(next());
        }

        String nextLine()
        {
            String str = "";
            try
            {
                str = br.readLine();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            return str;
        }
    }
}
