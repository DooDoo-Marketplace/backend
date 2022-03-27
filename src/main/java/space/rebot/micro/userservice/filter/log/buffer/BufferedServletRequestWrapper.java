package space.rebot.micro.userservice.filter.log.buffer;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class BufferedServletRequestWrapper extends HttpServletRequestWrapper {

    private byte[] buffer;

    public BufferedServletRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);

        try(InputStream is = request.getInputStream();
            ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            byte buff[] = new byte[1024];
            int read;
            while ((read = is.read(buff)) > 0) {
                baos.write(buff, 0, read);
            }
            this.buffer = baos.toByteArray();
        } catch (IOException e) {
            throw e;
        }
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        return new BufferedServletInputStream(this.buffer);
    }
}
