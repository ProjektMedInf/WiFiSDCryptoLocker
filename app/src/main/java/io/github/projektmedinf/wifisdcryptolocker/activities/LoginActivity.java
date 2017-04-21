package io.github.projektmedinf.wifisdcryptolocker.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.*;
import edu.vt.middleware.password.*;
import io.github.projektmedinf.wifisdcryptolocker.R;
import io.github.projektmedinf.wifisdcryptolocker.model.User;
import io.github.projektmedinf.wifisdcryptolocker.service.UserService;
import io.github.projektmedinf.wifisdcryptolocker.service.impl.UserServiceImpl;
import io.github.projektmedinf.wifisdcryptolocker.utils.CryptoUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static io.github.projektmedinf.wifisdcryptolocker.utils.Constansts.CURRENT_USER_KEY;

/**
 * A login screen that offers login via username/password.
 */
public class LoginActivity extends AppCompatActivity {

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    /**
     * Keep track of the register task to ensure we can cancel it if requested.
     */
    private UserRegisterTask mRegisterTask = null;

    // UI references.
    private AutoCompleteTextView mUsernameView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    private UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mUsernameView = (AutoCompleteTextView) findViewById(R.id.username);

        // setting focus to password field if "next" is clicked
        // it somehow didn't work through the XML with nextFocusForward or the others
        mUsernameView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                mPasswordView.requestFocus();
                return true;
            }
        });

        mPasswordView = (EditText) findViewById(R.id.password);

        // try to log in if done button is clicked on the keyboard
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mUsernameSignInButton = (Button) findViewById(R.id.username_sign_in_button);
        mUsernameSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        Button mCreateNewAccountButton = (Button) findViewById(R.id.username_create_new_button);
        mCreateNewAccountButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptRegister();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

        userService = new UserServiceImpl(getApplicationContext());
    }

    /**
     * Try to register a new account.
     */
    private void attemptRegister() {

        if (mRegisterTask != null) {
            return;
        }

        // Reset errors.
        mUsernameView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String username = mUsernameView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for empty username
        if (TextUtils.isEmpty(username)) {
            mUsernameView.setError(getString(R.string.error_field_required));
            focusView = mUsernameView;
            cancel = true;
        }

        // Check for valid password
        if (!isPasswordValid(password)) {
            focusView = mPasswordView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            showProgress(true);
            mRegisterTask = new UserRegisterTask(username, password);
            mRegisterTask.execute((Void) null);
        }
    }

    /**
     * Attempts to sign in the account specified by the login form.
     * If there are form errors (missing fields), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mUsernameView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String username = mUsernameView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for empty username
        if (TextUtils.isEmpty(username)) {
            mUsernameView.setError(getString(R.string.error_field_required));
            focusView = mUsernameView;
            cancel = true;
        }

        // Check for empty password
        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new UserLoginTask(username, password);
            mAuthTask.execute((Void) null);
        }
    }

    /**
     * Checks if the given password satisfies the predefined password criteria
     *
     * @param password the password which should be checked
     * @return true, if the password is ok, false otherwise (in this case the error message will also be set)
     */
    private boolean isPasswordValid(String password) {

        // pwd between 8 and 16 chars
        LengthRule lengthRule = new LengthRule(8, 16);

        // no whitespaces
        WhitespaceRule whitespaceRule = new WhitespaceRule();

        CharacterCharacteristicsRule charRule = new CharacterCharacteristicsRule();

        // at least one digit
        charRule.getRules().add(new DigitCharacterRule(1));

        //at least one non-alphabetic char
        charRule.getRules().add(new NonAlphanumericCharacterRule(1));

        // at least one upper case char
        charRule.getRules().add(new UppercaseCharacterRule(1));

        // at least one lower case char
        charRule.getRules().add(new LowercaseCharacterRule(1));

        charRule.setNumberOfCharacteristics(4);

        List<Rule> ruleList = new ArrayList<>();
        ruleList.add(lengthRule);
        ruleList.add(whitespaceRule);
        ruleList.add(charRule);

        PasswordValidator passwordValidator = new PasswordValidator(ruleList);
        PasswordData passwordData = new PasswordData(new Password(password));

        System.out.println("Before validation");
        RuleResult ruleResult = passwordValidator.validate(passwordData);

        if (!ruleResult.isValid()) {
            StringBuilder error = new StringBuilder();
            for (String msg :
                    passwordValidator.getMessages(ruleResult)) {
                error.append(msg).append('\n');
            }

            mPasswordView.setError(error.toString());
        }

        return ruleResult.isValid();
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    /**
     * Represents an asynchronous login task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mUsername;
        private final String mPassword;
        private User found;

        UserLoginTask(String username, String password) {
            mUsername = username;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            found = userService.getUserByUserName(mUsername);
            return found != null && CryptoUtils.comparePasswords(mPassword, found.getPassword());
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            // TODO: remove backdoor
            if (success || "test".equals(mUsername)) {
                // start the main activity
                Intent mainActivityIntent = new Intent(getApplicationContext(), MainActivity.class);
                // set the password to the plain text, this way we can access it during runtime,
                // and the user doesn't have to type it in every time git he/she wants to access
                // encrypted data
                found.setPassword(mPassword);
                mainActivityIntent.putExtra(CURRENT_USER_KEY, found);
                startActivity(mainActivityIntent);
            } else {
                mPasswordView.setError(getString(R.string.error_incorrect_credentials));
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
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
            showProgress(false);

            if (success) {
                // refresh page with success message
                finish();
                startActivity(getIntent());

                Context context = getApplicationContext();
                CharSequence text = "User created. Please sign in";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            } else {
                mUsernameView.setError(errorMsg);
                mUsernameView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mRegisterTask = null;
            errorMsg = null;
            showProgress(false);
        }
    }
}

