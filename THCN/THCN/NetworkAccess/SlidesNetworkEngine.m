//
//  SlidesNetworkEngine.m
//  THCN
//
//  Created by Juan Escudero on 12/5/12.
//  Copyright (c) 2012 Juan Escudero. All rights reserved.
//

#import "SlidesNetworkEngine.h"
#import "Vertical.h"

#define kVerticalsURL  @"/index.php/tools/mobile?vertical=%@&contentType=slideshow"

@implementation SlidesNetworkEngine

-(MKNetworkOperation*) getSlides:(NSString*)idVertical  withCompletionHandler:(SlidesResponseBlock) completionBlock  errorHandler:(MKNKErrorBlock) errorBlock
{
    
    NSString *path = [NSString stringWithFormat:kVerticalsURL, idVertical];
    
    MKNetworkOperation *op = [self operationWithPath:path
                                              params:nil
                                          httpMethod:@"GET"];
    
    [op addCompletionHandler:^(MKNetworkOperation *completedOperation)
     {
         // the completionBlock will be called twice.
         // if you are interested only in new values, move that code within the else block
         
         if([completedOperation isCachedResponse]) {
             DLog(@"Data from cache %@", [completedOperation responseJSON]);
         }
         else {
             DLog(@"Data from server %@", [completedOperation responseJSON]);
         }
         
         NSMutableArray *responseList = [[completedOperation responseJSON] objectForKey: @"items"];
         
         NSMutableArray *slideShowList = [[NSMutableArray alloc]init];
         
         for (NSDictionary* element in responseList) {
             SlideShow * slideShow = [[SlideShow alloc]initWithAttributes:[element objectForKey:@"item"]];
             
             slideShow.imageData = [NSData dataWithContentsOfURL:[NSURL URLWithString:[slideShow image]]];
             
             [slideShowList addObject:slideShow];
         }
         
         completionBlock([NSArray arrayWithArray:slideShowList]);
         
     }errorHandler:^(MKNetworkOperation *errorOp, NSError* error) {
         
         errorBlock(error);
     }];
    
    [self enqueueOperation:op];
    
    return op;
}

@end
