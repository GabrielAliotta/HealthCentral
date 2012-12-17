//
//  StaticGetObjects.m
//  THCN
//
//  Created by Juan Escudero on 12/10/12.
//  Copyright (c) 2012 Juan Escudero. All rights reserved.
//

#import "StaticGetObjects.h"
#import "Vertical.h"
#import "SlideShow.h"
#import "Slide.h"
#import "Quizz.h"
#import "QuizQuestion.h"
#import "QuizAnswer.h"

@implementation StaticGetObjects

-(NSArray*)getStaticVerticals{

    Vertical * v1 = [[Vertical alloc]init];
    v1._id = @"1";
    v1.name = @"Vertical 1";
    v1.imageURL = @"";
    v1.hasQuizzes = TRUE;
    v1.hasSlideshows = TRUE;

    Vertical * v2 = [[Vertical alloc]init];
    v2._id = @"2";
    v2.name = @"Vertical 2";
    v2.imageURL = @"";
    v2.hasQuizzes = TRUE;
    v2.hasSlideshows = TRUE;
    
    Vertical * v3 = [[Vertical alloc]init];
    v3._id = @"3";
    v3.name = @"Vertical 3";
    v3.imageURL = @"";
    v3.hasQuizzes = TRUE;
    v3.hasSlideshows = TRUE;
    
    Vertical * v4 = [[Vertical alloc]init];
    v4._id = @"4";
    v4.name = @"Vertical 4";
    v4.imageURL = @"";
    v4.hasQuizzes = TRUE;
    v4.hasSlideshows = TRUE;
    
    NSArray * list = [NSArray arrayWithObjects:v1,v2, v3, v4, nil];
    
    return list;
}

-(NSArray*)getStaticSlideshows{

    SlideShow *ss1 = [[SlideShow alloc]init];
    
    ss1._id = @"ss1";
    ss1.title = @"Slide Show 1";
    ss1.text = @"Slide Show 1";
    ss1.image = @"http://us-wdc.img.e-planning.net/esb/4/1/40a8/c18a96e55248b457.jpg";
    ss1.slides = [self getSlidesSlides];
    
    SlideShow *ss2 = [[SlideShow alloc]init];
    
    ss2._id = @"ss1";
    ss2.title = @"Slide Show 1";
    ss2.text = @"Slide Show 1";
    ss2.image = @"http://gloriapinskerdogtraining.com/images/Blinky_Dog.gif";
    ss2.slides = [self getSlidesSlides];
    
    SlideShow *ss3 = [[SlideShow alloc]init];
    
    ss3._id = @"ss1";
    ss3.title = @"Slide Show 1";
    ss3.text = @"Slide Show 1";
    ss3.image = @"http://animal.discovery.com/images/breed-selector/dogs/groups/lap-dogs_sm.jpg";
    ss3.slides = [self getSlidesSlides];
    
    SlideShow *ss4 = [[SlideShow alloc]init];
    
    ss4._id = @"ss1";
    ss4.title = @"Slide Show 1";
    ss4.text = @"Slide Show 1";
    ss4.image = @"http://upload.wikimedia.org/wikipedia/commons/thumb/4/43/Timba%2B1.jpg/220px-Timba%2B1.jpg";
    ss4.slides = [self getSlidesSlides];
    
    NSArray * list = [NSArray arrayWithObjects:ss1,ss2, ss3, ss4, nil];
    
    return list;
}

-(NSArray*)getSlidesSlides{

    Slide * s1 = [[Slide alloc]init];
    s1._id = @"slide 1";
    s1.text = @"tetx 1";
    s1.title = @"Slide Title 1";
    s1.image = @"s1";
    
    Slide * s2 = [[Slide alloc]init];
    s2._id = @"s2";
    s2.text = @"tetx 2";
    s2.title = @"Slide Title 2";
    s2.image = @"s2";
    
    Slide * s3 = [[Slide alloc]init];
    s3._id = @"s3";
    s3.text = @"tetx 3";
    s3.title = @"Slide Title 3";
    s3.image = @"s3";
    
    Slide * s4 = [[Slide alloc]init];
    s4._id = @"s4";
    s4.text = @"tetx 4";
    s4.title = @"Slide Title 4";
    s4.image = @"s4";
    
    Slide * s5 = [[Slide alloc]init];
    s5._id = @"s5";
    s5.text = @"tetx 5";
    s5.title = @"Slide Title 5";
    s5.image = @"s5";
    
    Slide * s6 = [[Slide alloc]init];
    s6._id = @"s6";
    s6.text = @"tetx 6";
    s6.title = @"Slide Title 6";
    s6.image = @"s6";
    
    NSArray * list = [NSArray arrayWithObjects:s1,s2, s3, s4, s5, s6, nil];
    
    return list;
}

-(NSArray *)getStaticQuizList
{

    Quizz * q1 = [[Quizz alloc]init];
    q1.text = @"1";
    q1.title = @"t1";
    q1._id = @"t1";
    q1.image = @"";
    q1.nextQuizId = @"2";
    q1.questions = [self getQuizQuestion];
    
    Quizz * q2 = [[Quizz alloc]init];
    q2.text = @"2";
    q2.title = @"t2";
    q2._id = @"t2";
    q2.image = @"";
    q2.nextQuizId = @"3";
    q2.questions = [self getQuizQuestion];
    
    Quizz * q3 = [[Quizz alloc]init];
    q3.text = @"3";
    q3.title = @"t3";
    q3._id = @"t3";
    q3.image = @"";
    q3.nextQuizId = @"3";
    q3.questions = [self getQuizQuestion];
    
    Quizz * q4 = [[Quizz alloc]init];
    q4.text = @"4";
    q4.title = @"t4";
    q4._id = @"t4";
    q4.image = @"";
    q4.nextQuizId = @"4";
    q4.questions = [self getQuizQuestion];
    
    NSArray * list = [NSArray arrayWithObjects:q1, q2, q3, q4, nil];
    
    return list;
}

-(NSArray *)getQuizAnswer{

    QuizAnswer *qa1 = [[QuizAnswer alloc]init];
    qa1.title = @"Is this TRUE";
    qa1.isValid = TRUE;

    QuizAnswer *qa2 = [[QuizAnswer alloc]init];
    qa2.title = @"This is false";
    qa2.isValid = FALSE;
    
    
    QuizAnswer *qa3 = [[QuizAnswer alloc]init];
    qa3.title = @"This is an example";
    qa3.isValid = FALSE;
    
    NSArray * list = [NSArray arrayWithObjects:qa1, qa2, qa3, nil];
    
    return list;
    
}


-(NSArray *)getQuizQuestion{
    
    QuizQuestion * qq1 = [[QuizQuestion alloc]init];
    qq1.title = @"Does peppermint and peppermint flavored food cause acid reflux?";
    qq1.text = @"<p>Peppermint and peppermint flavored foods can cause acid reflux symptoms in some people.<\/p>";
    qq1.image = @"http:\/\/thcn-db01.bar.tpg.corp\/files\/8113\/3339\/1205\/peppermint_300x300_stock.xchng.jpg";
    qq1.answers = [self getQuizAnswer];
    
    QuizQuestion * qq2 = [[QuizQuestion alloc]init];
    qq2.title = @"Does steak and other lean meat cause heartburn?";
    qq2.text = @"<p>A lean steak will usually not cause acid reflux symptoms.&nbsp; Enjoy!<\/p>";
    qq2.image = @"http:\/\/thcn-db01.bar.tpg.corp\/files\/5113\/3339\/0989\/steak_300x300_stock.xchng.jpg";
    qq2.answers = [self getQuizAnswer];
    
    QuizQuestion * qq3 = [[QuizQuestion alloc]init];
    qq3.title = @"Does tea and coffee cause heartburn?";
    qq3.text = @"<p>Tea and coffee, both caffeinated and decaffeinated will cause reflux symptoms.<\/p>";
    qq3.image = @"http:\/\/thcn-db01.bar.tpg.corp\/files\/8113\/1050\/0182\/caffeine_coffee_chocolate_tea_300x300_istock.jpg";
    qq3.answers = [self getQuizAnswer];
    
    QuizQuestion * qq4 = [[QuizQuestion alloc]init];
    qq4.title = @"Do oranges cause heartburn?";
    qq4.text = @"<p>Citrus fruits such as oranges, grapefruits and lemons will cause reflux symptoms.<\/p>";
    qq4.image = @"http:\/\/thcn-db01.bar.tpg.corp\/files\/9213\/1619\/7171\/orange_300x300_istock.jpg";
    qq4.answers = [self getQuizAnswer];
    
    QuizQuestion * qq5 = [[QuizQuestion alloc]init];
    qq5.title = @"Does wine or beer cause heartburn?";
    qq5.text = @"<p>Wine and any alcoholic drink will aggravate reflux symptoms.<\/p>";
    qq5.image = @"http:\/\/thcn-db01.bar.tpg.corp\/files\/4613\/0711\/9628\/wineglasses.jpg";
    qq5.answers = [self getQuizAnswer];
    
    QuizQuestion * qq6 = [[QuizQuestion alloc]init];
    qq6.title = @"Do tomatoes cause heartburn?";
    qq6.text = @"<p>Tomatoes and most tomato-based foods are highly acidic and will cause acid reflux.<\/p>";
    qq6.image = @"http:\/\/thcn-db01.bar.tpg.corp\/files\/3013\/0989\/0380\/tomatoes_300x300_istock.jpg";
    qq6.answers = [self getQuizAnswer];
    
    NSArray * list = [NSArray arrayWithObjects:qq1, qq2, qq3, qq4, qq5, qq6, nil];
    
    return list;
    
}


@end
