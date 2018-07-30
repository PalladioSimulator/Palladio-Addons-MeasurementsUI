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
	
	private static StartApplication instance;


	private StartApplication () {
		this.dataGathering = new DataGathering();
		this.modelAccessor = new ModelAccessor();

	}
	public static StartApplication getInstance () {
		if (StartApplication.instance == null) {
			StartApplication.instance = new StartApplication ();
		}
		return StartApplication.instance;
	}

	public void startApplication() {
		
		initializeSessionResourceURI(this.dataGathering.getAirdPath());
		initializeSession(sessionResourceURI);
		
		if(session!= null && session.isOpen()) {
			this.modelAccessor.initializeModels(session);
		} else {
			System.out.println("No Models are initiated.Make sure a Session is open.");
		}
		
	}
	
	private void initializeSessionResourceURI(String AirdPath) {
		
		try {
			this.sessionResourceURI = URI.createPlatformResourceURI(
					dataGathering.getAirdPath(), true);
		} catch (NullPointerException e) {
			System.out.println("Make sure a project in the project explorer is selected");
		}
		
	}
	
	private void initializeSession(URI sessionResourceURI) {
		
		try {
			this.session = SessionManager.INSTANCE.getSession(sessionResourceURI, new NullProgressMonitor());
		} catch (Exception e) {
			System.out.println("MAke sure a Session can be initiated. A valid URI must be present.");
		}
	}

	
	public ModelAccessor getModelAccessor() {
		return modelAccessor;
	}
	
	

}
