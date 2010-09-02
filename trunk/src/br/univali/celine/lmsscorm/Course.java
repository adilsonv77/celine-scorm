package br.univali.celine.lmsscorm;

public interface Course {

	String getFolderName();

	String getTitle();

	String getId();

	boolean isRemoved();

	boolean isDerived();

}
