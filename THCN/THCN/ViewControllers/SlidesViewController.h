//
//  SlidesViewController.h
//  THCN
//
//  Created by Juan Escudero on 12/12/12.
//  Copyright (c) 2012 Juan Escudero. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "SlideShow.h"

@interface SlidesViewController : UIViewController<UIScrollViewDelegate>

@property (strong, nonatomic) SlideShow  *selectedSlideShow;
@property (weak, nonatomic) IBOutlet UIScrollView *slideScrollView;
@property (weak, nonatomic) IBOutlet UIPageControl *slidePageControl;
@property (weak, nonatomic) IBOutlet UILabel *slideShowTitle;

- (IBAction)changePage:(id)sender;

@end
