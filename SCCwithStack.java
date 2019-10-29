import com.sun.source.tree.WhileLoopTree;

import java.util.*;
public class SCCwithStack {
    static int V;
    static int E;
    static int[][] graph;
    static int[] startTime;
    static int[] endTime;
    static int[] reverseOrder;
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        V = sc.nextInt();
        E = sc.nextInt();
        graph = new int[V][V];
        for(int i  = 0; i < E; i++){
            int a = sc.nextInt();
            int b = sc.nextInt();
            graph[a - 1][b - 1] = 1;
        }
        SCP();
    }
    public static void SCP(){
        componentlabel = new int[V];
        componentsize = new int[V];
        componenetCount = 0;
        traverseOrder = new Stack<Integer>();
        visited = new boolean[V];
        for(int i = 0; i < V;i++){
            if(!visited[i])
                dfs1(i);
        }

        visited = new boolean[V];
        for(int i = 0; i < componentlabel.length;i++){
            componentlabel[i] = 4;
        }
        while(!traverseOrder.isEmpty()){
            int next = traverseOrder.pop();
            System.out.println("Reversing ordering: " + next);
            if(!visited[next]){
                dfs2(next);
                componenetCount++;
            }
        }
        for(int i = 0; i < V;i++){
            System.out.println("Node: " + i + " component label: " + componentlabel[i] + " component size: " + componentsize[componentlabel[i]]);
        }
        for(int i = 0; i < V; i++){
            if(componentsize[componentlabel[i]] > 1){
                System.out.print("1 ");
            }else {
                System.out.print("0 ");
            }
        }
    }
    static Stack<Integer> traverseOrder;
    static boolean[] visited;
    public static void dfs1(int start){
        visited[start] = true;
        Stack<Integer> toVisit = new Stack<Integer>();
        toVisit.push(start);
        while(!toVisit.isEmpty()){
            int current = toVisit.pop();
            visited[current] = true;
            for(int i = 0; i < V;i++) {
                if (graph[current][i] == 1 && !visited[i]) {
                    toVisit.push(i);
                }
            }
            traverseOrder.push(current);
        }
    }
    static int[] componentlabel;
    static int[] componentsize;
    static int componenetCount;
    public static void dfs2(int start){
        Stack<Integer> toVisit = new Stack<Integer>();
        toVisit.push(start);
        while(!toVisit.isEmpty()){
            int current = toVisit.pop();
            componentlabel[current] = componenetCount;
            componentsize[componenetCount] += 1;
            visited[current] = true;
            System.out.println("DFS2 looking at: " + current);
            for(int i = 0; i < V; i++){
                if(graph[i][current] == 1 && !visited[i]){
                    toVisit.push(i);
                }
            }
        }
    }
}
