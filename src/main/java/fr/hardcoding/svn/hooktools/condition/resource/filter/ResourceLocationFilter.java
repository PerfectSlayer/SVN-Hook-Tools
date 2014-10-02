package fr.hardcoding.svn.hooktools.condition.resource.filter;

import java.util.Objects;

import fr.hardcoding.svn.hooktools.condition.StringComparison;
import fr.hardcoding.svn.hooktools.condition.resource.ResourceChange;
import fr.hardcoding.svn.hooktools.condition.resource.ResourceLocation;
import fr.hardcoding.svn.hooktools.condition.resource.ResourceLocationType;
import fr.hardcoding.svn.hooktools.configuration.ConfigurationParameter;
import fr.hardcoding.svn.hooktools.hook.AbstractHook;

/**
 * This class is a resource filter which check a resource location (location type and / or location path).
 * 
 * @author Perfect Slayer
 * 
 */
public class ResourceLocationFilter extends AbstractResourceFilter {
	/** Serialization id. */
	private static final long serialVersionUID = -4962673813714299042L;
	/*
	 * Condition parameters.
	 */
	/** The location type. */
	@ConfigurationParameter
	public ResourceLocationType type;
	/** The location project name to check (<code>null</code> if no project name to check). */
	@ConfigurationParameter
	public String projectName;
	/** The location project name comparison. */
	@ConfigurationParameter
	public StringComparison projectNameComparison;
	/** The location branch name to check (<code>null</code> if no branch name to check). */
	@ConfigurationParameter
	public String branchName;
	/** The location branch name comparison. */
	@ConfigurationParameter
	public StringComparison branchNameComparison;
	/** The location tag name to check (<code>null</code> if no tag name to check). */
	@ConfigurationParameter
	public String tagName;
	/** The location tag name comparison. */
	@ConfigurationParameter
	public StringComparison tagNameComparison;
	/** The location path to check (<code>null</code> if no path to check). */
	@ConfigurationParameter
	public String path;
	/** The location path comparison. */
	@ConfigurationParameter
	public StringComparison pathComparison;

	/**
	 * Constructor.
	 */
	public ResourceLocationFilter() {
		// Set default parameter values
		this.projectNameComparison = StringComparison.IS;
		this.branchNameComparison = StringComparison.IS;
		this.tagNameComparison = StringComparison.IS;
		this.pathComparison = StringComparison.CONTAINS;
	}

	@Override
	public boolean match(AbstractHook hook, ResourceChange resourceChange) {
		// Get resource location
		String path = resourceChange.getPath();
		ResourceLocation location = ResourceLocation.getFromPath(path);
		// Check location type
		if (this.type!=null&&location.getLocationType()!=this.type)
			return false;
		// Check project name
		String projectName = location.getProjectName();
		if (this.projectName!=null&&projectName!=null&&!this.projectNameComparison.compare(projectName, this.projectName))
			return false;
		// Check branch name
		String branchName = location.getBranchName();
		if (this.branchName!=null&&branchName!=null&&!this.branchNameComparison.compare(branchName, this.branchName))
			return false;
		// Check tag name
		String tagName = location.getTagName();
		if (this.tagName!=null&&tagName!=null&&!this.tagNameComparison.compare(tagName, this.tagName))
			return false;
		// Check path
		if (this.path!=null&&!this.pathComparison.compare(path, this.path))
			return false;
		// Return filter match
		return true;
	}
	
	@Override
	public String toString() {
		return "Resource location filter (type: "+Objects.toString(this.type)+", project: "+Objects.toString(this.projectName)+", branch: "+Objects.toString(this.branchName)+", tag: "+Objects.toString(tagName)+", path: "+Objects.toString(this.path)+")";
	}
}