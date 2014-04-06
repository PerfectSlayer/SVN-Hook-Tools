package fr.hardcoding.svn.hooktools.condition.operator;


/**
 * This class validate a condition group if all conditions validate the operation.
 * 
 * @author Perfect Slayer (bruce.bujon@gmail.com)
 * 
 */
public class AllCondition extends AbtractGroupCondition {
	/**
	 * Constructor.
	 */
	public AllCondition() {
		// Validate the group by default
		this.validated = true;
	}

	@Override
	protected boolean applyConditionStatus(boolean conditionStatus) {
		// Check condition status
		if (!conditionStatus)
			// Update group condition status
			this.validated = false;
		// Stop evaluation if a condition invalidate the operation
		return !conditionStatus;
	}
}