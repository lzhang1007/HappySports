package com.android.orient.sports.happysports;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.orient.sports.happysports.kldf.constant.HttpConstant;
import com.android.orient.sports.happysports.kldf.entity.req.AddStepReq;
import com.android.orient.sports.happysports.kldf.entity.req.LoginReq;
import com.android.orient.sports.happysports.kldf.entity.resp.LoginResp;
import com.android.orient.sports.happysports.kldf.util.CacheUtil;
import com.android.orient.sports.happysports.kldf.util.DateUtil;
import com.android.orient.sports.happysports.kldf.util.HttpHandler;
import com.android.orient.sports.happysports.kldf.util.HttpUtil;
import com.android.orient.sports.happysports.kldf.util.MyUtil;
import com.android.orient.sports.happysports.kldf.util.StepCacheUtil;
import com.encrypt.EncryptUtil;

import org.json.JSONObject;

import static android.Manifest.permission.READ_PHONE_STATE;
import static com.android.orient.sports.happysports.kldf.util.CacheUtil.getAppShared;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage, mInfo;
    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private EditText mVersion;

    private StringBuilder infoBuilder = new StringBuilder();
    private EditText mSteps;

    private View mProgressView;
    private View mLoginFormView;

    private View logout, loginContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = (TextView) findViewById(R.id.message);
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);
        mVersion = (EditText) findViewById(R.id.version);
        mInfo = (TextView) findViewById(R.id.info);
        mSteps = (EditText) findViewById(R.id.steps);
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
        loginContainer = findViewById(R.id.login_container);

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mEmailView.getText().toString();
                String password = mPasswordView.getText().toString();
                String version = mVersion.getText().toString();
                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(version)) {
                    Toast.makeText(MainActivity.this, "账号、密码、版本号不能为空", Toast.LENGTH_LONG).show();
                    return;
                }
                sendLoginService(email, password, version);
            }
        });
        findViewById(R.id.update).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(mSteps.getText())) {
                    return;
                }
                sendStepService(mSteps.getText().toString());
            }
        });
        logout = findViewById(R.id.logout);
        logout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                loginContainer.setVisibility(View.VISIBLE);
                logout.setVisibility(View.GONE);
            }
        });

        setupViewData();
        requestPhoneState();
    }

    private boolean requestPhoneState() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_PHONE_STATE)) {
            Snackbar.make(mEmailView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_PHONE_STATE}, 0);
                        }
                    });
        } else {
            requestPermissions(new String[]{READ_PHONE_STATE}, 0);
        }
        return false;
    }

    private void setupViewData() {
        String token = CacheUtil.getToken();
        if (TextUtils.isEmpty(token)) {
            mTextMessage.setText("未登录, 请先登录");
            loginContainer.setVisibility(View.VISIBLE);
            logout.setVisibility(View.GONE);
        } else {
            loginContainer.setVisibility(View.GONE);
            logout.setVisibility(View.VISIBLE);

            mSteps.setText(CacheUtil.getAppShared().getString("last_steps", ""));
            String account = CacheUtil.getAccount();
            mVersion.setText(getAppShared().getString("app_version", ""));
            mEmailView.setText(account);
            mPasswordView.setText(getAppShared().getString("password", ""));
            String lastTime = CacheUtil.getAppShared().getString("token_date", "" + System.currentTimeMillis());
            mTextMessage.setText("当前登录的账号为：" + "\n" + account
                    + "\n" + "最后登录日期为：" + "\n" + DateFormat.format("yyyy-MM-dd HH:mm:ss", Long.parseLong(lastTime))
                    + "\n" + "当前版本号为：" + CacheUtil.getAppShared().getString("app_version", "--"));
        }
    }

    private void sendLoginService(final String account, final String password, final String version) {
        showProgress(true);
        final LoginReq req = new LoginReq();
        req.setLoginName(account);
        req.setPassword(password);
        req.setChannelId(CacheUtil.getChannelId());
        req.setSign(EncryptUtil.md5(req.getLoginName() + req.getChannelId()));
        HttpUtil.httpPost(HttpConstant.USER_LOGIN, req, new HttpHandler() {
            @Override
            public void callBack(String str, JSONObject jSONObject, Object obj) {
                showProgress(false);
                String resultCode = null;
                try {
                    resultCode = jSONObject.getString("result");
                    if (HttpConstant.SUCCESS.equals(resultCode)) {
                        LoginResp resp = new LoginResp();
                        MyUtil.json2object(jSONObject, resp);
                        CacheUtil.setMemberId(resp.getMemberId());
                        CacheUtil.setAccount(account);
                        CacheUtil.setToken(resp.getVhstoken());
                        CacheUtil.setIsLoginHome(true);
                        CacheUtil.putAppShared("password", password);
                        CacheUtil.putAppShared("app_version", version);
                        CacheUtil.putAppShared("token_date", "" + System.currentTimeMillis());
                        StepCacheUtil.setStride((float) resp.getStride());
                        StepCacheUtil.setBraceletMac(resp.getHandMac());
                        CacheUtil.setLoginNum(resp.getLoginNum());
                        setupViewData();
                        logMessage("登录成功");
                    } else {
                        logMessage("登录失败" + "\n" + jSONObject);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void sendStepService(String step) {
        showProgress(true);
        if (Integer.parseInt(step) > 45000) {
            Toast.makeText(MainActivity.this, "小伙子不要太狂", Toast.LENGTH_SHORT).show();
            return;
        }
        AddStepReq req = new AddStepReq();
        req.setTimestamp(String.valueOf(System.currentTimeMillis()));
        req.setSteps(getStepsInfo());
        req.setSign(EncryptUtil.md5(CacheUtil.getToken() + req.getTimestamp()));
        HttpUtil.httpPost(HttpConstant.ADD_STEP, req, new HttpHandler() {
            @Override
            public void callBack(String str, JSONObject jSONObject, Object obj) {
                showProgress(false);
                String resultCode = null;
                try {
                    resultCode = jSONObject.getString("result");
                    if (HttpConstant.SUCCESS.equals(resultCode)) {
                        logMessage("同步数据成功" + "\n" + jSONObject);
                    } else {
                        logMessage("同步数据失败" + "\n" + jSONObject);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private String getStepsInfo() {
        CharSequence step = mSteps.getText();
        if (TextUtils.isEmpty(step)) {
            step = "0";
        }
        CacheUtil.putAppShared("last_steps", step);
        StringBuilder stepsBuf = new StringBuilder();
        stepsBuf.append("[");
        stepsBuf.append("{\"sportDate\":\"" + DateUtil.getCurrentDateStr() + "\",");
        stepsBuf.append("\"step\":" + Integer.parseInt(step.toString()) + ",");
        stepsBuf.append("\"handMac\":\"" + StepCacheUtil.getBraceletMac() + "\"}");
        stepsBuf.append("]");
        return stepsBuf.toString();
    }

    private void logMessage(CharSequence message) {
        infoBuilder.append(DateFormat.format("yyyy-MM-dd HH:mm:ss", System.currentTimeMillis()) + "  ").append(message).append("\n");
        mInfo.setText(infoBuilder);
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

}
