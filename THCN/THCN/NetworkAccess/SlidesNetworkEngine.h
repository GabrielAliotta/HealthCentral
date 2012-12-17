//
//  SlidesNetworkEngine.h
//  THCN
//
//  Created by Juan Escudero on 12/5/12.
//  Copyright (c) 2012 Juan Escudero. All rights reserved.
//

#import "MKNetworkEngine.h"
#import "SlideShow.h"

@interface SlidesNetworkEngine : MKNetworkEngine

typedef void (^SlidesResponseBlock)(NSArray *list);

-(MKNetworkOperation*) getSlides:(NSString*)_idVertical  withCompletionHandler:(SlidesResponseBlock) completionBlock  errorHandler:(MKNKErrorBlock) errorBlock;

@end
