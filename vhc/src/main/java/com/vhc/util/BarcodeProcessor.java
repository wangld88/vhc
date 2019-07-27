package com.vhc.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.apache.commons.codec.binary.Base64;
import org.krysalis.barcode4j.impl.code39.Code39Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.krysalis.barcode4j.tools.UnitConv;


/**
 *
 *
 * @author J
 *
 */
public class BarcodeProcessor {

	public String process(String barcode) {

		String imageData = "";

	    try {
	        //Create the barcode bean
	        Code39Bean bean = new Code39Bean();

	        final int dpi = 150;

	        //Configure the barcode generator
	        bean.setModuleWidth(UnitConv.in2mm(1.0f / dpi)); //makes the narrow bar
	                                                         //width exactly one pixel
	        bean.setWideFactor(3);
	        bean.doQuietZone(false);

	        //Open output file
	        String filePath = "/var/lib/tomcat8/files/"; //"D:\\var\\lib\\tomcat8\\files\\"; //
	        File outputFile = new File(filePath + "barcode.jpg");
	        OutputStream out = new FileOutputStream(outputFile);
	        try {
	            //Set up the canvas provider for monochrome JPEG output
	            BitmapCanvasProvider canvas = new BitmapCanvasProvider(
	                    out, "image/jpeg", dpi, BufferedImage.TYPE_BYTE_BINARY, false, 0);

	            //Generate the barcode
	            bean.generateBarcode(canvas, barcode);

	            //Signal end of generation
	            canvas.finish();

	            imageData = encodeFileToBase64Binary(outputFile);

	        } finally {
	            out.close();
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return imageData;

	}

	private static String encodeFileToBase64Binary(File file) throws Exception{
        FileInputStream fileInputStreamReader = new FileInputStream(file);
        byte[] bytes = new byte[(int)file.length()];
        fileInputStreamReader.read(bytes);
        fileInputStreamReader.close();

        return new String(Base64.encodeBase64(bytes), "UTF-8");
    }
}
