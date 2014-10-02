package fr.hardcoding.svn.hooktools.condition.resource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;

import fr.hardcoding.svn.hooktools.HookTools;
import fr.hardcoding.svn.hooktools.condition.AbstractCondition;
import fr.hardcoding.svn.hooktools.condition.resource.filter.AbstractResourceFilter;
import fr.hardcoding.svn.hooktools.hook.AbstractHook;
import fr.hardcoding.svn.hooktools.hook.UnavailableHookDataException;

/**
 * This class is a resource condition which validate if all resource filters validate one change operation.
 * 
 * @author Perfect Slayer (bruce.bujon@gmail.com)
 * 
 */
public class ResourceCondition extends AbstractCondition {
	/** Serialization id. */
	private static final long serialVersionUID = -81100215144128936L;
	/** The resource filter collection. */
	private final List<AbstractResourceFilter> resourceFilters;

	/**
	 * Constructor.
	 */
	public ResourceCondition() {
		// Create resource filter collection
		this.resourceFilters = new ArrayList<>();
	}

	/**
	 * Add a resource filter.
	 * 
	 * @param resourceFilter
	 *            The resource filter to add.
	 */
	public void addResourceFilter(AbstractResourceFilter resourceFilter) {
		this.resourceFilters.add(resourceFilter);
	}

	@Override
	public boolean check(AbstractHook hook) {
		// Get the commit changes
		Collection<ResourceChange> resourceChanges;
		try {
			resourceChanges = hook.getCommitChanges().values();
		} catch (UnavailableHookDataException exception) {
			HookTools.LOGGER.log(Level.WARNING, "An error occured while evaluating resource condition.", exception);
			// Invalidate the operation if data are not available
			return false;
		}
		// Check each resource change
		for (ResourceChange resourceChange : resourceChanges) {
			boolean failed = false;
			// Check each filter on each resource change
			for (AbstractResourceFilter resourceFilter : this.resourceFilters) {
				// Check filter on resource change
				if (!resourceFilter.match(hook, resourceChange)) {
					// Mark check as failed for the resource change
					failed = true;
					// Stop check for the resource change
					break;
				}
			}
			// Check if the resource change match
			if (!failed)
				return true;
		}
		// Return no resource change match
		return false;
	}
	
	@Override
	public String toString() {
		return "Resource condition ("+this.resourceFilters+")";
	}
}