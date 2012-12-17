//
//  QuizResultCellView.h
//  THCN
//
//  Created by Juan Escudero on 12/16/12.
//  Copyright (c) 2012 Juan Escudero. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface QuizResultCellView : UITableViewCell
@property (weak, nonatomic) IBOutlet UILabel *questionText;
@property (weak, nonatomic) IBOutlet UILabel *correctQuestionText;
@property (weak, nonatomic) IBOutlet UIWebView *questionExplanation;
@property (weak, nonatomic) IBOutlet UILabel *questionNum;

@end
