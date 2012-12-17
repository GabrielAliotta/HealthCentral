//
//  VerticalsViewController.m
//  THCN
//
//  Created by Juan Escudero on 12/4/12.
//  Copyright (c) 2012 Juan Escudero. All rights reserved.
//

#import "VerticalsViewController.h"
#import "VerticalsNetworkEngine.h"
#import "ItemSelectionViewController.h"
#import "StaticGetObjects.h"
#import "ProgressViewViewController.h"

@interface VerticalsViewController ()
{
    NSArray *datasource;
    ProgressViewViewController *pvc;
}

@end

@implementation VerticalsViewController

@synthesize activityIndicator;

- (id)initWithStyle:(UITableViewStyle)style
{
    self = [super initWithStyle:style];
    if (self) {
        // Custom initialization
        
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    
    self.navigationController.navigationBar.tintColor = [UIColor colorWithRed:(0/255.0) green:(136/255.0) blue:(189/255.0) alpha:1];
    
    self.title = @"Health Central";
    
    NSMutableDictionary *headerFields = [NSMutableDictionary dictionary];
    [headerFields setValue:@"iOS" forKey:@"x-client-identifier"];

    VerticalsNetworkEngine * v = [[VerticalsNetworkEngine alloc]initWithHostName:@"thcn-db01.bar.tpg.corp"];

    pvc = [[ProgressViewViewController alloc]initWithNibName:@"ProgressViewViewController" bundle:nil];

    [self.view addSubview:pvc.view];

    [v getVerticalsWithCompletionHandler:^(NSArray *list) {
        
        //unhide the loader
        [pvc.view removeFromSuperview];

        //set the datasource
        datasource = list;
        
        [self.tableView reloadData];
        
    } errorHandler:^(NSError *error) {
        
        //unhide the loader
        [pvc.view removeFromSuperview];
        
        //show allert
        [UIAlertView showWithError:error];
        
        NSLog(@"%@\t%@\t%@\t%@", [error localizedDescription], [error localizedFailureReason],
            [error localizedRecoveryOptions], [error localizedRecoverySuggestion]);
        
        StaticGetObjects *staticObjects = [[StaticGetObjects alloc]init];
        
        datasource = [staticObjects getStaticVerticals];
        
        [self.tableView reloadData];

    }];
    
    // Uncomment the following line to preserve selection between presentations.
    // self.clearsSelectionOnViewWillAppear = NO;
 
    // Uncomment the following line to display an Edit button in the navigation bar for this view controller.
    // self.navigationItem.rightBarButtonItem = self.editButtonItem;
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
    
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:CellIdentifier];
    
    if(!cell)
    {
        cell = [[UITableViewCell alloc]
                initWithStyle:UITableViewCellStyleDefault
                reuseIdentifier:CellIdentifier];
    }
    
    // Configure the cell...
    UIView *selectionColor = [[UIView alloc] init];
    selectionColor.backgroundColor = [UIColor colorWithRed:(140/255.0) green:(198/255.0) blue:(1/255.0) alpha:1];
    cell.selectedBackgroundView = selectionColor;
    cell.textLabel.text = [(Vertical*)[datasource objectAtIndex:indexPath.row] name];
    cell.textLabel.textColor = [UIColor colorWithRed:(0/255.0) green:(136/255.0) blue:(189/255.0) alpha:1];
    
    
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
    // Navigation logic may go here. Create and push another view controller.
    /*
     <#DetailViewController#> *detailViewController = [[<#DetailViewController#> alloc] initWithNibName:@"<#Nib name#>" bundle:nil];
     // ...
     // Pass the selected object to the new view controller.
     [self.navigationController pushViewController:detailViewController animated:YES];
     */
}


#pragma mark -
#pragma mark Table view selection
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    
    /*
     When a row is selected, the segue creates the detail view controller as the destination.
     Set the detail view controller's detail item to the item associated with the selected row.
     */
    if ([[segue identifier] isEqualToString:@"ShowSlectionItems"]) {
        
        ItemSelectionViewController *itemSelectionViewController = [segue destinationViewController];
        itemSelectionViewController.selectedVertical = [datasource objectAtIndex:self.tableView.indexPathForSelectedRow.row];
    }
}

@end
