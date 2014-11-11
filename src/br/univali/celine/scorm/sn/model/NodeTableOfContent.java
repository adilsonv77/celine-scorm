/**
 * CELINE SCORM
 *
 * Copyright 2014 Adilson Vahldick.
 * https://celine-scorm.googlecode.com/
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package br.univali.celine.scorm.sn.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Nó da árvore visivel para o usuário. Se o nó está aqui é porque está visível.
 * Mas pode estar desabilitado.
 * 
 * @author Adilson Vahldick
 *
 */
public class NodeTableOfContent {

	private List<NodeTableOfContent> children = new ArrayList<NodeTableOfContent>();
	private boolean active = false;
	private String idLearningActivity;
	
	private boolean enabled;
	private boolean visible;
	private String name;
	
	public NodeTableOfContent(String idLearningActivity, String name, boolean enabled, boolean visible) {
		this.idLearningActivity = idLearningActivity;
		this.name = name;
		this.enabled = enabled;
		this.visible = visible;
	}
	
	public List<NodeTableOfContent> getChildren() {
		return children;
	}
	
	public boolean isEnabled() {
		return enabled;
	}
	
	public boolean isVisible() {
		return visible;
	}
	public void setVisible(boolean visible) {
		mostrarFilhos(this, visible);
	}
	private void mostrarFilhos(NodeTableOfContent nodeTableOfContent, boolean visible) {
		nodeTableOfContent.visible = visible;
		for (NodeTableOfContent child:nodeTableOfContent.children)
			mostrarFilhos(child, visible);
		
	}
	public void addChild(NodeTableOfContent node) {
		children.add(node);
	}
	@Override
	public String toString() {
		return getName() + " " + this.enabled;
	}
	public String getName() {
		return this.name;
	}
	public void setEnabled(boolean enabled) {
		habilitarFilhos(this, enabled);
	}
	private void habilitarFilhos(NodeTableOfContent nodeTableOfContent, boolean enabled) {
		 
		nodeTableOfContent.enabled = enabled;
		for (NodeTableOfContent child:nodeTableOfContent.children)
			habilitarFilhos(child, enabled);
		
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}

	public String getIdLearningActivity() {
		return idLearningActivity;
	}
	
}
