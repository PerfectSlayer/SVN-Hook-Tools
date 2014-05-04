package fr.hardcoding.svn.hooktools.condition.resource.filter;

import java.util.Map;

import fr.hardcoding.svn.hooktools.condition.StringComparison;
import fr.hardcoding.svn.hooktools.condition.resource.ResourceChange;
import fr.hardcoding.svn.hooktools.condition.resource.ResourceDiff.PropertyChange;
import fr.hardcoding.svn.hooktools.configuration.ConfigurationParameter;
import fr.hardcoding.svn.hooktools.hook.AbstractHook;
import fr.hardcoding.svn.hooktools.hook.UnavailableHookDataException;

/**
 * This class is a resource filter which check a property change operation.
 * 
 * @author Perfect Slayer (bruce.bujon@gmail.com)
 * 
 */
public class ResourcePropertyChangeFilter extends AbstractResourceFilter {
	/** Serialization id. */
	private static final long serialVersionUID = -5713888368999827141L;
	/** The property name to check. */
	@ConfigurationParameter(isRequired = true)
	public String name;
	/** The property old value to check. */
	@ConfigurationParameter
	public String oldValue;
	/** The comparison to use for property old value check. */
	@ConfigurationParameter
	public StringComparison oldValueComparison;
	/** The property new value to check. */
	@ConfigurationParameter
	public String newValue;
	/** The comparison to use for property new value check. */
	@ConfigurationParameter
	public StringComparison newValueComparison;

	/**
	 * Constructor.
	 */
	public ResourcePropertyChangeFilter() {
		this.oldValueComparison = StringComparison.IS;
		this.newValueComparison = StringComparison.IS;
	}

	@Override
	public boolean match(AbstractHook hook, ResourceChange resourceChange) {
		try {
			// Check each changed property
			for (Map.Entry<String, PropertyChange> property : resourceChange.getDiff().getProperties().entrySet()) {
				// Check property name
				if (!property.getKey().equals(this.name))
					continue;
				// Check property value changes
				PropertyChange propertyChange = property.getValue();
				boolean oldValue = this.oldValue==null||this.oldValueComparison.compare(propertyChange.getOldValue(), this.oldValue);
				boolean newValue = this.newValue==null||this.newValueComparison.compare(propertyChange.getNewValue(), this.newValue);
				// Return property change match if both values match
				if (oldValue&&newValue)
					return true;
			}
			// Return no property change match found
			return false;
		} catch (UnavailableHookDataException exception) {
			// Invalidate the operation if data are not available
			return false;
		}
	}
}