package fr.hardcoding.svn.hooktools.condition.commit;

import fr.hardcoding.svn.hooktools.configuration.ConfigurationParameter;

/**
 * This class is a condition evaluated as true if commit log is too small.
 * 
 * @author Perfect Slayer (bruce.bujon@gmail.com)
 * 
 */
public class MinLengthCommitLogCondition extends AbstractCommitLogCondition {
	/** Serialization id. */
	private static final long serialVersionUID = -3577892608055109156L;
	/** The commit log min length (in characters). */
	@ConfigurationParameter(isRequired = true)
	public int length;

	@Override
	public boolean checkCommitLog(String commitLog) {
		return commitLog.trim().length()<this.length;
	}
}