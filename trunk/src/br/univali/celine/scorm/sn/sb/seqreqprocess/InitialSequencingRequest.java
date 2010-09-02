package br.univali.celine.scorm.sn.sb.seqreqprocess;

import br.univali.celine.scorm.sn.ProcessProvider;
import br.univali.celine.scorm.sn.model.LearningActivity;

public abstract class InitialSequencingRequest {

	protected void percorrerArvore(LearningActivity activity) {
		ProcessProvider.getInstance().getSelectChildrenProcess().run(activity);
		for (LearningActivity child:activity.getAvailableChildren())
			percorrerArvore(child);
	}

}
