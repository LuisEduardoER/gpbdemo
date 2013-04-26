
package net.jetensky.gpbdemo.service;

import net.jetensky.gpbdemo.dto.StudentPojo;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import static net.jetensky.gpbdemo.dto.StudentProto.Student;

// The Java class will be hosted at the URI path "/myresource"
@Path("/myresource")
public class MyResource {

    @GET
    @Produces("text/plain")
    public String getIt() {
        return "Hello world!";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Path("/student")
    public StudentPojo getStudent() {
        return new StudentPojo(1L,"Pavel","Jetenský","FEI");
    }

    @GET
    @Produces("application/x-protobuf")
    @Path("/studentGPB")
    public Student getStudentGPB() {
        return Student.newBuilder()
                .setObjectId(1L)
                .setFirstName("Pavel")
                .setSurName("Jetenský")
                .setFaculty("FEI")
                .build();
    }


   /*
    This we we could convert GPB message to JSON for testing purposes manually.
    However, better way is to use ProtobufToJsonMessageBodyWriter
    (in package net.jetensky.gpbdemo.service.provider)

    @GET
    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Path("/studentGPBjson")
    public String getStudentGPBAsJSON() {
        Student studentGPB = getStudentGPB();
        return JsonFormat.printToString(studentGPB);
    }*/

    /**
     * Endpoint output is converted by ProtobufToJsonMessageBodyWriter to JSON
     * @return
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Path("/studentGPBjson")
    public Student getStudentGPBAsJSON2() {
        return getStudentGPB();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8" + "," + "application/x-protobuf")
    @Path("/studentBest")
    public Student getStudentBest() {
        return Student.newBuilder()
                .setObjectId(1L)
                .setFirstName("Pavel")
                .setSurName("Jetenský")
                .setFaculty("FEI")
                .build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON + ";charset=UTF-8" + "," + "application/x-protobuf")
    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8" + "," + "application/x-protobuf")
    @Path("/echo")
    public Student echo(Student student) {
        // Lets modify first name
        return student.toBuilder()
                .setFirstName(student.getFirstName() + " modified")
            .build();

    }
}
