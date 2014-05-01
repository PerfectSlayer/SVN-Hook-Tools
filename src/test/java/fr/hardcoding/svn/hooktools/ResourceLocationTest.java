package fr.hardcoding.svn.hooktools;

import junit.framework.Assert;
import junit.framework.TestCase;
import fr.hardcoding.svn.hooktools.condition.resource.ResourceLocation;
import fr.hardcoding.svn.hooktools.condition.resource.ResourceLocationType;

/**
 * This class is a unit test for {@link ResourceLocation} features.
 * 
 * @author Perfect Slayer (bruce.bujon@gmail.com)
 * 
 */
public class ResourceLocationTest extends TestCase {
	/**
	 * Test the {@link ResourceLocation#getFromPath(String)} method.
	 */
	public void testFromPath() {
		/*
		 * Test resource location parsing.
		 */
		ResourceLocation location = null;
		location = ResourceLocation.getFromPath("/");
		Assert.assertEquals(ResourceLocationType.ROOT_LOCATION, location.getLocationType());
		location = ResourceLocation.getFromPath("/branches");
		Assert.assertEquals(ResourceLocationType.BRANCHES_LOCATION, location.getLocationType());
		location = ResourceLocation.getFromPath("/tags");
		Assert.assertEquals(ResourceLocationType.TAGS_LOCATION, location.getLocationType());
		location = ResourceLocation.getFromPath("/trunk");
		Assert.assertEquals(ResourceLocationType.TRUNK_LOCATION, location.getLocationType());
		location = ResourceLocation.getFromPath("/branches/branch-name");
		Assert.assertEquals(ResourceLocationType.A_BRANCH_LOCATION, location.getLocationType());
		Assert.assertEquals("branch-name", location.getBranchName());
		location = ResourceLocation.getFromPath("/tags/tag-name");
		Assert.assertEquals(ResourceLocationType.A_TAG_LOCATION, location.getLocationType());
		Assert.assertEquals("tag-name", location.getTagName());
		location = ResourceLocation.getFromPath("/branches/branch-name/README");
		Assert.assertEquals(ResourceLocationType.IN_A_BRANCH_LOCATION, location.getLocationType());
		Assert.assertEquals("branch-name", location.getBranchName());
		location = ResourceLocation.getFromPath("/tags/tag-name/README");
		Assert.assertEquals(ResourceLocationType.IN_A_TAG_LOCATION, location.getLocationType());
		Assert.assertEquals("tag-name", location.getTagName());
		location = ResourceLocation.getFromPath("/trunk/README");
		Assert.assertEquals(ResourceLocationType.IN_TRUNK_LOCATION, location.getLocationType());
		location = ResourceLocation.getFromPath("/project-name/branches");
		Assert.assertEquals(ResourceLocationType.BRANCHES_LOCATION, location.getLocationType());
		Assert.assertEquals("project-name", location.getProjectName());
		location = ResourceLocation.getFromPath("/project-name/tags");
		Assert.assertEquals(ResourceLocationType.TAGS_LOCATION, location.getLocationType());
		Assert.assertEquals("project-name", location.getProjectName());
		location = ResourceLocation.getFromPath("/project-name/trunk");
		Assert.assertEquals(ResourceLocationType.TRUNK_LOCATION, location.getLocationType());
		Assert.assertEquals("project-name", location.getProjectName());
		location = ResourceLocation.getFromPath("/project-name/branches/branch-name");
		Assert.assertEquals(ResourceLocationType.A_BRANCH_LOCATION, location.getLocationType());
		Assert.assertEquals("project-name", location.getProjectName());
		Assert.assertEquals("branch-name", location.getBranchName());
		location = ResourceLocation.getFromPath("/project-name/tags/tag-name");
		Assert.assertEquals(ResourceLocationType.A_TAG_LOCATION, location.getLocationType());
		Assert.assertEquals("project-name", location.getProjectName());
		Assert.assertEquals("tag-name", location.getTagName());
		location = ResourceLocation.getFromPath("/project-name/branches/branch-name/README");
		Assert.assertEquals(ResourceLocationType.IN_A_BRANCH_LOCATION, location.getLocationType());
		Assert.assertEquals("project-name", location.getProjectName());
		Assert.assertEquals("branch-name", location.getBranchName());
		location = ResourceLocation.getFromPath("/project-name/tags/tag-name/README");
		Assert.assertEquals(ResourceLocationType.IN_A_TAG_LOCATION, location.getLocationType());
		Assert.assertEquals("project-name", location.getProjectName());
		Assert.assertEquals("tag-name", location.getTagName());
		location = ResourceLocation.getFromPath("/project-name/trunk/README");
		Assert.assertEquals(ResourceLocationType.IN_TRUNK_LOCATION, location.getLocationType());
		Assert.assertEquals("project-name", location.getProjectName());
	}
}