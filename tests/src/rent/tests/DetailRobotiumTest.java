/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rent.tests;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import com.jayway.android.robotium.solo.Solo;
import static junit.framework.Assert.assertNotNull;
import ua.org.rent.controller.Result;
import ua.org.rent.controller.Tab;

/**
 *
 * @author petroff
 */
public class DetailRobotiumTest extends ActivityInstrumentationTestCase2<Tab> {

	private Solo solo;
	Activity tab;

	public DetailRobotiumTest() {
		super(Tab.class);
	}

	public void setUp() throws Exception {
		tab = getActivity();
		solo = new Solo(getInstrumentation(), tab);
	}

	public void testCheckDetail() throws Exception {
//		Button btF = (Button)solo.getView(ua.org.rent.R.id.btSearch);
//		assertNotNull(btF);
//		solo.clickOnView(btF);
		Button btD = (Button)solo.getView(ua.org.rent.R.id.btD);
		assertNotNull(btD);
		solo.clickOnView(btD);
		solo.waitForDialogToOpen(10);
		solo.clickOnText("Рио");
		
		solo.waitForActivity("Result", 100);
//		solo.assertCurrentActivity("Result", Result.class);
	}

	@Override
	public void tearDown() throws Exception {
		solo.finishOpenedActivities();
	}
}