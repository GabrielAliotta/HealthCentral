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

@synthesize questionView, answerView, selectedQuiz, questionNumber, questionsProgresss, submitButton, nextButton;

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
    
    [self.view addSubview:questionView];
    [self.view addSubview:answerView];
    answerView.hidden = TRUE;
    
    [self questionsProgresss].progress = ((float)(currentQuestionNumber + 1) / (float)selectedQuiz.questions.count);
    
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

- (IBAction)SubmitAnswer:(id)sender {
    
    if ([self.questionView selectedAnswer])
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
    
        submitButton.hidden = TRUE;
        nextButton.hidden = FALSE;
        answerView.hidden = FALSE;
    }
    else
    {
        UIAlertView *alert = [[UIAlertView alloc]initWithTitle:@"Error" message:@"Select an answer" delegate:self cancelButtonTitle:@"Ok" otherButtonTitles: nil];
        [alert show];
    }
}

- (IBAction)nextQuestion:(id)sender {
    
    self.questionView.selectedAnswer = nil;
    currentQuestionNumber ++;
    [self updateNumOfQuestions];

    [self questionsProgresss].progress = ((float)(currentQuestionNumber + 1) / (float)selectedQuiz.questions.count);

    submitButton.hidden = FALSE;
    nextButton.hidden = TRUE;
    answerView.hidden = TRUE;    
    
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
        
        for (QuizAnswer *validAnswer in question.answers) {
            if ([validAnswer isValid])
            {
                answerView.correctQuestionAnswer.text = [NSString stringWithFormat:@"The correct answer is: %@",validAnswer.title];
                break;
            }
        }
    
        answerView.questionAnswer.text = [NSString stringWithFormat:@"You answered: %@", [[self.questionView selectedAnswer]title]];
    }
    else
    {
        answerView.correctQuestionAnswer.hidden = TRUE;
        answerView.questionAnswer.hidden = TRUE;
        answerView.correctLabel.hidden = FALSE;
    }
    
    [answerView.answerExplanation loadHTMLString:question.text baseURL:nil];
    answerView.numOfCorrects.text = [NSString stringWithFormat:@"%d", numOfCorrectQuestions];
    answerView.numOfWrongs.text = [NSString stringWithFormat:@"%d", numOfWrongQuestions];
    

}

-(void)updateNumOfQuestions
{
    questionNumber.text = [NSString stringWithFormat:@"Question %d of %d", currentQuestionNumber + 1, selectedQuiz.questions.count];
}
@end
