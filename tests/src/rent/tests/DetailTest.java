package rent.tests;



import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.widget.Button;
import android.widget.EditText;
import static junit.framework.Assert.assertNotNull;
import ua.org.rent.controller.Tab;
import ua.org.rent.R;

public class DetailTest extends
		ActivityInstrumentationTestCase2<Tab> {

	private EditText editKmPerHour;
	private EditText editMeterPerSec;
	private Tab activity;

	public DetailTest() {
		super("ua.org.rent.controller", Tab.class);
	}

	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		super.setUp();
		activity = getActivity();

	}

	public void testControlsCreated() {
		assertNotNull(activity);
//		Button searchButton = (Button) activity.findViewById(R.id.btSearch);
//		assertNotNull(searchButton);
//		//TouchUtils.clickView(this, searchButton);
	}

}
