package tp;

import java.time.LocalDate;

public abstract class Actividad {

    private final LocalDate fecha;
    private final double monto;
    private final boolean aprobada;
    
    /*uso protected porque actividad esta pensado para herencia*/
    protected Actividad(double monto, boolean aprobada) {
        if (monto <= 0) {
            throw new RuntimeException("Monto inválido.");
        }
        this.fecha = Utilitarios.hoy();
        this.monto = monto;
        this.aprobada = aprobada;
    }

    public LocalDate consultarFecha() {
        return fecha;
    }

    public double consultarMonto() {
        return monto;
    }

    public boolean fueAprobada() {
        return aprobada;
    }

    public String consultarEstado() {
        return aprobada ? "Aprobado" : "Rechazado";
    }

    public abstract String descripcion();

    @Override
    public String toString() {
        return descripcion();
    }
}