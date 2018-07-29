package helloworldmenu.handlers;

import java.awt.Container;
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
import org.eclipse.core.resources.IContainer;
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
import org.palladiosimulator.edp2.models.measuringpoint.MeasuringpointFactory;
import org.palladiosimulator.edp2.models.measuringpoint.MeasuringpointPackage;
import org.palladiosimulator.monitorrepository.MonitorRepository;
import org.palladiosimulator.pcm.allocation.util.AllocationResourceFactoryImpl;
import org.palladiosimulator.pcm.repository.util.RepositoryResourceFactoryImpl;
import org.palladiosimulator.pcm.resourceenvironment.ProcessingResourceSpecification;
import org.palladiosimulator.pcm.resourceenvironment.ResourceContainer;
import org.palladiosimulator.pcm.resourceenvironment.util.ResourceenvironmentResourceFactoryImpl;
import org.palladiosimulator.pcm.system.util.SystemResourceFactoryImpl;
import org.palladiosimulator.simulizar.access.IModelAccess;
import org.palladiosimulator.simulizar.access.ModelAccess;
import org.xml.sax.SAXException;
import de.uka.ipd.sdq.workflow.mdsd.blackboard.MDSDBlackboard;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.sirius.business.api.session.Session;
import org.eclipse.sirius.business.api.session.SessionManager;

public class SampleHandler extends AbstractHandler {
	
	
    private final static String REPOSITORY_EXTENSION = "repository";
    private final static String RESOURCE_ENVIRONMENT_EXTENSION = "resourceenvironment";
    private final static String SYSTEM_EXTENSION = "system";
    private final static String ALLOCATION_EXTENSION = "allocation";
	
	
	
	private static final String MODEL_FOLDER = "/ExampleModels/org.palladiosimulator.simulizar.examples.loadbalancer";

    private final static String REPOSITORY_PATH = "/ExampleModels/org.palladiosimulator.simulizar.examples.loadbalancer/loadbalancer.repository";
    private final static String RESOURCE_ENVIRONMENT_PATH = "/ExampleModels/org.palladiosimulator.simulizar.examples.loadbalancer/iaas.resourceenvironment";
    private final static String SYSTEM_PATH = "/ExampleModels/org.palladiosimulator.simulizar.examples.loadbalancer/loadbalancer.system";
    private final static String ALLOCATION_PATH = "/ExampleModels/org.palladiosimulator.simulizar.examples.loadbalancer/loadbalancer_on_iaas.allocation";
	
	  private static final String MONITOR_REPO_PATH = MODEL_FOLDER + "/monitors/server.monitorrepository";
	
	   private static URI systemURI;
	    private static URI resourceEnvironmentURI;
	    private static URI repositoryURI;
	    private static URI allocationURI;
	 private static URI monitorRepoUri;
	 


	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		IWorkspaceRoot wsRoot = ResourcesPlugin.getWorkspace().getRoot();
		IProject [] wsProjects = wsRoot.getProjects();
		for (IProject project : wsProjects) {
			System.out.println("Project :"+project.getName());
			try {
				IResource [] allMembers = project.members();
				for( IResource oneMember : allMembers) {
					System.out.println("Member: "+oneMember.getName());
					System.out.println("Extension : "+oneMember.getFileExtension());
					try {
						if( oneMember.getFileExtension().equals("aird")) {
							System.out.println("Path of aird File: "+ oneMember.getFullPath());
							System.out.println("URI of aird File: "+ oneMember.getLocationURI().toString());
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
		
		
		URI sessionResourceURI = URI.createPlatformResourceURI(
				"/ExampleModels/org.palladiosimulator.simulizar.examples.loadbalancer/representations.aird", true);

		System.out.println("TEst");
		for (Session session : SessionManager.INSTANCE.getSessions()) {
			System.out.println(session.getID());
			for (Resource resource : session.getSemanticResources()) {
				System.out.println(resource.getURI());
				System.out.println(resource.toString());
			}
		}

		final Session session = SessionManager.INSTANCE.getSession(sessionResourceURI, new NullProgressMonitor());
		//ModelAccessor access = session.getModelAccessor();

		for (Resource resource : session.getSemanticResources()) {

			System.out.println("URI: " + resource.getURI());
			for (int i = 0; i < 3; i++) {
				EObject object = resource.getContents().get(i);

//				ResourceContainer contaienr = (ResourceContainer) object;

				for (EObject object2 : object.eContents()) {
					System.out.println("toString " + object2.toString());
					System.out.println("contaienr " + object2.eContainer().toString());
					System.out.println("resource " + object2.eResource().toString());
					System.out.println("class " + object2.eClass().getName());
					System.out.println("class " + object2.eClass().getClassifierID());
					System.out.println("class " + object2.eClass().getFeatureCount());
					for (EObject object3 : object2.eContents()) {

//						ProcessingResourceSpecification test = (ProcessingResourceSpecification) object3;

						System.out.println("toString " + object3.toString());
						System.out.println("contaienr " + object3.eContainer().toString());
						System.out.println("resource " + object3.eResource().toString());
						System.out.println("class " + object3.eClass().getName());
						System.out.println("class " + object3.eClass().getClassifierID());
						System.out.println("class " + object3.eClass().getFeatureCount());

					}
				}
			}
		}
		
		
//		
//		
//		  Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put(REPOSITORY_EXTENSION,
//	                new RepositoryResourceFactoryImpl());
//	        Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put(RESOURCE_ENVIRONMENT_EXTENSION,
//	                new ResourceenvironmentResourceFactoryImpl());
//	        Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put(SYSTEM_EXTENSION,
//	                new SystemResourceFactoryImpl());
//	        Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put(ALLOCATION_EXTENSION,
//	                new AllocationResourceFactoryImpl());
//	        Map<URI, URI> uriMap = URIConverter.URI_MAP;
//	        uriMap.put(URI.createURI(" pathmap://METRIC_SPEC_MODELS/commonMetrics.metricspec"),
//	                URI.createURI("platform:/plugin/org.palladiosimulator.metricspec.resources/commonMetrics.metricspec"));
//		
//		
//		 repositoryURI = URI.createURI(REPOSITORY_PATH, true);
//		 System.out.println(repositoryURI);
//		 
//	        repositoryURI = CommonPlugin.resolve(repositoryURI);
//	        
//	        System.out.println(repositoryURI);
//	        
//	        resourceEnvironmentURI = URI.createURI(RESOURCE_ENVIRONMENT_PATH, true);
//	        resourceEnvironmentURI = CommonPlugin.resolve(resourceEnvironmentURI);
//	        systemURI = URI.createURI(SYSTEM_PATH, true);
//	        systemURI = CommonPlugin.resolve(systemURI);
//	        allocationURI = URI.createURI(ALLOCATION_PATH, true);
//	        allocationURI = CommonPlugin.resolve(allocationURI);
//	     
//		
//		 monitorRepoUri = URI.createURI(MONITOR_REPO_PATH, true);
//		 monitorRepoUri = CommonPlugin.resolve(monitorRepoUri);
//		 
//		 final PCMResourceSetPartition pcmResourceSet = new PCMResourceSetPartition();
//	        pcmResourceSet.loadModel(repositoryURI);
//	        pcmResourceSet.loadModel(resourceEnvironmentURI);
//	        pcmResourceSet.loadModel(systemURI);
//	        pcmResourceSet.loadModel(allocationURI);
//	        final TreeIterator<EObject> pcmModelIterator = pcmResourceSet.getAllocation().eAllContents();
//	        System.out.println("SIZEE: " + pcmResourceSet.getRepositories().size());
//	        EObject monitoredElement = null;
//	        while (pcmModelIterator.hasNext()) {
//	            final EObject element = pcmModelIterator.next();
//	            final EAttribute id = element.eClass().getEIDAttribute();
//	           
//	            
//	           System.out.println(element.eContainingFeature());
//	        }
//
//	        /*
//	         * Put the PCM model into the MDSD blackboard.
//	         */
//	        final MDSDBlackboard blackboard = new MDSDBlackboard();
//	        blackboard.addPartition(LoadPCMModelsIntoBlackboardJob.PCM_MODELS_PARTITION_ID, pcmResourceSet);
//	        final IModelAccess modelAccess = new ModelAccess(blackboard);
//	        
//	        //System.out.println(modelAccess.getMonitorRepositoryModel().getMonitors().get(0).getEntityName());
//			return null;
//	
//	
	
//	private static final String UTF8_ENCODING = "UTF-8";
//	private static final String ELEMENT_ID_PREFIX = "platform:/resource";
//	private static final String REPOSITORY_FILE_EXTENSION = ".repository#";
//	public static final String MEASURING_POINT_FILE_EXTENSION = ".measuringpoint";
//	
//	private static Set<String> getMonitoredElementsForMeasuringPoints(IProject[] projects)
//			throws ParserConfigurationException, SAXException, IOException, TransformerException, CoreException {
//		Map<String, IProject> map = new HashMap<>();
//		for (IProject project : projects) {
//			if (project.isOpen())
//				project.accept(new ResourceURIsFromSAsExtractor(map), IResource.DEPTH_INFINITE, true);
//		}
//		return map.keySet();
//	}
//	
//	private static String getMeasuringPointName(String resourceURI) {
//		String fileName = resourceURI.replace(ELEMENT_ID_PREFIX, ""); //$NON-NLS-2$
//		fileName = fileName.replace(REPOSITORY_FILE_EXTENSION, ""); //$NON-NLS-2$
//		fileName = fileName + MEASURING_POINT_FILE_EXTENSION;
//		//fileName = "/Pets.com/PetsMeasuringPoint.measuringpoint";
//		return fileName;
//	}
//
//	@Override
//	public Object execute(ExecutionEvent event) throws ExecutionException {
//		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
//		MessageDialog.openInformation(
//				window.getShell(),
//				"HelloWorldMenu",
//				"Hello, Eclipse world");
//		
//		System.out.println("Test123");
//		
//
//		// Get the root of the workspace
//				IWorkspace workspace = ResourcesPlugin.getWorkspace();
//				IWorkspaceRoot root = workspace.getRoot();
//				// Get all projects in the workspace
//				IProject[] projects = root.getProjects();
//				// Loop over all projects
//				for (IProject project : projects) {
//					System.out.println(project.getName());
//					IResource test = project.findMember("PetsMeasuringPoint.measuringpoint");
//					IResource test2 =  project.getFolder("monitors");
//
//					if(test2!= null)
//						System.out.println(test2.getName());
//					
//					
//					if(test!= null)
//						
//						System.out.println(test.getName());
//				}
//				
//				
//				
//				System.out.println("HAIODLAWKDAWD");
//		
//				Set<String> newResourceURIs;
//
//				try {
////					newResourceURIs = getMonitoredElementsForMeasuringPoints(projects);
////					Iterator iter = newResourceURIs.iterator();
////					Object first = iter.next();
////					String resourceURI = (String) first;
//			
//				
//				
//				final ResourceSet resourceSet = new ResourceSetImpl();
//
//				// Get the URI of the model file.
//				//
////				String fileName = getMeasuringPointName(resourceURI);
////				final URI fileURI = URI.createPlatformResourceURI(fileName, true);
//
//				// Create a resource for this file.
//				//
////				final Resource resource = resourceSet.createResource(fileURI);
//
//				// Add the initial model object to the contents.
//				MeasuringpointPackage measuringpointPackage = MeasuringpointPackage.eINSTANCE;
//				MeasuringpointFactory measuringpointFactory = measuringpointPackage.getMeasuringpointFactory();
//
//				EClass measuringPoint = measuringpointPackage.getResourceURIMeasuringPoint();
//
//				final EObject rootObject = measuringpointFactory.create(measuringPoint);
//				EAttribute resourceURIAttribute = measuringpointPackage.getResourceURIMeasuringPoint_ResourceURI();
////				rootObject.eSet(resourceURIAttribute, resourceURI);
//				
//
//				System.out.println("HELLLO!!!!!");
//				System.out.println("HELLLO!!!!!");
//				System.out.println("HELLLO!!!!!");
//				System.out.println("HELLLO!!!!!");
//				System.out.println("HELLLO!!!!!");
//				
//				if (rootObject != null) {
////					resource.getContents().add(rootObject);
//					System.out.println(rootObject.toString());
//					
//				}
//
//				// Save the contents of the resource to the file system.
//				final Map<Object, Object> options = new HashMap<Object, Object>();
//				options.put(XMLResource.OPTION_ENCODING, UTF8_ENCODING);
////				resource.save(options);
//				
////				} catch (IOException | ParserConfigurationException | SAXException | TransformerException | CoreException e) {
//					// TODO Auto-generated catch block
////					e.printStackTrace();
//				}
//				
//				finally {
//					
//				}
//				
		return null;
	}
}
