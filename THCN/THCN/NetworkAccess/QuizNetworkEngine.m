//
//  QuizNetworkEngine.m
//  THCN
//
//  Created by Juan Escudero on 12/13/12.
//  Copyright (c) 2012 Juan Escudero. All rights reserved.
//

#import "QuizNetworkEngine.h"
#import "Quizz.h"
#import "QuizQuestion.h"

#define kQuizsURL  @"/index.php/tools/mobile?vertical=%@&contentType=quiz"
#define kQuizIdURL  @"/index.php/tools/mobile?contentType=quiz&id=%@&json=true"


@implementation QuizNetworkEngine

-(MKNetworkOperation*) getQuizes:(NSString*)idVertical  withCompletionHandler:(QuizResponseBlock) completionBlock  errorHandler:(MKNKErrorBlock) errorBlock
{
    
    NSString *path = [NSString stringWithFormat:kQuizsURL, idVertical];
    
    MKNetworkOperation *op = [self operationWithPath:path
                                              params:nil
                                          httpMethod:@"GET"];
    
    [op addCompletionHandler:^(MKNetworkOperation *completedOperation)
     {
         // the completionBlock will be called twice.
         // if you are interested only in new values, move that code within the else block
         
         if([completedOperation isCachedResponse]) {
             DLog(@"Data from cache %@", [completedOperation responseJSON]);
         }
         else {
             DLog(@"Data from server %@", [completedOperation responseJSON]);
         }
         
         NSMutableArray *responseList = [[completedOperation responseJSON] objectForKey: @"items"];
         
         NSMutableArray *quizList = [[NSMutableArray alloc]init];
         
         for (NSDictionary* element in responseList) {
             Quizz * quiz = [[Quizz alloc]initWithAttributes:[element objectForKey:@"item"]];
             
             quiz.imageData = [NSData dataWithContentsOfURL:[NSURL URLWithString:[quiz image]]];
             
             for (QuizQuestion *qa in quiz.questions)
             {
                 qa.imageData = [NSData dataWithContentsOfURL:[NSURL URLWithString:[qa image]]];
             }
             
             [quizList addObject:quiz];
         }
         
         for (Quizz *quiz in quizList){
             
             for (Quizz *quizAux in quizList) {
                 if (quiz.nextQuizId == quiz._id)
                 {
                     quiz.nextQuiz = quizAux;
                     break;
                 }
             }
         }

         completionBlock([NSArray arrayWithArray:quizList]);
         
     }errorHandler:^(MKNetworkOperation *errorOp, NSError* error) {
         
         errorBlock(error);
     }];
    
    [self enqueueOperation:op];
    
    return op;
}

-(MKNetworkOperation*) getQuiz:(NSString*)idQuiz  withCompletionHandler:(QuizIdResponseBlock) completionBlock  errorHandler:(MKNKErrorBlock) errorBlock
{
    
    NSString *path = [NSString stringWithFormat:kQuizIdURL, idQuiz];
    
    MKNetworkOperation *op = [self operationWithPath:path
                                              params:nil
                                          httpMethod:@"GET"];
    
    [op addCompletionHandler:^(MKNetworkOperation *completedOperation)
     {
         // the completionBlock will be called twice.
         // if you are interested only in new values, move that code within the else block
         
         if([completedOperation isCachedResponse]) {
             DLog(@"Data from cache %@", [completedOperation responseJSON]);
         }
         else {
             DLog(@"Data from server %@", [completedOperation responseJSON]);
         }
         
         NSMutableArray *responseList = [[completedOperation responseJSON] objectForKey: @"items"];

         Quizz * quiz;
         
         for (NSDictionary* element in responseList) {
             quiz = [[Quizz alloc]initWithAttributes:[element objectForKey:@"item"]];
             
             quiz.imageData = [NSData dataWithContentsOfURL:[NSURL URLWithString:[quiz image]]];
             
             for (QuizQuestion *qa in quiz.questions)
             {
                 qa.imageData = [NSData dataWithContentsOfURL:[NSURL URLWithString:[qa image]]];
             }
             
         }
         
         completionBlock(quiz);
         
     }errorHandler:^(MKNetworkOperation *errorOp, NSError* error) {
         
         errorBlock(error);
     }];
    
    [self enqueueOperation:op];
    
    return op;
}

@end
