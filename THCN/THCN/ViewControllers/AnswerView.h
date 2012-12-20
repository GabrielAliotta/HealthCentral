//
//  AnswerView.h
//  THCN
//
//  Created by Juan Escudero on 12/13/12.
//  Copyright (c) 2012 Juan Escudero. All rights reserved.
//

#import <UIKit/UIKit.h>

@protocol NextQuestionDelegate <NSObject>

-(void)nextQuestion;

@end

@interface AnswerView : UIView

@property (weak, nonatomic) IBOutlet UILabel *correctLabel;
@property (weak, nonatomic) IBOutlet UILabel *questionAnswer;
@property (weak, nonatomic) IBOutlet UILabel *correctQuestionAnswer;
@property (weak, nonatomic) IBOutlet UILabel *quizTitle;
@property (weak, nonatomic) IBOutlet UITextView *answerExplanation;
@property (weak, nonatomic) IBOutlet UIButton *nextButton;
@property (weak, nonatomic) IBOutlet UIView *resultAnswerView;

@property (strong, nonatomic) id<NextQuestionDelegate> delegate;

-(IBAction) nextQuestion:(id) sender;

@end
