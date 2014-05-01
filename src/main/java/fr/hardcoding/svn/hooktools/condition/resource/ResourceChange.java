package fr.hardcoding.svn.hooktools.condition.resource;

import fr.hardcoding.svn.hooktools.hook.AbstractHook;
import fr.hardcoding.svn.hooktools.hook.UnavailableHookDataException;

/**
 * This class represents a resource change.
 * 
 * @author Perfect Slayer (bruce.bujon@gmail.com)
 * 
 */
public class ResourceChange {
	/** The related hook. */
	private final AbstractHook hook;
	/** The resource path changed. */
	private final String path;
	/** The resource change operation. */
	private final ResourceOperation operation;
	/** The resource properties change status (<code>true</code> if the resource properties have changed, <code>false</code> otherwise). */
	private final boolean propertiesChanged;
	/** The resource diff (<code>null</code> until loaded, lazy loaded). */
	private ResourceDiff diff;

	/**
	 * Constructor.
	 * 
	 * @param hook
	 *            The related hook.
	 * @param path
	 *            The resource path changed.
	 * @param operation
	 *            The resource change operation.
	 * @param propertyChanged
	 *            <code>true</code> if the resource properties have changed, <code>false</code> otherwise.
	 */
	public ResourceChange(AbstractHook hook, String path, ResourceOperation operation, boolean propertyChanged) {
		this.hook = hook;
		this.path = path;
		this.operation = operation;
		this.propertiesChanged = propertyChanged;
	}

	/**
	 * Get the resource path changed.
	 * 
	 * @return The resource path changed.
	 */
	public String getPath() {
		return this.path;
	}

	/**
	 * Get the resource change operation.
	 * 
	 * @return The resource change operation.
	 */
	public ResourceOperation getOperation() {
		return this.operation;
	}

	/**
	 * Check if the resource properties have changed.
	 * 
	 * @return <code>true</code> if the resource properties have changed, <code>false</code> otherwise.
	 */
	public boolean isPropertiesChanged() {
		return this.propertiesChanged;
	}

	/**
	 * Get the resource diff.<br>
	 * The resource diff will be loaded at the first access.
	 * 
	 * @return The resource diff.
	 * @throws UnavailableHookDataException
	 *             Throws exception if diff could not be retrieved.
	 */
	public ResourceDiff getDiff() throws UnavailableHookDataException {
		// Check if resource diff is defined
		if (this.diff==null) {
			// Load commit diffs
			this.hook.loadCommitDiffs();
		}
		// Return resource diff
		return this.diff;
	}

	/**
	 * Set the resource diff.
	 * 
	 * @param diff
	 *            The resource diff to set.
	 */
	public void setDiff(ResourceDiff diff) {
		this.diff = diff;
	}
}