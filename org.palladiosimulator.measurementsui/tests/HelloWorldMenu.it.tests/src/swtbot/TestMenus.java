package swtbot;

import static org.junit.Assert.assertEquals;

import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotMenu;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestMenus {

	private static SWTBot bot;

    @BeforeClass
    public static void beforeClass() throws Exception {
        // don't use SWTWorkbenchBot here which relies on Platform 3.x
        bot = new SWTBot();
    }

    @Test
    public void ensureSaveIsDisabledWhenNothingIsDirty() {
       assertEquals(1,1);

       
    }
}
