package fr.hardcoding.svn.hooktools.condition.operator;

import java.util.ArrayList;
import java.util.List;

import fr.hardcoding.svn.hooktools.condition.AbstractCondition;
import fr.hardcoding.svn.hooktools.hook.AbstractHook;

/**
 * This class is an abstract base class for group condition.
 * 
 * @author Perfect Slayer (bruce.bujon@gmail.com)
 * 
 */
public abstract class AbtractGroupCondition extends AbstractCondition {
	/** The condition collection. */
	protected final List<AbstractCondition> conditions;
	/** The group condition status. */
	protected boolean validated;

	/**
	 * Constructor.
	 */
	public AbtractGroupCondition() {
		// Invalidate the group by default
		this.validated = false;
		// Create condition collection
		this.conditions = new ArrayList<>();
	}

	/**
	 * Add a condition to the group.
	 * 
	 * @param condition
	 *            The condition to add.
	 */
	public void addCondition(AbstractCondition condition) {
		this.conditions.add(condition);
	}

	@Override
	public boolean check(AbstractHook hook) {
		// Check each condition
		for (AbstractCondition condition : this.conditions) {
			// Apply each condition status
			if (this.applyConditionStatus(condition.check(hook)))
				// Break group evaluation
				break;
		}
		// Return group condition status
		return this.validated;
	}

	/**
	 * Apply a condition status.
	 * 
	 * @param conditionStatus
	 *            A condition status.
	 * @return <code>true</code> if the group condition should stop evaluation, <code>false</code> otherwise.
	 */
	protected abstract boolean applyConditionStatus(boolean conditionStatus);
}