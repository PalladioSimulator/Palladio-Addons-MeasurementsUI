package org.palladiosimulator.measurementsui.wizardmodel.pages;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.*;
import org.mockito.BDDMockito.Then;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.stubbing.Answer;
import org.palladiosimulator.edp2.models.measuringpoint.MeasuringPointRepository;
import org.palladiosimulator.edp2.models.measuringpoint.MeasuringpointFactory;
import org.palladiosimulator.measurementsui.datamanipulation.DataEditor;
import org.palladiosimulator.measurementsui.datamanipulation.ResourceEditorImpl;
import org.palladiosimulator.measurementsui.dataprovider.DataApplication;
import org.palladiosimulator.monitorrepository.Monitor;
import org.palladiosimulator.monitorrepository.MonitorRepositoryFactory;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.resourceenvironment.LinkingResource;
import org.palladiosimulator.pcm.resourceenvironment.ProcessingResourceSpecification;
import org.palladiosimulator.pcm.resourceenvironment.ResourceContainer;
import org.palladiosimulator.pcm.usagemodel.UsageScenario;
import org.palladiosimulator.pcmmeasuringpoint.PcmmeasuringpointFactory;
import org.palladiosimulator.pcmmeasuringpoint.PcmmeasuringpointPackage;
import static org.mockito.Matchers.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;


public class MeasuringPointSelectionWizardModelTest {
	
	@Mock
	private Monitor monitor;
	private boolean isEditing;
	private boolean finishable = true;
	@Rule public MockitoRule mockitoRule = MockitoJUnit.rule();

	@Test
	public void testIsFinishable() {
		MeasuringPointSelectionWizardModel measuringPointSelectionMock = Mockito.mock(MeasuringPointSelectionWizardModel.class);
		Boolean finishable = measuringPointSelectionMock.isFinishable();
		when(measuringPointSelectionMock.isFinishable()).thenReturn(finishable);
		//assertTrue(true);
	}

	@Test
	public void testSetFinishable() {
		MeasuringPointSelectionWizardModel measuringPointSelectionMock = Mockito.mock(MeasuringPointSelectionWizardModel.class);
		Mockito.doCallRealMethod().when(measuringPointSelectionMock).setFinishable(true);
			
	}

	@Test
	public void testIsEditing() {
		MeasuringPointSelectionWizardModel measuringPointSelectionMock = Mockito.mock(MeasuringPointSelectionWizardModel.class);
		Boolean editable = measuringPointSelectionMock.isEditing();
		when(measuringPointSelectionMock.isFinishable()).thenReturn(isEditing);
		
	}

	@Test
	public void testSetEditing() {
		MeasuringPointSelectionWizardModel setEditingMock = Mockito.mock(MeasuringPointSelectionWizardModel.class);
		Mockito.doCallRealMethod().when(setEditingMock).setEditing(isEditing);
			
	}

	@Test
	public void testMeasuringPointSelectionWizardModel() {
	//constructor	
	}

	@Test
	public void testSetMeasuringPointDependingOnEditMode() {
		//not tested
	}

	@Test
	public void testCreateMeasuringPoint() {
		//not tested
	}

	@Test
	public void testAddMeasuringPointToMonitor() {
		MeasuringPointSelectionWizardModel addMeasuringPointToMonitor = Mockito.mock(MeasuringPointSelectionWizardModel.class);
		Monitor monitor = MonitorRepositoryFactory.eINSTANCE.createMonitor();
		PcmmeasuringpointPackage pcmMeasuringPointPackage = PcmmeasuringpointPackage.eINSTANCE;
		PcmmeasuringpointFactory pcmMeasuringPointFactory = pcmMeasuringPointPackage.getPcmmeasuringpointFactory();
		//Mockito.verify(addMeasuringPointToMonitor).addMeasuringPointToMonitor();
	}

	@Test
	public void testAddMeasuringPointToRepository() {
		fail("Not yet implemented");
	}

	@Test
	public void testCanFinish() {
		MeasuringPointSelectionWizardModel measuringPointSelectionMock = mock(MeasuringPointSelectionWizardModel.class);
		Monitor monitor = MonitorRepositoryFactory.eINSTANCE.createMonitor();
		boolean finishable =  measuringPointSelectionMock.canFinish();
		when(monitor.getMeasuringPoint() != null).thenReturn(finishable);
		//assertTrue(true);
	}

	@Test
	public void testGetInfoText() {
		MeasuringPointSelectionWizardModel measuringPointSelectionMock = mock(MeasuringPointSelectionWizardModel.class);
		Monitor monitor = MonitorRepositoryFactory.eINSTANCE.createMonitor();
		String infoText = measuringPointSelectionMock.getInfoText();
		
		when(measuringPointSelectionMock.getInfoText()).thenAnswer(new Answer() {
			private static final String CREATE_MEASURINGPOINT_INFO_TEXT = "Select the element of your Models which should be "
					+ "monitored during a simulation run. Models for which a measuring point can be created are highlighted with a bold font.";
			private static final String EDIT_MEASURINGPOINT_INFO_TEXT = "Select a different measuring Point.";       

			@Override
			public Object answer(InvocationOnMock invocation) throws Throwable {
				if(monitor.getMonitorRepository() != null) 
					return EDIT_MEASURINGPOINT_INFO_TEXT;
				
				return CREATE_MEASURINGPOINT_INFO_TEXT;
			}
			
		});
	}

	@Test
	public void testNextStep() {
		//Not tested
	}

	@Test
	public void testGetTitleText() {
		MeasuringPointSelectionWizardModel measuringPointSelectionMock = mock(MeasuringPointSelectionWizardModel.class);
		Monitor monitor = MonitorRepositoryFactory.eINSTANCE.createMonitor();
		String titleText = measuringPointSelectionMock.getInfoText();
		when(measuringPointSelectionMock.getInfoText()).thenAnswer(new Answer() {
			private static final String CREATE_MEASURINGPOINT_TITLE = "Create Measuring Point";
			private static final String EDIT_MEASURINGPOINT_TITLE = "Edit Measuring Point";
			@Override
			public Object answer(InvocationOnMock invocation) throws Throwable {
				if(monitor.getMeasuringPoint() != null) 
					return CREATE_MEASURINGPOINT_TITLE;
				
				return EDIT_MEASURINGPOINT_TITLE;
			}
			
		});
	}

	@Mock
	public List<AssemblyContext> getAssemblyContexts;
	public @Mock AssemblyContext assemblyContexts;
	

	@Test
	public void testGetAssemblyContexts() {
		//DataApplication da = DataApplication.getInstance();
		//MeasuringPointSelectionWizardModel measuringPointSelectionMock = mock(MeasuringPointSelectionWizardModel.class);
//		when(getAssemblyContexts.get(0))
//				.thenReturn((AssemblyContext) da.getModelAccessor()
//				.getSystem()
//				.stream()
//				.flatMap(e -> e.getAssemblyContexts__ComposedStructure()
//				.stream())
//				.collect(Collectors.toCollection(LinkedList::new)));				
		when(getAssemblyContexts.get(anyInt())).thenReturn(assemblyContexts);
	}

	public @Mock List<ResourceContainer> getResourceContainer;
	public @Mock ResourceContainer resourceContainer;
	
	@Test
	public void testGetResourceContainer() {
		when(getResourceContainer.get(anyInt())).thenReturn(resourceContainer);
	}
	
	public @Mock List<ProcessingResourceSpecification> getActiveResources;
	public @Mock ProcessingResourceSpecification activeResources;
	@Test
	public void testGetActiveResources() {
		when(getActiveResources.get(anyInt())).thenReturn(activeResources);
	}
	public @Mock List<LinkingResource> getLinkingResources;
	public @Mock LinkingResource linkingResource;
	@Test
	public void testGetLinkingResources() {
		when(getLinkingResources.get(anyInt())).thenReturn(linkingResource);
	}
	
	public @Mock List<UsageScenario> getUsageScenarios;
	public @Mock UsageScenario usageScenario;
	@Test
	public void testGetUsageScenarios() {
		when(getUsageScenarios.get(anyInt())).thenReturn(usageScenario);
	}

	@Test
	public void testGetEntryLevelSystemCalls() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetExternalCallActions() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetComponents() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetSeffs() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetSignatures() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetAllSecondPageObjects() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetExistingMeasuringPoints() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddOnlyFilledLists() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetAllAdditionalModels() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetCurrentSelection() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetCurrentSelection() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetMonitor() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetMonitor() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetCurrentSecondStageModel() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetCurrentSecondStageModel() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetCurrentThirdStageModel() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetCurrentThirdStageModel() {
		fail("Not yet implemented");
	}

}
