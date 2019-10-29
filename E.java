import java.util.*;
public class E {
    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);
        String line1 = sc.nextLine();
        String[] pieces1 = line1.split("\\s+");
        int n = Integer.parseInt(pieces1[0]);
        int m = Integer.parseInt(pieces1[1]);
        HashMap<String,Integer> stringtoind = new HashMap<String, Integer>();
        ArrayList<ArrayList<Integer>> IS = new ArrayList<>();
        ArrayList<ArrayList<Integer>> HAS = new ArrayList<>();
        int currentObjects = 0;
        for(int i = 0; i < n;i++){
            String line = sc.nextLine();
            String[] pieces = line.split("\\s+");
            if(!stringtoind.containsKey(pieces[0])){
                stringtoind.put(pieces[0],currentObjects);
                //System.out.println(pieces[0] + " placed in " + currentObjects);
                currentObjects++;
                IS.add(new ArrayList<Integer>());
                HAS.add(new ArrayList<Integer>());
            }
            if(!stringtoind.containsKey(pieces[2])){
                stringtoind.put(pieces[2],currentObjects);
                //System.out.println(pieces[2] + " placed in " + currentObjects);
                currentObjects++;
                IS.add(new ArrayList<Integer>());
                HAS.add(new ArrayList<Integer>());
            }
            int start = stringtoind.get(pieces[0]);
            int end = stringtoind.get(pieces[2]);
            //System.out.println(start + pieces[1] + end);
            if(pieces[1].equals("is-a")){
                IS.get(start).add(end);
            }
            if(pieces[1].equals("has-a")){
                HAS.get(start).add(end);
            }
        }
        for(int i = 0; i < m; i++){
            String line = sc.nextLine();
            String[] pieces = line.split("\\s+");
            int start = stringtoind.get(pieces[0]);
            int end = stringtoind.get(pieces[2]);
            //System.out.println(start + pieces[1] + end);
            if(pieces[1].equals("is-a")){
                boolean flag = false;
                boolean[] visited = new boolean[currentObjects];
                ArrayList<Integer> queue = new ArrayList<>();
                queue.add(start);
                //  System.out.println(queue.size());
                while(!queue.isEmpty()){
                    int current = queue.get(0);
                    queue.remove(0);
                    //    System.out.println("Visiting " +  current);
                    visited[current] = true;
                    if(current == end){
                        flag = true;
                        break;
                    }
                    for(Integer adj: IS.get(current)){
                        if(!visited[adj]){
                            queue.add(adj);
                        }
                    }
                }
                System.out.println("Query " + (i + 1) +  ": " +flag);
            }
            if(pieces[1].equals("has-a")){
                boolean flag = false;
                boolean[] visited = new boolean[currentObjects];
                boolean[] hasVisited = new boolean[currentObjects];
                ArrayList<Integer> queue = new ArrayList<>();
                queue.add(start);
                ArrayList<Integer> hasQueue = new ArrayList<>();
                boolean[] hasFlag = new boolean[currentObjects];
                while(!queue.isEmpty()){
                    int current = queue.get(0);
                    queue.remove(0);
                    visited[current] = true;
                    if(hasFlag[current]) hasVisited[current] = true;
                    if(!hasFlag[current]){
                        for(int ADJ: HAS.get(start)){
                            if(hasVisited[ADJ]) continue;
                                hasFlag[ADJ] = true;
                                if(ADJ == end){
                                    flag = true;
                                    break;
                                }
                                queue.add(ADJ);
                            }
                            for(int ADJ: IS.get(start)){
                                if (visited[ADJ]) continue;
                                    queue.add(ADJ);
                                }

                    }else{
                        for(int ADJ: HAS.get(start)){
                            if(hasVisited[ADJ]) continue;
                            hasFlag[ADJ] = true;
                            if(ADJ == end){
                                flag = true;
                                break;
                            }
                            queue.add(ADJ);
                        }
                        for(int ADJ: IS.get(start)){
                            if(hasVisited[ADJ]) continue;
                            hasFlag[ADJ] = true;
                            if(ADJ == end){
                                flag = true;
                                break;
                            }
                            queue.add(ADJ);
                        }
                    }
                }
                flag = flag || hasFlag[end];
                System.out.println("Query " + (i + 1) +  ": " +flag);
            }
        }
    }
}
