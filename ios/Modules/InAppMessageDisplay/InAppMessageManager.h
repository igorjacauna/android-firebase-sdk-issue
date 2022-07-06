#import <Foundation/Foundation.h>
#import <React/RCTEventEmitter.h>
#import <Firebase.h>

@interface InAppMessageManager : RCTEventEmitter <RCTBridgeModule>

@property(nonatomic, readwrite) id<FIRInAppMessagingDisplayDelegate> displayDelegate;

@property (nonatomic, strong) NSMutableDictionary *displayDelegateDict;

@property (nonatomic, strong) NSMutableDictionary *inAppMessageDict;

- (void)FIAMReceivedModal: (FIRInAppMessagingModalDisplay *)inAppMessage
     displayDelegate:(id<FIRInAppMessagingDisplayDelegate>)displayDelegate;

- (void)FIAMReceivedBanner: (FIRInAppMessagingBannerDisplay *)inAppMessage
     displayDelegate:(id<FIRInAppMessagingDisplayDelegate>)displayDelegate;

- (void)FIAMReceivedImageOnly: (FIRInAppMessagingImageOnlyDisplay *)inAppMessage
     displayDelegate:(id<FIRInAppMessagingDisplayDelegate>)displayDelegate;

- (void)FIAMReceivedCard: (FIRInAppMessagingCardDisplay *)inAppMessage
     displayDelegate:(id<FIRInAppMessagingDisplayDelegate>)displayDelegate;

@end
