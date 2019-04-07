package it.raffomafr.tetris.model.mattoncini;

import org.apache.log4j.Logger;

import it.raffomafr.tetris.astratti.Mattoncino;
import it.raffomafr.tetris.enumeration.MattonciniString;

public class MattoncinoJ extends Mattoncino
{
	private static final Logger				log			= Logger.getLogger(MattoncinoJ.class);
	private static final MattonciniString	mattoncino	= MattonciniString.J;

	public MattoncinoJ()
	{
		super(mattoncino);
		log.info("Genero mattoncino : " + mattoncino.getDesc());
		super.stampa();
	}

}
