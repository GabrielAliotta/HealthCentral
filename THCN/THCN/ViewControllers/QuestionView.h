//
//  QuestionView.h
//  THCN
//
//  Created by Juan Escudero on 12/13/12.
//  Copyright (c) 2012 Juan Escudero. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "QuizQuestion.h"
#import "QuizAnswer.h"

@interface QuestionView : UIView<UITableViewDataSource, UITableViewDelegate>

@property (weak, nonatomic) IBOutlet UILabel *questionText;
@property (weak, nonatomic) IBOutlet UITableView *questionOptions;


@property (strong, nonatomic) QuizQuestion *question;
@property (strong, nonatomic) QuizAnswer *selectedAnswer;

-(void)loadData;

@end
