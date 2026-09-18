package com.airquality.configuracion;

import java.io.Serializable;

public class TareaMantenimiento implements Serializable, Cloneable {
    private String descripcion;
    private String periodicidad;

    public TareaMantenimiento(String descripcion, String periodicidad) {
        this.descripcion = descripcion;
        this.periodicidad = periodicidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPeriodicidad() {
        return periodicidad;
    }

    public void setPeriodicidad(String periodicidad) {
        this.periodicidad = periodicidad;
    }

    @Override
    public TareaMantenimiento clone() {
        return new TareaMantenimiento(this.descripcion, this.periodicidad);
    }
}
