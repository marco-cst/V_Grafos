package controller.tda.graph;

import java.io.FileWriter;
import java.lang.reflect.Array;
import java.util.HashMap;

import controller.tda.exception.LabelException;
import controller.tda.exception.ListEmptyException;
import controller.tda.list.LinkedList;
import models.Universidad;


public class GraphLabelDirect<E> extends GraphDirect {
    protected E labels[];
    protected HashMap<E, Integer> dictVertices;
    private Class<E> clazz;

    public GraphLabelDirect(Integer nro_vertices, Class<E> clazz) {
        super(nro_vertices);
        this.clazz = clazz;
        labels = (E[]) Array.newInstance(clazz, nro_vertices + 1);
        dictVertices = new HashMap<>();
    }

    public double[][] fWarshall() {
        int n = nro_vertices();
        double[][] dist = new double[n + 1][n + 1];
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                if (i == j) {
                    dist[i][j] = 0;
                } else {
                    dist[i][j] = Double.MAX_VALUE;
                }
            }
        }
        try {
            for (int i = 1; i <= n; i++) {
                LinkedList<Adjacency> adys = adjacencys(i);
                for (int j = 0; j < adys.getSize(); j++) {
                    Adjacency adj = adys.get(j);
                    dist[i][adj.getDestination()] = adj.getWeight();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (int k = 1; k <= n; k++) {
            for (int i = 1; i <= n; i++) {
                for (int j = 1; j <= n; j++) {
                    if (dist[i][j] > dist[i][k] + dist[k][j]) {
                        dist[i][j] = dist[i][k] + dist[k][j];
                    }
                }
            }
        }

        return dist;
    }

    public double[] bellFord(int inVertice) throws IndexOutOfBoundsException, ListEmptyException {
        int n = nro_vertices();
        double[] dist = new double[n + 1];
        int[] verticeAnterior = new int[n + 1];

        for (int i = 1; i <= n; i++) {
            dist[i] = Double.MAX_VALUE;
            verticeAnterior[i] = -1;
        }
        dist[inVertice] = 0;

        for (int i = 1; i <= n - 1; i++) {
            for (int u = 1; u <= n; u++) {
                LinkedList<Adjacency> adys = adjacencys(i);
                for (int j = 0; j < adys.getSize(); j++) {
                    Adjacency adj = adys.get(j);
                    int v = adj.getDestination();
                    double weight = adj.getWeight();
                    if (dist[u] != Double.MAX_VALUE && dist[u] + weight < dist[v]) {
                        dist[v] = dist[u] + weight;
                        verticeAnterior[v] = u;
                    }
                }
            }
        }
        for (int u = 1; u <= n; u++) {
            LinkedList<Adjacency> adys = adjacencys(u);
            for (int j = 0; j < adys.getSize(); j++) {
                Adjacency adj = adys.get(j);
                int v = adj.getDestination();
                double weight = adj.getWeight();
                if (dist[u] != Double.MAX_VALUE && dist[u] + weight < dist[v]) {
                    return null;
                }
            }
        }
        return dist;
    }

    public Boolean is_edgeL(E v1, E v2) throws Exception {
        if (isLabelsGraph()) {
            return is_edge(getVerticeL(v1), getVerticeL(v2));
        } else {
            throw new LabelException("Grafo no etiquetado");
        }
    }

    public void insertEdgeL(E v1, E v2, Double lat1, Double lon1, Double lat2, Double lon2) throws Exception {
        if (isLabelsGraph()) {
            float distance = (float) DistanceGraph.distanciaVincenty(lat1, lon1, lat2, lon2);
            add_edge(getVerticeL(v1), getVerticeL(v2), distance);
        } else {
            throw new LabelException("Grafo no etiquetado");
        }
    }

    public void insertEdgeL(E v1, E v2) throws Exception {
        if (isLabelsGraph()) {
            insertEdgeL(v1, v2, 0.0, 0.0, 0.0, 0.0);
        } else {
            throw new LabelException("Grafo no etiquetado");
        }
    }

    public LinkedList<Adjacency> adjacencysL(E v) throws Exception {
        if (isLabelsGraph()) {
            return adjacencys(getVerticeL(v));
        } else {
            throw new LabelException("Grafo no etiquetado");
        }
    }

    public void labelsVerticeL(Integer v, E data) {
        labels[v] = data;
        dictVertices.put(data, v);
    }

    public Boolean isLabelsGraph() {
        Boolean band = true;
        for (int i = 1; i < labels.length; i++) {
            if (labels[i] == null) {
                band = false;
                break;
            }
        }
        return band;
    }

    public Integer getVerticeL(E label) {
        return dictVertices.get(label);
    }

    public E getLabelL(Integer v1) {
        return labels[v1];
    }

    @Override
    public String toString() {
        String grafo = "";
        try {
            for (int i = 1; i <= this.nro_vertices(); i++) {
                grafo += "V" + i + " [" + getLabelL(i).toString() + "]" + "\n";
                LinkedList<Adjacency> lista = adjacencys(i);
                if (!lista.isEmpty()) {
                    Adjacency[] ady = lista.toArray();
                    for (int j = 0; j < ady.length; j++) {
                        Adjacency a = ady[j];
                        grafo += "ady " + "V" + a.getDestination() + " weight: " + a.getWeight() + " ["
                                + getLabelL(a.getDestination()).toString() + "]" + "\n";
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return grafo;
    }

    public String drawGraph() {
        StringBuilder grafo = new StringBuilder("var nodes = new vis.DataSet([\n");
        try {
            for (int i = 1; i <= this.nro_vertices(); i++) {
                Universidad u = (Universidad) getLabelL(i);
                grafo.append("{id:").append(i)
                    .append(", label:\"").append(u.getNombre()).append("\"},\n");
            }
            grafo.append("]);\n");
            
            grafo.append("var edges = new vis.DataSet([\n");
            
            for (int i = 1; i <= this.nro_vertices(); i++) {
                LinkedList<Adjacency> lista = adjacencys(i);
                for (int j = 0; j < lista.getSize(); j++) {
                    Adjacency a = lista.get(j);
                    grafo.append("{from:").append(i)
                        .append(", to:").append(a.getDestination())
                        .append(", label:\"").append(a.getWeight()).append("\"")
                        .append(", arrows:\"to\", color:\"blue\"},\n");
                }
            }
        
            grafo.append("]);\n");

            grafo.append("var container = document.getElementById('mynetwork');\n");
            grafo.append("var data = { nodes: nodes, edges: edges };\n");
            grafo.append("var options = {};\n");
            grafo.append("var network = new vis.Network(container, data, options);\n");

            String path = "/home/marco/Documents/ProgG/V_Grafo/FrontG/static/js/graph.js";
            FileWriter file = new FileWriter(path);
            file.write(grafo.toString());
            file.flush();
            file.close();
            
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return grafo.toString();
    }
}