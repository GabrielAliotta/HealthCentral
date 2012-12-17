//
//  QuizListViewController.h
//  THCN
//
//  Created by Juan Escudero on 12/13/12.
//  Copyright (c) 2012 Juan Escudero. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "Vertical.h"

@interface QuizListViewController : UIViewController<UITableViewDataSource, UITableViewDelegate>

@property (weak, nonatomic) IBOutlet UITableView *quizListTableView;
@property (strong, nonatomic) Vertical  *selectedVertical;

@end
