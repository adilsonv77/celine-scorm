package br.univali.celine.lmsscorm;

import br.univali.celine.scorm.sn.model.interaction.Interaction;

public class TrackInteraction extends Interaction {

	private String activityId;
	private int index;

	public TrackInteraction(String activityId, String id) {
		super(id);
		this.activityId = activityId;
	}

	public String getActivityId() {
		return activityId;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getIndex() {
		return index;
	}

	public Interaction makeInteraction() throws Exception {
		
		Interaction interact = createInteraction(getType());
		interact.setLearnerResponse(getLearnerResponse());
		
		for (int i=0;i<getCorrectResponsesPatternCount();i++)
			interact.setCorrectResponsesPattern(i, getCorrectResponsesPattern(i));
		
		return interact;
	}

	@Override
	protected void testPattern(int index, String pattern) throws Exception {
	}
	
	
}
