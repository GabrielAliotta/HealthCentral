//
//  NSString+StripHTMLTags.m
//  THCN
//
//  Created by Juan Escudero on 12/20/12.
//  Copyright (c) 2012 Juan Escudero. All rights reserved.
//

#import "NSString+StripHTMLTags.h"

@implementation NSString (StripHTMLTags)

-(NSString *) stringByStrippingHTML {
    NSRange r;
    NSString *s = [self copy];
    while ((r = [s rangeOfString:@"<[^>]+>" options:NSRegularExpressionSearch]).location != NSNotFound)
        s = [s stringByReplacingCharactersInRange:r withString:@""];

    s = [s stringByReplacingOccurrencesOfString:@"&nbsp;" withString:@""];
    return s;
}

@end
