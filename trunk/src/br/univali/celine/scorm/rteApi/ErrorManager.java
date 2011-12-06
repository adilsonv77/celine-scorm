package br.univali.celine.scorm.rteApi;

import br.univali.celine.lmsscorm.User;
import br.univali.celine.scorm.sn.model.ActivityTree;

public class ErrorManager {

	private int numError;
	private ActivityTree tree;
	private User user;
	private String courseFolder;
	private int version;
	
	public ErrorManager(ActivityTree tree, User user, String courseFolder, int version) {
		this.tree = tree;
		this.user = user;
		this.courseFolder = courseFolder;
		this.version = version;
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
		if (version == 12) {
			switch (numError) {
			case DataModelElementIsReadOnly   : this.numError = ElementIsReadOnly; break;
			case DataModelElementIsWriteOnly  : this.numError = ElementIsWriteOnly; break;
			case DataModelElementTypeMismatch : this.numError = IncorrectDataType; break;
			case GeneralArgumentError         : this.numError = InvalidArgumentError; break;
			default							  : this.numError = numError;
				
			}
			
		} else {
			this.numError = numError;
		}
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
	
	// 1.2
	private static final int InvalidArgumentError = 201;
	private static final int ElementCannotHaveChildren = 202;
	private static final int ElementNotAnArray = 203;
	
	private static final int NotInitialized = 301;
	
	private static final int NotImplementedError = 401;
	private static final int InvalidSetValue = 402;
	private static final int ElementIsReadOnly = 403;
	private static final int ElementIsWriteOnly = 404;
	private static final int IncorrectDataType = 405;

	public String getErrorString(int version, int code) {
		if (version == 12) {
			// 1.2
			
			switch (code) {
			case 0:                          return "No error";
			case GeneralException :          return "General exception";
			case InvalidArgumentError:       return "Invalid argument error";
			case ElementCannotHaveChildren:  return "Element cannot have children";
			case ElementNotAnArray:          return "Element not an array - cannot have count";
			case NotInitialized:             return "Not initialized";
			case NotImplementedError:        return "Not implemented error";
			case InvalidSetValue:            return "Invalid set value, element is a keyword";
			case ElementIsReadOnly:          return "Element is read only";
			case ElementIsWriteOnly:         return "Element is write only";
			case IncorrectDataType:          return "Incorrect data type";
			}
			
			return "";
		} else {
			// 2004
			return "";
		}
	}
	
	
}
