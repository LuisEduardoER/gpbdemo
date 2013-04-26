
package net.jetensky.gpbdemo.service;

import com.google.protobuf.Message;
import com.googlecode.protobuf.format.JsonFormat;
import net.jetensky.gpbdemo.gpbclient.GPBClient;

import javax.ws.rs.*;

import java.net.URI;

import static net.jetensky.gpbdemo.dto.StudentProto.Student;
import static net.jetensky.gpbdemo.service.IndexResource.convertStreamToString;


@Path("/test")
// The Java class will be hosted at the URI path "/myresource"
public class TestFormResource {

    GPBClient client;

    public TestFormResource() {
        this.client = new GPBClient(URI.create("http://localhost:9998"));
    }

    @GET
    @Produces("text/html;charset=UTF-8")
    public String testForm() {
        return convertStreamToString(this.getClass().getResourceAsStream("/testForm.html"));
    }

    @POST
    @Produces("application/json;charset=UTF-8")
    public String processTestForm(@FormParam("json") String json) {
        String student = null;
        Message.Builder message = Student.newBuilder();
        try {
            // We will recreate GPB message
            JsonFormat.merge(json, message);

            // Lets call the echo endpoint
            student = client.getWebResource()
                    .path("myresource/echo")
                    .type("application/x-protobuf")
                    .accept("application/json")
                    .post(String.class, message.build());
        } catch (JsonFormat.ParseException e) {
            throw new IllegalArgumentException("Cannot parse JSON string:" + e.getMessage(),e);
        }
        return student;
    }


}
