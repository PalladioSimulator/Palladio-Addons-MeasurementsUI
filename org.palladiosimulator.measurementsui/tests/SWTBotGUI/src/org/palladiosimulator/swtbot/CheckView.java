package org.palladiosimulator.swtbot;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotView;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotViewMenu;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * 
 * @author Birasanth Pushpanathan
 * 
 */

@RunWith(SWTBotJunit4ClassRunner.class)
public class CheckView {

    public static SWTWorkbenchBot bot = new SWTWorkbenchBot();

    @BeforeClass
    public static void beforeClass() throws Exception {
        bot = new SWTWorkbenchBot();
        bot.viewByTitle("Welcome").close();
        bot.menu("File").menu("Import...").click();
        SWTBotShell importshell = bot.shell("Import");
        importshell.activate();
        
        bot.tree().select("General").expandNode("General").select("Existing Projects into Workspace");
        bot.button("Next >").click();
        bot.radio("Select root directory:").click();
        bot.comboBox().setText("/Users/birasanthpushpanathan/Downloads/Pets.com");
        bot.button("Refresh").click();
        bot.button("Finish").click();
        bot.closeAllShells();

    }

    @Test
    public void canOpenWizard() throws Exception {
        bot.menu("Window").menu("Show View").menu("Other...").click();

        SWTBotShell shell = bot.shell("Show View");
        shell.activate();

        bot.tree().select("Sample Category").expandNode("Sample Category").select("Measurements Overview");
        bot.button("Open").click();
        bot.closeAllShells();

    }
    
    @Test
    public void checkButtons()throws Exception{
        bot = new SWTWorkbenchBot();
        SWTBotView measuringpointView =  bot.viewById("test.partDescFragment.ASampleE4View");
        Widget measuringpointViewWidget = measuringpointView.getWidget();
        bot.activeView();
        bot.button("Add new Measuring Point");
        bot.button("Delete...");
        bot.button("Edit...");
        bot.button("Assign to Monitor");
        bot.button("Create Standard Set");
    }
    
    @Test
    public void checkMonitorTree() throws Exception{
        bot = new SWTWorkbenchBot();
        SWTBotView measuringpointView =  bot.viewById("test.partDescFragment.ASampleE4View");
        Widget measuringpointViewWidget = measuringpointView.getWidget();
        Composite measuringpointViewComposite = (Composite) measuringpointView.getWidget();
        Tree monitorTree = (Tree)bot.widget(WidgetMatcherFactory.widgetOfType(Tree.class),measuringpointViewComposite);
        SWTBotTree tree = new SWTBotTree(monitorTree);
        tree.select("Monitor Repository PetsMonitore");
        tree.expandNode("Monitor Repository PetsMonitore");
    }
    @Test
    public void checkEmptyMeasurementsTree() throws Exception {
        bot = new SWTWorkbenchBot();
        SWTBotView measurmentView =  bot.viewById("test.partDescFragment.ASampleE4View");
        Widget measurmentViewWidget = measurmentView.getWidget();
        Composite measurmentViewComposite = (Composite) measurmentView.getWidget();
        Tree measurmentTree = (Tree)bot.widget(WidgetMatcherFactory.widgetOfType(Tree.class),measurmentViewComposite);
        SWTBotTree mtree = new SWTBotTree(measurmentTree);
    }
    
    @AfterClass
    public static void afterClass() throws Exception{
        bot.closeAllShells();
    }
}
