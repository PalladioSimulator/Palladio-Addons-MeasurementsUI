package org.palladiosimulator.measurementsui.dataprovider;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.sirius.business.api.session.Session;
import org.eclipse.sirius.business.api.session.SessionManager;
import org.palladiosimulator.measurementsui.fileaccess.ValidProjectAccessor;
import org.palladiosimulator.monitorrepository.MonitorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.palladiosimulator.measurementsui.fileaccess.ModelAccessor;

/**
 * This class manages the current project and monitorRepository of the workspace which are selected 
 * and provides access to all necessary data from this project.
 * 
 * @author Lasse Merz
 *
 */
public final class DataApplication {

    private ValidProjectAccessor validProjectAccessor;
    private ModelAccessor modelAccessor;
    private Session session;
    private URI sessionResourceURI;
    private IProject project;
    private MonitorRepository monitorRepository;

    private static DataApplication instance;

    private final Logger logger = LoggerFactory.getLogger(DataApplication.class);

    /**
     * Private constructor for singelton pattern initializes DataGathering and ModelAccesor
     */
    private DataApplication() {
        this.validProjectAccessor = new ValidProjectAccessor();
        this.modelAccessor = new ModelAccessor();
    }

    /**
     * Get the instance of DataApplication
     * 
     * @return instance of DataApplication
     */
    public static DataApplication getInstance() {
        if (DataApplication.instance == null) {
            DataApplication.instance = new DataApplication();
        }
        return DataApplication.instance;
    }

    /**
     * Loads all palladio component models Models given a project is selected and it has a .aird
     * file(modeling Project nature). Initializes a session correspondig to the project, which is
     * used to load the models. Checks for Monitor-/MeasuringPoint-Repositories and creates them if
     * none exist.
     * 
     * @param project
     *            to load data from
     * @param monitorRepositorySelectionIndex
     *             index from the monitorRepository to load
     */
    public void loadData(IProject project, int monitorRepositorySelectionIndex) {
        this.project = project;

        this.validProjectAccessor.getAirdFileOfProject(this.project).ifPresent(airdFile -> 
        initializeSessionResourceURI(airdFile));
        initializeSession(sessionResourceURI);

        if (session != null) {
            this.modelAccessor.initializeModels(session);
            this.modelAccessor.checkIfRepositoriesExist(project);
            updateMonitorRepository(monitorRepositorySelectionIndex);

        } else {
            logger.warn("No Models are initiated. Make sure a Session is open.");
        }

    }

    /**
     * Updates the models by reloading them through a new session
     * also reloads the selected monitorRepository
     *  
     * @param monitorRepositorySelectionIndex
     *             index from the monitorRepository to load
     */
    public void updateData(int monitorRepositorySelectionIndex) {
    
        this.validProjectAccessor.getAirdFileOfProject(this.project).ifPresent(airdFile -> 
        initializeSessionResourceURI(airdFile));
        initializeSession(sessionResourceURI);
        if (session != null) {
            this.modelAccessor.initializeModels(session);
            this.modelAccessor.checkIfRepositoriesExist(project);
            updateMonitorRepository(monitorRepositorySelectionIndex);
            
        } else {
            logger.warn("No Models are initiated. Make sure a Session is open.");
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
            logger.warn("No valid path to an air file");
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
            logger.warn("Make sure a Session can be initiated. A valid URI must be present.");
        }
    }

    /**
     * Updates the currently selected monitorRepository based on the selectionIndex
     * 
     * @param selectionIndex
     *            which monitorRepository should be selected
     */
    public void updateMonitorRepository(int selectionIndex) {
        if (this.modelAccessor.monitorRepositoryExists()
                && this.modelAccessor.getMonitorRepositoryList().size() > selectionIndex && selectionIndex >= 0) {
            this.monitorRepository = this.modelAccessor.getMonitorRepositoryList().get(selectionIndex);
        } else {
            this.monitorRepository = this.modelAccessor.getMonitorRepositoryList().get(0);
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
    public ValidProjectAccessor getValidProjectAccessor() {
        return validProjectAccessor;
    }

    /**
     * Returns the project to which the dataApplication is currently connected
     * 
     * @return current Project
     */
    public IProject getProject() {
        return project;
    }

    /**
     * Returns the selected monitorRepository
     * @return MonitorRepository
     */
    public MonitorRepository getMonitorRepository() {
        return monitorRepository;
    }

}
