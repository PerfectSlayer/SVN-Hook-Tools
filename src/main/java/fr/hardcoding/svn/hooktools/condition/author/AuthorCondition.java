package fr.hardcoding.svn.hooktools.condition.author;

import fr.hardcoding.svn.hooktools.condition.AbstractCondition;
import fr.hardcoding.svn.hooktools.hook.AbstractHook;
import fr.hardcoding.svn.hooktools.hook.UnavailableHookDataException;

/**
 * This class is a condition about the commit author.
 * 
 * @author Perfect Slayer (bruce.bujon@gmail.com)
 * 
 */
public class AuthorCondition extends AbstractCondition {
	/** The requested commit author. */
	private final String author;

	/**
	 * Constructor.
	 * 
	 * @param author
	 *            The requested commit author.
	 */
	public AuthorCondition(String author) {
		// Store requested commit author
		this.author = author;
	}

	@Override
	public boolean check(AbstractHook hook) {
		try {
			// Check commit author with requested commit author
			return hook.getCommitAuthor().equals(this.author);
		} catch (UnavailableHookDataException exception) {
			// Invalidate the operation if data are not available
			return false;
		}
	}
}