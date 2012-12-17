//
//  QuizNetworkEngine.h
//  THCN
//
//  Created by Juan Escudero on 12/13/12.
//  Copyright (c) 2012 Juan Escudero. All rights reserved.
//

#import "MKNetworkEngine.h"
#import "Quizz.h"

@interface QuizNetworkEngine : MKNetworkEngine

typedef void (^QuizResponseBlock)(NSArray *list);
typedef void (^QuizIdResponseBlock)(Quizz *list);

-(MKNetworkOperation*) getQuizes:(NSString*)_idVertical  withCompletionHandler:(QuizResponseBlock) completionBlock  errorHandler:(MKNKErrorBlock) errorBlock;

-(MKNetworkOperation*) getQuiz:(NSString*)idQuiz  withCompletionHandler:(QuizIdResponseBlock) completionBlock  errorHandler:(MKNKErrorBlock) errorBlock;

@end
