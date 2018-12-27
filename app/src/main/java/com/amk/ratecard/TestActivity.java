/*
 *      File: TestActivity.java
 *    Author: Antonio Prado <antonio.prado@amk-technologies.com>
 *      Date: Sep 10, 2018
 * Copyright: AMK Technologies, S.A. de C.V. 2017
 */
package com.amk.ratecard;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/**
 * Class to handle all events and UI from custom alert dialog responsibilities.
 *
 * @author Antonio Prado &lt;antonio.prado@amk-technologies.com&gt;
 * @version 0.0.1
 * @since 0.0.1
 */
public class TestActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * Object Rate as instance of alert dialog.
     */
    private Rate mRateDialog;
    /**
     * Broadcast receiver object to catch up rate from alert dialog.
     */
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(final Context context, final Intent intent) {
            // Get extra data included in the Intent
            final int rate = intent.getIntExtra(mRateDialog.RATE, 0);
            Log.d("receiver", "Got message: " + rate);
        }
    };

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter(mRateDialog.EVENT_RATE));
        final Button customImageBtn = findViewById(R.id.custom_image_btn);
        final Button noImageBtn = findViewById(R.id.no_image_btn);
        customImageBtn.setOnClickListener(this);
        noImageBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(final View view) {
        final int i = view.getId();
        if (i == R.id.custom_image_btn) {
            mRateDialog = new Rate(TestActivity.this, R.mipmap.im_sugus);
            mRateDialog.showDialogRate("AC Installation", "How was your service?");

        } else if (i == R.id.no_image_btn) {
            mRateDialog = new Rate(TestActivity.this, mRateDialog.NO_IMAGE);
            mRateDialog.showDialogRate("Boiler support", "How was your service?");

        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
    }
}
