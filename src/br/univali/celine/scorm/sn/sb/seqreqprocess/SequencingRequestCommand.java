package br.univali.celine.scorm.sn.sb.seqreqprocess;

import br.univali.celine.scorm.sn.model.ActivityTree;

public interface SequencingRequestCommand {

	ResultSequencingRequestCommand run(ActivityTree activityTree);

}
