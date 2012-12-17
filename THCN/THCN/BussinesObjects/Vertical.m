//
//  Vertical.m
//  THCN
//
//  Created by Juan Escudero on 12/5/12.
//  Copyright (c) 2012 Juan Escudero. All rights reserved.
//

#import "Vertical.h"

@implementation Vertical

@synthesize _id, name, imageURL, hasQuizzes, hasSlideshows;

- (id)initWithAttributes:(NSDictionary *)attributes {
    self = [super init];
    if (!self) {
        return nil;
    }
    
    _id = [attributes valueForKeyPath:@"id"];
    name = [attributes valueForKeyPath:@"name"];
    imageURL = [attributes valueForKeyPath:@"image"];
    hasQuizzes = [[attributes valueForKeyPath:@"hasQuizzes"] boolValue];
    hasSlideshows = [[attributes valueForKeyPath:@"hasSlideshows"] boolValue];
    
    
    return self;
}

@end
