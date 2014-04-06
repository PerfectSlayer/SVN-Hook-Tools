package fr.hardcoding.svn.hooktools.condition.resource;


/**
 * This class represents a resource change.
 * 
 * @author Perfect Slayer (bruce.bujon@gmail.com)
 * 
 */
public class ResourceChange {
	/** The resource change operation. */
	private final ResourceOperation operation;
	/** The resource path changed. */
	private final String path;

	/**
	 * Constructor.
	 * 
	 * @param operation
	 *            The resource change operation.
	 * @param path
	 *            The resource path changed.
	 */
	public ResourceChange(ResourceOperation operation, String path) {
		this.operation = operation;
		this.path = path;
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
	 * Get the resource path changed.
	 * 
	 * @return The resource path changed.
	 */
	public String getPath() {
		return this.path;
	}
}