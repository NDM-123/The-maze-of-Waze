package dataStructure;

import java.io.Serializable;
import java.util.*;

public class DGraph implements graph, Serializable {
    public HashMap<Integer, node_data> vertices = new HashMap<Integer, node_data>();
    ;
    public HashMap<node_data, HashMap<Integer, edge_data>> edges = new HashMap<node_data, HashMap<Integer, edge_data>>();
    int MC = 0;
    int edgesSize = 0;

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
        edge_data edge = this.edges.get(vertex).get(dest);

        return edge;
    }

    @Override
    public void addNode(node_data n) {
        if (this.vertices.containsKey(n) == false) {
            this.vertices.put(n.getKey(), n);
            this.edges.put(n, new HashMap<Integer, edge_data>());
            this.MC++;
        }
    }

    @Override
    public void connect(int src, int dest, double w) {
        try {
            node_data vertex_a = this.vertices.get(src);
            node_data vertex_b = this.vertices.get(dest);
            if (vertex_a == null || vertex_b == null)
                throw new IllegalArgumentException("source vertex not in graph");
            this.edges.get(vertex_a).put(dest, new edgeData(src, dest, w, "", 0));
            this.MC++;
            this.edgesSize++;
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }
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
        if (vertex == null) return null;
        Set<node_data> set = this.edges.keySet();
        for (node_data node_data : set) {
            edge_data edg = this.edges.get(node_data).remove(key);
            if (edg != null) this.edgesSize--;
        }
        this.edgesSize -= this.edges.get(vertex).size();
        this.edges.remove(vertex);
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

    public boolean equals(Object ob) {
        if (!(ob instanceof DGraph)) return false;
        boolean flag = false;
        DGraph dg = (DGraph) ob;
        if (!(this.vertices.equals(dg.vertices))) return false;
        if (!(this.edges.equals(dg.edges))) return false;

        return true;
    }


}
