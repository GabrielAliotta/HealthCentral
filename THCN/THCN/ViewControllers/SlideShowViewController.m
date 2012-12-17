//
//  SlideShowViewController.m
//  THCN
//
//  Created by Juan Escudero on 12/5/12.
//  Copyright (c) 2012 Juan Escudero. All rights reserved.
//

#import "SlideShowViewController.h"
#import "SlidesNetworkEngine.h"
#import "StaticGetObjects.h"
#import "SlideShow.h"
#import "SlideShowCellView.h"
#import "SlidesViewController.h"
#import "ProgressViewViewController.h"

@interface SlideShowViewController ()
{
    NSArray *datasource;
    SlideShow *selectedSlideShow;
    ProgressViewViewController *pvc;

}

@end

@implementation SlideShowViewController

@synthesize selectedVertical;

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
        self.slideShowTableView.dataSource = self;
        self.slideShowTableView.delegate = self;
        
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
    
    pvc = [[ProgressViewViewController alloc]initWithNibName:@"ProgressViewViewController" bundle:nil];
    
    [self.view addSubview:pvc.view];
    
    self.title = selectedSlideShow.title;

    NSMutableDictionary *headerFields = [NSMutableDictionary dictionary];
    [headerFields setValue:@"iOS" forKey:@"x-client-identifier"];
    
    SlidesNetworkEngine * s = [[SlidesNetworkEngine alloc]initWithHostName:@"thcn-db01.bar.tpg.corp"];
    
    [s getSlides:[self.selectedVertical _id] withCompletionHandler:^(NSArray *list) {
        
        //unhide the loader
        [pvc.view removeFromSuperview];

        datasource = list;
        
        [self.slideShowTableView reloadData];
        
    } errorHandler:^(NSError *error) {
        
        //unhide the loader
        [pvc.view removeFromSuperview];

        [UIAlertView showWithError:error];

        NSLog(@"%@\t%@\t%@\t%@", [error localizedDescription], [error localizedFailureReason],
              [error localizedRecoveryOptions], [error localizedRecoverySuggestion]);

        StaticGetObjects *staticObjects = [[StaticGetObjects alloc]init];
        
        datasource = [staticObjects getStaticSlideshows];
        
        [self.slideShowTableView reloadData];
        
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
    
    SlideShowCellView  *cell = nil;
    
    cell = (SlideShowCellView *) [self.slideShowTableView dequeueReusableCellWithIdentifier:CellIdentifier];
    
    
    if(!cell)
    {
        
        NSArray *topLevelObjects = [[NSBundle mainBundle] loadNibNamed:@"SlideShowCellView" owner:nil options:nil];
        
        for(id currentObject in topLevelObjects)
        {
            if([currentObject isKindOfClass:[SlideShowCellView class]])
            {
                cell = (SlideShowCellView *)currentObject;
                break;
            }
        }
    }
    
    
    SlideShow * slideShow = [datasource objectAtIndex:indexPath.row];
    cell.slideText.text = [slideShow title];
    cell.slideImage.image = [[UIImage alloc] initWithData:[slideShow imageData]];
    return cell;
}

/*
 // Override to support conditional editing of the table view.
 - (BOOL)tableView:(UITableView *)tableView canEditRowAtIndexPath:(NSIndexPath *)indexPath
 {
 // Return NO if you do not want the specified item to be editable.
 return YES;
 }
 */

/*
 // Override to support editing the table view.
 - (void)tableView:(UITableView *)tableView commitEditingStyle:(UITableViewCellEditingStyle)editingStyle forRowAtIndexPath:(NSIndexPath *)indexPath
 {
 if (editingStyle == UITableViewCellEditingStyleDelete) {
 // Delete the row from the data source
 [tableView deleteRowsAtIndexPaths:@[indexPath] withRowAnimation:UITableViewRowAnimationFade];
 }
 else if (editingStyle == UITableViewCellEditingStyleInsert) {
 // Create a new instance of the appropriate class, insert it into the array, and add a new row to the table view
 }
 }
 */

/*
 // Override to support rearranging the table view.
 - (void)tableView:(UITableView *)tableView moveRowAtIndexPath:(NSIndexPath *)fromIndexPath toIndexPath:(NSIndexPath *)toIndexPath
 {
 }
 */

/*
 // Override to support conditional rearranging of the table view.
 - (BOOL)tableView:(UITableView *)tableView canMoveRowAtIndexPath:(NSIndexPath *)indexPath
 {
 // Return NO if you do not want the item to be re-orderable.
 return YES;
 }
 */

#pragma mark - Table view delegate

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    selectedSlideShow = [datasource objectAtIndex:indexPath.row];
    [self performSegueWithIdentifier:@"ViewSlideShow" sender:self];

}

#pragma mark -
#pragma mark Table view selection
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    
    UIViewController *viewController = [segue destinationViewController];
    ((SlidesViewController *)viewController).selectedSlideShow = selectedSlideShow;
    
}

@end
