#import <Foundation/Foundation.h>
#import <Firebase.h>

@interface InAppMessageDisplay : NSObject <FIRInAppMessagingDisplay>

- (void)displayMessage:(FIRInAppMessagingDisplayMessage *)messageForDisplay
       displayDelegate:(id<FIRInAppMessagingDisplayDelegate>)displayDelegate;
@end
