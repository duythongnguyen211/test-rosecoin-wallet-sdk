package com.thongnguyen.testrosecoinlibrary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;

import io.rosecoin.wallet.RoseCoinManager;
import io.rosecoin.wallet.ui.activity.RoseCoinActivity;
import io.rosecoin.wallet.utils.SweetAlertDialogUtils;
import io.rosecoin.wallet.utils.Utils;

public class MainActivity extends AppCompatActivity {

    EditText etEmail;

    RelativeLayout btnSignIn;

    WebView webView;

    CheckBox cbStoringPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        View rootView = getWindow().getDecorView().getRootView();

        etEmail = (EditText) rootView.findViewById(R.id.et_email);

        btnSignIn = (RelativeLayout) rootView.findViewById(R.id.btn_sign_in);

        webView = (WebView) rootView.findViewById(R.id.webview);

        cbStoringPass = (CheckBox) rootView.findViewById(R.id.cb_storing_pass);

        webView.getSettings().setJavaScriptEnabled(true);
        // Zoom by touch and drag
        webView.getSettings().setSupportZoom(false);
        webView.getSettings().setBuiltInZoomControls(false);
        webView.getSettings().setDisplayZoomControls(false);
        // both of them: false => not fit full screen size, true => auto fit
        // full screen size
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.setInitialScale(0);
        webView.getSettings().setEnableSmoothTransition(true);
        webView.getSettings().setUserAgentString("Android");
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        webView.getSettings().setSavePassword(false);
        webView.getSettings().setSaveFormData(false);
        webView.loadUrl("file:///android_asset/welcome/rosecoin.io.html");

        webView.setEnabled(false);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!ValidateInputs())
                    return;

                String email = etEmail.getText().toString().trim();
                //Init RoseCoin Sdk
                new RoseCoinManager.Builder(MainActivity.this)
                        .setApiKey("Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3N1ZXIiOiJyb3NlY29pbmFkbWluIiwic3ViIjoidGVzdGluZyIsImtleSI6IjkzN2EzMTMxNmJiYWU4MGFhMDEyZWZlZGNjNDhkM2FmIn0=.4QS7H90iJFbTM8Fd4pW2yPIR7TVTtZs378kfm1mxXOM=")
                        .setUserIdentityData(email)
                        .setUsingStoringPassphrase(cbStoringPass.isChecked())
                        .build();

                Intent intent = new Intent(MainActivity.this, RoseCoinActivity.class);
                startActivity(intent);
            }
        });
    }

    public boolean ValidateInputs()
    {
        String error_title = getResources().getString(io.rosecoin.wallet.R.string.title_error);

        if (!Utils.isStringValid(etEmail.getText().toString().trim()))
        {
            String content = "Please enter a valid email address.";
            SweetAlertDialogUtils.ShowErrorDialog(this, error_title, content);
            return false;
        }
        return true;
    }
}
