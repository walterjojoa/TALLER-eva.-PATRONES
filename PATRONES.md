# Patrones 

1. Factory Method
Ubicacion: Paquete `com.airquality.puesto (PuestoDeMonitoreo, PuestoFijoReferencia, PuestoMovil`, `PuestoBajoCosto`) y paquete `com.airquality.analizador`.
-Justificacion: Permite delegar la creacion del analizador correspondiente a las subclases de puestos (`crearAnalizador(String contaminante)). De esta forma, la logica de procesamiento de la jornada es generica en la clase abstracta, y cada tipo de puesto instancia el analizador con su formula de correccion especifica sin acoplar codigo condicional.

2. Prototype
-Ubicacion: Paquete com.airquality.configuracion (`ConfiguracionEstacion, TareaMantenimiento).
-Justificacion: Permite duplicar configuraciones modelo base (como "Zona residencial") para nuevas estaciones realizando clonacion profunda de los mapas de umbrales, listas de contaminantes y tareas de mantenimiento, garantizando la independencia de cada estacion clonada frente al modelo base y entre ellas.

3. Builder
-Ubicacion**: Paquete com.airquality.boletin (`BoletinDiario).
- Justificacion: Facilita la construccion paso a paso de objetos inmutables complejos (`BoletinDiario`) que combinan atributos obligatorios y opcionales, validando reglas de negocio criticas en el metodo `build() (como lanzar `IllegalStateException` si faltan obligatorios o si la categoria es de riesgo sin recomendaciones).
