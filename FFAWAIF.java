import java.util.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.*;
import java.lang.*;
import java.util.StringTokenizer;
import java.io.OutputStreamWriter;
public class FFAWAIF {
    public static int V;
    public static int cgraph[][];
    public static int fgraph[][];
    public static int s;
    public static int t;
    static long maxflow;

    public static void main(String[] args){
        FastReader sc = new FastReader();
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
            cgraph[V-2][i] = 1;
            for(int j = 0; j < k;j++){
                int a = sc.nextInt() - 1;
                cgraph[i][children+a] = 1;

            }
        }
        for(int i = 0; i < catagories; i++){
            int num = sc.nextInt();
            for(int j = 0; j < num; j++){
                int toy = sc.nextInt() - 1;
                cgraph[toy + children][children+toys + i] = 1;
                catagorized[toy] = true;
            }
            int limit = sc.nextInt();
            cgraph[children + toys + i][V - 1] = limit;
        }
        for(int i = 0; i < toys;i++){
            if(catagorized[i]) continue;
            cgraph[children+i][V-1] = 1;
        }
        maxflow = maxflow();//Network 1 The bandwidth is 25.
        //System.out.println("Network " + testcount);
        //System.out.println("The bandwidth is " + maxflow + ".");
        /**for(int i = 0;i < V;i++){
            System.out.print(i + " printed to: ");
            for(int j = 0; j < V;j++){
                if(cgraph[i][j] != 0)
                System.out.print(j + " ");
            }
            System.out.println();
        }
        display();**/
        OutputWriter out = new OutputWriter(System.out);
        System.out.println(maxflow);
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
    static class OutputWriter {
        private final PrintWriter writer;

        public OutputWriter(OutputStream outputStream) {
            writer = new PrintWriter(
                    new BufferedWriter(new OutputStreamWriter(outputStream))
            );
        }

        public OutputWriter(Writer writer) {
            this.writer = new PrintWriter(writer);
        }

        public void print(Object... objects) {
            for (int i = 0; i < objects.length; i++) {
                if (i != 0) {
                    writer.print(' ');
                }
                writer.print(objects[i]);
            }
        }

        public void printLine(Object... objects) {
            print(objects);
            writer.println();
        }

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
