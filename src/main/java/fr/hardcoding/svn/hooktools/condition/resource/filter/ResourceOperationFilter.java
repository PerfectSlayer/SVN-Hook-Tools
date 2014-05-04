package fr.hardcoding.svn.hooktools.condition.resource.filter;

import fr.hardcoding.svn.hooktools.condition.resource.ResourceChange;
import fr.hardcoding.svn.hooktools.condition.resource.ResourceOperation;
import fr.hardcoding.svn.hooktools.configuration.ConfigurationParameter;
import fr.hardcoding.svn.hooktools.hook.AbstractHook;

/**
 * This class is a resource filter which check a resource operation.
 * 
 * @author Perfect Slayer (bruce.bujon@gmail.com)
 * 
 */
public class ResourceOperationFilter extends AbstractResourceFilter {
	/** Serialization id. */
	private static final long serialVersionUID = -3013854579148601946L;
	/** The operation to check. */
	@ConfigurationParameter(isRequired = true)
	public ResourceOperation operation;

	@Override
	public boolean match(AbstractHook hook, ResourceChange resourceChange) {
		return this.operation==resourceChange.getOperation();
	}
}