package com.example.rest;

import controller.tda.graph.DistanceGraph;

public class recorrido {
    public static void main(String[] args) {
        int vertices = 6;
        double[][] grafo = new double[vertices][vertices];

        for (int i = 0; i < vertices; i++) {
            for (int j = 0; j < vertices; j++) {
                if (i == j) {
                    grafo[i][j] = 0;
                } else {
                    grafo[i][j] = Double.MAX_VALUE;
                }
            }
        }

        grafo[0][1] = grafo[1][0] = DistanceGraph.distanciaVincenty(10.7128, -74.0060, 34.0522, -118.2437);
        grafo[1][2] = grafo[2][1] = DistanceGraph.distanciaVincenty(14.0522, -118.2437, 41.8781, -87.6298);  
        grafo[0][3] = grafo[3][0] = DistanceGraph.distanciaVincenty(10.7128, -74.0060, 51.5074, -0.1278);   
        grafo[2][4] = grafo[4][2] = DistanceGraph.distanciaVincenty(10.8781, -87.6298, 37.7749, -122.4194); 
        grafo[3][5] = grafo[5][3] = DistanceGraph.distanciaVincenty(10.5074, -0.1278, 48.8566, 2.3522); 
        String[] universidad = {"UNL", "UTPL", "UPM", "USC", "ACT", "IPN", "UANL", "UABC", "UVM"};

        System.out.println("\nGrafo con cDistances iniciales:");
        for (int i = 0; i < vertices; i++) {
            for (int j = 0; j < vertices; j++) {
                System.out.print((grafo[i][j] == Double.MAX_VALUE ? "INF" : grafo[i][j]) + " ");
            }
            System.out.println();
        }

        double[][] cDistancesFW = DistanceGraph.fWarshall(grafo);
        System.out.println("\nResultados de Floyd-Warshall:");
        for (int i = 0; i < cDistancesFW.length; i++) {
            for (int j = 0; j < cDistancesFW[i].length; j++) {
                System.out.print((cDistancesFW[i][j] == Double.MAX_VALUE ? "INF" : cDistancesFW[i][j]) + " ");
            }
            System.out.println();
        }

        double[] cDistancesBF = DistanceGraph.bellFord(grafo, 0);
        System.out.println("\nResultados de Bellman-Ford desde UNL:");
        for (int i = 0; i < cDistancesBF.length; i++) {
            System.out.println("Distancia de UNL a " + universidad[i] + ": " +(cDistancesBF[i] == Double.MAX_VALUE ? "INF" : cDistancesBF[i]));
        }

        System.out.println("\nCamino más corto de UPM a ACT:");
        double caminoMasCorto = grafo[2][4];
        if (caminoMasCorto == Double.MAX_VALUE) {
            System.out.println("No existe un camino.");
        } else {
            System.out.println("Distancia: " + caminoMasCorto + " km");
        }
    }
}