package fr.hardcoding.svn.hooktools.condition.resource;

/**
 * This class represents the changed operation of a transaction.
 * 
 * @author Perfect Slayer (bruce.bujon@gmail.com)
 * 
 */
public enum ResourceOperation {
	/** The file or directory add operation. */
	ADDED,
	/** The file or directory copy operation. */
	COPIED,
	/** The file or directory deletion operation. */
	DELETED,
	/** The file updated operation. */
	UPDATED,
	/** The file or directory property change operation (only property, not content). */
	PROPERTY_CHANGED;
}