package puenteambossentidos;

import java.util.Random;

public class Persona implements Runnable {
    // Atributos.
    private final String idPersona;
    private final int tiempoPaso;
    private final int pesoPersona;
    private final Puente puente;
    private final String direccion;
    
    // Constructor.
    public Persona(String idPersona, int tiempoPaso, int pesoPersona, Puente puente, String direccion) {
        this.idPersona = idPersona;
        this.tiempoPaso = tiempoPaso;
        this.pesoPersona = pesoPersona;
        this.puente = puente;
        this.direccion = direccion;
    }
    
    // Getters y setters.
    public String getIdPersona() {
        return idPersona;
    }

    public int getPesoPersona() {
        return pesoPersona;
    }

    public String getDireccion() {
        return direccion;
    }

    // Método run().
    @Override
    public void run() {
        System.out.printf(">>> La %s con %d kilos quiere cruzar en dirección %s y tomará %d segundos.\n",
                idPersona, pesoPersona, direccion, tiempoPaso);
        try {
            // Entrar.
            puente.entrar(this, direccion);
            // Cruzar.
            Thread.sleep(tiempoPaso * 100);
            // Salir.
            puente.salir(this, direccion);
        } catch (InterruptedException e) {
            System.out.printf(">>> La %s fue interrumpida.\n", idPersona);
        }
    }
}
