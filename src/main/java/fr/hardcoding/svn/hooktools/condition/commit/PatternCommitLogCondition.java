package fr.hardcoding.svn.hooktools.condition.commit;

import java.util.regex.Pattern;

import fr.hardcoding.svn.hooktools.configuration.ConfigurationParameter;

/**
 * This class is a condition evaluated as true if commit log match a pattern.
 * 
 * @author Perfect Slayer (bruce.bujon@gmail.com)
 * 
 */
public class PatternCommitLogCondition extends AbstractCommitLogCondition {
	/** The pattern the commit log must match. */
	@ConfigurationParameter(isRequired = true)
	public String pattern;

	/**
	 * Constructor.
	 */
	public PatternCommitLogCondition() {

	}

	@Override
	public boolean checkCommitLog(String commitLog) {
		return Pattern.matches(this.pattern, commitLog);
	}
}