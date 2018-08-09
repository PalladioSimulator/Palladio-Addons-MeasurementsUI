package org.palladiosimulator.unittests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import helloworldmenu.handlers.*;

import org.junit.Test;

public class ExampleTest {
	@Test
    public void test() {
        // just an example
        assertTrue(true);
    }
	
	@Test
    public void test2() {
        int testing = helloworldmenu.handlers.SampleHandler.test("test1");
        assertEquals(1, testing);
    }
}
