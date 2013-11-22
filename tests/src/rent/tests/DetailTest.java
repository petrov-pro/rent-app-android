package rent.tests;



import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;
import ua.org.rent.controller.Detail;

public class DetailTest extends
		ActivityInstrumentationTestCase2<Detail> {

	private EditText editKmPerHour;
	private EditText editMeterPerSec;
	private Detail activity;

	public DetailTest() {
		super("ua.org.rent.controller", Detail.class);
	}

	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		super.setUp();
		activity = getActivity();

	}

	public void testControlsCreated() {
		assertNotNull(activity);
	}

}
