//
//  QuizResultsViewController.m
//  THCN
//
//  Created by Juan Escudero on 12/16/12.
//  Copyright (c) 2012 Juan Escudero. All rights reserved.
//

#import "QuizResultsViewController.h"
#import "QuizResultCellView.h"
#import "QuizQuestion.h"
#import "QuizAnswer.h"
#import "ItemSelectionViewController.h"
#import "QuizzViewController.h"
#import "QuizNetworkEngine.h"
#import "ProgressViewViewController.h"

@interface QuizResultsViewController ()
{
    ProgressViewViewController *pvc;
}
@end

@implementation QuizResultsViewController

@synthesize selectedQuiz, listOfAnswers, correctPercentage, numCorrect, numWrong;

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
        self.resultList.delegate = self;
        self.resultList.dataSource = self;
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    
    pvc = [[ProgressViewViewController alloc]initWithNibName:@"ProgressViewViewController" bundle:nil];
    
    self.navigationController.navigationItem.hidesBackButton = TRUE;
    self.title = @"Quiz results";
    
    int count = 0;
    
    for (QuizAnswer * qa in listOfAnswers)
    {
        if (qa.isValid)
            count ++;
    }

    
    correctPercentage.text = [NSString stringWithFormat:@"%d %% Correct", ((count*100)/[self.listOfAnswers count]) ];

    numCorrect.text = [NSString stringWithFormat:@"%d", count];
    numWrong.text = [NSString stringWithFormat:@"%d", [self.listOfAnswers count] - count];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

#pragma mark - Table view data source

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    // Return the number of sections.
    return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    // Return the number of rows in the section.
    return selectedQuiz.questions.count;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString *CellIdentifier = @"Cell";
    
    QuizResultCellView  *cell = nil;
    
    cell = (QuizResultCellView *) [self.resultList dequeueReusableCellWithIdentifier:CellIdentifier];
    
    if(!cell)
    {
        
        NSArray *topLevelObjects = [[NSBundle mainBundle] loadNibNamed:@"QuizResultCellView" owner:nil options:nil];
        
        for(id currentObject in topLevelObjects)
        {
            if([currentObject isKindOfClass:[QuizResultCellView class]])
            {
                cell = (QuizResultCellView *)currentObject;
                break;
            }
        }
    }
    
    
    QuizQuestion * question = [selectedQuiz.questions objectAtIndex:indexPath.row];
    cell.questionText.text = [NSString stringWithFormat:@"%d. %@", indexPath.row + 1, ((QuizAnswer*)[listOfAnswers objectAtIndex:indexPath.row]).title];
    cell.questionExplanation.text = question.text;
    cell.correctQuestionText.text =  [NSString stringWithFormat:@"Correct Answer: %@",question.title];
    
    cell.isCorrect = ((QuizAnswer*)[listOfAnswers objectAtIndex:indexPath.row]).isValid;
    
    return cell;
    
}


- (IBAction)nextQuiz:(id)sender {
    
    if ([selectedQuiz.nextQuizId isEqualToString:@"0"])
    {
        for (UIViewController *vc in self.navigationController.viewControllers)
        {
            if ([vc isKindOfClass:[ItemSelectionViewController class]])
            {
                [self.navigationController popToViewController:vc animated:YES];
                break;
                
            }
        }
    }
    else
    {
        [self.view addSubview:pvc.view];
        
        for (UIViewController *vc in self.navigationController.viewControllers)
        {
            if ([vc isKindOfClass:[QuizzViewController class]])
            {
                
                if (!selectedQuiz.nextQuiz)
                {
                
                    QuizNetworkEngine * engine = [[QuizNetworkEngine alloc]initWithHostName:@"thcn-db01.bar.tpg.corp"];
            
                
                    [engine getQuiz:self.selectedQuiz.nextQuizId withCompletionHandler:^(Quizz *newQuiz) {
                    
                        [pvc.view removeFromSuperview];
                        ((QuizzViewController*)vc).selectedQuiz = newQuiz;
                        [self.navigationController popToViewController:vc animated:YES];
                    
                    } errorHandler:^(NSError *error) {
                    
                        [pvc.view removeFromSuperview];
                    
                        //[UIAlertView showWithError:error];
                    
                        NSLog(@"%@\t%@\t%@\t%@", [error localizedDescription], [error localizedFailureReason],
                          [error localizedRecoveryOptions], [error localizedRecoverySuggestion]);
                    }];
                }
                else
                {
                    [pvc.view removeFromSuperview];
                    ((QuizzViewController*)vc).selectedQuiz = selectedQuiz.nextQuiz;
                    [self.navigationController popToViewController:vc animated:YES];
                }
                
                
            }
        }
    }
    
}



@end
