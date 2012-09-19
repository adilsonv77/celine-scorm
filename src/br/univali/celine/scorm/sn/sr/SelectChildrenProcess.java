package br.univali.celine.scorm.sn.sr;

import java.util.HashMap;

import br.univali.celine.scorm.model.imsss.SelectionTiming;
import br.univali.celine.scorm.sn.ProcessProvider;
import br.univali.celine.scorm.sn.model.LearningActivity;

// [SR.1]
/**
 * 
 * O SCORM não define onde e quando usar esse processo.
 * Eu apliquei nas seguintes situacoes:
 * + StartSequencingRequest: aplicando na raiz da arvore
 * 
 */


public class SelectChildrenProcess {

	private HashMap<SelectionTiming, SelectionTimingComm> comms = new HashMap<SelectionTiming, SelectionTimingComm>();
	
	public SelectChildrenProcess() {
		comms.put(SelectionTiming.never, new NeverSelectionTimingComm());
		comms.put(SelectionTiming.once, new OnceSelectionTimingComm());
		comms.put(SelectionTiming.onEachNewAttempt, new OnEachNewAttemptSelectionTimingComm());
	}
	
	public void run(LearningActivity activity) {
		
		// cannot apply selection to a leaf activity
		if (activity.hasChildren() == false) {
			return;
		}
		
		// cannot apply selection to a suspended or active activity
		if (activity.isSuspended() == true || activity.isActive() == true) {
			return;
		}
		
		SelectionTimingComm comm = comms.get(activity.getSelectionTiming());
		
		if (comm != null)
			comm.run(activity);
		else
			; // undefined timing attribute
		
		// The normative Sequencing Behavior Pseudo Code (refer to Appendix C) does not
		//    explicitly state when the Randomize Children Process is invoked. 
		// Se a especificação (inclusive da 4th) nao fala quando usar, entao eu chamo logo depois de selecionar os filhos
		ProcessProvider.getInstance().getRandomizeChildrenProcess().run(activity);
		
	}	
	
}
