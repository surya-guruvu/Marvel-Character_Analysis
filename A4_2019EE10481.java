import java.util.LinkedList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.io.*;

class VertexNode<T>{
    private T vertexId;
    private int weight; //Used to store no.of cooccurences of each character  //changed
    public int visited=0;
    public LinkedList<EdgeNode<T>> E;

    public VertexNode(T v){
        this.vertexId=v;
        this.weight = 0;
        E=new LinkedList<EdgeNode<T>>();
    }
    public int getWeight() {
        return weight;
    }
    public LinkedList<EdgeNode<T>> getList(){
        return E;
    }
    public T getvertexId(){
        return vertexId;
    }
    public void addEdgeNode(EdgeNode<T> e){
        this.weight+=e.getWeight();
        E.add(e);
    }

}
class EdgeNode<T>{
    private T vertexId;
    private int weight;

    public EdgeNode(T vertexId, int weight) {
        this.vertexId = vertexId;
        this.weight = weight;
    }

    public T getVertexId() {
        return vertexId;
    }

    public int getWeight() {
        return weight;
    }
}

class Graph<T> {
    public HashMap<T,VertexNode<T>> adj; // edges
    private float h;

    public Graph(){
        adj=new HashMap<T,VertexNode<T>>();
    }

    public void addVertex(T v){
        if(!(this.adj.containsKey(v))){
            this.adj.put(v,new VertexNode<T>(v));
        }
        return;
    }

    public void addEdge(T v1, T v2, int weight) {
        EdgeNode<T> e1=new EdgeNode<T>(v1,weight);
        EdgeNode<T> e2=new EdgeNode<T>(v2,weight);
        this.addVertex(v1);
        this.addVertex(v2);
        this.adj.get(v1).addEdgeNode(e2);
        this.adj.get(v2).addEdgeNode(e1);  
    }

    public int NoOfEdgesFromNode(T v){
        if(adj.containsKey(v)){
            return adj.get(v).getList().size();
        }
        return 0;
    }
    public float Avg(){
        if(adj.size()==0){
            return 0;
        }
        adj.forEach( 
            (key, value) 
                -> h+=value.getList().size());
        return (h/(adj.size()));
    }
    public int getsize(){ // returns no of vertices
        return adj.size();
    }
    public HashMap<T,VertexNode<T>> getlist(){
        return adj;
    }
    public Connect dfs(T v){
        Connect ab=new Connect();
        if(adj.containsKey(v)){
            adj.get(v).visited=1;
            ab.addValue(1,(String)v);
            LinkedList<EdgeNode<T>> d=adj.get(v).getList();
            for(int j=0;j<d.size();j++){
                EdgeNode<T> e=d.get(j);
                if(e!=null && adj.get(e.getVertexId()).visited==0){
                    ab=Connect.add(ab,dfs(e.getVertexId()));
                }
            }
            return ab;
        }
        return null;
    }
    public Vector connected(){
        Vector v= new Vector();

        for(T key : adj.keySet()){
            if(adj.get(key).visited==0){
                v.add(dfs(key));
            }
        }
        return v;
    }

}

class Connect{
    public int size;
    public Vector<String> s;
    public Connect(){
        this.size=0;
        this.s=new Vector<String>();
    }
    public Connect(int size,Vector<String> s){
        this.size=size;
        this.s=s;
    }
    public void addSize(int size){
        this.size+=size;
    }
    public void addString(String su){
        this.s.add(su);
    }
    public static Connect add(Connect c1,Connect c2){
        int q=c1.size+c2.size;
        for(int i=0;i<c2.s.size();i++){
            c1.s.add(c2.s.get(i));
        }
        return new Connect(q,c1.s);
    }
    public void addValue(int size,String su){
        this.size+=size;
        this.s.add(su);
    }
}
public class A4_2019EE10481{

    public static int Compar(VertexNode<String> v1,VertexNode<String> v2){
        if(v1.getWeight()>v2.getWeight()){
            return 1;
        }
        else if(v1.getWeight()<v2.getWeight()){
            return -1;
        }
        else{
            return v1.getvertexId().compareTo(v2.getvertexId());
        }
    }
    public static int Compar1(String v1,String v2){
        
            return v1.compareTo(v2);
        
    }
    public static int Compar2(Connect v1,Connect v2){
        if(v1.size>v2.size){
            return 1;
        }
        else if(v1.size<v2.size){
            return -1;
        }
        else{
            return v1.s.get((v1.s.size())-1).compareTo(v2.s.get((v2.s.size())-1));
        }
    }

    
    private static int partition1(Vector<String> l,int first,int last){
        String x=l.get(first);
        int i=first+1;
        int j=last;
        while (i<j && A4_2019EE10481.Compar1(l.get(i),x)<=0){   
            i+=1;
        }
        while (i<j && A4_2019EE10481.Compar1(l.get(j),x)>0){
            j-=1;
        }
        if (A4_2019EE10481.Compar1(l.get(j),x)>=0){
            j=j-1;
        }
        String temp;
        while (i<j){
            temp=l.get(i);
            l.set(i,l.get(j));
            l.set(j,temp);
            i+=1;
            j-=1;
            while (A4_2019EE10481.Compar1(l.get(i),x)<=0){
                i=i+1;
            }
            while (A4_2019EE10481.Compar1(l.get(j),x)>0){
                j=j-1;
            }
        }
        temp=l.get(first);
        l.set(first,l.get(j));
        l.set(j,temp);
        return j;
    }
    private static int partition2(Vector<Connect> l,int first,int last){
        Connect x=l.get(first);
        int i=first+1;
        int j=last;
        while (i<j && A4_2019EE10481.Compar2(l.get(i),x)<=0){   
            i+=1;
        }
        while (i<j && A4_2019EE10481.Compar2(l.get(j),x)>0){
            j-=1;
        }
        if (A4_2019EE10481.Compar2(l.get(j),x)>=0){
            j=j-1;
        }
        Connect temp;
        while (i<j){
            temp=l.get(i);
            l.set(i,l.get(j));
            l.set(j,temp);
            i+=1;
            j-=1;
            while (A4_2019EE10481.Compar2(l.get(i),x)<=0){
                i=i+1;
            }
            while (A4_2019EE10481.Compar2(l.get(j),x)>0){
                j=j-1;
            }
        }
        temp=l.get(first);
        l.set(first,l.get(j));
        l.set(j,temp);
        return j;
    }
    private static void quicksort2(Vector<Connect> l,int left,int right){
        if (left<right){
            int pindex=A4_2019EE10481.partition2(l,left,right);
            quicksort2(l,left,pindex-1);
            quicksort2(l,pindex+1,right);
        }
    }
    
    private static void quicksort1(Vector<String> l,int left,int right){
        if (left<right){
            int pindex=A4_2019EE10481.partition1(l,left,right);
            quicksort1(l,left,pindex-1);
            quicksort1(l,pindex+1,right);
        }
    }
    private static int partition(Vector<VertexNode<String>> l,int first,int last){
        VertexNode<String> x=l.get(first);
        int i=first+1;
        int j=last;
        while (i<j && A4_2019EE10481.Compar(l.get(i),x)<=0){   
            i+=1;
        }
        while (i<j && A4_2019EE10481.Compar(l.get(j),x)>0){
            j-=1;
        }
        if (A4_2019EE10481.Compar(l.get(j),x)>=0){
            j=j-1;
        }
        VertexNode<String> temp;
        while (i<j){
            temp=l.get(i);
            l.set(i,l.get(j));
            l.set(j,temp);
            i+=1;
            j-=1;
            while (A4_2019EE10481.Compar(l.get(i),x)<=0){
                i=i+1;
            }
            while (A4_2019EE10481.Compar(l.get(j),x)>0){
                j=j-1;
            }
        }
        temp=l.get(first);
        l.set(first,l.get(j));
        l.set(j,temp);
        return j;
    }
    private static void quicksort(Vector<VertexNode<String>> l,int left,int right){
        if (left<right){
            int pindex=A4_2019EE10481.partition(l,left,right);
            quicksort(l,left,pindex-1);
            quicksort(l,pindex+1,right);
        }
    }

    public static void main(String[] args){
        Graph<String> v= new Graph<String>();

        try{  
            BufferedReader nodebr = new BufferedReader(new FileReader(args[0]));  
            String line =nodebr.readLine();
            while ((line = nodebr.readLine()) != null){  
                String[] nodes = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1); 
                if(nodes[1].charAt(0)=='"'){
                    int n=nodes[1].length();
                    v.addVertex(nodes[1].substring(1,n-1));
                }  
                else{
                    v.addVertex(nodes[1]);
                }
            }  
        }   
        catch (IOException e){  
            e.printStackTrace();  
        }

        try{   
            BufferedReader nodecr = new BufferedReader(new FileReader(args[1]));  

            String v1="";
            String v2="";

            String line1 = nodecr.readLine();
            while ((line1 = nodecr.readLine()) != null){
                String[] edges = line1.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);  
                if(edges[0].charAt(0)=='"'){
                    int n=edges[0].length();
                    v1=edges[0].substring(1,n-1);
                }  
                else{
                    v1=edges[0];
                }
                if(edges[1].charAt(0)=='"'){
                    int n1=edges[1].length();
                    v2=edges[1].substring(1,n1-1);
                }  
                else{
                    v2=edges[1];
                }
                int w=Integer.parseInt(edges[2]);
                v.addEdge(v1,v2,w);
            }  
        }   
        catch (IOException e)   {  
            e.printStackTrace();  
        }

        String s1="average";
        String s2="rank";
        String s3="independent_storylines_dfs";

        if (args[2].compareTo(s1)==0){
            System.out.format("%.2f", v.Avg());
        }

        else if(args[2].compareTo(s2)==0){
            HashMap<String,VertexNode<String>> s=v.getlist();
            Vector<VertexNode<String>> ver=new Vector<VertexNode<String>>();
            s.forEach( 
                (key, value) 
                    -> ver.add(value));
            int k=ver.size();
            A4_2019EE10481.quicksort(ver,0,k-1);
            for(int i=k-1;i>=0;i--){
                if(i!=0){
                    System.out.print(ver.get(i).getvertexId()+",");
                }
                else{
                    System.out.println(ver.get(i).getvertexId());
                }
            }
        }
        else if(args[2].compareTo(s3)==0){
            Vector<Connect> vert=(v.connected());
            for(int p=vert.size()-1;p>=0;p--){
                Connect c1=vert.get(p);
                Vector<String> b1=c1.s;
                A4_2019EE10481.quicksort1(b1,0,b1.size()-1);

            }
            A4_2019EE10481.quicksort2(vert,0,vert.size()-1);
            for(int l=vert.size()-1;l>=0;l--){
                Connect c=vert.get(l);
                Vector<String> b=c.s;
                for(int m=b.size()-1;m>0;m--){
                    System.out.print(b.get(m)+",");
                }
                System.out.println(b.get(0));

            }
        }

    }
}