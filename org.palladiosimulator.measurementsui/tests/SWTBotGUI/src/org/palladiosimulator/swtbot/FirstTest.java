package org.palladiosimulator.swtbot;

import static org.eclipse.swtbot.swt.finder.SWTBotAssert.pass;
import static org.junit.Assert.fail;

import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(SWTBotJunit4ClassRunner.class)
public class FirstTest {

    public static SWTWorkbenchBot bot = new SWTWorkbenchBot();

    @BeforeClass
    public static void beforeClass() throws Exception {
        bot = new SWTWorkbenchBot();
        bot.menu("File").menu("Import...").click();
        SWTBotShell importshell = bot.shell("Import");
        importshell.activate();
        
        bot.tree().select("General").expandNode("General").select("Existing Projects into Workspace");

    }

    @Test
    public void canOpenWizard() throws Exception {
        bot.menu("Window").menu("Show View").menu("Other...").click();

        SWTBotShell shell = bot.shell("Show View");
        shell.activate();

        bot.tree().select("Sample Category").expandNode("Sample Category").select("Measurements Overview");
        bot.button("Open").click();
    }
    
    @Test
    public void canAddNewMonitor()throws Exception{
        SWTBotShell measuringpointView = bot.shell("Measuringpoint View");
        measuringpointView.activate();
        
//        bot.button("Add new Measuring Point").click();
    }
}
