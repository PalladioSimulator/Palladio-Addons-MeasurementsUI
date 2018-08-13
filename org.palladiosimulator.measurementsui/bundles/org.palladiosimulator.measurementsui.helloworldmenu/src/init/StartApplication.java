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

	/**
	 * Starts the application.
	 * Loads all pcm Models given a project is selected and it has a .aird file(modeling Project nature).
	 */
	public void startApplication() {
		//gives airdFile of first project in Workspace that has an aird File
		initializeSessionResourceURI(this.dataGathering.getAirdFile(this.dataGathering.getAllProjectAirdfiles().get(0)));
		initializeSession(sessionResourceURI);
		
		if(session!= null ) {
			this.modelAccessor.initializeModels(session);
		} else {
			System.err.println("No Models are initiated. Make sure a Session is open.");
		}
		
	}
	
	/**
	 * Creates the session URI given a path to a .aird file of a project
	 * @param AirdPath path to the .aird file
	 */
	private void initializeSessionResourceURI(String AirdPath) {
		
		try {
			this.sessionResourceURI = URI.createPlatformResourceURI(
					AirdPath, true);
		} catch (NullPointerException e) {
			System.err.println("Make sure a project in the project explorer is selected");
		}
		
	}
	
	/**
	 * Initializes the session given a valid URI to a project
	 * 
	 * @param sessionResourceURI URI of the session
	 */
	private void initializeSession(URI sessionResourceURI) {
		
		try {
			this.session = SessionManager.INSTANCE.getSession(sessionResourceURI, new NullProgressMonitor());
		} catch (Exception e) {
			System.err.println("MAke sure a Session can be initiated. A valid URI must be present.");
		}
	}

	/**
	 * Returns an instance of ModelAccessor which can be used to access all pcm models after they are loaded
	 * @return ModelAccessor instance
	 */
	public ModelAccessor getModelAccessor() {
		return modelAccessor;
	}
	
	

}
