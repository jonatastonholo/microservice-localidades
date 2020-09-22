package com.microservices.localidades.util;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

/**
 * @author Jônatas Tonholo
 * A File utils class with some utils methods for file operation
 */
public abstract class FileUtils {

    public static OutputStream fileDownload(HttpServletResponse response, String data) throws Exception{
        OutputStream outputStream = response.getOutputStream();
        OutputStreamWriter writer = new OutputStreamWriter(outputStream, StandardCharsets.ISO_8859_1);
        writer.write(data);
        writer.flush();
        writer.close();
        return outputStream;
    }

}
