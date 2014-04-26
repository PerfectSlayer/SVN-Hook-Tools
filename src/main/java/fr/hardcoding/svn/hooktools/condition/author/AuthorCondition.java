package fr.hardcoding.svn.hooktools.condition.author;

import fr.hardcoding.svn.hooktools.condition.AbstractCondition;
import fr.hardcoding.svn.hooktools.condition.StringComparison;
import fr.hardcoding.svn.hooktools.configuration.ConfigurationParameter;
import fr.hardcoding.svn.hooktools.hook.AbstractHook;
import fr.hardcoding.svn.hooktools.hook.UnavailableHookDataException;

/**
 * This class is a condition about the commit author name.
 * 
 * @author Perfect Slayer (bruce.bujon@gmail.com)
 * 
 */
public class AuthorCondition extends AbstractCondition {
	/** The requested commit author name. */
	@ConfigurationParameter(isRequired = true)
	public String name;
	/** The author name comparison. */
	@ConfigurationParameter
	public StringComparison nameComparison;

	/**
	 * Constructor.
	 */
	public AuthorCondition() {
		// Set default name comparison
		this.nameComparison = StringComparison.IS;
	}

	@Override
	public boolean check(AbstractHook hook) {
		try {
			// Check commit author with requested commit author
			return this.nameComparison.compare(hook.getCommitAuthor(), this.name);
		} catch (UnavailableHookDataException exception) {
			// Invalidate the operation if data are not available
			return false;
		}
	}
}