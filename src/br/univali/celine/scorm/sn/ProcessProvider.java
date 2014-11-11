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
package br.univali.celine.scorm.sn;

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

/**
 * Essa classe contém os objetos de processos e subprocessos, conforme mapeado no XML.
 * Isso nos permite uma grande flexibilidade na customizacao do padrão, assim como na 
 * manutencao quando liberadas novas versões.
 * 
 * Esse conceito segue a idéia da Injeção de Controle (IoC)
 * 
 * @author Adilson Vahldick
 *
 */
public class ProcessProvider {

	private static ProcessProvider instance;

	public static ProcessProvider getInstance() {
		
		if (instance == null) {
			instance = new ProcessProvider();
		}
		
		return instance;
		
	}
	
	public static void clearInstance() {
		instance = null;
	}
	
	private OverallSequencingProcess overallSequencingProcess; // [OP.1]
	
	private NavigationRequestProcess navigationRequestProcess; // [NB.2.1]
	
	private SequencingExitActionRulesSubprocess sequencingExitActionRulesSubprocess; // [TB.2.1]
	private SequencingPostConditionRulesSubprocess sequencingPostConditionRulesSubprocess; // [TB.2.2]
	private TerminationRequestProcess terminationRequestProcess; // [TB.2.3]
	
	private MeasureRollupProcess measureRollupProcess; // [RB.1.1]
	private ActivityProgressRollupProcess activityProgressRollupProcess; // [RB.1.3]
	private RollupRuleCheckSubprocess rollupRuleCheckSubprocess; // [RB.1.4]
	private EvaluateRollupConditionsSubprocess evaluateRollupConditionsSubprocess; // [RB.1.4.1]
	private CheckChildRollupSubprocess checkChildRollupSubprocess; // [RB.1.4.2]
	private OverallRollupProcess overallRollupProcess; // [RB.1.5]
	
	private SelectChildrenProcess selectChildrenProcess; // [SR.1]
	private RandomizeChildrenProcess randomizeChildrenProcess; // [SR.2]
	
	private FlowTreeTraversalSubprocess flowTreeTraversalSubprocess; // [SB.2.1]
	private FlowActivityTraversalSubprocess flowActivityTraversalSubprocess; // [SB.2.2]
	private FlowSubprocess flowSubprocess; // [SB.2.3]
	private ChoiceActivityTraversalSubprocess choiceActivityTraversalSubprocess; // [SB.2.4]
	private ChoiceFlowSubprocess choiceFlowSubprocess; // [SB.2.9.1]
	private ChoiceFlowTreeTraversalSubprocess choiceFlowTreeTraversalSubprocess; // [SB.2.9.1] 
	private SequencingRequestProcess sequencingRequestProcess; // [SB.2.12]
	
	private DeliveryRequestProcess deliveryRequestProcess; // [DB.1.1]
	private ContentDeliveryEnvironmentProcess contentDeliveryEnvironmentProcess; // [DB.2] 
	private ClearSuspendedActivitySubprocess clearSuspendedActivitySubprocess; // [DB.2.1] 
	
	private LimitConditionsCheckProcess limitConditionsCheckProcess; // [UP.1]
	private SequencingRulesCheckProcess sequencingRulesCheckProcess; // [UP.2]
	private SequencingRuleCheckSubprocess sequencingRuleCheckSubprocess; // [UP.2.1]
	private TerminateDescendentAttemptsProcess terminateDescendentAttemptsProcess; // [UP.3]
	private EndAttemptProcess endAttemptProcess; // [UP.4]
	private CheckActivityProcess checkActivityProcess; // [UP.5] 

	public NavigationRequestProcess getNavigationRequestProcess() {
		return navigationRequestProcess;
	}
	public void setNavigationRequestProcess(
			NavigationRequestProcess navigationRequestProcess) {
		this.navigationRequestProcess = navigationRequestProcess;
	}
	public OverallSequencingProcess getOverallSequencingProcess() {
		return overallSequencingProcess;
	}
	public void setOverallSequencingProcess(
			OverallSequencingProcess overallSequencingProcess) {
		this.overallSequencingProcess = overallSequencingProcess;
	}
	public SequencingRequestProcess getSequencingRequestProcess() {
		return sequencingRequestProcess;
	}
	public void setSequencingRequestProcess(
			SequencingRequestProcess sequencingRequestProcess) {
		this.sequencingRequestProcess = sequencingRequestProcess;
	}
	public TerminationRequestProcess getTerminationRequestProcess() {
		return terminationRequestProcess;
	}
	public void setTerminationRequestProcess(
			TerminationRequestProcess terminationRequestProcess) {
		this.terminationRequestProcess = terminationRequestProcess;
	}
	public EndAttemptProcess getEndAttemptProcess() {
		return endAttemptProcess;
	}
	public void setEndAttemptProcess(EndAttemptProcess endAttemptProcess) {
		this.endAttemptProcess = endAttemptProcess;
	}
	public SequencingExitActionRulesSubprocess getSequencingExitActionRulesSubprocess() {
		return this.sequencingExitActionRulesSubprocess;
	}
	public void setSequencingExitActionRulesSubprocess(
			SequencingExitActionRulesSubprocess sequencingExitActionRulesSubprocess) {
		this.sequencingExitActionRulesSubprocess = sequencingExitActionRulesSubprocess;
	}
	public OverallRollupProcess getOverallRollupProcess() {
		return overallRollupProcess;
	}
	public void setOverallRollupProcess(OverallRollupProcess overallRollupProcess) {
		this.overallRollupProcess = overallRollupProcess;
	}
	public MeasureRollupProcess getMeasureRollupProcess() {
		return measureRollupProcess;
	}
	public void setMeasureRollupProcess(MeasureRollupProcess measureRollupProcess) {
		this.measureRollupProcess = measureRollupProcess;
	}
	public ActivityProgressRollupProcess getActivityProgressRollupProcess() {
		return activityProgressRollupProcess;
	}
	public void setActivityProgressRollupProcess(
			ActivityProgressRollupProcess activityProgressRollupProcess) {
		this.activityProgressRollupProcess = activityProgressRollupProcess;
	}
	public SequencingPostConditionRulesSubprocess getSequencingPostConditionRulesSubprocess() {
		return sequencingPostConditionRulesSubprocess;
	}
	public void setSequencingPostConditionRulesSubprocess(
			SequencingPostConditionRulesSubprocess sequencingPostConditionRulesSubprocess) {
		this.sequencingPostConditionRulesSubprocess = sequencingPostConditionRulesSubprocess;
	}
	public SelectChildrenProcess getSelectChildrenProcess() {
		return selectChildrenProcess;
	}
	public void setSelectChildrenProcess(SelectChildrenProcess selectChildrenProcess) {
		this.selectChildrenProcess = selectChildrenProcess;
	}
	public TerminateDescendentAttemptsProcess getTerminateDescendentAttemptsProcess() {
		return terminateDescendentAttemptsProcess;
	}
	public void setTerminateDescendentAttemptsProcess(
			TerminateDescendentAttemptsProcess terminateDescendentAttemptsProcess) {
		this.terminateDescendentAttemptsProcess = terminateDescendentAttemptsProcess;
	}
	public SequencingRulesCheckProcess getSequencingRulesCheckProcess() {
		return this.sequencingRulesCheckProcess;
		
	}
	public void setSequencingRulesCheckProcess(
			SequencingRulesCheckProcess sequencingRulesCheckProcess) {
		this.sequencingRulesCheckProcess = sequencingRulesCheckProcess;
	}
	public LimitConditionsCheckProcess getLimitConditionsCheckProcess() {
		return this.limitConditionsCheckProcess;
	}
	public void setLimitConditionsCheckProcess(
			LimitConditionsCheckProcess limitConditionsCheckProcess) {
		this.limitConditionsCheckProcess = limitConditionsCheckProcess;
	}
	public CheckActivityProcess getCheckActivityProcess() {
		return checkActivityProcess;
	}
	public void setCheckActivityProcess(CheckActivityProcess checkActivityProcess) {
		this.checkActivityProcess = checkActivityProcess;
	}
	public SequencingRuleCheckSubprocess getSequencingRuleCheckSubprocess() {
		return this.sequencingRuleCheckSubprocess;
	}
	public void setSequencingRuleCheckSubprocess(
			SequencingRuleCheckSubprocess sequencingRuleCheckSubprocess) {
		this.sequencingRuleCheckSubprocess = sequencingRuleCheckSubprocess;
	}
	public RollupRuleCheckSubprocess getRollupRuleCheckSubprocess() {
		return rollupRuleCheckSubprocess;
	}
	public void setRollupRuleCheckSubprocess(
			RollupRuleCheckSubprocess rollupRuleCheckSubprocess) {
		this.rollupRuleCheckSubprocess = rollupRuleCheckSubprocess;
	}
	public CheckChildRollupSubprocess getCheckChildRollupSubprocess() {
		return checkChildRollupSubprocess;
	}
	public void setCheckChildRollupSubprocess(
			CheckChildRollupSubprocess checkChildRollupSubprocess) {
		this.checkChildRollupSubprocess = checkChildRollupSubprocess;
	}
	public EvaluateRollupConditionsSubprocess getEvaluateRollupConditionsSubprocess() {
		return evaluateRollupConditionsSubprocess;
	}
	public void setEvaluateRollupConditionsSubprocess(
			EvaluateRollupConditionsSubprocess evaluateRollupConditionsSubprocess) {
		this.evaluateRollupConditionsSubprocess = evaluateRollupConditionsSubprocess;
	}
	public RandomizeChildrenProcess getRandomizeChildrenProcess() {
		return randomizeChildrenProcess;
	}
	public void setRandomizeChildrenProcess(
			RandomizeChildrenProcess randomizeChildrenProcess) {
		this.randomizeChildrenProcess = randomizeChildrenProcess;
	}
	public DeliveryRequestProcess getDeliveryRequestProcess() {
		return deliveryRequestProcess;
	}
	public void setDeliveryRequestProcess(
			DeliveryRequestProcess deliveryRequestProcess) {
		this.deliveryRequestProcess = deliveryRequestProcess;
	}
	public FlowTreeTraversalSubprocess getFlowTreeTraversalSubprocess() {
		return flowTreeTraversalSubprocess;
	}
	public void setFlowTreeTraversalSubprocess(
			FlowTreeTraversalSubprocess flowTreeTraversalSubprocess) {
		this.flowTreeTraversalSubprocess = flowTreeTraversalSubprocess;
	}
	public FlowActivityTraversalSubprocess getFlowActivityTraversalSubprocess() {
		return flowActivityTraversalSubprocess;
	}
	public void setFlowActivityTraversalSubprocess(
			FlowActivityTraversalSubprocess flowActivityTraversalSubprocess) {
		this.flowActivityTraversalSubprocess = flowActivityTraversalSubprocess;
	}
	public FlowSubprocess getFlowSubprocess() {
		return flowSubprocess;
	}
	public void setFlowSubprocess(FlowSubprocess flowSubprocess) {
		this.flowSubprocess = flowSubprocess;
	}
	public ChoiceActivityTraversalSubprocess getChoiceActivityTraversalSubprocess() {
		return choiceActivityTraversalSubprocess;
	}
	public void setChoiceActivityTraversalSubprocess(
			ChoiceActivityTraversalSubprocess choiceActivityTraversalSubprocess) {
		this.choiceActivityTraversalSubprocess = choiceActivityTraversalSubprocess;
	}
	public ContentDeliveryEnvironmentProcess getContentDeliveryEnvironmentProcess() {
		return this.contentDeliveryEnvironmentProcess;
	}
	public void setContentDeliveryEnvironmentProcess(
			ContentDeliveryEnvironmentProcess contentDeliveryEnvironmentProcess) {
		this.contentDeliveryEnvironmentProcess = contentDeliveryEnvironmentProcess;
	}
	public ChoiceFlowSubprocess getChoiceFlowSubprocess() {
		return choiceFlowSubprocess;
	}
	public void setChoiceFlowSubprocess(ChoiceFlowSubprocess choiceFlowSubprocess) {
		this.choiceFlowSubprocess = choiceFlowSubprocess;
	}
	public ChoiceFlowTreeTraversalSubprocess getChoiceFlowTreeTraversalSubprocess() {
		return choiceFlowTreeTraversalSubprocess;
	}
	public void setChoiceFlowTreeTraversalSubprocess(
			ChoiceFlowTreeTraversalSubprocess choiceFlowTreeTraversalSubprocess) {
		this.choiceFlowTreeTraversalSubprocess = choiceFlowTreeTraversalSubprocess;
	}
	public ClearSuspendedActivitySubprocess getClearSuspendedActivitySubprocess() {
		return clearSuspendedActivitySubprocess;
	}
	public void setClearSuspendedActivitySubprocess(
			ClearSuspendedActivitySubprocess clearSuspendedActivitySubprocess) {
		this.clearSuspendedActivitySubprocess = clearSuspendedActivitySubprocess;
	}
	
	
	
	
}
