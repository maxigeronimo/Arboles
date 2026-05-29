package tp;
public class Usuario {

    private final String dni;
    private final String nombre;
    private final String telefono;
    private final String email;
    private double totalInvertido;

    public Usuario(String dni, String nombre, String telefono, String email) {
        this.dni = validarDni(dni);
        this.nombre = validarNombre(nombre);
        this.telefono = validarTelefono(telefono);
        this.email = validarEmail(email);
        this.totalInvertido=0;
    }

    public String consultarDni() {
        return dni;
    }

    public String consultarNombre() {
        return nombre;
    }

    public String consultarTelefono() {
        return telefono;
    }

    public String consultarEmail() {
        return email;
    }

    private static String validarDni(String dni) {
        if (dni == null) {
            throw new RuntimeException("DNI no puede ser nulo");
        }
        if (dni.length() <= 3) {
            throw new RuntimeException("DNI debe tener más de 3 caracteres");
        }
        if (!dni.matches("\\d+")) {
            throw new RuntimeException("DNI debe contener solo dígitos");
        }
        return dni;
    }

    private static String validarNombre(String nombre) {
        if (nombre == null) {
            throw new RuntimeException("Nombre no puede ser nulo");
        }
        if (nombre.length() < 3) {
            throw new RuntimeException("Nombre inválido");
        }
        return nombre;
    }

    private static String validarTelefono(String telefono) {
        if (telefono == null) {
            throw new RuntimeException("Teléfono no puede ser nulo");
        }
        if (telefono.length() < 3) {
            throw new RuntimeException("Teléfono inválido");
        }
        if (!telefono.matches("^[0-9()\\s+-]+$")) {
            throw new RuntimeException("Teléfono inválido: solo se permiten dígitos, espacios, paréntesis, guiones y signos de suma");
        }
        return telefono;
    }

    private static String validarEmail(String email) {
        if (email == null) {
            throw new RuntimeException("Email no puede ser nulo");
        }
        int arroba = email.indexOf('@');
        if (arroba <= 0 || arroba == email.length() - 1) {
            throw new RuntimeException("Email inválido");
        }
        String dominio = email.substring(arroba + 1);
        if (!dominio.contains(".")) {
            throw new RuntimeException("Email inválido");
        }
        return email;
    }

    public double obtenerTotalInvertido() {
        return totalInvertido;
    }

    public void sumarTotalInvertido(double monto) {
        if (monto <= 0) {
            throw new IllegalArgumentException("Monto inválido.");
        }
        totalInvertido += monto;
    }

    public void restarTotalInvertido(double monto) {
        if (monto <= 0 || monto > totalInvertido) {
            throw new IllegalArgumentException("Monto inválido.");
        }
        totalInvertido -= monto;
    }

    @Override
    public String toString() {
        return "Usuario{dni='" + dni + "', nombre='" + nombre + "', telefono='" + telefono + "', email='" + email + "'}";
    }
}

