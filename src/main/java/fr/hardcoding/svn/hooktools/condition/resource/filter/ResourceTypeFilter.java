package fr.hardcoding.svn.hooktools.condition.resource.filter;

import fr.hardcoding.svn.hooktools.condition.resource.ResourceChange;
import fr.hardcoding.svn.hooktools.condition.resource.ResourceType;
import fr.hardcoding.svn.hooktools.configuration.ConfigurationParameter;
import fr.hardcoding.svn.hooktools.hook.AbstractHook;

/**
 * This class is a resource filter which check the type of resource.
 * 
 * @author Perfect Slayer (bruce.bujon@gmail.com)
 * 
 */
public class ResourceTypeFilter extends AbstractResourceFilter {
	/** Serialization id. */
	private static final long serialVersionUID = -8659707170631114776L;
	/** The resource type to check. */
	@ConfigurationParameter(isRequired = true)
	public ResourceType type;

	@Override
	public boolean match(AbstractHook hook, ResourceChange resourceChange) {
		// Check the if the resource type match resource type
		return resourceChange.getType()==this.type;
	}
}