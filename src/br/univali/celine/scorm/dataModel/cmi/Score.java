package br.univali.celine.scorm.dataModel.cmi;

import java.util.HashMap;

import br.univali.celine.scorm.dataModel.DataModelCommand;
import br.univali.celine.scorm.rteApi.ErrorManager;

// cmi.score 4.2.20
/**
 * Fields Implemented:
 * <ul>
 * <li>_children</li>
 * <li>scaled</li>
 * <li>raw</li>
 * <li>min</li>
 * <li>max</li>
 * 
 * DM Element completed!!!
 * 
 */

public class Score implements DataModelCommand {

	public static final String name = "cmi.score";

	private HashMap<String, DotNotationCommand> comms = new HashMap<String, DotNotationCommand>();
	
	public Score() {
		comms.put("scaled", new Scaled());
		comms.put("raw", new Raw());
		comms.put("min", new Min());
		comms.put("max", new Max());
	}
	
	
	public String getValue(String key, ErrorManager errorManager)
			throws Exception {

		if (key.equals("_children")) {
			return getChildren();
		}

		DotNotationCommand comm = comms.get(key);
		if (comm == null) {
			errorManager.attribError(ErrorManager.UndefinedDataModelElement);
			return "";
		}
		return comm.getValue(errorManager, -1, -1);
	}

	private String getChildren() {
		return "scaled, raw, min, max";
	}

	
	public boolean setValue(String key, String newValue,
			ErrorManager errorManager) throws Exception {
		
		if (key.equals("_children")) {
			errorManager.attribError(ErrorManager.DataModelElementIsReadOnly);
			return false;
		}

		DotNotationCommand comm = comms.get(key);
		if (comm == null) {
			errorManager.attribError(ErrorManager.UndefinedDataModelElement);
			return false;
		}
		
		return comm.setValue(errorManager, -1, -1, newValue);
	}

	
	public void clear(ErrorManager errorManager) throws Exception {
	}

	
	public void initialize(ErrorManager errorManager) {

	}

	private class Scaled implements DotNotationCommand {

		
		public String getValue(ErrorManager errorManager, int n, int size) {
			
			Double value = errorManager.getTree().getCurrentActivity().getScoreScaled();
			if (value == null) {
				errorManager.attribError(ErrorManager.DataModelElementValueNotInitialized);
				return "";
			}
			
			return String.valueOf(value);
		}

		
		public boolean setValue(ErrorManager errorManager, int n, int size,
				String novoValor) {

			Double value = null;
			try {
				
				value = Double.valueOf(novoValor);
				if (value <-1 || value >1) {
					errorManager.attribError(ErrorManager.DataModelElementValueOutOfRange);
					return false;
				}
				
			} catch (Exception e) {
				errorManager.attribError(ErrorManager.DataModelElementTypeMismatch);
				return false;
			}
			
			/* Sequencing Impacts
			 * 
			 * If the SCO sets cmi.score.scaled, the Objective Measure Status for the 
			   primary objective of the learning activity associated with the SCO shall be 
			   true, and the Objective Normalized Measure for the primary objective of the 
			   learning activity associated with the SCO shall equal the value of 
			   score.scaled.
			 * 
			 */
			
			// preferi fazer aqui no DataModel, do que no setScoreScaled, porque aqui é o SCO que seta
			errorManager.getTree().getCurrentActivity().setScoreScaled(value);
			errorManager.getTree().getCurrentActivity().getPrimaryObjective().setMeasureStatus(true);
			errorManager.getTree().getCurrentActivity().getPrimaryObjective().setNormalizedMeasure(value);
			
			return true;
		}
		
	}
	
	private class Raw implements DotNotationCommand {

		
		public String getValue(ErrorManager errorManager, int n, int size) {

			Double value = errorManager.getTree().getCurrentActivity().getScoreRaw();
			if (value == null) {
				errorManager.attribError(ErrorManager.DataModelElementValueNotInitialized);
				return "";
			}
			
			return String.valueOf(value);
		}

		
		public boolean setValue(ErrorManager errorManager, int n, int size,
				String novoValor) {
			Double value = null;
			try {
				value = Double.valueOf(novoValor);
			} catch (Exception e) {
				errorManager.attribError(ErrorManager.DataModelElementTypeMismatch);
				return false;
			}
			
			errorManager.getTree().getCurrentActivity().setScoreRaw(value);
			
			return true;
		}
		
	}
	
	private class Min implements DotNotationCommand {

		
		public String getValue(ErrorManager errorManager, int n, int size) {

			Double value = errorManager.getTree().getCurrentActivity().getScoreMin();
			if (value == null) {
				errorManager.attribError(ErrorManager.DataModelElementValueNotInitialized);
				return "";
			}
			
			return String.valueOf(value);
		}

		
		public boolean setValue(ErrorManager errorManager, int n, int size,
				String novoValor) {
			
			Double value = null;
			try {
				value = Double.valueOf(novoValor);
			} catch (Exception e) {
				errorManager.attribError(ErrorManager.DataModelElementTypeMismatch);
				return false;
			}
			
			errorManager.getTree().getCurrentActivity().setScoreMin(value);
			
			return true;
		}
				
	}
	
	private class Max implements DotNotationCommand {

		
		public String getValue(ErrorManager errorManager, int n, int size) {

			Double value = errorManager.getTree().getCurrentActivity().getScoreMax();
			if (value == null) {
				errorManager.attribError(ErrorManager.DataModelElementValueNotInitialized);
				return "";
			}
			
			return String.valueOf(value);
		}

		
		public boolean setValue(ErrorManager errorManager, int n, int size,
				String novoValor) {
			
			Double value = null;
			try {
				value = Double.valueOf(novoValor);
			} catch (Exception e) {
				errorManager.attribError(ErrorManager.DataModelElementTypeMismatch);
				return false;
			}
			
			errorManager.getTree().getCurrentActivity().setScoreMax(value);
			
			return true;
		}
		
	}
	
}
