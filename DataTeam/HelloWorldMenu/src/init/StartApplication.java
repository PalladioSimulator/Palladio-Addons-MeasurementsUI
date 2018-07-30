package init;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.sirius.business.api.session.Session;
import org.eclipse.sirius.business.api.session.SessionManager;

import dataManagement.*;

public class StartApplication {
	
	private DataGathering dataGathering;
	private ModelAccessor modelAccessor;
	private Session session;
	private URI sessionResourceURI;
	
	public StartApplication(){
		this.dataGathering = new DataGathering();
		this.modelAccessor = new ModelAccessor();
	}
	
	public void startApplication() {
		
		initializeSessionResourceURI(this.dataGathering.getAirdPath());
		initializeSession(sessionResourceURI);
		
		this.modelAccessor.initializeModels(session);
		
	}
	
	private void initializeSessionResourceURI(String AirdPath) {
		this.sessionResourceURI = URI.createPlatformResourceURI(
				dataGathering.getAirdPath(), true);
		
	}
	
	private void initializeSession(URI sessionResourceURI) {
		this.session = SessionManager.INSTANCE.getSession(sessionResourceURI, new NullProgressMonitor());
	}

	
	public ModelAccessor getModelAccessor() {
		return modelAccessor;
	}
	
	

}
