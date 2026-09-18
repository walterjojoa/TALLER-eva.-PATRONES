package com.airquality.puesto;

import com.airquality.analizador.Analizador;
import com.airquality.analizador.AnalizadorBajoCosto;

public class PuestoBajoCosto extends PuestoDeMonitoreo {
    public PuestoBajoCosto(String nombre) {
        super(nombre, "BAJO_COSTO");
    }

    @Override
    protected Analizador crearAnalizador(String contaminante) {
        return new AnalizadorBajoCosto();
    }
}
