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

export default function Home({navigation}) {
  const [messageReceived, setMessageReceived] = useState('');
  const [loading, setLoading] = useState(true);

  const handleDisplayMessage = mes => {
    console.log('Message received', mes);
    setMessageReceived(JSON.stringify(mes, null, 2));
    InAppMessageManager.markAsImpressionDetected(mes.messageId);
  };

  const setListener = useCallback(() => {
    console.log('Native Module', InAppMessageManager);
    const eventEmitter = new NativeEventEmitter(InAppMessageManager);
    const subscription = eventEmitter.addListener(
      'OnDisplayInAppMessage',
      data => handleDisplayMessage(data),
    );
    return subscription;
  }, []);

  const fireEvent = () => {
    console.log('Triggering session_start event');
    inAppMessaging().triggerEvent('session_start');
  };

  useEffect(() => {
    setListener();
  }, [setListener]);

  useEffect(() => {
    if (loading) {
      return;
    }
    fireEvent();
  }, [loading]);

  /** Simulating a content request */
  setTimeout(() => setLoading(false), 5000);

  return (
    <SafeAreaView>
      <StatusBar />
      <ScrollView contentInsetAdjustmentBehavior="automatic">
        <View>
          <Button title="Go back" onPress={() => navigation.goBack()} />
          <Button
            title="Click here to fire IAM events and receive the messages"
            onPress={fireEvent}
          />
        </View>
        <Text>
          After ~5s a message should be received. If no, try button above:
        </Text>
        <Text>Message received:</Text>
        <Text>{messageReceived}</Text>
      </ScrollView>
    </SafeAreaView>
  );
}
