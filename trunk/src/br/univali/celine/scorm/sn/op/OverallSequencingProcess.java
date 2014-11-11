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
package br.univali.celine.scorm.sn.op;

import br.univali.celine.scorm.sn.ProcessProvider;
import br.univali.celine.scorm.sn.db.DeliveryRequestResult;
import br.univali.celine.scorm.sn.model.ActivityTree;
import br.univali.celine.scorm.sn.model.LearningActivity;
import br.univali.celine.scorm.sn.model.NavigationRequest;
import br.univali.celine.scorm.sn.model.NavigationRequestException;
import br.univali.celine.scorm.sn.model.NavigationRequestType;
import br.univali.celine.scorm.sn.model.SequencingRequest;
import br.univali.celine.scorm.sn.nb.NavigationRequestResult;
import br.univali.celine.scorm.sn.sb.seqreqprocess.ResultSequencingRequestCommand;
import br.univali.celine.scorm.sn.tb.TerminationRequestResult;

/**
 *	Overall Sequencing Process [OP.1]
 * 
 * @author Adilson Vahldick
 *
 */
public class OverallSequencingProcess {

	public LearningActivity run(NavigationRequest navigationRequest, ActivityTree activityTree) throws NavigationRequestException {
		return doRun(navigationRequest, activityTree, false);
	}
	
	public boolean isValidRequest(NavigationRequest navigationRequest, ActivityTree activityTree) {
		try {
			simulateRequest(navigationRequest, activityTree);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public LearningActivity simulateRequest(NavigationRequest navigationRequest, ActivityTree activityTree) throws Exception {

		try {
			return doRun(navigationRequest, activityTree, true);
			
		} catch (NavigationRequestException e) {
			throw new Exception(e);
		}
		
		
	}
	
	private LearningActivity doRun(NavigationRequest navigationRequest, ActivityTree activityTree, boolean justTest) throws NavigationRequestException {
		ProcessProvider pp = ProcessProvider.getInstance();
		NavigationRequestResult navreqprocres = pp.getNavigationRequestProcess().run(activityTree, navigationRequest);
		if (navreqprocres.isNavigationRequestValid() == false) { // 1.2
			// Behaviour not specified
			throw new NavigationRequestException(navreqprocres.getException()); 
		} 
	
		// adicao minha... o SN nao trata bem qdo se seleciona o mesmo item
		// entao eu adicionei essa condicao
		if (navigationRequest.getNavigationRequestCommand() == NavigationRequestType.Choice && 
				activityTree.getTargetActivity() == navreqprocres.getTargetActivity()) {
			throw new NavigationRequestException(55);
		}
		
		LearningActivity previousTargetAct = activityTree.getTargetActivity();
		
		activityTree.setTargetActivity(navreqprocres.getTargetActivity());
		
		SequencingRequest sequencingRequest = navreqprocres.getSequencingRequest();
		// if the current activity is active, end the attempt on the current activity
		if (justTest == false && navreqprocres.isTerminationRequest()) { // 1.3
			// se tirar justTest desse if, no termination request process altera vários valores das atividades
			TerminationRequestResult termreqres = pp.getTerminationRequestProcess().run(activityTree, navreqprocres.getTerminationRequest());
			if (termreqres.isValid() == false) {
				// Behaviour not specified
				throw new NavigationRequestException(termreqres.getException());
			}
			
			
			if (termreqres.getSequencingRequest() != null) {
				// there can only one pending sequencing request. Use the one returned by the termination request process, if it exists.
				sequencingRequest = termreqres.getSequencingRequest();	
			}
			
		}
		
		LearningActivity deliveryRequest = null;
		if (sequencingRequest != null) {  // 1.4
			ResultSequencingRequestCommand seqreqres = pp.getSequencingRequestProcess().run(activityTree, sequencingRequest);
			if (seqreqres.isValid()==false)
				throw new NavigationRequestException(seqreqres.getException());
			
			if (seqreqres.isEndSequencingSession()) {
				/*
				 * exiting from the root of the activity tree ends the sequencing session; return control to the LTS
				 */
				return null;
			}
			
			deliveryRequest = seqreqres.getDeliveryRequest();

			if (justTest) { // nao é para mudar a atividade corrente. Só verificar se o sequencing request é válido
				activityTree.setTargetActivity(previousTargetAct);
				return deliveryRequest; 
			}
			
			if (deliveryRequest == null)
				return null;
 
		}
		
		if (deliveryRequest != null) {
			DeliveryRequestResult deliveryRes = pp.getDeliveryRequestProcess().run(activityTree, deliveryRequest);
			if (deliveryRes.isValid() == false)
				// Behaviour not specified
				throw new NavigationRequestException(deliveryRes.getException());
			
			pp.getContentDeliveryEnvironmentProcess().run(activityTree, deliveryRequest);
		}
		
		return deliveryRequest;
	}
	
}
