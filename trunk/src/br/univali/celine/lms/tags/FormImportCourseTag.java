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
import javax.servlet.jsp.JspWriter;

import br.univali.celine.lms.model.UserImpl;
import br.univali.celine.lmsscorm.User;

public class FormImportCourseTag extends NextURLBodyTagSupport {

	private static final long serialVersionUID = 1L;

	private String labelSubmit = "Send";
	private String labelFieldId = "ID: ";
	private String labelFieldTitle = "Title: ";
	private String labelFieldFile = "File: ";
	private String progressListener = null;
	private String statusListener = null;
	private String refreshInterval = "250";

	@Override
	public int doStartTag() throws JspException {

		User user = (User) pageContext.getSession().getAttribute(UserImpl.USER);

		try {

			String onSubmit = "javascript:void(0);";
			JspWriter out = pageContext.getOut();

			if ((progressListener != null) || (statusListener != null)) {

				out.println("<script type='text/javascript' src='dwr/engine.js'></script>");
				out.println("<script type='text/javascript' src='dwr/util.js'></script>");
				out.println("<script type='text/javascript' src='dwr/interface/AJAX_INTERFACE.js'></script>");

				out.println("<script type='text/javascript'>");
				out.println("	function startProgressMonitor() {");

				out.println("		try {");

				out.println(progressListener + "(0);");
				out.println("			setInterval('getProgress()', "
						+ Integer.parseInt(refreshInterval) + ");");

				out.println("		} catch (error) { }");

				out.println("	}");

				out.println("	function startStatusMonitor() {");
				out.println("		try {");

				out.println(statusListener + "(0);");
				out.println("			setInterval('getStatus()', "
						+ Integer.parseInt(refreshInterval) + ");");

				out.println("		} catch (error) { }");

				out.println("	}");

				out.println("	function getProgress() {");
				out.println("		AJAX_INTERFACE.getUploadProgress('"
						+ user.getName() + "', " + progressListener + ");");
				out.println("	}");

				out.println("	function getStatus() {");
				out.println("		AJAX_INTERFACE.getUploadStatus('"
						+ user.getName() + "', " + statusListener + ");");
				out.println("	}");

				out.println("	function startMonitors() {");

				out.println("		startStatusMonitor();");
				out.println("		startProgressMonitor();");

				out.println("	}");

				out.println("</script>");

				onSubmit = "javascript:startMonitors();";

			}

			// TODO: Pense nisso!!! Que tal criarmos um padrão na hora de nomear
			// os estilos das divs?
			// Criei um padrao que é assim: IniciasDaClasse_nomeDoField.
			// Por exemplo, esta classe tem as inicias FICT de Form Import
			// Course Tag,
			// e possui um field com nome courseId, então a div correspondente
			// fica
			// fict_courseId. Poderíamos manter o mesmo padrão para todas as
			// outras classes, assim ficaria mais
			// fácil para o usuário customizar o visual da aplicação.

			// O java também traz o recurso, de colocar um tag <description> no
			// tld, assim podemos usar esse
			// recurso em cada tag para informar os estilos das divs ao usuário.
			// Mas é só uma idéia, então pense nisso...

			out.println("<div class='fict_content'>");
			out.println("	<form enctype='multipart/form-data' method='post' action='lms?action=importcourse' onsubmit='"
					+ onSubmit + "'>");

			out.println("		<input type='hidden' name='nextURL' value='"
					+ getNextURL() + "'/>");

			out.println("		<div class='fict_label_id'>");
			out.println(labelFieldId);
			out.println("		</div>");

			out.println("		<div class='fict_id'>");
			out.println("			<input type='text' name='id' value=''/>");
			out.println("		</div>");

			out.println("		<div class='fict_label_title'>");
			out.println(labelFieldTitle);
			out.println("		</div>");

			out.println("		<div class='fict_title'>");
			out.println("			<input type='text' name='title' value=''/><br/>");
			out.println("		</div>");

			out.println("		<div class='fict_label_file'>");
			out.println(labelFieldFile);
			out.println("		</div>");

			out.println("		<div class='fict_file'>");
			out.println("			<input type='file' name='file'/>");
			out.println("		</div>");

		} catch (Exception e) {
			throw new JspException(e);
		}

		return EVAL_BODY_INCLUDE;

	}

	@Override
	public int doEndTag() throws JspException {
		JspWriter out = pageContext.getOut();
		try {
			out.println("		<div class='fict_submit'>");
			out.println("			<input type='submit' name='submit' value='"
					+ labelSubmit + "'>");
			out.println("		</div>");

			out.println("	</form>");
			out.println("</div>");
		} catch (Exception e) {
			throw new JspException(e);
		}
		return super.doEndTag();
	}

	public String getLabelSubmit() {
		return labelSubmit;
	}

	public void setLabelSubmit(String labelSubmit) {
		this.labelSubmit = labelSubmit;
	}

	public String getLabelFieldId() {
		return labelFieldId;
	}

	public void setLabelFieldId(String labelFieldId) {
		this.labelFieldId = labelFieldId;
	}

	public String getLabelFieldTitle() {
		return labelFieldTitle;
	}

	public void setLabelFieldTitle(String labelFieldTitle) {
		this.labelFieldTitle = labelFieldTitle;
	}

	public String getLabelFieldFile() {
		return labelFieldFile;
	}

	public void setLabelFieldFile(String labelFieldFile) {
		this.labelFieldFile = labelFieldFile;
	}

	public String getProgressListener() {
		return progressListener;
	}

	public void setProgressListener(String progressListener) {
		this.progressListener = progressListener;
	}

	public String getRefreshInterval() {
		return refreshInterval;
	}

	public void setRefreshInterval(String refreshInterval) {
		this.refreshInterval = refreshInterval;
	}

	public void setStatusListener(String statusListener) {
		this.statusListener = statusListener;
	}

	public String getStatusListener() {
		return statusListener;
	}

}
