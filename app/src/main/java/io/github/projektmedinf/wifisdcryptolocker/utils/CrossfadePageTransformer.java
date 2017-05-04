package io.github.projektmedinf.wifisdcryptolocker.utils;

import android.app.Dialog;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.*;
import com.nineoldandroids.view.ViewHelper;
import io.github.projektmedinf.wifisdcryptolocker.R;
import io.github.projektmedinf.wifisdcryptolocker.activities.SetupActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * This class was originally written by matrixxun
 * https://github.com/matrixxun/ProductTour
 */
public class CrossfadePageTransformer implements ViewPager.PageTransformer {

    private List<String> wifiNetworks = new ArrayList<>();
    private ArrayAdapter adapter;
    private EditText registerPasswordEditText;
    private SetupActivity setupActivity;

    public CrossfadePageTransformer(SetupActivity setupActivity) {
        this.setupActivity = setupActivity;
    }

    public ArrayAdapter getAdapter() {
        return adapter;
    }

    public EditText getRegisterPasswordEditText() {
        return registerPasswordEditText;
    }

    public void setWifiNetworks(List<String> wifiNetworks) {
        this.wifiNetworks = wifiNetworks;
    }

    @Override
    public void transformPage(View page, float position) {
        int pageWidth = page.getWidth();
        View topImage = page.findViewById(R.id.topImage);
        View backgroundView = page.findViewById(R.id.welcome_fragment);
        View textHead = page.findViewById(R.id.heading);
        View textContent = page.findViewById(R.id.content);

        //Password Page
        View enterPassword = page.findViewById(R.id.setup_page_1_enter_password);
        View registerPassword = page.findViewById(R.id.registerPassword);
        if (page.findViewById(R.id.registerPassword) != null) {
            registerPasswordEditText = (EditText) page.findViewById(R.id.registerPassword);
            registerPasswordEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        SetupActivity setupActivity = new SetupActivity();
                        setupActivity.checkRegistrationPassword(registerPasswordEditText);
                    }
                }
            });
        }

        //WiFi Card Page
        final ListView setupPage3ListView = (ListView) page.findViewById(R.id.setup_page_3_wifi_list);
        if (setupPage3ListView != null) {
            adapter = new ArrayAdapter<String>(setupActivity, R.layout.white_list_view_text, R.id.list_content, wifiNetworks);
            setupPage3ListView.setAdapter(adapter);
            setupPage3ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    final String wifiSSID = setupPage3ListView.getItemAtPosition(position).toString();
                    final Dialog dialog = new Dialog(setupActivity);
                    dialog.setContentView(R.layout.connect);
                    dialog.setTitle("Connect to Network");
                    TextView ssid = (TextView) dialog.findViewById(R.id.ssid);
                    Button dialogButton = (Button) dialog.findViewById(R.id.connectButton);
                    final EditText password = (EditText) dialog.findViewById(R.id.password);
                    ssid.setText(wifiSSID);
                    dialogButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            new WifiScanReceiver(CrossfadePageTransformer.this,setupActivity).finallyConnect(password.getText().toString(), wifiSSID);
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }
            });
        }

        if (0 <= position && position < 1) {
            ViewHelper.setTranslationX(page, pageWidth * -position);
        }
        if (-1 < position && position < 0) {
            ViewHelper.setTranslationX(page, pageWidth * -position);
        }

        if (((position > -1.0f) && (position < 1.0f)) && (position != 0.0f)) {
            setHorizontalTranslation(textHead, pageWidth * position);
            setHorizontalTranslation(textContent, pageWidth * position);
            setHorizontalTranslation(enterPassword, pageWidth * position);
            setHorizontalTranslation(registerPassword, pageWidth * position);
            setHorizontalTranslation(topImage, pageWidth / 2 * position);
            setHorizontalTranslation(setupPage3ListView, pageWidth / 2 * position);

            setAlpha(backgroundView, 1.0f - Math.abs(position));
            setAlpha(textHead, 1.0f - Math.abs(position));
            setAlpha(textContent, 1.0f - Math.abs(position));
            setAlpha(enterPassword, 1.0f - Math.abs(position));
            setAlpha(registerPassword, 1.0f - Math.abs(position));
            setAlpha(topImage, 1.0f - Math.abs(position));
            setAlpha(setupPage3ListView, 1.0f - Math.abs(position));
        }
    }

    private void setHorizontalTranslation(View view, float translation) {
        if (view == null) {
            return;
        }
        ViewHelper.setTranslationX(view, translation);
    }

    private void setAlpha(View view, float alpha) {
        if (view == null) {
            return;
        }
        ViewHelper.setAlpha(view, alpha);
    }
}