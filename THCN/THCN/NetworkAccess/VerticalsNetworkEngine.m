//
//  VerticalsNetworkEngine.m
//  THCN
//
//  Created by Juan Escudero on 12/5/12.
//  Copyright (c) 2012 Juan Escudero. All rights reserved.
//

#import "VerticalsNetworkEngine.h"
#import "Vertical.h"

#define kVerticalsURL  @"/index.php/tools/verticals/"

@implementation VerticalsNetworkEngine 


-(MKNetworkOperation*) getVerticalsWithCompletionHandler:(VerticalResponseBlock) completionBlock  errorHandler:(MKNKErrorBlock) errorBlock {
    
    MKNetworkOperation *op = [self operationWithPath:kVerticalsURL
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
         
         NSMutableArray *responseList = [[completedOperation responseJSON] objectForKey: @"verticals"];

         NSMutableArray *verticalList = [[NSMutableArray alloc]init];
         
         for (NSDictionary* element in responseList) {
             Vertical * verticalElement = [[Vertical alloc]initWithAttributes:[element objectForKey:@"vertical"]];
             [verticalList addObject:verticalElement];
         }
         
         completionBlock([NSArray arrayWithArray:verticalList]);
         
     }errorHandler:^(MKNetworkOperation *errorOp, NSError* error) {
         
         errorBlock(error);
     }];
    
    [self enqueueOperation:op];
    
    return op;
}




@end
