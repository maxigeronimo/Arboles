package tp;

import java.time.LocalDate;

public abstract class Inversion extends Actividad {

    private static int proximoId = 1;

    private int id;
    private LocalDate fechaConstitucion;
    private int plazoDias;
    private double montoInvertido;
    private boolean precancelable;
    private boolean fuePrecancelada;

    public Inversion(double montoInvertido, int plazoDias, boolean precancelable) {
        super(montoInvertido, true);

        if (montoInvertido <= 0) {
            throw new IllegalArgumentException("El monto invertido debe ser positivo.");
        }

        if (plazoDias <= 0) {
            throw new IllegalArgumentException("El plazo debe ser positivo.");
        }

        this.id = proximoId++;
        this.fechaConstitucion = Utilitarios.hoy();
        this.plazoDias = plazoDias;
        this.montoInvertido = montoInvertido;
        this.precancelable = precancelable;
        this.fuePrecancelada = false;
    }

    public int consultarId() {
        return id;
    }

    public LocalDate consultarFechaConstitucion() {
        return fechaConstitucion;
    }

    public int consultarPlazoDias() {
        return plazoDias;
    }

    public double consultarMontoInvertido() {
        return montoInvertido;
    }

    public boolean esPrecancelable() {
        return precancelable;
    }

    public boolean fuePrecancelada() {
        return fuePrecancelada;
    }

    public boolean estaActiva() {
        return !fuePrecancelada;
    }

    public void marcarPrecancelada() {
        if (!precancelable) {
            throw new IllegalArgumentException("La inversión no es precancelable.");
        }

        if (fuePrecancelada) {
            throw new IllegalArgumentException("La inversión ya fue precancelada.");
        }

        fuePrecancelada = true;
    }

    public int diasTranscurridos() {
        return (int) java.time.temporal.ChronoUnit.DAYS.between(fechaConstitucion, Utilitarios.hoy());
    }

    public boolean venceHoy() {
        LocalDate fechaVencimiento = fechaConstitucion.plusDays(plazoDias);
        return fechaVencimiento.equals(Utilitarios.hoy());
    }

    public abstract double calcularResultado();

    public abstract double calcularResultadoPrecancelado();

    public abstract String descripcion();
}