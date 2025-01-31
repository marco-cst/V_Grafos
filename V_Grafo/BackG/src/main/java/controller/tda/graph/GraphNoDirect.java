package controller.tda.graph;

import controller.tda.exception.OverFlowException;

public class GraphNoDirect extends GraphDirect {
    public GraphNoDirect(Integer nro_vertices) {
        super(nro_vertices);
    }

    public void add_edge(Integer v1, Integer v2, Float weight) throws Exception {
        if (v1.intValue() <= nro_vertices() && v2.intValue() <= nro_vertices()) {
            if (!is_edge(v1, v2)) {
                setNro_edges(nro_edges() + 1);
                Adjacency aux = new Adjacency();
                aux.setWeight(weight);
                aux.setDestination(v2);
                getListaAdjacencys()[v1].add(aux);

                Adjacency auxD = new Adjacency();
                auxD.setWeight(weight);
                auxD.setDestination(v1);
                getListaAdjacencys()[v2].add(auxD);
            }
        } else {
            throw new OverFlowException("Vertices fuera de rango");
        }
        super.add_edge(v1, v2, weight);
    }
}
