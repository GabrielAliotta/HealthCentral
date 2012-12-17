//
//  QuizzListCellViewViewController.h
//  THCN
//
//  Created by Juan Escudero on 12/13/12.
//  Copyright (c) 2012 Juan Escudero. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface QuizListCellView : UITableViewCell

@property (strong, nonatomic) IBOutlet UILabel *quizzText;
@property (strong, nonatomic) IBOutlet UIImageView *quizzImage;

@end
