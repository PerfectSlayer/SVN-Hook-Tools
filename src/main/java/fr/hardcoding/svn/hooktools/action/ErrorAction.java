package fr.hardcoding.svn.hooktools.action;

import fr.hardcoding.svn.hooktools.hook.AbstractHook;

/**
 * This class is an error action. Its sends error message and define error code.
 * 
 * @author Perfect Slayer (bruce.bujon@gmail.com)
 * 
 */
public class ErrorAction extends AbstractAction {
	/**
	 * Constructor.
	 */
	public ErrorAction() {
	}

	@Override
	public boolean perform(AbstractHook hook) {
		hook.sendErrorMessage("Error message.");
		hook.setErrorCode(-10);
		return false;
	}
}