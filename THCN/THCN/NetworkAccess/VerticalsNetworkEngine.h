//
//  VerticalsNetworkEngine.h
//  THCN
//
//  Created by Juan Escudero on 12/5/12.
//  Copyright (c) 2012 Juan Escudero. All rights reserved.
//

#import "MKNetworkEngine.h"
#import "Vertical.h"

@interface VerticalsNetworkEngine : MKNetworkEngine

typedef void (^VerticalResponseBlock)(NSArray *list);

-(MKNetworkOperation*) getVerticalsWithCompletionHandler:(VerticalResponseBlock) completionBlock  errorHandler:(MKNKErrorBlock) errorBlock;

@end
