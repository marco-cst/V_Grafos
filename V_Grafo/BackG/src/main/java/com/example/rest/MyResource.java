package com.example.rest;

import java.util.HashMap;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import controller.Dao.servicies.UniversidadServicies;
import controller.tda.graph.GraphLabelNoDirect;
import controller.tda.list.LinkedList;
import models.Universidad;

@SuppressWarnings("unchecked")
@Path("myresource")
public class MyResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getIt() {

        HashMap<String, Object> mapa = new HashMap<>();
        // GraphLabelNoDirect<Universidad> graph = new GraphLabelNoDirect<>(5, Universidad.class);
        GraphLabelNoDirect graph = new GraphLabelNoDirect<>(5, Universidad.class);

        try {
            UniversidadServicies ps = new UniversidadServicies();
            LinkedList<Universidad> lp = ps.listAll();
            if (!lp.isEmpty()) {
                graph = new GraphLabelNoDirect<>(lp.getSize(), Universidad.class);
                Universidad[] m = lp.toArray();
                for (int i = 0; i < lp.getSize(); i++) {
                    graph.labelsVerticeL((i + 1), m[i]);
                }
            }

            Universidad a = new Universidad("UNL");
            Universidad b = new Universidad("UTPL");
            Universidad c = new Universidad("UPM");

            graph.labelsVerticeL(1, a);
            graph.labelsVerticeL(2, b);
            graph.labelsVerticeL(3, c);

            graph.insertEdgeL(a, b);
            graph.insertEdgeL(a, c);

        graph.drawGraph();

        } catch (Exception e) {
            mapa.put("msg", "Ok");
            mapa.put("data", e.toString());
            return Response.status(Status.BAD_REQUEST).entity(mapa).build();
        }
        System.out.println(graph.toString());

        mapa.put("msg", "OK");
        mapa.put("data", graph.toString());
        return Response.ok(mapa).build();
    }
}