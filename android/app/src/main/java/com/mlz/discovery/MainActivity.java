package com.mlz.discovery;

import android.os.Bundle;

import com.google.firebase.inappmessaging.FirebaseInAppMessaging;

import com.facebook.react.ReactActivity;
import android.util.Log;

public class MainActivity extends ReactActivity {

  @Override
  public void onResume() {
      super.onResume();
      Log.i("FIAM", "activity started");
      MainApplication mainApplication = (MainApplication) this.getApplication();
      CustomizeInAppMessage custom = mainApplication.inAppMessagePackage.inAppMessageManager;;
      FirebaseInAppMessaging.getInstance().setMessageDisplayComponent(custom);
      Log.i("FIAM", "CustomizeInAppMessage registered");
  }

  /**
   * Returns the name of the main component registered from JavaScript. This is used to schedule
   * rendering of the component.
   */
  @Override
  protected String getMainComponentName() {
    return "IAMFirebase";
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(null);
  }
}
