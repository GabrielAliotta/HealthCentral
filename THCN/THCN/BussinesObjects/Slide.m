//
//  Slide.m
//  THCN
//
//  Created by Juan Escudero on 12/5/12.
//  Copyright (c) 2012 Juan Escudero. All rights reserved.
//

#import "Slide.h"

@implementation Slide

@synthesize _id, image, text, title;

- (id)initWithAttributes:(NSDictionary *)attributes{
    self = [super init];
    if (!self) {
        return nil;
    }
    
    _id = [attributes valueForKeyPath:@"id"];
    text = [attributes valueForKeyPath:@"text"];
    title = [attributes valueForKeyPath:@"title"];
    image = [attributes valueForKeyPath:@"image"];
    
    return self;

}

@end
