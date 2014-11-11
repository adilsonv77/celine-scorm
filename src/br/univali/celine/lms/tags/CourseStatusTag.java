/**
 * CELINE SCORM
 *
 * Copyright 2014 Adilson Vahldick.
 * https://celine-scorm.googlecode.com/
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package br.univali.celine.lms.tags;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import br.univali.celine.lms.config.LMSConfig;
import br.univali.celine.lmsscorm.Course;
import br.univali.celine.lmsscorm.DAO;
import br.univali.celine.lmsscorm.User;
import br.univali.celine.scorm.model.cam.ContentPackage;
import br.univali.celine.scorm.sn.model.ActivityTree;
import br.univali.celine.scorm.sn.model.ActivityTreeFactory;
import br.univali.celine.scorm.sn.model.LearningActivity;
import br.univali.celine.scorm.sn.model.TrackModelManager;

@SuppressWarnings("serial")
public class CourseStatusTag extends BodyTagSupport {

	private String courseId;
	private String learnerId;

	private String varItemName = "varItemName";
	private String varItemLevel = "itemLevel";
	private String varItemCompletionStatus = "itemCompletionStatus";
	private String varItemSuccessStatus = "itemSatisfiedStatus";
	private String varItemScoreScaled = "itemScoreScaled";
	private String varItemIsLeaf = "itemIsLeaf";
	private String varCourseName = "courseName";
	private String varItemExperiencedDuration = "itemExperiencedDuration";
	
	private LearningActivity itemAtual;
	private Iterator<LearningActivity> children;
	private List<Iterator<LearningActivity>> pilha = new ArrayList<Iterator<LearningActivity>>();
	
	@Override
	public int doStartTag() throws JspException {

		DAO dao = LMSConfig.getInstance().getDAO();
		
		User user;
		Course course;
		try {
			
			user = dao.findUser(learnerId);
			course = dao.findCourse(courseId);
			
		} catch (Exception e) {
			throw new JspException(e);
		}
		
		ContentPackage cp;
		
		try {
			cp = LMSConfig.getInstance().openContentPackageById(user, courseId);			
		} catch (Exception e) {
			throw new JspException(e);
		}
		
		ActivityTree tree = ActivityTreeFactory.build(cp);
		TrackModelManager tm = new TrackModelManager(tree, learnerId, course.getFolderName());
		try {
			tm.load(dao);
		} catch (Exception e) {
			throw new JspException(e);
		}
		
		pageContext.setAttribute(varCourseName, cp.getOrganizations().getDefaultOrganization().getTitle());
		
		itemAtual = tree.getRootActivity();
		children = itemAtual.getChildren().iterator();

		setAttributes();

		return EVAL_BODY_INCLUDE;
	}

	private void setAttributes() {
		
		pageContext.setAttribute(varItemName, itemAtual.getItem().getTitle());
		pageContext.setAttribute(varItemLevel, pilha.size());
		pageContext.setAttribute(varItemIsLeaf, children. hasNext()?false:true);
		pageContext.setAttribute(varItemCompletionStatus, itemAtual.getCompletionStatus());
		pageContext.setAttribute(varItemSuccessStatus, itemAtual.getSuccessStatus());
		pageContext.setAttribute(varItemScoreScaled, itemAtual.getScoreScaled());
		pageContext.setAttribute(varItemExperiencedDuration, itemAtual.getExperiencedDuration());

		if (children.hasNext()) {
			
			pilha.add(children);
			itemAtual = children.next();
			children = itemAtual.getChildren().iterator();
			
		} else {

			if (pilha.size() == 0) 
				itemAtual = null;
			
			else {
			
				itemAtual = null;
				
				while (itemAtual == null && pilha.size() > 0)				
					if (pilha.get(pilha.size() - 1).hasNext()) {
						
						itemAtual = pilha.get(pilha.size() - 1).next();
						children = itemAtual.getChildren().iterator();
						
					}
				
					else pilha.remove(pilha.size() - 1);				
			}
		}
	}

	@Override
	public int doAfterBody() throws JspException {

		if (itemAtual == null)
			return SKIP_BODY;

		setAttributes();

		return EVAL_BODY_AGAIN;
	}

	public String getCourseId() {
		return courseId;
	}
	
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getLearnerId() {
		return learnerId;
	}

	public void setLearnerId(String userId) {
		this.learnerId = userId;
	}

	public String getVarItemLevel() {
		return varItemLevel;
	}

	public void setVarItemLevel(String varItemLevel) {
		this.varItemLevel = varItemLevel;
	}

	public String getVarItemName() {
		return varItemName;
	}

	public void setVarItemName(String varItemName) {
		this.varItemName = varItemName;
	}

	public String getVarItemIsLeaf() {
		return varItemIsLeaf;
	}

	public void setVarItemIsLeaf(String varItemIsLeaf) {
		this.varItemIsLeaf = varItemIsLeaf;
	}

	public String getVarItemCompletionStatus() {
		return varItemCompletionStatus;
	}

	public void setVarItemCompletionStatus(String varItemCompletionStatus) {
		this.varItemCompletionStatus = varItemCompletionStatus;
	}

	public String getVarItemSuccessStatus() {
		return varItemSuccessStatus;
	}

	public void setVarItemSuccessStatus(String varItemSuccessStatus) {
		this.varItemSuccessStatus = varItemSuccessStatus;
	}

	public String getVarItemScoreScaled() {
		return varItemScoreScaled;
	}

	public void setVarItemScoreScaled(String varItemScoreScaled) {
		this.varItemScoreScaled = varItemScoreScaled;
	}

	public String getVarItemExperiencedDuration() {
		return varItemExperiencedDuration;
	}

	public void setVarItemExperiencedDuration(String varItemExperiencedDuration) {
		this.varItemExperiencedDuration = varItemExperiencedDuration;
	}
	
}
