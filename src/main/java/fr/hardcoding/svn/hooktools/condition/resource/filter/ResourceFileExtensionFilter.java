package fr.hardcoding.svn.hooktools.condition.resource.filter;

import fr.hardcoding.svn.hooktools.condition.resource.ResourceOperation;
import fr.hardcoding.svn.hooktools.hook.AbstractHook;

/**
 * This class is a resource filter which check a resource file extension.
 * 
 * @author Perfect Slayer
 * 
 */
public class ResourceFileExtensionFilter extends AbstractResourceFilter {
	/** The resource file extension to check. */
	private final String fileExtension;

	/**
	 * Constructor.
	 * 
	 * @param fileExtension
	 *            The resource file extension to check.
	 */
	public ResourceFileExtensionFilter(String fileExtension) {
		this.fileExtension = fileExtension;
	}

	@Override
	public boolean match(AbstractHook hook, ResourceOperation operation, String path) {
		// Get file extension
		String fileName = this.getFileName(path);
		String fileExtension = this.getFileExtension(fileName);
		if (fileExtension==null)
			// Return as invalid if no extension
			return false;
		// Check file extension
		return fileExtension.equals(this.fileExtension);
	}
}