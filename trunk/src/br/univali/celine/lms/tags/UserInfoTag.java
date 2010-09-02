package br.univali.celine.lms.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import br.univali.celine.lms.config.LMSConfig;
import br.univali.celine.lmsscorm.DAO;
import br.univali.celine.lmsscorm.User;

@SuppressWarnings("serial")
public class UserInfoTag extends BodyTagSupport {

	private String name;
	private String varPassw = "varPassw";
	private String varAdmin = "varAdmin";
	private User user;
	
	@Override
	public int doStartTag() throws JspException {
	
		try {
		
			DAO dao = LMSConfig.getInstance().getDAO();
			user = dao.findUser(name);
			setAttributes();
			
		} catch (Exception e) {
			throw new JspException(e);
		}
		
		return EVAL_BODY_INCLUDE;
	}

	private void setAttributes(){
		
		pageContext.setAttribute(varAdmin, user.isAdmin());
		pageContext.setAttribute(varPassw, user.getPassw());
		
	}
	
	public String getName() {
	
		return name;
		
	}

	
	public void setName(String name) {
	
		this.name = name;
		
	}

	
	public String getVarPassw() {
	
		return varPassw;
		
	}

	
	public void setVarPassw(String varPassw) {
	
		this.varPassw = varPassw;
		
	}

	
	public String getVarAdmin() {
	
		return varAdmin;
		
	}

	
	public void setVarAdmin(String varAdmin) {

		this.varAdmin = varAdmin;
		
	}
	
}
