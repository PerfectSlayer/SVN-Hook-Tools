package fr.hardcoding.svn.hooktools.condition.resource.filter;

import fr.hardcoding.svn.hooktools.condition.resource.ResourceChange;
import fr.hardcoding.svn.hooktools.hook.AbstractHook;

/**
 * This class is a resource filter which check a resource file name.
 * 
 * @author Perfect Slayer
 * 
 */
public class ResourceFileNameFilter extends AbstractResourceFilter {
	/*
	 * Condition parameter.
	 */
	/** The resource file name to check. */
	public String fileName;

	/**
	 * Constructor.
	 */
	public ResourceFileNameFilter() {
	}

	@Override
	public boolean match(AbstractHook hook, ResourceChange resourceChange) {
		// Check file name
		return this.getFileName(resourceChange.getPath()).equals(this.fileName);
	}
}