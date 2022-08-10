# iOS Firebase SDK Issue

> Please, before run configure Firebase project and save the `google-services.json` on `android/app/` directory

> Create 2 messages on Firebase Console for Android devices. First message binded to `session_start` event and the second message binded to `second_event` event

## Android code

The Android project and code are on `android` directory

## To run

```bash
yarn install
yarn android
```

## Uninstall App

Every time we have to try receive again the messages we must uninstall the app before run `yarn android` again

## Scenario

Run on terminal this command to see ADB logs and ReactNative logs:

```bash
adb logcat | grep -E "(FIAM|ReactNativeJS)"
```

When we click on button on screen, we fire two events `session_start` and `second_event` we receive the messages binded to this events and after we mark as read. If we click again we see in logs that only one message were marked as read. So we have to click again to fire the events, receive messages and mark as read the second message.

On terminal we will see something like:

```bash
FIAM.Headless: Fetching campaigns from service.
FIAM.Headless: Successfully fetched 2 messages from backend
ReactNativeJS: Firing event 1
ReactNativeJS: Firing event 2
FIAM.Headless: Already impressed campaign Test 1 ? : false
FIAM.Headless: Already impressed campaign Test 2 ? : false
ReactNativeJS: 'Message received', { messageId: '1079538721622331745' }
ReactNativeJS: 'Marking as Impression detected', { messageId: '1079538721622331745' }
ReactNativeJS: 'Message received', { messageId: '2135348672364510312' }
ReactNativeJS: 'Marking as Impression detected', { messageId: '2135348672364510312' }
ReactNativeJS: Firing event 1
ReactNativeJS: Firing event 2
FIAM.Headless: Already impressed campaign Campanha de teste 1 ? : true
FIAM.Headless: Already impressed campaign Campanha de teste 2 ? : false
ReactNativeJS: 'Message received', { messageId: '2135348672364510312' }
ReactNativeJS: 'Marking as Impression detected', { messageId: '2135348672364510312' }
ReactNativeJS: Firing event 1
ReactNativeJS: Firing event 2
FIAM.Headless: Already impressed campaign Campanha de teste 1 ? : true
FIAM.Headless: Already impressed campaign Campanha de teste 2 ? : true
```