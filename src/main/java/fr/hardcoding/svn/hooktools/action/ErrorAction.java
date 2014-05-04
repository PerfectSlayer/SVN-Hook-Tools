package fr.hardcoding.svn.hooktools.action;

import fr.hardcoding.svn.hooktools.configuration.ConfigurationParameter;
import fr.hardcoding.svn.hooktools.hook.AbstractHook;

/**
 * This class is an error action. It sends error message and define error code.
 * 
 * @author Perfect Slayer (bruce.bujon@gmail.com)
 * 
 */
public class ErrorAction extends AbstractAction {
	/** Serialization id. */
	private static final long serialVersionUID = 7685944852705286928L;
	/*
	 * Action parameters.
	 */
	/** The error code. */
	@ConfigurationParameter(isRequired = true)
	public int code;
	/** The error message. */
	@ConfigurationParameter()
	public String message;

	@Override
	public boolean perform(AbstractHook hook) {
		// Check error message
		if (this.message!=null)
			hook.sendErrorMessage(this.message);
		// Set error code
		hook.setErrorCode(this.code);
		// Continue the remaining rule actions
		return false;
	}
}