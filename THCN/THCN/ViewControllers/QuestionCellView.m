//
//  AnswerCellView.m
//  THCN
//
//  Created by Juan Escudero on 1/2/13.
//  Copyright (c) 2013 Juan Escudero. All rights reserved.
//

#import "QuestionCellView.h"

@implementation QuestionCellView

@synthesize imageView, selectedImage;

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

    // Configure the view for the selected state
    if (selected == TRUE)
        self.selectedImage.image = [UIImage imageNamed:@"HealthCentral-radio-checked.png"];
    else
        self.selectedImage.image = [UIImage imageNamed:@"HealthCentral-radio-unchecked.png"];
    
}

@end
