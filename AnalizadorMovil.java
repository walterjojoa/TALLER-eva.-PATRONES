package com.airquality.analizador;

public class AnalizadorMovil implements Analizador {
    @Override
    public double corregir(double lecturaCruda, double humedadRelativa) {
        return lecturaCruda * 1.05 + 0.8;
    }

    @Override
    public String getNombreAnalizador() {
        return "Analizador optico portatil";
    }
}
