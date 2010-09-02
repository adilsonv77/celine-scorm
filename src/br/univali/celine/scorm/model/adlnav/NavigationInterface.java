package br.univali.celine.scorm.model.adlnav;

import java.util.LinkedList;
import java.util.List;

public class NavigationInterface {

	private List<String> hideLMSUI = new LinkedList<String>();
	
	public void addHideLMSUI(String nomeLMSUI) {
		this.hideLMSUI.add(nomeLMSUI);
	}
	
	@Override
	public String toString() {
		String ret = "<adlnav:navigationInterface>\n";
		for (String botao:hideLMSUI) {
			ret += "<adlnav:hideLMSUI>" + botao + "</adlnav:hideLMSUI>\n";
		}
		return ret+"</adlnav:navigationInterface>\n";
	}
}
