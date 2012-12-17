//
//  Vertical.h
//  THCN
//
//  Created by Juan Escudero on 12/5/12.
//  Copyright (c) 2012 Juan Escudero. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface Vertical : NSObject

@property (nonatomic, strong) NSString * _id;
@property (nonatomic, strong) NSString * name;
@property (nonatomic, strong) NSString * imageURL;
@property (nonatomic) BOOL hasSlideshows;
@property (nonatomic) BOOL hasQuizzes;

- (id)initWithAttributes:(NSDictionary *)attributes;

@end
