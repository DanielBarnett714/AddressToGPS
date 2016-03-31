package me.danielbarnett.AddressToGPS;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.*;

import java.net.URI;
import java.net.URISyntaxException;

public class MainActivity extends Activity{

    private Button lookupButton;
    private Button navigateButton;
    private WebView webView;
    private  EditText region, city, address;
    private String lookupValue;
    private Intent intent;
    private String action;
    private String type;
    final Activity activity = this;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startWebView();
        inputFields();
        handleIntent();
        buttons();

    }

    void inputFields(){
        region   = (EditText)findViewById(R.id.region);
        city   = (EditText)findViewById(R.id.city);
        address   = (EditText)findViewById(R.id.address);
    }

    void handleIntent(){
        intent = getIntent();
        action = intent.getAction();
        type = intent.getType();
        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                handleSendText(intent); // Handle text being sent
            }
        } else if (Intent.ACTION_VIEW.equals(action)) {
                handleActionView(intent);
        }

    }

    void buttons(){
        lookupButton = (Button) findViewById(R.id.lookup);
        lookupButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                lookupValue = address.getText().toString() + " " + city.getText().toString() + " " + region.getText().toString();

                for(int i = 0; i < 2; i++) // this loop fixes a bug where the address is not updated if one has already been searched
                {
                    activity.getCurrentFocus().clearFocus();
                    webView.requestFocus();
                    webView.loadUrl("javascript:setFocus(\"" + lookupValue + "\")");
                    if (i < 1){
                        activity.getCurrentFocus().clearFocus();
                    }
                }
            }
        });
        navigateButton = (Button) findViewById(R.id.navigate);
        navigateButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                webView.loadUrl("javascript:openInMaps()");
            }
        });
    }

    void startWebView(){
        webView = (WebView) findViewById(R.id.webView);
        JavaScriptInterface jsInterface = new JavaScriptInterface(this);
        webView.getSettings().setJavaScriptEnabled(true); // enable javascript
        webView.getSettings().setUserAgentString("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.116 Safari/537.36");
        webView.addJavascriptInterface(jsInterface, "JSInterface");
        webView.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(activity, description, Toast.LENGTH_SHORT).show();
            }
        });
        webView.loadUrl("file:///android_asset/AddressToGPS/index.html");
        webView.requestFocus();
    }

    void handleSendText(Intent intent) {
        String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
        if (sharedText != null) {
            address.setText(sharedText);
        }
    }

    void handleActionView(Intent intent) {
        try {
            URI uri = new URI(intent.getData().toString());
            String q = uri.getQuery();
            if (q != null) {
                String addr=q.substring(q.indexOf("=") + 1).replace("\n",",");
                address.setText(addr);
            }
        } catch (URISyntaxException e) {
            // Probably ought to put something here
        }
    }

    private class JavaScriptInterface {
        private Activity activity;
        public JavaScriptInterface(Activity activiy) {
            this.activity = activiy;
        }
        @JavascriptInterface
        public void openLink(String link){
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(link));
            activity.startActivity(intent);
        }
    }
}