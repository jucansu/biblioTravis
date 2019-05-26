package com.biblitek.service.codigoqr;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

public class WebcamQRCodeGenerator extends JFrame {

	private static final long serialVersionUID = 6441489157408381878L;

	

	
	public static File generarQRNuevoUsuario(String texto) throws IOException {
		BitMatrix qr = null;
		// Para usar en cualquier entorno
		String desktop =   "D:/US/2018-2019/TFG/Repositorio/BIBLIOTEK/WORKSPACE/Bibliotek/src/main/webapp/content/tmp/";
		String nombre= texto.split("-")[1]; 
		File f = new File(desktop +nombre+ ".png");
		f.mkdirs();

		try {

			qr = new MultiFormatWriter().encode(texto, BarcodeFormat.QR_CODE, 250, 250);
			BufferedImage image = new BufferedImage(qr.getWidth(), qr.getHeight(), BufferedImage.TYPE_INT_RGB);
			image.createGraphics();
			Graphics2D graphics = (Graphics2D) image.getGraphics();
			graphics.setColor(Color.WHITE);
			graphics.fillRect(0, 0, qr.getWidth(), qr.getHeight());
			graphics.setColor(Color.BLACK);

			for (int i = 0; i < qr.getWidth(); i++) {
				for (int j = 0; j < qr.getHeight(); j++) {
					if (qr.get(i, j)) {
						graphics.fillRect(i, j, 1, 1);
					}
				}
			}

			ImageIO.write(image, "png", f);
			

		} catch (WriterException e) {
			e.printStackTrace();
		}
		return f;
	}

}