package fr.hardcoding.svn.hooktools.action;

import fr.hardcoding.svn.hooktools.configuration.ConfigurationParameter;
import fr.hardcoding.svn.hooktools.hook.AbstractHook;

/**
 * This class is an error action. Its sends error message and define error code.
 * 
 * @author Perfect Slayer (bruce.bujon@gmail.com)
 * 
 */
public class ErrorAction extends AbstractAction {
	/*
	 * Action parameters.
	 */
	/** The error code. */
	@ConfigurationParameter(isRequired = true)
	public int errorCode;
	/** The error message. */
	@ConfigurationParameter()
	public String errorMessage;

	/**
	 * Constructor.
	 */
	public ErrorAction() {
	}

	@Override
	public boolean perform(AbstractHook hook) {
		// Check error message
		if (this.errorMessage!=null)
			hook.sendErrorMessage(this.errorMessage);
		// Set error code
		hook.setErrorCode(this.errorCode);
		// Continue the remaining rule actions
		return false;
	}
}