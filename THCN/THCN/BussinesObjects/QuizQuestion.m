//
//  QuizQuestion.m
//  THCN
//
//  Created by Juan Escudero on 12/13/12.
//  Copyright (c) 2012 Juan Escudero. All rights reserved.
//

#import "QuizQuestion.h"
#import "QuizAnswer.h"

@implementation QuizQuestion

@synthesize title, text, imageData, image, answers;

- (id)initWithAttributes:(NSDictionary *)attributes{
    self = [super init];
    if (!self) {
        return nil;
    }
    
    title = [attributes valueForKeyPath:@"title"];
    text = [attributes valueForKeyPath:@"text"];
    image = [attributes valueForKeyPath:@"image"];
    
    NSMutableArray *list = [[NSMutableArray alloc]init];
    
    for (NSDictionary* element in [[attributes objectForKey:@"answers"] objectForKey:@"answer"])
    {
        QuizAnswer * answer = [[QuizAnswer alloc]initWithAttributes:element];
        [list addObject:answer];
        
    }
    
    answers = list;
    
    return self;
    
}

@end
