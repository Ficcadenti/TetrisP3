package it.raffomafr.tetris.game;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import it.raffomafr.tetris.astratti.Mattoncino;
import it.raffomafr.tetris.controller.Matrice;
import it.raffomafr.tetris.model.TavoloDaGioco;

public class Tetris
{

	private static final Logger log = Logger.getLogger(Tetris.class);

	public static void main(String[] args)
	{
		TavoloDaGioco tetris = TavoloDaGioco.getInstance();
		Matrice matrice = Matrice.getInstance();
		PropertyConfigurator.configure("log4j.properties");

		// setto il livello 0
		tetris.generaLivello(0);
		Mattoncino mattoncinoCasuale = tetris.generaMattoncino();
		mattoncinoCasuale.info();

		tetris.inserisciMattoncino(mattoncinoCasuale);

	}

}
