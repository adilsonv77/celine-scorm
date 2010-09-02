package br.univali.celine.scorm.rteApi;

import br.univali.celine.lmsscorm.User;
import br.univali.celine.scorm.sn.model.ActivityTree;

public class ErrorManager {

	private int numError;
	private ActivityTree tree;
	private User user;
	private String courseFolder;
	
	public ErrorManager(ActivityTree tree, User user, String courseFolder) {
		this.tree = tree;
		this.user = user;
		this.courseFolder = courseFolder;
	}

	public String getCourseFolder() {
		return courseFolder;
	}

	public User getUser() {
		return user;
	}

	public ActivityTree getTree() {
		return tree;
	}

	public void attribError(int numError) {
		this.numError = numError;
	}

	public int getNumError() {
		return numError;
	}

	public void success() {
		this.numError = 0;
	}
	
	public static final int GeneralException = 101;
	public static final int GeneralInitializationFailure = 102;
	public static final int AlreadyInitialized = 103;
	public static final int ContentInstanceTerminated = 104;
	
	public static final int GeneralTerminationFailure = 111;
	public static final int TerminationBeforeInitialization = 112;
	public static final int TerminationAfterInitialization = 113;
	
	public static final int RetrieveDataBeforeInitialization = 122;

	public static final int StoreDataBeforInitialization = 132;

	public static final int CommitBeforeInitialization = 142;
	
	public static final int GeneralArgumentError = 201;

	public static final int GeneralGetFailure = 301;
	public static final int GeneralSetFailure = 351;
	public static final int GeneralCommitFailure = 391;

	public static final int UndefinedDataModelElement = 401;
	public static final int DataModelElementValueNotInitialized = 403;
	public static final int DataModelElementIsReadOnly = 404;
	public static final int DataModelElementIsWriteOnly = 405;
	public static final int DataModelElementTypeMismatch = 406;
	public static final int DataModelElementValueOutOfRange = 407;
	public static final int DataModelDependencyNotEstablished = 408;
	
}
