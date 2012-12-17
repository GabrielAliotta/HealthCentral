//
//  QuizAnswer.h
//  THCN
//
//  Created by Juan Escudero on 12/13/12.
//  Copyright (c) 2012 Juan Escudero. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface QuizAnswer : NSObject

@property (nonatomic, strong) NSString * title;
@property (nonatomic) BOOL isValid;

- (id)initWithAttributes:(NSDictionary *)attributes;

@end
