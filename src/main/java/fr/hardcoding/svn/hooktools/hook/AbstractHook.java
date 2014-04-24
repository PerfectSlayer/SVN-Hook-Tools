package fr.hardcoding.svn.hooktools.hook;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.internal.io.fs.FSRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc.admin.ISVNChangeEntryHandler;
import org.tmatesoft.svn.core.wc.admin.SVNChangeEntry;
import org.tmatesoft.svn.core.wc.admin.SVNLookClient;

import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;

import fr.hardcoding.svn.hooktools.condition.resource.ResourceChange;
import fr.hardcoding.svn.hooktools.condition.resource.ResourceDiff;
import fr.hardcoding.svn.hooktools.condition.resource.ResourceOperation;
import fr.hardcoding.svn.hooktools.configuration.Rule;

/**
 * This class is an abstract base class for hook implementation.
 * 
 * @author Perfect Slayer (bruce.bujon@gmail.com)
 * 
 */
public abstract class AbstractHook {
	/** The hook type. */
	protected final HookType type;

	/*
	 * SVNKit related.
	 */
	/** The SVN client manager (<code>null</code> if not initialized). */
	protected SVNClientManager svnClientManager;

	/*
	 * Data related.
	 */
	/** The repository path (<code>null</code> if not defined). */
	protected File repositoryPath;
	/** The transaction name (may be <code>null</code>). */
	protected String transactionName;
	/** The revision number (<code>-1</code> if not available). */
	protected int revisionNumber;
	/** The commit log (<code>null</code> if not available). */
	protected String commitLog;
	/** The commit author (<code>null</code> if not available). */
	protected String commitAuthor;
	/** The commit changes (<code>null</code> if not available). */
	protected List<ResourceChange> commitChanges;
	/** The commit diffs (<code>null</code> if not available). */
	protected List<ResourceDiff> commitDiffs;

	/*
	 * Result related.
	 */
	/** The result error code (<code>0</code> if no error). */
	protected int errorCode;

	/**
	 * Constructor.
	 * 
	 * @param type
	 *            The hook type.
	 * @param parameters
	 *            The hook parameters.
	 */
	public AbstractHook(HookType type, String[] parameters) {
		// Store the hook type
		this.type = type;
		// Parse hook parameters
		this.parseParameters(parameters);
		// Initialize unavailable data
		this.revisionNumber = -1;
		// Set default error code
		this.errorCode = 0;
	}

	/**
	 * Process the hook.
	 * 
	 * @param rules
	 *            The rules to process.
	 */
	public void process(List<Rule> rules) {
		// Process each rule
		for (Rule rule : rules) {
			// Check rule processing
			if (rule.process(this))
				// Break rule processing
				break;
		}
	}

	/**
	 * Get the commit log.
	 * 
	 * @return The commit log.
	 * @throws UnavailableHookDataException
	 *             Throws exception if the commit author could not be retrieved.
	 */
	public String getCommitLog() throws UnavailableHookDataException {
		// Check the commit log
		if (this.commitLog==null) {
			try {
				// Check required data
				if (this.repositoryPath==null)
					throw new UnavailableHookDataException("repository path");
				if (this.transactionName!=null) {
					// Get SVN look client
					SVNLookClient svnLookClient = this.getSvnClientManager().getLookClient();
					// Get commit log from transaction
					this.commitLog = svnLookClient.doGetLog(this.repositoryPath, this.transactionName);
				} else if (this.revisionNumber!=-1) {
					// TODO Handle revision
				} else {
					throw new UnavailableHookDataException("commit log");
				}
			} catch (SVNException exception) {
				throw new UnavailableHookDataException("commit log", exception);
			}
		}
		// Return the commit log
		return this.commitLog;
	}

	/**
	 * Get the commit author.
	 * 
	 * @return The commit author.
	 * @throws UnavailableHookDataException
	 *             Throws exception if the commit author could not be retrieved.
	 */
	public String getCommitAuthor() throws UnavailableHookDataException {
		// Check the commit author
		if (this.commitAuthor==null) {
			try {
				// Check repository path
				if (this.repositoryPath==null)
					throw new UnavailableHookDataException("repository path");
				// Check transaction name
				if (this.transactionName!=null) {
					// Get SVN look client
					SVNLookClient svnLookClient = this.getSvnClientManager().getLookClient();
					// Get commit author from transaction
					this.commitAuthor = svnLookClient.doGetAuthor(this.repositoryPath, this.transactionName);
				} else if (this.revisionNumber!=-1) {
					// TODO Handle revision
				} else {
					throw new UnavailableHookDataException("revision / transaction");
				}
			} catch (SVNException exception) {
				throw new UnavailableHookDataException("commit author", exception);
			}
		}
		// Return the commit author
		return this.commitAuthor;
	}

	/**
	 * Get the commit changes.
	 * 
	 * @return The commit changes.
	 * @throws UnavailableHookDataException
	 *             Throws exception if the commit changes could not be retrieved.
	 */
	public List<ResourceChange> getCommitChanges() throws UnavailableHookDataException {
		// Check the commit changes
		if (this.commitChanges==null) {
			try {
				// Check repository path
				if (this.repositoryPath==null)
					throw new UnavailableHookDataException("repository path");
				// Check transaction name
				if (this.transactionName!=null) {
					// Get SVN look client
					SVNLookClient svnLookClient = this.getSvnClientManager().getLookClient();
					this.commitChanges = new ArrayList<>();
					// Get commit changes from transaction
					svnLookClient.doGetChanged(this.repositoryPath, this.transactionName, new ISVNChangeEntryHandler() {
						@Override
						public void handleEntry(SVNChangeEntry changeEntry) throws SVNException {
							// Get resource operation
							ResourceOperation operation;
							switch (changeEntry.getType()) {
								case SVNChangeEntry.TYPE_ADDED:
									if (changeEntry.getCopyFromPath()==null)
										operation = ResourceOperation.ADDED;
									else
										operation = ResourceOperation.COPIED;
									break;
								case SVNChangeEntry.TYPE_DELETED:
									operation = ResourceOperation.DELETED;
									break;
								case SVNChangeEntry.TYPE_UPDATED:
									operation = ResourceOperation.UPDATED;
								default:
									operation = ResourceOperation.PROPERTY_CHANGED;
							}
							// Create and add resource change
							ResourceChange resourceChange = new ResourceChange(changeEntry.getPath(), operation, changeEntry.hasPropertyModifications());
							AbstractHook.this.commitChanges.add(resourceChange);
						}
					}, true);
				} else if (this.revisionNumber!=-1) {
					// TODO Handle revision
				} else {
					throw new UnavailableHookDataException("revision / transaction");
				}
			} catch (SVNException exception) {
				throw new UnavailableHookDataException("commit changes", exception);
			}
		}
		// Return the commit changes
		return this.commitChanges;
	}

	/**
	 * Get the commit diffs.
	 * 
	 * @return The commit diffs.
	 * @throws UnavailableHookDataException
	 *             Throws exception if the commit diffs could not be retrieved.
	 */
	public List<ResourceDiff> getCommitDiffs() throws UnavailableHookDataException {
		// Check the commit diffs
		if (this.commitDiffs==null) {
			// Create output stream for diff result
			try (ByteOutputStream byteOutputStream = new ByteOutputStream()) {
				// Check repository path
				if (this.repositoryPath==null)
					throw new UnavailableHookDataException("repository path");
				// Check transaction name
				if (this.transactionName!=null) {
					// Get SVN look client
					SVNLookClient svnLookClient = this.getSvnClientManager().getLookClient();
					// Get commit changes from transaction
					svnLookClient.doGetDiff(this.repositoryPath, this.transactionName, true, true, true, byteOutputStream);
				} else if (this.revisionNumber!=-1) {
					// TODO Handle revision
				} else {
					throw new UnavailableHookDataException("revision / transaction");
				}
				// Get diff output from output stream
				String diffOutput = new String(byteOutputStream.getBytes());
				// Parse diff output as resource diffs
				this.commitDiffs = DiffTools.parseDiffs(diffOutput);
			} catch (SVNException exception) {
				throw new UnavailableHookDataException("commit diffs", exception);
			}
		}
		// Return the commit diffs
		return this.commitDiffs;
	}

	/**
	 * Send an error message to subversion client.
	 * 
	 * @param message
	 *            the error message to send.
	 */
	public void sendErrorMessage(String message) {
		System.err.println(message);
	}

	/**
	 * Get the result error code.
	 * 
	 * @return The result error code (<code>0</code> if no error).
	 */
	public int getErrorCode() {
		return this.errorCode;
	}

	/**
	 * Set the result error code.
	 * 
	 * @param errorCode
	 *            The result error code to set (<code>0</code> if no error).
	 */
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * Get the SVN client manager.
	 * 
	 * @return The SVN client manager.
	 */
	protected SVNClientManager getSvnClientManager() {
		// Check SVN client manager
		if (this.svnClientManager==null) {
			// Initialize client back-end
			FSRepositoryFactory.setup();
			// Create SVN client manager
			this.svnClientManager = SVNClientManager.newInstance();
		}
		// Return SVN client manager
		return svnClientManager;
	}

	/**
	 * Parse the hook parameters.
	 * 
	 * @param parameters
	 *            The hook parameters to parse.
	 */
	protected abstract void parseParameters(String[] parameters);
}