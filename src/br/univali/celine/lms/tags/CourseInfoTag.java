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

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import br.univali.celine.lms.config.LMSConfig;
import br.univali.celine.lmsscorm.Course;


@SuppressWarnings("serial")
public class CourseInfoTag extends BodyTagSupport {

	private String courseId;
	private String varFolderName = "varFolderName";
	private String varTitle = "varTitle";
	
	@Override
	public int doStartTag() throws JspException {

		try {
			
			setAttributes();
			
		} catch (Exception e) {
			throw new JspException(e);
		}
		
		return EVAL_BODY_INCLUDE;
	}
	
	private void setAttributes() throws Exception {
		
		Course course = LMSConfig.getInstance().getDAO().findCourse(courseId);
		
		pageContext.setAttribute(varTitle, course.getTitle());
		pageContext.setAttribute(varFolderName, course.getFolderName());
		
	}
	
	public String getCourseId() {
		return courseId;
	}
	
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	
	public String getVarFolderName() {
		return varFolderName;
	}
	
	public void setVarFolderName(String varFolderName) {
		this.varFolderName = varFolderName;
	}
	
	public String getVarTitle() {
		return varTitle;
	}
	
	
	public void setVarTitle(String varTitle) {
		this.varTitle = varTitle;
	}
	
}
