package fr.hardcoding.svn.hooktools.hook;

/**
 * This class is the post-revprop-change hook implementation.
 * 
 * @author Perfect Slayer (bruce.bujon@gmail.com)
 * 
 */
public class PostRevpropChangeHook extends AbstractRevpropChangeHook {
	/**
	 * Constructor.
	 * 
	 * @param type
	 *            The hook type.
	 * @param parameters
	 *            The hook parameters.
	 */
	public PostRevpropChangeHook(HookType type, String[] parameters) {
		super(type, parameters);
	}

	@Override
	protected String getOldValue(String propertyName) {
		return this.readPropertyValue();
	}

	@Override
	protected String getNewValue(String propertyName) {
		return this.getRevpropValue(propertyName);
	}
}