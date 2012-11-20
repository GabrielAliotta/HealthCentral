package com.heathcentral.service;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import org.kroz.activerecord.ActiveRecordBase;
import org.kroz.activerecord.ActiveRecordException;
import org.kroz.activerecord.Database;
import org.kroz.activerecord.DatabaseBuilder;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;

import com.heathcentral.model.Quiz;
import com.heathcentral.model.QuizLearnMoreLink;
import com.heathcentral.model.QuizQuestion;
import com.heathcentral.model.QuizQuestionAnswer;
import com.heathcentral.model.QuizResult;
import com.heathcentral.model.Slide;
import com.heathcentral.model.Slideshow;
import com.heathcentral.model.SlideshowImage;
import com.heathcentral.model.Vertical;

public class DatabaseController {

	private final static String dbName = "healthcentral.db";
	private final static int dbVersion = 19;
	private static Context ctx = null;
	private static DatabaseBuilder builder = null;
	private static ActiveRecordBase conn = null;

	@SuppressWarnings("static-access")
	public DatabaseController(Context context) {
		this.ctx = context;
	}

	public static void initDatabase() throws ActiveRecordException {
		builder = new DatabaseBuilder(dbName);
		builder.addClass(Slideshow.class);
		builder.addClass(SlideshowImage.class);
		builder.addClass(Quiz.class);
		builder.addClass(QuizQuestion.class);
		builder.addClass(QuizResult.class);
		builder.addClass(QuizQuestionAnswer.class);
		builder.addClass(QuizLearnMoreLink.class);
		builder.addClass(Vertical.class);
		builder.addClass(Slide.class);

		// Setup the builder

		Database.setBuilder(builder);
		conn = ActiveRecordBase.open(ctx, dbName, dbVersion);
	}

	public ActiveRecordBase getDatabase() throws ActiveRecordException {
		return conn;
	}

	public boolean getIsOpenDatabase() throws ActiveRecordException {
		return conn.isOpen();
	}

	// Vertical Methods

	public void saveVertical(Vertical vertical) {
		Vertical verticalToSave = null;

		try {
			if (!getIsOpenDatabase())
				getDatabase().open();

			verticalToSave = getDatabase().newEntity(Vertical.class);
			verticalToSave.setVerticalId(vertical.getVerticalId());
			verticalToSave.setVerticalName(vertical.getVerticalName());
			verticalToSave.setVerticalImageURL(vertical.getVerticalImageURL());
			verticalToSave.setVerticalImage(vertical.getVerticalImage());
			verticalToSave.setHasSlideshows(vertical.getHasSlideshows());
			verticalToSave.setHasQuizzes(vertical.getHasQuizzes());

			verticalToSave.save();
		} catch (ActiveRecordException e) {
			e.printStackTrace();
		}
	}

	public boolean VerticalLoaded(String verticalId) {
		boolean verticalLoaded = false;

		List<Vertical> verticalOnDb = null;

		try {
			if (getDatabase().isOpen() != true)
				getDatabase().open();
			verticalOnDb = getDatabase().findByColumn(Vertical.class,
					"vertical_id", verticalId);
			getDatabase().close();
			if (verticalOnDb.size() > 0)
				verticalLoaded = true;
		} catch (ActiveRecordException e) {
			e.printStackTrace();
		}
		return verticalLoaded;
	}

	public Vertical getVerticalById(String verticalId) {
		Vertical vertical = null;
		try {
			if (getDatabase().isOpen() != true)
				getDatabase().open();
			vertical = getDatabase().findByColumn(Vertical.class,
					"vertical_id", verticalId).get(0);
			getDatabase().close();
		} catch (ActiveRecordException e) {
			e.printStackTrace();
		}
		return vertical;
	}

	public List<Vertical> getVerticals() {
		List<Vertical> verticals = new ArrayList<Vertical>();
		try {
			if (getDatabase().isOpen() != true)
				getDatabase().open();
			verticals = getDatabase().find(Vertical.class, true, null, null,
					null, null, null, null);
			getDatabase().close();
		} catch (ActiveRecordException e) {
			e.printStackTrace();
		}
		return verticals;
	}

	// Slideshows Methods

	public boolean slideshowLoaded(String slideshowId) {
		boolean isLoaded = false;
		List<Slideshow> slideshowOnDb = null;
		try {
			if (getDatabase().isOpen() != true)
				getDatabase().open();
			slideshowOnDb = getDatabase().findByColumn(Slideshow.class,	"id", slideshowId);
			getDatabase().close();
			if (slideshowOnDb.size() > 0)
				isLoaded = true;
		} catch (ActiveRecordException e) {
			e.printStackTrace();
		}
		return isLoaded;
	}

	public void saveSlide(Slide slide) {
		Slide slideToSave = null;
		try {
			if (getDatabase().isOpen() != true)
				getDatabase().open();
			slideToSave = getDatabase().newEntity(Slide.class);
			slideToSave.setSlideshowId(slide.getSlideshowId());
			slideToSave.setTitle(slide.getTitle());
			slideToSave.setText(slide.getText());
			slideToSave.setId(slide.getId());
			slideToSave.setImage(slide.getImage());
			slideToSave.save();
			getDatabase().close();
		} catch (ActiveRecordException e) {
			e.printStackTrace();
		}
	}
	
	public void saveSlideshow(Slideshow slideshow) {
		Slideshow siteToSave = null;

		try {
			if (getDatabase().isOpen() != true)
				getDatabase().open();
			siteToSave = getDatabase().newEntity(Slideshow.class);
			siteToSave.setTitle(slideshow.getTitle());
			siteToSave.setBlurb(slideshow.getBlurb());
			siteToSave.setImage(slideshow.image);
			siteToSave.setImageUrl(slideshow.getImageUrl());
			siteToSave.setVertical(slideshow.getVertical());
			siteToSave.setId(slideshow.getId());
			siteToSave.save();

			getDatabase().close();
		} catch (ActiveRecordException e) {
			e.printStackTrace();
		}
	}
	
	public List<Slide> getSlides(String slideshowId) {
		List<Slide> slide = null;
		try {
			if (getDatabase().isOpen() != true)
				getDatabase().open();
			slide = getDatabase().findByColumn(Slide.class, "slideshow_id", slideshowId);
			getDatabase().close();
		} catch (ActiveRecordException e) {
			e.printStackTrace();
		}
		return slide;
	}

	public List<String> getSlideshowsIds() {

		List<Slideshow> sites = null;
		List<String> ids = new ArrayList<String>();

		try {
			if (getDatabase().isOpen() != true)
				getDatabase().open();
			sites = getDatabase().find(Slideshow.class, true, null, null,
					"vertical", null, null, null);
			for (Slideshow site : sites) {
				ids.add(site.getId());
			}
			getDatabase().close();
		} catch (ActiveRecordException e) {
			e.printStackTrace();
		}
		return ids;
	}

	public Slideshow getSlideshowById(String id) {
		List<Slideshow> sites = null;

		try {
			if (getDatabase().isOpen() != true)
				getDatabase().open();
			sites = getDatabase().findByColumn(Slideshow.class, "id", id);
			getDatabase().close();
		} catch (ActiveRecordException e) {
			e.printStackTrace();
		}
		return sites.get(0);
	}

	public boolean slideshowsLoaded() {
		boolean sitesLoaded = false;

		List<Slideshow> sites = null;

		try {
			if (getDatabase().isOpen() != true)
				getDatabase().open();
			sites = getDatabase().findAll(Slideshow.class);
			getDatabase().close();
			if (sites.size() > 0)
				sitesLoaded = true;
		} catch (ActiveRecordException e) {
			e.printStackTrace();
		}
		return sitesLoaded;
	}

	public List<Slideshow> getSlideshows(String vertical) {
		List<Slideshow> sites = null;

		try {
			if (getDatabase().isOpen() != true)
				getDatabase().open();
			sites = getDatabase().findByColumn(Slideshow.class, "vertical",
					vertical);
			getDatabase().close();
		} catch (ActiveRecordException e) {
			e.printStackTrace();
		}
		return sites;
	}

	public void saveSlideshowImage(SlideshowImage slideshowImage) {
		SlideshowImage slideshowImageToSave = null;

		try {
			if (getDatabase().isOpen() != true)
				getDatabase().open();
			slideshowImageToSave = getDatabase()
					.newEntity(SlideshowImage.class);
			slideshowImageToSave.slideshowId = slideshowImage.slideshowId;
			slideshowImageToSave.slideshowOrder = slideshowImage.slideshowOrder;
			slideshowImageToSave.image = slideshowImage.image;
			slideshowImageToSave.save();
			getDatabase().close();

		} catch (ActiveRecordException e) {
			e.printStackTrace();
		}
	}

	public List<SlideshowImage> getSlideshowImagesById(String id) {
		List<SlideshowImage> slideshowImage = null;

		try {
			if (getDatabase().isOpen() != true)
				getDatabase().open();
			slideshowImage = getDatabase().findByColumn(SlideshowImage.class,
					"slideshow_id", id);
			getDatabase().close();
		} catch (ActiveRecordException e) {
			e.printStackTrace();
		}
		return slideshowImage;
	}

	public boolean SlideshowImagesLoaded(String id) {
		boolean slideshowImagesLoaded = false;

		List<SlideshowImage> slideshowImages = null;

		try {
			if (getDatabase().isOpen() != true)
				getDatabase().open();
			slideshowImages = getDatabase().findByColumn(SlideshowImage.class,
					"slideshow_id", id);
			getDatabase().close();
			if (slideshowImages.size() > 0)
				slideshowImagesLoaded = true;
		} catch (ActiveRecordException e) {
			e.printStackTrace();
		}
		return slideshowImagesLoaded;
	}

	public List<Slideshow> getSlideshows() {
		List<Slideshow> slideshows = null;
		try {
			if (getDatabase().isOpen() != true)
				getDatabase().open();
			slideshows = getDatabase().find(Slideshow.class, true, null, null,
					"vertical", null, null, null);
			getDatabase().close();
		} catch (ActiveRecordException e) {
			e.printStackTrace();
		}
		return slideshows;
	}
	
	// Quiz Methods

	public void saveQuiz(Quiz quiz) {
		Quiz quizToSave = null;

		try {
			if (getDatabase().isOpen() != true)
				getDatabase().open();
			quizToSave = getDatabase().newEntity(Quiz.class);
			quizToSave.setVertical(quiz.getVertical());
			quizToSave.setFriendlyTitle(capitalize(quiz.getVertical().replace(
					"-", " ")));
			quizToSave.setQuizId(quiz.getQuizId());
			quizToSave.setTitle(quiz.getTitle());
			quizToSave.setText(quiz.getText());
			quizToSave.setImageUrl(quiz.getImageUrl());
			quizToSave.setImage(quiz.getImage());
			quizToSave.setNextQuizId(quiz.getNextQuizId());
			quizToSave.save();

			getDatabase().close();
		} catch (ActiveRecordException e) {
			e.printStackTrace();
		}
	}

	public List<Quiz> getQuizzes() {
		List<Quiz> quizzes = null;

		try {
			if (getDatabase().isOpen() != true)
				getDatabase().open();
			quizzes = getDatabase().find(Quiz.class, true, null, null,
					"vertical", null, null, null);
			getDatabase().close();
		} catch (ActiveRecordException e) {
			e.printStackTrace();
		}
		return quizzes;
	}

	public List<Quiz> getQuizzesByVertical(String vertical) {
		List<Quiz> quizzes = null;

		try {
			if (getDatabase().isOpen() != true)
				getDatabase().open();
			quizzes = getDatabase().findByColumn(Quiz.class, "vertical",
					vertical);
			getDatabase().close();
		} catch (ActiveRecordException e) {
			e.printStackTrace();
		}
		return quizzes;
	}

	public List<QuizQuestion> getQuestionsByVertical(String quizId) {
		List<QuizQuestion> questions = null;

		try {
			if (getDatabase().isOpen() != true)
				getDatabase().open();
			questions = getDatabase().findByColumn(QuizQuestion.class,
					"quiz_Id", quizId);
			getDatabase().close();
		} catch (ActiveRecordException e) {
			e.printStackTrace();
		}
		return questions;
	}
	
	public BitmapDrawable getImageByQuizId(String quizId) {
		Bitmap image = null;
		List<Quiz> quiz = new ArrayList<Quiz>();

		try {
			if (getDatabase().isOpen() != true)
				getDatabase().open();
			quiz = getDatabase().findByColumn(Quiz.class, "quiz_Id", quizId);
			getDatabase().close();
		} catch (ActiveRecordException e) {
			e.printStackTrace();
		}
		
		ByteArrayInputStream imageStream = new ByteArrayInputStream(quiz.get(0).getImage());
		image = BitmapFactory.decodeStream(imageStream);
		
		return new BitmapDrawable(image);
	}

	public String saveQuizQuestion(QuizQuestion quizQuestion) {
		QuizQuestion quizQuestionToSave = null;

		long questionId = 0;

		try {
			if (getDatabase().isOpen() != true)
				getDatabase().open();
			quizQuestionToSave = getDatabase().newEntity(QuizQuestion.class);
			quizQuestionToSave.setQuizId(quizQuestion.getQuizId());
			quizQuestionToSave.setQuizTitle(quizQuestion.getQuizTitle());
			quizQuestionToSave.setAnswerText(quizQuestion.getAnswerText());
			quizQuestionToSave.setQuestion(quizQuestion.getQuestion());
			quizQuestionToSave.setImageUrl(quizQuestion.getImageUrl());
			quizQuestionToSave.setImage(quizQuestionToSave.getImage());
			questionId = quizQuestionToSave.save();

			getDatabase().close();
		} catch (ActiveRecordException e) {
			e.printStackTrace();
		}

		return String.valueOf(questionId);
	}

	public void saveQuestionAnswer(QuizQuestionAnswer questionAnswer) {
		QuizQuestionAnswer QuestionAnswerToSave = null;

		try {
			if (getDatabase().isOpen() != true)
				getDatabase().open();
			QuestionAnswerToSave = getDatabase().newEntity(
					QuizQuestionAnswer.class);
			QuestionAnswerToSave.setQuestionId(questionAnswer.getQuestionId());
			QuestionAnswerToSave.setTitle(questionAnswer.getTitle());
			QuestionAnswerToSave.setValid(questionAnswer.getValid());
			QuestionAnswerToSave.save();

			getDatabase().close();
		} catch (ActiveRecordException e) {
			e.printStackTrace();
		}

	}

	public void saveQuizTextResult(QuizResult quizResult) {
		QuizResult quizResultToSave = null;

		try {
			if (!getIsOpenDatabase())
				getDatabase().open();

			quizResultToSave = getDatabase().newEntity(QuizResult.class);
			quizResultToSave.setQuizId(quizResult.getQuizId());
			quizResultToSave.setRange(quizResult.getRange());
			quizResultToSave.setValue(quizResult.getValue());
			quizResultToSave.save();

			getDatabase().close();
		} catch (ActiveRecordException e) {
			e.printStackTrace();
		}
	}

	public void saveQuizLearnMoreLink(QuizLearnMoreLink quizLearnMoreLink) {
		QuizLearnMoreLink quizLearnMoreLinkToSave = null;
		try {
			if (!getIsOpenDatabase())
				getDatabase().open();

			quizLearnMoreLinkToSave = getDatabase().newEntity(
					QuizLearnMoreLink.class);
			quizLearnMoreLinkToSave.setQuizId(quizLearnMoreLink.getQuizId());
			quizLearnMoreLinkToSave.setTitle(quizLearnMoreLink.getTitle());
			quizLearnMoreLinkToSave.setLink(quizLearnMoreLink.getLink());
			quizLearnMoreLinkToSave.save();
			getDatabase().close();
		} catch (ActiveRecordException e) {
			e.printStackTrace();
		}
	}

	public boolean QuizLoaded(String quizId) {
		boolean quizzesLoaded = false;

		List<Quiz> quizOnDb = null;

		try {
			if (getDatabase().isOpen() != true)
				getDatabase().open();
			quizOnDb = getDatabase().findByColumn(Quiz.class, "quiz_Id", quizId);
			getDatabase().close();
			if (quizOnDb.size() > 0)
				quizzesLoaded = true;
		} catch (ActiveRecordException e) {
			e.printStackTrace();
		}
		return quizzesLoaded;
	}

	public List<QuizQuestionAnswer> getAnswersByQuestionId(String questionId) {
		List<QuizQuestionAnswer> answers = null;

		try {
			if (getDatabase().isOpen() != true)
				getDatabase().open();
			answers = getDatabase().findByColumn(QuizQuestionAnswer.class,
					"question_Id", questionId);
			getDatabase().close();
		} catch (ActiveRecordException e) {
			e.printStackTrace();
		}
		return answers;
	}

	public void closeConnection() {
		conn.close();
	}

	public String capitalize(String str) {
		int strLen;
		if (str == null || (strLen = str.length()) == 0) {
			return str;
		}
		return new StringBuffer(strLen)
				.append(Character.toTitleCase(str.charAt(0)))
				.append(str.substring(1)).toString();
	}
}
