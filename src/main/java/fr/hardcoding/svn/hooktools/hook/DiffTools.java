package fr.hardcoding.svn.hooktools.hook;

import java.util.ArrayList;
import java.util.List;

import fr.hardcoding.svn.hooktools.condition.resource.ResourceDiff;

/**
 * This class is an utility class to parse diff format as {@link ResourceDiff}.
 * 
 * @author Perfect Slayer (bruce.bujon@gmail.com)
 * 
 */
public class DiffTools {
	/*
	 * The diff actions.
	 */
	/** The add action. */
	private static final String ADDED_ACTION = "Added:";
	/** The copied action. */
	private static final String COPIED_ACTION = "Copied:";
	/** The deleted action. */
	private static final String DELETED_ACTION = "Deleted:";
	/** The modified action. */
	private static final String MODIFIED_ACTION = "Modified:";
	/** The property changed action. */
	private static final String PROPERTY_CHANGE_ACTION = "Property changes on:";

	/**
	 * Parse a diff output as {@link ResourceDiff}.
	 * 
	 * @param diffOutput
	 *            The diff output to parse.
	 * @return The parsed {@link ResourceDiff}.
	 */
	public static List<ResourceDiff> parseDiffs(String diffOutput) {
		// Declare resource diff collection
		List<ResourceDiff> resourcesDiffs = new ArrayList<>();
		// Split output diff as lines
		String[] lines = diffOutput.split("\\r?\\n", -1);
		// Consume diff output line to parse resource diff
		for (int lineIndex = 0; lineIndex<lines.length; lineIndex++) {
			// Get the diff output line
			String line = lines[lineIndex];
			// Try to read action
			if (line.startsWith(DiffTools.ADDED_ACTION)) {
				// Get resource path
				String resourcePath = line.substring(DiffTools.ADDED_ACTION.length()+1);
				// Create resource diff
				ResourceDiff resourceDiff = new ResourceDiff(resourcePath);
				// Parse the property changes
				// i+= DiffTools.parsePropertyChanges(lines, i+2, resourceDiff); // TODO
				// Add the created resource diff
				resourcesDiffs.add(resourceDiff);
			} else if (line.startsWith(DiffTools.COPIED_ACTION)) {
				// Get copy origin path
				int index = line.indexOf('(');
				if (index==-1)
					continue;
				// Get resource path
				String resourcePath = line.substring(DiffTools.COPIED_ACTION.length()+1, line.indexOf('('));
				// Create resource diff
				ResourceDiff resourceDiff = new ResourceDiff(resourcePath);
				// Parse the property changes
				// i+= DiffTools.parsePropertyChanges(lines, i+2, resourceDiff); // TODO
				// Add the created resource diff
				resourcesDiffs.add(resourceDiff);
			} else if (line.startsWith(DiffTools.DELETED_ACTION)) {
				// Get resource path
				String resourcePath = line.substring(DiffTools.DELETED_ACTION.length()+1);
				// Create resource diff
				ResourceDiff resourceDiff = new ResourceDiff(resourcePath);
				// Parse the property changes
				// i+= DiffTools.parsePropertyChanges(lines, i+2, resourceDiff); // TODO
				// Add the created resource diff
				resourcesDiffs.add(resourceDiff);
			} else if (line.startsWith(DiffTools.MODIFIED_ACTION)) {
				// Get resource path
				String resourcePath = line.substring(DiffTools.MODIFIED_ACTION.length()+1);
				// Create resource diff
				ResourceDiff resourceDiff = new ResourceDiff(resourcePath);
				// Parse the property changes
				// i+= DiffTools.parsePropertyChanges(lines, i+2, resourceDiff); // TODO
				// Add the created resource diff
				resourcesDiffs.add(resourceDiff);
			} else if (line.startsWith(DiffTools.PROPERTY_CHANGE_ACTION)) {
				// Get resource path
				String resourcePath = line.substring(DiffTools.PROPERTY_CHANGE_ACTION.length()+1);
				// Create resource diff
				ResourceDiff resourceDiff = new ResourceDiff(resourcePath);
				// Parse the property changes
				lineIndex += DiffTools.parsePropertyChanges(lines, lineIndex, resourceDiff);
				// Add the created resource diff
				resourcesDiffs.add(resourceDiff);
			}
		}
		// Return parsed resource diffs
		return resourcesDiffs;
	}

	/**
	 * Parse the property changes.
	 * 
	 * @param lines
	 *            The diff output lines.
	 * @param startLine
	 *            The line to start from.
	 * @param resourceDiff
	 *            The resource diff to update with property changes.
	 * @return The number of diff output line consumed.
	 */
	private static int parsePropertyChanges(String[] lines, int startLine, ResourceDiff resourceDiff) {
		// Declare property operation attributes
		String propertyName = null;
		boolean oldValue = false;
		boolean newValue = false;
		String propertyOldValue = "";
		String propertyNewValue = "";
		// Consume diff output line to parse property changes
		for (int lineIndex = startLine+2; lineIndex<lines.length; lineIndex++) {
			// Get the diff output line
			String line = lines[lineIndex];
			/*
			 * Check end of property operation.
			 */
			boolean endOfPropertyChange = DiffTools.isEndOfPropertyChange(lines, lineIndex);
			if (endOfPropertyChange||line.startsWith(DiffTools.ADDED_ACTION)||line.startsWith(DiffTools.MODIFIED_ACTION)
					||line.startsWith(DiffTools.DELETED_ACTION)) {
				// Check if a property operation already was read
				if (propertyName!=null&&(!propertyOldValue.isEmpty()||!propertyNewValue.isEmpty())) {
					// Add the property change
					resourceDiff.addPropertyChange(propertyName, propertyOldValue, propertyNewValue);
				}
				// Reset property operation attributes
				propertyName = null;
				oldValue = false;
				newValue = false;
				propertyOldValue = "";
				propertyNewValue = "";
				// Check end of property changes
				if (endOfPropertyChange) {
					// Return consumed lines
					return lineIndex-startLine+1;
				}
				// Check property operation declaration
				else if (line.startsWith(DiffTools.ADDED_ACTION)) {
					// Get the property name
					propertyName = line.substring(DiffTools.ADDED_ACTION.length()+1);
				} else if (line.startsWith(DiffTools.MODIFIED_ACTION)) {
					// Get the property name
					propertyName = line.substring(DiffTools.MODIFIED_ACTION.length()+1);
				} else if (line.startsWith(DiffTools.DELETED_ACTION)) {
					// Get the property name
					propertyName = line.substring(DiffTools.DELETED_ACTION.length()+1);
				}
			}
			/*
			 * Check property modification.
			 */
			else if (line.startsWith("   + ")) {
				// Mark value as new value
				oldValue = false;
				newValue = true;
				// Strip operator
				line = line.substring(5);
			} else if (line.startsWith("   - ")) {
				// Mark value as old value
				oldValue = true;
				newValue = false;
				// Strip operator
				line = line.substring(5);
			}
			/*
			 * Append modification value.
			 */
			if (oldValue) {
				// Append the modification to old value
				if (!propertyOldValue.isEmpty())
					propertyOldValue += System.lineSeparator();
				propertyOldValue += line;
			} else if (newValue) {
				// Append the modification to new value
				if (!propertyNewValue.isEmpty())
					propertyNewValue += System.lineSeparator();
				propertyNewValue += line;
			}
		}
		// Return no line consumed (should not come here)
		return 0;
	}

	/**
	 * Check if it is the end of a property change (ie two empty following lines).
	 * 
	 * @param lines
	 *            The lines to check.
	 * @param startLine
	 *            The start line to check.
	 * @return <code>true</code> if it is the end of a property change, <code>false</code> otherwise.
	 */
	private static boolean isEndOfPropertyChange(String[] lines, int startLine) {
		/*
		 * Check first line.
		 */
		// Check first empty line
		if (!lines[startLine].isEmpty())
			return false;
		/*
		 * Check second line.
		 */
		// Check if second line is available
		if (lines.length<=startLine)
			return true;
		// Check if second line is empty
		if (!lines[startLine+1].isEmpty())
			return false;
		/*
		 * Check third line (3 empty lines means this is not an end).
		 */
		// Check if third line is available
		if (lines.length>startLine+2)
			return !lines[startLine+2].isEmpty();
		// Return as end of property change (two lines are empty and third isn't)
		return true;
	}
}