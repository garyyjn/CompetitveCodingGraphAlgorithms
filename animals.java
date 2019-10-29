import com.sun.source.tree.WhileLoopTree;

import java.util.*;
public class animals {
    static int V;
    static int E;
    static int[][] graph;
    static int[][] reverseGraph;
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        V = sc.nextInt();
        E = sc.nextInt();
        graph = new int[V][V];
        reverseGraph = new int[V][V];

        for(int i  = 0; i < E; i++){
            int a = sc.nextInt();
            int b = sc.nextInt();
            graph[a - 1][b - 1] = 1;
            graph[b - 1][a - 1] = 1;
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
        //System.out.println(componenetCount - 1);
        ICP();
        /**for(int i = 0; i < componenetCount;i++){
         for (int j = 0; j < componenetCount; j++){
         System.out.print(icps[i][j]+ " ");
         }
         System.out.println();
         }**/
        topoSort();
        System.out.println("Component Count: "+ componenetCount);
        for(Integer i: topoOrder){
            System.out.println("TOPOLOGICAL ORDER");
            System.out.println(i);
            for(Integer J: componentMembers[i]){
                System.out.print(J);
            }
        }
        System.out.println(SolveAnimals());

    }
    static Stack<Integer> traverseOrder;
    static boolean[] visited;
    public static void dfs1(int start){
        visited[start] = true;
        for(int i = 0; i < V; i++){
            if(graph[start][i] == 1){
                if(!visited[i]){
                    dfs1(i);
                }
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
        componentMembers[componentlabel[current]].add(current);
        System.out.println("DFS 2 looking at node: " + current + "with compoenent: " + componenetCount);
        for(int i = 0; i < V; i++){
            if(reverseGraph[current][i] == 1){
                if(!visited[i]) {
                    dfs2(i);
                }
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
        for(int i = 0; i < V; i++){
            if(graph[current][i] == 1){
            int c1 = componentlabel[current];
            int c2 = componentlabel[i];
            icps[c1][c2] = 1;
                if(!visited[i]){
                    dfs3(i);
                }
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
        topoOrder.add(current);
    }
    public static int SolveAnimals(){
        int output = 0;
        for(int i = componenetCount - 1; i >= 0; i--){
            for(int j = i; j >= 0; j--){
                if(icps[i][j] == 1){
                    if(i != j){
                        int count = 0;
                        for(Integer a: componentMembers[i]){
                            for(Integer b: componentMembers[j]){
                                System.out.println("Looking at interponent " + a + " " + b);
                                if(graph[a][b] == 0){
                                    count += 1;
                                }
                            }
                        }
                        output += count;
                    }
                    else{
                        int count = 0;
                        for(Integer a: componentMembers[i]){
                            for(Integer b: componentMembers[j]){
                                System.out.println("Looking at interponent " + a + " " + b);
                                if(graph[a][b] == 0 && a!=b){
                                    count += 1;
                                }
                            }
                        }
                        output += count;
                    }
                }
            }
        }
        return output;
    }
}
