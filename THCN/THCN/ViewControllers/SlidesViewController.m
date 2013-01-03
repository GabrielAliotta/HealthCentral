//
//  SlidesViewController.m
//  THCN
//
//  Created by Juan Escudero on 12/12/12.
//  Copyright (c) 2012 Juan Escudero. All rights reserved.
//

#import "SlidesViewController.h"
#import "Slide.h"
#import "SlideItemView.h"

@interface SlidesViewController ()

@end

@implementation SlidesViewController

@synthesize slideScrollView, selectedVertical, selectedSlideShow, slideShowTitle;

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
    slideShowTitle.text = selectedSlideShow.title;
    
    self.title = selectedVertical.name;
    
    self.navigationItem.backBarButtonItem.title = @"Back";

    int numOfPages = [[selectedSlideShow slides] count];
    
    
    slideScrollView.delegate=self;
    slideScrollView.contentSize=CGSizeMake(slideScrollView.frame.size.width*numOfPages, 1);
    slideScrollView.pagingEnabled=YES;    
        
    self.slidePageControl.pageIndicatorImage = [UIImage imageNamed:@"HealthCentral-asset-slide-indicator-grey.png"];
    self.slidePageControl.currentPageIndicatorImage = [UIImage imageNamed:@"HealthCentral-asset-slide-indicator-blue.png"];

    self.slidePageControl.currentPage=0;
    self.slidePageControl.numberOfPages=numOfPages;

    int i = 0;
    for (Slide *slide in [selectedSlideShow slides])
    {
        
        SlideItemView *slideView = [[[NSBundle mainBundle] loadNibNamed:@"SlideItemView" owner:self options:nil] objectAtIndex:0];
        slideView.slideTitle.text = [NSString stringWithFormat:@"%d. %@", i+1, slide.title];
        slideView.slideText.text = slide.text;
        
        slideView.slideImage.image = [UIImage imageWithData:[NSData dataWithContentsOfURL:[NSURL URLWithString:[slide image]]]];
        
        slideView.scrollView.contentSize=CGSizeMake(slideScrollView.frame.size.width, slideView.slideText.frame.size.height + slideView.slideTitle.frame.size.height + slideView.slideImage.frame.size.height + 30);
        
        
        
        CGRect frame = slideScrollView.frame;
        frame.origin.x = frame.size.width * i;
        frame.origin.y = 0;
        slideView.frame = frame;
        
        [self.slideScrollView addSubview:slideView];
        
        i++;
    }
    

}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}


- (void)scrollViewDidScroll:(UIScrollView *)sender
{
    // We don't want a "feedback loop" between the UIPageControl and the scroll delegate in
    // which a scroll event generated from the user hitting the page control triggers updates from
    // the delegate method. We use a boolean to disable the delegate logic when the page control is used.
 
	
    // Switch the indicator when more than 50% of the previous/next page is visible
    CGFloat pageWidth = slideScrollView.frame.size.width;
    int page = floor((slideScrollView.contentOffset.x - pageWidth / 2) / pageWidth) + 1;
        self.slidePageControl.currentPage = page;
    
        
    // A possible optimization would be to unload the views+controllers which are no longer visible
}


- (IBAction)changePage:(id)sender {
    
    int page=    self.slidePageControl.currentPage;
    CGRect frame=slideScrollView.frame;
    frame.origin.x=frame.size.width*page;
    frame.origin.y=0;
    [slideScrollView scrollRectToVisible:frame animated:YES];
}


@end
