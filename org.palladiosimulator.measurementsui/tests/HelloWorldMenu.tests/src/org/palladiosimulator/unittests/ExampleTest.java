package org.palladiosimulator.unittests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.resource.ResourceItemProviderAdapterFactory;
import org.palladiosimulator.measurementsui.datamanipulation.RepositoryCreator;
import org.palladiosimulator.measurementsui.datamanipulation.ResourceEditorImpl;
import org.palladiosimulator.measurementsui.fileaccess.DataGathering;
import org.palladiosimulator.monitorrepository.Monitor;
import org.palladiosimulator.monitorrepository.MonitorRepository;
import org.palladiosimulator.monitorrepository.MonitorRepositoryFactory;
import org.palladiosimulator.monitorrepository.MonitorRepositoryPackage;
import org.palladiosimulator.edp2.dao.*;


public class ExampleTest {
    
    private ResourceEditorImpl editor ;
    
    @Before
    public void init() {
      editor  =  ResourceEditorImpl.getInstance();
     
        
    }
    
    
	@Test
    public void test() {
        // just an example
        assertTrue(true);
    }
	
	@Test
    public void test2() {
        assertEquals(1,1);
    }
	
	@Test
	public void testResourceEditor() {
	    
	    String monitorRepositoryfileName = "/default.monitorrepository";   
        final URI monitorRepositoryfileURI = URI.createPlatformResourceURI(monitorRepositoryfileName, true);

        List<AdapterFactory> factories = new ArrayList<>();
        factories.add(new ResourceItemProviderAdapterFactory());
        ComposedAdapterFactory adapterFactory = new ComposedAdapterFactory();
        BasicCommandStack commandStack = new BasicCommandStack();
        AdapterFactoryEditingDomain editingDomain = new AdapterFactoryEditingDomain(adapterFactory,
                commandStack);
        final Resource resource = editingDomain.getResourceSet().createResource(monitorRepositoryfileURI);

        EClass monitorRepository = MonitorRepositoryPackage.eINSTANCE.getMonitorRepository();
        final EObject monitorRepositoryRootObject = MonitorRepositoryFactory.eINSTANCE.create(monitorRepository);
        
        Monitor monitor = MonitorRepositoryFactory.eINSTANCE.createMonitor();
       System.out.println(resource);
        if (monitorRepositoryRootObject != null) {
            resource.getContents().add(monitorRepositoryRootObject);
        }
        
        ((MonitorRepository) monitorRepositoryRootObject).getMonitors().add(monitor);
        
        try {
           resource.save(null);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

       ResourceEditorImpl.getInstance().setResourceName(monitor, "Hello");
       
       assertEquals("Hello", monitor.getEntityName());
       
	    
	    
	}
}
