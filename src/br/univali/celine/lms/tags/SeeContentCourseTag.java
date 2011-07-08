package br.univali.celine.lms.tags;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import br.univali.celine.lms.config.LMSConfig;
import br.univali.celine.lms.model.UserImpl;
import br.univali.celine.lmsscorm.User;
import br.univali.celine.scorm.model.cam.ContentPackage;
import br.univali.celine.scorm.model.cam.Item;
import br.univali.celine.scorm.model.cam.SuperItem;

@SuppressWarnings("serial")
public class SeeContentCourseTag extends BodyTagSupport {

	private String courseId;
	private String varName = "name";
	private String varLevel = "level";
	private String varIsLeaf = "isLeaf";
	private String nomeCurso;
	private String varCourseName = "varCourseName";
	private String varItemId = "varItemId";
	
	private SuperItem itemAtual;
	private Iterator<Item> children;
	private List<Iterator<Item>> pilha = new ArrayList<Iterator<Item>>();	
	//private List<AbstractItem> pilha = new ArrayList<AbstractItem>();
	
	
	@Override
	public int doStartTag() throws JspException {

		User user = (User) pageContext.getSession().getAttribute(UserImpl.USER);
		
		ContentPackage cp;
		
		try {
			cp = LMSConfig.getInstance().openContentPackageById(user, courseId);			
		} catch (Exception e) {
			throw new JspException(e);
		}
		
		nomeCurso = cp.getOrganizations().getDefaultOrganization().getTitle();
		itemAtual = cp.getOrganizations().getDefaultOrganization();
		children = itemAtual.getChildren();

		setAttributes();

		return EVAL_BODY_INCLUDE;
	}

	private void setAttributes() {
		
		pageContext.setAttribute(varName, itemAtual.getTitle());
		pageContext.setAttribute(varLevel, pilha.size());
		pageContext.setAttribute(varCourseName, nomeCurso);
		pageContext.setAttribute(varItemId, itemAtual.getIdentifier());
		pageContext.setAttribute(varIsLeaf, children.hasNext()?false:true);

		if (children.hasNext()) {
			
			pilha.add(children);
			itemAtual = children.next();
			children = itemAtual.getChildren();
			
		} else {

			if (pilha.size() == 0) 
				itemAtual = null;
			
			else {
			
				itemAtual = null;
				
				while (itemAtual == null && pilha.size() > 0)				
					if (pilha.get(pilha.size() - 1).hasNext()) {
						
						itemAtual = pilha.get(pilha.size() - 1).next();
						children = itemAtual.getChildren();
						
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

	public String getVarName() {
		return varName;
	}

	public void setVarName(String varName) {
		this.varName = varName;
	}

	public String getVarLevel() {
		return varLevel;
	}

	public void setVarLevel(String varLevel) {
		this.varLevel = varLevel;
	}

	public String getNomeCurso() {
		return nomeCurso;
	}

	public void setNomeCurso(String nomeCurso) {
		this.nomeCurso = nomeCurso;
	}

	public String getVarCourseName() {
		return varCourseName;
	}

	public void setVarCourseName(String varCourseName) {
		this.varCourseName = varCourseName;
	}
	
	public String getVarItemId() {
		return varItemId;
	}
	
	public void setVarItemId(String varItemId) {
		this.varItemId = varItemId;
	}

	public String getVarIsLeaf() {
		return varIsLeaf;
	}

	public void setVarIsLeaf(String varIsLeaf) {
		this.varIsLeaf = varIsLeaf;
	}

	public String getCourseId() {
		return courseId;
	}
	
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	
}
