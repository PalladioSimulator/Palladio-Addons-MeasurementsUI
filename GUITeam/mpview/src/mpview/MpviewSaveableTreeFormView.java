package mpview;


import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.edit.ui.provider.PropertyDescriptor;
import org.eclipse.emf.edit.ui.provider.PropertySource;
import org.eclipse.emf.edit.ui.view.ExtendedPropertySheetPage;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySheetPage;
import org.eclipse.ui.views.properties.IPropertySource;
import org.palladiosimulator.monitorrepository.MonitorRepository;
import org.palladiosimulator.monitorrepository.MonitorRepositoryFactory;
import org.palladiosimulator.monitorrepository.util.MonitorRepositoryAdapterFactory;

import com.google.inject.Inject;

import mptree.SaveableMpTreeView;;


public class MpviewSaveableTreeFormView extends SaveableMpTreeView  {
	/**
	 * Creates the control objects of the simulizar measuring point view
	 * @param parent
	 */
	private ExtendedPropertySheetPage propertyPage;
	
	@Override
	public void createPartControl(Composite parent) {
		// TODO Auto-generated method stub
		super.createPartControl(parent);
		getSite().setSelectionProvider(this.getViewer());
	}
	
	@Override
	public <T> T getAdapter(Class<T> adapter) {
		BasicCommandStack commandStack = new BasicCommandStack();
		AdapterFactoryEditingDomain editingDomain = new AdapterFactoryEditingDomain(new MonitorRepositoryAdapterFactory(), commandStack);
		if (adapter.equals(IPropertySheetPage.class)) {
			if (propertyPage == null) {
				propertyPage = new ExtendedPropertySheetPage(editingDomain);
				propertyPage.setPropertySourceProvider(new AdapterFactoryContentProvider(new MonitorRepositoryAdapterFactory()));
			}
			return (T) propertyPage;
		}
		System.out.println("nope");
		return super.getAdapter(adapter);
	}
	
	

}
