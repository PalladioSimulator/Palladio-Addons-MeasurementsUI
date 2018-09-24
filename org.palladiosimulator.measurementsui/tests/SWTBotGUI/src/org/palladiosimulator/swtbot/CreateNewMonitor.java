package org.palladiosimulator.swtbot;

import static org.eclipse.swtbot.swt.finder.SWTBotAssert.pass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotView;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.eclipse.swtbot.swt.finder.matchers.WithText;
import org.eclipse.swtbot.swt.finder.waits.Conditions;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotCheckBox;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotText;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
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
        bot.comboBox().setText("/Users/birasanthpushpanathan/Downloads/Pets.com");
        bot.button("Refresh").click();
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

        bot.tree().select("Sample Category").expandNode("Sample Category").select("Measurements Overview");
        bot.button("Open").click();
        bot.closeAllShells();

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
        SWTBotCheckBox addCheck = bot.checkBoxWithLabel("Activated");
        if (addCheck.isChecked())
            addCheck.click();

        SWTBotText text = bot.textWithLabel("Name").setText("test");
        if (addCheck.isChecked() == false)
            addCheck.click();
        bot.button("Next >").click();

//        SWTBotShell measuringShell = bot.shell("Edit Measuring Point");
//        measuringShell.activate();
        SWTBotTree tree = bot.treeWithId("org.palladiosimulator.measurementsui.emptymeasuringpoints");
        tree.setFocus();
    }

    
    public static void closeWelcomePage () {
        for(SWTBotView view : bot.views()) {
            if(view.getTitle().equals("Welcome")) {
                view.close();
            }
        }
    }
    @AfterClass
    public static void afterClass() throws Exception {
        bot.sleep(2000);
        bot.closeAllShells();
    }
}
