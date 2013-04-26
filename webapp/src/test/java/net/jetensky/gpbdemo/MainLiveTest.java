
package net.jetensky.gpbdemo;

import net.jetensky.gpbdemo.dto.StudentProto;
import net.jetensky.gpbdemo.gpbclient.GPBClient;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static junit.framework.Assert.assertNotNull;

/**
 * This type of test can be used to verify existing installation (it does not start own server in @BeforeClass)
 * This is handy if new releases are deployed to production (test must be non-destructive to production environment of course :))
 */
public class MainLiveTest {

    GPBClient client;


    @Before
    public void before() throws Exception {
        client = new GPBClient(Main.BASE_URI);
    }

    @Ignore("Use this test only if you want to test running server")
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

        MainTest.assertGpb(serviceStudentPB, "Pavel modified");

    }

    @Test
    public void dummyTest() {
        // This is to avoid No runnable tests failure error if testStudentBestEcho is ignored
    }

    private void assertJson(String serviceStudentJson) {
        System.out.println(serviceStudentJson);
        assertNotNull(serviceStudentJson);
    }
}
