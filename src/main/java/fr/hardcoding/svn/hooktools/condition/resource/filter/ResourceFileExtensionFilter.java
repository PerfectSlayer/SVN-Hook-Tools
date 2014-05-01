package fr.hardcoding.svn.hooktools.condition.resource.filter;

import fr.hardcoding.svn.hooktools.condition.StringComparison;
import fr.hardcoding.svn.hooktools.condition.resource.ResourceChange;
import fr.hardcoding.svn.hooktools.configuration.ConfigurationParameter;
import fr.hardcoding.svn.hooktools.hook.AbstractHook;

/**
 * This class is a resource filter which check a resource file extension.
 * 
 * @author Perfect Slayer
 * 
 */
public class ResourceFileExtensionFilter extends AbstractResourceFilter {
	/*
	 * Condition parameter.
	 */
	/** The resource file extension to check. */
	@ConfigurationParameter(isRequired = true)
	public String fileExtension;
	/** The resource file extension comparison. */
	public StringComparison fileExtensionComparison;

	/**
	 * Constructor.
	 */
	public ResourceFileExtensionFilter() {
		// Set default file extension comparison
		this.fileExtensionComparison = StringComparison.IS;
	}

	@Override
	public boolean match(AbstractHook hook, ResourceChange resourceChange) {
		// Get file extension
		String fileName = this.getFileName(resourceChange.getPath());
		String fileExtension = this.getFileExtension(fileName);
		if (fileExtension==null)
			fileExtension = "";
		// Check file extension
		return this.fileExtensionComparison.compare(fileExtension, this.fileExtension);
	}
}