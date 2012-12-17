//
//  QuizzViewController.h
//  THCN
//
//  Created by Juan Escudero on 12/5/12.
//  Copyright (c) 2012 Juan Escudero. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "QuestionView.h"
#import "AnswerView.h"
#import "Quizz.h"

@interface QuizzViewController : UIViewController

@property (strong, nonatomic) IBOutlet AnswerView *answerView;
@property (strong, nonatomic) IBOutlet QuestionView *questionView;
@property (weak, nonatomic) IBOutlet UIButton *submitButton;
@property (weak, nonatomic) IBOutlet UIProgressView *questionsProgresss;
@property (weak, nonatomic) IBOutlet UILabel *questionNumber;

@property (strong, nonatomic) Quizz *selectedQuiz;
- (IBAction)SubmitAnswer:(id)sender;
@property (weak, nonatomic) IBOutlet UIButton *nextButton;
- (IBAction)nextQuestion:(id)sender;

@end
