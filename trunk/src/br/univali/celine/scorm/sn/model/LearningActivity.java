package br.univali.celine.scorm.sn.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import br.univali.celine.scorm.model.cam.Item;
import br.univali.celine.scorm.model.cam.Item20043rd;
import br.univali.celine.scorm.model.cam.SuperItem;
import br.univali.celine.scorm.model.imsss.ConditionOperator;
import br.univali.celine.scorm.model.imsss.RandomizationTiming;
import br.univali.celine.scorm.model.imsss.RequiredForRollupConsiderations;
import br.univali.celine.scorm.model.imsss.RollupAction;
import br.univali.celine.scorm.model.imsss.RollupCondition;
import br.univali.celine.scorm.model.imsss.RollupRule;
import br.univali.celine.scorm.model.imsss.RuleCondition;
import br.univali.celine.scorm.model.imsss.SelectionTiming;
import br.univali.celine.scorm.model.imsss.SequencingRule;
import br.univali.celine.scorm.sn.model.interaction.Interaction;
import br.univali.celine.scorm2004_4th.model.cam.Item20044th;

public class LearningActivity {

	// Item mapeado do XML
	private SuperItem item;

	// esses saos os filhos realmente da atividade
	private List<LearningActivity> children = new ArrayList<LearningActivity>(); 
	
	// o pai do atividade. é inicializado quando monta a lista de filhos (children)
	private LearningActivity parent;

	private boolean active;

	private boolean suspended;

	// se refere a lista de objetivos da atividade
	private List<ProxyObjective> objectives = new ArrayList<ProxyObjective>();

	// sao os filhos selecionados pelo processo
	private List<LearningActivity> availableChildren;

	private int attemptCount;

	private boolean attemptProgressStatus;

	private boolean attemptCompletionStatus;

	private boolean progressStatus;

	private double attemptExperiencedDuration;

	private double activityExperiencedDuration;

	private double initialSessionTime;

	private LocalObjective primaryObjective;

	private static Logger logger = Logger.getLogger("global");

	public LearningActivity(SuperItem item) {
		this.item = item;
	}
	
	public String getLearningActivityId() {
		return getItem().getIdentifier();
	}
	
	public SuperItem getItem() {
		return this.item;
	}
	
	public String toString() {
		return this.item.getTitle();
	}
	
	public void addChild(LearningActivity activity) {
		children.add(activity);
		activity.setParent(this);
	}
	
	public boolean isLeaf() {
		return children.isEmpty();
	}

	public boolean hasChildren() {
		return this.isLeaf() == false;
	}
	
	public List<LearningActivity> getChildren() {
		return this.children;
	}

	public List<LearningActivity> getAvailableChildren() {
		if (this.availableChildren == null)
			this.availableChildren = new ArrayList<LearningActivity>(children.size());
		
		return this.availableChildren;
	}
	
	public void setAvailableChildren(List<LearningActivity> newChildren) {
		this.availableChildren = newChildren;
	}

	public LearningActivity getParent() {
		return this.parent;
	}

	private void setParent(LearningActivity parent) {
		this.parent = parent;
	}

	public boolean hasParent() {
		return getParent() != null;
	}

	public boolean isActive() {
		return this.active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isSuspended() {
		return this.suspended;
	}

	public void setSuspended(boolean suspended) {
		this.suspended = suspended;
	}

	// is there any child whose suspended is true?
	public boolean existsChildSuspended() {
		
		for (LearningActivity child:children) {
			if (child.isSuspended())
				return true;
		}
		
		return false;
	}

	public List<ProxyObjective> getObjectives() {
		return this.objectives;
	}

	public LocalObjective getPrimaryObjective() {
		/*
		br.univali.celine.scorm.model.imsss.Objective primObj = this.item.getImsssSequencing().getObjectives().getPrimaryObjective();
		for (LocalObjective obj:objectives) {
			if (obj.getReferencedObjective() == primObj)
				return obj;
		}
		return null;
		*/
		return primaryObjective;
	}

	public void setPrimaryObjective(LocalObjective objective) {
		this.primaryObjective = objective;
	}

	public LocalObjective getObjective(String objectiveID) {
		if (objectiveID == null) // se o id foi submetido, entao usa o primario
			return getObjectives().get(0);
		
		for (LocalObjective objective:getObjectives()) {
			if (objectiveID.equals(objective.getObjectiveID())) // tem que ser desse jeito pois o objprimario, que fica na lista, nao tem ID
				return objective;
			
		}
		return null;
	}

	public boolean isSequencingControlFlow() {
		return this.item.getImsssSequencing().getControlMode().isFlow();
	}

	public boolean isSequencingControlForwardOnly() {
		return this.item.getImsssSequencing().getControlMode().isForwardOnly();
	}

	public boolean isSequencingControlChoice() {
		return this.item.getImsssSequencing().getControlMode().isChoice();
	}

	public boolean isSequencingControlChoiceExit() {
		return this.item.getImsssSequencing().getControlMode().isChoiceExit();
	}

	public int getAttemptCount() {
		return this.attemptCount;
	}

	public void incAttemptCount() {
		this.attemptCount++;	
	}

	/**
	 * Usado no carregamento da atividade a partir da persistencia
	 *  
	 * @param attemptCount
	 */
	public void setAttemptCount(int attemptCount) {
		this.attemptCount = attemptCount;
	}

	// se as atividades sao irmãs
	public boolean isSibling(LearningActivity learningActivity) {
		return parent == learningActivity.getParent();
	}

	public List<SequencingRule> getExitSequencingRules() {
		return this.item.getImsssSequencing().getExitConditionRules();
	}

	public List<SequencingRule> getPostConditionSequencingRules() {
		return this.item.getImsssSequencing().getPostConditionRules();
	}

	public List<SequencingRule> getSkipSequencingRules() {
		return this.item.getImsssSequencing().getSkippedConditionRules();
	}

	public List<SequencingRule> getStopSequencingRules() {
		return this.item.getImsssSequencing().getStoppedConditionRules();
	}

	public List<SequencingRule> getHideFromChoiceSequencingRules() {
		return this.item.getImsssSequencing().getHideFromChoiceConditionRules();
	}

	public List<SequencingRule> getDisabledSequencingRules() {
		return this.item.getImsssSequencing().getDisabledConditionRules();
	}

	public boolean isAttemptProgressStatus() {
		return this.attemptProgressStatus;
	}

	public void setAttemptProgressStatus(boolean attemptProgressStatus) {
		this.attemptProgressStatus = attemptProgressStatus;
	}

	public boolean isAttemptCompletionStatus() {
		return this.attemptCompletionStatus;
	}

	public void setAttemptCompletionStatus(boolean attemptCompletionStatus) {
		this.attemptCompletionStatus = attemptCompletionStatus;
	}

	public boolean isProgressStatus() {
		return this.progressStatus;
	}

	public void setProgressStatus(boolean progressStatus) {
		this.progressStatus = progressStatus;
	}

	public boolean isTracked() {
		return this.item.getImsssSequencing().getDeliveryControls().isTracked();
	}

	public boolean isCompletionSetByContent() {
		return this.item.getImsssSequencing().getDeliveryControls().isCompletionSetByContent();
	}

	public boolean isObjectiveSetByContent() {
		return this.item.getImsssSequencing().getDeliveryControls().isObjectiveSetByContent();
	}

	public int getLimitConditionAttemptLimit() {
		return this.item.getImsssSequencing().getLimitConditions().getAttemptLimit();
	}

	public boolean isLimitConditionAttemptControl() {
		return getLimitConditionAttemptLimit() > 0; // nao encontrei nada nos livros onde atribui esse valor. Entao estabeleci essa regra
	}

	public double getLimitConditionAbsoluteDurationLimit() {
		return this.item.getImsssSequencing().getLimitConditions().getAttemptAbsoluteDurationLimitInSeconds();
	}

	public boolean isLimitConditionAbsoluteDurationControl() {
		return getLimitConditionAbsoluteDurationLimit() > 0; // nao encontrei nada nos livros onde atribui esse valor. Entao estabeleci essa regra
	}

	public SelectionTiming getSelectionTiming() {
		return this.item.getImsssSequencing().getRandomizationControls().getEnumSelectionTiming();
	}

	public int getSelectionCount() {
		return this.item.getImsssSequencing().getRandomizationControls().getSelectCount();
	}

	public RandomizationTiming getRandomizationTiming() {
		return this.item.getImsssSequencing().getRandomizationControls().getEnumRandomizationTiming();
	}

	public boolean isSelectionCountStatus() {
		return getSelectionCount() > 0; // nao encontrei nos livros onde buscar esse atributo
										// entao criei essa regrinha
	}

	public boolean isRandomizeChildren() {
		return this.item.getImsssSequencing().getRandomizationControls().isReorderChildren();
	}

	public void reorderRandomly() {
		// reorganiza randomicamente os filhos validos dessa atividade (ver SR.2)
		
		Random sorteio = new Random();
		
		List<LearningActivity> novo = new ArrayList<LearningActivity>();
		while (availableChildren.isEmpty() == false) {
			int index = Math.abs(sorteio.nextInt(availableChildren.size()));
			novo.add(availableChildren.get(index));
			availableChildren.remove(index);
		}
		
		this.availableChildren = novo;
	}

	public ObjectiveRollupUsing getObjectiveRollupUsing() {
		if (this.item.getImsssSequencing().getObjectives().getPrimaryObjective().isSatisfiedByMeasure())
			return ObjectiveRollupUsing.MEASURE;
		else
			return ObjectiveRollupUsing.RULES;
	}

	/* SN.Table 4.2.1.3a. 
	 * The cumulative duration of all attempts on 
	   the activity, i.e., the time from the initial start 
	   of the activity to the end of the activity.
	   
	 * The mechanism for determining the duration is not defined in this model. */
	
	public double getActivityAbsoluteDuration() {
		return 0;
	}

	// usado no TrackModel
	public void setActivityAbsoluteDuration(double absoluteDuration) {
	}

	/*
	 *  The cumulative experienced duration of all 
		attempts on the activity, i.e., the time from 
		the initial start of the activity to the end of the 
		activity, not including any time elapsed while 
		the activity is suspended (i.e., when the 
		activity is not being experienced or is inactive).
	 */
	public double getExperiencedDuration() {
		double r = 0;
		if (hasChildren()) {
			for (LearningActivity la:getChildren()) {
				r += la.getExperiencedDuration();
			}
		} else {
			r = activityExperiencedDuration;
			if (initialSessionTime > 0) {
				r += System.currentTimeMillis() - initialSessionTime; 
			}
		}
		return r;
	}

	public void setExperiencedDuration(double experiencedDuration) {
		this.activityExperiencedDuration = experiencedDuration;
	}
	
	// devolve o tempo dessa tentativa
	public double getAttemptAbsoluteDuration() {
		
		return 0; // TODO ainda nao implementado e nem é exigencia do LMS
	}

	public double getAttemptExperiencedDuration() {
		/*
		 * preciso anotar aqui o motivo desse if...
		 * no RDBDAO é invocado esse método, e aquela altura é necessario obter a duracao da atividade
		if (active == false)
			return 0;
		*/
		double ret = attemptExperiencedDuration;
		if (initialSessionTime > 0) {
			ret += System.currentTimeMillis() - initialSessionTime; 
		}
		return ret;
	}

	public int getCurrentTimePoint() {
		return 0; // TODO ainda nao implementado e nem é exigencia do LMS
	}

	public double getRolledUpObjectiveMeasureWeight() {
		return item.getImsssSequencing().getRollupRules().getObjectiveMeasureWeight();
	}

	public boolean isRollupObjectiveSatisfied() {
		return item.getImsssSequencing().getRollupRules().isRollupObjectiveSatisfied();
	}

	public boolean isRollupProgressCompletion() {
		return item.getImsssSequencing().getRollupRules().isRollupProgressCompletion();
	}

	public boolean isMeasureSatisfactionIfActive() {
		return item.getImsssSequencing().getRollupConsiderations().isMeasureSatisfactionIfActive();
	}

	public RequiredForRollupConsiderations getRequiredForSatisfied() {
		return item.getImsssSequencing().getRollupConsiderations().getEnumRequiredForSatisfied();
	}

	public RequiredForRollupConsiderations getRequiredForNotSatisfied() {
		return item.getImsssSequencing().getRollupConsiderations().getEnumRequiredForNotSatisfied();
	}

	public RequiredForRollupConsiderations getRequiredForCompleted() {
		return item.getImsssSequencing().getRollupConsiderations().getEnumRequiredForCompleted();
	}

	public RequiredForRollupConsiderations getRequiredForIncomplete() {
		return item.getImsssSequencing().getRollupConsiderations().getEnumRequiredForIncomplete();
	}

	public boolean isPreventActivation() {
		return item.getImsssSequencing().getConstrainedChoiceConsiderations().isPreventActivation();
	}

	public boolean isConstrainedChoice() {
		return item.getImsssSequencing().getConstrainedChoiceConsiderations().isConstrainChoice();
	}

	public boolean containsRollupRules(RollupAction rollupAction) {

		for (RollupRule rr:item.getImsssSequencing().getRollupRules().getRollupRules())
			if (rr.getRollupAction().equals(rollupAction))
				return true;
		
		return false;
	}

	public List<RollupRule> selectRollupRules(RollupAction rollupAction) {
		List<RollupRule> rollupRules = new ArrayList<RollupRule>();
		
		for (RollupRule rr:item.getImsssSequencing().getRollupRules().getRollupRules())
			if (rr.getRollupAction().equals(rollupAction))
				rollupRules.add(rr);
		return rollupRules;
		
	}

	// se a atividade atual vem depois da atividade passada como parametro
	public boolean isForwardFrom(LearningActivity activity) {
		return getParent().getChildren().indexOf(this) > getParent().getChildren().indexOf(activity);
	}
	
	// se a atividade atual é descendente da atividade passada como parametro
	public boolean isDescendantFrom(LearningActivity activity) {
		
		return percorrerFilhos(activity);
		
	}

	private boolean percorrerFilhos(LearningActivity activity) {
		for (LearningActivity child:activity.getChildren()) {
			if (child == this)
				return true;
			boolean ret = percorrerFilhos(child);
			if (ret == true)
				return true;
		}
		return false;
		
	}

	public void initializeObjectiveProgressInformation() {
		// TODO existem seções no livro SN sobre esse assunto
	}

	public void initializeActivityProgressInformation() {
		activityExperiencedDuration = 0; // esse método somente é chamado no primeiro attempt
	}
	
	public void initializeAttemptProgressInformation() {
		// TODO existem seções no livro SN sobre esse assunto
	}

	/*
	[- - - - - - - - - - - - Activity Time - - - - - - - - - - - - - - ]
	[- - - - Learner Attempt - - - ]	[- - - - Learner Attempt - - - ]  
	[ Learner ]          [ Learner ]	[ Learner ]          [ Learner ]
	[ Session ]          [ Session ]	[ Session ]          [ Session ]
	 */
	
	public void startAttemptExperiencedDuration() {
		attemptExperiencedDuration = 0;
		startSession();
	}

	public void startAttemptAbsoluteDuration() {
		// TODO existem seções no livro SN sobre esse assunto
	}

	public void suspend() {
		setSuspended(true);
		endSession();
	}

	private void startSession() {
		double b = initialSessionTime;
		initialSessionTime = System.currentTimeMillis();
		logger.info("startSession=> " + b + ":" + initialSessionTime + "[" + this + "]");
	}

	private void endSession() {
		/*
		if (initialSessionTime == 0) // existem situacoes em que o sistema chama duas vezes o endSession 
			return;
		*/
		double sessionTime = System.currentTimeMillis() - initialSessionTime;
		logger.info("endSession=> " + sessionTime + "[" + this + "]");
		activityExperiencedDuration += sessionTime;
		attemptExperiencedDuration += sessionTime;
		
//		initialSessionTime = 0;
	}

	public void resume() {
		setSuspended(false);
		startSession();
	}

	public void endAttempt() {
		setActive(false); 
		
		//When an attempt on a tracked (Tracked equals True) activity ends, “write” objective 
		//... maps are honored.
		
		// An attempt begins when the activity is identified for delivery and ends while the LMS’s 
		//... sequencing implementation attempts to identify the next activity for delivery
		
		// segundo a seção 3.10. Objective Description, no final da tentativa deve atualizar
		// os objetivos globais
		
		/*
		If the cmi.exit is set to “normal”, “logout”,“time-out” or “” (empty 
				characterstring) then the SCOs learner attempt ends.  The SCOs Run-Time 
				Environment data model element values of the current learner session will NOT 
				be available if the SCO is relaunched.
		*/ 
		for (ProxyObjective obj:objectives) {
			obj.updateGlobalObjective();
		}

		endSession();
		
	}

	// See SeqConditionSet.evaluateCondition da implementacao de referencia
	public Resultado evaluate(RuleCondition ruleCondition) {
		LocalObjective objective = getObjective(ruleCondition.getReferencedObjective());
		
		if (objective == null)
			objective = getPrimaryObjective();
		
		Resultado resultado = Resultado.UNKNOWN;
		
		// See 3.4.2. Rule Conditions in SN Book
		switch (ruleCondition.getEnumCondition()) {
		
			case satisfied: ;
			
				if (objective.isProgressStatus())
					resultado =  objective.isSatisfiedStatus()? Resultado.TRUE:Resultado.FALSE;
				break;
			
			case objectiveStatusKnown: 
				resultado = objective.isProgressStatus()? Resultado.TRUE:Resultado.FALSE;
				break;
				
			case objectiveMeasureKnown: ;
				resultado = objective.isMeasureStatus()? Resultado.TRUE:Resultado.FALSE;
				break;
			
			case objectiveMeasureGreaterThan:
				if (objective.isMeasureStatus())
					resultado = objective.getNormalizedMeasure() > ruleCondition.getMeasureThreshold()? Resultado.TRUE:Resultado.FALSE;
				break;
				
			case objectiveMeasureLessThan: 
				if (objective.isMeasureStatus())
					resultado = objective.getNormalizedMeasure() < ruleCondition.getMeasureThreshold()? Resultado.TRUE:Resultado.FALSE;
				break;
			
			case activityProgressKnown: ;
				resultado = isProgressStatus() == true && isAttemptProgressStatus() == true ? Resultado.TRUE:Resultado.FALSE;
				break;
		
			case completed: 
				if (isAttemptProgressStatus())
					resultado =  isAttemptCompletionStatus() == true ? Resultado.TRUE:Resultado.FALSE;
				break;
				
			case attempted: ;
				if (isProgressStatus())
					resultado = getAttemptCount() > 0 ? Resultado.TRUE:Resultado.FALSE;
				break;
		
			case attemptLimitExceeded: ;
				if (isProgressStatus() == true)
					resultado =  isLimitConditionAttemptControl() &&
									getAttemptCount() >= getLimitConditionAttemptLimit()? Resultado.TRUE:Resultado.FALSE;
				break;
	
			case always: 
				resultado = Resultado.TRUE;
				break;
			
			// A tabela do item 3.4.2. Rule Conditions nao contém esses itens
			case outsideAvailableTimeRange:;
			case timeLimitExceeded:;
				
		}
		
		if (resultado != Resultado.UNKNOWN && ruleCondition.getEnumConditionOperator() == ConditionOperator.not) {
			resultado = resultado == Resultado.FALSE?Resultado.TRUE:Resultado.FALSE;
		}
		
		return resultado;
	}
	
	public boolean evaluate(RollupCondition rollupCondition) {
		
		Objective objective = getPrimaryObjective();
		boolean res = false;
		
		switch (rollupCondition.getEnumCondition()) {
			case satisfied :
				res = objective.isProgressStatus() && objective.isSatisfiedStatus();
				break;
				
			case objectiveStatusKnown : ;
				res = objective.isProgressStatus();
				break;
				
			case objectiveMeasureKnown : ;
				res = objective.isMeasureStatus();
				break;
				
			case completed : ;
				res = isAttemptProgressStatus() && isAttemptCompletionStatus();
				break;
				
			case activityProgressKnown : ;
				res = isProgressStatus() && isAttemptProgressStatus();
				break;
				
			case attempted : ;
				res = isAttemptProgressStatus() && getAttemptCount() > 0;
				break;
				
			case attemptLimitExceeded : ;
				res = isProgressStatus() && isLimitConditionAttemptControl() &&
					  getAttemptCount() >= getLimitConditionAttemptLimit();
				break;
			
			// Esses dois tipos de condições abaixo não estão na tabela do item 3.7.2 do SN Book!!
			case outsideAvailableTimeRange : ;
			case timeLimitExceeded : ;
		}
		
		if (rollupCondition.getEnumOperator() == ConditionOperator.not) {
			res = !res;
		}
		return res;
	}

	public boolean isAncestralFrom(LearningActivity activity) {
		
		LearningActivity p = activity.getParent();
		while (p != null) {
			if (p == this)
				return true;
			p = p.getParent();
		}
		
		return false;
	}

	/* Informacoes alimentadas e gerenciadas pelo próprio SCO durante a sua execucao */
	
	// cmi.comments_from_learner
	private List<Comment> commentsFromLearner = new ArrayList<Comment>();
	
	// cmi.success_status
	private SuccessStatus successStatus = SuccessStatus.unknown;
	
	private LessonStatus lessonStatus = LessonStatus.notAttempted;
	
	// cmi.progress_measure
	private double progressMeasure = -1;

	private Double scoreScaled;
	private Double scoreRaw, scoreMin, scoreMax;
	
	public List<Comment> getCommentsFromLearner() {
		return this.commentsFromLearner ;
	}

	public double getCompletionThreshold() {
		if (item.getClass().isAssignableFrom(Item20043rd.class) || item.getClass().isAssignableFrom(Item20044th.class)) {
			
			if (item.getClass().isAssignableFrom(Item20044th.class)) {
				if (((Item20044th)item).getObjectCompletionThreshold().isCompletedByMeasure() == false) {
					return -1;
				}
			}
			
			return item.getCompletionThreshold();
		} else {
			return -1;
		}
	}

	// Table 4.2.4.1a: GetValue() Evaluation of Completion Status
	public CompletionStatusValue getCompletionStatus() {
		double ct = this.getCompletionThreshold();
		double pm = this.getProgressMeasure();
		if (ct == -1 || pm == -1) {
			return completionStatusEvaluateByLMS();
		} else
			if (pm < ct)
				return CompletionStatusValue.incomplete;
			else
				return CompletionStatusValue.completed; // if (pm >= ct)
		
	}

	private CompletionStatusValue completionStatusEvaluateByLMS() {
		if (isAttemptProgressStatus() == false) {
			return CompletionStatusValue.unknown;
		} else {
			if (isAttemptCompletionStatus() == true) {
				return CompletionStatusValue.completed;
			} else {
				return CompletionStatusValue.incomplete;
			}
		}
	}

	public void setCompletionStatus(CompletionStatusValue completionStatus) {
		
		// ver Sequencing Impacts em 4.2.4 no RTE
		
		switch (completionStatus) {
			case completed:
				setAttemptProgressStatus(true);
				setAttemptCompletionStatus(true);
				break;

			case incomplete:;
				setAttemptProgressStatus(true);
				setAttemptCompletionStatus(false);
				break;

			case notAttempted:;
				setAttemptProgressStatus(true);
				setAttemptCompletionStatus(false);
				break;

			case unknown:
				setAttemptProgressStatus(false);
		}
		
	}

	
	public double getProgressMeasure() {
		return progressMeasure;
	}

	public void setProgressMeasure(double valor) {
		this.progressMeasure = valor;
	}

	public String getDataFromLMS() {
		return ((Item)this.item).getDataFromLMS();
	}

	public String getTimeLimitAction() {
		return ((Item)this.item).getTimeLimitAction();
	}

	public String getLimitConditionAbsoluteDurationLimitTimeInterval() {
		return ((Item)this.item).getImsssSequencing().getLimitConditions().getAttemptAbsoluteDurationLimit();
	}

	private List<Interaction> interactions = null;

	// TODO essa é uma informacao gerenciada pelo LMS. Por exemplo, um mural de recados do professor para alguma atividade.
	//      entao pensar mais tarde em fazer algum recurso no CELINE para que os professores possam adicionar esses comentarios 
	private List<Comment> commentsFromLMS = new ArrayList<Comment>();

	public List<Interaction> getInteractions() {
		if (interactions == null) {
			interactions = new ArrayList<Interaction>();
		}

		return interactions;
	}
	public void changeInteractionType(int n, String newType) {
		getInteractions().set(n, interactions.get(n).createInteraction(newType));
	}

	public void addInteraction(Interaction interaction) {
		getInteractions().add(interaction);
	}

	public int getInteractionsCount() {
		return getInteractions().size();
	}

	public void clearInteractions() {
		getInteractions().clear();	
	}

	// SCORM 1.2
	public LessonStatus getLessonStatus() {
		return lessonStatus;
	}

	public void setLessonStatus(LessonStatus value) {
		this.lessonStatus = value;
	}

	public SuccessStatus getSuccessStatus() {

		// Table 4.2.22.1a: GetValue() Evaluation for Success Status
		if (getScaledPassingScore() != null) {
			
			Double passingScore = getScaledPassingScore();
			Double score = getScoreScaled();
			
			if (score == null)
				successStatus = SuccessStatus.unknown;
			else
				if (score < passingScore) {
					setSuccessStatus(SuccessStatus.failed);
				} else {
					setSuccessStatus(SuccessStatus.passed);
				}
		} else {
			if (getPrimaryObjective().isProgressStatus()) {
				
				if (getPrimaryObjective().isSatisfiedStatus()) {
					setSuccessStatus(SuccessStatus.passed);
				} else {
					setSuccessStatus(SuccessStatus.failed);
				}
				
			} else {
				setSuccessStatus(SuccessStatus.unknown); 
			}
		}
		
		return successStatus;
	}

	public void setSuccessStatus(SuccessStatus successStatus) {
		// Sequencing Impacts
		/*
		If the SCO or LMS (through the above process) sets cmi.success_status, of 
		the SCO to “passed”, the Objective Progress Status for the primary 
		objective of the learning activity associated with the SCO shall be true, and 
		the Objective Satisfied Status for the primary objective of the learning
		activity associated with the SCO shall be true.
		
		If the SCO or LMS (through the above process) sets cmi.success_status, of
		the SCO to “failed”, the Objective Progress Status for the primary objectiv
		of the learning activity associated with the SCO shall be true, and the 
		Objective Satisfied Status for the primary objective of the learning activity 
		associated with the SCO shall be false. 
		*/

		if (successStatus == SuccessStatus.passed) {
			getPrimaryObjective().setProgressStatus(true);
			getPrimaryObjective().setSatisfiedStatus(true);
		} else {
			if (successStatus == SuccessStatus.failed) {
				getPrimaryObjective().setProgressStatus(true);
				getPrimaryObjective().setSatisfiedStatus(false);
				
			}
		}
		
		this.successStatus = successStatus;
	}

	public Double getScaledPassingScore() {
		LocalObjective primary = getPrimaryObjective();
		if (primary.isSatisfiedByMeasure()) {
			return primary.getReferencedObjective().getMinNormalizedMeasure();
		}
		
		return null;
	}

	public Double getScoreScaled() {
		if (isLeaf()) 
			return scoreScaled;
		
		return getPrimaryObjective().getNormalizedMeasure();
	}

	public void setScoreScaled(Double scoreScaled) {
		this.scoreScaled = scoreScaled;
	}

	public Double getScoreRaw() {
		return scoreRaw;
	}

	public void setScoreRaw(Double scoreRaw) {
		this.scoreRaw = scoreRaw;
		logger.info("scoreraw " + scoreRaw);
	}

	public Double getScoreMin() {
		return scoreMin;
	}

	public void setScoreMin(Double scoreMin) {
		this.scoreMin = scoreMin;
		logger.info("scoremin " + scoreMin);
	}

	public Double getScoreMax() {
		return scoreMax;
	}

	public void setScoreMax(Double scoreMax) {
		this.scoreMax = scoreMax;
		logger.info("scoremax " + scoreMax);
	}

	public List<Comment> getCommentsFromLMS() {
		return this.commentsFromLMS;
	}

	public void restartDataModel() {
		clearInteractions();
	}

}
