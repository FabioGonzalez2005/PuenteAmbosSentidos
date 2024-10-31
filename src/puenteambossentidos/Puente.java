package puenteambossentidos;

public class Puente {
    // Constantes.
    private static final int MAXIMO_PERSONAS = 4;
    private static final int MAXIMO_PERSONAS_POR_SENTIDO = 3;
    private static final int MAXIMO_PESO = 300;
    
    // Variables.
    private int numeroPersonas = 0;
    private int pesoPersonas = 0;
    private int personasNorte = 0; // Contador para dirección norte
    private int personasSur = 0;   // Contador para dirección sur
    
    // Constructor.
    public Puente() {}

    // Getters.
    public int getNumeroPersonas() {
        return numeroPersonas;
    }

    public int getPesoPersonas() {
        return pesoPersonas;
    }

    // Método para que la persona entre al puente.
    public synchronized void entrar(Persona persona, String direccion) throws InterruptedException {
        while ((numeroPersonas >= MAXIMO_PERSONAS) || 
               (pesoPersonas + persona.getPesoPersona() > MAXIMO_PESO) || 
               (direccion.equals("NORTE") && personasNorte >= MAXIMO_PERSONAS_POR_SENTIDO) ||
               (direccion.equals("SUR") && personasSur >= MAXIMO_PERSONAS_POR_SENTIDO)) {
            System.out.printf("*** La %s debe esperar. Dirección: %s\n", persona.getIdPersona(), direccion);
            wait();
        }

        // Actualizar variables de estado
        numeroPersonas++;
        pesoPersonas += persona.getPesoPersona();
        if (direccion.equals("NORTE")) {
            personasNorte++;
        } else {
            personasSur++;
        }

        System.out.printf(">>> La %s entra en dirección %s. Estado del puente: %d personas, %d kilos.\n",
                persona.getIdPersona(), direccion, numeroPersonas, pesoPersonas);
    }

    // Método para que la persona salga del puente.
    public synchronized void salir(Persona persona, String direccion) {
        numeroPersonas--;
        pesoPersonas -= persona.getPesoPersona();
        
        if (direccion.equals("NORTE")) {
            personasNorte--;
        } else {
            personasSur--;
        }

        notifyAll();
        System.out.printf(">>> La %s sale de dirección %s. Estado del puente: %d personas, %d kilos.\n",
                persona.getIdPersona(), direccion, numeroPersonas, pesoPersonas);
    }
}