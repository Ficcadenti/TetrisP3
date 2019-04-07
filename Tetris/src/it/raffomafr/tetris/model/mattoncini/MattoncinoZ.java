package it.raffomafr.tetris.model.mattoncini;

import org.apache.log4j.Logger;

import it.raffomafr.tetris.astratti.Mattoncino;
import it.raffomafr.tetris.enumeration.MattonciniString;

public class MattoncinoZ extends Mattoncino
{
	private static final Logger				log			= Logger.getLogger(MattoncinoZ.class);
	private static final MattonciniString	mattoncino	= MattonciniString.Z;

	public MattoncinoZ()
	{
		super(mattoncino);
		log.info("Genero mattoncino : " + mattoncino.getDesc());
		super.stampa();
	}

}
