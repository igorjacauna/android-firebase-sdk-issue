# iOS Firebase SDK Issue

> Please, before run configure Firebase project and save the `GoogleService-Info.plist` on ios directory

> Create 2 or more messages on Firebase Console for iOS devices

## iOS code

The iOS project and code are on `ios` directory

## To run

```bash
yarn install

cd ios
pod install

cd ..
yarn ios
```

> If have any error on build, open the `ios` directory on XCode and build it on XCode. After build try run `yarn ios` **on root dir** of project

## Uninstall App

Every time we have to try receive again the messages we must uninstall the app before run `yarn ios` again

## Code with workaround

On file `App.js` we have the logic about receive and mark as read or clicked the message.

If we click on button on screen, we receive a message. If we click again no one message are received.

To workaround we have to uncomment on line 42 of `App.js`. Remember to uninstall the app and run `yarn ios` again to start over to receive the messages
