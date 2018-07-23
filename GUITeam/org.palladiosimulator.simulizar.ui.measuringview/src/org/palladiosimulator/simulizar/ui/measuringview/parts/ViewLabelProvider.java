package org.palladiosimulator.simulizar.ui.measuringview.parts;

import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.swt.graphics.Image;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.palladiosimulator.simulizar.ui.measuringview.data.MeasurementList;
import org.palladiosimulator.simulizar.ui.measuringview.data.Monitor;
import org.palladiosimulator.simulizar.ui.measuringview.data.TreeContentItem;

class ViewLabelProvider extends LabelProvider implements IStyledLabelProvider {

    private final String ICON_LOCATION = "icons/ActiveIcon.png";
    private ImageDescriptor directoryImage;
    private ResourceManager resourceManager;

    public ViewLabelProvider() {
        this.directoryImage = createImageDescriptor();
    }
    
    private ImageDescriptor createImageDescriptor() {
		Bundle bundle = FrameworkUtil.getBundle(ViewLabelProvider.class);
        URL url = FileLocator.find(bundle, new Path(ICON_LOCATION), null);
        return ImageDescriptor.createFromURL(url);
	}

    @Override
    public StyledString getStyledText(Object element) {
        if (element instanceof TreeContentItem) {
        	TreeContentItem treeItem = (TreeContentItem) element;
            StyledString styledString = new StyledString(treeItem.toString());
            TreeContentItem[] treeItems = treeItem.getChildren();
            if (treeItems != null && treeItem instanceof MeasurementList) {
                styledString.append(" ( " + treeItems.length + " ) ",
                        StyledString.COUNTER_STYLER);
            }
            return styledString;
        }
        return null;
    }

    @Override
    public Image getImage(Object element) {
        if (element instanceof Monitor) {
            if (((Monitor) element).getStatus().equals(Monitor.Status.ACTIVE)) {
                return getResourceManager().createImage(directoryImage);
            }
        }
        return super.getImage(element);
    }

    @Override
    public void dispose() {
        // garbage collect system resources
        if (resourceManager != null) {
            resourceManager.dispose();
            resourceManager = null;
        }
    }

    protected ResourceManager getResourceManager() {
        if (resourceManager == null) {
            resourceManager = new LocalResourceManager(JFaceResources.getResources());
        }
        return resourceManager;
    }
}
