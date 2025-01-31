package com.example.rest;
import controller.tda.graph.GraphLabelNoDirect;
import models.Universidad;

public class medidaTiempo {
    public static void main(String[] args) {
        StringBuilder results = new StringBuilder();
        results.append("+-----------------+---------------------+---------------------+----------------------+").append("\n");
        results.append("| Tamaño del Grafo | Bellman-Ford (Tiempo) | Floyd-Warshall (Tiempo) | Algoritmo Más Rápido |").append("\n");
        results.append("+-----------------+---------------------+---------------------+----------------------+").append("\n");
        
        int[] sizes = {10, 20, 30};

        for (int size : sizes) {
            try {
                GraphLabelNoDirect<Universidad> graph = new GraphLabelNoDirect<>(size, Universidad.class);
                Universidad[] m = new Universidad[size];

                for (int i = 0; i < size; i++) {
                    Universidad s = new Universidad(); 
                    m[i] = s;
                    graph.labelsVerticeL(i + 1, s);
                }
                for (int i = 0; i < size - 1; i++) {
                    graph.insertEdgeL(m[i], m[i + 1], (i + 1) * 2.0f);
                }

                //  tiempo  Bellman-Ford
                long startTime = System.nanoTime();
                graph.bellFord(1);
                long endTime = System.nanoTime();
                long bellFordDuration = endTime - startTime;

                // Tiempo de Floyd-Warshall
                startTime = System.nanoTime();
                graph.fWarshall();
                endTime = System.nanoTime();
                long fWarshallDuration = endTime - startTime;

                // algoritmo + rapido
                String fastestAlgorithm = bellFordDuration < fWarshallDuration ? "Bellman-Ford" : "Floyd-Warshall";

                // resultados con alineación
                results.append(String.format("| %-15s | %-19d | %-19d | %-19s |\n", 
                        size + " vértices", bellFordDuration, fWarshallDuration, fastestAlgorithm));

            } catch (Exception e) {
                results.append("| Error en ejecución | Error en ejecución  | Error en ejecución  | Error en ejecución  |\n");
            }
        }
        results.append("+-----------------+---------------------+---------------------+----------------------+").append("\n");
        System.out.println(results.toString());
    }
}