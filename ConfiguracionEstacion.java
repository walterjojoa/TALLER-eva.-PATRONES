package com.airquality.configuracion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfiguracionEstacion implements Cloneable {
    private String nombreZona;
    private int intervaloMuestreo;
    private List<String> contaminantes;
    private Map<String, Double> umbralesAlerta;
    private List<TareaMantenimiento> planMantenimiento;

    public ConfiguracionEstacion(String nombreZona, int intervaloMuestreo) {
        this.nombreZona = nombreZona;
        this.intervaloMuestreo = intervaloMuestreo;
        this.contaminantes = new ArrayList<>();
        this.umbralesAlerta = new HashMap<>();
        this.planMantenimiento = new ArrayList<>();
    }

    public void agregarContaminante(String c) {
        contaminantes.add(c);
    }

    public void ponerUmbral(String c, double val) {
        umbralesAlerta.put(c, val);
    }

    public void agregarTarea(TareaMantenimiento t) {
        planMantenimiento.add(t);
    }

    public String getNombreZona() {
        return nombreZona;
    }

    public void setNombreZona(String nombreZona) {
        this.nombreZona = nombreZona;
    }

    public Map<String, Double> getUmbralesAlerta() {
        return umbralesAlerta;
    }

    public List<TareaMantenimiento> getPlanMantenimiento() {
        return planMantenimiento;
    }

    @Override
    public ConfiguracionEstacion clone() {
        ConfiguracionEstacion clon = new ConfiguracionEstacion(this.nombreZona, this.intervaloMuestreo);
        for (String c : this.contaminantes) {
            clon.contaminantes.add(c);
        }
        for (Map.Entry<String, Double> entry : this.umbralesAlerta.entrySet()) {
            clon.umbralesAlerta.put(entry.getKey(), entry.getValue());
        }
        for (TareaMantenimiento t : this.planMantenimiento) {
            clon.planMantenimiento.add(t.clone());
        }
        return clon;
    }
}
