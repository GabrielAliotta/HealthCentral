//
//  QuizResultsViewController.h
//  THCN
//
//  Created by Juan Escudero on 12/16/12.
//  Copyright (c) 2012 Juan Escudero. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "Quizz.h"

@interface QuizResultsViewController : UIViewController<UITableViewDataSource, UITableViewDelegate>

@property (weak, nonatomic) IBOutlet UITableView *resultList;
@property (weak, nonatomic) IBOutlet UIButton *nextQuiz;
@property (weak, nonatomic) IBOutlet UILabel *correctPercentage;
@property (strong, nonatomic) NSArray *listOfAnswers;
@property (strong, nonatomic) Quizz *selectedQuiz;
@property (weak, nonatomic) IBOutlet UILabel *numCorrect;
@property (weak, nonatomic) IBOutlet UILabel *numWrong;

- (IBAction)nextQuiz:(id)sender;

@end
