package fr.hardcoding.svn.hooktools.condition.resource;


/**
 * This class represents a resource location.
 * 
 * @author Perfect Slayer (bruce.bujon@gmail.com)
 * 
 */
public class ResourceLocation {
	/*
	 * Default location names.
	 */
	/** The trunk location name. */
	private static final String TRUNK_LOCATION_NAME = "trunk";
	/** The branches location name. */
	private static final String BRANCHES_LOCATION_NAME = "branches";
	/** The tags location name. */
	private static final String TAGS_LOCATION_NAME = "tags";
	/*
	 * Location related.
	 */
	/** The resource location type. */
	private final ResourceLocationType locationType;
	/** The resource project name (<code>null</code> if no project). */
	private final String projectName;
	/** The resource branch name (<code>null</code> if not in a branch). */
	private final String branchName;
	/** The resource tag name (<code>null</code> if not in a tag). */
	private final String tagName;

	/**
	 * Get a resource location from path.
	 * 
	 * @param path
	 *            The resource path to get location.
	 * @return The related resource location.
	 */
	public static ResourceLocation getFromPath(String path) {
		// Split location as resource parts
		String[] parts = path.split("/");
		int nbrOfParts = parts.length;
		// Declare resource location attributes
		ResourceLocationType locationType = null;
		String projectName = null;
		String branchName = null;
		String tagName = null;
		// Check root location
		if (parts.length<=1)
			locationType = ResourceLocationType.ROOT_LOCATION;
		// Check if location type is not defined
		if (locationType==null) {
			// Check trunk, branches and tag location
			switch (parts[nbrOfParts-1]) {
				case ResourceLocation.TRUNK_LOCATION_NAME:
					locationType = ResourceLocationType.TRUNK_LOCATION;
					break;
				case ResourceLocation.BRANCHES_LOCATION_NAME:
					locationType = ResourceLocationType.BRANCHES_LOCATION;
					break;
				case ResourceLocation.TAGS_LOCATION_NAME:
					locationType = ResourceLocationType.TAGS_LOCATION;
					break;
			}
			// Check if location type is identified
			if (locationType!=null) {
				// Check project name
				if (nbrOfParts>2)
					projectName = parts[nbrOfParts-2];
			}
		}
		// Check if location type is not defined
		if (locationType==null) {
			// Check in branch and tag location
			if (nbrOfParts>2) {
				switch (parts[nbrOfParts-2]) {
					case ResourceLocation.BRANCHES_LOCATION_NAME:
						locationType = ResourceLocationType.A_BRANCH_LOCATION;
						break;
					case ResourceLocation.TAGS_LOCATION_NAME:
						locationType = ResourceLocationType.A_TAG_LOCATION;
						break;
				}
				// Check if location type is identified
				if (locationType!=null) {
					// Get branch or tag name
					switch (locationType) {
						case A_BRANCH_LOCATION:
							branchName = parts[nbrOfParts-1];
							break;
						default:
						case A_TAG_LOCATION:
							tagName = parts[nbrOfParts-1];
							break;
					}
					// Check project name
					if (nbrOfParts>3)
						projectName = parts[nbrOfParts-3];
					// Return resource location
					return new ResourceLocation(locationType, projectName, branchName, tagName);
				}
			}
		}
		// Check if location type is not defined
		if (locationType==null) {
			// Check trunk, branch or tag in location
			for (int i = 1; i<=nbrOfParts-2; i++) {
				// Check part with known location name
				switch (parts[i]) {
					case ResourceLocation.TRUNK_LOCATION_NAME:
						locationType = ResourceLocationType.IN_TRUNK_LOCATION;
						break;
					case ResourceLocation.BRANCHES_LOCATION_NAME:
						locationType = ResourceLocationType.IN_A_BRANCH_LOCATION;
						break;
					case ResourceLocation.TAGS_LOCATION_NAME:
						locationType = ResourceLocationType.IN_A_TAG_LOCATION;
						break;
				}
				// Check if location is still not defined
				if (locationType==null)
					continue;
				// Check project name
				if (i>1)
					projectName = parts[i-1];
				// Get branch or tag name
				if (i<nbrOfParts-1) {
					switch (locationType) {
						case IN_A_BRANCH_LOCATION:
							branchName = parts[i+1];
							break;
						default:
						case IN_A_TAG_LOCATION:
							tagName = parts[i+1];
							break;
					}
				}
				// Stop exploration
				break;
			}
		}
		// Check if location type is not defined
		if (locationType==null)
			// Apply default location type
			locationType = ResourceLocationType.IN_ROOT_LOCATION;
		// Return created resource location
		return new ResourceLocation(locationType, projectName, branchName, tagName);
	}

	/**
	 * Private constructor.
	 * 
	 * @param locationType
	 *            The location type.
	 * @param projectName
	 *            The location project name (<code>null</code> if no project).
	 * @param branchName
	 *            The location branch name (<code>null</code> if no branch).
	 * @param tagName
	 *            The location tag name (<code>null</code> if no branch).
	 */
	// TODO Review constructor (always one null value between branch and tag names)
	private ResourceLocation(ResourceLocationType locationType, String projectName, String branchName, String tagName) {
		this.locationType = locationType;
		this.projectName = projectName;
		this.branchName = branchName;
		this.tagName = tagName;
	}

	/**
	 * Get the resource location type.
	 * 
	 * @return The resource location type.
	 */
	public ResourceLocationType getLocationType() {
		return this.locationType;
	}

	/**
	 * Get the resource location project name.
	 * 
	 * @return The resource location project name (<code>null</code> if no project).
	 */
	public String getProjectName() {
		return this.projectName;
	}

	/**
	 * Get the resource location branch name.
	 * 
	 * @return The resource location branch name (<code>null</code> if not in a branch).
	 */
	public String getBranchName() {
		return this.branchName;
	}

	/**
	 * Get the resource location tag name.
	 * 
	 * @return The resource location tag name (<code>null</code> if not in a tag).
	 */
	public String getTagName() {
		return this.tagName;
	}
}