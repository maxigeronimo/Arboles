package tp;

import java.util.HashSet;
import java.util.Set;

public class Empresa {

    private final String cuit;
    private final String nombreFantasia;
    private final String telefono;
    private final String email;
    private final String nombreContacto;
/*utilizo HashSet para personas autorizadas porque no almacena repetidos y la busqueda es o(1)*/
    private final Set<String> personasAutorizadas;

    public Empresa(String cuit,
                   String nombreFantasia,
                   String telefono,
                   String email,
                   String nombreContacto) {

        this.cuit = validarCuit(cuit);
        this.nombreFantasia = validarNombreFantasia(nombreFantasia);
        this.telefono = validarTelefono(telefono);
        this.email = validarEmail(email);
        this.nombreContacto = validarNombreContacto(nombreContacto);

        this.personasAutorizadas = new HashSet<>();
    }

    public String consultarCuit() {
        return cuit;
    }

    public String consultarNombreFantasia() {
        return nombreFantasia;
    }

    public String consultarTelefono() {
        return telefono;
    }

    public String consultarEmail() {
        return email;
    }

    public String consultarNombreContacto() {
        return nombreContacto;
    }

    public void agregarAutorizado(String dni) {

        if (dni == null || dni.isBlank()) {
            throw new IllegalArgumentException("DNI inválido.");
        }

        if (personasAutorizadas.contains(dni)) {
            throw new IllegalArgumentException("La persona ya está autorizada.");
        }

        personasAutorizadas.add(dni);
    }

    public boolean estaAutorizado(String dni) {
        return personasAutorizadas.contains(dni);
    }

    private String validarCuit(String cuit) {

        if (cuit == null || cuit.isBlank()) {
            throw new IllegalArgumentException("CUIT inválido.");
        }

        return cuit;
    }

    private String validarNombreFantasia(String nombreFantasia) {

        if (nombreFantasia == null || nombreFantasia.isBlank()) {
            throw new IllegalArgumentException("Nombre de fantasía inválido.");
        }

        return nombreFantasia;
    }

    private String validarTelefono(String telefono) {

        if (telefono == null || telefono.isBlank()) {
            throw new IllegalArgumentException("Teléfono inválido.");
        }

        return telefono;
    }

    private String validarEmail(String email) {

        if (email == null || email.isBlank() || !email.contains("@")) {
            throw new IllegalArgumentException("Email inválido.");
        }

        return email;
    }

    private String validarNombreContacto(String nombreContacto) {

        if (nombreContacto == null || nombreContacto.isBlank()) {
            throw new IllegalArgumentException("Nombre de contacto inválido.");
        }

        return nombreContacto;
    }

    @Override
    public String toString() {
        return "Empresa{" +
                "cuit='" + cuit + '\'' +
                ", nombreFantasia='" + nombreFantasia + '\'' +
                ", telefono='" + telefono + '\'' +
                ", email='" + email + '\'' +
                ", nombreContacto='" + nombreContacto + '\'' +
                ", autorizados=" + personasAutorizadas.size() +
                '}';
    }
}