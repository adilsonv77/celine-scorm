package br.univali.celine.scorm.sn.model;

public enum LessonStatus {
	notAttempted {
		@Override
		public String toString() {
			return "not attempted";
		}
	}, passed, completed, failed, incomplete, browsed
}
