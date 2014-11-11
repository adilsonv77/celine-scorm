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
package br.univali.celine.scorm.dataModel.cmi;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import br.univali.celine.scorm.dataModel.DataModelCommand;
import br.univali.celine.scorm.rteApi.ErrorManager;
import br.univali.celine.scorm.sn.model.dataTypes.LongIdentifierType;
import br.univali.celine.scorm.sn.model.dataTypes.Second10_2;
import br.univali.celine.scorm.sn.model.dataTypes.TimeSecond10_0;
import br.univali.celine.scorm.sn.model.interaction.Interaction;
import br.univali.celine.scorm.sn.model.interaction.OtherInteraction;

//cmi.interactions 4.2.9
/**
 * Fields Implemented:
 * <ul>
 * <li>_children</li>
 * <li>_count</li>
 * <li>n.id</li>
 * <li>n.type</li>
 * <li>n.timestamp</li>
 * <li>n.objectives
 * 		<ul>
 * 			<li>_count</li>
 * 			<li>m.id</li>
 * 		</ul>
 * </li>
 * <li>n.correct_responses
 * 		<ul>
 * 			<li>_count</li>
 * 			<li>m.pattern</li>
 * 		</ul>
 * </li>
 * <li>n.weighting</li>
 * <li>n.learner_response</li>
 * <li>n.result</li>
 * <li>n.latency</li>
 * <li>n.description</li>
 * </ul>
 * 
 * DM Completed !!!
 */
public class Interactions implements DataModelCommand {

	public static final String name = "cmi.interactions";
	private static final String simpleName = "interactions";

	private static final String[] type_state = new String[] { "choice",
			"fill-in", "likert", "long-fill-in", "matching", "numeric",
			"other", "performance", "sequencing", "true-false" };

	private static final String[] result_state = new String[] { "correct",
			"incorrect", "neutral", "unanticipated" };
	
	private DotNotationCommand interactionCount = new InteractionsCount();

	private DotNotationCommandManager commMan = new DotNotationCommandManager(
			simpleName, interactionCount );
	
	public Interactions() {
		commMan.add("id", new TrataIdInteractionsDotNotationCommand());
		commMan.add("type", new TrataType());
		commMan.add("timestamp", new TrataTimeStamp());
		commMan.add("weighting", new TrataWeighting());
		commMan.add("learner_response", new TrataLearnerResponse());
		commMan.add("result", new TrataResult());
		commMan.add("latency", new TrataLatency());
		commMan.add("description", new TrataDescription());
		commMan.add("objectives", null); // necessario para producao do
											// _children
		commMan.add("correct_responses", null); // necessario para producao do
												// _children

		// objectives e correct_responses sao tratados dentro dos métodos
		// getValue e setValue
	}

	private Logger logger = Logger.getLogger("global");

	public String getValue(String key, ErrorManager errorManager)
			throws Exception {

		if (key.equals("_count")) {

			String count = String.valueOf(errorManager.getTree()
					.getCurrActInteractionsCount());

			return count;

		}

		if (key.contains(".correct_responses.")) {
			return getTratarCorrectResponses(key, errorManager);
		} else {
			if (key.contains(".objectives.")) {
				return getTratarObjectives(key, errorManager);
			}
		}

		return commMan.getValue(key, errorManager);
	}

	public boolean setValue(String key, String newValue,
			ErrorManager errorManager) throws Exception {

		if (key.contains(".correct_responses.")) {
			return setTratarCorrectResponses(key, newValue, errorManager);
		}

		if (key.contains(".objectives.")) {
			return setTratarObjectives(key, newValue, errorManager);
		}

		return commMan.setValue(key, newValue, errorManager);
	}

	public void clear(ErrorManager errorManager) throws Exception {
		/*
		 * TODO rever se precisa e qdo fazer esse clear 
		 * // the interactions are per SCO errorManager.getTree().clearInteractions();
		 * commMan.clear(errorManager);
		 */
	}

	public void initialize(ErrorManager errorManager) {}

	private Interaction getInteraction(int index, ErrorManager errorManager) {
		try {
			return errorManager.getTree().getInteraction(index);
		} catch (Exception e) {
			logger.severe(e.getLocalizedMessage());
			errorManager.attribError(ErrorManager.DataModelDependencyNotEstablished);
			return null;
		}
	}

	private boolean gerarErroIndice(int indice, int size, ErrorManager errorManager, boolean get) {
		if (indice == size) { 
			if (get)
				errorManager.attribError(ErrorManager.GeneralGetFailure);
			else
				errorManager.attribError(ErrorManager.DataModelDependencyNotEstablished);
		}
		else
			if (indice > size) {
				if (get)
					errorManager.attribError(ErrorManager.GeneralGetFailure);
				else
					errorManager.attribError(ErrorManager.GeneralSetFailure);
			}
			else
				return false;
		
		return true;
		
	}
	
	private String getTratarCorrectResponses(String key,
			ErrorManager errorManager) {
		
		// _count
		// pattern
		
		String campos[] = key.split("[.]");

		int indice = Integer.parseInt(campos[0]);

		Interaction it = getInteraction(indice, errorManager);
		if (it == null) {
			
			int size = Integer.parseInt(interactionCount.getValue(errorManager, 0, 0));
			gerarErroIndice(indice, size, errorManager, true);
			return "";
			
		}

		if (campos[campos.length - 1].equals("_count")) {
			return String.valueOf(it.getCorrectResponsesPatternCount());
		}

		int indResp = Integer.parseInt(campos[campos.length - 2]);
		int size = it.getCorrectResponsesPatternCount();
		if (!gerarErroIndice(indResp, size, errorManager, true))
			try {
				return it.getCorrectResponsesPattern(indResp);
			} catch (Exception e) {
				errorManager.attribError(ErrorManager.GeneralGetFailure);
				return "";
			}
		else
			return "";
	}

	private boolean setTratarCorrectResponses(String key, String newValue,
			ErrorManager errorManager) {

		// _count
		// pattern
		
		String campos[] = key.split("[.]");

		if (campos[campos.length - 1].equals("_count")) {
			errorManager.attribError(ErrorManager.DataModelElementIsReadOnly);
			return false;
		}
		
		int indice = Integer.parseInt(campos[0]);
		
		Interaction it = getInteraction(indice, errorManager);
		if (it == null) {
			int size = Integer.parseInt(interactionCount.getValue(errorManager, 0, 0));
			return !gerarErroIndice(indice, size, errorManager, false);
		}

		if (it.getType() == null) {
			errorManager.attribError(ErrorManager.DataModelDependencyNotEstablished);
			return false;
		}

		try {
			int indResp = Integer.parseInt(campos[2]);
			int size = it.getCorrectResponsesPatternCount();
			
			if (indResp <= size) {
				it.setCorrectResponsesPattern(indResp, newValue);
			} else
				if (gerarErroIndice(indResp, size, errorManager, false)) 
					return false;
			
		} catch (Exception e) {
 		    errorManager.attribError(ErrorManager.DataModelElementTypeMismatch);
			return false;
		}

		return true;
	}

	private String getTratarObjectives(String key, ErrorManager errorManager) {

		// Tem somente os elementos abaixo !!!
		// n.objectives._count
		// n.objectives.m.id

		String fields[] = key.split("[.]");
		int indice = Integer.parseInt(fields[0]);
		Interaction it = getInteraction(indice, errorManager);
		if (it == null) {
			int size = Integer.parseInt(interactionCount.getValue(errorManager, 0, 0));
			gerarErroIndice(indice, size, errorManager, true);
			//errorManager.attribError(ErrorManager.GeneralGetFailure);
			return "";
		}
			

		if (fields[fields.length - 1].equals("_count")) {
			return String.valueOf(it.getObjectivesCount());
		}

		int indResp = Integer.parseInt(fields[2]);
		int size = it.getObjectivesCount();
		if (!gerarErroIndice(indResp, size, errorManager, true))
			return it.getObjectiveId(indResp);
		else
			return "";

	}

	private boolean setTratarObjectives(String key, String newValue,
			ErrorManager errorManager) {

		// Tem somente os elementos abaixo !!!
		// n.objectives._count
		// n.objectives.m.id

		String fields[] = key.split("[.]");
		if (fields[fields.length - 1].equals("_count")) {
			errorManager.attribError(ErrorManager.DataModelElementIsReadOnly);
			return false;
		}

		Interaction it = getInteraction(Integer.parseInt(fields[0]), errorManager);
		if (it == null) {
			errorManager.attribError(ErrorManager.DataModelDependencyNotEstablished);
			return false;
		}

		int m = Integer.parseInt(fields[2]);
		if (m > it.getObjectivesCount()) {
			errorManager.attribError(ErrorManager.GeneralSetFailure);
			return false;
		}

		if (fields[fields.length-1].equals("id")) {

			if (LongIdentifierType.validate(newValue) == false) {
				errorManager.attribError(ErrorManager.DataModelElementTypeMismatch);
				return false;
			}
			
			int idx = it.containsObjectiveId(newValue);
			if (idx >= 0 && idx != m) {
				errorManager.attribError(ErrorManager.GeneralSetFailure);
				return false;
			}
			
			it.setObjectiveId(m, newValue);
		}
		
		return true;
	}

	private class TrataIdInteractionsDotNotationCommand implements
			DotNotationCommand {

		public String getValue(ErrorManager errorManager, int n, int size) {
			if (n >= size) {
				errorManager.attribError(ErrorManager.GeneralGetFailure);
				return "";
			}

			return errorManager.getTree().getInteraction(n).getId();
		}

		public boolean setValue(ErrorManager errorManager, int n, int size,
				String interactionId) {

			if (LongIdentifierType.validate(interactionId) == false) {
				errorManager.attribError(ErrorManager.DataModelElementTypeMismatch);
				return false;
			}
			
			if (n > size) {
				errorManager.attribError(ErrorManager.GeneralSetFailure);
				return false;
			}

			List<Interaction> interactions = errorManager.getTree()
					.getCurrentActivity().getInteractions();
			for (Interaction interact : interactions) {
				if (interact.getId().equals(interactionId)) {
					errorManager.attribError(ErrorManager.GeneralSetFailure);
					return false;
				}
			}
			errorManager.getTree().addCurrActInteraction(
					new OtherInteraction(interactionId));

			return true;
		}

	}

	private class TrataType extends DotNotationCommandImpl {

		public TrataType() {
			super(type_state);
		}

		protected String doGetValue(ErrorManager errorManager, int n, int size) {
			String type = errorManager.getTree().getInteraction(n).getType();

			if (type == null) {
				errorManager.attribError(ErrorManager.DataModelElementValueNotInitialized);
				return "";
			}

			return type;
		}

		protected boolean doSetValue(ErrorManager errorManager, int n,
				int size, String novoValor) {

			errorManager.getTree().changeCurrActInteractionType(n, novoValor);

			return true;
		}

	}

	public class TrataTimeStamp extends DotNotationCommandImpl {

		public TrataTimeStamp() {
			super(null);
		}

		protected String doGetValue(ErrorManager errorManager, int n, int size) {
			String timeStamp = errorManager.getTree().getInteraction(n)
					.getTimeStamp();

			if (timeStamp == null) {
				errorManager
						.attribError(ErrorManager.DataModelElementValueNotInitialized);
				return "";
			}

			return timeStamp;
		}

		protected boolean doSetValue(ErrorManager errorManager, int n,
				int size, String novoValor) {

			if (TimeSecond10_0.validate(novoValor) == false) {
				errorManager
						.attribError(ErrorManager.DataModelElementTypeMismatch);
				return false;
			}

			errorManager.getTree().getInteraction(n).setTimeStamp(novoValor);

			return true;
		}

	}

	public class TrataWeighting extends DotNotationCommandImpl {

		public TrataWeighting() {
			super(null);
		}

		protected String doGetValue(ErrorManager errorManager, int n, int size) {
			Double weighting = errorManager.getTree().getInteraction(n)
					.getWeighting();
			if (weighting == null) {
				errorManager
						.attribError(ErrorManager.DataModelElementValueNotInitialized);
				return "";
			}
			return weighting.toString();
		}

		protected boolean doSetValue(ErrorManager errorManager, int n,
				int size, String novoValor) {

			double valor = 0;

			try {
				valor = Double.valueOf(novoValor);
			} catch (Exception e) {
				logger.severe(e.getLocalizedMessage());
				errorManager
						.attribError(ErrorManager.DataModelElementTypeMismatch);
				return false;
			}

			errorManager.getTree().getInteraction(n).setWeighting(valor);
			return true;
		}

	}

	public class TrataLearnerResponse extends DotNotationCommandImpl {

		public TrataLearnerResponse() {
			super(null);
		}

		protected String doGetValue(ErrorManager errorManager, int n, int size) {
			Interaction it = errorManager.getTree().getInteraction(n);
			
			String learnerResponse = it.getLearnerResponse();
			if (learnerResponse == null) {
				errorManager.attribError(ErrorManager.DataModelElementValueNotInitialized);
			}
			return learnerResponse;
		}

		protected boolean doSetValue(ErrorManager errorManager, int n,
				int size, String novoValor) {

			// Refer to Section 4.2.9.2: Learner Response Data Model Element
			// Specifics for more details on each type.
			// Essa seção foi a base para implementar o método
			// setLearnerResponse

			Interaction it = errorManager.getTree().getInteraction(n);
			
			if (it.getType() == null) {
				errorManager.attribError(ErrorManager.DataModelDependencyNotEstablished);
				return false;
			}
			
			try {
				it.setLearnerResponse(novoValor);
			} catch (Exception e) {
				logger.severe(e.getLocalizedMessage());
				errorManager.attribError(ErrorManager.DataModelElementTypeMismatch);
				return false;
			}

			return true;
		}

	}

	public class TrataDescription extends DotNotationCommandImpl {

		public TrataDescription() {
			super(null);
		}

		protected String doGetValue(ErrorManager errorManager, int n, int size) {
			String description = errorManager.getTree().getInteraction(n).getDescription();
			if (description == null) {
				errorManager.attribError(ErrorManager.DataModelElementValueNotInitialized);
				
			}
			return description;
		}

		protected boolean doSetValue(ErrorManager errorManager, int n,
				int size, String novoValor) {
			errorManager.getTree().getInteraction(n).setDescription(novoValor);
			return true;
		}

	}

	public class TrataLatency extends DotNotationCommandImpl {

		public TrataLatency() {
			super(null);
		}

		protected String doGetValue(ErrorManager errorManager, int n, int size) {
			String latency = errorManager.getTree().getInteraction(n).getLatency();

			if (latency == null) {
				errorManager.attribError(ErrorManager.DataModelElementValueNotInitialized);
			}

			return latency;
		}

		protected boolean doSetValue(ErrorManager errorManager, int n,
				int size, String novoValor) {

			if (Second10_2.validate(novoValor) == false) {
				errorManager.attribError(ErrorManager.DataModelElementTypeMismatch);
				return false;
			}

			errorManager.getTree().getInteraction(n).setLatency(novoValor);
			return true;
		}

	}

	public class TrataResult extends DotNotationCommandImpl {

		public TrataResult() {
			super(null);
		}

		protected String doGetValue(ErrorManager errorManager, int n, int size) {
			String res = errorManager.getTree().getInteraction(n).getResult();
			if (res == null)
				errorManager
						.attribError(ErrorManager.DataModelElementValueNotInitialized);
			return res;
		}

		protected boolean doSetValue(ErrorManager errorManager, int n,
				int size, String novoValor) {

			logger.info("TrataResult " + n + " " + novoValor);
			try {

				Double.valueOf(novoValor);

			} catch (Exception e) {
				logger.info("TrataResult não foi");
				// se nao é um numero entao tem que estar no vetor de strings
				// fixas
				if (Arrays.binarySearch(result_state, novoValor) < 0) {
					errorManager
							.attribError(ErrorManager.DataModelElementTypeMismatch);
					return false;
				}
			}

			errorManager.getTree().getInteraction(n).setResult(novoValor);

			return true;
		}

	}

	public class InteractionsCount implements DotNotationCommand {

		public String getValue(ErrorManager errorManager, int n, int size) {
			return String.valueOf(errorManager.getTree()
					.getCurrActInteractionsCount());
		}

		public boolean setValue(ErrorManager errorManager, int n, int size,
				String novoValor) {
			return false;
		}

	}


}
