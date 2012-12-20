//
//  QuizzViewController.m
//  THCN
//
//  Created by Juan Escudero on 12/5/12.
//  Copyright (c) 2012 Juan Escudero. All rights reserved.
//

#import "QuizzViewController.h"
#import "Quizz.h"
#import "QuizResultsViewController.h"

@interface QuizzViewController ()
{
    NSInteger currentQuestionNumber;
    NSInteger numOfCorrectQuestions;
    NSInteger numOfWrongQuestions;
    NSMutableArray *listOfAnswers;
}
@end

@implementation QuizzViewController

@synthesize questionView, answerView, selectedQuiz, questionNumber, numOfCorrect, numOfWrong, partialResultsQuestisonsViews;

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization


    }
    return self;
}

- (void)viewWillAppear:(BOOL)animated{
    
    currentQuestionNumber = 0;
    numOfCorrectQuestions = 0;
    numOfWrongQuestions = 0;
    
    listOfAnswers = [[NSMutableArray alloc]init];
    
    questionView.delegate = self;
    answerView.delegate = self;
    
    answerView.quizTitle = [NSString stringWithFormat:@"Quiz: %@", selectedQuiz.title];
    questionView.quizTitle = [NSString stringWithFormat:@"Quiz: %@", selectedQuiz.title];
    
    [self.view addSubview:questionView];
    [self.view addSubview:answerView];
    answerView.hidden = TRUE;
    partialResultsQuestisonsViews.hidden = TRUE;
    
    // Do any additional setup after loading the view from its nib.
    self.questionView.question = [[selectedQuiz questions]objectAtIndex:currentQuestionNumber];
    [self updateNumOfQuestions];
    [self.questionView loadData];
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    
    self.title = @"Quizz";
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

-(void)questionAnswered:(QuizAnswer *)selectedAnswered{

    if (selectedAnswered)
    {
        [listOfAnswers addObject:[self.questionView selectedAnswer]];
        
        if ([[self.questionView selectedAnswer]isValid])
        {
            numOfCorrectQuestions ++;
        }
        else
        {
            numOfWrongQuestions ++;
        }
        
        [self setQuestionResult:[[self.questionView selectedAnswer]isValid]];
        
        answerView.hidden = FALSE;
        partialResultsQuestisonsViews.hidden = FALSE;
    }
    else
    {
        UIAlertView *alert = [[UIAlertView alloc]initWithTitle:@"Error" message:@"Select an answer" delegate:self cancelButtonTitle:@"Ok" otherButtonTitles: nil];
        [alert show];
    }
}



-(void)nextQuestion{

    self.questionView.selectedAnswer = nil;
    currentQuestionNumber ++;
    [self updateNumOfQuestions];

    answerView.hidden = TRUE;
    partialResultsQuestisonsViews.hidden = TRUE;
    
    if (currentQuestionNumber < selectedQuiz.questions.count)
    {
        self.questionView.question = [[selectedQuiz questions]objectAtIndex:currentQuestionNumber];
        [self.questionView loadData];
        
    }
    else
    {
        //DO validation, show the last view with the results
        [self performSegueWithIdentifier:@"ShowResults" sender:self];
        
    }
}

- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    
    UIViewController *viewController = [segue destinationViewController];
    ((QuizResultsViewController *)viewController).selectedQuiz = selectedQuiz;
    ((QuizResultsViewController *)viewController).listOfAnswers = [NSArray arrayWithArray:listOfAnswers];
}

-(void)setQuestionResult:(BOOL)isCorrect{
    QuizQuestion *question = [[selectedQuiz questions]objectAtIndex:currentQuestionNumber];

    if (!isCorrect)
    {
        answerView.correctLabel.hidden = TRUE;
        answerView.correctQuestionAnswer.hidden = FALSE;
        answerView.questionAnswer.hidden = FALSE;
        answerView.resultAnswerView.hidden = FALSE;
        
        for (QuizAnswer *validAnswer in question.answers) {
            if ([validAnswer isValid])
            {
                answerView.correctQuestionAnswer.text = validAnswer.title;
                break;
            }
        }
    
        answerView.questionAnswer.text = [[self.questionView selectedAnswer]title];
        
        answerView.answerExplanation.frame = CGRectMake(40, 180, answerView.answerExplanation.frame.size.width, answerView.answerExplanation.frame.size.height);
    }
    else
    {
        answerView.correctQuestionAnswer.hidden = TRUE;
        answerView.questionAnswer.hidden = TRUE;
        answerView.correctLabel.hidden = FALSE;
        answerView.resultAnswerView.hidden = TRUE;
        answerView.answerExplanation.frame = CGRectMake(40, 60, answerView.answerExplanation.frame.size.width, answerView.answerExplanation.frame.size.height);

    }
    
    answerView.answerExplanation.text = question.text;
    
    numOfCorrect.text = [NSString stringWithFormat:@"%d", numOfCorrectQuestions];
    numOfWrong.text = [NSString stringWithFormat:@"%d", numOfWrongQuestions];
    

}

-(void)updateNumOfQuestions
{
    questionNumber.text = [NSString stringWithFormat:@"Question %d of %d", currentQuestionNumber + 1, selectedQuiz.questions.count];
}
@end
