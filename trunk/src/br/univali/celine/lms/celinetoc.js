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
var frameHidden = false;
var mainFrame = null;
var firstTime = true;

function loadPage(page) {
	getAPI().loadTOC = false;
	if (getAPI().Terminated() == false) {
		storeDataValue( 'adl.nav.request', '{target='+page+'}choice' );
		window.parent.frames[2].location = "lms?action=file&name=nothing.htm";
	} else {
		getAPI().ProcessRequest(page);
		window.parent.frames[2].location = "lms?action=navrequest";
	}
}

function initializeCelineTOC() {
	getWindowMasterParent().loadPage = loadPage;
	getWindowMasterParent().storeDataValue = storeDataValue;
	getWindowMasterParent().terminateCommunication = terminateCommunication;
	
	// configuracoes usadas no ftiens4.js
	USETEXTLINKS=1;STARTALLOPEN=1;HIGHLIGHT=0;PRESERVESTATE=1;USEFRAMES=0;
	USEICONS = 0;BUILDALL = 1;PRESERVESTATE = 0;
	ICONPATH="lms?action=file&name=";
	mainFrame = parent.document.getElementById('courseFrameset');
	
	var elemAtivo = createTOC(window.parent.frames[1].document, getAPI().GetTOC());
	BUILDALL = 0;
	
	if (elemAtivo != null) {
		var totalScroll = 0;
		while (elemAtivo != null) {
			if (elemAtivo.tagName == 'BODY') 
				break;
			totalScroll = totalScroll + elemAtivo.scrollHeight;
			elemAtivo = elemAtivo.parentNode;
		}
		window.scroll(0, totalScroll);
	}
		
}	
		
function showFrame() {
	var link = document.getElementById('linkTree');
	var linkImage = document.getElementById('show_hide_tree_icon');
	if (frameHidden) {		
		mainFrame.setAttribute('cols', '0, 250, *', 0);
		frameHidden = false;
		link.href = 'javascript:hideFrame();';
		linkImage.src='lms?action=file&name=hide_tree.png';
		linkImage.title='Hide Activity Tree';		
	}
}		

function hideFrame() {
	var link = document.getElementById('linkTree');	
	var linkImage = document.getElementById('show_hide_tree_icon');		
	if (!frameHidden) {
		mainFrame.setAttribute('cols', '0, 50, *', 0);
		frameHidden = true;
		var domDiv = document.getElementById('domRoot');
		domDiv.innerHTML = '';
		link.href='javascript:showFrame();';
		linkImage.src='lms?action=file&name=show_tree.png';		
		linkImage.title='Show Activity Tree';
	}
}
