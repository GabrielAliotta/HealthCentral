//
//  SlideShow.m
//  THCN
//
//  Created by Juan Escudero on 12/5/12.
//  Copyright (c) 2012 Juan Escudero. All rights reserved.
//

#import "SlideShow.h"
#import "Slide.h"

@implementation SlideShow

@synthesize _id, image, title, slides, text, imageData;

- (id)initWithAttributes:(NSDictionary *)attributes{
    self = [super init];
    if (!self) {
        return nil;
    }
    
    _id = [attributes valueForKeyPath:@"id"];
    title = [attributes valueForKeyPath:@"title"];
    image = [attributes valueForKeyPath:@"slideshowImage"];
    text = [attributes valueForKeyPath:@"blurb"];

    NSMutableArray *list = [[NSMutableArray alloc]init];
    
    for (NSDictionary* element in [[attributes objectForKey:@"slides"] objectForKey:@"slide"])
    {
        Slide * slide = [[Slide alloc]initWithAttributes:element];
        [list addObject:slide];
        
    }
    
    slides = list;
    
    return self;

}


@end
