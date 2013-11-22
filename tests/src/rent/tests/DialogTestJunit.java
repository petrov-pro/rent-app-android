/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rent.tests;

import junit.framework.TestCase;

/**
 *
 * @author petroff
 */
public class DialogTestJunit extends TestCase {

	protected double fValue1;
	protected double fValue2;

	protected void setUp() {
		fValue1 = 2.0;
		fValue2 = 3.0;
	}

	public void testAdd() {
		double result = fValue1 + fValue2;
		assertTrue(result == 5.0);
	}
}
