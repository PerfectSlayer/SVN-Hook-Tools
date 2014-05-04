package fr.hardcoding.svn.hooktools.condition.commit;

/**
 * This class is a condition evaluated as true if commit log is empty.
 * 
 * @author Perfect Slayer (bruce.bujon@gmail.com)
 * 
 */
public class EmptyCommitLogCondition extends AbstractCommitLogCondition {
	/** Serialization id. */
	private static final long serialVersionUID = 907022117269104061L;

	@Override
	public boolean checkCommitLog(String commitLog) {
		return commitLog.trim().isEmpty();
	}
}