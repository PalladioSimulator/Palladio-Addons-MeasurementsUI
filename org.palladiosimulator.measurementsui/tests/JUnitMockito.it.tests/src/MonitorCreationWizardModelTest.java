import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.palladiosimulator.measurementsui.wizardmodel.pages.MonitorCreationWizardModel;
import org.palladiosimulator.monitorrepository.Monitor;
import org.palladiosimulator.monitorrepository.MonitorRepositoryFactory;
import org.palladiosimulator.monitorrepository.impl.MonitorRepositoryFactoryImpl;
import static org.mockito.Mockito.*;

import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.stubbing.Answer;
public class MonitorCreationWizardModelTest {

	@Mock
	private Monitor monitor;
	private boolean isEditting;
    @Rule public MockitoRule mockitoRule = MockitoJUnit.rule();
   
 
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
		when(test.getInfoText()).thenAnswer(new Answer(){
			 	@Mock
			    private static final String EDIT_MONITOR_INFO_TEXT = "Edit your Monitor name and set him activated/not activated.";
				@Mock
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
			
	
		
	}

