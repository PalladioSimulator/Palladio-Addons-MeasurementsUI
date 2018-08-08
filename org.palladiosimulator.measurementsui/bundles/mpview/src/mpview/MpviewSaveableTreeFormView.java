package mpview;


import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.edit.ui.view.ExtendedPropertySheetPage;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.IPropertySheetPage;
import org.palladiosimulator.monitorrepository.util.MonitorRepositoryAdapterFactory;

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
	
	/**
	 * Code um das selektierte EMF-Objekt im Properties View anzuzeigen. Es funktioniert momentan noch nicht, da kein Adapter vom Typ IPropertySheetPage
	 * die Funktion aufruft. Ich vermute, dass man hier noch einen Extensionpoint dem Projekt hinzuf�gen muss, habe aber nichts gefunden was funktioniert.
	 */
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
		return super.getAdapter(adapter);
	}
	
	

}
