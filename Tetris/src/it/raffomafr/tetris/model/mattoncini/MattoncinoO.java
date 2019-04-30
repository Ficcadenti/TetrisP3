package it.raffomafr.tetris.model.mattoncini;

import org.apache.log4j.Logger;

import it.raffomafr.tetris.enumeration.MattonciniString;

public class MattoncinoO extends Mattoncino
{
	private static final Logger				log			= Logger.getLogger(MattoncinoO.class);
	private static final MattonciniString	mattoncino	= MattonciniString.O;

	public MattoncinoO()
	{
		super(mattoncino);
	}

}
