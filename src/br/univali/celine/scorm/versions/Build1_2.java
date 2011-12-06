package br.univali.celine.scorm.versions;

import br.univali.celine.scorm.dataModel.DataModelCommandManager;
import br.univali.celine.scorm.dataModel.adl.nav.Request;
import br.univali.celine.scorm.dataModel.adl.nav.RequestValid;
import br.univali.celine.scorm.dataModel.cmi.Comments;
import br.univali.celine.scorm.dataModel.cmi.LaunchData;
import br.univali.celine.scorm.dataModel.cmi.LearnerPreference;
import br.univali.celine.scorm.dataModel.cmi.StudentData;
import br.univali.celine.scorm.dataModel.cmi.SuspendData;
import br.univali.celine.scorm.sn.ProcessProvider;
import br.univali.celine.scorm.sn.db.ClearSuspendedActivitySubprocess;
import br.univali.celine.scorm.sn.db.ContentDeliveryEnvironmentProcess;
import br.univali.celine.scorm.sn.db.DeliveryRequestProcess;
import br.univali.celine.scorm.sn.nb.NavigationRequestProcess;
import br.univali.celine.scorm.sn.op.OverallSequencingProcess;
import br.univali.celine.scorm.sn.rb.ActivityProgressRollupProcess;
import br.univali.celine.scorm.sn.rb.CheckChildRollupSubprocess;
import br.univali.celine.scorm.sn.rb.EvaluateRollupConditionsSubprocess;
import br.univali.celine.scorm.sn.rb.MeasureRollupProcess;
import br.univali.celine.scorm.sn.rb.OverallRollupProcess;
import br.univali.celine.scorm.sn.rb.RollupRuleCheckSubprocess;
import br.univali.celine.scorm.sn.sb.ChoiceActivityTraversalSubprocess;
import br.univali.celine.scorm.sn.sb.ChoiceFlowSubprocess;
import br.univali.celine.scorm.sn.sb.ChoiceFlowTreeTraversalSubprocess;
import br.univali.celine.scorm.sn.sb.FlowActivityTraversalSubprocess;
import br.univali.celine.scorm.sn.sb.FlowSubprocess;
import br.univali.celine.scorm.sn.sb.FlowTreeTraversalSubprocess;
import br.univali.celine.scorm.sn.sb.seqreqprocess.SequencingRequestProcess;
import br.univali.celine.scorm.sn.sr.RandomizeChildrenProcess;
import br.univali.celine.scorm.sn.sr.SelectChildrenProcess;
import br.univali.celine.scorm.sn.tb.SequencingExitActionRulesSubprocess;
import br.univali.celine.scorm.sn.tb.SequencingPostConditionRulesSubprocess;
import br.univali.celine.scorm.sn.tb.TerminationRequestProcess;
import br.univali.celine.scorm.sn.up.CheckActivityProcess;
import br.univali.celine.scorm.sn.up.EndAttemptProcess;
import br.univali.celine.scorm.sn.up.LimitConditionsCheckProcess;
import br.univali.celine.scorm.sn.up.SequencingRuleCheckSubprocess;
import br.univali.celine.scorm.sn.up.SequencingRulesCheckProcess;
import br.univali.celine.scorm.sn.up.TerminateDescendentAttemptsProcess;
import br.univali.celine.scorm1_2.dataModel.cmi.Core;

public class Build1_2 implements BuildVersion {

	public Build1_2() {
		
		buildDM();
		buildSN();
		
	}

	private void buildSN() {
		ProcessProvider.clearInstance();
		ProcessProvider pp = ProcessProvider.getInstance();
		
		pp.setActivityProgressRollupProcess(new ActivityProgressRollupProcess());
		pp.setCheckActivityProcess(new CheckActivityProcess());
		pp.setCheckChildRollupSubprocess(new CheckChildRollupSubprocess());
		pp.setChoiceActivityTraversalSubprocess(new ChoiceActivityTraversalSubprocess());
		pp.setChoiceFlowSubprocess(new ChoiceFlowSubprocess());
		pp.setChoiceFlowTreeTraversalSubprocess(new ChoiceFlowTreeTraversalSubprocess());
		pp.setClearSuspendedActivitySubprocess(new ClearSuspendedActivitySubprocess());
		pp.setContentDeliveryEnvironmentProcess(new ContentDeliveryEnvironmentProcess());
		pp.setDeliveryRequestProcess(new DeliveryRequestProcess());
		pp.setEndAttemptProcess(new EndAttemptProcess());
		pp.setEvaluateRollupConditionsSubprocess(new EvaluateRollupConditionsSubprocess());
		pp.setFlowActivityTraversalSubprocess(new FlowActivityTraversalSubprocess());
		pp.setFlowSubprocess(new FlowSubprocess());
		pp.setFlowTreeTraversalSubprocess(new FlowTreeTraversalSubprocess());
		pp.setLimitConditionsCheckProcess(new LimitConditionsCheckProcess());
		pp.setMeasureRollupProcess(new MeasureRollupProcess());
		pp.setNavigationRequestProcess(new NavigationRequestProcess());
		pp.setOverallRollupProcess(new OverallRollupProcess());
		pp.setOverallSequencingProcess(new OverallSequencingProcess());
		pp.setRandomizeChildrenProcess(new RandomizeChildrenProcess());
		pp.setRollupRuleCheckSubprocess(new RollupRuleCheckSubprocess());
		pp.setSelectChildrenProcess(new SelectChildrenProcess());
		pp.setSequencingExitActionRulesSubprocess(new SequencingExitActionRulesSubprocess());
		pp.setSequencingPostConditionRulesSubprocess(new SequencingPostConditionRulesSubprocess());
		pp.setSequencingRequestProcess(new SequencingRequestProcess());
		pp.setSequencingRuleCheckSubprocess(new SequencingRuleCheckSubprocess());
		pp.setSequencingRulesCheckProcess(new SequencingRulesCheckProcess());
		pp.setTerminateDescendentAttemptsProcess(new TerminateDescendentAttemptsProcess());
		pp.setTerminationRequestProcess(new TerminationRequestProcess());
	}

	private void buildDM() {
		DataModelCommandManager.clearGlobalInstance();
		DataModelCommandManager dm = DataModelCommandManager.getGlobalInstance();
		
		dm.put(Request.name, new Request());
		dm.put(RequestValid.name, new RequestValid());

		dm.put(Comments.name, new Comments());
		dm.put(Core.name, new Core());
		dm.put(StudentData.name, new StudentData());
		dm.put("cmi.student_preference", new LearnerPreference());
		dm.put(SuspendData.name, new SuspendData());
		dm.put(LaunchData.name, new LaunchData());

	}

	@Override
	public int getVersion() {
		return 12;
	}
	
}
