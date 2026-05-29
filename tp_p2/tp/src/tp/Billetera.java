package tp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Billetera implements IBilletera {
	private Map<String, Usuario> usuariosPorDni;
    private Map<String, Empresa> empresasPorCuit;
    private Map<String, Cuenta> cuentasPorCvu;
    private Map<String, Cuenta> cuentasPorAlias;
    private Map<String, List<Cuenta>> cuentasPorUsuario;
    private List<Actividad> actividadesGlobales;

	public Billetera() {
		this.usuariosPorDni = new HashMap<>();
        this.empresasPorCuit = new HashMap<>();
        this.cuentasPorCvu = new HashMap<>();
        this.cuentasPorAlias = new HashMap<>();
        this.cuentasPorUsuario = new HashMap<>();
        this.actividadesGlobales = new ArrayList<>();
	}

	@Override
	public void registrarEmpresa(String cuit, String nombreFantasia, String telefono, String email,
			String nombreContacto) {

		if (empresasPorCuit.containsKey(cuit)){
			throw new IllegalArgumentException("La empresa ya esta registrada.");
		}

		Empresa empresa = new Empresa(cuit, nombreFantasia, telefono, email, nombreContacto);

		empresasPorCuit.put(cuit, empresa);
	}

	@Override
	public void agregarPersonaAutorizada(String cuitEmpresa, String dniAutorizado) {
		
		Empresa empresa = empresasPorCuit.get(cuitEmpresa);

		if (empresa == null) {
			throw new IllegalArgumentException("Empresa inexistente.");
		}

		empresa.agregarAutorizado(dniAutorizado);

	}

	@Override
	public void registrarUsuario(String dni, String nombre, String telefono, String email) {
		if (usuariosPorDni.containsKey(dni)) {
        	throw new IllegalArgumentException("El usuario ya está registrado.");
    	}
    	Usuario usuario = new Usuario(dni, nombre, telefono, email);
    	usuariosPorDni.put(dni, usuario);
	}

	@Override
	public String crearCuentaRegular(String dniUsuario, String alias) {
		validarUsuarioYAliasParaCreacion(dniUsuario, alias);

		String cvu = Utilitarios.generarSiguienteCvu();
		Cuenta cuenta = new CuentaRegular(cvu, alias, dniUsuario);

		registrarCuenta(cuenta);
		return cvu;
	}

	@Override
	public String crearCuentaPremium(String dniUsuario, String alias, double depositoInicial) {
		validarUsuarioYAliasParaCreacion(dniUsuario, alias);
		if (depositoInicial < 500000) throw new IllegalArgumentException("Depósito inicial insuficiente para cuenta premium");

		String cvu = Utilitarios.generarSiguienteCvu();
		Cuenta cuenta = new CuentaPremium(cvu, alias, dniUsuario, depositoInicial);

		registrarCuenta(cuenta);
		return cvu;
	}

	@Override
	public String crearCuentaCorporativa(String dniUsuario, String alias, String cuitEmpresa) {
		validarUsuarioYAliasParaCreacion(dniUsuario, alias);
		Empresa emp = empresasPorCuit.get(cuitEmpresa);
		if (emp == null) throw new IllegalArgumentException("Empresa no registrada: " + cuitEmpresa);
		if (!emp.estaAutorizado(dniUsuario)) throw new IllegalArgumentException("Usuario no autorizado para empresa: " + dniUsuario);

		String cvu = Utilitarios.generarSiguienteCvu();
		Cuenta cuenta = new CuentaCorporativa(cvu, alias, dniUsuario, cuitEmpresa);

		registrarCuenta(cuenta);
		return cvu;
	}

	@Override
	public List<String> obtenerCuentas(String dniUsuario) {
		if (!usuariosPorDni.containsKey(dniUsuario)) throw new IllegalArgumentException("Usuario no existe: " + dniUsuario);
		List<Cuenta> cuentas = cuentasPorUsuario.get(dniUsuario);
		List<String> resultado = new ArrayList<>();
		if (cuentas != null) {
			for (Cuenta c : cuentas) {
				resultado.add(c.consultarTipo() + ": " + c.consultarAlias() + " (" + c.consultarCvu() + ")");
			}
		}
		return resultado;
	}

	@Override
	public double obtenerSaldoDisponible(String cvu) {
		Cuenta c = cuentasPorCvu.get(cvu);
		if (c == null) throw new IllegalArgumentException("Cuenta no encontrada: " + cvu);
		return c.obtenerSaldoDisponible();
	}

	@Override
	public void realizarTransferencia(String cvuOrigen, String cvuDestino, double monto) {
		Cuenta[] cuentas = validarDatosTransferencia(cvuOrigen, cvuDestino, monto);
		Cuenta origen = cuentas[0];
    	Cuenta destino = cuentas[1];

    	origen.retirar(monto);
    	destino.depositar(monto);

    	Transferencia transferencia = new Transferencia(origen, destino, monto, true);

    	actividadesGlobales.add(transferencia);

    	origen.registrarActividad(transferencia);
    	destino.registrarActividad(transferencia);
	}

	private Cuenta[] validarDatosTransferencia(String cvuOrigen, String cvuDestino, double monto){
		Cuenta origen = cuentasPorCvu.get(cvuOrigen);
    	Cuenta destino = cuentasPorCvu.get(cvuDestino);

    	if (origen == null) {
        	throw new IllegalArgumentException("Cuenta origen inexistente.");
    	}

    	if (destino == null) {
        	throw new IllegalArgumentException("Cuenta destino inexistente.");
    	}

    	if (monto <= 0) {
        	throw new IllegalArgumentException("Monto inválido.");
    	}

    	if (!destino.puedeRecibir(monto)) {
        	throw new IllegalStateException("La cuenta destino supera el límite permitido.");
    	}

		return new Cuenta[] { origen, destino };
	}

	@Override
	public int realizarInversionRentaFija(String dni, String cvu, double monto, int plazoDias) {
		Cuenta cuenta = validarDatosBasicosInversion(dni, cvu, monto, plazoDias);
		Inversion inversion = new RentaFija(monto, plazoDias);
		cuenta.retirar(monto);
		cuenta.agregarMontoInvertido(monto);
		cuenta.registrarActividad(inversion);
		actividadesGlobales.add(inversion);
		Usuario usuario = usuariosPorDni.get(dni);
		usuario.sumarTotalInvertido(monto);
		return inversion.consultarId();
	}

	@Override
	public int realizarInversionDivisa(String dni, String cvu, double monto, int plazoDias, String divisa,
			double tasa) {
		Cuenta cuenta = validarDatosBasicosInversion(dni, cvu, monto, plazoDias);
		Inversion inversion = new InversionDivisa(monto, plazoDias, divisa, tasa);
		cuenta.retirar(monto);
		cuenta.agregarMontoInvertido(monto);
		cuenta.registrarActividad(inversion);
		actividadesGlobales.add(inversion);
		Usuario usuario = usuariosPorDni.get(dni);
		usuario.sumarTotalInvertido(monto);
		return inversion.consultarId();
	}

	@Override
	public int realizarInversionLiquidez(String dni, String cvu, double monto, int plazoDias) {
 		Cuenta cuenta = validarDatosBasicosInversion(dni, cvu, monto, plazoDias);
		if (!(cuenta instanceof CuentaCorporativa)) {
			throw new IllegalArgumentException("El fondo de liquidez solo puede hacerse desde una cuenta corporativa.");
		}
		Inversion inversion = new FondoLiquidezEmpresarial(monto, plazoDias);
		cuenta.retirar(monto);
		cuenta.agregarMontoInvertido(monto);
		cuenta.registrarActividad(inversion);
		actividadesGlobales.add(inversion);
		Usuario usuario = usuariosPorDni.get(dni);
		usuario.sumarTotalInvertido(monto);
		return inversion.consultarId();
	}

	@Override
	public void precancelarInversion(String dni, String cvu, int idInversion) {
		if (!usuariosPorDni.containsKey(dni)) throw new IllegalArgumentException("Usuario inexistente.");
		Cuenta cuenta = cuentasPorCvu.get(cvu);
		if (cuenta == null) throw new IllegalArgumentException("Cuenta inexistente.");
		if (!cuenta.consultarDniTitular().equals(dni)) throw new IllegalArgumentException("La cuenta no pertenece al usuario indicado.");

		Inversion inversionEncontrada = null;
		for (Actividad a : cuenta.consultarHistorial()) {
			if (a instanceof Inversion) {
				Inversion inv = (Inversion) a;
				if (inv.consultarId() == idInversion) {
					inversionEncontrada = inv;
					break;
				}
			}
		}

		if (inversionEncontrada == null) throw new IllegalArgumentException("Inversión no encontrada.");

		// marcar precancelada (validaciones internas)
		inversionEncontrada.marcarPrecancelada();

		// calcular monto a devolver y actualizar saldos
		double montoDevuelto = inversionEncontrada.calcularResultadoPrecancelado();
		cuenta.depositar(montoDevuelto);
		cuenta.descontarMontoInvertido(inversionEncontrada.consultarMontoInvertido());
		Usuario usuario = usuariosPorDni.get(dni);
		usuario.restarTotalInvertido(inversionEncontrada.consultarMontoInvertido());

		// registrar actividad de precancelación en historial y global
		Actividad act = new Actividad(montoDevuelto, true) {
			@Override
			public String descripcion() {
				return "Precancela inversion id: " + idInversion + " (" + cvu + ")";
			}
		};
		cuenta.registrarActividad(act);
		actividadesGlobales.add(act);

	}

	@Override
	public String consultarCvu(String alias) {
		Cuenta c = cuentasPorAlias.get(alias);
		if (c == null) throw new IllegalArgumentException("Alias no registrado: " + alias);
		return c.consultarCvu();
	}

	private void validarUsuarioYAliasParaCreacion(String dniUsuario, String alias) {
		if (dniUsuario == null || dniUsuario.isEmpty()) {
			throw new IllegalArgumentException("DNI inválido");
		}
		if (alias == null || alias.isEmpty()) {
			throw new IllegalArgumentException("Alias inválido");
		}
		if (!usuariosPorDni.containsKey(dniUsuario)) {
			throw new IllegalArgumentException("Usuario no existe: " + dniUsuario);
		}
		if (cuentasPorAlias.containsKey(alias)) {
			throw new IllegalArgumentException("Alias ya registrado: " + alias);
		}
	}

	private void registrarCuenta(Cuenta cuenta) {
		cuentasPorCvu.put(cuenta.consultarCvu(), cuenta);
		cuentasPorAlias.put(cuenta.consultarAlias(), cuenta);
		
		List<Cuenta> cuentas=cuentasPorUsuario.get(cuenta.consultarDniTitular());
		if(cuentas==null){
			cuentas = new ArrayList<>();
			cuentasPorUsuario.put(cuenta.consultarDniTitular(), cuentas);
		}
		cuentas.add(cuenta);
	}

	@Override
	public List<String> consultarHistorialGlobal() {
		List<String> salida = new ArrayList<>();
		for (Actividad a : actividadesGlobales) {
			salida.add(a.toString());
		}
		return salida;
	}

	@Override
	public List<String> consultarHistorialCuenta(String cvu) {
		Cuenta c = cuentasPorCvu.get(cvu);
		if (c == null) throw new IllegalArgumentException("Cuenta no encontrada: " + cvu);
		List<String> salida = new ArrayList<>();
		for (Actividad a : c.consultarHistorial()) {
			salida.add(a.toString());
		}
		return salida;
	}

	@Override
	public List<String> consultarHistorialUsuario(String dniUsuario) {
		if (!usuariosPorDni.containsKey(dniUsuario)) throw new IllegalArgumentException("Usuario no existe: " + dniUsuario);
		List<Cuenta> cuentas = cuentasPorUsuario.get(dniUsuario);
		List<String> salida = new ArrayList<>();
		if (cuentas != null) {
			for (Cuenta c : cuentas) {
				for (Actividad a : c.consultarHistorial()) {
					salida.add(a.toString());
				}
			}
		}
		return salida;
	}

	@Override
	public double obtenerTotalInvertido(String dniUsuario) {
		Usuario usuario = usuariosPorDni.get(dniUsuario);
		if (usuario == null) {
			throw new IllegalArgumentException("Usuario inexistente.");
		}
		return usuario.obtenerTotalInvertido();
	}

	@Override
	public List<String> cuentasConMayorVolumen(int cantidadTop) {
		if (cantidadTop <= 0) throw new IllegalArgumentException("cantidadTop debe ser positiva.");
		List<Cuenta> lista = new ArrayList<>(cuentasPorCvu.values());
		lista.sort((a, b) -> Integer.compare(b.consultarHistorial().size(), a.consultarHistorial().size()));
		List<String> salida = new ArrayList<>();
		for (int i = 0; i < Math.min(cantidadTop, lista.size()); i++) {
			Cuenta c = lista.get(i);
			salida.add(c.consultarTipo() + ": " + c.consultarAlias() + " (" + c.consultarCvu() + ")");
		}
		return salida;
	}

	private Cuenta validarDatosBasicosInversion(String dni, String cvu, double monto, int plazoDias) {
		if (!usuariosPorDni.containsKey(dni)) {
			throw new IllegalArgumentException("Usuario inexistente.");
		}

		Cuenta cuenta = cuentasPorCvu.get(cvu);
		if (cuenta == null) {
			throw new IllegalArgumentException("Cuenta inexistente.");
		}

		if (!cuenta.consultarDniTitular().equals(dni)) {
			throw new IllegalArgumentException("La cuenta no pertenece al usuario indicado.");
		}

		if (monto <= 0) {
			throw new IllegalArgumentException("Monto inválido.");
		}

		if (plazoDias <= 0) {
			throw new IllegalArgumentException("Plazo inválido.");
		}

		if (cuenta.obtenerSaldoDisponible() < monto) {
			throw new IllegalArgumentException("Saldo insuficiente.");
		}

		return cuenta;
	}

public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(String.format("Usuarios registrados: %d%n", usuariosPorDni.size()));
    sb.append(String.format("Empresas registradas: %d%n", empresasPorCuit.size()));
    sb.append(String.format("Cuentas activas: %d%n", cuentasPorCvu.size()));
    sb.append(String.format("Total de actividades: %d%n", actividadesGlobales.size()));
    return sb.toString();
}
}