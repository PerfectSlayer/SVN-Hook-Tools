package fr.hardcoding.svn.hooktools.hook;

/**
 * This class is the pre-revprop-change hook implementation.
 * 
 * @author Perfect Slayer (bruce.bujon@gmail.com)
 * 
 */
public class PreRevpropChangeHook extends AbstractRevpropChangeHook {
	/**
	 * Constructor.
	 * 
	 * @param type
	 *            The hook type.
	 * @param parameters
	 *            The hook parameters.
	 */
	public PreRevpropChangeHook(HookType type, String[] parameters) {
		super(type, parameters);
	}

	@Override
	protected String getOldValue(String propertyName) {
		return this.getRevpropValue(propertyName);
	}

	@Override
	protected String getNewValue(String propertyName) {
		return this.readPropertyValue();
	}
}