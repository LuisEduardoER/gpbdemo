package net.jetensky.gpbdemo.service.provider;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import com.google.protobuf.Message;

    @Provider
    @Produces("application/x-protobuf")
    public class ProtobufMessageBodyWriter implements MessageBodyWriter<Message> {

	public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		return Message.class.isAssignableFrom(type);
	}

	public long getSize(Message message, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		return message.getSerializedSize();
	}

	public void writeTo(Message message, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) throws IOException, WebApplicationException {
		message.writeTo(entityStream);
	}

}
