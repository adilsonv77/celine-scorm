package br.univali.celine.lms.tags;

import java.util.Iterator;
import java.util.List;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import br.univali.celine.lms.config.LMSConfig;
import br.univali.celine.lmsscorm.User;

@SuppressWarnings("serial")
public class ListUsersTag extends BodyTagSupport {

	private String varName = "varName";
	private String varIndex = "varIndex";

	private int index = -1;
	private Iterator<User> users;
	
		
	@Override
	public int doStartTag() throws JspException {


		List<User> lista = null;
		
		try {			
			lista = LMSConfig.getInstance().getDAO().listUsers();
		} catch (Exception e) {
			throw new JspException(e);
		}
		
		users = lista.iterator();
		
		if (users.hasNext()) {
			setAttributes();
			return EVAL_BODY_INCLUDE;
			
		}
		
		return SKIP_BODY;
	}
	
	private void setAttributes() {
		
		User user = users.next();
		index++; 
		
		pageContext.setAttribute(varIndex, index);
		pageContext.setAttribute(varName, user.getName());
		
	}
	
	@Override
	public int doAfterBody() throws JspException {
		
		if (users.hasNext()) {
			
			setAttributes();
			return EVAL_BODY_AGAIN;
		}		
		
		index = -1;			
		return SKIP_BODY;
	}
	
	public void setVarName(String varName) {
		this.varName = varName;
	}
	
	public String getVarName() {
		return varName;
	}

	public String getVarIndex() {
		return varIndex;
	}
	
	public void setVarIndex(String varIndex) {
		this.varIndex = varIndex;
	}	
	
}
