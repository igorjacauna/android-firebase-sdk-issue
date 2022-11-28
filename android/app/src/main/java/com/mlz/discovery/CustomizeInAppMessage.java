package com.mlz.discovery;


import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.google.firebase.inappmessaging.FirebaseInAppMessagingDisplay;
import com.google.firebase.inappmessaging.FirebaseInAppMessagingDisplayCallbacks;
import com.google.firebase.inappmessaging.model.Action;
import com.google.firebase.inappmessaging.model.CardMessage;
import com.google.firebase.inappmessaging.model.InAppMessage;
import com.google.firebase.inappmessaging.model.MessageType;
import com.google.firebase.inappmessaging.FirebaseInAppMessaging;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/** @hide */
public class CustomizeInAppMessage extends ReactContextBaseJavaModule implements FirebaseInAppMessagingDisplay, Application.ActivityLifecycleCallbacks {

  private final HashMap<String, HashMap> InAppMessages = new HashMap<>();
  private final ReactContext mReactContext;

  public CustomizeInAppMessage(ReactApplicationContext context) {
      super(context);
      this.mReactContext = context;
  }

  @NonNull
  @Override
  public String getName() {
      return "InAppMessageManager";
  }

  @Override
  public void displayMessage(
      InAppMessage inAppMessage, 
      FirebaseInAppMessagingDisplayCallbacks callbacks
  ) {
      try {
            Log.i("FIAM", "displayMessage");          
            String campaignId = inAppMessage.getCampaignMetadata().getCampaignId();
            HashMap<String, Object> inAppMessageObject = new HashMap<String, Object>();
            inAppMessageObject.put("message", inAppMessage);
            inAppMessageObject.put("callbacks", callbacks);
            this.InAppMessages.put(campaignId, inAppMessageObject);
            this.setParams(campaignId, inAppMessage);
            this.sendEvent(campaignId);
        } catch (Exception e) {
            Log.e("FIAM", e.toString());
        }
  }

  public void setParams(String campaignId, InAppMessage inAppMessage) {
      WritableMap params = Arguments.createMap();
      MessageType messageType = inAppMessage.getMessageType();
      params.putString("messageId", inAppMessage.getCampaignMetadata().getCampaignId());

      // Add other stuffs related to message if needed like, title, image, links, etc.

      this.InAppMessages.get(campaignId).put("ParamsList", params);
  }

  public void sendEvent(String campaignId) {
      this.mReactContext
          .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
          .emit("OnDisplayInAppMessage", this.InAppMessages.get(campaignId).get("ParamsList"));
  }

  @ReactMethod
  public void markAsClicked(String campaignId, Promise promise) {
    try {
      FirebaseInAppMessagingDisplayCallbacks callbacks = (FirebaseInAppMessagingDisplayCallbacks) this.InAppMessages.get(campaignId).get("callbacks");
      InAppMessage inAppMessage = (InAppMessage) this.InAppMessages.get(campaignId).get("message");
      Action messageAction = inAppMessage.getAction();
      callbacks.messageClicked(messageAction);
      promise.resolve("Clicked!");
    } catch(Exception e) {
      Log.d("FIAM", e.toString());
      promise.reject("Error", e);
    }
  }

  @ReactMethod
  public void markAsImpressionDetected(String campaignId, Promise promise) {
    try {
      FirebaseInAppMessagingDisplayCallbacks callbacks = (FirebaseInAppMessagingDisplayCallbacks) this.InAppMessages.get(campaignId).get("callbacks");
      callbacks.impressionDetected();
      promise.resolve("Impression detected!");
    } catch(Exception e) {
      Log.d("FIAM", e.toString());
      promise.reject("Error", e);
    }
  }

  @ReactMethod
  public void markAsDismissed(String campaignId, Promise promise) {
    try {
      FirebaseInAppMessagingDisplayCallbacks callbacks = (FirebaseInAppMessagingDisplayCallbacks) this.InAppMessages.get(campaignId).get("callbacks");
      callbacks.messageDismissed(FirebaseInAppMessagingDisplayCallbacks.InAppMessagingDismissType.UNKNOWN_DISMISS_TYPE);
      promise.resolve("Dismissed!");
    } catch(Exception e) {
      Log.d("FIAM", e.toString());
      promise.reject("Error", e);
    }
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
    Log.e("SavedInstance activity:", activity.getClass().getName());
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