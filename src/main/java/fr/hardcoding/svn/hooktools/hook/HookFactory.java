package fr.hardcoding.svn.hooktools.hook;

/**
 * The factory for {@link AbstractHook} implementation.
 * 
 * @author Perfect Slayer (bruce.bujon@gmail.com)
 * 
 */
public class HookFactory {
	/**
	 * Create a hook.
	 * 
	 * @param hookType
	 *            The type of hook to create.
	 * @param hookParameters
	 *            The hook parameters.
	 * @return The created hook.
	 */
	public static AbstractHook build(HookType hookType, String[] hookParameters) {
		AbstractHook hook = null;
		switch (hookType) {
		case PRE_COMMIT_HOOK:
			hook = new PreCommitHook(hookType, hookParameters);
			break;
		case POST_COMMIT_HOOK:
			break;
		case POST_LOCK_HOOK:
			break;
		case POST_REV_PROP_CHANGE_HOOK:
			break;
		case POST_UNLOCK_HOOK:
			break;
		case PRE_LOCK_HOOK:
			break;
		case PRE_REV_PROP_CHANGE_HOOK:
			break;
		case PRE_UNLOCK_HOOK:
			break;
		case START_HOOK:
			break;
		default:
			break;

		}

		// Return created hook
		return hook;
	}

}