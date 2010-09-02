package br.univali.celine.lms.core.commands;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class BinaryFileHandler {

	private static final String folder = "/br/univali/celine/lms/";
	private byte[] content;
	
	private Map<String, byte[]> imagens = new HashMap<String, byte[]>();

	public BinaryFileHandler(HttpServletRequest request, String fileName) throws IOException, URISyntaxException {

		byte[] img = imagens.get(fileName);
		if (img != null) {
			this.content = img;
			return;
		}
		
		InputStream inputStream = getClass().getResourceAsStream(folder + fileName);
		// como aqui ele retorna um ByteArrayInputStream, os deslocamentos pelo arquivo serão bem rápidos
		long len = inputStream.skip(Integer.MAX_VALUE);
		inputStream.reset();
		byte[] dados = new byte[(int)len];
		for (int index = 0; index < dados.length; index++)
        {
            dados[index] = (byte) inputStream.read();
        }

		imagens.put(fileName, dados);
        this.content = dados;
        
	}

	public byte[] getContent() { return this.content; }
	
	public static void main(String[] args) throws IOException, URISyntaxException {
		new BinaryFileHandler(null, "diffDoc.gif");
	}
}
