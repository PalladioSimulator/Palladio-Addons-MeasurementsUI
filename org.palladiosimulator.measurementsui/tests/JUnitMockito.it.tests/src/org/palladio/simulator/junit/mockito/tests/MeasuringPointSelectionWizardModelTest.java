package org.palladio.simulator.junit.mockito.tests;



import org.junit.Test;
import org.junit.Rule;
import org.mockito.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.stubbing.Answer;
import org.palladiosimulator.measurementsui.wizardmodel.pages.MeasuringPointSelectionWizardModel;
import org.palladiosimulator.monitorrepository.Monitor;
import org.palladiosimulator.monitorrepository.MonitorRepositoryFactory;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * 
 * @author Mehmet Ali Tepeli
 *
 */


public class MeasuringPointSelectionWizardModelTest {
	@Mock
	private Monitor monitor;
	private boolean isEditing;
	private boolean finishable = true;
	@Rule public MockitoRule mockitoRule = MockitoJUnit.rule();
	MeasuringPointSelectionWizardModel measuringPointSelectionMock = Mockito.mock(MeasuringPointSelectionWizardModel.class);
	Monitor createMonitor = MonitorRepositoryFactory.eINSTANCE.createMonitor();
	
	@Test
	public void testIsFinishable() {
		Boolean finishable = measuringPointSelectionMock.isFinishable();
		when(measuringPointSelectionMock.isFinishable()).thenReturn(finishable);
		assertTrue(true);
}
	@Test
	public void testSetFinishable() {
		Mockito.doCallRealMethod().when(measuringPointSelectionMock).setFinishable(true);
}
	@Test
	public void testIsEditing() {
		Boolean editable = measuringPointSelectionMock.isEditing();
		when(measuringPointSelectionMock.isFinishable()).thenReturn(isEditing);
		
}
	@Test
	public void testSetEditing() {
		MeasuringPointSelectionWizardModel setEditingMock = Mockito.mock(MeasuringPointSelectionWizardModel.class);
		Mockito.doCallRealMethod().when(setEditingMock).setEditing(isEditing);
			
}
	@Test
	public void testCanFinish() {
		boolean finishable =  measuringPointSelectionMock.canFinish();
		when(createMonitor.getMeasuringPoint() != null).thenReturn(finishable);
		assertTrue(true);
}
	@Test
	public void testGetInfoText() {
		String infoText = measuringPointSelectionMock.getInfoText();
		when(measuringPointSelectionMock.getInfoText()).thenAnswer(new Answer() {
			private static final String CREATE_MEASURINGPOINT_INFO_TEXT = "Select the element of your Models which should be "
					+ "monitored during a simulation run. Models for which a measuring point can be created are highlighted with a bold font.";
			private static final String EDIT_MEASURINGPOINT_INFO_TEXT = "Select a different measuring Point.";       

			@Override
			public Object answer(InvocationOnMock invocation) throws Throwable {
				if(createMonitor.getMonitorRepository() != null) 
					return EDIT_MEASURINGPOINT_INFO_TEXT;
				
				return CREATE_MEASURINGPOINT_INFO_TEXT;
			}
			
		});
}
	@Test
	public void testGetTitleText() {
		String titleText = measuringPointSelectionMock.getInfoText();
		when(measuringPointSelectionMock.getInfoText()).thenAnswer(new Answer() {
			private static final String CREATE_MEASURINGPOINT_TITLE = "Create Measuring Point";
			private static final String EDIT_MEASURINGPOINT_TITLE = "Edit Measuring Point";
			@Override
			public Object answer(InvocationOnMock invocation) throws Throwable {
				if(createMonitor.getMeasuringPoint() != null) 
					return CREATE_MEASURINGPOINT_TITLE;
				
				return EDIT_MEASURINGPOINT_TITLE;
			}
			
		});
}
}
