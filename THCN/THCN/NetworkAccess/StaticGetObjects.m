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
    v1._id = @"acid-reflux";
    v1.name = @"Acid Reflux";
    v1.imageURL = @"http:\/\/www.healthcentral.com\/about\/wp-content\/uploads\/2009\/06\/apple_150x150.gif";
    v1.hasQuizzes = TRUE;
    v1.hasSlideshows = TRUE;

    Vertical * v2 = [[Vertical alloc]init];
    v2._id = @"adhd";
    v2.name = @"ADHD";
    v2.imageURL = @"http:\/\/www.healthcentral.com\/about\/wp-content\/uploads\/2009\/06\/apple_150x150.gif";
    v2.hasQuizzes = TRUE;
    v2.hasSlideshows = TRUE;
    
    Vertical * v3 = [[Vertical alloc]init];
    v3._id = @"allergy";
    
    v3.name = @"Allergy";
    v3.imageURL = @"http:\/\/www.healthcentral.com\/about\/wp-content\/uploads\/2009\/06\/apple_150x150.gif";
    v3.hasQuizzes = TRUE;
    v3.hasSlideshows = TRUE;
    
    Vertical * v4 = [[Vertical alloc]init];
    v4._id = @"alzheimers";
    v4.name = @"Alzheimer's";
    v4.imageURL = @"http:\/\/www.healthcentral.com\/about\/wp-content\/uploads\/2009\/06\/apple_150x150.gif";
    v4.hasQuizzes = TRUE;
    v4.hasSlideshows = TRUE;
    
    NSArray * list = [NSArray arrayWithObjects:v1,v2, v3, v4, nil];
    
    return list;
}

-(NSArray*)getStaticSlideshows{

    SlideShow *ss1 = [[SlideShow alloc]init];
    
    ss1._id = @"1620";
    ss1.title = @"10 Foods to Avoid with Acid Reflux";
    ss1.text = @"Acid reflux and Heartburn symptoms are often triggered by specific foods.  While every person's individual food sensitivities are different these foods are most likely to cause acid reflux symptoms.";
    ss1.image = @"http:\/\/thcn-db01.bar.tpg.corp\/files\/4413\/1049\/7288\/coffee_300x300.jpg";
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
    s1._id = @"1626";
    s1.text = @"Coffee";
    s1.title = @"<p><span>Caffeinated beverages such as coffee, some teas and soda will trigger Acid Reflux symptoms for most people. &nbsp;They are best avoided all together. &nbsp;Try a run or yoga for your morning jolt instead.&nbsp;<\/span><\/p>";
    s1.image = @"http:\/\/thcn-db01.bar.tpg.corp\/files\/4413\/1049\/7288\/coffee_300x300.jpg";
    
    Slide * s2 = [[Slide alloc]init];
    s2._id = @"1627";
    s2.text = @"Cheesecake";
    s2.title = @"<p>The high fat content in cheesecake will trigger reflux symptoms.&nbsp;<\/p>";
    s2.image = @"http:\/\/thcn-db01.bar.tpg.corp\/files\/7013\/1049\/8689\/cheesecake_300x300_istock.jpg";
    
    Slide * s3 = [[Slide alloc]init];
    s3._id = @"1630";
    s3.text = @"Broccoli";
    s3.title = @"p>If your acid reflux is associated with gas and indegestion, foods like broccoli will add gas to your digestive system triggering reflux symptoms.<\/p>";
    s3.image = @"http:\/\/thcn-db01.bar.tpg.corp\/files\/9813\/1050\/1043\/broccoli2_300x300_stockxchng.jpg";
    
    Slide * s4 = [[Slide alloc]init];
    s4._id = @"1632";
    s4.text = @"Citrus Fruits";
    s4.title = @"<p>The acid present in citrus fruits will trigger reflux symptoms.<\/p>";
    s4.image = @"http:\/\/thcn-db01.bar.tpg.corp\/files\/4713\/1050\/0908\/citrus_fruit_300x300_istock.jpg";
    
    Slide * s5 = [[Slide alloc]init];
    s5._id = @"1635";
    s5.text = @"Alcohol";
    s5.title = @"<p>Beer, wine and liquor can all cause severe acid reflux symptoms.<\/p>";
    s5.image = @"http:\/\/thcn-db01.bar.tpg.corp\/files\/6713\/1050\/3632\/beer_300x300_stock.xchng.jpg";
    
    Slide * s6 = [[Slide alloc]init];
    s6._id = @"1636";
    s6.text = @"Spicy Foods";
    s6.title = @"<p>Spicy foods (chili, curry, etc.) are among the most common causes of acid reflux symptoms in Americans.&nbsp; If you suffer from any kind of reflux, it is best to avoid the heat.<\/p>";
    s6.image = @"http:\/\/thcn-db01.bar.tpg.corp\/files\/3013\/1056\/8158\/chilies_300x300_stock.xchng.jpg";
    
    NSArray * list = [NSArray arrayWithObjects:s1,s2, s3, s4, s5, s6, nil];
    
    return list;
}

-(NSArray *)getStaticQuizList
{

    Quizz * q1 = [[Quizz alloc]init];
    q1.text = @"Heartburn Trigger Foods";
    q1.title = @"One way to control acid reflux and heartburn symptoms is to know which foods are most likely to cause reflux.  Do you know which of these foods are common reflux triggers? ";
    q1._id = @"t1";
    q1.image = @"http:\/\/thcn-db01.bar.tpg.corp\/files\/8513\/2346\/2716\/question_mark_2_300x300_stock.xchng.jpg";
    q1.nextQuizId = @"4159";
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
    qa1.title = @"True";
    qa1.isValid = TRUE;

    QuizAnswer *qa2 = [[QuizAnswer alloc]init];
    qa2.title = @"Flase";
    qa2.isValid = FALSE;
    
    NSArray * list = [NSArray arrayWithObjects:qa1, qa2, nil];
    
    return list;
    
}


-(NSArray *)getQuizQuestion{
    
    QuizQuestion * qq1 = [[QuizQuestion alloc]init];
    qq1.title = @"Does peppermint and peppermint flavored food cause acid reflux?";
    qq1.text = @"<p>Peppermint and peppermint flavored foods can cause acid reflux symptoms in some people.<\/p>";
    qq1.image = @"http:\/\/190.3.107.106\/files\/8113\/3339\/1205\/peppermint_300x300_stock.xchng.jpg";
    qq1.answers = [self getQuizAnswer];
    
    QuizQuestion * qq2 = [[QuizQuestion alloc]init];
    qq2.title = @"Does steak and other lean meat cause heartburn?";
    qq2.text = @"<p>A lean steak will usually not cause acid reflux symptoms.&nbsp; Enjoy!<\/p>";
    qq2.image = @"http:\/\/190.3.107.106\/files\/5113\/3339\/0989\/steak_300x300_stock.xchng.jpg";
    qq2.answers = [self getQuizAnswer];
    
    QuizQuestion * qq3 = [[QuizQuestion alloc]init];
    qq3.title = @"Does tea and coffee cause heartburn?";
    qq3.text = @"<p>Tea and coffee, both caffeinated and decaffeinated will cause reflux symptoms.<\/p>";
    qq3.image = @"http:\/\/190.3.107.106\/files\/8113\/1050\/0182\/caffeine_coffee_chocolate_tea_300x300_istock.jpg";
    qq3.answers = [self getQuizAnswer];
    
    QuizQuestion * qq4 = [[QuizQuestion alloc]init];
    qq4.title = @"Do oranges cause heartburn?";
    qq4.text = @"<p>Citrus fruits such as oranges, grapefruits and lemons will cause reflux symptoms.<\/p>";
    qq4.image = @"http:\/\/190.3.107.106\/files\/9213\/1619\/7171\/orange_300x300_istock.jpg";
    qq4.answers = [self getQuizAnswer];
    
    QuizQuestion * qq5 = [[QuizQuestion alloc]init];
    qq5.title = @"Does wine or beer cause heartburn?";
    qq5.text = @"<p>Wine and any alcoholic drink will aggravate reflux symptoms.<\/p>";
    qq5.image = @"http:\/\/190.3.107.106\/files\/4613\/0711\/9628\/wineglasses.jpg";
    qq5.answers = [self getQuizAnswer];
    
    QuizQuestion * qq6 = [[QuizQuestion alloc]init];
    qq6.title = @"Do tomatoes cause heartburn?";
    qq6.text = @"<p>Tomatoes and most tomato-based foods are highly acidic and will cause acid reflux.<\/p>";
    qq6.image = @"http:\/\/190.3.107.106\/files\/3013\/0989\/0380\/tomatoes_300x300_istock.jpg";
    qq6.answers = [self getQuizAnswer];
    
    NSArray * list = [NSArray arrayWithObjects:qq1, qq2, qq3, qq4, qq5, qq6, nil];
    
    return list;
    
}


@end
