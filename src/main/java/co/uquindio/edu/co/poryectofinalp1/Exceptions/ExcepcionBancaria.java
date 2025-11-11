package co.uquindio.edu.co.poryectofinalp1.Exceptions;

/**
 * MANEJO DE EXCEPCIONES - Excepciones personalizadas para el sistema bancario
 */

/**
 * Excepción base para todas las excepciones bancarias
 */
class ExcepcionBancaria extends Exception {
    public ExcepcionBancaria(String mensaje) {
        super(mensaje);
    }
}

/**
 * Excepción para saldo insuficiente
 */
class SaldoInsuficienteException extends ExcepcionBancaria {
    private double saldoActual;
    private double montoRequerido;

    public SaldoInsuficienteException(double saldoActual, double montoRequerido) {
        super(String.format("Saldo insuficiente. Disponible: $%.2f, Requerido: $%.2f",
                saldoActual, montoRequerido));
        this.saldoActual = saldoActual;
        this.montoRequerido = montoRequerido;
    }

    public double getSaldoActual() {
        return saldoActual;
    }

    public double getMontoRequerido() {
        return montoRequerido;
    }
}

/**
 * Excepción para cuenta inactiva
 */
class CuentaInactivaException extends ExcepcionBancaria {
    private String numeroCuenta;

    public CuentaInactivaException(String numeroCuenta) {
        super("La cuenta " + numeroCuenta + " está inactiva");
        this.numeroCuenta = numeroCuenta;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }
}

/**
 * Excepción para cuenta no encontrada
 */
class CuentaNoEncontradaException extends ExcepcionBancaria {
    private String identificador;

    public CuentaNoEncontradaException(String identificador) {
        super("No se encontró la cuenta con identificador: " + identificador);
        this.identificador = identificador;
    }

    public String getIdentificador() {
        return identificador;
    }
}

/**
 * Excepción para límite de retiro excedido
 */
class LimiteRetiroExcedidoException extends ExcepcionBancaria {
    private double limiteRetiro;
    private double montoSolicitado;

    public LimiteRetiroExcedidoException(double limiteRetiro, double montoSolicitado) {
        super(String.format("Límite de retiro excedido. Límite: $%.2f, Solicitado: $%.2f",
                limiteRetiro, montoSolicitado));
        this.limiteRetiro = limiteRetiro;
        this.montoSolicitado = montoSolicitado;
    }

    public double getLimiteRetiro() {
        return limiteRetiro;
    }

    public double getMontoSolicitado() {
        return montoSolicitado;
    }
}

/**
 * Excepción para monto inválido
 */
class MontoInvalidoException extends ExcepcionBancaria {
    private double monto;

    public MontoInvalidoException(double monto) {
        super("El monto " + monto + " no es válido. Debe ser mayor a 0");
        this.monto = monto;
    }

    public double getMonto() {
        return monto;
    }
}

/**
 * Excepción para autenticación fallida
 */
class AutenticacionFallidaException extends ExcepcionBancaria {
    private String usuario;

    public AutenticacionFallidaException(String usuario) {
        super("Autenticación fallida para el usuario: " + usuario);
        this.usuario = usuario;
    }

    public String getUsuario() {
        return usuario;
    }
}

/**
 * Excepción para permisos insuficientes
 */
class PermisosInsuficientesException extends ExcepcionBancaria {
    private String accion;
    private String rol;

    public PermisosInsuficientesException(String accion, String rol) {
        super(String.format("El rol '%s' no tiene permisos para: %s", rol, accion));
        this.accion = accion;
        this.rol = rol;
    }

    public String getAccion() {
        return accion;
    }

    public String getRol() {
        return rol;
    }
}

/**
 * Excepción para datos duplicados
 */
class DatosDuplicadosException extends ExcepcionBancaria {
    private String campo;
    private String valor;

    public DatosDuplicadosException(String campo, String valor) {
        super(String.format("Ya existe un registro con %s: %s", campo, valor));
        this.campo = campo;
        this.valor = valor;
    }

    public String getCampo() {
        return campo;
    }

    public String getValor() {
        return valor;
    }
}