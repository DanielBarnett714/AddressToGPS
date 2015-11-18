package me.danielbarnett.AddressToGPS;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
public class MainActivity extends Activity {

    private WebView mWebview ;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        mWebview  = new WebView(this);
        JavaScriptInterface jsInterface = new JavaScriptInterface(this);
        mWebview.getSettings().setJavaScriptEnabled(true); // enable javascript
        mWebview.getSettings().setUserAgentString("Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; FSL 7.0.6.01001)");
        mWebview.addJavascriptInterface(jsInterface, "JSInterface");

        final Activity activity = this;

        mWebview.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(activity, description, Toast.LENGTH_SHORT).show();
            }
        });

        mWebview.loadUrl("http://addresstogps.com");
        setContentView(mWebview );

    }
    public void showMap(Uri geoLocation) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(geoLocation);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
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

