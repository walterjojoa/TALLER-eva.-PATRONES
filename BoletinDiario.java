package com.airquality.boletin;

import java.util.List;

public class BoletinDiario {
    private String fecha;
    private String entidadEmisora;
    private List<EstacionResultadoBoletin> estaciones;
    private int icaMaxCiudad;
    private String categoriaCiudad;
    private String recomendaciones;
    private String pronostico;
    private String anexoDatosFaltantes;
    private String responsable;
    private String notaMetodologica;
    private double coberturaRed;

    private BoletinDiario(Builder builder) {
        this.fecha = builder.fecha;
        this.entidadEmisora = builder.entidadEmisora;
        this.estaciones = builder.estaciones;
        this.icaMaxCiudad = builder.icaMaxCiudad;
        this.categoriaCiudad = builder.categoriaCiudad;
        this.recomendaciones = builder.recomendaciones;
        this.pronostico = builder.pronostico;
        this.anexoDatosFaltantes = builder.anexoDatosFaltantes;
        this.responsable = builder.responsable;
        this.notaMetodologica = builder.notaMetodologica;
        this.coberturaRed = builder.coberturaRed;
    }

    public String getFecha() { return fecha; }
    public String getEntidadEmisora() { return entidadEmisora; }
    public List<EstacionResultadoBoletin> getEstaciones() { return estaciones; }
    public int getIcaMaxCiudad() { return icaMaxCiudad; }
    public String getCategoriaCiudad() { return categoriaCiudad; }
    public String getRecomendaciones() { return recomendaciones; }
    public double getCoberturaRed() { return coberturaRed; }

    public static class EstacionResultadoBoletin {
        private String nombreEstacion;
        private int ica;
        private String categoria;
        private String contaminanteCritico;

        public EstacionResultadoBoletin(String nombreEstacion, int ica, String categoria, String contaminanteCritico) {
            this.nombreEstacion = nombreEstacion;
            this.ica = ica;
            this.categoria = categoria;
            this.contaminanteCritico = contaminanteCritico;
        }

        public String getNombreEstacion() { return nombreEstacion; }
        public int getIca() { return ica; }
        public String getCategoria() { return categoria; }
        public String getContaminanteCritico() { return contaminanteCritico; }
    }

    public static class Builder {
        private String fecha;
        private String entidadEmisora;
        private List<EstacionResultadoBoletin> estaciones;
        private Integer icaMaxCiudad;
        private String categoriaCiudad;
        private String recomendaciones;
        private String pronostico;
        private String anexoDatosFaltantes;
        private String responsable;
        private String notaMetodologica;
        private double coberturaRed;

        public Builder fecha(String fecha) {
            this.fecha = fecha;
            return this;
        }

        public Builder entidadEmisora(String entidad) {
            this.entidadEmisora = entidad;
            return this;
        }

        public Builder estaciones(List<EstacionResultadoBoletin> estaciones) {
            this.estaciones = estaciones;
            return this;
        }

        public Builder icaMaxCiudad(int icaMax) {
            this.icaMaxCiudad = icaMax;
            return this;
        }

        public Builder categoriaCiudad(String cat) {
            this.categoriaCiudad = cat;
            return this;
        }

        public Builder recomendaciones(String rec) {
            this.recomendaciones = rec;
            return this;
        }

        public Builder pronostico(String pron) {
            this.pronostico = pron;
            return this;
        }

        public Builder anexoDatosFaltantes(String anexo) {
            this.anexoDatosFaltantes = anexo;
            return this;
        }

        public Builder responsable(String resp) {
            this.responsable = resp;
            return this;
        }

        public Builder notaMetodologica(String nota) {
            this.notaMetodologica = nota;
            return this;
        }

        public Builder coberturaRed(double cob) {
            this.coberturaRed = cob;
            return this;
        }

        public BoletinDiario build() {
            if (fecha == null || fecha.isEmpty() ||
                entidadEmisora == null || entidadEmisora.isEmpty() ||
                estaciones == null || estaciones.isEmpty() ||
                icaMaxCiudad == null ||
                categoriaCiudad == null || categoriaCiudad.isEmpty()) {
                throw new IllegalStateException("Missing mandatory fields in BoletinDiario");
            }

            boolean esGrave = categoriaCiudad.equals("Danina a la salud") || categoriaCiudad.equals("Muy danina");
            if (esGrave && (recomendaciones == null || recomendaciones.isEmpty())) {
                throw new IllegalStateException("Recommendations are mandatory when category is Danina a la salud or worse");
            }

            return new BoletinDiario(this);
        }
    }
}
