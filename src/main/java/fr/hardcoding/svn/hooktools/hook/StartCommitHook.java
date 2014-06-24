package fr.hardcoding.svn.hooktools.hook;

import java.io.File;

/**
 * This class is the start-commit hook implementation.
 * 
 * @author Perfect Slayer (bruce.bujon@gmail.com)
 * 
 */
public class StartCommitHook extends AbstractHook {
	/**
	 * Constructor.
	 * 
	 * @param type
	 *            The hook type.
	 * @param parameters
	 *            The hook parameters.
	 */
	public StartCommitHook(HookType type, String[] parameters) {
		super(type, parameters);
	}

	@Override
	protected void parseParameters(String[] parameters) {
		// Save repository path
		this.repositoryPath = new File(parameters[0]);
		// Save commit author
		this.commitAuthor = parameters[1];
		// Save transaction name if available
		if (parameters.length>3)
			this.transactionName = parameters[3];
	}
}