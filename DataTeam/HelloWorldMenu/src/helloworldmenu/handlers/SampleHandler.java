package helloworldmenu.handlers;

import java.io.IOException;
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

import org.eclipse.jface.dialogs.MessageDialog;
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
			System.out.println("resource");
		}
		if (pcmModel instanceof org.palladiosimulator.pcm.system.System) {
			this.system = (org.palladiosimulator.pcm.system.System) pcmModel;
			System.out.println("system");
		}
		if (pcmModel instanceof Allocation) {
			this.allocation = (Allocation) pcmModel;
			System.out.println("allo");
		}
		if (pcmModel instanceof Repository) {
			this.repository = (Repository) pcmModel;
			System.out.println(pcmModel.eResource().getURI());
			
			System.out.println(((Repository) pcmModel).getEntityName());
			System.out.println(((Repository) pcmModel).getId());
			System.out.println("repsoi");
		}

		if (pcmModel instanceof UsageModel) {
			this.usageModel = (UsageModel) pcmModel;
			System.out.println("usage");
		}

		if (pcmModel instanceof MeasuringPointRepository) {
			this.measuringPointRpository = (MeasuringPointRepository) pcmModel;
			System.out.println("MP");
		}

		if (pcmModel instanceof MonitorRepository) {
			this.monitorRepository = (MonitorRepository) pcmModel;
			System.out.println("monitor");
		}
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		
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
				filePath, true);
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
}
