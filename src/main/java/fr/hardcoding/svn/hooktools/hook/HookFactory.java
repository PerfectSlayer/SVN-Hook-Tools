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
		case START_COMMIT_HOOK:
			hook = new StartCommitHook(hookType, hookParameters);
			break;
		case PRE_COMMIT_HOOK:
			hook = new PreCommitHook(hookType, hookParameters);
			break;
		case POST_COMMIT_HOOK:
			hook = new PostCommitHook(hookType, hookParameters);
			break;
		case PRE_REV_PROP_CHANGE_HOOK:
			hook = new PreRevpropChangeHook(hookType, hookParameters);
			break;
		case POST_REV_PROP_CHANGE_HOOK:
			hook = new PostRevpropChangeHook(hookType, hookParameters);
			break;
		case PRE_LOCK_HOOK:
			hook = new PreLockHook(hookType, hookParameters);
			break;
		case POST_LOCK_HOOK:
			hook = new PostLockHook(hookType, hookParameters);
			break;
		case PRE_UNLOCK_HOOK:
			hook = new PreUnlockHook(hookType, hookParameters);
			break;
		case POST_UNLOCK_HOOK:
			hook = new PostUnlockHook(hookType, hookParameters);
			break;
		}
		// Return created hook
		return hook;
	}
}