import com.sun.source.tree.WhileLoopTree;

import java.sql.Array;
import java.util.*;
public class SCP {
    static int V;
    static int E;
    static ArrayList<ArrayList<Integer>> graph;
    static ArrayList<ArrayList<Integer>> reverseGraph;
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        V = sc.nextInt();
        E = sc.nextInt();
        graph = new ArrayList<>();
        reverseGraph = new ArrayList<>();
        for(int i  = 0 ; i < V; i++){
            graph.add(new ArrayList<Integer>());
            reverseGraph.add(new ArrayList<Integer>());
        }
        for(int i  = 0; i < E; i++){
            int a = sc.nextInt();
            int b = sc.nextInt();
            graph.get(a-1).add(b-1);
            reverseGraph.get(b-1).add(a-1);
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
       // while(!traverseOrder.isEmpty()){
            //System.out.println(traverseOrder.pop());
       // }

        visited = new boolean[V];
        componentMembers = new ArrayList[V];
        for(int i = 0; i < V; i++){
            componentMembers[i] = new ArrayList<Integer>();
        }
        while(!traverseOrder.isEmpty()){
            int next = traverseOrder.pop();
            if(!visited[next]){
                dfs2(next);
                componenetCount++;
            }
        }
        /**for(int i = 0; i < V;i++){
            for(int j = 0; j < componentMembers[i].size();j++){
                System.out.print(componentMembers[i].get(j));
            }
            System.out.println();
        }**/
        /**for(int i = 0; i < V;i++){
            System.out.println("Node: " + i + " component label: " + componentlabel[i] + " component size: " + componentsize[componentlabel[i]]);
        }**/
        /**for(int i = 0; i < V; i++){
            if(componentsize[componentlabel[i]] > 1){
                System.out.print("1 ");
            }else {
                System.out.print("0 ");
            }
        }**/
        System.out.println(componenetCount - 1);
        ICP();
        /**for(int i = 0; i < componenetCount;i++){
            for (int j = 0; j < componenetCount; j++){
                System.out.print(icps[i][j]+ " ");
            }
            System.out.println();
        }**/
        topoSort();
        for(Integer i: topoOrder){
            System.out.println(i);
        }

    }
    static Stack<Integer> traverseOrder;
    static boolean[] visited;
    public static void dfs1(int start){
        visited[start] = true;
        int size = graph.get(start).size();
        for(int i = 0; i < size; i++){
            int adj = graph.get(start).get(i);
            if(!visited[adj]){
                dfs1(adj);
            }
        }
        traverseOrder.push(start);
    }
    static int[] componentlabel;
    static int[] componentsize;
    static ArrayList<Integer>[] componentMembers;
    static int componenetCount;
    public static void dfs2(int current){
        componentlabel[current] = componenetCount;
        componentsize[componenetCount] += 1;
        visited[current] = true;
        int size = reverseGraph.get(current).size();
        componentMembers[componentlabel[current]].add(current);
        for(int i = 0; i < size; i++){
            int adj = reverseGraph.get(current).get(i);
            if(!visited[adj]) {
                dfs2(adj);
            }
        }
    }

    public static int[][] icps;
    public static void ICP(){//generate inter-component path
        int start = 0;
        icps = new int[componenetCount][componenetCount];
        visited = new boolean[V];
        dfs3(0);
    }
    public static void dfs3(int current){
        visited[current] = true;
        int size = graph.get(current).size();
        for(int i = 0; i < size; i++){
            int adj = graph.get(current).get(i);
            int c1 = componentlabel[current];
            int c2 = componentlabel[adj];
            icps[c1][c2] = 1;
            if(!visited[adj]){
                dfs3(adj);
            }
        }
    }
    public static ArrayList<Integer> topoOrder = new ArrayList<Integer>();
    public static boolean[] visitedComponenet;
    public static void topoSort(){//topological sorting
        visitedComponenet = new boolean[componenetCount];
        for(int i = 0; i < componenetCount; i++){
            if(visitedComponenet[i]) continue;
            dfs4(0);
        }
    }
    public static void dfs4(int current){
        visitedComponenet[current] = true;
        for(int i = 0; i < componenetCount; i++){
            if(visitedComponenet[i]) continue;
            if(icps[current][i] == 1){
                dfs4(i);
            }
        }
        topoOrder.add(0, current);
    }
    public static int SolveAnimals(){
        int output = 0;
        for(int i = componenetCount - 1; i >= 0; i--){
            int current = i;
            for(int j = i; j >= 0; j--){
                if(icps[i][j] == 1){
                    if(i != j){
                        int count = 0;
                        for(Integer a: componentMembers[i]){
                            for(Integer b: componentMembers[j]){

                            }
                        }
                    }
                }
            }
        }
        return 0;
    }
}
