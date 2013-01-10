//
//  QuizListViewController.m
//  THCN
//
//  Created by Juan Escudero on 12/13/12.
//  Copyright (c) 2012 Juan Escudero. All rights reserved.
//

#import "QuizListViewController.h"
#import "QuizListCellView.h"
#import "ProgressViewViewController.h"
#import "QuizNetworkEngine.h"
#import "Quizz.h"
#import "StaticGetObjects.h"
#import "QuizzViewController.h"

@interface QuizListViewController ()
{

    NSArray *datasource;
    Quizz *selectedQuiz;
    ProgressViewViewController *pvc;

}
@end

@implementation QuizListViewController

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
        self.quizListTableView.dataSource = self;
        self.quizListTableView.delegate = self;
        
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];

    // Do any additional setup after loading the view from its nib.
    
    pvc = [[ProgressViewViewController alloc]initWithNibName:@"ProgressViewViewController" bundle:nil];
    
    [self.view addSubview:pvc.view];
    
    self.title = selectedQuiz.title;
    
    NSMutableDictionary *headerFields = [NSMutableDictionary dictionary];
    [headerFields setValue:@"iOS" forKey:@"x-client-identifier"];
    
    QuizNetworkEngine * engine = [[QuizNetworkEngine alloc]initWithHostName:@"190.3.107.106"];
    
    [engine getQuizes:[self.selectedVertical _id] withCompletionHandler:^(NSArray *list) {
        
        //unhide the loader
        [pvc.view removeFromSuperview];
        
        datasource = list;
        
        [self.quizListTableView reloadData];
        
    } errorHandler:^(NSError *error) {
        
        //unhide the loader
        [pvc.view removeFromSuperview];
        
        //[UIAlertView showWithError:error];
        
        NSLog(@"%@\t%@\t%@\t%@", [error localizedDescription], [error localizedFailureReason],
              [error localizedRecoveryOptions], [error localizedRecoverySuggestion]);
        
        StaticGetObjects *staticObjects = [[StaticGetObjects alloc]init];
        
        datasource = [staticObjects getStaticQuizList];
        
        [self.quizListTableView reloadData];
        
    }];

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
    return datasource.count;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString *CellIdentifier = @"Cell";
    
    QuizListCellView  *cell = nil;
    
    cell = (QuizListCellView *) [self.quizListTableView dequeueReusableCellWithIdentifier:CellIdentifier];
    
    if(!cell)
    {
        
        NSArray *topLevelObjects = [[NSBundle mainBundle] loadNibNamed:@"QuizListCellView" owner:nil options:nil];
        
        for(id currentObject in topLevelObjects)
        {
            if([currentObject isKindOfClass:[QuizListCellView class]])
            {
                cell = (QuizListCellView *)currentObject;
                break;
            }
        }
    }
    
    
    Quizz * quizz = [datasource objectAtIndex:indexPath.row];
    cell.quizzText.text = [quizz title];
    cell.quizzImage.image = [[UIImage alloc] initWithData:[quizz imageData]];
    cell.accessoryView = [[ UIImageView alloc ]
                          initWithImage:[UIImage imageNamed:@"HealthCentral-asset-row-arrow.png" ]];
    return cell;

}


#pragma mark - Table view delegate

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    selectedQuiz = [datasource objectAtIndex:indexPath.row];
    [self performSegueWithIdentifier:@"ExecuteQuiz" sender:self];
    
}

#pragma mark -
#pragma mark Table view selection
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    
    UIViewController *viewController = [segue destinationViewController];
    ((QuizzViewController *)viewController).selectedQuiz = selectedQuiz;
    
}
@end
