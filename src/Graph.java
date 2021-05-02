import java.util.*;
public class Graph {
    // We use Hashmap to store the edges in the graph
    public Map<String, List<String>> map = new HashMap<>();
  
    // This function adds a new vertex to the graph
    public void addVertex(String s){
        map.put(s, new LinkedList<String>());
    }
  
    // This function adds the edge
    // between source to destination
    public void addEdge(String source,String destination,boolean bidirectional){
  
        if (!map.containsKey(source))
            addVertex(source);
  
        if (!map.containsKey(destination))
            addVertex(destination);
  
        map.get(source).add(destination);
        if (bidirectional == true) {
            map.get(destination).add(source);
        }
    }
  
    // This function gives the count of vertices
    public void getVertexCount(){
        System.out.println("The graph has "
                           + map.keySet().size()
                           + " vertex");
    }
  
    // This function gives the count of edges
    public void getEdgesCount(boolean bidirection){
        int count = 0;
        for (String v : map.keySet()) {
            count += map.get(v).size();
        }
        if (bidirection == true) {
            count = count / 2;
        }
        System.out.println("The graph has "
                           + count
                           + " edges.");
    }
  
    // This function gives whether
    // a vertex is present or not.
    public boolean hasVertex(String s){
        if (map.containsKey(s)) {
            System.out.println("The graph contains "
                               + s + " as a vertex.");
            return true;
        }
        else {
            System.out.println("The graph does not contain "
                               + s + " as a vertex.");
            return false;
        }
    }
  
    // This function gives whether an edge is present or not.
    public boolean hasEdge(String s, String d){
        if(map.get(s)==null){
            return false;
        }
        if (map.get(s).contains(d)) {
            
            return true;
        }
        else {
            
            return false;
        }
    }
  
    // Prints the adjancency list of each vertex.
    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();
  
        for (String v : map.keySet()) {
            builder.append(v.toString() + ": ");
            for (String w : map.get(v)) {
                builder.append(w.toString() + " ");
            }
            builder.append("\n");
        }
  
        return (builder.toString());
    }
}
