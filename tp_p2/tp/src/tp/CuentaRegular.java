package tp;

public class CuentaRegular extends Cuenta {
    private static final double LIMITE_SALDO = 5_000_000.0;

    public CuentaRegular(String cvu, String alias, String dniTitular) {
        super(cvu, alias, dniTitular);
    }

    @Override
    public boolean puedeRecibir(double monto) {
        return obtenerSaldoDisponible() + monto <= 5_000_000;
    }

    @Override
    public String consultarTipo() {
        return "Regular";
    }

    @Override
    protected void verificarDeposito(double monto) {
        if (obtenerSaldoDisponible() + monto > LIMITE_SALDO) {
            throw new RuntimeException("El saldo no puede superar los 5.000.000 para cuentas regulares.");
        }
    }
}
