
package net.jetensky.gpbdemo.service;

import com.google.protobuf.Message;
import com.googlecode.protobuf.format.JsonFormat;
import net.jetensky.gpbdemo.gpbclient.GPBClient;

import javax.ws.rs.*;
import java.net.URI;

import static net.jetensky.gpbdemo.dto.StudentProto.Student;


@Path("/")
// The Java class will be hosted at the URI path "/myresource"
public class IndexResource {

    @GET
    @Produces("text/html;charset=UTF-8")
    @Path("/")
    public String index() {
        return convertStreamToString(this.getClass().getResourceAsStream("/index.html"));
    }

    public static String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

}
