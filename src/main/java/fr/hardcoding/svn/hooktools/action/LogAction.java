package fr.hardcoding.svn.hooktools.action;

import java.util.Objects;
import java.util.logging.Level;

import fr.hardcoding.svn.hooktools.HookTools;
import fr.hardcoding.svn.hooktools.configuration.ConfigurationParameter;
import fr.hardcoding.svn.hooktools.hook.AbstractHook;

/**
 * This class is a log action. It writes log entry into the application log.
 * 
 * @author Perfect Slayer (bruce.bujon@gmail.com)
 * 
 */
public class LogAction extends AbstractAction {
	/** Serialization id. */
	private static final long serialVersionUID = 1309481957705730911L;
	/** The log entry message. */
	@ConfigurationParameter(isRequired = true)
	public String message;
	/** The log entry level. */
	@ConfigurationParameter
	public String level;

	/**
	 * Constructor.
	 */
	public LogAction() {
		// Set default log entry level
		this.level = Level.INFO.toString();
	}

	@Override
	public void perform(AbstractHook hook) {
		// Add log entry to application log
		HookTools.LOGGER.log(Level.parse(this.level), "[LogAction] "+this.message);
	}
	
	@Override
	public String toString() {
		return "Log action (message: "+Objects.toString(this.message)+")";
	}
}