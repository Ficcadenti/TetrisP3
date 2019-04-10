package it.raffomafr.tetris.model.mattoncini;

import org.apache.log4j.Logger;

import it.raffomafr.tetris.enumeration.MattonciniString;

public class MattoncinoS extends Mattoncino
{
	private static final Logger				log			= Logger.getLogger(MattoncinoS.class);
	private static final MattonciniString	mattoncino	= MattonciniString.S;

	public MattoncinoS()
	{
		super(mattoncino);
		log.info("Genero mattoncino : " + mattoncino.getDesc());
		super.stampa();
	}

}
