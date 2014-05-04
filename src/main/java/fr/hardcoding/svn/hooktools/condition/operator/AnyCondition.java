package fr.hardcoding.svn.hooktools.condition.operator;

/**
 * This class validate a condition group if at least a condition validate the operation.
 * 
 * @author Perfect Slayer (bruce.bujon@gmail.com)
 * 
 */
public class AnyCondition extends AbtractGroupCondition {
	/** Serialization id. */
	private static final long serialVersionUID = -2454191928621348634L;

	@Override
	protected boolean applyConditionStatus(boolean conditionStatus) {
		// Check condition status
		if (conditionStatus)
			// Update group condition status
			this.validated = true;
		// Stop evaluation if a condition invalidate the operation
		return conditionStatus;
	}
}