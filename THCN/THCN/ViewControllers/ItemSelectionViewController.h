//
//  ItemSelectionViewController.h
//  THCN
//
//  Created by Juan Escudero on 12/5/12.
//  Copyright (c) 2012 Juan Escudero. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "Vertical.h"

@interface ItemSelectionViewController : UIViewController

    @property(nonatomic) Vertical* selectedVertical;
    @property (weak, nonatomic) IBOutlet UIButton *quizButton;
    @property (weak, nonatomic) IBOutlet UIButton *slideButton;
    @property (weak, nonatomic) IBOutlet UILabel *verticalTitle;
    - (IBAction)slidesTap:(UIButton *)sender;
    - (IBAction)quizzesTap:(UIButton *)sender;

@end
