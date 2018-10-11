package org.palladiosimulator.swtbot;

import java.io.File;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotView;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * 
 * @author Birasanth Pushpanathan
 * 
 */

@RunWith(SWTBotJunit4ClassRunner.class)
public class DeleteMonitor {


    public static SWTWorkbenchBot bot = new SWTWorkbenchBot();

    @BeforeClass
    public static void beforeClass() throws Exception {
        bot = new SWTWorkbenchBot();
        closeWelcomePage();
        bot.menu("File").menu("Import...").click();
        SWTBotShell importshell = bot.shell("Import");
        importshell.activate();

        bot.tree().select("General").expandNode("General").select("Existing Projects into Workspace");
        bot.button("Next >").click();
        bot.radio("Select root directory:").click();
        String basePath = new File("").getAbsolutePath();
        String path = new File("/testProject/Pets.com")
                .getAbsolutePath();
        bot.comboBox().setText(basePath+path);        bot.button("Refresh").click();
        bot.button("Finish").click();
        bot.closeAllShells();

    }
    
    @Test
    public void canOpenWizard() throws Exception {
        bot.menu("Window").menu("Show View").menu("Other...").click();

        SWTBotShell shell = bot.shell("Show View");
        shell.activate();

        SWTBotTree tree = bot.tree();
        for (SWTBotTreeItem item : tree.getAllItems()) {
            if ("SimuLizar".equals(item.getText())) {

                item.expand();
                for (SWTBotTreeItem element : item.getItems()) {
                    if ("Measurements Dashboard".equals(element.getText())) {

                        element.select();
                        bot.button("Open").click();
                    }
                }
            }
        }

    }
    
    @Test
    
    public void deleteMonitor() throws Exception {
        bot = new SWTWorkbenchBot();
        SWTBotView measuringpointView = bot.viewById("org.palladiosimulator.measurementsui.dashboardview");
        Widget measuringpointViewWidget = measuringpointView.getWidget();
        Composite measuringpointViewComposite = (Composite) measuringpointView.getWidget();
        Tree monitorTree = (Tree) bot.widget(WidgetMatcherFactory.widgetOfType(Tree.class),
                measuringpointViewComposite);
        SWTBotTree tree = new SWTBotTree(monitorTree);
        tree.select("Monitor Repository PetsMonitore");
        tree.expandNode("Monitor Repository PetsMonitore");
        tree.getTreeItem("Monitor Repository PetsMonitore").getNode("ImageServerCPU [ResourceContainerMeasuringPoint]").select();
        bot.button("Delete Monitor").click();
    }
    
    public static void closeWelcomePage() {
        for (SWTBotView view : bot.views()) {
            if(view!=null) {
                if (view.getTitle().equals("Welcome")) {
                    view.close();
                }
            }
        }
    }

}
