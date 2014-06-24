package fr.hardcoding.svn.hooktools.hook;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.logging.Level;

import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNPropertyValue;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc.admin.SVNLookClient;

import fr.hardcoding.svn.hooktools.HookTools;
import fr.hardcoding.svn.hooktools.condition.resource.ResourceChange;
import fr.hardcoding.svn.hooktools.condition.resource.ResourceDiff;
import fr.hardcoding.svn.hooktools.condition.resource.ResourceOperation;
import fr.hardcoding.svn.hooktools.condition.resource.ResourceType;

/**
 * This class is the pre-revprop-change hook implementation.
 * 
 * @author Perfect Slayer (bruce.bujon@gmail.com)
 * 
 */
public abstract class AbstractRevpropChangeHook extends AbstractHook {
	/**
	 * Constructor.
	 * 
	 * @param type
	 *            The hook type.
	 * @param parameters
	 *            The hook parameters.
	 */
	public AbstractRevpropChangeHook(HookType type, String[] parameters) {
		super(type, parameters);
	}

	/**
	 * Get the new value of the changed property.
	 * 
	 * @param propertyName
	 *            The changed property name.
	 * @return The new value of the changed property.
	 */
	protected abstract String getOldValue(String propertyName);

	/**
	 * Get the old value of the changed property.
	 * 
	 * @param propertyName
	 *            The changed property name.
	 * @return The old value of the changed property.
	 */
	protected abstract String getNewValue(String propertyName);

	/**
	 * Get a revprop value.
	 * 
	 * @param propertyName
	 *            The property to get value.
	 * @return The property value for the defined revision number.
	 */
	protected String getRevpropValue(String propertyName) {
		// Declare property value
		String value = "";
		// Create SVN revision
		SVNRevision svnRevision = SVNRevision.create(this.revisionNumber);
		// Get SVN look client
		SVNLookClient lookClient = this.getSvnClientManager().getLookClient();
		try {
			// Get old property value
			SVNPropertyValue svnPropertyValue = lookClient.doGetRevisionProperty(this.repositoryPath, propertyName, svnRevision);
			value = svnPropertyValue.getString();
		} catch (SVNException exception) {
			HookTools.LOGGER.log(Level.WARNING, "Unable to retrieve property value.", exception);
		}
		// Return property value
		return value;
	}

	/**
	 * Read the property value from the standard input.
	 * 
	 * @return The read property value.
	 */
	protected String readPropertyValue() {
		// Declare the property value
		String value = "";
		// Create reader from standard input
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
			String line;
			// Read each line
			while ((line = reader.readLine())!=null) {
				// Append line separator if not empty
				if (!value.isEmpty())
					value += AbstractHook.LINE_SEPARATOR;
				// Append line value
				value += line;
			}
		} catch (IOException exception) {
			HookTools.LOGGER.log(Level.WARNING, "Unable to read new property value.", exception);
		}
		// Return property value
		return value;
	}

	@Override
	protected void parseParameters(String[] parameters) {
		// Save repository path
		this.repositoryPath = new File(parameters[0]);
		// Save revision whose property is changed
		this.revisionNumber = Integer.parseInt(parameters[1]);
		// Save commit author and log
		this.commitAuthor = parameters[2];
		this.commitLog = "";
		// Get property changed
		String propertyName = parameters[3];
		// Get property operation
		String operation = parameters[4];
		// Declare property old and new value
		String oldValue = "";
		String newValue = "";
		// Get property old value if not a property addition operation
		if (!operation.equals("A"))
			oldValue = this.getOldValue(propertyName);
		// Get property new value if not a property deletion operation
		if (!operation.equals("D"))
			newValue = this.getNewValue(propertyName);
		// Create resource change and diff
		String path = "";
		ResourceType type = ResourceType.FILE;
		ResourceChange resourceChange = new ResourceChange(this, path, type, ResourceOperation.PROPERTY_CHANGED, true);
		ResourceDiff resourceDiff = new ResourceDiff(path);
		resourceDiff.addPropertyChange(propertyName, oldValue, newValue);
		resourceChange.setDiff(resourceDiff);
		// Save resource change
		this.commitChanges = new HashMap<>();
		this.commitChanges.put(path, resourceChange);
		this.commitDiffLoaded = true;
	}
}