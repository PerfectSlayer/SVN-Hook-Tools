package fr.hardcoding.svn.hooktools;

import java.util.List;

import junit.framework.Assert;
import junit.framework.TestCase;
import fr.hardcoding.svn.hooktools.condition.resource.ResourceDiff;
import fr.hardcoding.svn.hooktools.condition.resource.ResourceDiff.PropertyChange;

/**
 * This class is a unit test for {@link DiffTools} features.
 * 
 * @author Perfect Slayer (bruce.bujon@gmail.com)
 * 
 */
public class DiffToolsTest extends TestCase {
	/**
	 * Test property addition parsing.
	 */
	public void testPropertyAddition() {
		// Declare diff output
		String diffOutput = ""+System.lineSeparator()
				+"Property changes on: trunk/extern"+System.lineSeparator()
				+"___________________________________________________________________"+System.lineSeparator()
				+"Added: svn:externals"+System.lineSeparator()
				+"   + ^/branches@3 branch3"+System.lineSeparator()
				+""+System.lineSeparator()
				+""+System.lineSeparator();
		// Parse diff output
		List<ResourceDiff> resourceDiffs = DiffTools.parseDiffs(diffOutput);
		// Check resource diff count
		Assert.assertEquals(1, resourceDiffs.size());
		ResourceDiff resourceDiff = resourceDiffs.get(0);
		// Check resource diff path
		Assert.assertEquals("trunk/extern", resourceDiff.getPath());
		// Check property change count
		Assert.assertEquals(1, resourceDiff.getProperties().size());
		// Check property name
		Assert.assertEquals(true, resourceDiff.getProperties().containsKey("svn:externals"));
		PropertyChange propertyChange = resourceDiff.getProperties().get("svn:externals");
		// Check property old value
		Assert.assertEquals("", propertyChange.getOldValue());
		// Check property new value
		Assert.assertEquals("^/branches@3 branch3"+System.lineSeparator(), propertyChange.getNewValue());
	}

	/**
	 * Test property modification parsing.
	 */
	public void testPropertyModification() {
		// Declare diff output
		String diffOutput = ""+System.lineSeparator()+
				"Property changes on: trunk/extern"+System.lineSeparator()
				+"___________________________________________________________________"+System.lineSeparator()
				+"Modified: svn:externals"+System.lineSeparator()
				+"   - svn://localhost/branches@3 branch3"+System.lineSeparator()
				+""+System.lineSeparator()
				+"   + ^/branches@3 branch3"+System.lineSeparator()
				+""+System.lineSeparator()
				+""+System.lineSeparator();
		// Parse diff output
		List<ResourceDiff> resourceDiffs = DiffTools.parseDiffs(diffOutput);
		// Check resource diff count
		Assert.assertEquals(1, resourceDiffs.size());
		ResourceDiff resourceDiff = resourceDiffs.get(0);
		// Check resource diff path
		Assert.assertEquals("trunk/extern", resourceDiff.getPath());
		// Check property change count
		Assert.assertEquals(1, resourceDiff.getProperties().size());
		// Check property name
		Assert.assertEquals(true, resourceDiff.getProperties().containsKey("svn:externals"));
		PropertyChange propertyChange = resourceDiff.getProperties().get("svn:externals");
		// Check property old value
		Assert.assertEquals("svn://localhost/branches@3 branch3"+System.lineSeparator(), propertyChange.getOldValue());
		// Check property new value
		Assert.assertEquals("^/branches@3 branch3"+System.lineSeparator(), propertyChange.getNewValue());
	}

	/**
	 * Test property deletion parsing.
	 */
	public void testProperytDeletion() {
		// Declare diff output
		String diffOutput = ""+System.lineSeparator()
				+"Property changes on: trunk/extern"+System.lineSeparator()
				+"___________________________________________________________________"+System.lineSeparator()
				+"Added: svn:externals"+System.lineSeparator()
				+"   - ^/branches@3 branch3"+System.lineSeparator()
				+""+System.lineSeparator()
				+""+System.lineSeparator();
		// Parse diff output
		List<ResourceDiff> resourceDiffs = DiffTools.parseDiffs(diffOutput);
		// Check resource diff count
		Assert.assertEquals(1, resourceDiffs.size());
		ResourceDiff resourceDiff = resourceDiffs.get(0);
		// Check resource diff path
		Assert.assertEquals("trunk/extern", resourceDiff.getPath());
		// Check property change count
		Assert.assertEquals(1, resourceDiff.getProperties().size());
		// Check property name
		Assert.assertEquals(true, resourceDiff.getProperties().containsKey("svn:externals"));
		PropertyChange propertyChange = resourceDiff.getProperties().get("svn:externals");
		// Check property old value
		Assert.assertEquals("^/branches@3 branch3"+System.lineSeparator(), propertyChange.getOldValue());
		// Check property new value
		Assert.assertEquals("", propertyChange.getNewValue());
	}
}