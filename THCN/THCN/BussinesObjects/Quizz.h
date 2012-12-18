//
//  Quizz.h
//  THCN
//
//  Created by Juan Escudero on 12/13/12.
//  Copyright (c) 2012 Juan Escudero. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface Quizz : NSObject


@property (nonatomic, strong) NSString * _id;
@property (nonatomic, strong) NSString * title;
@property (nonatomic, strong) NSString * text;
@property (nonatomic, strong) NSString * image;
@property (nonatomic, strong) NSData * imageData;
@property (nonatomic, strong) NSString *nextQuizId;
@property (nonatomic, strong) NSArray *questions;
@property (nonatomic, strong) Quizz *nextQuiz;

- (id)initWithAttributes:(NSDictionary *)attributes;

@end
