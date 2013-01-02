//
//  AnswerCellView.h
//  THCN
//
//  Created by Juan Escudero on 1/2/13.
//  Copyright (c) 2013 Juan Escudero. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface QuestionCellView : UITableViewCell

@property (weak, nonatomic) IBOutlet UILabel *questionText;
@property (weak, nonatomic) IBOutlet UIImageView *selectedImage;

@end
