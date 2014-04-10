package fr.hardcoding.svn.hooktools.condition.resource;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * This class represents a resource diff.
 * 
 * @author Perfect Slayer (bruce.bujon@gmail.com)
 * 
 */
public class ResourceDiff {
	/** The resource path. */
	private final String path;
	/** The resource properties additions. */
	private final Map<String, String> propertyAdditions;

	/**
	 * Constructor.
	 * 
	 * @param path
	 *            The resource path.
	 */
	public ResourceDiff(String path) {
		this.path = path;
		this.propertyAdditions = new HashMap<>();
	}

	/**
	 * Get the resource path.
	 * 
	 * @return The resource path.
	 */
	public String getPath() {
		return this.path;
	}

	/**
	 * Add a property additions to the resource diff.
	 * 
	 * @param propertyName
	 *            The changed property name.
	 * @param propertyAdditions
	 *            The property additions.
	 */
	public void addPropertyChange(String propertyName, String propertyAdditions) {
		this.propertyAdditions.put(propertyName, propertyAdditions);
	}

	/**
	 * Get the resource properties changes.
	 * 
	 * @return The resources properties changes.
	 */
	public Map<String, String> getProperties() {
		return Collections.unmodifiableMap(this.propertyAdditions);
	}
}
