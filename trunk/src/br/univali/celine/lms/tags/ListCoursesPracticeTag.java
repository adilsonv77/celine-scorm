package br.univali.celine.lms.tags;

import java.util.Iterator;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import br.univali.celine.lms.config.LMSConfig;
import br.univali.celine.lmsscorm.CoursePractice;
import br.univali.celine.scorm.sn.model.ActivityTree;
import br.univali.celine.scorm.sn.model.ActivityTreeFactory;
import br.univali.celine.scorm.sn.model.TrackModelManager;

@SuppressWarnings("serial")
public class ListCoursesPracticeTag extends BodyTagSupport {

	private String userId;
	private String varCourse="varCourse";
	private String varTime="varTime";
	private String varLastTime="varLastTime";
	private String varCompletionStatus="varCompletionStatus";
	private String varSuccessStatus="varSuccessStatus";
	private String varScoreScaled="varScoreScaled";

	private Iterator<CoursePractice> courses;
		
	@Override
	public int doStartTag() throws JspException {

		List<CoursePractice> lista = null;
		
		try {			
			lista = LMSConfig.getInstance().getDAO().listCoursesPractice(userId);
		} catch (Exception e) {
			throw new JspException(e);
		}
		
		courses = lista.iterator();
		
		if (courses.hasNext()) {
			setAttributes();
			return EVAL_BODY_INCLUDE;
			
		}
		
		return SKIP_BODY;
	}
	
	private void setAttributes() throws JspException {
		
		CoursePractice course = courses.next();
		
		pageContext.setAttribute(varCourse, course.getCourse());
		pageContext.setAttribute(varTime, course.getTime());
		pageContext.setAttribute(varLastTime, course.getLastTimeAccessed());
		
		try {
			ActivityTree tree = ActivityTreeFactory.build(LMSConfig.getInstance().openContentPackageByFolderName(course.getCourse().getFolderName()));
			TrackModelManager tmm = new TrackModelManager(tree, "adilson", course.getCourse().getFolderName());
			tmm.load(LMSConfig.getInstance().getDAO());
			pageContext.setAttribute(varCompletionStatus, tree.getRootActivity().getCompletionStatus());
			pageContext.setAttribute(varSuccessStatus, tree.getRootActivity().getSuccessStatus());
			pageContext.setAttribute(varScoreScaled, tree.getRootActivity().getScoreScaled());
		} catch (Exception e) {
			throw new JspException(e);
		}
		
	}
	
	@Override
	public int doAfterBody() throws JspException {
		
		if (courses.hasNext()) {
			
			setAttributes();
			return EVAL_BODY_AGAIN;
		}		
		
		return SKIP_BODY;
	}

	public String getVarTime() {
		return varTime;
	}

	public void setVarTime(String varTime) {
		this.varTime = varTime;
	}

	public String getVarLastTime() {
		return varLastTime;
	}

	public void setVarLastTime(String varLastTime) {
		this.varLastTime = varLastTime;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String user) {
		this.userId = user;
	}

	public String getVarCourse() {
		return varCourse;
	}

	public void setVarCourse(String varCourse) {
		this.varCourse = varCourse;
	}

	public String getVarCompletionStatus() {
		return varCompletionStatus;
	}

	public void setVarCompletionStatus(String varCompletionStatus) {
		this.varCompletionStatus = varCompletionStatus;
	}

	public String getVarSuccessStatus() {
		return varSuccessStatus;
	}

	public void setVarSuccessStatus(String varSuccessStatus) {
		this.varSuccessStatus = varSuccessStatus;
	}

	public String getVarScoreScaled() {
		return varScoreScaled;
	}

	public void setVarScoreScaled(String varScoreScaled) {
		this.varScoreScaled = varScoreScaled;
	}
	
	
}
