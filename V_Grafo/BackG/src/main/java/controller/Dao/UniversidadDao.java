package controller.Dao;

import models.Universidad;
import controller.Dao.implement.AdapterDao;
import controller.tda.list.LinkedList;

public class UniversidadDao extends AdapterDao<Universidad> {
    private Universidad universidad;
    private LinkedList<Universidad> listAll;

    public UniversidadDao() {
        super(Universidad.class);
        this.listAll = new LinkedList<>();
    }

    public Universidad getUniversidad() {
        if (universidad == null) {
            universidad = new Universidad();
        }
        return this.universidad;
    }

    public void setUniversidad(Universidad universidad) {
        this.universidad = universidad;
    }

    public LinkedList<Universidad> getlistAll() {
        if (listAll.isEmpty()) {
            this.listAll = listAll();
        }
        return listAll;
    }

    public Boolean update() throws Exception {
        try {
            this.merge(getUniversidad(), getUniversidad().getIdUniversidad() - 1);
            this.listAll = getlistAll();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean save() throws Exception {
        Integer id = getlistAll().getSize() + 1;
        universidad.setIdUniversidad(id);
        this.persist(this.universidad);
        this.listAll = getlistAll();
        return true;
    }

    public Boolean delete(Integer id) throws Exception {
        LinkedList<Universidad> list = getlistAll();
        Universidad universidad = get(id);
        if (universidad != null) {
            list.remove(universidad);
            String info = g.toJson(list.toArray());
            saveFile(info);
            this.listAll = list;
            return true;
        } else {
            System.out.println("Universidad con id " + id + " no encontrada.");
            return false;
        }
    }
}
