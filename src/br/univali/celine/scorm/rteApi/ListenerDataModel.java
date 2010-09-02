package br.univali.celine.scorm.rteApi;

import br.univali.celine.lmsscorm.User;

public interface ListenerDataModel {

	boolean setValue(String key, String value, User user, String courseFolder) throws Exception;
	String getValue(String key, User user, String courseFolder) throws Exception;

}
