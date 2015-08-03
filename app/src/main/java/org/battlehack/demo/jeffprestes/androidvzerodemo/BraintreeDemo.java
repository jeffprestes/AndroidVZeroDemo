package org.battlehack.demo.jeffprestes.androidvzerodemo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;
import org.w3c.dom.ProcessingInstruction;


public class BraintreeDemo extends Activity {

    private String clientToken;

    //Define here the URL root of your ServerSide
    private static final String SERVER_URL = "https://www.novatrix.com.br/bhdemo/";

    private static final String TAG = "BraintreeDemo";

    private AsyncHttpClient httpClient = new AsyncHttpClient();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getClientToken();
        setContentView(R.layout.activity_braintree_demo);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_braintree_demo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * Get Braintree's Client Token from Server-Side
     */
    private void getClientToken()     {

        String tokenRequestUrl = SERVER_URL + "token.php";

        Log.d(TAG,"Initializing token request to server side: " + tokenRequestUrl);

        httpClient.get(tokenRequestUrl, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                String errorMsg = "An error occurred when tried to get the token: " + throwable.getLocalizedMessage();
                Log.e(TAG, errorMsg, throwable);
                Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                clientToken = responseString;
                Button btnPay = (Button) findViewById(R.id.btnPay);
                btnPay.setEnabled(true);
                Log.d(TAG, "Token obtained: " + clientToken);
            }
        });
    }
}
