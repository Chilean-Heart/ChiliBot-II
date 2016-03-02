package com.team2576.lib;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import com.team2576.lib.util.ChiliConstants;

import fi.iki.elonen.NanoHTTPD;
import fi.iki.elonen.NanoHTTPD.Response.Status;
import fi.iki.elonen.ServerRunner;

/**
 * TODO
 * 
 * Implement HTTP server to allow remote logging. Include graphs and remote access 
 * to filesystem (remote auto script uploading).
 * 
 * 
 * @author To be determined
 *
 */

public class ChiliHTTPServer extends NanoHTTPD implements ChiliServers {
	
	public static final String
	    MIME_PLAINTEXT = "text/plain",
	    MIME_HTML = "text/html",
	    MIME_JS = "application/javascript",
	    MIME_CSS = "text/css",
	    MIME_PNG = "image/png",
	    MIME_DEFAULT_BINARY = "application/octet-stream",
	    MIME_ICO = "image/x-icon",
	    MIME_SVG = "image/svg+xml",
	    MIME_JSON = "application/json",
	    MIME_XML = "text/xml";
	
	Status HTTP_OK = Status.OK;
	private final String index = "/index.html";
	private final String path = "/webUI";
	
	public ChiliHTTPServer() throws IOException {
		super(ChiliConstants.webUIPort);
	}
	
	@Override
	public Response serve(IHTTPSession session) {
		
		// Declare auxiliary variables
		String currentURI;
		InputStream mbuffer = null;
		
		// Get the actual URI requested
		currentURI = session.getUri();
		
		// Get URL parameters from GET request
		@SuppressWarnings("unused")
		Map<String, List<String>> decodedQuerPar = decodeParameters(session.getQueryParameterString());
		
		// Print the URI and decodedQuerPar for debugging purposes
		//System.out.println(currentURI);
		//System.out.println(decodedQuerPar.toString());
		
		/*
		 * This section of code was an attempt to optimize lines and uses of streams.
		 * It hangs while trying to load the index.html file.
		 * An attempt to use the method commented below fails to load anything besided
		 * the index.html file.
		 */
		
//		// Try obtaining the data to send and return appropiate request
//		if(currentURI != null) {
//			if (currentURI.equals("/")) {
//				try (FileInputStream fs = new FileInputStream(new File("resources/index.html"))) {
//					return new NanoHTTPD.Response(this.HTTP_OK, MIME_HTML, fs);
//				} catch (IOException ioe) {
//					System.err.println("Read error: " + ioe);
//				}
//			} else {
//				try (FileInputStream fs = new FileInputStream(new File("resources/index.html"))) {
//					if (currentURI.contains(".js")) {
//						return new NanoHTTPD.Response(this.HTTP_OK, MIME_JS, fs);
//					} else if (currentURI.contains(".css")) {
//						return new NanoHTTPD.Response(this.HTTP_OK, MIME_CSS, fs);
//					} else if (currentURI.contains(".png")) {
//						return new NanoHTTPD.Response(this.HTTP_OK, MIME_PNG, fs);
//					}
//				} catch (IOException ioe) {
//					System.err.println("Read error: " + ioe);
//				}
//			}
//		}
		
		/*
		 * This was the first attempt at loading the site. It did not provide any files
		 * besides index.html, due to the disregard of any URI besided the initial "/".
		 */
		
//		try {
//			html = this.parser.parse();
//		} catch (IOException ioe) {
//			System.err.println("Read error: " + ioe);
//			return newFixedLengthResponse("<html><body>Error</body></html>");
//		}
//		return newFixedLengthResponse(html);
		
		/*
		 * This code functions perfectly, however it does not seem to close the
		 * stream when finished. This has not been proved, but due to the lack of
		 * try-with-resources loops and no explicit calls to .close(), it can be 
		 * assumed that there may be a possibility for a memory leak or the files
		 * may be impossible to open/copy while the code is still running.
		 * 
		 * TODO: 
		 *  - Test if the stream not being closed affects the possibility of modifying
		 *    the files
		 *  - Find a way to close the stream if necessary
		 */
		
		// Try obtaining the data to send and return appropiate request
		try {
			if (currentURI != null){
				
				// URI requesting a javascript file
				if (currentURI.contains(".js")) {
					mbuffer = getClass().getResourceAsStream(path+currentURI);
					return new NanoHTTPD.Response(this.HTTP_OK, MIME_JS, mbuffer);
				} 
				
				// URI requesting a css file
				else if(currentURI.contains(".css")){
					mbuffer = getClass().getResourceAsStream(path+currentURI);
                    return new NanoHTTPD.Response(HTTP_OK, MIME_CSS, mbuffer);
				} 
				
				// URI requesting a png file
				else if(currentURI.contains(".png")){
					mbuffer = getClass().getResourceAsStream(path+currentURI);
                    return new NanoHTTPD.Response(HTTP_OK, MIME_PNG, mbuffer);
				}
				
				// URI requesting an ico file
				else if(currentURI.contains(".ico")){
					mbuffer = getClass().getResourceAsStream(path+currentURI);
                    return new NanoHTTPD.Response(HTTP_OK, MIME_ICO, mbuffer);
				}
				
				// URI requesting an svg file
				else if(currentURI.contains(".svg")){
					mbuffer = getClass().getResourceAsStream(path+currentURI);
                    return new NanoHTTPD.Response(HTTP_OK, MIME_SVG, mbuffer);
				}
				
				// URI requesting an json file
				else if(currentURI.contains(".json")){
					mbuffer = getClass().getResourceAsStream(path+currentURI);
                    return new NanoHTTPD.Response(HTTP_OK, MIME_JSON, mbuffer);
				}
				
				// URI requesting a index.html
				else {
					//System.out.println("Delivering "+this.index+"!");					
					mbuffer = getClass().getResourceAsStream(path+this.index);
					return new NanoHTTPD.Response(HTTP_OK, MIME_HTML, mbuffer);

				}
			}
			
		} catch (NullPointerException npe) {
			System.err.println("Read error: " + npe);
			InputStream is = new ByteArrayInputStream("<html><body>Error</body></html>".getBytes());
			return new NanoHTTPD.Response(HTTP_OK, MIME_HTML, is);
			/*
			 * TODO: Test if the above code returns an error page correctly.
			 * The string to stream conversion has not been tested.
			 */
			//return newFixedLengthResponse("<html><body>Error</body></html>");
		}
		
		/*
		 * This section of code attemps to close the mbuffer stream, but 
		 * in doing so, it does not allow loading any resources. The server 
		 * hangs whilst loading index.html and does not deliver anything
		 * to the client.
		 * 
		 * The resason for this is that finally executes before the return in
		 * the try loop, so the mbuffer object being returned is sent off as
		 * a null object.
		 * 
		 * TODO: FIX THAT ASAP. THE STREAM HAS TO BE CLOSED.
		 */
		
//		} finally {
//			if (mbuffer != null) {
//				try {
//					mbuffer.close();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//		}
		
		return null;
	}

	@Override
	public boolean load() {
		ServerRunner.run(ChiliHTTPServer.class);
		try {
			new ChiliHTTPServer();
		} catch (IOException ioe) {
			System.err.println("Server init error: " + ioe);
		}
		return false;
	}

}
