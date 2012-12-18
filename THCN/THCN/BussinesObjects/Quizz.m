//
//  Quizz.m
//  THCN
//
//  Created by Juan Escudero on 12/13/12.
//  Copyright (c) 2012 Juan Escudero. All rights reserved.
//

#import "Quizz.h"
#import "QuizQuestion.h"

@implementation Quizz

@synthesize _id, title, text, imageData, nextQuizId, image, questions, nextQuiz;

- (id)initWithAttributes:(NSDictionary *)attributes{
    self = [super init];
    if (!self) {
        return nil;
    }
    
    _id = [attributes valueForKeyPath:@"id"];
    title = [attributes valueForKeyPath:@"title"];
    text = [attributes valueForKeyPath:@"text"];
    image = [attributes valueForKeyPath:@"image"];
    nextQuizId = [attributes valueForKeyPath:@"nextQuiz"];
    nextQuiz = nil;
    
    NSMutableArray *list = [[NSMutableArray alloc]init];
    
    for (NSDictionary* element in [[attributes objectForKey:@"questions"] objectForKey:@"question"])
    {
        QuizQuestion * question = [[QuizQuestion alloc]initWithAttributes:element];
        [list addObject:question];
    }
    
    questions = list;
    
    return self;
    
}

@end
