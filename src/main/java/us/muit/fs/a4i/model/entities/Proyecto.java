package us.muit.fs.a4i.model.entities;

public class Proyecto {
    private Integer numeroMiembros;
    private Integer numeroIssues;

    public Proyecto(Integer numeroMiembros, Integer numeroIssues) {
        this.numeroMiembros = numeroMiembros;
        this.numeroIssues = numeroIssues;
    }

    public Integer getNumeroMiembros() {
        return numeroMiembros;
    }

    public void setNumeroMiembros(Integer numeroMiembros) {
        this.numeroMiembros = numeroMiembros;
    }

    public Integer getNumeroIssues() {
        return numeroIssues;
    }

    public void setNumeroIssues(Integer numeroIssues) {
        this.numeroIssues = numeroIssues;
    }
}
