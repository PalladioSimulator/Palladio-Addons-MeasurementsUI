package mptree;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.parsley.config.Configurator;

import com.google.inject.Inject;

public class SaveableMpTreeView  extends  AbstractMpTreeViewer{

	@Inject
	private Configurator configurator;
	
	@Override
	protected URI createResourceURI() {
		return configurator.createResourceURI(this);
	}

}
