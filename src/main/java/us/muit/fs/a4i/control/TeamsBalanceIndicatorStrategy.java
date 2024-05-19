package us.muit.fs.a4i.control;

import us.muit.fs.a4i.exceptions.NotAvailableMetricException;
import us.muit.fs.a4i.exceptions.ReportItemException;
import us.muit.fs.a4i.model.entities.Indicator;
import us.muit.fs.a4i.model.entities.Organizacion;
import us.muit.fs.a4i.model.entities.Proyecto;
import us.muit.fs.a4i.model.entities.ReportItem;
import us.muit.fs.a4i.model.entities.ReportItemI;

import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.lang.Math;

public class TeamsBalanceIndicatorStrategy implements IndicatorStrategy<Organizacion>{

	private static Logger log = Logger.getLogger(Indicator.class.getName());
    private static final List<String> REQUIRED_METRICS = Arrays.asList("issuesPerRepository","teamsPerRepository","teams","repositories");
    

    @Override
    public ReportItemI<Organizacion> calcIndicator(List<ReportItemI<Organizacion>> metrics) throws NotAvailableMetricException {
        //Metricas: Numero de proyectos, numero de issues por proyecto, miembros por proyecto
        ReportItemI<Organizacion>  organizacionRI = metrics.stream().filter(m -> REQUIRED_METRICS.get(0).equals(m.getName())).findAny().orElseThrow(() -> new NotAvailableMetricException("La metrica organizacion no ha podido ser recuperada"));
        List<Proyecto> proyectos = organizacionRI.getValue().getProyectos();
        Organizacion organizacion = organizacionRI.getValue();

        ReportItemI<Organizacion> indicatorReport = null;

        Integer issuesTotales=0;
        Integer desarrolladoresTotales=0;
        Double desajustePromedioOrganizacion=0.0;
        List<Double> desajustePromedioProyecto = new ArrayList<Double>();

        //Obtener el numero total de issues y desarrolladores
        for(Proyecto proyecto: proyectos){
            issuesTotales+=proyecto.getNumeroIssues();
            desarrolladoresTotales+=proyecto.getNumeroMiembros();
        }

        //Calcular el desajuste promedio de cada proyecto y guardarlo en desajustePromedioProyecto
        for(Proyecto proyecto: proyectos){
            if (proyecto.getNumeroMiembros()!=0){
                desajustePromedioProyecto.add((double) (Math.abs((proyecto.getNumeroIssues())/(issuesTotales)-(proyecto.getNumeroMiembros())/(desarrolladoresTotales))));
            }
            
        }

        //Sumar todos los desajustes y guardarlos en desajustePromedioOrganizacion
        for (Double desajuste: desajustePromedioProyecto){
            desajustePromedioOrganizacion+=desajuste;
        }

        organizacion.setDesajustePromedioOrganizacion(desajustePromedioOrganizacion);

        try {
            indicatorReport = new ReportItem.ReportItemBuilder<Organizacion>("teamsBalance", organizacion)
                 .metrics(Arrays.asList(organizacionRI))
                 .indicator(Indicator.IndicatorState.UNDEFINED).build();
        } catch (ReportItemException e) {
            log.info("Error en construcción del indicatorReport de la clase "+this.getClass().getSimpleName());
            log.info(e.toString());
        }
        
        return indicatorReport;
    }

    @Override
    public List<String> requiredMetrics() {
        return REQUIRED_METRICS;
    }
}
