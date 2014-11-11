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
package br.univali.celine.ws.core;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.namespace.QName;
import javax.xml.soap.AttachmentPart;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import sun.misc.BASE64Decoder;

public class HandlerAttachment implements SOAPHandler<SOAPMessageContext> {

	@Override
	public Set<QName> getHeaders() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void close(MessageContext arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean handleFault(SOAPMessageContext arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean handleMessage(SOAPMessageContext messageContext) {
		try {
			System.out.println("do handleMessage");
			SOAPMessage msg = messageContext.getMessage();
			SOAPHeader h = msg.getSOAPHeader();
			if (h != null) {
				//Iterator helem = h.getChildElements(new QName("http://core.ws.celine.univali.br/", "anexo", "h"));
				//SOAPElement anexo = (SOAPElement) helem.next();
				//SOAPElement dadosArq = (SOAPElement) anexo.getChildElements().next();
				//SOAPElement nomeArq = (SOAPElement) dadosArq.getChildElements(new QName("nomearq")).next();
				@SuppressWarnings("unchecked")
				Iterator<AttachmentPart> it = msg.getAttachments();
				while (it.hasNext()) {
					AttachmentPart att = it.next();
					String[] mh = att.getMimeHeader("content-transfer-encoding");
//					System.out.println("content-transfer-encoding : " + mh[0]);
					boolean isBinary = mh[0].equals("binary"); // senao é base64 !!!
					File zipFile = File.createTempFile("scorm_", ".zip");
					FileOutputStream f = new FileOutputStream(zipFile);
					if (isBinary) {
	//					System.out.println("binary " + zipFile.getAbsolutePath());
						f.write(att.getRawContentBytes());
					} else {
						File b64File = File.createTempFile("base64_", ".txt");
						FileOutputStream f64 = new FileOutputStream(b64File);
						f64.write(att.getRawContentBytes());
						f64.close();
		//				System.out.println("Base64 " + b64File.getAbsolutePath());
						
						BASE64Decoder bd = new BASE64Decoder();
			//			System.out.println("Convertido de Base64 " + zipFile.getAbsolutePath());
						f.write(bd.decodeBuffer(att.getRawContent()));
					}
					f.close();
					
					HttpSession session = ((HttpServletRequest)messageContext.get(MessageContext.SERVLET_REQUEST)).getSession();
					session.setAttribute("CelineService.filename_scorm_zip", zipFile.getAbsolutePath());
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return true;
	}
}
