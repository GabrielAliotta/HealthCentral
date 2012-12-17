//
//  QuizAnswer.m
//  THCN
//
//  Created by Juan Escudero on 12/13/12.
//  Copyright (c) 2012 Juan Escudero. All rights reserved.
//

#import "QuizAnswer.h"

@implementation QuizAnswer

@synthesize title, isValid;

- (id)initWithAttributes:(NSDictionary *)attributes{
    self = [super init];
    if (!self) {
        return nil;
    }
    
    title = [attributes valueForKeyPath:@"title"];
    isValid = [[attributes valueForKeyPath:@"valid"] boolValue];

    return self;
}

@end
