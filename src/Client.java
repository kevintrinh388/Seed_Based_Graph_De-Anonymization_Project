import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class Client {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        LinkedList<Edge> g1 = readG1();             
        LinkedList<Edge> g2 = readG2();
        LinkedList<Edge> nodePairs = readNodePairs();

        // g1 to map
        Map<String,LinkedList<String>> g1Map = new TreeMap<String,LinkedList<String>>();
        for (Edge edge : g1) {
            if(g1Map.containsKey(edge.getA())){
                g1Map.get(edge.getA()).add(edge.getB());
            }
            else{
                g1Map.put(edge.getA(), new LinkedList<String>());
                g1Map.get(edge.getA()).add(edge.getB());
            }
        }
        
        // g1map degrees
        Map<String,Integer> g1MapDegree = new TreeMap<String,Integer>();
        for (Map.Entry<String,LinkedList<String>> entry: g1Map.entrySet()) {
            g1MapDegree.put(entry.getKey(), entry.getValue().size());
        }

        // g2 to map 
        Map<String,LinkedList<String>> g2Map = new TreeMap<String,LinkedList<String>>();
        for (Edge edge : g2) {
            if(g2Map.containsKey(edge.getA())){
                g2Map.get(edge.getA()).add(edge.getB());
            }
            else{
                g2Map.put(edge.getA(), new LinkedList<String>());
                g2Map.get(edge.getA()).add(edge.getB());
            }
        }
        
        // g2map degrees
        Map<String,Integer> g2MapDegree = new TreeMap<String,Integer>();
        for (Map.Entry<String,LinkedList<String>> entry: g2Map.entrySet()) {
            g2MapDegree.put(entry.getKey(), entry.getValue().size());
        }


        // g1 to graph
        Graph g1Graph = new Graph();
        for (Edge edge : g1) {
            if(!g1Graph.hasEdge(edge.getA(), edge.getB())){
                g1Graph.addEdge(edge.getA(), edge.getB(), true);
            }
            else{
                // 
            }
        }
        
        // g2 to graph
        Graph g2Graph = new Graph();
        for (Edge edge : g2) {
            if(!g2Graph.hasEdge(edge.getA(), edge.getB())){
                g2Graph.addEdge(edge.getA(), edge.getB(), true);
            }
            else{
                // 
            }
        }

        System.out.println("G1 as a linked list is: "+g1);
        System.out.println("G1 as a map is: "+g1Map);
        System.out.println("G1 as a graph is: \n"+g1Graph);
        System.out.println("G1 nodes and their degrees are: "+g1MapDegree);
        
        // CALCULATION STARTS HERE
        System.out.println("CALCULATING PLEASE WAIT...");
        Map<String,String> newNodePairsMap = new TreeMap<String,String>();                  // THIS MAP WILL HOLD THE PAIRS THAT WE FIND

        for (Map.Entry<String,LinkedList<String>> g1nodes : g1Map.entrySet()) {
            Edge tempEdge = new Edge();
            Edge edgeToPair = new Edge();
            double maxEcce = 0;

            for (Map.Entry<String,LinkedList<String>> entry : g1Map.entrySet()) {
                
                if(newNodePairsMap.containsKey(entry.getKey())){
                    continue;
                }

                LinkedList<Double> scoreList = new LinkedList<Double>();
                double maxScore=0;
                
                int degreeOfV = g1MapDegree.get(entry.getKey());

                for (Map.Entry<String,LinkedList<String>> entry2 : g2Map.entrySet()) {

                    if(newNodePairsMap.containsValue(entry2.getKey())){
                        continue;
                    }

                    int degreeOfU = g2MapDegree.get(entry2.getKey());
                    int numMappedNeighbors = numMappedNeighbors(nodePairs, g1Graph, g2Graph, entry.getKey(), entry2.getKey());
                    double sqrtDegreeMultiplied = Math.sqrt(degreeOfV)*Math.sqrt(degreeOfU);
                    double score = numMappedNeighbors/(sqrtDegreeMultiplied);
                    scoreList.add(score);
                    if(score>maxScore){
                        maxScore=score;
                        tempEdge = new Edge(entry.getKey(),entry2.getKey());
                    }

                }

                Collections.sort(scoreList);
                double smax=0;
                double smax2=0;
                if(scoreList.size()>1){
                    smax=scoreList.get(scoreList.size()-1);
                    smax2=scoreList.get(scoreList.size()-2);
                }
                double std = standardDeviation(scoreList);
                double ecce = (smax-smax2)/(std);
                if(ecce>=0.5){
                    if(ecce>maxEcce){
                        maxEcce=ecce;
                        edgeToPair=tempEdge;

                    }
                }
                
            }

            if(!edgeToPair.getA().equals("")){
                newNodePairsMap.put(edgeToPair.getA(), edgeToPair.getB());
                nodePairs.add(edgeToPair);
            }

        }
        long elapsedTime = System.currentTimeMillis() - startTime;
        long elapsedSeconds = elapsedTime / 1000;
        long elapsedMinutes = elapsedSeconds / 60;

        // sorting the map with the pairs found in the final calculation by converting to int
        Map<Integer,Integer> newNodePairsMapSorted = new TreeMap<Integer,Integer>();
        for (Map.Entry<String,String> e : newNodePairsMap.entrySet()) {
            newNodePairsMapSorted.put(Integer.parseInt(e.getKey().substring(1)), Integer.parseInt(e.getValue().substring(1)));
        }

        for (Edge edge : nodePairs) {
            newNodePairsMapSorted.putIfAbsent(Integer.parseInt(edge.getA().substring(1)), Integer.parseInt(edge.getB().substring(1)));
        }

        System.out.println("All matched pairs are: "+newNodePairsMapSorted);
        writeResult(newNodePairsMapSorted);

        System.out.println("Runtime was: "+elapsedMinutes+" minutes and "+elapsedSeconds+" seconds");
        

    }

    // returns the standard deviation of a list
    public static double standardDeviation(List<Double> list){
        if(list.isEmpty()){
            return 0;
        }

        double mean=0;
        double sum=0;
        double standardDeviation=0;
        double sq=0;
        double res=0;
        for (Double num : list) {
            sum+=num;
        }
        mean=sum/list.size();
        
        for (Double num : list) {
            standardDeviation+=Math.pow((num-mean), 2);
        }

        sq=standardDeviation/list.size();
        res=Math.sqrt(sq);

        return res;
    }

    // returns the number of mapped neighbors between g1(v) and g2(u)
    public static int numMappedNeighbors(LinkedList<Edge> pair, Graph graph1, Graph graph2, String g1, String g2){
        int numMapped = 0;
        for (Edge edge : pair) {
            if(graph1.hasEdge(g1, edge.getA())){
                if(graph2.hasEdge(g2, edge.getB())){
                    numMapped++;
                }
            }
        }
        return numMapped;
    }

    // reads seed_G1.txt from the input folder and converts it into a linked list
    public static LinkedList<Edge> readG1(){
        LinkedList<Edge> g1 = new LinkedList<Edge>();
        try {
            File myObj = new File("input\\slide_seed_G1.txt");       // Change this file path if you want to test with a different file
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {

                String data = myReader.nextLine();
                String[] toEdgeList = data.split(" ");
                Edge edge = new Edge("V"+toEdgeList[0], "V"+toEdgeList[1]);
                g1.add(edge);

            }
            myReader.close();
          } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
          }

        return g1;
    }

    // reads seed_G2.txt from the input folder and converts it into a linked list
    public static LinkedList<Edge> readG2(){
        LinkedList<Edge> g2 = new LinkedList<Edge>();
        try {
            File myObj = new File("input\\slide_seed_G2.txt");       // Change this file path if you want to test with a different file
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {

                String data = myReader.nextLine();
                String[] toEdgeList = data.split(" ");
                Edge edge = new Edge("U"+toEdgeList[0], "U"+toEdgeList[1]);
                g2.add(edge);

            }
            myReader.close();
          } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
          }

        return g2;
    }

    // reads seed_node_pairs.txt from the input folder and converts it into a linked list
    public static LinkedList<Edge> readNodePairs(){
        LinkedList<Edge> nodePairs = new LinkedList<Edge>();
        try {
            File myObj = new File("input\\slide_node_pairs.txt");        // Change this file path if you want to test with a different file
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {

                String data = myReader.nextLine();
                String[] toEdgeList = data.split(" ");
                Edge edge = new Edge("V"+toEdgeList[0], "U"+toEdgeList[1]);
                nodePairs.add(edge);

            }
            myReader.close();
          } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
          }

        return nodePairs;
    }

    // writes our result and puts it in the output folder
    public static void writeResult(Map<Integer,Integer> input){
        try {
            File myObj = new File("output\\our_result.txt");        // The result file will be named "our_result.txt"
            FileWriter myWriter = new FileWriter(myObj);

            for (Map.Entry<Integer,Integer> entry : input.entrySet()) {
                
                myWriter.write(entry.getKey()+" "+entry.getValue()+"\n");
            }

            myWriter.close();
            System.out.println("Successfully wrote to the file.");
          } catch (IOException e) {
            System.out.println("An error occurred.");
            e.getStackTrace();
          }
    }
}
