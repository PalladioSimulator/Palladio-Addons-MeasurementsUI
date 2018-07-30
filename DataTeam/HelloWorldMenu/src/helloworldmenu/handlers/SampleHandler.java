package helloworldmenu.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

import init.StartApplication;




public class SampleHandler extends AbstractHandler {


	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		
		StartApplication start = StartApplication.getInstance();
		
		start.startApplication();
		
		
		
		
		
		// test for any available session
//		for (Session session : SessionManager.INSTANCE.getSessions()) {
//			System.out.println(session.getID());
//			for (Resource resource : session.getSemanticResources()) {
//				System.out.println(resource.getURI());
//				System.out.println(resource.toString());
//			}
//		}


			
		return null;
	}
}
