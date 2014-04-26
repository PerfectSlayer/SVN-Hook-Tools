package fr.hardcoding.svn.hooktools.action;

import java.io.IOException;
import java.util.logging.Level;

import fr.hardcoding.svn.hooktools.HookTools;
import fr.hardcoding.svn.hooktools.configuration.ConfigurationParameter;
import fr.hardcoding.svn.hooktools.hook.AbstractHook;

/**
 * This class is an execute action. It runs program.
 * 
 * @author Perfect Slayer (bruce.bujon@gmail.com)
 * 
 */
public class ExecAction extends AbstractAction {
	/** The command to execute. */
	@ConfigurationParameter(isRequired = true)
	public String command;
	/** The command parameter. */
	@ConfigurationParameter
	public String parameters;
	/** The wait for command status (<code>true</code> if the rule must end the command execution, <code>false</code> otherwise). */
	@ConfigurationParameter
	public Boolean waitFor;

	@Override
	public boolean perform(AbstractHook hook) {
		// Create command array
		String[] commandArray = new String[this.parameters==null ? 1 : 2];
		commandArray[0] = this.command;
		// Append parameters
		if (this.parameters!=null)
			commandArray[1] = this.parameters;
		// Run the command
		try {
			Runtime runtime = Runtime.getRuntime();
			Process process = runtime.exec(commandArray);
			// Check if the process must be waiting for
			if (this.waitFor!=null&&this.waitFor)
				process.waitFor();
		} catch (IOException exception) {
			HookTools.LOGGER.log(Level.WARNING, "Unable to execute the command: \""+this.command+"\".", exception);
		} catch (InterruptedException exception) {
			HookTools.LOGGER.log(Level.WARNING, "Unable to wait the result for the command: \""+this.command+"\".", exception);
		}
		// Continue the remaining rule actions
		return false;
	}
}