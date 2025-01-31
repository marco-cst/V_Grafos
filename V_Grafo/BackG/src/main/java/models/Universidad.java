package models;

public class Universidad {
    private int idUniversidad;
    private String nombre;
    private Double latitud = 0.0;
    private Double longitud = 0.0;
    private String tipo;

    public Universidad() {
    }

    public Universidad(int idUniversidad, String nombre, Double latitud, Double longitud, String tipo) {
        this.idUniversidad = idUniversidad;
        this.nombre = nombre;
        this.latitud = latitud;
        this.longitud = longitud;
        this.tipo = tipo;
    }
    
    public Universidad(String nombre) {
        this.nombre = nombre;
    }

    public int getIdUniversidad() {
        return idUniversidad;
    }

    public void setIdUniversidad(int idUniversidad) {
        this.idUniversidad = idUniversidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    @Override
    public String toString() {
        return "Universidad [idUniversidad=" + idUniversidad + ", nombre=" + nombre + ", latitud=" + latitud + ", longitud=" + longitud + ", tipo=" + tipo + "]";
    }

    public static Boolean update() {
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }
}
