import React from 'react';
import {
  SafeAreaView,
  ScrollView,
  StatusBar,
  View,
  Text,
  Button,
} from 'react-native';

export default function Landing({navigation}) {
  return (
    <SafeAreaView>
      <StatusBar />
      <ScrollView contentInsetAdjustmentBehavior="automatic">
        <View>
          <Button
            title="Click here to go to screen with IAM"
            onPress={() => navigation.navigate('Home')}
          />
        </View>
        <Text>There is no IAM here, go to next screen press above button</Text>
      </ScrollView>
    </SafeAreaView>
  );
}
