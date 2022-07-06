#import <Foundation/Foundation.h>
#import <Firebase.h>
#import "InAppMessageDisplay.h"
#import "InAppMessageManager.h"

NSString *const kFIRAppReady = @"FIRAppReadyToConfigureSDKNotification";

@implementation InAppMessageDisplay : NSObject

+ (void)load {
  [[NSNotificationCenter defaultCenter] addObserver:self
                                           selector:@selector(didReceiveConfigureSDKNotification:)
                                               name:kFIRAppReady
                                             object:nil];
}

+ (void)didReceiveConfigureSDKNotification:(NSNotification *)notification {

  InAppMessageDisplay *display = [[InAppMessageDisplay alloc] init];
  [FIRInAppMessaging inAppMessaging].messageDisplayComponent = display;
}

- (void)displayMessage:(FIRInAppMessagingDisplayMessage *)messageForDisplay
       displayDelegate:(id<FIRInAppMessagingDisplayDelegate>)displayDelegate {
  NSLog(@"FIAM: Mensagem recebida");
  if ([messageForDisplay isKindOfClass:[FIRInAppMessagingModalDisplay class]]) {
    InAppMessageManager *emitter = [InAppMessageManager allocWithZone: nil];
    [emitter FIAMReceivedModal:messageForDisplay displayDelegate:displayDelegate];
  } else if ([messageForDisplay isKindOfClass:[FIRInAppMessagingBannerDisplay class]]) {
    InAppMessageManager *emitter = [InAppMessageManager allocWithZone: nil];
    [emitter FIAMReceivedBanner:messageForDisplay displayDelegate:displayDelegate];
  } else if ([messageForDisplay isKindOfClass:[FIRInAppMessagingImageOnlyDisplay class]]) {
    InAppMessageManager *emitter = [InAppMessageManager allocWithZone: nil];
    [emitter FIAMReceivedImageOnly:messageForDisplay displayDelegate:displayDelegate];
  } else if ([messageForDisplay isKindOfClass:[FIRInAppMessagingCardDisplay class]]) {
    InAppMessageManager *emitter = [InAppMessageManager allocWithZone: nil];
    [emitter FIAMReceivedCard:messageForDisplay displayDelegate:displayDelegate];
  }
}
@end
