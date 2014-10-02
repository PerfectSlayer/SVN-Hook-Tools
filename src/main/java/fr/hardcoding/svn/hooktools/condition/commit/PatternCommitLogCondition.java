package fr.hardcoding.svn.hooktools.condition.commit;

import java.util.Objects;
import java.util.regex.Pattern;

import fr.hardcoding.svn.hooktools.configuration.ConfigurationParameter;

/**
 * This class is a condition evaluated as true if commit log match a pattern.
 * 
 * @author Perfect Slayer (bruce.bujon@gmail.com)
 * 
 */
public class PatternCommitLogCondition extends AbstractCommitLogCondition {
	/** Serialization id. */
	private static final long serialVersionUID = 6357730044308097798L;
	/** The pattern the commit log must match. */
	@ConfigurationParameter(isRequired = true)
	public String pattern;

	@Override
	public boolean checkCommitLog(String commitLog) {
		return Pattern.matches(this.pattern, commitLog);
	}
	
	@Override
	public String toString() {
		return "Pattern commit condition (pattern: "+Objects.toString(this.pattern)+")";
	}
}