package tp;

public class CuentaCorporativa extends Cuenta {
    private final String cuitEmpresa;

    public CuentaCorporativa(String cvu, String alias, String dniTitular, String cuitEmpresa) {
        super(cvu, alias, dniTitular);
        this.cuitEmpresa = validarCuit(cuitEmpresa);
    }

    @Override
    public boolean puedeRecibir(double monto) {
        return true;
    }

    public String consultarCuitEmpresa() {
        return cuitEmpresa;
    }

    @Override
    public String consultarTipo() {
        return "Corporativa";
    }

    @Override
    protected void verificarDeposito(double monto) {
        // Las cuentas corporativas no tienen límite superior de saldo.
    }

    private String validarCuit(String cuit) {
        if (cuit == null || cuit.isBlank()) {
            throw new RuntimeException("CUIT inválido para la cuenta corporativa.");
        }
        return cuit;
    }
}
