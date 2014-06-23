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
		this.repositoryPath = new File(parameters[0]);
		this.commitAuthor = parameters[1];
		if (parameters.length>3)
			this.transactionName = parameters[3];
	}
}