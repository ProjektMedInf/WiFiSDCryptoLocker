package io.github.projektmedinf.wifisdcryptolocker.activities;

import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.*;
import io.github.projektmedinf.wifisdcryptolocker.R;
import io.github.projektmedinf.wifisdcryptolocker.exceptions.InvalidPasswordException;
import io.github.projektmedinf.wifisdcryptolocker.service.UserService;
import io.github.projektmedinf.wifisdcryptolocker.service.impl.UserServiceImpl;
import io.github.projektmedinf.wifisdcryptolocker.utils.*;

import java.math.BigDecimal;

/**
 * This class was originally written by matrixxun
 * https://github.com/matrixxun/ProductTour
 */
public class SetupActivity extends AppCompatActivity {

    private final int NUM_PAGES = 5;
    private WifiScanReceiver wifiReciever;
    private String temp_password = "";
    private ViewPager pager;
    private LinearLayout circles;
    private boolean isOpaque = true;
    private CrossfadePageTransformer crossfadePageTransformer;
    /**
     * Keep track of the register task to ensure we can cancel it if requested.
     */
    private UserRegisterTask mRegisterTask = null;
    private UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_setup);

        final ImageButton previous = ImageButton.class.cast(findViewById(R.id.skip));
        final ImageButton next = ImageButton.class.cast(findViewById(R.id.next));
        final Button done = Button.class.cast(findViewById(R.id.done));
        pager = (ViewPager) findViewById(R.id.pager);

        userService = new UserServiceImpl(getApplicationContext());
        crossfadePageTransformer = new CrossfadePageTransformer(this);
        wifiReciever = new WifiScanReceiver(crossfadePageTransformer, this);

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((pager.getCurrentItem() - 1) >= 0) {
                    pager.setCurrentItem(pager.getCurrentItem() - 1, true);
                } else {
                    pager.setCurrentItem(0, true);
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pager.getCurrentItem() == 0) {
                    checkRegistrationPassword(crossfadePageTransformer.getRegisterPasswordEditText());
                }
                pager.setCurrentItem(pager.getCurrentItem() + 1, true);
            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishSetup();
            }
        });

        pager.setAdapter(new ScreenSlidePagerAdapter(getSupportFragmentManager(), NUM_PAGES));
        pager.setPageTransformer(true, crossfadePageTransformer);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == NUM_PAGES - 2 && positionOffset > 0) {
                    if (isOpaque) {
                        pager.setBackgroundColor(Color.TRANSPARENT);
                        isOpaque = false;
                    }
                } else {
                    if (!isOpaque) {
                        pager.setBackgroundColor(getResources().getColor(R.color.primary_material_light));
                        isOpaque = true;
                    }
                }
            }

            @Override
            public void onPageSelected(int position) {
                setIndicator(position);
                int firstPagePosition = 0;
                int lastPagePosition = NUM_PAGES - 2;
                if (position == firstPagePosition) {
                    previous.setVisibility(View.GONE);
                    next.setVisibility(View.VISIBLE);
                    done.setVisibility(View.GONE);
                } else if ((firstPagePosition < position) && (position < lastPagePosition)) {
                    previous.setVisibility(View.VISIBLE);
                    next.setVisibility(View.VISIBLE);
                    done.setVisibility(View.GONE);
                } else if (position == lastPagePosition) {
                    previous.setVisibility(View.VISIBLE);
                    next.setVisibility(View.GONE);
                    done.setVisibility(View.VISIBLE);
                } else if (position == NUM_PAGES - 1) {
                    finishSetup();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (pager.getCurrentItem() == 0) {
                    checkRegistrationPassword(crossfadePageTransformer.getRegisterPasswordEditText());
                }
            }
        });
        buildCircles();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (pager != null) {
            pager.clearOnPageChangeListeners();
        }
    }

    @Override
    protected void onPause() {
        unregisterReceiver(wifiReciever);
        super.onPause();
    }

    @Override
    protected void onResume() {
        registerReceiver(wifiReciever, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        super.onResume();
    }

    private void buildCircles() {
        circles = LinearLayout.class.cast(findViewById(R.id.circles));
        float scale = getResources().getDisplayMetrics().density;
        int padding = (int) (5 * scale + 0.5f);
        for (int i = 0; i < NUM_PAGES - 1; i++) {
            ImageView circle = new ImageView(this);
            circle.setImageResource(R.drawable.setup_page_indicator);
            circle.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            circle.setAdjustViewBounds(true);
            circle.setPadding(padding, 0, padding, 0);
            circles.addView(circle);
        }
        setIndicator(0);
    }

    private void setIndicator(int index) {
        if (index < NUM_PAGES) {
            for (int i = 0; i < NUM_PAGES - 1; i++) {
                ImageView circle = (ImageView) circles.getChildAt(i);
                if (i == index) {
                    circle.setColorFilter(getResources().getColor(R.color.text_selected));
                } else {
                    circle.setColorFilter(getResources().getColor(android.R.color.transparent));
                }
            }
        }
    }

    private void finishSetup() {
        try {
            CryptoUtils.isPasswordValid(temp_password);
        } catch (InvalidPasswordException e) {
            pager.setCurrentItem(0);
            return;
        }
        SetupAppUtils.setSetupCompleted(this);
        setContentView(R.layout.finish_setup);
        mRegisterTask = new SetupActivity.UserRegisterTask("wifiCryptoSDLockerUser", temp_password);
        mRegisterTask.execute((Void) null);
    }

    @Override
    public void onBackPressed() {
        if (pager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            pager.setCurrentItem(pager.getCurrentItem() - 1);
        }
    }

    /**
     * Try to register a new account.
     */
    public void checkRegistrationPassword(EditText passwordTextView) {

        if (mRegisterTask != null) {
            return;
        }

        // Reset errors.
        passwordTextView.setError(null);

        // Store values at the time of the login attempt.
        String password = passwordTextView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for valid password
        try {
            if (!CryptoUtils.isPasswordValid(password)) {
                focusView = passwordTextView;
                cancel = true;
            }
        } catch (InvalidPasswordException e) {
            passwordTextView.setError(e.getMessage());
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            temp_password = password;
            //TODO store password in a secure way
        }
    }

    /**
     * Represents an asynchronous registration task used to register
     * a new user.
     */
    public class UserRegisterTask extends AsyncTask<Void, Void, Boolean> {

        private final String mUsername;
        private final String mPassword;
        private String errorMsg;

        UserRegisterTask(String username, String password) {
            mUsername = username;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            switch ((new BigDecimal(userService.insertUser(mUsername,
                    CryptoUtils.hashPassword(mPassword))).intValueExact())) {
                case -1:
                    errorMsg = getString(R.string.database_error);
                    return false;
                case -2:
                    errorMsg = getString(R.string.username_already_exists);
                    return false;
                default:
                    return true;
            }
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mRegisterTask = null;

            if (success) {
                // refresh page with success message
                finish();
                Intent loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(loginIntent);
                Toast.makeText(getApplicationContext(), "User created. Please sign in", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Failed to create user!", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onCancelled() {
            mRegisterTask = null;
            errorMsg = null;
        }
    }
}