package fr.hardcoding.svn.hooktools.hook;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.logging.Level;

import fr.hardcoding.svn.hooktools.HookTools;
import fr.hardcoding.svn.hooktools.condition.resource.ResourceChange;
import fr.hardcoding.svn.hooktools.condition.resource.ResourceDiff;
import fr.hardcoding.svn.hooktools.condition.resource.ResourceOperation;
import fr.hardcoding.svn.hooktools.condition.resource.ResourceType;

/**
 * This class is the post-lock hook implementation.
 * 
 * @author Perfect Slayer (bruce.bujon@gmail.com)
 * 
 */
public class PostUnlockHook extends AbstractHook {
	/**
	 * Constructor.
	 * 
	 * @param type
	 *            The hook type.
	 * @param parameters
	 *            The hook parameters.
	 */
	public PostUnlockHook(HookType type, String[] parameters) {
		super(type, parameters);
	}

	@Override
	protected void parseParameters(String[] parameters) {
		// Save repository path
		this.repositoryPath = new File(parameters[0]);
		// Save commit author and log
		this.commitAuthor = parameters[1];
		this.commitLog = "";
		// Create commit changes
		this.commitChanges = new HashMap<>();
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
			String path;
			// Read each locked path from standard input
			while ((path = reader.readLine())!=null) {
				// Create resource change for each path
				ResourceChange resourceChange = new ResourceChange(this, path, ResourceType.FILE, ResourceOperation.UNLOCK, false);
				resourceChange.setDiff(new ResourceDiff(path));
				this.commitChanges.put(path, resourceChange);
			}
		} catch (IOException exception) {
			HookTools.LOGGER.log(Level.WARNING, "Unable to read unlocked path.", exception);
		}
		this.commitDiffLoaded = true;
	}
}