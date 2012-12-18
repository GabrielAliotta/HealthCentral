//
//  SlideItemView.h
//  THCN
//
//  Created by Juan Escudero on 12/12/12.
//  Copyright (c) 2012 Juan Escudero. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface SlideItemView : UIView

@property (weak, nonatomic) IBOutlet UILabel *slideTitle;
@property (weak, nonatomic) IBOutlet UIImageView *slideImage;
@property (weak, nonatomic) IBOutlet UIWebView *slideContent;
@property (weak, nonatomic) IBOutlet UIScrollView *scrollView;


@end
