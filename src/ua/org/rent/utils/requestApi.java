package ua.org.rent.utils;

import android.content.res.Resources;
import android.util.Log;
import ua.org.rent.settings.RentAppState;

import javax.net.ssl.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

import ua.org.rent.settings.Settings;
import ua.org.rent.utils.AppNetworkException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * Created with IntelliJ IDEA.
 * User: admin
 * Date: 20.09.13
 * Time: 15:17
 * To change this template use File | Settings | File Templates.
 */
public abstract class RequestApi<Result> {
    protected String URLTarget;
    protected String params;
    private Resources mAppRes;
    public RequestApi(String targetURL) {
        URLTarget = targetURL;
        mAppRes = RentAppState.getAppInstance().getResources();
    }

    public Result execute() throws AppNetworkException{
        return executeWebRequest();
    }

    protected Result executeWebRequest() throws AppNetworkException{
        return null;
    }




    protected abstract String createParams();
    protected final String excutePost() throws AppNetworkException
    {
        params = createParams();
        if (!Utils.isOnline()) throw new AppNetworkException("Network not available",AppNetworkException.ERROR_CONNECTION_UNAVAILABLE);
        if (URLTarget == null | params == null)
        {	throw new NullPointerException("URL or params not specified! URL : "+URLTarget+" , POST params: "+params+" Class name ::"+this.getClass().getName());}
        URL url;
        HttpURLConnection connection = null;
        Settings mSettings = Settings.get();
        String token = mSettings.getAccessToken();
        try {
            //Create connection
            url = new URL(URLTarget);

            connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Accept-Charset",  "UTF-8");
            if(token!=null) connection.setRequestProperty("Authorization", ("Basic "+token));

            connection.setUseCaches (false);
            connection.setDoInput(true);
            connection.setDoOutput(true);

            //Send request

            OutputStreamWriter wr = new OutputStreamWriter  (
                    connection.getOutputStream ());
            wr.write (params);
            wr.flush ();
            wr.close ();
            if(token!=null) Log.i("Networking", "URL:  " + URLTarget + "\nParams: " + params);
            connection.connect();

            //Get Response
            int resposeCode = connection.getResponseCode();

            Log.i("Networking" , "message: " + connection.getResponseMessage() + " (Code: "+resposeCode+" )");
            if(resposeCode!=200 && resposeCode!=499) throw new AppNetworkException(createErrorMessage(URLTarget), resposeCode);

            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuffer response = new StringBuffer();
            while((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            return response.toString();

        } catch (IOException e) {
            e.printStackTrace();
            //Crittercism.logHandledException(e);
            throw new AppNetworkException("ERROR CONNECTION FAILED",AppNetworkException.ERROR_CONNECTION_FAILED);

        } finally {

            if(connection != null) {
                connection.disconnect();
            }
        }
    }




    private String createErrorMessage(String pURLTarget) {
        //mAppRes.getString(R.string.lb_err_getting_consult_aswers);
        /*
        String message=mAppRes.getString(R.string.lb_err_app_exception);
        if(pURLTarget.equals(Const.URL_LOGIN)) {
            message = mAppRes.getString(R.string.lb_err_authorization_failed);;
        }else if(pURLTarget.equals(Const.URL_EVENTS)) {
            message = mAppRes.getString(R.string.lb_err_events_update_failed);
        }else if(pURLTarget.equals(Const.URL_SYNC)) {
            message = mAppRes.getString(R.string.lb_err_profiles_sync_failed);
        }else if(pURLTarget.equals(Const.URL_MEAL)) {
            message = mAppRes.getString(R.string.lb_err_meal_db_sync);
        }else if(pURLTarget.equals(Const.URL_EVENTS_SENDER)) {
            message = mAppRes.getString(R.string.lb_err_events_delivering_failed);
        }else if(pURLTarget.equals(Const.URL_CONSULT_QUESTION)) {
            message = mAppRes.getString(R.string.lb_erro_delivering_consult_question);
        }else if(pURLTarget.equals(Const.URL_CONSULT_OP_LIST)) {
            message = mAppRes.getString(R.string.lb_err_getting_consult_aswers);

        }     */
        String message="Error Happend";
        return message;
    }
}

