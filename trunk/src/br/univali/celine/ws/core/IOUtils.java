package br.univali.celine.ws.core;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class IOUtils {

	public static int copy(final InputStream input, final OutputStream output) throws IOException {
    	
    	int t = 0;
    	while (true) {
    		int l = input.read();
    		if (l == -1)
    			break;
    		output.write(l);
    		t++;
    	}
    	return t;
    }
    

}
