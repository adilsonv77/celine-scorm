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


import java.util.Iterator;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import br.univali.celine.lms.config.LMSConfig;
import br.univali.celine.lms.integration.LMSIntegration;
import br.univali.celine.lms.model.UserImpl;
import br.univali.celine.lmsscorm.Course;
import br.univali.celine.lmsscorm.User;

@SuppressWarnings("serial")
public class ListCoursesTag extends BodyTagSupport {

	private String varName;
	private String varLink;
	private String varId = "varCourseId";
	private String varStatusCourseRegistered = "varStatusCourseRegistered";
	private String varHasRights = "varHasRights";
	private String varCourse = "varCourse";
	private Iterator<Course> cursos;
	
	@Override
	public int doStartTag() throws JspException {
		
		LMSConfig config = LMSConfig.getInstance();
		User user = (User) pageContext.getSession().getAttribute(UserImpl.USER);		
		LMSIntegration lmsInt = config.getLMSIntegration();
		
		List<Course> lista = null;
		
		try {
			lista = config.getDAO().listCourses();
		} catch (Exception e) {
			throw new JspException(e);
		}
		
		try {
		
			verifyNotRemovedCourse(lista); // antes e depois pois o sistema pode ter incluído um curso removido			
			lmsInt.selectCourses(user, lista);			
			verifyNotRemovedCourse(lista);
			 
		} catch (Exception e) {
			throw new JspException(e);
		}
		
		cursos = lista.iterator();
		
		if (cursos.hasNext()) {
		
			setAttributes();
			return EVAL_BODY_INCLUDE;
			
		}
		
		return SKIP_BODY;
	}

	private void verifyNotRemovedCourse(List<Course> list) {
		for (int x = list.size() - 1; x >= 0; x--) {
			Course course = list.get(x);
			if (course.isRemoved())
				list.remove(course);
		}
		
	}
	
	private void setAttributes() { 
		
		boolean userRegistered = false;
		boolean hasRights = false;
		
		Course course = cursos.next();
		String courseId = course.getId();
		String title = course.getTitle();
		String link = "lms?action=opencourse&courseId=" + courseId;		
		UserImpl user = (UserImpl) pageContext.getSession().getAttribute(UserImpl.USER);		
		LMSConfig config = LMSConfig.getInstance();
		
		
		try {
		
			userRegistered = config.getDAO().userIsRegisteredAtCourse(user, courseId);
			hasRights = config.getDAO().userHasRightsAtCourse(user, courseId);
			
		} catch (Exception e) {
			
			e.printStackTrace();			
			title = "Error when try to access the user data";
			link = "";
			
		}

		
		pageContext.setAttribute(varName,  title);
		pageContext.setAttribute(varLink,  link);
		pageContext.setAttribute(varId,  courseId);
		pageContext.setAttribute(varStatusCourseRegistered, userRegistered);
		pageContext.setAttribute(varHasRights, hasRights);
		pageContext.setAttribute(varCourse, course);
		
	}	
	

	@Override
	public int doAfterBody() throws JspException {
		
		if (cursos.hasNext()) {
		
			setAttributes();
			return EVAL_BODY_AGAIN;
			
		}
			
		return SKIP_BODY;
	}

	
	
	public void setVarName(String varName) {
		this.varName = varName;
	}

	public void setVarLink(String varLink) {
		this.varLink = varLink;
	}

	public void setVarStatusCourseRegistered(String varStatusCourseRegistered) {
		this.varStatusCourseRegistered = varStatusCourseRegistered;
	}
	
	public void setVarId(String varId) {
		this.varId =  varId;
	}

	public void setVarHasRights(String varHasRights) {
		this.varHasRights = varHasRights;
	}

	public void setVarCourse(String varCourse) {
		this.varCourse = varCourse;
	}

}
