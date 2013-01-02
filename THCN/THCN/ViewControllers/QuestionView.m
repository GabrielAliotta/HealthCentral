//
//  QuestionView.m
//  THCN
//
//  Created by Juan Escudero on 12/13/12.
//  Copyright (c) 2012 Juan Escudero. All rights reserved.
//

#import "QuestionView.h"
#import "QuizAnswer.h"
#import "QuestionCellView.h"

@implementation QuestionView

@synthesize questionText, questionOptions, question, selectedAnswer, delegate;

- (id)initWithFrame:(CGRect)frame
{
    self = [super initWithFrame:frame];
    if (self) {
        // Initialization code
    }
    return self;
}

/*
// Only override drawRect: if you perform custom drawing.
// An empty implementation adversely affects performance during animation.
- (void)drawRect:(CGRect)rect
{
    // Drawing code
}
*/

-(void)loadData{

    [self clearQuestionSelection];
    questionText.text = question.title;
    [questionOptions reloadData];
    
    
    /*
    UIImageView *tempImageView = [[UIImageView alloc] initWithImage:[UIImage imageWithData:question.imageData]];
    tempImageView.alpha = 0.5;
    
    [tempImageView setFrame:self.questionOptions.frame];
    
    self.questionOptions.backgroundView = tempImageView;*/
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
    return question.answers.count;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    
    static NSString *CellIdentifier = @"Cell";
    
    QuestionCellView  *cell = nil;
    
    cell = (QuestionCellView *) [self.questionOptions dequeueReusableCellWithIdentifier:CellIdentifier];
    
    if(!cell)
    {
        
        NSArray *topLevelObjects = [[NSBundle mainBundle] loadNibNamed:@"QuestionCellView" owner:nil options:nil];
        
        for(id currentObject in topLevelObjects)
        {
            if([currentObject isKindOfClass:[QuestionCellView class]])
            {
                cell = (QuestionCellView *)currentObject;
                break;
            }
        }
    }
    
    cell.questionText.text = [(QuizAnswer*)[question.answers objectAtIndex:indexPath.row] title];
    cell.selected = FALSE;
    
    return cell;

}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{    
    ((QuestionCellView*)[tableView cellForRowAtIndexPath:indexPath]).selected = TRUE;
    
    selectedAnswer = (QuizAnswer*)[question.answers objectAtIndex:indexPath.row];
}

-(void)clearQuestionSelection
{

    for (int i = 0; i < question.answers.count; i++)
    {
        [questionOptions cellForRowAtIndexPath:[NSIndexPath indexPathForRow:i inSection:0]].selected = FALSE;
    }
}

-(IBAction) submitQuestion:(id) sender{

    [delegate questionAnswered:selectedAnswer];
}

@end
