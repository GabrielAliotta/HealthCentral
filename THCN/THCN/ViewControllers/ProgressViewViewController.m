//
//  ProgressViewViewController.m
//  THCN
//
//  Created by Juan Escudero on 12/10/12.
//  Copyright (c) 2012 Juan Escudero. All rights reserved.
//

#import "ProgressViewViewController.h"

@interface ProgressViewViewController ()

@end

@implementation ProgressViewViewController

@synthesize activityIndicator;

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
    // Do any additional setup after loading the view from its nib.
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (void)showIndicator
{
    [activityIndicator startAnimating];
}

-(void)hideIndicator{

    [activityIndicator stopAnimating];
}

@end
