package org.palladiosimulator.measurementsui.datamanipulation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.HashMap;

import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.palladiosimulator.measurementsui.wizardmodel.pages.MonitorCreationWizardModel;
import org.palladiosimulator.monitorrepository.Monitor;
import org.palladiosimulator.monitorrepository.MonitorRepositoryFactory;
import org.palladiosimulator.pcm.resourceenvironment.ResourceContainer;
import org.palladiosimulator.pcmmeasuringpoint.PcmmeasuringpointFactory;
import org.palladiosimulator.pcmmeasuringpoint.PcmmeasuringpointPackage;
import org.palladiosimulator.pcmmeasuringpoint.ResourceContainerMeasuringPoint;
import org.palladiosimulator.edp2.dao.*;

class ResourceEditorImplTest {


	 
//	 @Test
//	void testGetInstance() {
//		fail("Not yet implemented");
//	}
	

	@Test
	void testSetResourceName() throws IOException {
//		 DataEditor editor1 = new DataEditor();
//		 ComposedAdapterFactory adapterFactory = new ComposedAdapterFactory();
//		 BasicCommandStack commandStack = new BasicCommandStack();
//		 AdapterFactoryEditingDomain editingDomain = new AdapterFactoryEditingDomain(adapterFactory, commandStack);
//		 ResourceEditorImpl editor = ResourceEditorImpl.getInstance();
//		 Resource resource = editingDomain.getResourceSet().createResource(URI.createURI(System.getProperty("D:\\EnPro\\Pets\\Pets.com\\PetsMonitor.monitorrepository")));
		 Monitor element = MonitorRepositoryFactory.eINSTANCE.createMonitor();
//		 resource.getContents().add(element);
//		 resource.save(null); 
		 //assertEquals(editor.setResourceName(monitor,element.getEntityName()));
//		 editor.setResourceName(element, element.getEntityName());
		 assertEquals("New element value is not assigned.","aName",
					element.getEntityName());
	}

//	@Test
//	void testChangeMonitorActive() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testChangeTriggersSelfAdapting() {
//		fail("Not yet implemented");
//	}
//
	@Test
	void testSetMeasuringPointToMonitor() {
		PcmmeasuringpointPackage pcmMeasuringPointPackage = PcmmeasuringpointPackage.eINSTANCE;
		PcmmeasuringpointFactory pcmMeasuringPointFactory = pcmMeasuringPointPackage.getPcmmeasuringpointFactory();
			ResourceContainerMeasuringPoint mp = (ResourceContainerMeasuringPoint) pcmMeasuringPointFactory
					.create(PcmmeasuringpointPackage.eINSTANCE.getResourceContainerMeasuringPoint());
			Monitor monitor = MonitorRepositoryFactory.eINSTANCE.createMonitor();
			monitor.setMeasuringPoint(mp);
			//assertEquals(mp,"ResourceContainerMeasuringPoint");
			assertEquals("New element value is not assigned.","ResourceContainerMeasuringPoint[ResourceEnvironment]",
					mp);
	
	}
//
//	@Test
//	void testAddMeasuringPointToRepository() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testDeleteResource() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testDeleteMultipleResources() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testSetMetricDescription() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testAddMeasurementSpecificationToMonitor() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testAddMeasurementSpecificationsToMonitor() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testAddMonitorToRepository() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testSetProcessingType() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testSetAProcessingTypeAttribute() {
//		fail("Not yet implemented");
//	}

}
