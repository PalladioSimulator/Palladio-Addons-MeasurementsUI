/**
 * Datamanipulation offers methods to modify resources that already belong to an editing domain,
 * since the normal PCM methods to modify fields cause write transaction errors on existing objects.
 * Repository Creator creates repositories. Needed if the user has not yet created any repositories
 * to save new Monitors or MPs in.
 * 
 * @author Florian Nieuwenhuizen, Lasse Merz
 *
 */
package org.palladiosimulator.measurementsui.datamanipulation;
