package org.palladiosimulator.simulizar.ui.measuringview.parts;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.palladiosimulator.simulizar.ui.measuringview.data.TreeContentItem;

public class MpTreeContentProvider implements ITreeContentProvider {
    @Override
    public boolean hasChildren(Object element) {
    	TreeContentItem item = (TreeContentItem) element;
        return item.hasChildren();
    }

    @Override
    public Object getParent(Object element) {
    	TreeContentItem item = (TreeContentItem) element;
        return item.getParent();
    }

    @Override
    public Object[] getElements(Object inputElement) {
        return ArrayContentProvider.getInstance().getElements(inputElement);
    }

    @Override
    public Object[] getChildren(Object parentElement) {
    	TreeContentItem item = (TreeContentItem) parentElement;
        return item.getChildren();
    }
}