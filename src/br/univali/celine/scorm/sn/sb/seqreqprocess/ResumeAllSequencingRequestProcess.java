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
package br.univali.celine.scorm.sn.sb.seqreqprocess;

import br.univali.celine.scorm.sn.model.ActivityTree;

/**
 * Resume All Sequencing Request Process [SB.2.6]
 *
 * Passo (2.) do Sequencing Request Process [SB.2.12] 
 * 
 * @author Adilson Vahldick
 *
 */

public class ResumeAllSequencingRequestProcess extends InitialSequencingRequest implements
		SequencingRequestCommand {

	
	public ResultSequencingRequestCommand run(ActivityTree activityTree) {
		if (activityTree.getCurrentActivity() != null) {
			// make sure the sequencing session has not already begun
			return new ResultSequencingRequestCommand(31);
		}
		
		if (activityTree.getSuspendActivity() == null) {
			// make sure there is something to resume
			return new ResultSequencingRequestCommand(32);
		}
		
		// minha decisao: o SCORM nao define quando executar esse processo
		// desnecessario... no carregamento da arvore a partir da persistencia ele precisa carregar os filhos da ultima interacao
		// percorrerArvore(activityTree.getRootActivity());

		ResultSequencingRequestCommand res = new ResultSequencingRequestCommand();
		res.setDeliveryRequest(activityTree.getSuspendActivity());
		return res;
	}

}
