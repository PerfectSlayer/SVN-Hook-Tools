package fr.hardcoding.svn.hooktools.condition.author;

import java.util.Objects;
import java.util.logging.Level;

import fr.hardcoding.svn.hooktools.HookTools;
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
	/** Serialization id. */
	private static final long serialVersionUID = 1345635563075721795L;
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
			HookTools.LOGGER.log(Level.WARNING, "An error occured while evaluating author condition.", exception);
			// Invalidate the operation if data are not available
			return false;
		}
	}

	@Override
	public String toString() {
		return "Author condition (name: "+Objects.toString(this.name)+")";
	}
}