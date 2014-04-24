package fr.hardcoding.svn.hooktools.condition.resource.filter;

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
	/** The operation to check. */
	@ConfigurationParameter(isRequired = true)
	public ResourceOperation operation;

	/**
	 * Constructor.
	 */
	public ResourceOperationFilter() {

	}

	@Override
	public boolean match(AbstractHook hook, ResourceOperation operation, String path) {
		return this.operation==operation;
	}
}