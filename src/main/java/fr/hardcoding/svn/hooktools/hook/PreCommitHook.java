package fr.hardcoding.svn.hooktools.hook;

import java.io.File;

/**
 * This class is the pre-commit hook implementation.
 * 
 * @author Perfect Slayer (bruce.bujon@gmail.com)
 * 
 */
public class PreCommitHook extends AbstractHook {
	/**
	 * Constructor.
	 * 
	 * @param type
	 *            The hook type.
	 * @param parameters
	 *            The hook parameters.
	 */
	public PreCommitHook(HookType type, String[] parameters) {
		super(type, parameters);
	}

	@Override
	protected void parseParameters(String[] parameters) {
		this.repositoryPath = new File(parameters[0]);
		this.transactionName = parameters[1];
	}
}