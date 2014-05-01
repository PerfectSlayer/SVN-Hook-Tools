package fr.hardcoding.svn.hooktools.condition.resource;

/**
 * This class represents a resource change.
 * 
 * @author Perfect Slayer (bruce.bujon@gmail.com)
 * 
 */
public class ResourceChange {
	/** The resource path changed. */
	private final String path;
	/** The resource change operation. */
	private final ResourceOperation operation;
	/** The resource properties change status (<code>true</code> if the resource properties have changed, <code>false</code> otherwise). */
	private final boolean propertiesChanged;

	/**
	 * Constructor.
	 * 
	 * @param path
	 *            The resource path changed.
	 * @param operation
	 *            The resource change operation.
	 * @param propertyChanged
	 *            <code>true</code> if the resource properties have changed, <code>false</code> otherwise.
	 */
	public ResourceChange(String path, ResourceOperation operation, boolean propertyChanged) {
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
}