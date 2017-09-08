package com.android.orient.sports.happysports.kldf.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.orient.sports.happysports.R;
import com.android.orient.sports.happysports.kldf.constant.HttpConstant;
import com.android.orient.sports.happysports.kldf.entity.req.AddStepReq;
import com.android.orient.sports.happysports.kldf.util.CacheUtil;
import com.android.orient.sports.happysports.kldf.util.DateUtil;
import com.android.orient.sports.happysports.kldf.util.HttpHandler;
import com.android.orient.sports.happysports.kldf.util.HttpUtil;
import com.android.orient.sports.happysports.kldf.util.StepCacheUtil;
import com.encrypt.EncryptUtil;

import org.json.JSONObject;

public class StepSyncActivity extends FragmentActivity implements OnClickListener {

    private TextView currentStep;
    private EditText syncEditText;
    private Button syncBtn;
    private Button currentStepBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_sync);
//        currentStep = (TextView) findViewById(R.id.textView);
        syncEditText = (EditText) findViewById(R.id.editText);
        syncBtn = (Button) findViewById(R.id.button);
//        currentStepBtn = (Button) findViewById(R.id.button2);
        syncBtn.setOnClickListener(this);
//        currentStepBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.button) {
            CharSequence step = syncEditText.getText();
            if (Integer.parseInt(step.toString()) > 45000) {
                Toast.makeText(this, "小伙子不要太狂", Toast.LENGTH_SHORT).show();
                return;
            }
            AddStepReq req = new AddStepReq();
            req.setTimestamp(String.valueOf(System.currentTimeMillis()));
            req.setSteps(getStepsInfo());
            req.setSign(EncryptUtil.md5(CacheUtil.getToken() + req.getTimestamp()));
            HttpUtil.httpPost(HttpConstant.ADD_STEP, req, new HttpHandler() {
                @Override
                public void callBack(String str, JSONObject jSONObject, Object obj) {
                    Toast.makeText(StepSyncActivity.this, jSONObject.toString(), Toast.LENGTH_SHORT).show();
                }
            });
//        } else if (id == R.id.button2) {
        }
    }

    private String getStepsInfo() {
        CharSequence step = syncEditText.getText();
        if (TextUtils.isEmpty(step)) {
            step = "0";
        }
        StringBuffer stepsBuf = new StringBuffer();
        stepsBuf.append("[");
        stepsBuf.append("{\"sportDate\":\"" + DateUtil.getCurrentDateStr() + "\",");
        stepsBuf.append("\"step\":" + Integer.parseInt(step.toString()) + ",");
        stepsBuf.append("\"handMac\":\"" + StepCacheUtil.getBraceletMac() + "\"}");
        stepsBuf.append("]");
        return stepsBuf.toString();
    }
}
