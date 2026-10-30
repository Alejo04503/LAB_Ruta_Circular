package edu.uajs.listas.ruta;

/** PARTE 1: ejecuta el contrato contra la implementación manual. */
class RutaCircularManualTest extends RutaCircularContractTest {
    @Override
    RutaCircular crear() {
        return new RutaCircularManual();
    }
}
