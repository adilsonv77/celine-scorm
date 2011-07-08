package br.univali.celine.scorm.sn.model;


public enum NavigationRequestType {
	
	Start, ResumeAll, Continue, Previous, Forward, Backward, Choice, Exit, ExitAll, Abandon, AbandonAll, SuspendAll,
	Jump; // Jump - 2004 4th

	public boolean isTerminationRequest() {
		
		return this == Exit || this == ExitAll || this ==  Abandon || this == AbandonAll || this == SuspendAll;
	}
	
}
