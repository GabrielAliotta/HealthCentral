//
//  ItemSelectionViewController.m
//  THCN
//
//  Created by Juan Escudero on 12/5/12.
//  Copyright (c) 2012 Juan Escudero. All rights reserved.
//

#import "ItemSelectionViewController.h"
#import "SlideShowViewController.h"
#import "QuizListViewController.h"

@interface ItemSelectionViewController ()

@end

@implementation ItemSelectionViewController

@synthesize quizButton, slideButton, selectedVertical, verticalTitle;

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization

    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];

    self.title = selectedVertical.name;
    quizButton.hidden = !selectedVertical.hasQuizzes;
    slideButton.hidden = !selectedVertical.hasSlideshows;
    
    // Do any additional setup after loading the view from its nib.
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (IBAction)slidesTap:(UIButton *)sender {
    [self performSegueWithIdentifier:@"DoSlide" sender:self];
    
}

- (IBAction)quizzesTap:(UIButton *)sender {
    [self performSegueWithIdentifier:@"DoQuizz" sender:self];

}

#pragma mark -
#pragma mark Table view selection
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    
    UIViewController *viewController = [segue destinationViewController];
    
    if ([segue.identifier isEqualToString:@"DoSlide"])
    {
        ((SlideShowViewController *)viewController).selectedVertical = selectedVertical;
    }
    else
    {
        ((QuizListViewController *)viewController).selectedVertical = selectedVertical;
    }
    
    
    
}
@end
