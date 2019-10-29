import java.util.*;
public class Keeping {
    static int V;
    static ArrayList<ArrayList<Integer>> adjList;
    static boolean[] visited;
    static ArrayList<ArrayList<Integer>> children;
    static int best1,best2;
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        V = sc.nextInt();
        best1 = 0;
        best2 = 0;
        visited = new boolean[V + 1];
        adjList = new ArrayList<ArrayList<Integer>>();
        children = new ArrayList<>();
        for(int i = 0; i < V + 1; i++){
            adjList.add(new ArrayList<Integer>());
            children.add(new ArrayList<Integer>());
        }
        for(int i = 0; i < V; i++){
            int s = sc.nextInt();
            int t = sc.nextInt();
            adjList.get(s).add(t);
            adjList.get(t).add(s);
        }
        cwd(0);
        System.out.println(best1 + " " + best2);
    }
    public static int cwd(int start){//count weighted degrees, a variation of dfs
        visited[start] = true;
        int totalout = 0;
        int brokenpairs = 0;
        int large1 = 0;
        int large2 = 0;
        for(Integer adj: adjList.get(start)){
            if(visited[adj]) continue;
            int weightedDegree = cwd(adj);
            children.get(start).add(weightedDegree);
            totalout += weightedDegree;
            if(weightedDegree > large1){
                large2 = large1;
                large1 = weightedDegree;
            }else {
                if (weightedDegree > large2) {
                    large2 = weightedDegree;
                }
            }
        }
        int missingPiece = V  - totalout;
        if(missingPiece > large1){
            large2 = large1;
            large1 = missingPiece;
        }else {
            if (missingPiece > large2) {
                large2 = missingPiece;
            }
        }
        children.get(start).add(missingPiece);
        ArrayList<Integer> currentChildren = children.get(start);
        for(int i = 0; i < currentChildren.size(); i++){
            for(int k = i + 1; k <currentChildren.size(); k++){
                brokenpairs += currentChildren.get(i) * currentChildren.get(k);
            }
        }
        if(brokenpairs > best1){
            best1 = brokenpairs;
            best2 = brokenpairs - large1 * large2;
        }
       // System.out.println("Node " + start + " " + brokenpairs + " " + (brokenpairs-large1*large2));
        return totalout + 1;
    }
}
