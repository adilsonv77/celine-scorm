package br.univali.celine.scorm.dataModel.cmi;

import br.univali.celine.scorm.dataModel.DataModelCommand;
import br.univali.celine.scorm.rteApi.ErrorManager;

public class DataModelBasedDotNotationCommand implements DotNotationCommand {

	private DataModelCommand dm;

	public DataModelBasedDotNotationCommand(DataModelCommand dm) {
		this.dm = dm;		
	}
	
	@Override
	public String getValue(ErrorManager errorManager, int n, int size) {
		try {
			return dm.getValue(null, errorManager);
		} catch (Exception e) {
			return "";
		}
	}

	@Override
	public boolean setValue(ErrorManager errorManager, int n, int size,
			String novoValor) {
		try {
			return dm.setValue(null, novoValor, errorManager);
		} catch (Exception e) {
			return false;
		}
	}

}


