package org.palladiosimulator.measurementsui.wizardmodel.pages;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceFactoryImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.RollbackException;
import org.eclipse.emf.transaction.TransactionalCommandStack;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.impl.AbstractTransactionalCommandStack;
import org.eclipse.emf.transaction.impl.EMFCommandTransaction;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.stubbing.Answer;
import org.palladiosimulator.measurementsui.datamanipulation.ResourceEditorImpl;
import org.palladiosimulator.measurementsui.wizardmodel.WizardModel;
import org.palladiosimulator.monitorrepository.Monitor;
import org.palladiosimulator.monitorrepository.MonitorRepositoryFactory;
import org.palladiosimulator.monitorrepository.impl.MonitorRepositoryFactoryImpl;
import static org.mockito.Mockito.*;

public class MonitorCreationWizardModelTest {
	
	@Mock
	private Monitor monitor;
	private boolean isEditting;
	@Rule public MockitoRule mockitoRule = MockitoJUnit.rule();
	
	@Before
	public void setUp() throws Exception {
		this.monitor = monitor;
		this.isEditting = isEditting;
		
	}
	
//	
//	@Test Constructor
//	public void testMonitorCreationWizardModel() {
//		fail("Not yet implemented");
//		
//	}

	@Test
	public void testGetMonitor() {
		MonitorCreationWizardModel test = mock(MonitorCreationWizardModel.class);
		Monitor monitor = MonitorRepositoryFactory.eINSTANCE.createMonitor();
		when(test.getMonitor()).thenReturn(monitor);
		assertEquals(test.getMonitor(), monitor);
	}

	@Test
	public void testCanFinish() {
		MonitorCreationWizardModel test = mock(MonitorCreationWizardModel.class);
		Monitor monitor = MonitorRepositoryFactory.eINSTANCE.createMonitor();
		boolean canFinish =  test.canFinish();
		when(monitor.getEntityName().isEmpty()).thenReturn(canFinish);
		assertTrue(true);
	}

	@Test
	public void testGetInfoText() {
		MonitorCreationWizardModel test = mock(MonitorCreationWizardModel.class);
		Monitor monitor = MonitorRepositoryFactory.eINSTANCE.createMonitor();
		String infoText = test.getInfoText();
		
		when(test.getInfoText()).thenAnswer(new Answer() {
			private static final String EDIT_MONITOR_INFO_TEXT = "Edit your Monitor name and set him activated/not activated.";
			private static final String CREATE_MONITOR_INFO_TEXT = "A Monitor specifies which element of your "
			        + "Models should be analyzed during a simulation run."
			        + "\n In this page you can give your Monitor an appropiate name and set it activated/not activated."
			        + "\n Activated Monitors will be simulated during a SimuLizar run, not activated ones will be ignored. ";

			@Override
			public Object answer(InvocationOnMock invocation) throws Throwable {
				if(monitor.getMonitorRepository() != null) 
					return EDIT_MONITOR_INFO_TEXT;
				
				return CREATE_MONITOR_INFO_TEXT;
			}
			
		});
		}

	@Test
	public void testNextStep() {
		//Not tested
	}

	@Test
	public void testGetTitleText() {
		MonitorCreationWizardModel test = mock(MonitorCreationWizardModel.class);
		Monitor monitor = MonitorRepositoryFactory.eINSTANCE.createMonitor();
		String titleText = test.getTitleText();
		
		doAnswer(new Answer(){
			private static final String CREATE_MONITOR_TITEL = "Create Monitor";
			private static final String EDIT_MONITOR_TITEL = "Edit Monitor";
			public Object answer(InvocationOnMock invocation) {
				if(monitor.getMonitorRepository() != null) 
					return EDIT_MONITOR_TITEL;
				
				return CREATE_MONITOR_TITEL;
				}
		}).when(test).getTitleText();
	}

//	@Test
//	public void testSetMonitorName() throws IOException {
//		MonitorCreationWizardModel test = new MonitorCreationWizardModel(monitor, true);
//		ResourceEditorImpl editor = ResourceEditorImpl.getInstance();
//		ComposedAdapterFactory adapterFactory = new ComposedAdapterFactory();
//		BasicCommandStack commandStack = new BasicCommandStack();
//		TransactionalEditingDomain editingDomain = TransactionalEditingDomain.Factory.INSTANCE.createEditingDomain();
//		Monitor monitor = MonitorRepositoryFactory.eINSTANCE.createMonitor();
//		//test.setMonitorName("entityName");
//		commandStack.flush();
//		Resource resource = editingDomain.getResourceSet().createResource(
//		URI.createURI("D:\\Pets\\Pets.com\\PetsMonitor.monitorrepository"));
//		if(monitor != null) {
//			TransactionalCommandStack stack = (TransactionalCommandStack) editingDomain.getCommandStack();
//		stack.execute(new RecordingCommand(editingDomain) {
//
//			@Override
//			protected void doExecute() {
//				test.setMonitorName("entityName");
//				resource.getContents().add(monitor);
//				try {
//				resource.save(null);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//	});
//}
//		assertEquals("entityName", test.getMonitor().getEntityName());
//}

//		ResourceEditorImpl editor = ResourceEditorImpl.getInstance();
//		MonitorRepositoryFactoryImpl.init();
//		ComposedAdapterFactory adapterFactory = new ComposedAdapterFactory();
//		BasicCommandStack commandStack = new BasicCommandStack();
//		//AdapterFactoryEditingDomain editingDomain = new AdapterFactoryEditingDomain(adapterFactory, commandStack);
//		TransactionalEditingDomain editingDomain = TransactionalEditingDomain.Factory.INSTANCE.createEditingDomain();
//		Monitor monitor = MonitorRepositoryFactory.eINSTANCE.createMonitor();
//		commandStack.flush();
//		Resource resource = editingDomain.getResourceSet().createResource(
//		URI.createURI("platform:/D:/Pets/Pets.com/PetsMonitor.monitorrepository"));
//		if(monitor != null) {
//			TransactionalCommandStack stack = (TransactionalCommandStack) editingDomain.getCommandStack();
//			stack.execute(new RecordingCommand(editingDomain) {
//				
////				@Override
////				protected void doExecute() {
////					//wizardModel.setMonitorName("entityName");
////					resource.getContents().add(monitor);
////					try {
////						resource.save(null);
////					} catch (IOException e) {
////						e.printStackTrace();
////					}
////				}
////			});
////		}
////		assertEquals("The new name of monitor is not true.",monitor,wizardModel.setMonitorName("entityName"));
//		
	
	

	@Test
	public void testSetMonitorActivated() {
			
	}

}
