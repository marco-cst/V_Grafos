package controller.tda.graph;

import controller.tda.list.LinkedList;

public abstract class Graph {
    public abstract Integer nro_vertices();
    public abstract Integer nro_edges();
    public abstract Boolean is_edge(Integer v1, Integer v2) throws Exception;
    public abstract Float weight_edge(Integer v1, Integer v2) throws Exception;
    public abstract void add_edge(Integer v1, Integer v2) throws Exception;
    public abstract void add_edge(Integer v1, Integer v2, Float weight) throws Exception;
    public abstract LinkedList<Adjacency> adjacencys(Integer vi);

    @Override
    public String toString(){
        String grafo = "";
        try {
            for (int i = 1; i <= this.nro_vertices(); i++) {
                grafo += "V" + i + "\n";
                LinkedList<Adjacency> lista = adjacencys(i);
                if(!lista.isEmpty()){
                    Adjacency[] ady = lista.toArray();
                    for(int j = 0; j < ady.length; j++){
                        Adjacency a = ady[j];
                        grafo += "ady " + "V" + a.getDestination() + " weight: " + a.getWeight() + "\n";
                    }
                }
            }
        } catch (Exception e) {
   
        }
        return grafo;
    }
}
