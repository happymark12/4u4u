package _4u4u_init.ude;

public class CantCreateAdException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public CantCreateAdException() {
	}

	public CantCreateAdException(String message) {
		super(message);
	}

	public CantCreateAdException(Throwable cause) {
		super(cause);
	}

	public CantCreateAdException(String message, Throwable cause) {
		super(message, cause);
	}

	public CantCreateAdException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
