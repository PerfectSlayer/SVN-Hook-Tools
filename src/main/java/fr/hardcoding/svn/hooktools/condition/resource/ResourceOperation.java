package fr.hardcoding.svn.hooktools.condition.resource;

/**
 * This class represents the changed operation of a transaction.
 * 
 * @author Perfect Slayer (bruce.bujon@gmail.com)
 * 
 */
public enum ResourceOperation {
	/** The item (file or directory) added operation. */
	ITEM_ADDED,
	/** The item (file or directory) deleted operation. */
	ITEM_DELETED,
	/** The file content changed operation. */
	FILE_CONTENT_CHANGED,
	/** The item (file or directory) property changed operation. */
	PROPERTY_CHANGED,
	/** The file content and property changed operation. */
	FILE_CONTENT_AND_PROPERTY_CHANGED;
}