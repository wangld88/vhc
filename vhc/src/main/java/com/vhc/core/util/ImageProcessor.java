package com.vhc.core.util;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;
import java.sql.Blob;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.web.multipart.MultipartFile;


public class ImageProcessor {

	private int image_width;
	private int image_height;

	// Maximum size of the image file
	private Integer IMAGE_MAX_SIZE = 2097152;

	private Set<String> allowedImageExtensions;

	// return message
	private String message;

	/**
	 * Using the default size on processing image
	 */
	public ImageProcessor() {
		this.allowedImageExtensions = new HashSet<String>();
		this.allowedImageExtensions.add("png");
		this.allowedImageExtensions.add("jpg");
		this.allowedImageExtensions.add("gif");
		clearMessage();
	}

	/**
	 * Using the custom size on processing image
	 *
	 * @param width
	 * @param height
	 */
	public ImageProcessor(int width, int height) {
		this();
		setSize(width, height);
	}

	/**
	 * Set the new image size
	 *
	 * @param width
	 * @param height
	 */
	public void setSize(int width, int height) {
		image_width = width;
		image_height = height;
	}

	/**
	 * Get the width of the image being resized to
	 *
	 * @return
	 */
	public int getImageWidth() {
		return image_width;
	}

	/**
	 * Get the height of the image being resized to
	 *
	 * @return
	 */
	public int getImageHeight() {
		return image_width;
	}

	/**
	 * Get the imge.
	 *
	 * @param filePath
	 * @return
	 */
	public BufferedImage getBufferedImage(String filePath) {

		BufferedImage image = null;
		try {

			image = ImageIO.read(new File(filePath));

		} catch (Exception e) {
			e.printStackTrace();
		}

		return image;
	}

	/**
	 * Save an image to file system
	 *
	 * @param img
	 * @param filePath
	 * @param imgType
	 */
	public void saveImageToFile(BufferedImage img, String filePath,
			String imgType) {
		try {

			ImageIO.write(img, imgType, new File(filePath));

		} catch (Exception e) {
			message = e.getMessage();
			e.printStackTrace();
		}

	}

	/**
	 *
	 * @param image
	 * @param imgType
	 * @return
	 */
	public InputStream getImageStream(BufferedImage image, String imgType) {

		InputStream is = null;

		try {

			ByteArrayOutputStream os = new ByteArrayOutputStream();

			ImageIO.write(image, imgType, os);

			is = new ByteArrayInputStream(os.toByteArray());

		} catch (Exception e) {
			message = e.getMessage();
			e.printStackTrace();
		}
		return is;
	}

	/**
	 * Resize an image
	 *
	 * @param originalImage
	 * @return
	 */
	public BufferedImage resizeImage(BufferedImage originalImage, int w, int h) {

		int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB
				: originalImage.getType();

		BufferedImage resizedImage = new BufferedImage(w, h, type);

		Graphics2D image = resizedImage.createGraphics();

		image.drawImage(originalImage, 0, 0, w, h, null);

		image.dispose();

		return resizedImage;
	}

	/**
	 *
	 * @param originalImage
	 * @return
	 */
	public BufferedImage resizeImageInHighQuality(BufferedImage originalImage) {

		int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB
				: originalImage.getType();

		BufferedImage resizedImage = new BufferedImage(image_width,
				image_height, type);

		Graphics2D image = resizedImage.createGraphics();

		image.drawImage(originalImage, 0, 0, image_width, image_height, null);

		image.dispose();

		image.setComposite(AlphaComposite.Src);

		image.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BILINEAR);

		image.setRenderingHint(RenderingHints.KEY_RENDERING,
				RenderingHints.VALUE_RENDER_QUALITY);

		image.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		return resizedImage;
	}

	/**
	 * Process the Image, resize it to the required size
	 *
	 * @param file
	 * @return InputStream
	 */
	public InputStream process(MultipartFile file, int x, int y, int w, int h, int outputW, int outputH) {

		long fileSize = file.getSize();
		InputStream is = null;

		try {

			if (fileSize > 0) {
				if (fileSize <= IMAGE_MAX_SIZE) {

					InputStream inputStream = file.getInputStream();

					String imgType = FilenameUtils.getExtension(file.getOriginalFilename());

					BufferedImage originalImage = ImageIO.read(inputStream);

					is = this.getImageStream(originalImage, imgType);
					/*BufferedImage resizedImage = this.resizeImage(originalImage, image_width, image_height);

					BufferedImage crop = resizedImage.getSubimage(x, y, w, h);
					BufferedImage resizedCropImage = this.resizeImage(crop, outputW, outputH);

					is = this.getImageStream(resizedCropImage, imgType);*/

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return is;
	}

	public String getImageByteArray(Blob pic) {

		String blobAsBytes = null;

		try {
			if (pic != null) {

				int blobLength = (int) pic.length();
				if (blobLength > 0) {
					byte[] encoded = Base64.encode(pic.getBytes(1, blobLength));
					blobAsBytes = new String(encoded);
				}
			}

			/*
			 * System.out.println(
			 * "==========================================================\n" +
			 * "  getImageByteArray() METHOD\n" +
			 * "==========================================================\n" +
			 * blobLength + " bytes read.\n" );
			 */

		} catch (Exception e) {
			System.out
					.println("Caught SQL Exception: (Write BLOB value - Put Method).");
			e.printStackTrace();
		}
		return blobAsBytes;
	}

	public BufferedImage backToImage(Blob pic) {

		BufferedImage image = null;

		try {
			int blobLength = (int) pic.length();
			byte[] blobAsBytes = pic.getBytes(1, blobLength);

			image = ImageIO.read(new ByteArrayInputStream(blobAsBytes));

			System.out
					.println("==========================================================\n"
							+ "  PUT METHOD\n"
							+ "==========================================================\n"
							+ blobLength
							+ " bytes read.\n"
							+ blobLength
							+ " bytes written.\n");

		} catch (Exception e) {
			System.out
					.println("Caught SQL Exception: (Write BLOB value - Put Method).");
			e.printStackTrace();
		}
		return image;
	}

	private void clearMessage() {
		this.message = null;
	}

	public int getImage_height() {
		return image_height;
	}

	public void setImage_height(int image_height) {
		this.image_height = image_height;
	}

	public int getImage_width() {
		return image_width;
	}

	public void setImage_width(int image_width) {
		this.image_width = image_width;
	}

}
