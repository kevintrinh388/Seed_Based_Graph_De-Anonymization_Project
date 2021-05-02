public class Edge {
    private String a;
    private String b;

    public Edge(){
        this.a = "";
        this.b = "";

    }

    public Edge(String a, String b){
        this.a = a;
        this.b = b;

    }


    public String getA(){
        return a;
    }

    public String getB(){
        return b;
    }


    public String toString(){
        return getA()+" "+getB();
    }
}
