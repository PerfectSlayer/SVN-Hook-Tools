package fr.hardcoding.svn.hooktools.condition.commit;

import fr.hardcoding.svn.hooktools.condition.AbstractCondition;
import fr.hardcoding.svn.hooktools.hook.AbstractHook;
import fr.hardcoding.svn.hooktools.hook.UnavailableHookDataException;

/**
 * This class is an abstract base class for commit log condition.
 * 
 * @author Perfect Slayer (bruce.bujon@gmail.com)
 * 
 */
public abstract class AbstractCommitLogCondition extends AbstractCondition {
	/**
	 * Constructor.
	 */
	public AbstractCommitLogCondition() {

	}

	@Override
	public boolean check(AbstractHook hook) {
		try {
			// Get the commit log
			String commitLog = hook.getCommitLog();
			// Check the commit log
			return this.checkCommitLog(commitLog);
		} catch (UnavailableHookDataException exception) {
			// Invalidate the operation if data are not available
			return false;
		}
	}

	/**
	 * Check commit log.
	 * 
	 * @param commitLog
	 *            The commit log to check.
	 * @return <code>true</code> if the commit log is valid, <code>false</code> otherwise.
	 */
	public abstract boolean checkCommitLog(String commitLog);
}