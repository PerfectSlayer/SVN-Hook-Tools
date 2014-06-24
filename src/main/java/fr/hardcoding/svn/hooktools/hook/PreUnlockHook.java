package fr.hardcoding.svn.hooktools.hook;

import java.io.File;
import java.util.HashMap;

import fr.hardcoding.svn.hooktools.condition.resource.ResourceChange;
import fr.hardcoding.svn.hooktools.condition.resource.ResourceDiff;
import fr.hardcoding.svn.hooktools.condition.resource.ResourceOperation;
import fr.hardcoding.svn.hooktools.condition.resource.ResourceType;

/**
 * This class is the pre-unlock hook implementation.
 * 
 * @author Perfect Slayer (bruce.bujon@gmail.com)
 * 
 */
public class PreUnlockHook extends AbstractHook {
	/**
	 * Constructor.
	 * 
	 * @param type
	 *            The hook type.
	 * @param parameters
	 *            The hook parameters.
	 */
	public PreUnlockHook(HookType type, String[] parameters) {
		super(type, parameters);
	}

	@Override
	protected void parseParameters(String[] parameters) {
		// Save repository path
		this.repositoryPath = new File(parameters[0]);
		// Save commit author and log
		this.commitAuthor = parameters[2];
		this.commitLog = "";
		// Create resource change
		String path = parameters[1];
		ResourceChange resourceChange = new ResourceChange(this, path, ResourceType.FILE, ResourceOperation.UNLOCK, false);
		resourceChange.setDiff(new ResourceDiff(path));
		// Save resource change
		this.commitChanges = new HashMap<>();
		this.commitChanges.put(path, resourceChange);
		this.commitDiffLoaded = true;
	}
}