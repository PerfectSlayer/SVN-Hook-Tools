package fr.hardcoding.svn.hooktools.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;

import fr.hardcoding.svn.hooktools.HookTools;
import fr.hardcoding.svn.hooktools.configuration.ConfigurationParameter;
import fr.hardcoding.svn.hooktools.hook.AbstractHook;

/**
 * This class is an request action. It sends HTTP request.
 * 
 * @author Perfect Slayer (bruce.bujon@gmail.com)
 * 
 */
public class RequestAction extends AbstractAction {
	/** Serialization id. */
	private static final long serialVersionUID = 7034277181838051334L;
	/** The request URL to access to. */
	@ConfigurationParameter(isRequired = true)
	public String url;
	/** The request type to perform. */
	@ConfigurationParameter
	public String type;
	/** The headers to send. */
	@ConfigurationParameter
	public String headers;
	/** The request data to send (<code>null</code> if no data). */
	@ConfigurationParameter
	public String data;

	/**
	 * Constructor.
	 */
	public RequestAction() {
		// Set default request type
		this.type = "GET";
	}

	@Override
	public void perform(AbstractHook hook) {
		try {
			// Check if there is data to append to URL
			if (this.type.equals("GET")&&this.data!=null)
				this.url += "?"+this.data;
			// Open URL connection
			URL requestUrl = new URL(this.url);
			URLConnection connection = requestUrl.openConnection();
			if (!(connection instanceof HttpURLConnection))
				return;
			// Configure URL connection
			HttpURLConnection httpConnection = (HttpURLConnection) connection;
			httpConnection.setRequestMethod(this.type);
			// Check HTTP headers
			if (this.headers!=null) {
				// Send each header
				for (String header : this.headers.split("\\\\r\\\\n")) {
					// Split header into field and value
					int index = header.indexOf(": ");
					if (index==-1)
						continue;
					int length = header.length();
					if (index+2>=length)
						continue;
					String headerField = header.substring(0, index);
					String headerValue = header.substring(index+2, length);
					// Add HTTP header
					httpConnection.setRequestProperty(headerField, headerValue);
				}
			}
			// Check POST data
			if (this.type.equals("POST")&&this.data!=null) {
				// Send POST data
				connection.setDoOutput(true);
				try (OutputStream outputStream = httpConnection.getOutputStream(); OutputStreamWriter writer = new OutputStreamWriter(outputStream)) {
					writer.write(this.data);
				}
			}
			// Read request answer
			try (InputStream inputStream = httpConnection.getInputStream(); BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
				String line;
				do {
					line = reader.readLine();
					if (line!=null)
						HookTools.LOGGER.info(line);
				} while (line!=null);
			}
		} catch (IOException exception) {
			HookTools.LOGGER.log(Level.WARNING, "Unable to perform the request to \""+this.url+"\".", exception);
		}
	}
}