package helloworldmenu.handlers;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.CommonPlugin;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;
import org.palladiosimulator.analyzer.workflow.blackboard.PCMResourceSetPartition;
import org.palladiosimulator.analyzer.workflow.jobs.LoadPCMModelsIntoBlackboardJob;
import org.palladiosimulator.edp2.models.measuringpoint.MeasuringPointRepository;
import org.palladiosimulator.edp2.models.measuringpoint.MeasuringpointFactory;
import org.palladiosimulator.edp2.models.measuringpoint.MeasuringpointPackage;
import org.palladiosimulator.monitorrepository.MonitorRepository;
import org.palladiosimulator.pcm.allocation.Allocation;
import org.palladiosimulator.pcm.allocation.util.AllocationResourceFactoryImpl;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.util.RepositoryResourceFactoryImpl;
import org.palladiosimulator.pcm.resourceenvironment.ProcessingResourceSpecification;
import org.palladiosimulator.pcm.resourceenvironment.ResourceContainer;
import org.palladiosimulator.pcm.resourceenvironment.ResourceEnvironment;
import org.palladiosimulator.pcm.resourceenvironment.util.ResourceenvironmentResourceFactoryImpl;
import org.palladiosimulator.pcm.system.util.SystemResourceFactoryImpl;
import org.palladiosimulator.pcm.usagemodel.UsageModel;
import org.palladiosimulator.simulizar.access.IModelAccess;
import org.palladiosimulator.simulizar.access.ModelAccess;
import org.xml.sax.SAXException;
import de.uka.ipd.sdq.workflow.mdsd.blackboard.MDSDBlackboard;

import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain; 
import org.eclipse.emf.transaction.util.TransactionUtil;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.sirius.business.api.session.Session;
import org.eclipse.sirius.business.api.session.SessionManager;

public class SampleHandler extends AbstractHandler {
	

	private final String filePath= "/nbvc/representations.aird";
	
	private ResourceEnvironment resourceEnvironment;
	private org.palladiosimulator.pcm.system.System system;
	private Allocation allocation;
	private Repository repository;
	private UsageModel usageModel;
	
	private MeasuringPointRepository measuringPointRpository;
	private MonitorRepository monitorRepository;
	
	
	private void checkPcmModel(EObject pcmModel) {
		
		if (pcmModel instanceof ResourceEnvironment) {
			this.resourceEnvironment = (ResourceEnvironment) pcmModel;
//			System.out.println("resource");
		}
		if (pcmModel instanceof org.palladiosimulator.pcm.system.System) {
			this.system = (org.palladiosimulator.pcm.system.System) pcmModel;
//			System.out.println("system");
		}
		if (pcmModel instanceof Allocation) {
			this.allocation = (Allocation) pcmModel;
//			System.out.println("allo");
		}
		if (pcmModel instanceof Repository) {
			
			
			if(pcmModel.eResource().getURI().toString().split(":")[0].contains("platform")) {
				this.repository = (Repository) pcmModel;
				
//				System.out.println(pcmModel.eResource().getURI());
//				
//				System.out.println(((Repository) pcmModel).getEntityName());
//				System.out.println(((Repository) pcmModel).getId());
//				System.out.println("repsoi");
			}
		}

		if (pcmModel instanceof UsageModel) {
			this.usageModel = (UsageModel) pcmModel;
//			System.out.println("usage");
		}

		if (pcmModel instanceof MeasuringPointRepository) {
			this.measuringPointRpository = (MeasuringPointRepository) pcmModel;
//			System.out.println("MP");
		}

		if (pcmModel instanceof MonitorRepository) {
			this.monitorRepository = (MonitorRepository) pcmModel;
			System.out.println("MonRepoName: "+monitorRepository.getEntityName());
			doEditing(monitorRepository);
			System.out.println("MonRepoName: "+monitorRepository.getEntityName());
			System.out.println("MonitorChildName: "+ monitorRepository.getMonitors().get(0).getEntityName());
		}
	}



	public void doEditing(EObject element) {
	    // Make sure your element is attached to a resource, otherwise this will return null
	    TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(element);
	    domain.getCommandStack().execute(new RecordingCommand(domain) {
	    
	        @Override
	        protected void doExecute() {
	            // Implement your write operations here,
	            // for example: set a new name
	            element.eSet(element.eClass().getEStructuralFeature("Entity Name"), "aNewName");
	        }
	    });
	}
	
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

//		getAirdFile(getCurrentSelectedProject());
		System.out.println("Path: "+getAirdFile(getCurrentSelectedProject().toString()));
		String path = "/"+getAirdFile(getCurrentSelectedProject().toString());
		
		
		
		// test for any available session
//		for (Session session : SessionManager.INSTANCE.getSessions()) {
//			System.out.println(session.getID());
//			for (Resource resource : session.getSemanticResources()) {
//				System.out.println(resource.getURI());
//				System.out.println(resource.toString());
//			}
//		}

		
		// create URI and accordingly the session
		URI sessionResourceURI = URI.createPlatformResourceURI(
				path, true);
		final Session session = SessionManager.INSTANCE.getSession(sessionResourceURI, new NullProgressMonitor());
		//ModelAccessor access = session.getModelAccessor();

		for (Resource resource : session.getSemanticResources()) {

			for (EObject pcmModel: resource.getContents()) {	
				//System.out.println(pcmModel.eClass().getName());
				
				checkPcmModel(pcmModel);
			}
		}
		
		
			
		return null;
	}
/**
 * @author Florian
 * This method returns the aird file of the project that is currently selected in project explorer
 * @param selectedProject a String of the base project that is currently selected in the project explorer
 */
	private String getAirdFile(String selectedProject) {
		IWorkspaceRoot wsRoot = ResourcesPlugin.getWorkspace().getRoot();
		IProject [] wsProjects = wsRoot.getProjects();
		for (IProject project : wsProjects) {
			if(selectedProject != null && project.getName().equals(selectedProject)) {	
//				System.out.println("Project :"+project.getName());
				try {
					IResource [] allMembers = project.members();
					for( IResource oneMember : allMembers) {
//						System.out.println("Member: "+oneMember.getName());
//						System.out.println("Extension : "+oneMember.getFileExtension());
						try {
							if( oneMember.getFileExtension().equals("aird")) {
//								System.out.println("Path of aird File: "+ oneMember.getFullPath());
//								System.out.println("URI of aird File: "+ oneMember.getLocationURI().toString());
								
								return oneMember.getFullPath().toString();
							}
						} catch (NullPointerException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				} catch (CoreException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		return null;
	}
	/**
	 * @author Florian
	 * @return the name of the selected base project
	 */
	private String getCurrentSelectedProject() {
		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		try {
			if(window != null) {
//				System.out.println("Window: "+window.toString());
				IStructuredSelection selection = (IStructuredSelection) window.getSelectionService().getSelection();
//				System.out.println("Selection: "+selection.toString());
				
				Object firstElement = selection.getFirstElement();
//				System.out.println("First Element: "+ firstElement.toString());
				String projectName = firstElement.toString().split("/")[1];
//				System.out.println("Base Project: "+ projectName);
				
//				if(firstElement instanceof IAdaptable) {
//					IProject project = (IProject)((IAdaptable)firstElement).getAdapter(IProject.class);
//					System.out.println("Project: "+ project);
//					
//				}
				return projectName;
			}
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			System.out.println("Make sure a project is selected");
		}
		return null;
	}
}
