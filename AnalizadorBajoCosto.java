package com.airquality.analizador;

public class AnalizadorBajoCosto implements Analizador {
    @Override
    public double corregir(double lecturaCruda, double humedadRelativa) {
        if (humedadRelativa > 75.0) {
            return lecturaCruda / (1.0 + 0.012 * (humedadRelativa - 75.0));
        }
        return lecturaCruda;
    }

    @Override
    public String getNombreAnalizador() {
        return "Sensor de bajo costo con correcion por humedad";
    }
}
