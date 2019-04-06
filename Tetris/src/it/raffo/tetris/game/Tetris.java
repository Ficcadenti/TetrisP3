package it.raffo.tetris.game;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import it.raffo.tetris.controller.Matrice;
import it.raffo.tetris.enumeration.Rotazioni;
import it.raffo.tetris.model.MattoncinoM1;
import it.raffo.tetris.model.TavoloDaGioco;

public class Tetris
{

	private static final Logger log = Logger.getLogger(Tetris.class);

	public static void main(String[] args)
	{
		// PropertiesConfigurator is used to configure logger from properties file
		PropertyConfigurator.configure("log4j.properties");

		TavoloDaGioco.getInstance().generaLivello(0);
		TavoloDaGioco.getInstance().stampaTavolo();

		MattoncinoM1 M1 = new MattoncinoM1();

		Matrice.getInstance().setMatrice(M1.getMatrice());
		Matrice.getInstance().rotazione(Rotazioni.DX);
		Matrice.getInstance().stampaMatrice();

	}

}
