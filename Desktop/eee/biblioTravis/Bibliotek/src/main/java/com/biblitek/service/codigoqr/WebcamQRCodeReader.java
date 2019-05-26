package com.biblitek.service.codigoqr;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import javax.sound.sampled.Clip;
import javax.swing.JFrame;
import javax.swing.JTextArea;

import org.springframework.beans.factory.annotation.Autowired;

import com.bibliotek.domain.Biblioteca;
import com.bibliotek.domain.Estudiante;
import com.bibliotek.repository.BibliotecaRepository;
import com.bibliotek.repository.EstudianteRepository;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

public class WebcamQRCodeReader extends JFrame implements Runnable, ThreadFactory {

	private static final long serialVersionUID = 6441489157408381878L;

	private Executor executor = Executors.newSingleThreadExecutor(this);

	private Webcam webcam = null;
	private WebcamPanel panel = null;
	private JTextArea textarea = null;
	@Autowired
	private BibliotecaRepository bibliotecaRepository;
	@Autowired
	private EstudianteRepository estudianteRepository;

	private int num = 0;

	public WebcamQRCodeReader(BibliotecaRepository bibliotecaRepository, EstudianteRepository estudianteRepository) {
		super();
		this.bibliotecaRepository = bibliotecaRepository;
		this.estudianteRepository = estudianteRepository;
		setLayout(new FlowLayout());
		setTitle("Read QR / Bar Code With Webcam");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Dimension size = WebcamResolution.VGA.getSize();// Definición de resolución
														// de cámara, VGA (640x480 px)

		webcam = Webcam.getWebcams().get(0);// Lectura de cámaras del sistema

		if (!webcam.isOpen()) {
			webcam.setViewSize(size);
		}

		panel = new WebcamPanel(webcam); // Creación de una ventana para
		panel.setPreferredSize(size); // poder visualizar cómo escanea
		panel.setFPSDisplayed(true); // la cámara en tiempo real

		textarea = new JTextArea(); // Adición de un cuadro de
		textarea.setEditable(false); // texto para visualizar la
		textarea.setPreferredSize(size); // información en timepo real

		add(panel);
		add(textarea);

		pack();
		setVisible(true);

		executor.execute(this); // Ejecución en hilo
	}

	public void run() {

		do {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			Result result = null;
			BufferedImage image = null;

			if (webcam.isOpen()) {

				if ((image = webcam.getImage()) == null) {
					continue;
				}
				// Creación de mapa de bits que solicita luminiszancia en escala de grises
				LuminanceSource source = new BufferedImageLuminanceSource(image);

				// Generacion de mapa binario de bits a partir del objeto anterior(Imagen 1)
				BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

				try {
					// Decodificación del mapa binario creado en el objeto BinaryBitmap anterior
					result = new MultiFormatReader().decode(bitmap);
				} catch (NotFoundException e) {
					// fall thru, it means there is no QR code in image
				}
			}

			if (result != null) {

				if (result.getText() != null) {
					boolean suena = false;
					textarea.setText(result.getText());
					// Clip sound = Sounds.getSound("SonarPing.wav");

					// Inicializamos Estiudiante y Biblioteca
					Estudiante estudiante = new Estudiante();
					Biblioteca biblioteca = new Biblioteca();

					// Sacamos el id del estudiante y el id Biblioteca
					String[] cadenaText = textarea.getText().split("-");
					Long idEstudiante = Long.parseLong(cadenaText[0]);
					Long idBiblioteca = 1L;

					// Encontramos el estudiante y la bibliteca

					biblioteca = this.bibliotecaRepository.findById(idBiblioteca).get();
					estudiante = this.estudianteRepository.findById(idEstudiante).get();

					int plazasOcupadas = biblioteca.getPlazasOcupadas();
					int capacidadTotal = biblioteca.getPlazasTotales();
					int plazasDisponibles = capacidadTotal - plazasOcupadas;
					Date d = new Date();
					Instant tiempoDesdeQuePique = estudiante.getFechaModificacion();
					Long horaActual = d.getTime();
					Timestamp t = new Timestamp(d.getTime());

					// estudiante.setIdBibliotecaEsta(0L);
					// comprobamos si el estudiante esta en una biblioteca

					// ESTUDIANTE ENTRA O SALE DE MANERA ABSOLUTA
					if (estudiante.getPausa() != null && estudiante.getPausa() == false) {

						if (estudiante.getIdBibliotecaEsta() == (int) 0
								&& estudiante.getIdBibliotecaEsta() != idBiblioteca) {

							// entra de la biblio
							estudiante.setIdBibliotecaEsta(idBiblioteca);
							estudiante.setFechaModificacion(t.toInstant());
							if (plazasDisponibles > 0) {
								Clip entrada = Sounds.getSound("SonarPing.wav");
								suena = Sounds.playSound(entrada);

								biblioteca.setPlazasOcupadas(plazasOcupadas + 1);
								// update/save de biblioteca y estudiante
								this.bibliotecaRepository.updateBiblioteca(biblioteca.getPlazasOcupadas(),
										idBiblioteca);
								this.estudianteRepository.update(idBiblioteca, idEstudiante,
										estudiante.getFechaModificacion());
							}

						} else {

							// sale de la biblio
							Clip salida = Sounds.getSound("SonarPing.wav");
							// TODO meter sound de salida
							suena = Sounds.playSound(salida);

							estudiante.setIdBibliotecaEsta(0L);
							estudiante.setFechaModificacion(t.toInstant());

							if (plazasDisponibles <= capacidadTotal) {
								biblioteca.setPlazasOcupadas(plazasOcupadas - 1);
								// update/save de biblioteca y estudiante
								this.bibliotecaRepository.updateBiblioteca(biblioteca.getPlazasOcupadas(),
										idBiblioteca);
								this.estudianteRepository.update(0L, idEstudiante, estudiante.getFechaModificacion());
							}

						}
						// TODO EL ESTUDIANTE HA PEDIDO UNA PAUSA PARA SALIR A DESCANSAR
					} else {
						this.estudianteRepository.updatePausa(false, idEstudiante);
						// TODO EL ESTUDIANTE ENTRA ANTES DE LOS 30 MIN FUERA MAXIMOS
						Clip salida = Sounds.getSound("SonarPing.wav");
						// TODO meter sound de salida
						suena = Sounds.playSound(salida);
				/*		if ((horaActual - tiempoDesdeQuePique.getEpochSecond()) < 30
								&& estudiante.getIdBibliotecaEsta() == idBiblioteca) {
							// Base de datos no hace nada, usuario abre el torno correctamente

							// TODO PASADOS LOS 30 MIN, EL ESTUDIANTE ES DADO DE BAJA EN LA BIBLITOCA

						} else if ((horaActual - tiempoDesdeQuePique.getEpochSecond()) > 30
								&& estudiante.getIdBibliotecaEsta() == idBiblioteca) {

							biblioteca.setPlazasOcupadas(plazasOcupadas - 1);
							estudiante.setFechaModificacion(t.toInstant());

							// update/save de biblioteca y estudiante
							this.bibliotecaRepository.updateBiblioteca(biblioteca.getPlazasOcupadas(), idBiblioteca);
							this.estudianteRepository.update(0L, idEstudiante, estudiante.getFechaModificacion());

						} else {
							// TODO el estudiante no vuelve a entrar nunca mas, se ejecuta un schedule cada
							// 10 min que revise
							// aquellos estudiantes que tengan el boton pausa a 1 y que haya pasado más de
							// 45 min desde la última fecha de
							// modificacion, entonces eliminaremos esos estudiantes de la bbdd

						}*/

					}
					if (suena && num < 20) {
						try {
							Thread.sleep(2000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}

					} else {
						break;
					}
				}
			}

		} while (true);

	}

	public Thread newThread(Runnable r) {
		Thread t = new Thread(r, "example-runner");
		t.setDaemon(true);
		return t;
	}

}