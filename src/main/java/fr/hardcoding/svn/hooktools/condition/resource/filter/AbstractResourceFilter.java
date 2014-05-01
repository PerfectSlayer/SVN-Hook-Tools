package fr.hardcoding.svn.hooktools.condition.resource.filter;

import fr.hardcoding.svn.hooktools.condition.resource.ResourceChange;
import fr.hardcoding.svn.hooktools.hook.AbstractHook;

/**
 * This class is an abstract base class for resource filter.
 * 
 * @author Perfect Slayer (bruce.bujon@gmail.com)
 * 
 */
public abstract class AbstractResourceFilter {
	/**
	 * Check if the filter match the change.
	 * 
	 * @param hook
	 *            The checking hook.
	 * @param resourceChange
	 *            The resource change to check.
	 * @return <code>true</code> if the filter match the change, <code>false</code> otherwise.
	 */
	public abstract boolean match(AbstractHook hook, ResourceChange resourceChange);

	/**
	 * Get the file name of a path.
	 * 
	 * @param path
	 *            The path to get file name.
	 * @return The path file name.
	 */
	protected String getFileName(String path) {
		String fileName = path;
		int index = path.lastIndexOf('/');
		if (index!=-1)
			fileName = fileName.substring(index+1);
		return fileName;
	}

	/**
	 * Get the file extension from a file name.
	 * 
	 * @param fileName
	 *            The file name to get extension.
	 * @return The file name extension, <code>null</code> if no extension.
	 */
	protected String getFileExtension(String fileName) {
		int index = fileName.lastIndexOf('.');
		if (index==-1)
			return null;
		return fileName.substring(index+1);
	}
}