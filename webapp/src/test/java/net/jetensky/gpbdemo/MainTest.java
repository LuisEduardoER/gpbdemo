
package net.jetensky.gpbdemo;

import com.sun.grizzly.http.SelectorThread;
import com.sun.jersey.core.header.MediaTypes;
import net.jetensky.gpbdemo.dto.StudentProto;
import net.jetensky.gpbdemo.gpbclient.GPBClient;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.ws.rs.core.MediaType;

import static junit.framework.Assert.*;


public class MainTest {

    private static SelectorThread threadSelector;

    static GPBClient client;


    @BeforeClass
    public static void beforeClass() throws Exception {
        threadSelector = Main.startServer();
        client = new GPBClient(Main.BASE_URI);
    }

    @AfterClass
    public static void afterClass() throws Exception {
        threadSelector.stopEndpoint();
    }

    /**
     * Test to see that the message "Hello world!" is sent in the response.
     */
    @Test
    public void testMyResource() {
        String responseMsg = client.getWebResource().path("myresource").get(String.class);
        assertEquals("Hello world!", responseMsg);
    }

    /**
     * Test if a WADL document is available at the relative path
     * "application.wadl".
     */
    @Test
    public void testApplicationWadl() {
        String serviceWadl = client.getWebResource().path("application.wadl").
                accept(MediaTypes.WADL).get(String.class);

        assertTrue(serviceWadl.length() > 0);
    }

    /**
     * Test if a WADL document is available at the relative path
     * "application.wadl".
     */
    @Test
    public void testStudentJson() {
        String serviceStudentJson = client.getWebResource().path("myresource/student").
                accept(MediaType.APPLICATION_JSON).get(String.class);
        assertJson(serviceStudentJson);
    }

    @Test
    public void testStudentGPB() {
        StudentProto.Student serviceStudentPB = client.getWebResource()
                .path("myresource/studentGPB")
                .type("application/x-protobuf")
                .get(StudentProto.Student.class);
        assertGpb(serviceStudentPB, "Pavel");
    }


    @Test
    public void testStudentGPBJson() {
        String serviceStudentJson = client.getWebResource()
                .path("myresource/studentGPBjson")
                .type("application/x-protobuf")
                .accept("application/json")
                .get(String.class);
        assertJson(serviceStudentJson);
    }

    @Test
    public void testStudentBest() {
        String serviceStudentJson = client.getWebResource()
                .path("myresource/studentBest")
                .type("application/x-protobuf")
                .accept("application/json")
                .get(String.class);
        assertJson(serviceStudentJson);

        StudentProto.Student serviceStudentPB = client.getWebResource()
                .path("myresource/studentBest")
                .type("application/x-protobuf")
                .accept("application/x-protobuf")
                .get(StudentProto.Student.class);
        assertGpb(serviceStudentPB, "Pavel");

    }

    @Test
    public void testStudentBestEcho() {

        StudentProto.Student toEcho = StudentProto.Student.newBuilder()
                .setObjectId(1L)
                .setFirstName("Pavel")
                .setSurName("Jetenský")
                .setFaculty("FEI")
                .build();

        String serviceStudentJson = client.getWebResource()
                .path("myresource/echo")
                .type("application/x-protobuf")
                .accept("application/json")
                .post(String.class, toEcho);
        assertJson(serviceStudentJson);

        StudentProto.Student serviceStudentPB = client.getWebResource()
                .path("myresource/echo")
                .type("application/x-protobuf")
                .accept("application/x-protobuf")
                .post(StudentProto.Student.class, toEcho);

        assertGpb(serviceStudentPB, "Pavel modified");

    }

    private void assertJson(String serviceStudentJson) {
        System.out.println(serviceStudentJson);
        assertNotNull(serviceStudentJson);
    }

    public static void assertGpb(StudentProto.Student serviceStudentPB, String expectedFirstName) {
        System.out.println(serviceStudentPB.toString());
        assertNotNull(serviceStudentPB);
        assertEquals(expectedFirstName, serviceStudentPB.getFirstName());
        assertEquals("Jetenský", serviceStudentPB.getSurName());
    }

}
