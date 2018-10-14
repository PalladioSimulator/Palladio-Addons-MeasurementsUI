package org.palladio.simulator.junit.mockito.tests;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.palladiosimulator.measurementsui.dataprovider.ProcessingTypeProvider;
import org.palladiosimulator.measurementsui.wizardmodel.pages.ProcessingTypeSelectionWizardModel;
import org.palladiosimulator.monitorrepository.Monitor;


public class ProcessingTypeSelectionWizardModelTest {

	@Mock  
	private Monitor usedMetricsMonitor;
	private boolean isEditting;
	private ProcessingTypeProvider provider;
	ProcessingTypeSelectionWizardModel test = mock(ProcessingTypeSelectionWizardModel.class);
	
	@Test
	public void testGetUsedMetricsMonitor() {
		when(test.getUsedMetricsMonitor()).thenReturn(usedMetricsMonitor);
	}
	
	@Test
	public void testGetInfoText() {
		String infoText = test.getInfoText();
		when(test.getInfoText()).thenAnswer(new Answer() {
			@Mock
			   private static final String INFORMATION_MESSAGE = "Please select a Processing Type for each Metric Description."
			    		+ "\nA Processing Type defines how the measurements are computed during a simulation. "
			    		+ "Choose a different type than FeedThrough, if the measurements should "
			    		+ "be aggregated for the simulation results.";
			@Override
			public Object answer(InvocationOnMock invocation) throws Throwable {
				return INFORMATION_MESSAGE;
			}
		});
	}
	
	@Test
	public void testGetTitleText() {
		String titleText = test.getTitleText();
		when(test.getTitleText()).then(new Answer() {
			@Mock
			private static final String PROCESSING_TYPE_TITLE = "Select Processing Types";
			@Override
			public Object answer(InvocationOnMock invocation) throws Throwable {
				return PROCESSING_TYPE_TITLE;
			}
		});
	}
}
