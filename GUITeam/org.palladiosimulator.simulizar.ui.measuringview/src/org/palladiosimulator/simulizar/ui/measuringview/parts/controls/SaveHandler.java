package org.palladiosimulator.simulizar.ui.measuringview.parts.controls;

import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.e4.ui.workbench.modeling.EPartService;

/*
 * Klasse zum Speichern des EMF Modells. Leider weiß ich nicht wie man den SaveCommand den E4 Plugin hinzufügt, sodass beim Speichern
 * die execute Funktion ausgeführt wird. So ist die Klasse momentan unbenutzt. Mehr dazu steht in Adding the dirty state and Save command
 * in der Parsley Doku.
 * @author DavidPC2
 *
 */
public class SaveHandler {
	@Execute
	void execute(EPartService partService, @Named(IServiceConstants.ACTIVE_PART) MPart part) {
		partService.savePart(part, false);
	}
}
