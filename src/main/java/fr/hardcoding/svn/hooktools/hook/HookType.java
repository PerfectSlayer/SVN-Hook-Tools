package fr.hardcoding.svn.hooktools.hook;

/**
 * The hook type.
 * 
 * @author Perfect Slayer (bruce.bujon@gmail.com)
 * 
 */
public enum HookType {
	/** The start commit hook type. */
	START_COMMIT_HOOK("start-commit", 3),
	/** The pre-commit hook type. */
	PRE_COMMIT_HOOK("pre-commit", 2),
	/** The post-commit hook type. */
	POST_COMMIT_HOOK("post-commit", 2),
	/** The pre-revprop-change hook type. */
	PRE_REVPROP_CHANGE_HOOK("pre-revprop-change", 5),
	/** The post-revprop-change hook type. */
	POST_REVPROP_CHANGE_HOOK("post-revprop-change", 5),
	/** The pre-lock hook type. */
	PRE_LOCK_HOOK("pre-lock", 5),
	/** The post-lock hook type. */
	POST_LOCK_HOOK("post-lock", 2),
	/** The pre-unlock hook type. */
	PRE_UNLOCK_HOOK("pre-unlock", 5),
	/** The post-unlock hook type. */
	POST_UNLOCK_HOOK("post-unlock", 2);

	/** The hook name. */
	private final String hookName;
	/** The number of parameters for the hook. */
	private final int parameterCount;

	/**
	 * Get a hook type according a hook name.
	 * 
	 * @param name
	 *            The hook name to get the hook type.
	 * @return The related hook type, <code>null</code> if no related hook type.
	 */
	public static HookType fromName(String name) {
		// Check each hook type name
		for (HookType hookType : HookType.values()) {
			// Check hook name
			if (hookType.hookName.equals(name))
				// Return related hook type
				return hookType;
		}
		// Return no hook type found
		return null;
	}

	/**
	 * Constructor.
	 * 
	 * @param name
	 *            The hook name.
	 * @param parameterCount
	 *            The number of parameters for the hook.
	 */
	private HookType(String name, int parameterCount) {
		// Store hook parameters
		this.hookName = name;
		this.parameterCount = parameterCount;
	}

	/**
	 * Get the hook name.
	 * 
	 * @return The hook name.
	 */
	public String getHookName() {
		return this.hookName;
	}

	/**
	 * Get the number of parameters for the hook.
	 * 
	 * @return The number of parameters for the hook.
	 */
	public int getParameterCount() {
		return this.parameterCount;
	}
}