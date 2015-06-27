package org.battlehack.demo.jeffprestes.androidvzerodemo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.braintreepayments.api.dropin.BraintreePaymentActivity;
import com.braintreepayments.api.dropin.Customization;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;


public class BraintreeDemo extends Activity {

    private String clientToken;
    private static final String SERVER_URL = "https://www.novatrix.com.br/bhdemo";
    private static final int REQUEST_CODE = Menu.FIRST;
    private AsyncHttpClient httpClient = new AsyncHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_braintree_demo);
        clientToken = getClientToken();
    }

    private String getClientToken() {
        Log.i("BraintreeDemoServerUrl", SERVER_URL + "/token.php");
        httpClient.get(SERVER_URL + "/token.php", new TextHttpResponseHandler() {
            public void onSuccess(String response) {
                clientToken = response;
                findViewById(R.id.btnPay).setEnabled(true);
            }
        });
        return clientToken;
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

    public void checkout(View v)        {

        Customization.CustomizationBuilder cb = new Customization.CustomizationBuilder();
        cb.primaryDescription("Awesome picture");
        cb.secondaryDescription("Buying using BT Dropin for Android");
        cb.amount("$ 1000");
        cb.submitButtonText("Pay");
        Customization customization = cb.build();


        Intent intent = new Intent(this, BraintreePaymentActivity.class);
        intent.putExtra(BraintreePaymentActivity.EXTRA_CUSTOMIZATION, customization);
        intent.putExtra(BraintreePaymentActivity.EXTRA_CLIENT_TOKEN, clientToken);

        startActivityForResult(intent, REQUEST_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode == BraintreePaymentActivity.RESULT_OK) {
                String paymentMethodNonce = data.getStringExtra(BraintreePaymentActivity.EXTRA_PAYMENT_METHOD_NONCE);
                Log.i("BraintreeDemoServerUrl", "Nonce: " + paymentMethodNonce);
                postNonceToServer(paymentMethodNonce);
            }
        }
    }

    private void postNonceToServer(String paymentMethodNonce) {

        RequestParams requestParams = new RequestParams();
        requestParams.put("payment_method_nonce", paymentMethodNonce);
        requestParams.put("amount", "1000");
        Log.i("BraintreeDemoServerUrl", "********** Before the Call **********");

        httpClient.post(SERVER_URL + "/checkout.php", requestParams, new TextHttpResponseHandler() {
            @Override
            public void onSuccess(String response)    {
                final AlertDialog msgAlert = new AlertDialog.Builder(BraintreeDemo.this)
                                        .setMessage(response)
                                        .setPositiveButton(" OK ", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                    dialogInterface.dismiss();
                                            }
                                        })
                                        .create();
                msgAlert.show();

                Button payBtn = (Button) findViewById(R.id.btnPay);
                payBtn.setText("Buy it AGAIN!");
            }
            @Override
            public void onFailure(String response, Throwable e)     {
                Toast.makeText(BraintreeDemo.this, "Payment has not been processed. Reason: " + response, Toast.LENGTH_LONG).show();
            }
        });

        Log.i("BraintreeDemoServerUrl", "********** After the Call **********");
    }
}
