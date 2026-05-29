package tp;

public class InversionDivisa extends Inversion {

    private String divisa;
    private double tasaAnual;
    private double cantidadDivisaComprada;

    public InversionDivisa(double montoInvertido, int plazoDias, String divisa, double tasaAnual) {
        super(montoInvertido, plazoDias, true);

        if (divisa == null || divisa.trim().isEmpty()) {
            throw new IllegalArgumentException("La divisa no puede estar vacía.");
        }

        if (tasaAnual < 0) {
            throw new IllegalArgumentException("La tasa no puede ser negativa.");
        }

        this.divisa = divisa;
        this.tasaAnual = tasaAnual;

        double cotizacionInicial = Utilitarios.consultarCotizacion(divisa);
        this.cantidadDivisaComprada = montoInvertido / cotizacionInicial;
    }

    @Override
    public double calcularResultado() {
        double interesesEnDivisa = cantidadDivisaComprada * (tasaAnual / 365) * consultarPlazoDias();
        double totalDivisa = cantidadDivisaComprada + interesesEnDivisa;
        return totalDivisa * Utilitarios.consultarCotizacion(divisa);
    }

    @Override
    public double calcularResultadoPrecancelado() {
        int dias = diasTranscurridos();
        double interesesEnDivisa = cantidadDivisaComprada * (tasaAnual / 365) * dias;
        interesesEnDivisa = interesesEnDivisa / 2;

        double totalDivisa = cantidadDivisaComprada + interesesEnDivisa;
        return totalDivisa * Utilitarios.consultarCotizacion(divisa);
    }

    @Override
    public String descripcion() {
        return "Inversion Divisa " + divisa;
    }

    public String getDivisa() {
        return divisa;
    }
}