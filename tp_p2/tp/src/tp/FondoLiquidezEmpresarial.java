package tp;

public class FondoLiquidezEmpresarial extends Inversion {

    private static final double TASA_ANUAL = 0.08;
    private static final double MONTO_MINIMO = 20000000;

    public FondoLiquidezEmpresarial(double montoInvertido, int plazoDias) {
        super(montoInvertido, plazoDias, false);
        if (montoInvertido < MONTO_MINIMO) {
            throw new IllegalArgumentException("El fondo de liquidez requiere un monto mínimo de 20 millones.");
        }
    }

    @Override
    public double calcularResultado() {
        double cotizacionFle = Utilitarios.consultarCotizacion("FLE");
        double montoAjustado = consultarMontoInvertido() * cotizacionFle;
        double intereses = montoAjustado * (TASA_ANUAL / 365) * consultarPlazoDias();

        return montoAjustado + intereses;
    }

    @Override
    public double calcularResultadoPrecancelado() {
        throw new RuntimeException("El fondo de liquidez empresarial no es precancelable.");
    }

    @Override
    public String descripcion() {
        return "Fondo Liquidez Empresarial";
    }
}