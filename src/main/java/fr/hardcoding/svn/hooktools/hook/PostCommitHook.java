package fr.hardcoding.svn.hooktools.hook;

import java.io.File;

import org.tmatesoft.svn.core.wc.SVNRevision;

/**
 * This class is the post-commit hook implementation.
 * 
 * @author Perfect Slayer (bruce.bujon@gmail.com)
 * 
 */
public class PostCommitHook extends AbstractHook {
	/**
	 * Constructor.
	 * 
	 * @param type
	 *            The hook type.
	 * @param parameters
	 *            The hook parameters.
	 */
	public PostCommitHook(HookType type, String[] parameters) {
		super(type, parameters);
	}

	@Override
	protected void parseParameters(String[] parameters) {
		// Save repository path
		this.repositoryPath = new File(parameters[0]);
		// Save revision number
		this.revisionNumber = SVNRevision.create(Integer.parseInt(parameters[1]));
	}
}