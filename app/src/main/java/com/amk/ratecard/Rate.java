/*
 *      File: Rate.java
 *    Author: Antonio Prado <antonio.prado@amk-technologies.com>
 *      Date: Sep 18, 2018
 * Copyright: AMK Technologies, S.A. de C.V. 2017
 */
package com.amk.ratecard;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Class to handle all events and UI from custom alert dialog responsibilities.
 *
 * @author Antonio Prado &lt;antonio.prado@amk-technologies.com&gt;
 * @version 0.0.1
 * @since 0.0.1
 */
public class Rate implements View.OnClickListener {

    /**
     * Int var to specify there is no image.
     */
    public static final int NO_IMAGE = 0;
    /**
     * String var for param at rate settled at extra used by intent.
     */
    public static final String RATE = "rate";
    /**
     * String name for intent filter used for local broadcast.
     */
    public static final String EVENT_RATE = "event-rate";
    /**
     * String var for logger, contains java class name.
     */
    private static final String TAG = Rate.class.getName();
    /**
     * Activity var for get context parent where is called the library.
     */
    private Activity mContext;
    /**
     * Bitmap for circular resource image.
     */
    private Bitmap mResource;
    /**
     * Dialog object for custom alert.
     */
    private Dialog mDialogRate;
    /**
     * List of all views from stars rates.
     */
    private List<ImageView> mStarsList;

    /**
     * Constructor from Rate class, where it needs the activity source and int resource from image
     * profile to ve cropped.
     *
     * @param context  Activity source where is called the class.
     * @param resource Int id from mipmap resource.
     */
    public Rate(final Activity context, int resource) {
        if (resource == 0) {
            resource = R.mipmap.ic_profile;
        }
        final Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resource);
        this.mContext = context;
        this.mResource = bitmap;
    }

    /**
     * Method called to show the custom alert dialog, this method will inflate the proper views.
     *
     * @param service String service used to rate the service.
     * @param survey  String survey oriented to rate the service with custom question.
     */
    public void showDialogRate(final String service, final String survey) {
        final RoundedBitmapDrawable roundDrawable = initDialog();
        mStarsList = initStarsViews();
        final ImageView imageViewProfile = mDialogRate.findViewById(R.id.iv_profile);
        imageViewProfile.setImageDrawable(roundDrawable);
        final TextView textViewSurvey = mDialogRate.findViewById(R.id.tv_message);
        final TextView textViewService = mDialogRate.findViewById(R.id.tv_service);
        textViewSurvey.setText(survey);
        textViewService.setHint(service);
        mDialogRate.show();
    }

    /**
     * Method called to init the star views.
     *
     * @return List of Images views.
     */
    private List<ImageView> initStarsViews() {
        final List<ImageView> starsList = new ArrayList<>();
        starsList.add((ImageView) mDialogRate.findViewById(R.id.bt_star_one));
        starsList.add((ImageView) mDialogRate.findViewById(R.id.bt_star_two));
        starsList.add((ImageView) mDialogRate.findViewById(R.id.bt_star_three));
        starsList.add((ImageView) mDialogRate.findViewById(R.id.bt_star_four));
        starsList.add((ImageView) mDialogRate.findViewById(R.id.bt_star_five));
        for (ImageView view : starsList) {
            view.setOnClickListener(this);
        }
        return starsList;
    }

    /**
     * Called method to round the image resource.
     *
     * @return a BitmapDrawable.
     */
    private RoundedBitmapDrawable initDialog() {
        // Dialog Function
        mDialogRate = new Dialog(mContext);
        // Removing the features of Normal Dialogs
        mDialogRate.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialogRate.setContentView(R.layout.dialog_rate);
        mDialogRate.setCancelable(false);
        mDialogRate.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        // Create the RoundedBitmapDrawable.
        final RoundedBitmapDrawable roundDrawable = RoundedBitmapDrawableFactory
                .create(mContext.getResources(), mResource);
        roundDrawable.setCircular(true);
        return roundDrawable;
    }

    @Override
    public void onClick(final View view) {
        final int i = view.getId();
        if (i == R.id.bt_star_one) {
            sendRate(0);

        } else if (i == R.id.bt_star_two) {
            sendRate(1);

        } else if (i == R.id.bt_star_three) {
            sendRate(2);

        } else if (i == R.id.bt_star_four) {
            sendRate(3);

        } else if (i == R.id.bt_star_five) {
            sendRate(4);

        } else {
            mDialogRate.dismiss();

        }
    }

    /**
     * Method called to send the rate through local broadcast receiver.
     *
     * @param id int rate given from service.
     */
    private void sendRate(final int id) {
        final int rate = id + 1;
        for (int index = 0; index <= id; index++) {
            mStarsList.get(index).setBackgroundResource(R.drawable.ic_star_full);
        }
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                mDialogRate.dismiss(); // when the task active then close the dialog
                timer.cancel(); // also just top the timer thread, otherwise, you may receive a crash report
            }
        }, 600);
        if (BuildConfig.DEBUG) {
            Toast.makeText(mContext, "Thank you!", Toast.LENGTH_SHORT).show();
        }
        final Intent intent = new Intent(EVENT_RATE);
        // You can also include some extra data.
        intent.putExtra(RATE, rate);
        LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
        Log.d("sender", "Broadcasting message");
    }
}
