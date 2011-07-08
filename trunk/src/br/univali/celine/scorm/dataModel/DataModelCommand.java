package br.univali.celine.scorm.dataModel;

import br.univali.celine.scorm.rteApi.ErrorManager;

/**
 * É bom lembrar que os DataModelCommands sao stateless
 * 
 * @author Adilson Vahldick
 *
 */

public interface DataModelCommand extends BasicDataModelCommand {
	
	public void clear(ErrorManager errorManager) throws Exception;
	public void initialize(ErrorManager errorManager);
}
