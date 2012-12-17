//
//  Slide.h
//  THCN
//
//  Created by Juan Escudero on 12/5/12.
//  Copyright (c) 2012 Juan Escudero. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface Slide : NSObject

@property (nonatomic, strong) NSString * _id;
@property (nonatomic, strong) NSString * title;
@property (nonatomic, strong) NSString * text;
@property (nonatomic, strong) NSString * image;


- (id)initWithAttributes:(NSDictionary *)attributes;

@end
