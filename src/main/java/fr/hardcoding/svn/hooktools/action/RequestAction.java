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
	/** The request URL to access to. */
	@ConfigurationParameter(isRequired = true)
	public String url;
	/** The request type to perform. */
	@ConfigurationParameter
	public String type;
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
	public boolean perform(AbstractHook hook) {
		try {
			// Check if there is data to append to URL
			if (this.type.equals("GET")&&this.data!=null)
				this.url += "?"+this.data;
			// Open URL connection
			URL requestUrl = new URL(this.url);
			URLConnection connection = requestUrl.openConnection();
			if (!(connection instanceof HttpURLConnection))
				return false;
			// Configure URL connection
			HttpURLConnection httpConnection = (HttpURLConnection) connection;
			httpConnection.setRequestMethod(this.type);
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
				} while (line!=null);
			}
		} catch (IOException exception) {
			HookTools.LOGGER.log(Level.WARNING, "Unable to perform the request to \""+this.url+"\".", exception);
		}
		// Continue the remaining rule actions
		return false;
	}
}
