package us.muit.fs.a4i.model.entities;

import java.util.List;

public class Organizacion {
    private List<Proyecto> proyectos;
    private Double desajustePromedioOrganizacion;

    public Organizacion(List<Proyecto> proyectos) {
        this.proyectos = proyectos;
    }

    public Organizacion(List<Proyecto> proyectos, Double desajustePromedioOrganizacion) {
        this.proyectos = proyectos;
        this.desajustePromedioOrganizacion = desajustePromedioOrganizacion;
    }

    public List<Proyecto> getProyectos() {
        return proyectos;
    }

    public void setProyectos(List<Proyecto> proyectos) {
        this.proyectos = proyectos;
    }

    public Double getDesajustePromedioOrganizacion() {
        return desajustePromedioOrganizacion;
    }

    public void setDesajustePromedioOrganizacion(Double desajustePromedioOrganizacion) {
        this.desajustePromedioOrganizacion = desajustePromedioOrganizacion;
    }
}
