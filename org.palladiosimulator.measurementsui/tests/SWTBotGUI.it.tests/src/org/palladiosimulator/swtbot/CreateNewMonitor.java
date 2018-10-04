package org.palladiosimulator.swtbot;

import static org.eclipse.swtbot.swt.finder.SWTBotAssert.pass;
import static org.eclipse.swtbot.swt.finder.waits.Conditions.shellCloses;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;

import org.eclipse.e4.ui.internal.workbench.E4Workbench;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotView;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.eclipse.swtbot.swt.finder.matchers.WithText;
import org.eclipse.swtbot.swt.finder.waits.Conditions;
import org.eclipse.swtbot.swt.finder.waits.ICondition;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotCheckBox;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTabItem;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotText;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.eclipse.ui.internal.dnd.SwtUtil;
import org.hamcrest.Matcher;
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
public class CreateNewMonitor {

    public static SWTWorkbenchBot bot = new SWTWorkbenchBot();

    @BeforeClass
    public static void beforeClass() throws Exception {
        closeWelcomePage();
        bot = new SWTWorkbenchBot();
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

        Matcher<Shell> shellMatcher = WithText.withTextIgnoringCase("Show View");
        // wait until the shell is opened
        bot.waitUntil(Conditions.waitForShell(shellMatcher));

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
                        bot.sleep(1500);
                    }
                }
            }
        }

//        bot.tree().select("Sample Category").expandNode("Sample Category").select("Measurements Overview");
//        bot.button("Open").click();
//        bot.waitUntil(shellCloses(shell));

    }

    @Test
    public void canAddNewMonitor() throws Exception {
        bot = new SWTWorkbenchBot();
//        SWTBotView measuringpointView = bot.viewByPartName("Measurements Overview");
        SWTBotView addView = bot.viewById("test.partDescFragment.ASampleE4View");
        assertNotNull(addView);
        Widget measuringpointViewWidget = addView.getWidget();
        bot.activeView();
        bot.button("Add new Measuring Point").click();
        SWTBotCheckBox addCheck = bot.checkBoxWithLabel("");
        if (addCheck.isChecked())
            addCheck.click();

        SWTBotText text = bot.textWithLabel("").setText("test");
        if (addCheck.isChecked() == false)
            addCheck.click();
        bot.button("Next >").click();
        Display.getDefault().syncExec(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub

            }

        });
        SWTBotTabItem tabItem = bot.tabItem("Create new measuring point");
        assertEquals("Create new measuring point", tabItem.getText());
        SWTBotTabItem tabItem2 = bot.tabItem("Select existing measuring point");
        bot.tabItem("Select existing measuring point").activate();
        assertEquals("Select existing measuring point", tabItem2.getText());
        bot.tabItem("Create new measuring point").activate().setFocus();

        SWTBotShell shell = bot.activeShell();
        shell.activate();

//        SWTBotTree tree = bot.tree().select("System");
    }

    public static void closeWelcomePage() {
        for (SWTBotView view : bot.views()) {
            if (view!=null &&view.getTitle().equals("Welcome")) {
                view.close();
            }
        }
    }

    @AfterClass
    public static void afterClass() throws Exception {
        bot.sleep(2000);
//        bot.closeAllShells();
    }
}
