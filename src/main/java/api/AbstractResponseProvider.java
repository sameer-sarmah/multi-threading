package api;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;

public abstract class AbstractResponseProvider {
	protected final String countURL;
	protected final HttpClient httpClient;

	public AbstractResponseProvider(String countURL,HttpClient httpClient) {
		this.countURL = countURL;
		this.httpClient = httpClient;
	}
	
	protected String getResponse() throws IOException {
		HttpGet request = new HttpGet(countURL);
		HttpClientResponseHandler<String> responseHandler = (ClassicHttpResponse response) -> {
			InputStream inputStream = response.getEntity().getContent();
			String responseStr = IOUtils.toString(inputStream, Charset.defaultCharset());
			return responseStr;
		};
		return httpClient.execute(request,responseHandler);
	}

}
