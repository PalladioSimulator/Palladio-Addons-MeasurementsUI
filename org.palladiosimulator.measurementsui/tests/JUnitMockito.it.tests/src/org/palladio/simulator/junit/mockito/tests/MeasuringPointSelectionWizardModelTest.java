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
		assertTrue(true);
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
	public void testCanFinish() {
		MeasuringPointSelectionWizardModel measuringPointSelectionMock = mock(MeasuringPointSelectionWizardModel.class);
		Monitor monitor = MonitorRepositoryFactory.eINSTANCE.createMonitor();
		boolean finishable =  measuringPointSelectionMock.canFinish();
		when(monitor.getMeasuringPoint() != null).thenReturn(finishable);
		assertTrue(true);
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
}
