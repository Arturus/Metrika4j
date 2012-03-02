package ru.metrika4j;

import junit.framework.Assert;
import junit.framework.TestCase;
import org.junit.Test;

/**
 * @author Artur
 * @version $Id$
 */
public class MetrikaDateTest extends TestCase {
    @Test
    public void testDiffDayPeriods() throws Exception {
        MetrikaDate date1 = new MetrikaDate(2,3,2012);
        MetrikaDate date2 = new MetrikaDate(22,2,2012);
        int days = date2.diffDayPeriods(date1);
        Assert.assertEquals(9, days);
    }
}
