package fr.hardcoding.svn.hooktools.condition.resource;

/**
 * This class represents a resource location.
 * 
 * @author Perfect Slayer (bruce.bujon@gmail.com)
 * 
 */
public enum ResourceLocationType {
	/** The root location (/[project]). */
	ROOT_LOCATION,
	/** The trunk location ([/project]/trunk). */
	TRUNK_LOCATION,
	/** The branches location ([/project]/branches). */
	BRANCHES_LOCATION,
	/** The tags location ([/project]/tags). */
	TAGS_LOCATION,
	/** A branch location ([/project]/branches/branch). */
	A_BRANCH_LOCATION,
	/** A tag location ([/project]/tags/tag). */
	A_TAG_LOCATION,
	/** In trunk location ([/project]/trunk/.../location). */
	IN_TRUNK_LOCATION,
	/** In a branch location ([/project]/branches/branch/.../location). */
	IN_A_BRANCH_LOCATION,
	/** In trunk location ([/project]/tags/tag/.../location). */
	IN_A_TAG_LOCATION,
	/** Any special location (/.../location). */
	IN_ROOT_LOCATION;
}