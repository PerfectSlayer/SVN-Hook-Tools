package fr.hardcoding.svn.hooktools;

import java.util.regex.Pattern;

import junit.framework.TestCase;

/**
 * This class is a unit test for fixed operatives reversion on svn:externals property.
 * 
 * @author Perfect Slayer (bruce.bujon@gmail.com)
 * 
 */
public class FixedExternalsTest extends TestCase {
	/**
	 * Test property addition parsing.
	 */
	public void testFixedExternals() {
		// Declare fixed operative reversions pattern
		Pattern pattern = Pattern.compile("^((.+[ 	])?-r[ 	]?[0-9]+[ 	].+\r?\n?)+$");
		// Check new declaration format
		assertTrue(pattern.matcher("third-party/skins -r148        http://svn.example.com/skinproj").matches());
		assertTrue(pattern.matcher("third-party/skins	-r148        http://svn.example.com/skinproj").matches());
		assertTrue(pattern.matcher("third-party/skins -r148        http://svn.example.com/skinproj").matches());
		assertTrue(pattern.matcher("third-party/skins -r 148        http://svn.example.com/skinproj").matches());
		assertTrue(pattern.matcher("third-party/skins -r	148        http://svn.example.com/skinproj").matches());
		// Check old declaration format
		assertTrue(pattern.matcher("-r148 third-party/skins        http://svn.example.com/skinproj").matches());
		assertTrue(pattern.matcher("-r 148 third-party/skins        http://svn.example.com/skinproj").matches());
		assertTrue(pattern.matcher("-r	148 third-party/skins        http://svn.example.com/skinproj").matches());
		// Check wrong declarations
		assertFalse(pattern.matcher("third-party/skins-r148        http://svn.example.com/skinproj").matches());
		assertFalse(pattern.matcher("third-party/-r148skins       http://svn.example.com/skinproj").matches());
		assertFalse(pattern.matcher("third-party/skins-r148        http://svn.example.com/skinproj-r148").matches());
		assertFalse(pattern.matcher("third-party/skins        http://svn.example.com/skinproj -r148").matches());
		// Check multi-line declarations
		assertTrue(pattern.matcher("third-party/skins -r148        http://svn.example.com/skinproj\n" +
								   "third-party/skins2 -r149       http://svn.example.com/skinproj").matches());
		assertTrue(pattern.matcher("-r148 third-party/skins        http://svn.example.com/skinproj\n" +
								   "-r149 third-party/skins2       http://svn.example.com/skinproj").matches());
		assertFalse(pattern.matcher("third-party/skins             http://svn.example.com/skinproj\n" +
								   "third-party/skins2 -r149       http://svn.example.com/skinproj").matches());
		assertFalse(pattern.matcher("-r148 third-party/skins       http://svn.example.com/skinproj\n" +
								    "third-party/skins2            http://svn.example.com/skinproj").matches());
	}
}