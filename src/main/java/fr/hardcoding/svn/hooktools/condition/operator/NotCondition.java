package fr.hardcoding.svn.hooktools.condition.operator;

import fr.hardcoding.svn.hooktools.condition.AbstractCondition;

/**
 * This class validate a condition group if the condition invalidate the operation.
 * 
 * @author Perfect Slayer (bruce.bujon@gmail.com)
 * 
 */
public class NotCondition extends AbtractGroupCondition {
	/** Serialization id. */
	private static final long serialVersionUID = 1169687315224072951L;

	@Override
	public void addCondition(AbstractCondition condition) {
		// Check only one condition is stored
		if (this.conditions.size()>=1)
			return;
		// Delegate condition addition
		super.addCondition(condition);
	}

	@Override
	protected boolean applyConditionStatus(boolean conditionStatus) {
		// Negate the status
		this.validated = !conditionStatus;
		// Do not stop the evaluation
		return false;
	}
}