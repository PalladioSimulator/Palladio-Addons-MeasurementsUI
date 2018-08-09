package de.unistuttgart.enpro.wizard.handlers;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

public final class SelectedMeasurementsLabelProvider extends LabelProvider implements ITableLabelProvider {

    public Image getColumnImage(Object element, int columnIndex) {
        return null;
    }

    public String getColumnText(Object element, int columnIndex) {
        Measurement data = (Measurement) element;
        switch (columnIndex) {
        case 0:
            return data.getName();
        case 1:
            if(element instanceof Measurement && element != null){
                if(((Measurement)element).isTriggerSelfAdapting()){
                    return Character.toString((char)0x2611);
                }else{
                    return Character.toString((char)0x2610);
                }
            }
            return super.getText(element);
        default:
            return "";
        }
    }

}
