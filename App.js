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
    console.log('Marking as Impression detected', mes);
    InAppMessageManager.markAsImpressionDetected(mes.messageId);
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
    setMessageReceived('');
    console.log('Firing event 1');
    inAppMessaging().triggerEvent('session_start');
    console.log('Firing event 2');
    inAppMessaging().triggerEvent('second_event');
  };

  useEffect(() => {
    setListener();
  }, [setListener]);

  return (
    <SafeAreaView>
      <StatusBar />
      <ScrollView contentInsetAdjustmentBehavior="automatic">
        <View>
          <Button
            title="Click here to fire IAM events and receive the messages"
            onPress={fireEvent}
          />
        </View>
        <Text>Message received: {messageReceived}</Text>
      </ScrollView>
    </SafeAreaView>
  );
};

export default App;
