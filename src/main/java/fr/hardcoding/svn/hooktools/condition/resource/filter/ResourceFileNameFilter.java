package fr.hardcoding.svn.hooktools.condition.resource.filter;

import java.util.Objects;

import fr.hardcoding.svn.hooktools.condition.resource.ResourceChange;
import fr.hardcoding.svn.hooktools.hook.AbstractHook;

/**
 * This class is a resource filter which check a resource file name.
 * 
 * @author Perfect Slayer
 * 
 */
public class ResourceFileNameFilter extends AbstractResourceFilter {
	/** Serialization id. */
	private static final long serialVersionUID = -1383516591494401816L;
	/*
	 * Condition parameter.
	 */
	/** The resource file name to check. */
	public String fileName;

	@Override
	public boolean match(AbstractHook hook, ResourceChange resourceChange) {
		// Check file name
		return this.getFileName(resourceChange.getPath()).equals(this.fileName);
	}
	
	@Override
	public String toString() {
		return "File name filter (name: "+Objects.toString(this.fileName)+")";
	}
}