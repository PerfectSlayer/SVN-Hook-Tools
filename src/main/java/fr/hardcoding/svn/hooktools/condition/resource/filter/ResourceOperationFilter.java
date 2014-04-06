package fr.hardcoding.svn.hooktools.condition.resource.filter;

import fr.hardcoding.svn.hooktools.condition.resource.ResourceOperation;
import fr.hardcoding.svn.hooktools.hook.AbstractHook;

/**
 * This class is a resource filter which check a resource operation.
 * 
 * @author Perfect Slayer (bruce.bujon@gmail.com)
 * 
 */
public class ResourceOperationFilter extends AbstractResourceFilter {
	/** The operation to check. */
	private final ResourceOperation operation;

	/**
	 * Constructor.
	 * 
	 * @param operation
	 *            The operation to check.
	 */
	public ResourceOperationFilter(ResourceOperation operation) {
		this.operation = operation;
	}

	@Override
	public boolean match(AbstractHook hook, ResourceOperation operation, String path) {
		return this.operation==operation;
	}
}