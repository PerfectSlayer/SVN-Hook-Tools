package fr.hardcoding.svn.hooktools.action;

import java.io.Serializable;

import fr.hardcoding.svn.hooktools.hook.AbstractHook;

/**
 * This class is an abstract base class for a rule action.
 * 
 * @author Perfect Slayer (bruce.bujon@gmail.com)
 * 
 */
public abstract class AbstractAction implements Serializable {
	/** Serialization id. */
	private static final long serialVersionUID = 2036945766061285256L;

	/**
	 * Perform the action.
	 * 
	 * @param hook
	 *            The performing hook.
	 * @return <code>true</code> if the action should break the remaining rule actions, <code>false</code> otherwise.
	 */
	public abstract boolean perform(AbstractHook hook);
}