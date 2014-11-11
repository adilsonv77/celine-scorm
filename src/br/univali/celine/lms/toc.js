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
var browserVersion = 0;
var elemAtivo = null;

function getWindowMasterParent() {

	var win = window;
	while (win != null && win.parent != win) {
		win = win.parent;
	}
	
	return win;

}

function atualizarTOC(documento, toc) {

  switch(navigator.family)
  {
    case 'ie4':
      browserVersion = 1; //Simply means IE > 3.x
      break;
    case 'opera':
      browserVersion = (navigator.version > 6 ? 1 : 0); //opera7 has a good DOM
      break;
    case 'nn4':
      browserVersion = 2; //NS4.x 
      break;
    case 'gecko':
      browserVersion = 3; //NS6.x
      break;
    case 'safari':
      browserVersion = 1; //Safari Beta 3 seems to behave like IE in spite of being based on Konkeror
      break;
	default:
      browserVersion = 0; //other, possibly without DHTML  
      break;
  }
	var api = getWindowMasterParent().API_1484_11;
	if (api) {

		if (api.NeedsRecreateTree()) { // if the system remakes the tree with the intelligent core 
		  createTOC(documento, toc);
		}
	}
	
	elemAtivo = null;
	iterarItens(documento, toc.children);
	
	return elemAtivo;

}

function iterarItens(documento, elems) {

	for (var i=0; i<elems.length; i++) {
	    var chavePesquisa = "itemTextLink"+elems[i].idLearningActivity;
		var node = documento.getElementById(chavePesquisa);
		if (node != null) {
			var nodeA = node;
			node.style.color = "";
			node.style.fontWeight = "";
			/*
			 * preciso ver uma forma de deixar somente texto, tirando o link
			 * 
			if (elems[i].enabled == false) 
				node.style.color = "red"; 
			else {
			*/
				node = documento.getElementById("item"+elems[i].idLearningActivity);
				if (browserVersion == 1 || browserVersion == 3 || browserVersion == 0) {
					if (elems[i].visible == false || elems[i].enabled == false) 
						node.style.display = "none";
					else
						node.style.display = "block";
				} else {
					if (elems[i].visible == false || elems[i].enabled == false) {
						node.visibility = "hidden" 
					} else {
						node.visibility = "show";
					}
				// }
				 
			}
			if (elems[i].active) {
				nodeA.style.fontWeight = "bold";
				elemAtivo = nodeA;
			}
		}
		iterarItens(documento, elems[i].children);
	}
}


function createTOC(documento, toc) {
	
	var foldersTree = gFld(toc.name, null, 'treeID');
	foldersTree.treeID = "curso";
	window.parent.foldersTree = foldersTree;
	
	var niveis = new Array();
	niveis.push(foldersTree);
	
	var nivelBase=foldersTree;
	
	iterateAddNode(niveis, toc.children);
	
	initializeDocument(foldersTree, toc);
	return atualizarTOC(documento, toc);
}


function iterateAddNode(niveis, elems) {

	for (var i=0; i<elems.length; i++) {
		var id = elems[i].idLearningActivity;
		if (elems[i].children.length == 0) {
			insDoc(niveis[niveis.length-1], gLnk("T", elems[i].name, "javascript:loadPage('"+id+"');", id));
			
		} else {
		
			nivelBase = insFld(niveis[niveis.length-1], gFld(elems[i].name, "javascript:loadPage('"+id+"');", id));
			niveis.push(nivelBase);
			
		}
		
		iterateAddNode(niveis, elems[i].children);
		
		if (elems[i].children.length > 0) {
			niveis.pop();
		}
	}

}

