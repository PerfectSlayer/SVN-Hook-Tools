package fr.hardcoding.svn.hooktools.condition;

import java.io.Serializable;

import fr.hardcoding.svn.hooktools.hook.AbstractHook;

/**
 * This class is an abstract base class for a rule checker implementation (used as base for filter and validator).
 * 
 * @author Perfect Slayer (bruce.bujon@gmail.com)
 * 
 */
public abstract class AbstractCondition implements Serializable {
	/** Serialization id. */
	private static final long serialVersionUID = 1773206780313904465L;

	/**
	 * Check if the checker validate the operation.
	 * 
	 * @param hook
	 *            The checking hook.
	 * @return <code>true</code> if the checker validate the operation, <code>false</code> otherwise.
	 */
	public abstract boolean check(AbstractHook hook);
}