package fr.hardcoding.svn.hooktools.configuration;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import fr.hardcoding.svn.hooktools.action.AbstractAction;
import fr.hardcoding.svn.hooktools.condition.AbstractCondition;
import fr.hardcoding.svn.hooktools.hook.AbstractHook;

/**
 * This class represents a hook rule.
 * 
 * @author Perfect Slayer (bruce.bujon@gmail.com)
 * 
 */
public class Rule implements Serializable {
	/** Serialization id. */
	private static final long serialVersionUID = 7723529480316367053L;
	/** The rule name. */
	protected final String name;
	/** The rule condition. */
	protected AbstractCondition condition;
	/** The rule actions (at least one action). */
	protected final List<AbstractAction> actions;

	/**
	 * Constructor.
	 * 
	 * @param name
	 *            The rule name.
	 */
	public Rule(String name) {
		this.name = name;
		this.actions = new LinkedList<AbstractAction>();
	}

	/**
	 * Get the rule name.
	 * 
	 * @return The rule name.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Get the rule condition.
	 * 
	 * @return The rule condition (<code>null</code> if no condition).
	 */
	public AbstractCondition getCondition() {
		return this.condition;
	}

	/**
	 * Set the rule condition.
	 * 
	 * @param condition
	 *            The rule condition to set (<code>null</code> if rule always apply).
	 */
	public void setCondition(AbstractCondition condition) {
		this.condition = condition;
	}

	/**
	 * Add an action to the rule.
	 * 
	 * @param action
	 *            The action to add.
	 */
	public void addAction(AbstractAction action) {
		this.actions.add(action);
	}

	/**
	 * Get the action rules.
	 * 
	 * @return The actions rules (at least one).
	 */
	public List<AbstractAction> getActions() {
		return Collections.unmodifiableList(this.actions);
	}

	/**
	 * Remove an action to the rule.
	 * 
	 * @param action
	 *            The action to remove.
	 */
	public void removeAction(AbstractAction action) {
		this.actions.remove(action);
	}

	/**
	 * Process the rule.
	 * 
	 * @param hook
	 *            The hook processing the rule.
	 * 
	 * @return <code>true</code> if the rule should break the remaining rule processing, <code>false</code>.
	 */
	public boolean process(AbstractHook hook) {
		// Check condition
		if (this.condition!=null&&!this.condition.check(hook))
			// Skip the rule
			return false;
		// Perform each action
		for (AbstractAction action : this.actions) {
			// Perform action
			if (action.perform(hook))
				// Break action chain
				break;
		}
		// TODO
		return false;
	}
}