package br.univali.celine.lms.core;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Command {

	String executar(HttpServletRequest request, HttpServletResponse response) throws Exception;

}
