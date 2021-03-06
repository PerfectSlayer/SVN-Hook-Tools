package fr.hardcoding.svn.hooktools.condition;

import java.util.regex.Pattern;

/**
 * This enum describes string comparison operations.
 * 
 * @author Perfect Slayer (bruce.bujon@gmail.com)
 * 
 */
public enum StringComparison {
	/** The equals operation. */
	IS,
	/** The contains operation. */
	CONTAINS,
	/** The match operation (for regexp). */
	MATCHES,
	/** The opposite of equals operation. */
	NOT_IS,
	/** The opposite of contains operation. */
	NOT_CONTAINS,
	/** The opposite of match operation (for regexp). */
	NOT_MATCHES;

	/**
	 * Compare two string according the comparison operation.
	 * 
	 * @param base
	 *            The string to compare.
	 * @param to
	 *            The string used by the comparison operation.
	 * @return <code>true</code> if the comparison matches, <code>false</code> otherwise.
	 */
	public boolean compare(String base, String to) {
		// Check parameters
		if (base==null)
			throw new IllegalArgumentException("Base parameter could not be null");
		if (to==null)
			throw new IllegalArgumentException("To parameter could not be null");
		// Check comparison operation
		switch (this) {
			case IS:
				return base.equals(to);
			case CONTAINS:
				return base.contains(to);
			case MATCHES:
				return Pattern.matches(to, base);
			case NOT_IS:
				return !base.equals(to);
			case NOT_CONTAINS:
				return !base.contains(to);
			case NOT_MATCHES:
				return !Pattern.matches(to, base);
		}
		// Return comparison not match
		return false;
	}
}