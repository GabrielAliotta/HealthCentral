package com.heathcentral.service;

import java.util.ArrayList;
import java.util.List;

import org.kroz.activerecord.ActiveRecordBase;
import org.kroz.activerecord.ActiveRecordException;
import org.kroz.activerecord.Database;
import org.kroz.activerecord.DatabaseBuilder;

import com.heathcentral.model.QuizQuestionAnswer;
import com.heathcentral.model.Quiz;
import com.heathcentral.model.QuizQuestion;
import com.heathcentral.model.QuizResult;
import com.heathcentral.model.Site;
import com.heathcentral.model.SlideshowImage;

import android.content.Context;

public class DatabaseController {

	private final static String dbName = "healthcentral.db";
	private final static int dbVersion = 14;
	private static Context ctx = null;
	private static DatabaseBuilder builder = null;
	private static ActiveRecordBase conn = null;

	@SuppressWarnings("static-access")
	public DatabaseController(Context context) {
		this.ctx = context;
	}

	public static void initDatabase() throws ActiveRecordException {
		builder = new DatabaseBuilder(dbName);
		builder.addClass(Site.class);
		builder.addClass(SlideshowImage.class);
		builder.addClass(Quiz.class);
		builder.addClass(QuizQuestion.class);
		builder.addClass(QuizResult.class);
		builder.addClass(QuizQuestionAnswer.class);

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

	// Sites Methods

	public void saveSite(Site site) {
		Site siteToSave = null;

		try {
			if (getDatabase().isOpen() != true)
				getDatabase().open();
			siteToSave = getDatabase().newEntity(Site.class);
			siteToSave.setType(site.getType());

			siteToSave.setFriendlyTitle(capitalize(site.getVertical().replace(
					"-", " ")));
			siteToSave.setTitle(site.getTitle());
			siteToSave.setBlurb(site.getBlurb());
			siteToSave.setUrl(site.getUrl());
			siteToSave.setImage(site.image);
			siteToSave.setImageUrl(site.getImageUrl());
			siteToSave.setPublished(site.getPublished());
			siteToSave.setUpdated(site.getUpdated());
			siteToSave.setVertical(site.getVertical());
			siteToSave.setId(site.getId());
			siteToSave.setContents(site.getContents());
			siteToSave.save();

			getDatabase().close();
		} catch (ActiveRecordException e) {
			e.printStackTrace();
		}
	}

	public List<Site> getSites() {
		List<Site> sites = null;

		try {
			if (getDatabase().isOpen() != true)
				getDatabase().open();
			sites = getDatabase().find(Site.class, true, null, null,
					"vertical", null, null, null);
			getDatabase().close();
		} catch (ActiveRecordException e) {
			e.printStackTrace();
		}
		return sites;
	}

	public List<String> getSitesIds() {

		List<Site> sites = null;
		List<String> ids = new ArrayList<String>();

		try {
			if (getDatabase().isOpen() != true)
				getDatabase().open();
			sites = getDatabase().find(Site.class, true, null, null,
					"vertical", null, null, null);
			for (Site site : sites) {
				ids.add(site.getId());
			}
			getDatabase().close();
		} catch (ActiveRecordException e) {
			e.printStackTrace();
		}
		return ids;
	}

	public Site getSiteById(String id) {
		List<Site> sites = null;

		try {
			if (getDatabase().isOpen() != true)
				getDatabase().open();
			sites = getDatabase().findByColumn(Site.class, "id", id);
			getDatabase().close();
		} catch (ActiveRecordException e) {
			e.printStackTrace();
		}
		return sites.get(0);
	}

	public boolean sitesLoaded() {
		boolean sitesLoaded = false;

		List<Site> sites = null;

		try {
			if (getDatabase().isOpen() != true)
				getDatabase().open();
			sites = getDatabase().findAll(Site.class);
			getDatabase().close();
			if (sites.size() > 0)
				sitesLoaded = true;
		} catch (ActiveRecordException e) {
			e.printStackTrace();
		}
		return sitesLoaded;
	}

	// Slideshows Methods

	public List<Site> getSlideshows(String site) {
		List<Site> sites = null;

		try {
			if (getDatabase().isOpen() != true)
				getDatabase().open();
			sites = getDatabase().findByColumn(Site.class, "vertical", site);
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

	// Quiz Methods

	public void saveQuiz(Quiz quiz) {
		Quiz quizToSave = null;

		try {
			if (getDatabase().isOpen() != true)
				getDatabase().open();
			quizToSave = getDatabase().newEntity(Quiz.class);
			quizToSave.setVertical(quiz.getVertical());
			quizToSave.setQuizId(quiz.getQuizId());
			quizToSave.setTitle(quiz.getTitle());
			quizToSave.setText(quiz.getText());
			quizToSave.setImageUrl(quiz.getImageUrl());
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
			quizzes = getDatabase().find(Quiz.class, true, null, null, "vertical", null, null, null);
			getDatabase().close();
		} catch (ActiveRecordException e) {
			e.printStackTrace();
		}
		return quizzes;
	}
	
	public String saveQuizQuestion(QuizQuestion quizQuestion) {
		QuizQuestion quizQuestionToSave = null;

		long questionId = 0;
		
		try {
			if (getDatabase().isOpen() != true)
				getDatabase().open();
			quizQuestionToSave = getDatabase().newEntity(QuizQuestion.class);
			quizQuestionToSave.setQuizId(quizQuestion.getQuizId());
			quizQuestionToSave.setTitle(quizQuestion.getTitle());
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
			QuestionAnswerToSave = getDatabase().newEntity(QuizQuestionAnswer.class);
			QuestionAnswerToSave.setQuestionId(questionAnswer.getQuestionId());
			QuestionAnswerToSave.setTitle(questionAnswer.getTitle());
			QuestionAnswerToSave.setValid(questionAnswer.getValid());
			QuestionAnswerToSave.save();

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
