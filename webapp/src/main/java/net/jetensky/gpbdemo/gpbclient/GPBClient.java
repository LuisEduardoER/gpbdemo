package net.jetensky.gpbdemo.gpbclient;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import net.jetensky.gpbdemo.service.provider.ProtobufMessageBodyReader;
import net.jetensky.gpbdemo.service.provider.ProtobufMessageBodyWriter;

import java.net.URI;

/**
 * This client can access GPB web service deployed on some server. Server URL is passed to constructor.<br/>
 *
 * Client has two usages:<br/>
 * 1) When developer wants to send JSON instead of GPB message to existing service,
 *    TestFormResource calls web service using this client<br/>
 *
 * 2) Client is called from unit tests and integration tests to verify proper function of web services<br/>
 *
 */
public class GPBClient {

    public WebResource webResource;

    public GPBClient(URI baseUri) {
        ClientConfig clientConfiguration = new DefaultClientConfig();
        clientConfiguration.getClasses().add(ProtobufMessageBodyReader.class);
        clientConfiguration.getClasses().add(ProtobufMessageBodyWriter.class);

        Client c = Client.create(clientConfiguration);
        webResource = c.resource(baseUri);
    }

    public WebResource getWebResource() {
        return webResource;
    }
}
