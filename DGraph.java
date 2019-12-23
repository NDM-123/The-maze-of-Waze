package dataStructure;

import java.util.*;

public class DGraph implements graph {
    public HashMap<Integer, node_data> vertices;
    public HashMap<node_data,HashMap<Integer, edge_data> > edges;
    int MC=0;
    int edgesSize=0;

    public DGraph() {
        this.vertices = new HashMap<Integer, node_data>();
        this.edges = new HashMap<node_data, HashMap<Integer, edge_data>>();
    }

    @Override
    public node_data getNode(int key) {
        return this.vertices.get(key);
    }

    @Override
    public edge_data getEdge(int src, int dest) {
       node_data vertex = this.vertices.get(src);
        edge_data edge =  this.edges.get(vertex).get(dest);

        return edge;
    }

    @Override
    public void addNode(node_data n) {

        if(this.vertices.containsKey(n)==false) {
            this.vertices.put(n.getKey(), n);
            this.edges.put(n, new HashMap<Integer, edge_data>());
            this.MC++;
        }
    }

    @Override
    public void connect(int src, int dest, double w) {
         node_data vertex = this.vertices.get(src);
         if(vertex==null)throw new IllegalArgumentException("source vertex not in graph");
        this.edges.get(vertex).put(dest,new edgeData(src,dest,w,"",1));
        this.MC++;
        this.edgesSize++;
    }

    @Override
    public Collection<node_data> getV() {
        Collection<node_data> ver;
        ver = this.vertices.values();
        return ver;
    }

    @Override
    public Collection<edge_data> getE(int node_id) {
    node_data vertex = this.vertices.get(node_id);
    Collection<edge_data> edg = this.edges.get(vertex).values();
    return edg;
    }
    @Override
    public node_data removeNode(int key) {
    node_data vertex = this.vertices.get(key);
        Iterator it = this.edges.get(vertex).entrySet().iterator();
        while (it.hasNext()) {                                             //iterate over edges
            Map.Entry pair = (Map.Entry)it.next();
            this.edges.get(vertex).remove(key);
            this.edgesSize--;
        }
        it.remove(); // avoids a ConcurrentModificationException
    this.vertices.remove(vertex);
    this.MC++;
    return this.vertices.remove(key);
    }

    @Override
    public edge_data removeEdge(int src, int dest) {
    node_data vertex = this.vertices.get(src);
    edge_data edge = this.edges.get(vertex).remove(dest);
    this.MC++;
        return edge;
    }

    @Override
    public int nodeSize() {
        return this.vertices.size();
    }

    @Override
    public int edgeSize() {
        return this.edgesSize;
    }

    @Override
    public int getMC() {
        return MC;
    }

}
