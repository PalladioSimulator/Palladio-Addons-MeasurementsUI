package de.unistuttgart.enpro.wizard.handlers;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

public final class MeasurementSpecLabelProvider extends LabelProvider implements ITableLabelProvider {

    public Image getColumnImage(Object element, int columnIndex) {
        return null;
    }

    public String getColumnText(Object element, int columnIndex) {
        Measurement data = (Measurement) element;
        switch (columnIndex) {
        case 0:
            return data.getName();
        case 1:
            return data.getType().toString();
        case 2:
            return String.valueOf(data.getProperty1());
        case 3:
            return String.valueOf(data.getProperty2());
        default:
            return "";
        }
    }

}
