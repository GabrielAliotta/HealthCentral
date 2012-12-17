//
//  AnswerView.h
//  THCN
//
//  Created by Juan Escudero on 12/13/12.
//  Copyright (c) 2012 Juan Escudero. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface AnswerView : UIView

@property (weak, nonatomic) IBOutlet UILabel *correctLabel;
@property (weak, nonatomic) IBOutlet UILabel *questionAnswer;
@property (weak, nonatomic) IBOutlet UILabel *correctQuestionAnswer;
@property (weak, nonatomic) IBOutlet UIWebView *answerExplanation;
@property (weak, nonatomic) IBOutlet UILabel *numOfCorrects;
@property (weak, nonatomic) IBOutlet UILabel *numOfWrongs;

@end
