package br.univali.celine.scorm.sn.model.interaction;

import java.util.ArrayList;
import java.util.List;

/*
 * Essa é a classe usada quando nao existe um type para a interaction (o que é um erro
 * de quem produziu o SCO).
 * 
 * De acordo com o type da interação será criado um descendente dessa classe. 
 * 
 */
public abstract class Interaction {

	private String type;
	private String timeStamp;
	private Double weighting;
	private String learnerResponse;
	private String description;
	private String latency;
	private String result;
	protected List<String> correctResponses = new ArrayList<String>();
	private List<String> objectivesId = new ArrayList<String>();
	private String id;

	public Interaction(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Double getWeighting() {
		return weighting;
	}

	public void setWeighting(Double weighting) {
		this.weighting = weighting;
	}

	public String getLearnerResponse() {
		return learnerResponse;
	}

	
	public final void setLearnerResponse(String learnerResponse) throws Exception {
		
		testLearnerResponse(learnerResponse);
		
		this.learnerResponse = learnerResponse;
	}

	protected void testLearnerResponse(String learnerResponse) throws Exception {
		// a maioria dos interactions o learnerResponse é igual ao pattern
		
		testPattern(0, learnerResponse, false);
		
	}

	public String getLatency() {
		return latency;
	}

	public void setLatency(String latency) {
		this.latency = latency;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	final public Interaction createInteraction(String newType) {

		InteractionType it = InteractionType.valueOfEx(newType);
		Interaction newInteraction = null;
		switch (it) {
		case choice:
			newInteraction = new MultipleChoiceInteraction(id);
			break;

		case true_false:
			newInteraction = new TrueFalseInteraction(id);
			break;

		case fill_in:
			newInteraction = new FillInInteraction(id);
			break;

		case long_fill_in:
			newInteraction = new LongFillInInteraction(id);
			break;

		case likert:
			newInteraction = new LikertInteraction(id);
			break;

		case matching:
			newInteraction = new MatchingInteraction(id);
			break;

		case performance:
			newInteraction = new PerformanceInteraction(id);
			break;

		case sequencing:
			newInteraction = new SequencingInteraction(id);
			break;

		case numeric:
			newInteraction = new NumericInteraction(id);
			break;

		case other:
			newInteraction = new OtherInteraction(id);
			break;

		}
		newInteraction.setType(newType);
		newInteraction.setTimeStamp(timeStamp);
		newInteraction.setWeighting(weighting);
		newInteraction.setDescription(description);
		newInteraction.setLatency(latency);

		// trocou o tipo perde a resposta. isso tbem é um erro de quem produziu o SCO
		// newInteraction.setLearnerResponse(learnerResponse);

		return newInteraction;
	}

	public int getCorrectResponsesPatternCount() {
		return correctResponses.size();
	}

	public void setCorrectResponsesPattern(int index, String pattern) throws Exception {

		testPattern(index, pattern, true);
		
		if (index == correctResponses.size())
			this.correctResponses.add(pattern);
		else
			this.correctResponses.set(index, pattern);
	}

	protected abstract void testPattern(int index, String pattern, boolean correct_responses) throws Exception;
	
	public String getCorrectResponsesPattern(int index) throws Exception {
		testGetResponsesPattern(index);
		
		return this.correctResponses.get(index);
	}

	protected void testGetResponsesPattern(int index) throws Exception {	}

	@Override
	public String toString() {
		
		return id + " " + type + " " + learnerResponse;
	}

	public int getObjectivesCount() {
		return objectivesId.size();
	}

	public String getObjectiveId(int index) {
		return objectivesId.get(index);
	}

	public void setObjectiveId(int m, String objectiveId) {
		if (m == objectivesId.size()) {
			objectivesId.add(objectiveId);
		} else {
			objectivesId.set(m, objectiveId);
		}
	}

	public int containsObjectiveId(String objectiveId) {
		return objectivesId.indexOf(objectiveId);
	}
	
}
