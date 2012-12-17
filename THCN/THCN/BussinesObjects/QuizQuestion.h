//
//  QuizQuestion.h
//  THCN
//
//  Created by Juan Escudero on 12/13/12.
//  Copyright (c) 2012 Juan Escudero. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface QuizQuestion : NSObject

@property (nonatomic, strong) NSString * title;
@property (nonatomic, strong) NSString * text;
@property (nonatomic, strong) NSString * image;
@property (nonatomic, strong) NSData * imageData;
@property (nonatomic, strong) NSArray *answers;

- (id)initWithAttributes:(NSDictionary *)attributes;

@end
