package org.palladiosimulator.measurementsui.dataprovider;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.sirius.business.api.session.Session;
import org.eclipse.sirius.business.api.session.SessionManager;
import org.palladiosimulator.measurementsui.fileaccess.*;

/**
 * Connection class between data management and gui
 * @author Lasse
 *
 */
public class DataApplication {

    private DataGathering dataGathering;
    private ModelAccessor modelAccessor;
    private Session session;
    private URI sessionResourceURI;

    private static DataApplication instance;

    private DataApplication() {
        this.dataGathering = new DataGathering();
        this.modelAccessor = new ModelAccessor();

    }

    public static DataApplication getInstance() {
        if (DataApplication.instance == null) {
            DataApplication.instance = new DataApplication();
        }
        return DataApplication.instance;
    }

    /**
     * Starts the application. Loads all pcm Models given a project is selected and it has a .aird
     * file(modeling Project nature).
     */
    public void loadData(int selectionIndex) {
        // gives airdFile of project in Workspace that has an aird File with index selectionIndex
        initializeSessionResourceURI(
                this.dataGathering.getAirdFile(this.dataGathering.getAllProjectAirdfiles().get(selectionIndex)));
        initializeSession(sessionResourceURI);

        if (session != null) {
            this.modelAccessor.initializeModels(session);
        } else {
            System.err.println("No Models are initiated. Make sure a Session is open.");
        }

    }

    /**
     * Creates the session URI given a path to a .aird file of a project
     * 
     * @param airdPath
     *            path to the .aird file
     */
    private void initializeSessionResourceURI(String airdPath) {

        try {
            this.sessionResourceURI = URI.createPlatformResourceURI(airdPath, true);
        } catch (NullPointerException e) {
            System.err.println("No valid path to an air file");
        }

    }

    /**
     * Initializes the session given a valid URI to a project
     * 
     * @param sessionResourceURI
     *            URI of the session
     */
    private void initializeSession(URI sessionResourceURI) {

        try {
            this.session = SessionManager.INSTANCE.getSession(sessionResourceURI, new NullProgressMonitor());
        } catch (Exception e) {
            System.err.println("MAke sure a Session can be initiated. A valid URI must be present.");
        }
    }

    /**
     * Returns an instance of ModelAccessor which can be used to access all pcm models after they
     * are loaded
     * 
     * @return ModelAccessor instance
     */
    public ModelAccessor getModelAccessor() {
        return modelAccessor;
    }

    /**
     * Returns an instance of the DataGatherer which is responsible for getting projects and paths
     * in the eclipse workbench
     * 
     * @return DataGathering instance
     */
    public DataGathering getDataGathering() {
        return dataGathering;
    }

}
