package singleton;

public class SistemaDeUsuarios {
    private static SistemaDeUsuarios instancia;

    private SistemaDeUsuarios() {
       super();
    }
    
    public static SistemaDeUsuarios getInstancia() {
        if (instancia == null) {
            instancia = new SistemaDeUsuarios();
        }
        return instancia;
    }

    //Agregar en un futuro los metodos de usuarios; (login, logout, etc).
}