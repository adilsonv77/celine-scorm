package br.univali.celine.scorm.sn.sr;

import java.util.ArrayList;
import java.util.List;

import br.univali.celine.scorm.sn.model.LearningActivity;

public class NeverSelectionTimingComm implements SelectionTimingComm {

	public void run(LearningActivity activity) {
		// nothing to do
		
		// minha mudanca: como todo o processo depende dos AvailableChildren, e esses filhos precisam ser
		//   iniciados em algum canto, modifiquei esse tipo de selecao para pegar todos os filhos da atividade
		
		List<LearningActivity> newChildren = new ArrayList<LearningActivity>();
		for (LearningActivity at:activity.getChildren())
			newChildren.add(at);
		
		activity.setAvailableChildren(newChildren);
	}

}
