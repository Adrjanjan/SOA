package pl.edu.agh.soa.protobuf;

import lombok.SneakyThrows;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

@Provider
@Produces("application/protobuf")
@Consumes("application/protobuf")
public class CarriagesMessageWriter implements MessageBodyWriter, MessageBodyReader {

    @Override
    public boolean isReadable(Class type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return isCarriagesAssignableFrom(type) || isRailwayCarriagesAssignableFrom(type);
    }

    @Override
    public Object readFrom(Class type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap httpHeaders, InputStream entityStream)
            throws IOException, WebApplicationException {
        if ( isCarriagesAssignableFrom(type) ) {
            return CarriagesProto.Carriages.parseFrom(entityStream);
        } else if ( isRailwayCarriagesAssignableFrom(type) ) {
            return CarriagesProto.RailwayCarriage.parseFrom(entityStream);
        }
        throw new BadRequestException("Class " + type + " can't be deserialized");
    }

    @Override
    public boolean isWriteable(Class type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return isCarriagesAssignableFrom(type) || isRailwayCarriagesAssignableFrom(type);
    }

    @Override
    public long getSize(Object o, Class type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return -1;
    }

    @SneakyThrows
    @Override
    public void writeTo(Object o, Class type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap httpHeaders,
            OutputStream entityStream) throws IOException, WebApplicationException {
        if ( o instanceof CarriagesProto.Carriages ) {
            entityStream.write(((CarriagesProto.Carriages) o).toByteArray());
        } else if ( o instanceof CarriagesProto.RailwayCarriage ) {
            entityStream.write(((CarriagesProto.RailwayCarriage) o).toByteArray());
        }
    }

    private boolean isRailwayCarriagesAssignableFrom(Class type) {
        return CarriagesProto.RailwayCarriage.class.isAssignableFrom(type);
    }

    private boolean isCarriagesAssignableFrom(Class type) {
        return CarriagesProto.Carriages.class.isAssignableFrom(type);
    }
}