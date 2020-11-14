/**
 * 
 */
package auctionhouse;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import org.junit.Test;

/**
 * @author pbj
 *
 */
public class MoneyTest {

    @Test    
    public void testAdd() {
        Money val1 = new Money("12.34");
        Money val2 = new Money("0.66");
        Money result = val1.add(val2);
        assertEquals("13.00", result.toString());
    }

    /*
     ***********************************************************************
     * BEGIN MODIFICATION AREA
     ***********************************************************************
     * Add all your JUnit tests for the Money class below.
     */
    
    @Test
    public void testSubtract() {
        Money val1 = new Money("19.78");
        Money val2 = new Money("0.13");
        Money result = val1.subtract(val2);
        assertEquals("19.65", result.toString());
    }
    
    @Test
    public void testAddPercent() {
    	    Money val1 = new Money("40.00");
    	    Money result = val1.addPercent(50.00);
    	    assertEquals("60.00", result.toString());
    }
    
    @Test
    public void testToString() {
	    Money val1 = new Money("40.3928347");
	    String result = val1.toString();
	    assertEquals("40.39", result);
    }
    
    @Test
    public void testCompareTo() {
        Money val1 = new Money("11.11");
        Money val2 = new Money("22.22");
        Money val3 = new Money("22.22");
        
        assertTrue(val1.compareTo(val2) < 0);
        assertTrue(val2.compareTo(val1) > 0);
        assertEquals(0, val2.compareTo(val3));

    }
    
    @Test
    public void testLessEqual() {
        Money val1 = new Money("78.77");
        Money val2 = new Money("38.95");
        Money result = val1.subtract(val2);
        assertTrue(val2.lessEqual(val1));
        assertFalse(val1.lessEqual(val2));
    }
    
    @Test
    public void testEquals() {
        Money val1 = new Money("34.87");
        Money val2 = new Money("97.35");
        Money val3 = new Money("97.35");
        assertTrue(val2.equals(val3));
        assertFalse(val1.equals(val2));
        assertFalse(val1.equals(val3));
    }
    
    @Test
    public void testHashCode() {
        Money val1 = new Money("11.11");
        assertEquals(1111, val1.hashCode());
    }


    /*
     * Put all class modifications above.
     ***********************************************************************
     * END MODIFICATION AREA
     ***********************************************************************
     */


}
