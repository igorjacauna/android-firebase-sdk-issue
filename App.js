/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow strict-local
 */

import React, {useCallback, useEffect, useState} from 'react';
import {
  SafeAreaView,
  ScrollView,
  StatusBar,
  View,
  Text,
  Button,
  NativeModules,
  NativeEventEmitter,
  Platform,
} from 'react-native';

import inAppMessaging from '@react-native-firebase/in-app-messaging';

const {InAppMessageManager} = NativeModules;

const App = () => {
  const [messageReceived, setMessageReceived] = useState('');
  const handleDisplayMessage = mes => {
    console.log('Message received', mes);
    setMessageReceived(JSON.stringify(mes, null, 2));

    /**
     * Here, when we receive a message, we always call mark impression detected
     */
    InAppMessageManager.markAsImpressionDetected(mes.messageId);

    /**
     * Workaround to message stack works when only mark as read
     */
    if (Platform.OS === 'ios') {
      // Uncomment below to get next message when press button again
      // InAppMessageManager.markAsClicked(mes.messageId);
    }

  };

  const setListener = useCallback(() => {
    const eventEmitter = new NativeEventEmitter(InAppMessageManager);
    const subscription = eventEmitter.addListener(
      'OnDisplayInAppMessage',
      data => handleDisplayMessage(data),
    );
    return subscription;
  }, []);

  const fireEvent = () => {
    console.log('Firing event');
    setMessageReceived('');
    inAppMessaging().triggerEvent('firebase_iam_event');
  };

  useEffect(() => {
    setListener();
  }, [setListener]);

  return (
    <SafeAreaView>
      <StatusBar/>
      <ScrollView
        contentInsetAdjustmentBehavior="automatic">
        <View>
          <Button title="Click here to fire IAM event and receive a message" onPress={fireEvent} />
        </View>
        <Text>
          Message received: {messageReceived}
        </Text>
      </ScrollView>
    </SafeAreaView>
  );
};

export default App;
