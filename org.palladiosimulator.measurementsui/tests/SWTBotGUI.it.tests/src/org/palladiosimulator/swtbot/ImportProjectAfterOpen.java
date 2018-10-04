package org.palladiosimulator.swtbot;

import java.io.File;

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
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
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
public class ImportProjectAfterOpen {

    public static SWTWorkbenchBot bot = new SWTWorkbenchBot();

    @BeforeClass
    public static void beforeClass() throws Exception {
        bot = new SWTWorkbenchBot();
        closeWelcomePage();
        canOpenWizard();
    }

    public static void canOpenWizard() throws Exception {
        bot.menu("Window").menu("Show View").menu("Other...").click();

        SWTBotShell shell = bot.shell("Show View");
        shell.activate();

        SWTBotTree tree = bot.tree();
        for (SWTBotTreeItem item : tree.getAllItems()) {
            if ("Sample Category".equals(item.getText())) {

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
    public void checkIfViewisReachable() throws Exception {
        bot = new SWTWorkbenchBot();
        SWTBotView measuringpointView = bot.viewById("test.partDescFragment.ASampleE4View");
        Widget measuringpointViewWidget = measuringpointView.getWidget();
        bot.button("Add new Measuring Point");
        bot.button("Delete...");
        bot.button("Edit...");
        bot.button("Create Standard Set");
      
    }

    @AfterClass
    public static void afterClass() throws Exception {
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
        if(bot.button("Finish").isEnabled()) {
            bot.button("Finish").click();
         }else {
            bot.button("Cancel").click();
        }        bot.closeAllShells();
    }

    public static void closeWelcomePage() {
        for (SWTBotView view : bot.views()) {
            if (view!=null &&view.getTitle().equals("Welcome")) {
                view.close();
            }
        }
    }
}
