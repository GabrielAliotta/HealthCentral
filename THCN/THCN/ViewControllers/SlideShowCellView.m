//
//  SlideShowCellView.m
//  THCN
//
//  Created by Juan Escudero on 12/10/12.
//  Copyright (c) 2012 Juan Escudero. All rights reserved.
//

#import "SlideShowCellView.h"

@implementation SlideShowCellView

@synthesize slideImage, slideText;

- (id)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier
{
    self = [super initWithStyle:style reuseIdentifier:reuseIdentifier];
    if (self) {
        // Initialization code
    }
    return self;
}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated
{
    [super setSelected:selected animated:animated];

    UIView *selectionColor = [[UIView alloc] init];
    selectionColor.backgroundColor = [UIColor colorWithRed:(140/255.0) green:(198/255.0) blue:(1/255.0) alpha:1];
    self.selectedBackgroundView = selectionColor;
    
    // Configure the view for the selected state
}

@end
