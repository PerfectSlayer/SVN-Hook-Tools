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
	/** The resource properties changes. */
	private final Map<String, PropertyChange> propertyChanges;

	/**
	 * Constructor.
	 * 
	 * @param path
	 *            The resource path.
	 */
	public ResourceDiff(String path) {
		this.path = path;
		this.propertyChanges = new HashMap<>();
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
	 * Add a property change to the resource diff.
	 * 
	 * @param propertyName
	 *            The changed property name.
	 * @param oldValue
	 *            The old property value (may be <code>null</code> if added).
	 * @param newValue
	 *            The new property value (may be <code>null</code> if deleted).
	 */
	public void addPropertyChange(String propertyName, String oldValue, String newValue) {
		this.propertyChanges.put(propertyName, new PropertyChange(oldValue, newValue));
	}

	/**
	 * Get the resource properties changes.
	 * 
	 * @return The resources properties changes.
	 */
	public Map<String, PropertyChange> getProperties() {
		return Collections.unmodifiableMap(this.propertyChanges);
	}

	/**
	 * This class represents a property value change.
	 * 
	 * @author Perfect Slayer (bruce.bujon@gmail.com)
	 * 
	 */
	public static class PropertyChange {
		/** The old property value. */
		private final String oldValue;
		/** The new property value. */
		private final String newValue;

		/**
		 * Constructor.
		 * 
		 * @param oldValue
		 *            The old property value.
		 * @param newValue
		 *            The new property value.
		 */
		public PropertyChange(String oldValue, String newValue) {
			this.oldValue = oldValue==null ? "" : oldValue;
			this.newValue = newValue==null ? "" : newValue;
		}

		/**
		 * Get the old property value.
		 * 
		 * @return The old property value.
		 */
		public String getOldValue() {
			return this.oldValue;
		}

		/**
		 * Get the new property value.
		 * 
		 * @return The new property value.
		 */
		public String getNewValue() {
			return this.newValue;
		}

		@Override
		public String toString() {
			return this.oldValue+">"+this.newValue;
		}
	}
}