package br.univali.celine.scorm.model.imsss;

import br.univali.celine.scorm.model.dataTypes.Duration;

public class LimitConditions {

	private int attemptLimit = 0; // not initialized
	private String attemptAbsoluteDurationLimit = null; 
	private Duration duracao = null; // not initialized
	
	public int getAttemptLimit() {
		return attemptLimit;
	}
	public void setAttemptLimit(int attemptLimit) {
		this.attemptLimit = attemptLimit;
	}
	
	public double getAttemptAbsoluteDurationLimitInSeconds() {
		if (duracao == null)
			return -1;
		
		return duracao.toSeconds();
	}
	
	public String getAttemptAbsoluteDurationLimit() {
		return attemptAbsoluteDurationLimit;
	}
	
	public void setAttemptAbsoluteDurationLimit(String attemptAbsoluteDurationLimit) {
		this.attemptAbsoluteDurationLimit = attemptAbsoluteDurationLimit;
		this.duracao = new Duration(attemptAbsoluteDurationLimit);
	}
	
	@Override
	public String toString() {
		if (attemptLimit == 0 && attemptAbsoluteDurationLimit == null)
			return "";
		
		String ret = "";
		if (attemptLimit > 0) {
			ret += "attemptLimit=\""+attemptLimit+"\"";
		}
		
		if (attemptAbsoluteDurationLimit != null) {
			ret += " attemptAbsoluteDurationLimit=\""+attemptAbsoluteDurationLimit+"\"";
		}
		
		return "<imsss:limitConditions "+ret+"/>\n";
	}
	public void assign(LimitConditions limitConditions) {
		// TODO Auto-generated method stub
		
	}
	
}
