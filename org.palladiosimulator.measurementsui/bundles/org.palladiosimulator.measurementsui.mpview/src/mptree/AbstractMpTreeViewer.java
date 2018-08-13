package mptree;

import org.eclipse.emf.parsley.composite.TreeFormComposite;
import org.eclipse.emf.parsley.composite.TreeFormFactory;

import org.eclipse.emf.parsley.views.AbstractSaveableViewerView;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import com.google.inject.Inject;

/**
 * 
 * @author David Schütz
 * Abstrakte Klasse für den MpTreeViewer. Normalerweise muss man die nicht selber schreiben, 
 * sondern man erbt wie einfach von import org.eclipse.emf.parsley.views.SaveableTreeFormView.
 * Ich hab das hier einfach mal gemacht, um theorethisch dem TreeViewer Checkboxen geben zu können
 * und um einen MouseListener hinzufügen. Wobei letzeres auch sicher einfacher geht.
 *
 */
public abstract class AbstractMpTreeViewer extends AbstractSaveableViewerView {

	@Inject
	private TreeFormFactory treeFormFactory;

	private TreeFormComposite treeFormComposite;

	protected Object getContents(Resource resource) {
		return resource;
	}

	@Override
	public void createPartControl(Composite parent) {
		super.createPartControl(parent);

		treeFormComposite = treeFormFactory
				.createTreeFormComposite(parent, SWT.BORDER);

		treeFormComposite.update(getContents(getResource()));

		afterCreateViewer();
	}

	public void forceReloadResource(){
		treeFormComposite.update(getContents(getResource()));
	}

	@Override
	public void setFocus() {
		treeFormComposite.setFocus();
	}

	@Override
	public StructuredViewer getViewer() {
		return treeFormComposite.getViewer();
	}
	
	@Override
	public void addMouseListenerToViewer() {
		super.addMouseListenerToViewer();
		getViewer().getControl().addMouseListener(new MpTreeDoubleClickListener((TreeViewer) getViewer()));
	}

}
