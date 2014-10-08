package fr.hardcoding.svn.hooktools;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import fr.hardcoding.svn.hooktools.configuration.RuleSet;
import fr.hardcoding.svn.hooktools.hook.AbstractHook;
import fr.hardcoding.svn.hooktools.hook.HookFactory;
import fr.hardcoding.svn.hooktools.hook.HookType;

/**
 * This class is the entry point for SVN Hook Tools application.
 * 
 * @author Perfect Slayer (bruce.bujon@gmail.com)
 * 
 */
public class HookTools {
	/** The application logger. */
	public static final Logger LOGGER = Logger.getLogger(HookTools.class.getName());

	/**
	 * The main procedure.
	 * 
	 * @param args
	 *            The CLI parameters.
	 */
	public static void main(String[] args) {
		/*
		 * Configure loggers.
		 */
		// Load logging configuration file
		try (InputStream inputStream = new FileInputStream("config/logging.properties")) {
			LogManager logManager = LogManager.getLogManager();
			// Apply logging configuration
			logManager.readConfiguration(inputStream);
		} catch (IOException exception) {
			HookTools.LOGGER.log(Level.WARNING, "Unable to configure loggers.", exception);
		}
		/*
		 * Create hook.
		 */
		// Check parameters length
		if (args.length==0) {
			// Log and exit
			HookTools.LOGGER.severe("Missing hook name parameter.");
			System.exit(-1);
		}
		// Get hook type
		String hookName = args[0];
		HookType hookType = HookType.fromName(hookName);
		if (hookType==null) {
			// Log and exit
			HookTools.LOGGER.severe("Unknown hook name: "+hookName+".");
			System.exit(-1);
		}
		// Check hook parameter count
		int hookParameterCount = hookType.getParameterCount();
		if (args.length<hookParameterCount+1) {
			// Log and exit
			HookTools.LOGGER.severe("Missing hook parameters ("+hookParameterCount+" parameters waited).");
			System.exit(-1);
		}
		// Create hook
		String[] hookParameters = new String[hookParameterCount];
		System.arraycopy(args, 1, hookParameters, 0, hookParameterCount);
		AbstractHook hook = HookFactory.build(hookType, hookParameters);
		if (hook==null) {
			// Log and exit
			HookTools.LOGGER.severe("Unsupported hook type: "+hookType.name()+".");
			System.exit(-1);
		}
		/*
		 * Load configuration.
		 */
		// Load rule set
		RuleSet ruleSet = RuleSet.fromHookType(hookType);
		/*
		 * Process hook.
		 */
		// Process hook
		ruleSet.process(hook);
		// Exit with the error code
		System.exit(hook.getErrorCode());
	}
}