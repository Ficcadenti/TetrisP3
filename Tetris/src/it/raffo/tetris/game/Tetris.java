package it.raffo.tetris.game;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import it.raffo.tetris.controller.Matrice;

public class Tetris
{

	private static final Logger log = Logger.getLogger(Tetris.class);

	public static void main(String[] args)
	{
		// PropertiesConfigurator is used to configure logger from properties file
		PropertyConfigurator.configure("log4j.properties");

		Matrice.getInstance().setLarghezza(3);
		Matrice.getInstance().setAltezza(2);
		Matrice.getInstance().azzeraMatrice();
		Matrice.getInstance().getMatrice()[0][0] = 1;
		Matrice.getInstance().getMatrice()[1][0] = 1;
		Matrice.getInstance().getMatrice()[2][0] = 0;
		Matrice.getInstance().getMatrice()[0][1] = 0;
		Matrice.getInstance().getMatrice()[1][1] = 1;
		Matrice.getInstance().getMatrice()[2][1] = 1;

		Matrice.getInstance().stampaMatrice();

		log.info("\\nRotazione 90° DX ");
		Matrice.getInstance().rotazione();
		Matrice.getInstance().stampaMatrice();

		log.info("\\nRotazione 90° DX ");
		Matrice.getInstance().rotazione();
		Matrice.getInstance().stampaMatrice();

	}

}
