package com.mlz.discovery;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import com.google.firebase.inappmessaging.FirebaseInAppMessagingDisplay;
import com.google.firebase.inappmessaging.FirebaseInAppMessagingDisplayCallbacks;
import com.google.firebase.inappmessaging.model.InAppMessage;
import android.util.Log;

/** @hide */
public class CustomizeInAppMessage
    implements FirebaseInAppMessagingDisplay, Application.ActivityLifecycleCallbacks {

  public CustomizeInAppMessage() {
      Log.e("CustomizeInAppMessage", "contructor");
  }

  @Override
  public void displayMessage(
      InAppMessage inAppMessage, 
      FirebaseInAppMessagingDisplayCallbacks callbacks
  ) {
      Log.e("CustomizeInAppMessage", inAppMessage.getCampaignId());
  }

  /** @hide */
  @Override
  public void onActivityCreated(final Activity activity, Bundle bundle) {
    Log.e("Created activity: ", activity.getClass().getName());
  }

  /** @hide */
  @Override
  public void onActivityPaused(Activity activity) {
    Log.e("Pausing activity: ", activity.getClass().getName());
  }

  /** @hide */
  @Override
  public void onActivityStopped(Activity activity) {
    Log.e("Stopped activity: ", activity.getClass().getName());
  }

  /** @hide */
  @Override
  public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    Log.e("SavedInstance activity: ", activity.getClass().getName());
  }

  /** @hide */
  @Override
  public void onActivityDestroyed(Activity activity) {
    Log.e("Destroyed activity: ", activity.getClass().getName());
  }

  /** @hide */
  @Override
  public void onActivityStarted(Activity activity) {
    Log.e("Started activity: ", activity.getClass().getName());
  }

  /** @hide */
  @Override
  public void onActivityResumed(Activity activity) {
    Log.e("Resumed activity: ", activity.getClass().getName());
  }
}