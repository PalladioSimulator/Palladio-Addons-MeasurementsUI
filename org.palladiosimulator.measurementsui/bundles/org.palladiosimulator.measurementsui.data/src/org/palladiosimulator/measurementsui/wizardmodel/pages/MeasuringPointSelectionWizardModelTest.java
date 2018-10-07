package org.palladiosimulator.measurementsui.wizardmodel.pages;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.*;
import org.mockito.BDDMockito.Then;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.palladiosimulator.monitorrepository.Monitor;
import org.palladiosimulator.monitorrepository.MonitorRepositoryFactory;
import static org.mockito.Mockito.when;

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
		fail("Not yet implemented");
	}

	@Test
	public void testIsEditing() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetEditing() {
		fail("Not yet implemented");
	}

	@Test
	public void testMeasuringPointSelectionWizardModel() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetMeasuringPointDependingOnEditMode() {
		fail("Not yet implemented");
	}

	@Test
	public void testCreateMeasuringPoint() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddMeasuringPointToMonitor() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddMeasuringPointToRepository() {
		fail("Not yet implemented");
	}

	@Test
	public void testCanFinish() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetInfoText() {
		fail("Not yet implemented");
	}

	@Test
	public void testNextStep() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetTitleText() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetAssemblyContexts() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetResourceContainer() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetActiveResources() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetLinkingResources() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetUsageScenarios() {
		fail("Not yet implemented");
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
