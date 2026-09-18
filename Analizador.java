package com.airquality.analizador;

public interface Analizador {
    double corregir(double lecturaCruda, double humedadRelativa);
    String getNombreAnalizador();
}
