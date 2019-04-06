package it.raffo.tetris.game;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

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

	}

}
