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
