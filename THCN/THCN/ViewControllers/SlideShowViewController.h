//
//  SlideShowViewController.h
//  THCN
//
//  Created by Juan Escudero on 12/12/12.
//  Copyright (c) 2012 Juan Escudero. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "Vertical.h"

@interface SlideShowViewController : UIViewController<UITableViewDataSource, UITableViewDelegate>

@property (weak, nonatomic) IBOutlet UITableView *slideShowTableView;
@property (strong, nonatomic) Vertical  *selectedVertical;

@end
