package fr.hardcoding.svn.hooktools.condition.resource.filter;

import fr.hardcoding.svn.hooktools.condition.resource.ResourceOperation;
import fr.hardcoding.svn.hooktools.hook.AbstractHook;
import fr.hardcoding.svn.hooktools.hook.UnavailableHookDataException;

public class ResourcePropertyChangeFilter extends AbstractResourceFilter {
	
	private final String propertyName;
	
	public ResourcePropertyChangeFilter(String propertyName) {
		this.propertyName = propertyName;
	}

	@Override
	public boolean match(AbstractHook hook, ResourceOperation operation, String path) {
		try {
			hook.getCommitDiffs();
		} catch (UnavailableHookDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		// TODO Auto-generated method stub
		return false;
	}

}
