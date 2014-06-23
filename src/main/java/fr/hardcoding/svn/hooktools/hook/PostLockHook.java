package fr.hardcoding.svn.hooktools.hook;

import java.io.File;

/**
 * This class is the post-lock hook implementation.
 * 
 * @author Perfect Slayer (bruce.bujon@gmail.com)
 * 
 */
public class PostLockHook extends AbstractHook {
	/**
	 * Constructor.
	 * 
	 * @param type
	 *            The hook type.
	 * @param parameters
	 *            The hook parameters.
	 */
	public PostLockHook(HookType type, String[] parameters) {
		super(type, parameters);
	}

	@Override
	protected void parseParameters(String[] parameters) {
		this.repositoryPath = new File(parameters[0]);
		this.commitAuthor = parameters[1];
	}
}