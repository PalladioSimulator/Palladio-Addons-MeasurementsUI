package mptree;

import org.eclipse.emf.parsley.composite.TreeFormComposite;
import org.eclipse.emf.parsley.composite.TreeFormFactory;
import org.eclipse.emf.parsley.viewers.ViewerFactory;
import org.eclipse.emf.parsley.views.AbstractSaveableViewerView;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import com.google.inject.Inject;

/**
 * 
 * @author David Sch�tz
 * Abstrakte Klasse f�r den MpTreeViewer. Normalerweise muss man die nicht selber schreiben, 
 * sondern man erbt wie einfach von import org.eclipse.emf.parsley.views.SaveableTreeFormView.
 * Ich hab das hier einfach mal gemacht, um theorethisch dem TreeViewer Checkboxen geben zu k�nnen
 * und um einen MouseListener hinzuf�gen. Wobei letzeres auch sicher einfacher geht.
 *
 */
public abstract class AbstractMpTreeViewer extends AbstractSaveableViewerView {

	@Inject
	private ViewerFactory viewerFactory;

	private TreeViewer treeViewer;

	protected Object getContents() {
		return getContents(getResource());
	}

	protected Object getContents(Resource resource) {
		return resource;
	}

	@Override
	public void createPartControl(Composite parent) {
		super.createPartControl(parent);

		treeViewer = createAndInitializeTreeViewer(parent);

		afterCreateViewer();
	}

	protected TreeViewer createAndInitializeTreeViewer(Composite parent) {
		TreeViewer viewer = new TreeViewer(parent);
		getViewerFactory().initialize(viewer, getContents());
		return viewer;
	}

	protected ViewerFactory getViewerFactory() {
		return viewerFactory;
	}
	
	@Override
	public void setFocus() {
		treeViewer.getTree().setFocus();
	}

	@Override
	public StructuredViewer getViewer() {
		return treeViewer;
	}
}
