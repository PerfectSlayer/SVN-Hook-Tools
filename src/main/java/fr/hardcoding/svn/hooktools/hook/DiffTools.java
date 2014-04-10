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
		String[] lines = diffOutput.split("\n");
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
		String propertyAdditions = null;
		// Consume diff output line to parse property changes
		for (int lineIndex = startLine+2; lineIndex<lines.length; lineIndex++) {
			// Get the diff output line
			String line = lines[lineIndex];
			// Check end of property operation
			if (line.trim().isEmpty()) {
				// Check if a property operation already was read
				if (propertyName!=null&&propertyAdditions!=null) {
					// Add the property operation
					if (propertyName!=null&&propertyAdditions!=null) {
						resourceDiff.addPropertyChange(propertyName, propertyAdditions);
					}
					// Return consumed lines
					return lineIndex-startLine;
				}
			}
			// Check property operation declaration
			if (line.charAt(0)!=' ') {
				// Check if a property operation already was read
				if (propertyName!=null&&propertyAdditions!=null) {
					// Add the property operation
					resourceDiff.addPropertyChange(propertyName, propertyAdditions);
					// Reset property operation attributes
					propertyName = null;
					propertyAdditions = null;
				}
				// Check property operation declaration
				if (line.startsWith(DiffTools.ADDED_ACTION)) {
					// Get the property name
					propertyName = line.substring(DiffTools.ADDED_ACTION.length()+1);
				} else if (line.startsWith(DiffTools.MODIFIED_ACTION)) {
					// Get the property name
					propertyName = line.substring(DiffTools.MODIFIED_ACTION.length()+1);
				}
				// Skip the line
				continue;
			}
			// Check property addition
			if (line.startsWith("   + ")) {
				// Get the property addition
				String propertyAddition = line.substring(5);
				// Append the property addition
				if (propertyAdditions==null) {
					propertyAdditions = "";
				} else {
					propertyAddition += "\n";
				}
				propertyAdditions += propertyAddition;
				// Skip the line
				continue;
			}
		}
		// Return no line consumed (should not come here)
		return 0;
	}
}