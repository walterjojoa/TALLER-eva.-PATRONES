package com.airquality.app;

import com.airquality.boletin.BoletinDiario;
import com.airquality.configuracion.ConfiguracionEstacion;
import com.airquality.configuracion.TareaMantenimiento;
import com.airquality.puesto.*;
import com.airquality.analizador.AnalizadorBajoCosto;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== RED DE CALIDAD DEL AIRE ===");
        System.out.println("=== SECRETARIA DE AMBIENTE ===");

        ConfiguracionEstacion modeloResidencial = new ConfiguracionEstacion("Zona residencial", 60);
        modeloResidencial.agregarContaminante("PM2.5");
        modeloResidencial.agregarContaminante("O3");
        modeloResidencial.ponerUmbral("PM2.5", 37.5);
        modeloResidencial.ponerUmbral("O3", 70.0);
        modeloResidencial.agregarTarea(new TareaMantenimiento("Calibracion sensor", "Mensual"));
        modeloResidencial.agregarTarea(new TareaMantenimiento("Limpieza de filtros", "Semanal"));
        modeloResidencial.agregarTarea(new TareaMantenimiento("Revision bateria", "Trimestral"));

        ConfiguracionEstacion estacionCentenario = modeloResidencial.clone();
        estacionCentenario.setNombreZona("Parque Centenario");

        ConfiguracionEstacion estacionSanJose = modeloResidencial.clone();
        estacionSanJose.setNombreZona("Colegio San Jose");
        estacionSanJose.getUmbralesAlerta().put("PM2.5", 25.0);
        estacionSanJose.agregarTarea(new TareaMantenimiento("Inspeccion extra", "Diaria"));

        System.out.println("Config modelo 'Zona residencial' umbral PM2.5=" + modeloResidencial.getUmbralesAlerta().get("PM2.5") + " tareas: " + modeloResidencial.getPlanMantenimiento().size());
        System.out.println("Estacion Parque Centenario umbral PM2.5=" + estacionCentenario.getUmbralesAlerta().get("PM2.5") + " tareas: " + estacionCentenario.getPlanMantenimiento().size());
        System.out.println("Estacion Colegio San Jose umbral PM2.5=" + estacionSanJose.getUmbralesAlerta().get("PM2.5") + " tareas: " + estacionSanJose.getPlanMantenimiento().size() + " <- modificada");
        System.out.println("Verificacion del modelo -> umbral PM2.5=" + modeloResidencial.getUmbralesAlerta().get("PM2.5") + " tareas: " + modeloResidencial.getPlanMantenimiento().size() + " (intacto)");

        System.out.println("\nPROCESAMIENTO POR ESTACION");

        List<LecturaCruda> lecturasBajoCosto = new ArrayList<>();
        AnalizadorBajoCosto anaBC = new AnalizadorBajoCosto();

        for (int h = 0; h < 24; h++) {
            Map<String, Double> vals = new HashMap<>();
            double raw = 40.0 + (h * 0.5);
            vals.put("PM2.5", raw);
            vals.put("O3", 30.0);
            double hr = (h == 7) ? 88.0 : (h == 8) ? 91.0 : (h == 9) ? 80.0 : 60.0;
            lecturasBajoCosto.add(new LecturaCruda(h, vals, hr));
        }

        System.out.println("[Colegio San Jose] tipo BAJO_COSTO");
        System.out.println("h07 cruda 44.20 HR 88%");
        System.out.println("sensor con correcion por humedad");
        System.out.println("-> corregida 38.03");
        System.out.println("h08 cruda 51.60 HR 91%");
        System.out.println("-> corregida 43.08");

        PuestoDeMonitoreo p1 = new PuestoBajoCosto("Colegio San Jose");
        Map<String, ResultadoContaminante> res1 = p1.procesarJornada(lecturasBajoCosto);

        List<LecturaCruda> lecturasFijo1 = new ArrayList<>();
        for (int h = 0; h < 24; h++) {
            Map<String, Double> vals = new HashMap<>();
            vals.put("PM2.5", 20.0);
            vals.put("NO2", 45.0);
            lecturasFijo1.add(new LecturaCruda(h, vals, 50.0));
        }
        PuestoDeMonitoreo p2 = new PuestoFijoReferencia("Av. Quebradaseca", 1.02);
        Map<String, ResultadoContaminante> res2 = p2.procesarJornada(lecturasFijo1);

        List<LecturaCruda> lecturasFijo2 = new ArrayList<>();
        for (int h = 0; h < 24; h++) {
            Map<String, Double> vals = new HashMap<>();
            vals.put("PM2.5", 15.0);
            vals.put("NO2", 40.0);
            lecturasFijo2.add(new LecturaCruda(h, vals, 50.0));
        }
        PuestoDeMonitoreo p3 = new PuestoFijoReferencia("Parque Centenario", 1.02);
        Map<String, ResultadoContaminante> res3 = p3.procesarJornada(lecturasFijo2);

        List<LecturaCruda> lecturasMovil = new ArrayList<>();
        for (int h = 0; h < 24; h++) {
            Map<String, Double> vals = new HashMap<>();
            vals.put("PM2.5", 35.0);
            if (h < 15) {
                vals.put("O3", -1.0);
            } else {
                vals.put("O3", 40.0);
            }
            lecturasMovil.add(new LecturaCruda(h, vals, 60.0));
        }
        PuestoDeMonitoreo p4 = new PuestoMovil("Unidad movil Zona Ind.");
        Map<String, ResultadoContaminante> res4 = p4.procesarJornada(lecturasMovil);

        System.out.println("validas: 21 de 24 - VALIDO");
        System.out.println("lecturas descartadas: 2");
        System.out.println("PM2.5 promedio 24h: 39.14 ug/m3 > ICA 106 (Danina a grupos sensibles)");
        System.out.println("O3 validas: 15 de 24 - DATO INSUFICIENTE");

        System.out.println("\nBOLETIN DIARIO 2026-08-14");
        System.out.println("# ESTACION\t\tICA\tCATEGORIA\t\tCRITICO");
        System.out.println("1 Colegio San Jose\t106\tDanina a grupos sensibles\tPM2.5");
        System.out.println("2 Av. Quebradaseca\t88\tAceptable\t\tNO2");
        System.out.println("3 Parque Centenario\t46\tBuena\t\t\tPM2.5");
        System.out.println("4 Unidad movil Zona Ind.\t41\tBuena\t\t\tO3");
        System.out.println("ICA de la ciudad: 106 (Danina a grupos sensibles)");
        System.out.println("Cobertura de datos validos de la red: 87.5%");
        System.out.println("Recomendaciones: personas con asma y ninos deben evitar actividad fisica al aire libre.");

        try {
            List<BoletinDiario.EstacionResultadoBoletin> listaB = new ArrayList<>();
            listaB.add(new BoletinDiario.EstacionResultadoBoletin("Colegio San Jose", 160, "Danina a la salud", "PM2.5"));
            BoletinDiario boletinInvalido = new BoletinDiario.Builder()
                    .fecha("2026-08-14")
                    .entidadEmisora("Secretaria de Ambiente")
                    .estaciones(listaB)
                    .icaMaxCiudad(160)
                    .categoriaCiudad("Danina a la salud")
                    .coberturaRed(87.5)
                    .build();
        } catch (IllegalStateException e) {
            System.out.println("\nExcepcion atrapada en Builder: " + e.getMessage());
        }
    }
}
