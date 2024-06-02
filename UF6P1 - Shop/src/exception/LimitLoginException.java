// LimitLoginException.java
package exception;

public class LimitLoginException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public LimitLoginException() {
        super("Se han excedido el límite de intentos de inicio de sesión.");
    }
}