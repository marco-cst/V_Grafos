package controller.Dao.servicies;

import controller.tda.list.LinkedList;
import models.Universidad;
import controller.Dao.UniversidadDao;

public class UniversidadServicies {
    private UniversidadDao objUniversidadDao;

    public UniversidadServicies(){
        objUniversidadDao = new UniversidadDao();
    }

    public Boolean save() throws Exception{
        return objUniversidadDao.save();
    }

    public Boolean update() throws Exception {
        return objUniversidadDao.update();
    }

    public Boolean delete(Integer id) throws Exception {
        return objUniversidadDao.delete(id);
    }

    public LinkedList listAll() {
        return objUniversidadDao.getlistAll();
    }

    public Universidad getUniversidad() {
        return objUniversidadDao.getUniversidad();
    }

    public void setUniversidad(Universidad universidad) {
        objUniversidadDao.setUniversidad(universidad);
    }

    public Universidad get(Integer id) throws Exception {
        return objUniversidadDao.get(id);
    }
}
