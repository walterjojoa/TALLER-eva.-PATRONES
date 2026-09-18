package com.airquality.analizador;

public class AnalizadorFijoReferencia implements Analizador {
    private double factorCalibracion;

    public AnalizadorFijoReferencia(double factorCalibracion) {
        this.factorCalibracion = factorCalibracion;
    }

    @Override
    public double corregir(double lecturaCruda, double humedadRelativa) {
        return lecturaCruda * factorCalibracion;
    }

    @Override
    public String getNombreAnalizador() {
        return "Analizador de metodo de referencia";
    }
}
