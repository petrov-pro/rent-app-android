/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.org.rent.models;

import org.json.JSONException;
import org.json.JSONObject;
import ua.org.rent.R;
import ua.org.rent.entities.SearchData;
import ua.org.rent.settings.Consts;
import ua.org.rent.settings.RentAppState;
import ua.org.rent.utils.Http;
import ua.org.rent.utils.TaskPreperDate;

/**
 *
 * @author petroff
 */
public class ResultModel {

	private SearchData searchData;
	private String message = "";
	private TaskPreperDate task;
	private String response;
	private boolean resultOperation = false;
	private JSONObject jresult;
	

	public ResultModel(SearchData searchData) {
		this.searchData = searchData;
	}

	public String getMessage() {
		return message;
	}

	synchronized public void preparationAndProcessing() {
		if (Http.hasConnection(RentAppState.getContext())) {
			getResponseFromServer();
			processingData();
			
			
			//Test Data
			message = RentAppState.getContext().getText(R.string.hasnt_connect).toString();
			resultOperation = false;
		} else {
			message = RentAppState.getContext().getText(R.string.hasnt_connect).toString();
			resultOperation = false;
		}

	}

	public boolean isResultOperation() {
		return resultOperation;
	}

	synchronized public void getResponseFromServer() {
		response = Http.connect(Consts.HOST_API);
	}

	public void processingData() {
		if (response == null || response.isEmpty()) {
			message = RentAppState.getContext().getText(R.string.server_problem__connect).toString();
			resultOperation = false;
		} else {
			try {
				jresult = new JSONObject(response);
			} catch (JSONException e) {
				message = RentAppState.getContext().getText(R.string.json_parser_problem).toString();
				resultOperation = false;
			}

		}
	}
}
