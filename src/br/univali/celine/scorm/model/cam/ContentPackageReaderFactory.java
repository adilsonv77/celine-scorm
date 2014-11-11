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
package br.univali.celine.scorm.model.cam;

import java.io.ByteArrayInputStream;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import br.univali.celine.scorm1_2.model.cam.ContentPackageReader12;

public class ContentPackageReaderFactory {

	public static ContentPackageReader getContentPackageReader(String fileName)
			throws Exception {
		
		return processar(new InputSource(fileName));
	}

	public static ContentPackageReader getContentPackageReader(ByteArrayInputStream bytes) throws Exception {
		return processar(new InputSource(bytes));
	}

	private static ContentPackageReader processar(InputSource inputSource) throws Exception {
		final Object[] data = new Object[2];
		data[0] = false; // flag
		data[1] = "";    // version
		
		SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
		try {
			parser.parse(
					inputSource,
					new DefaultHandler() {

						@Override
						public void characters(char[] ch, int start, int length)
								throws SAXException {
							if ((Boolean)data[0]) {
								data[1] = new String(ch, start, length);
							}
						}
						
						@Override
						public void startElement(String uri, String localName,
								String qName, Attributes attributes)
								throws SAXException {

							if (qName.equals("schemaversion")) {
								data[0] = true;
							}

						}
						
						@Override
						public void endElement(String uri, String localName,
								String qName) throws SAXException {
							if ((Boolean)data[0])
								throw new SAXException("Parando");
						}
					});
		} catch (SAXException e) {
			if (!e.getMessage().equals("Parando")) {
				throw e;
			}
		} 
		
		String version = (String)data[1];
		
		// TODO needs make more sophisticated
		if (version.equals("1.2"))
			return new ContentPackageReader12();
		else
			if (version.equals("CAM 1.3") || version.equals("2004 3rd Edition")) {
				return new ContentPackageReader20043rd();
			} else { // SCORM 2004 4th Edition
				return new br.univali.celine.scorm2004_4th.model.cam.ContentPackageReader20044th();
		}
	}

}
