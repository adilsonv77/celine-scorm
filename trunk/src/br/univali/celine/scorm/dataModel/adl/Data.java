package br.univali.celine.scorm.dataModel.adl;

import br.univali.celine.scorm.dataModel.DataModelCommand;
import br.univali.celine.scorm.dataModel.cmi.DotNotationCommand;
import br.univali.celine.scorm.dataModel.cmi.DotNotationCommandManager;
import br.univali.celine.scorm.rteApi.ErrorManager;
import br.univali.celine.scorm2004_4th.model.cam.AdlcpMap;
import br.univali.celine.scorm2004_4th.model.cam.Item20044th;

//adl.data 4.3
/**
 * Fields Implemented:
 * <ul>
 * <li>_children</li>
 * <li>_count</li>
 * <li>n.id</li>
 * <li>n.store</li>
 * </ul>
 * 
 * DM Completed !!!
 */
public class Data implements DataModelCommand {

	public static final String name = "adl.data";
	private static final String simpleName = "data";

	private DotNotationCommandManager commMan = new DotNotationCommandManager(
			simpleName, new DataCount());
	
	public Data() {
		commMan.add("id", new TrataIdDataDotNotationCommand());
		commMan.add("store", new TrataStoreDataDotNotationCommand());
	}
	
	@Override
	public String getValue(String key, ErrorManager errorManager)
			throws Exception {
		return commMan.getValue(key, errorManager);
	}

	@Override
	public boolean setValue(String key, String newValue,
			ErrorManager errorManager) throws Exception {
		
		return commMan.setValue(key, newValue, errorManager);
	}

	@Override
	public void clear(ErrorManager errorManager) throws Exception {
		
		commMan.clear(errorManager);
	}

	@Override
	public void initialize(ErrorManager errorManager) {
	}

	public class DataCount implements DotNotationCommand {

		public String getValue(ErrorManager errorManager, int n, int size) {
			
			Item20044th i4th = (Item20044th) errorManager.getTree().getCurrentActivity().getItem();
			return String.valueOf(i4th.getAdlcpData().size());
		}

		public boolean setValue(ErrorManager errorManager, int n, int size,
				String novoValor) {
			
			errorManager.attribError(ErrorManager.DataModelElementIsReadOnly);
			
			return false;
		}

	}
	
	public class TrataIdDataDotNotationCommand implements DotNotationCommand {

		@Override
		public String getValue(ErrorManager errorManager, int n, int size) {
			if (n >= size) {
				errorManager.attribError(ErrorManager.GeneralGetFailure);
				return "";
			}
			
			Item20044th i4th = (Item20044th) errorManager.getTree().getCurrentActivity().getItem();
			return i4th.getAdlcpData().get(n).getTargetID();
		}

		@Override
		public boolean setValue(ErrorManager errorManager, int n, int size,
				String novoValor) {
			
			errorManager.attribError(ErrorManager.DataModelElementIsReadOnly);
			
			return false;
		}

	}

	public class TrataStoreDataDotNotationCommand implements DotNotationCommand {

		@Override
		public String getValue(ErrorManager errorManager, int n, int size) {

			if (n >= size) {
				errorManager.attribError(ErrorManager.GeneralGetFailure);
				return "";
			}
			
			Item20044th i4th = (Item20044th) errorManager.getTree().getCurrentActivity().getItem();
			AdlcpMap m = i4th.getAdlcpData().get(n);
			
			if (!m.isReadSharedData()) {
				errorManager.attribError(ErrorManager.DataModelElementIsWriteOnly);
				return "";
			}
			
			// TODO precisa considerar o atributo sharedDataGlobalToSystem que indica que o valor será guardado no LMS, e nao somente para o usuario

			String res = errorManager.getTree().retrieveData(m.getTargetID());
			if (res == null) {
				errorManager.attribError(ErrorManager.DataModelElementValueNotInitialized);
				return "";
			}
			
			return res;
		}

		@Override
		public boolean setValue(ErrorManager errorManager, int n, int size,
				String novoValor) {
			
			if (n >= size) {
				errorManager.attribError(ErrorManager.GeneralSetFailure);
				return false;
			}
			
			Item20044th i4th = (Item20044th) errorManager.getTree().getCurrentActivity().getItem();
			AdlcpMap m = i4th.getAdlcpData().get(n);
			
			if (!m.isWriteSharedData()) {
				errorManager.attribError(ErrorManager.DataModelElementIsReadOnly);
				return false;
			}
			
			// TODO precisa considerar o atributo sharedDataGlobalToSystem que indica que o valor será guardado no LMS, e nao somente para o usuario
			
			errorManager.getTree().storeData(m.getTargetID(), novoValor);
			
			return true;
		}

	}

}
