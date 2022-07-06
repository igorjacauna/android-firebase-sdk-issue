#import <Foundation/Foundation.h>
#import <React/RCTLog.h>
#import "InAppMessageDisplay.h"
#import "InAppMessageManager.h"

@implementation InAppMessageManager

RCT_EXPORT_MODULE();

- (nullable FIRInAppMessagingDisplayMessage *)inAppMessage {
  return nil;
}

- (NSMutableDictionary *) displayDelegateDict {
    if (!_displayDelegateDict) {
      _displayDelegateDict = [NSMutableDictionary new];
    }
    return _displayDelegateDict;
}

- (NSMutableDictionary *) inAppMessageDict {
    if (!_inAppMessageDict) {
      _inAppMessageDict = [NSMutableDictionary new];
    }
    return _inAppMessageDict;
}

+ (id)allocWithZone:(NSZone *)zone {
    static InAppMessageManager *sharedInstance = nil;
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        sharedInstance = [super allocWithZone:zone];
    });
    return sharedInstance;
}

- (NSArray<NSString *> *)supportedEvents {
    return @[@"OnDisplayInAppMessage"];
}

RCT_EXPORT_METHOD(markAsImpressionDetected:(NSString *)messageId){
  if ([self.displayDelegateDict objectForKey:messageId]) {
    [[self.displayDelegateDict objectForKey:messageId] impressionDetectedForMessage:[self.inAppMessageDict objectForKey:messageId]];
  }
}

RCT_EXPORT_METHOD(markAsClicked:(NSString *)messageId){
  NSString *actionURL = @"";
  if ([[self.inAppMessageDict objectForKey:messageId] isKindOfClass:[FIRInAppMessagingModalDisplay class]]) {
    FIRInAppMessagingModalDisplay *message = [self.inAppMessageDict objectForKey:messageId];
    if (message.actionURL.absoluteString) {
      actionURL = message.actionURL.absoluteString;
    }
  } else if ([[self.inAppMessageDict objectForKey:messageId] isKindOfClass:[FIRInAppMessagingBannerDisplay class]]) {
    FIRInAppMessagingBannerDisplay *message = [self.inAppMessageDict objectForKey:messageId];
    if (message.actionURL.absoluteString) {
      actionURL = message.actionURL.absoluteString;
    }
  } else if ([[self.inAppMessageDict objectForKey:messageId] isKindOfClass:[FIRInAppMessagingImageOnlyDisplay class]]) {
    FIRInAppMessagingImageOnlyDisplay *message = [self.inAppMessageDict objectForKey:messageId];
    if (message.actionURL.absoluteString) {
      actionURL = message.actionURL.absoluteString;
    }
  } else if ([[self.inAppMessageDict objectForKey:messageId] isKindOfClass:[FIRInAppMessagingCardDisplay class]]) {
    FIRInAppMessagingCardDisplay *message = [self.inAppMessageDict objectForKey:messageId];
    if (message.primaryActionURL.absoluteString) {
      actionURL = message.primaryActionURL.absoluteString;
    }
  }
  if ([self.displayDelegateDict objectForKey:messageId]) {
    NSURL *url = [NSURL URLWithString:actionURL];
    FIRInAppMessagingAction *action =
            [[FIRInAppMessagingAction alloc] initWithActionText:nil
                                                      actionURL:url];
    [[self.displayDelegateDict objectForKey:messageId] messageClicked:[self.inAppMessageDict objectForKey:messageId] withAction: action];
  }
}

RCT_EXPORT_METHOD(markAsDismissed:(NSString *)messageId){
  if ([self.displayDelegateDict objectForKey:messageId]) {
    [[self.displayDelegateDict objectForKey:messageId] messageDismissed:[self.inAppMessageDict objectForKey:messageId] dismissType:FIRInAppMessagingDismissTypeAuto];
  }
}

- (void)FIAMReceivedModal: (FIRInAppMessagingModalDisplay *)inAppMessage
        displayDelegate:(id<FIRInAppMessagingDisplayDelegate>)displayDelegate {
  [self sendEventWithName:@"OnDisplayInAppMessage" body:@{
    @"type":@"Modal",
    @"messageId": inAppMessage.campaignInfo.messageID
  }];
  self.displayDelegate = displayDelegate;
  [self.displayDelegateDict setObject:displayDelegate forKey:inAppMessage.campaignInfo.messageID];
  [self.inAppMessageDict setObject:inAppMessage forKey:inAppMessage.campaignInfo.messageID];
}

- (void)FIAMReceivedBanner: (FIRInAppMessagingBannerDisplay *)inAppMessage
        displayDelegate:(id<FIRInAppMessagingDisplayDelegate>)displayDelegate {
  [self sendEventWithName:@"OnDisplayInAppMessage" body:@{
    @"type":@"Banner",
    @"messageId": inAppMessage.campaignInfo.messageID
  }];
  self.displayDelegate = displayDelegate;
  [self.displayDelegateDict setObject:displayDelegate forKey:inAppMessage.campaignInfo.messageID];
  [self.inAppMessageDict setObject:inAppMessage forKey:inAppMessage.campaignInfo.messageID];
}

- (void)FIAMReceivedImageOnly: (FIRInAppMessagingImageOnlyDisplay *)inAppMessage
        displayDelegate:(id<FIRInAppMessagingDisplayDelegate>)displayDelegate {
  [self sendEventWithName:@"OnDisplayInAppMessage" body:@{
    @"type":@"ImageOnly",
    @"messageId": inAppMessage.campaignInfo.messageID
  }];
  self.displayDelegate = displayDelegate;
  [self.displayDelegateDict setObject:displayDelegate forKey:inAppMessage.campaignInfo.messageID];
  [self.inAppMessageDict setObject:inAppMessage forKey:inAppMessage.campaignInfo.messageID];
}

- (void)FIAMReceivedCard: (FIRInAppMessagingCardDisplay *)inAppMessage
        displayDelegate:(id<FIRInAppMessagingDisplayDelegate>)displayDelegate {
  [self sendEventWithName:@"OnDisplayInAppMessage" body:@{
    @"type":@"Card",
    @"messageId": inAppMessage.campaignInfo.messageID
  }];
  self.displayDelegate = displayDelegate;
  [self.displayDelegateDict setObject:displayDelegate forKey:inAppMessage.campaignInfo.messageID];
  [self.inAppMessageDict setObject:inAppMessage forKey:inAppMessage.campaignInfo.messageID];
}

@end
