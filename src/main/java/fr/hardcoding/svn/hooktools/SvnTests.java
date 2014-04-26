package fr.hardcoding.svn.hooktools;

import java.io.File;

import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.fs.FSRepositoryFactory;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

/**
 * This class is a simple test class.
 * 
 * @author Perfect Slayer (bruce.bujon@gmail.com)
 * 
 */
public class SvnTests {
	/**
	 * The main procedure.
	 * 
	 * @param args
	 *            The CLI parameters.
	 */
	public static void main(String[] args) {

		FSRepositoryFactory.setup();
		File repositoryPath = new File("F:\\Programmation\\Java\\svnhooktools\\svn\\repo");
		String name = "anonymous";
		String password = "anonymous";

		try {
			SVNURL svnUrl = SVNURL.fromFile(repositoryPath);
			SVNRepository repository = SVNRepositoryFactory.create(svnUrl);
			ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(name, password);
			repository.setAuthenticationManager(authManager);

			System.out.println("Repository Root: "+repository.getRepositoryRoot(true));
			System.out.println("Repository UUID: "+repository.getRepositoryUUID(true));
		} catch (SVNException exception) {
			exception.printStackTrace();
		}

	}

}
