package org.palladiosimulator.measurementsui.parsleyviewer;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.widgets.Composite;
import org.palladiosimulator.measurementsui.abstractviewer.WizardTableViewer;
import org.palladiosimulator.measurementsui.dataprovider.DataApplication;
import org.palladiosimulator.measurementsui.dataprovider.UnselectedMetricSpecificationsProvider;
import org.palladiosimulator.metricspec.MetricDescription;
import org.palladiosimulator.monitorrepository.Monitor;
import org.palladiosimulator.monitorrepository.MonitorRepositoryFactory;
import org.palladiosimulator.monitorrepository.MonitorRepositoryPackage;
import org.palladiosimulator.monitorrepository.impl.MonitorRepositoryFactoryImpl;

import tableform.TableformInjectorProvider;

/**
 * Generates a table view where all MeasurementSpecifications of a monitor are shown
 * @author David Schuetz
 *
 */
public class SelectMeasurementsViewer extends WizardTableViewer {

	/**
	 * 
	 * @param parent          container where the tree viewer is placed in
	 * @param dataApplication Connection to the data binding. This is needed in
	 *                        order to get the repository of the current project.
	 */
	public SelectMeasurementsViewer(Composite parent, DataApplication dataApplication) {
		super(parent, dataApplication);
	}

	@Override
	protected void initInjector() {
		this.injector = TableformInjectorProvider.getInjector();
	}

	@Override
	protected EObject getModelRepository() {
		UnselectedMetricSpecificationsProvider test = new UnselectedMetricSpecificationsProvider();
		MonitorRepositoryFactoryImpl test1 = new MonitorRepositoryFactoryImpl();
		Monitor aMon = test1.createMonitor();
		return test.createMonitorWithMissingMetricDescriptions(aMon);
	}

}