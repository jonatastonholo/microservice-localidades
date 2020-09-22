package com.microservices.localidades.util;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.List;

/**
 * @author Jônatas Tonholo
 * A class with some CSV operations
 */
public abstract class CSVUtils {

    /**
     * Get a HttpServletResponse of CSV as OutputStream to download
     * @param response
     * @param localidades
     * @return
     * @throws Exception
     */
    public static OutputStream getCsvResponse(HttpServletResponse response, List localidades) throws Exception{
        final String data = CSVUtils.listObjectToCsvStringFormat(localidades, ";", true);
        return FileUtils.fileDownload(response, data);
    }

    /**
     * Convert a received list of object to a CSV string format
     * @param list
     * @param delimiter
     * @param firstAsHeader
     * @return CSV string formate
     */
    private static String listObjectToCsvStringFormat(List<Object> list, String delimiter, boolean firstAsHeader) {
        try {
            StringBuilder csv = new StringBuilder();
            // Validate the fields
            if(list == null) throw new NullPointerException("The list Can't be null");
            if(delimiter == null || delimiter.equals("")) delimiter = ";";


            // header
            if(firstAsHeader) {
                csv.append(getCsvRow(list.get(0), delimiter, true));
                csv.append(System.lineSeparator());
            }
            // body
            for(Object o : list) {
                csv.append(getCsvRow(o,delimiter,false));
                csv.append(System.lineSeparator());
            }
            return csv.toString();
        }
        catch (Exception e) {
            return "";
        }
    }

    /**
     * Receive a object and convert it to a CSV Line
     * @param object
     * @param delimiter
     * @param isHeader
     * @return CSV Line as string
     */
    private static String getCsvRow(Object object, String delimiter, boolean isHeader){
        StringBuilder csvLine = new StringBuilder();
        try {
            for (Field field : object.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                csvLine
                    .append(isHeader ? field.getName() : field.get(object).toString())
                    .append(delimiter);
            }
            return csvLine.toString();
        }
        catch (Exception e) {
            return "";
        }
    }
}
