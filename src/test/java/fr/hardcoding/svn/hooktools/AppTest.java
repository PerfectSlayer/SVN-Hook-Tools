package fr.hardcoding.svn.hooktools;

import fr.hardcoding.svn.hooktools.condition.resource.ResourceLocation;
import fr.hardcoding.svn.hooktools.condition.resource.ResourceLocationType;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {
	/**
	 * Create the test case
	 * 
	 * @param testName
	 *            name of the test case
	 */
	public AppTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(AppTest.class);
	}

	/**
	 * Rigorous Test :-)
	 */
	public void testApp() {
		/*
		 * Test resource location parsing.
		 */
		ResourceLocation location = null;
		location = ResourceLocation.getFromPath("/");
		assertEquals(ResourceLocationType.ROOT_LOCATION, location.getLocationType());
		location = ResourceLocation.getFromPath("/branches");
		assertEquals(ResourceLocationType.BRANCHES_LOCATION, location.getLocationType());
		location = ResourceLocation.getFromPath("/tags");
		assertEquals(ResourceLocationType.TAGS_LOCATION, location.getLocationType());
		location = ResourceLocation.getFromPath("/trunk");
		assertEquals(ResourceLocationType.TRUNK_LOCATION, location.getLocationType());
		location = ResourceLocation.getFromPath("/branches/branch-name");
		assertEquals(ResourceLocationType.A_BRANCH_LOCATION, location.getLocationType());
		assertEquals("branch-name", location.getBranchName());
		location = ResourceLocation.getFromPath("/tags/tag-name");
		assertEquals(ResourceLocationType.A_TAG_LOCATION, location.getLocationType());
		assertEquals("tag-name", location.getTagName());
		location = ResourceLocation.getFromPath("/branches/branch-name/README");
		assertEquals(ResourceLocationType.IN_A_BRANCH_LOCATION, location.getLocationType());
		assertEquals("branch-name", location.getBranchName());
		location = ResourceLocation.getFromPath("/tags/tag-name/README");
		assertEquals(ResourceLocationType.IN_A_TAG_LOCATION, location.getLocationType());
		assertEquals("tag-name", location.getTagName());
		location = ResourceLocation.getFromPath("/trunk/README");
		assertEquals(ResourceLocationType.IN_TRUNK_LOCATION, location.getLocationType());
		location = ResourceLocation.getFromPath("/project-name/branches");
		assertEquals(ResourceLocationType.BRANCHES_LOCATION, location.getLocationType());
		assertEquals("project-name", location.getProjectName());
		location = ResourceLocation.getFromPath("/project-name/tags");
		assertEquals(ResourceLocationType.TAGS_LOCATION, location.getLocationType());
		assertEquals("project-name", location.getProjectName());
		location = ResourceLocation.getFromPath("/project-name/trunk");
		assertEquals(ResourceLocationType.TRUNK_LOCATION, location.getLocationType());
		assertEquals("project-name", location.getProjectName());
		location = ResourceLocation.getFromPath("/project-name/branches/branch-name");
		assertEquals(ResourceLocationType.A_BRANCH_LOCATION, location.getLocationType());
		assertEquals("project-name", location.getProjectName());
		assertEquals("branch-name", location.getBranchName());
		location = ResourceLocation.getFromPath("/project-name/tags/tag-name");
		assertEquals(ResourceLocationType.A_TAG_LOCATION, location.getLocationType());
		assertEquals("project-name", location.getProjectName());
		assertEquals("tag-name", location.getTagName());
		location = ResourceLocation.getFromPath("/project-name/branches/branch-name/README");
		assertEquals(ResourceLocationType.IN_A_BRANCH_LOCATION, location.getLocationType());
		assertEquals("project-name", location.getProjectName());
		assertEquals("branch-name", location.getBranchName());
		location = ResourceLocation.getFromPath("/project-name/tags/tag-name/README");
		assertEquals(ResourceLocationType.IN_A_TAG_LOCATION, location.getLocationType());
		assertEquals("project-name", location.getProjectName());
		assertEquals("tag-name", location.getTagName());
		location = ResourceLocation.getFromPath("/project-name/trunk/README");
		assertEquals(ResourceLocationType.IN_TRUNK_LOCATION, location.getLocationType());
		assertEquals("project-name", location.getProjectName());
	}
}