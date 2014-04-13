package fr.hardcoding.svn.hooktools;

import java.io.FileInputStream;
import java.io.IOException;
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

	/*
	 * Configure loggers.
	 */
	static {
		// Load logging configuration file
		// TODO fix path
		try (FileInputStream fileInputStream = new FileInputStream("F:/Programmation/Java/svnhooktools/config/logging.properties")) {
			LogManager logManager = LogManager.getLogManager();
			// Apply logging configuration
			logManager.readConfiguration(fileInputStream);
		} catch (IOException exception) {
			HookTools.LOGGER.log(Level.WARNING, "Unable to configure loggers.", exception);
		}
	}

	/**
	 * The main procedure.
	 * 
	 * @param args
	 *            The CLI parameters.
	 */
	public static void main(String[] args) {
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
			HookTools.LOGGER.severe("Missing hook parameters ("+hookParameterCount+" parameter waited).");
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
		RuleSet ruleSet = RuleSet.fromHookType(hookType);

		// Rule emptyCommitLogRule = new Rule("empty commit log");

		// emptyCommitLogRule.setCondition(new EmptyCommitLogCondition());

		// AllCondition allCondition = new AllCondition();
		// allCondition.addCondition(new EmptyCommitLogCondition());
		// allCondition.addCondition(new AuthorCondition("svnuser"));
		// emptyCommitLogRule.setCondition(allCondition);

		// AnyCondition anyCondition = new AnyCondition();
		// anyCondition.addCondition(new EmptyCommitLogCondition());
		// anyCondition.addCondition(new AuthorCondition("Perfect Slayer"));
		// emptyCommitLogRule.setCondition(anyCondition);

		// ResourceCondition resourceCondition = new ResourceCondition();
		// resourceCondition.addResourceFilter(new ResourceOperationFilter(ResourceOperation.ITEM_ADDED));
		// emptyCommitLogRule.setCondition(resourceCondition);

		// emptyCommitLogRule.addAction(new ErrorAction());

		/*
		 * Process hook.
		 */
		// Process hook
		ruleSet.process(hook);
		// Exit with the error code
		System.exit(hook.getErrorCode());
	}
}