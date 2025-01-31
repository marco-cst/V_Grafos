package com.example.rest;

import controller.Dao.servicies.UniversidadServicies;
import java.util.HashMap;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import models.Universidad;
import com.google.gson.Gson;

@SuppressWarnings("unchecked")
@Path("universidad")
public class UniversidadApi {
    @Path("/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUniversidads() {
        HashMap map = new HashMap<>();
        UniversidadServicies ps = new UniversidadServicies();
        map.put("msg", "Ok");
        map.put("data", ps.listAll().toArray());
        if (ps.listAll().isEmpty()) {
            map.put("data", new Object[] {});
        }
        return Response.ok(map).build();
    }

    @Path("/get/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUniversidad(@PathParam("id") Integer id) {
        HashMap map = new HashMap<>();
        UniversidadServicies ps = new UniversidadServicies();
        try {
            ps.setUniversidad(ps.get(id));
        } catch (Exception e) {
        }

        map.put("msg", "Ok");
        map.put("data", ps.getUniversidad());

        if (ps.getUniversidad() == null || ps.getUniversidad().getIdUniversidad() == 0) {
            map.put("msg", "No se encontró universidad con ese id");
            return Response.status(Status.NOT_FOUND).entity(map).build();
        }
        if (ps.listAll().isEmpty()) {
            map.put("data", new Object[] {});
            return Response.status(Status.BAD_REQUEST).entity(map).build();
        }
        return Response.ok(map).build();
    }

    @Path("/save")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(HashMap map) {
        HashMap res = new HashMap<>();
        Gson g = new Gson();
        String a = g.toJson(map);
        try {
            UniversidadServicies ps = new UniversidadServicies();
            StringBuilder missingParams = new StringBuilder();

            String[] requiredParams = { "nombre", "latitud", "longitud", "tipo" };
            for (String param : requiredParams) {
                if (map.get(param) == null) {
                    missingParams.append(param).append(", ");
                }
            }

            ps.getUniversidad().setNombre(map.get("nombre").toString());
            ps.getUniversidad().setLatitud(Double.parseDouble(map.get("latitud").toString()));
            ps.getUniversidad().setLongitud(Double.parseDouble(map.get("longitud").toString()));
            ps.getUniversidad().setTipo(map.get("tipo").toString());

            ps.save();
            res.put("msg", "Ok");
            res.put("data", "Guardado correctamente");
            return Response.ok(res).build();

        } catch (Exception e) {
            res.put("msg", "Error");
            res.put("data", "Error: " + e.toString());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }

    @Path("/update")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(HashMap<String, Object> map) {
        HashMap<String, Object> res = new HashMap<>();
        try {
            UniversidadServicies ps = new UniversidadServicies();
            if (!map.containsKey("idUniversidad")) {
                res.put("msg", "Error");
                res.put("data", "El campo idUniversidad es requerido.");
                return Response.status(Status.BAD_REQUEST).entity(res).build();
            }
            Integer idUniversidad = Integer.parseInt(map.get("idUniversidad").toString());
            Universidad universidad = ps.get(idUniversidad);

            if (universidad == null) {
                res.put("msg", "Error");
                res.put("data", "Universidad no encontrada.");
                return Response.status(Status.NOT_FOUND).entity(res).build();
            }
            map.forEach((key, value) -> {
                switch (key) {
                    case "Nombre":
                        universidad.setNombre(value.toString());
                        break;
                    case "Latitud":
                        universidad.setLatitud(Double.parseDouble(value.toString()));
                        break;
                    case "Longitud":
                        universidad.setLongitud(Double.parseDouble(value.toString()));
                        break;
                    case "Tipo":
                        universidad.setTipo(value.toString());
                        break;
                }
            });

            ps.update();

            res.put("msg", "Ok");
            res.put("data", "Universidad actualizada correctamente.");
            return Response.ok(res).build();

        } catch (Exception e) {
            res.put("msg", "Error");
            res.put("data", "Error: " + e.toString());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }

    @Path("/delete")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(HashMap<String, Object> map) {
        HashMap<String, Object> res = new HashMap<>();
        try {
            UniversidadServicies ps = new UniversidadServicies();
            Integer id = Integer.parseInt(map.get("idUniversidad").toString());

            Boolean success = ps.delete(id);
            if (success) {
                res.put("msg", "Ok");
                res.put("data", "Eliminado");
                return Response.ok(res).build();
            } else {
                res.put("msg", "Error");
                res.put("data", "Universidad no encontrada");
                return Response.status(Status.NOT_FOUND).entity(res).build();
            }
        } catch (Exception e) {
            System.out.println("Error al borrar" + e.toString());
            res.put("msg", "Error");
            res.put("data", e.toString());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }
}