package br.univali.celine.scorm.model.cam;

import java.io.ByteArrayInputStream;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ContentPackageReaderFactory {

	private static String version;
	
	public static ContentPackageReader getContentPackageReader(String fileName)
			throws Exception {
		
		return processar(new InputSource(fileName));
	}

	public static ContentPackageReader getContentPackageReader(ByteArrayInputStream bytes) throws Exception {
		return processar(new InputSource(bytes));
	}

	private static ContentPackageReader processar(InputSource inputSource) throws Exception {
		
		version = "";
		SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
		try {
			parser.parse(
					inputSource,
					new DefaultHandler() {

						@Override
						public void startElement(String uri, String localName,
								String qName, Attributes attributes)
								throws SAXException {

							version = attributes.getValue("version");

							throw new SAXException("Parando");
						}
					});
		} catch (SAXException e) {
			if (!e.getMessage().equals("Parando")) {
				throw e;
			}
		}

		if (version.equals("1.1.1")) {
			return new br.univali.celine.scorm2004_4th.model.cam.ContentPackageReader20044th();
		} else {
			return new ContentPackageReader20043rd();
		}
	}

}
