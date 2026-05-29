package tp;

public class Transferencia extends Actividad {

    private final Cuenta cuentaDestino;
    private final Cuenta cuentaOrigen;

    public Transferencia(Cuenta cuentaOrigen, Cuenta cuentaDestino, double monto, boolean aprobada) {

        super(monto, aprobada);

        if (cuentaDestino == null) {
            throw new IllegalArgumentException("Cuenta destino inválida.");
        }

        this.cuentaDestino = cuentaDestino;
        this.cuentaOrigen = cuentaOrigen;
    }

    public Cuenta consultarCuentaDestino() {
        return cuentaDestino;
    }

    public Cuenta consultarCuentaOrigen() {
        return cuentaOrigen;
    }

    @Override
    public String descripcion() {

        String estado = fueAprobada() ? "Aprobado" : "Rechazado";

        return "fecha: " + consultarFecha() + "\norigen: " + consultarCuentaOrigen().consultarDniTitular() + " (" + consultarCuentaOrigen().consultarCvu() + ")" + "\ndestino: " + cuentaDestino.consultarDniTitular() + " (" + cuentaDestino.consultarCvu() + ")" + "\nmonto: " + consultarMonto() + "\n" + estado;
    }

    @Override
    public String toString() {
        return descripcion();
    }
}