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

@interface QuizzViewController : UIViewController<QuestionAnsweredDelegate, NextQuestionDelegate>

@property (strong, nonatomic) IBOutlet AnswerView *answerView;
@property (strong, nonatomic) IBOutlet QuestionView *questionView;
@property (weak, nonatomic) IBOutlet UILabel *questionNumber;
@property (weak, nonatomic) IBOutlet UIView *partialResultsQuestisonsViews;
@property (weak, nonatomic) IBOutlet UILabel *numOfCorrect;
@property (weak, nonatomic) IBOutlet UILabel *numOfWrong;

@property (strong, nonatomic) Quizz *selectedQuiz;


@end
