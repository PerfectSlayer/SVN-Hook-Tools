package fr.hardcoding.svn.hooktools.condition.resource;

import java.util.ArrayList;
import java.util.List;

import fr.hardcoding.svn.hooktools.condition.AbstractCondition;
import fr.hardcoding.svn.hooktools.condition.resource.filter.AbstractResourceFilter;
import fr.hardcoding.svn.hooktools.hook.AbstractHook;
import fr.hardcoding.svn.hooktools.hook.UnavailableHookDataException;

/**
 * This class is a resource condition which validate if all resource filters valide one change operation.
 * 
 * @author Perfect Slayer (bruce.bujon@gmail.com)
 * 
 */
public class ResourceCondition extends AbstractCondition {
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
		List<ResourceChange> changes = null;
		try {
			changes = hook.getCommitChanges();
		} catch (UnavailableHookDataException exception) {
			// Invalidate the operation if data are not available
			return false;
		}
		for (ResourceChange change : changes) {
			boolean failed = false;
			for (AbstractResourceFilter resourceFilter : this.resourceFilters) {
				if (!resourceFilter.match(hook, change.getOperation(), change.getPath()))
					failed = true;

			}
			if (!failed)
				return true;
		}
		return false;
	}
}
