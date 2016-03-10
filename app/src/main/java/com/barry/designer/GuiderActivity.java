package com.barry.designer;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.barry.designer.utils.BitmapUtil;

import me.relex.circleindicator.CircleIndicator;


/**
 * 向导页面： 只有软件第一次用的时候会有向导页面，进入后会生成一个xml
 * 文件，如果没有表示没有登陆
 */
public class GuiderActivity extends AppCompatActivity {

    private ViewPager viewPager;

    private static int[] imageIds = {
            R.drawable.intro_1,
            R.drawable.intro_2,
            R.drawable.intro_3
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guider);

        viewPager = (ViewPager) findViewById(R.id.vp);
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return SectionFragment.newInstance(position);
            }

            @Override
            public int getCount() {
                return 3;
            }
        });

        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.ci_guider);
        Toast.makeText(GuiderActivity.this, "  indicator:"+indicator, Toast.LENGTH_SHORT).show();

        indicator.setViewPager(viewPager);


    }

    //登录
    public void login(View view) {

        View loginView = LayoutInflater.from(this).inflate(R.layout.activity_login, null);
        Button signBtn = (Button) loginView.findViewById(R.id.email_sign_in_button);
        signBtn.setText("登陆");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(loginView);
        builder.show();

        mLoginFormView = loginView.findViewById(R.id.login_form);
        mProgressView = loginView.findViewById(R.id.login_progress);

        mEmailView = (AutoCompleteTextView) loginView.findViewById(R.id.email);
        mPasswordView = (EditText) loginView.findViewById(R.id.password);

        signBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
//                Intent intent = new Intent(GuiderActivity.this, MainActivity.class);
//                startActivity(intent);
            }
        });
    }

    //注册
    public void sign(View view) {
        final View loginView = LayoutInflater.from(this).inflate(R.layout.activity_login, null);
        Button signBtn = (Button) loginView.findViewById(R.id.email_sign_in_button);
        signBtn.setText("注册");
        TextInputLayout phoneLayout = (TextInputLayout) loginView.findViewById(R.id.phone_layout);
        phoneLayout.setVisibility(View.VISIBLE);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(loginView);
        builder.show();

        mLoginFormView = loginView.findViewById(R.id.login_form);
        mProgressView = loginView.findViewById(R.id.login_progress);

        mEmailView = (AutoCompleteTextView) loginView.findViewById(R.id.email);
        mPasswordView = (EditText) loginView.findViewById(R.id.password);


        signBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneNumber = (EditText) loginView.findViewById(R.id.phone_number);
                attemptLogin();
//                Intent intent = new Intent(GuiderActivity.this, MainActivity.class);
//                startActivity(intent);
            }
        });
    }

    //创建一个Fragment的类
    public static class SectionFragment extends Fragment {

        public static SectionFragment newInstance(int position) {
            SectionFragment fragment = new SectionFragment();
            Bundle b = new Bundle();
            b.putInt("pos", position);
            //创建一个bundle用于传递
            fragment.setArguments(b);
            return fragment;
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            //创建一个imageView
            View view = inflater.inflate(R.layout.frag_guider_vp_layout, null);
            //获取到传递过来的参数
            int position = getArguments().getInt("pos");

            //找到imageView
            ImageView imageView = (ImageView) view.findViewById(R.id.iv_guider);

            Bitmap oldBm = BitmapFactory.decodeResource(inflater.getContext().getResources(), imageIds[position]);
            Bitmap bm = BitmapUtil.scaleImage(oldBm, 1280, 800);
            imageView.setImageBitmap(bm);
            // imageView.setImageResource(imageIds[position]);

            imageView.setAdjustViewBounds(true);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);

            return view;
        }
    }

    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }
        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();
        String phone = null;

        boolean cancel = false;
        View focusView = null;

        if (phoneNumber != null) {//判断手机号（注册） 登陆没有进去
            phoneNumber.setError(null);
            phone = phoneNumber.getText().toString();
            if (TextUtils.isEmpty(phone) || !isPhoneValid(phone)) {
                phoneNumber.setError(getString(R.string.error_invalid_phone));
                focusView = phoneNumber;
                cancel = true;
            }
        }

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password) || !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
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

            mAuthTask = new UserLoginTask(email, password, phone);
            mAuthTask.execute((Void) null);
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    private boolean isPhoneValid(String phone) {
        //TODO: Replace this with your own logic
        return phone.length() == 11 || phone.length() == 7;
    }


    private UserLoginTask mAuthTask = null;
    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private EditText phoneNumber;

    private View mProgressView;
    private View mLoginFormView;


    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };

    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;
        private final String mPhone;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
            mPhone = null;
        }

        public UserLoginTask(String email, String password, String phone) {
            this.mEmail = email;
            this.mPassword = password;
            this.mPhone = phone;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                // Simulate network access.
                Thread.sleep(200);
            } catch (InterruptedException e) {
                return false;
            }

            for (String credential : DUMMY_CREDENTIALS) {
                String[] pieces = credential.split(":");
                if (pieces[0].equals(mEmail)) {
                    // Account exists, return true if the password matches.
                    return pieces[1].equals(mPassword);
                }
            }

            // TODO: register the new account here.
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            // showProgress(false);

            if (success) {
                Intent intent = new Intent(GuiderActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }


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


}
