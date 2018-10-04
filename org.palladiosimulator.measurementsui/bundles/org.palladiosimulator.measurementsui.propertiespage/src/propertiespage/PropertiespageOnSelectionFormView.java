package propertiespage;

/**
 * Parsleyview to show properties of a selected eobject in a form. This is a temporary workaround,
 * since the project will be using the eclipse properties view in later versions
 * 
 * @author David Schuetz
 *
 */
public class PropertiespageOnSelectionFormView extends org.eclipse.emf.parsley.views.OnSelectionFormView {
    @Override
    public void setFocus() {
        /*
         * In order to avoid a bug when clicking on the entity name text field of a monitor this
         * method has to be overwritten
         */
    }
}
