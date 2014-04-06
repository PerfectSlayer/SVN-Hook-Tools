package fr.hardcoding.svn.hooktools.hook;

/**
 * This class is a specialized exception for hook missing data.
 * 
 * @author Perfect Slayer (bruce.bujon@gmail.com)
 * 
 */
public class UnavailableHookDataException extends Exception {
	/** Serialization id. */
	private static final long serialVersionUID = -4256966515942222081L;

	/**
	 * Constructor.
	 * 
	 * @param missingData
	 *            The missing data name.
	 */
	public UnavailableHookDataException(String missingData) {
		super("Missing data: "+missingData);
	}

	/**
	 * Constructor.
	 * 
	 * @param missingData
	 *            The missing data name.
	 * @param throwable
	 *            The cause throwable.
	 */
	public UnavailableHookDataException(String missingData, Throwable throwable) {
		super("Missing data: "+missingData, throwable);
	}
}