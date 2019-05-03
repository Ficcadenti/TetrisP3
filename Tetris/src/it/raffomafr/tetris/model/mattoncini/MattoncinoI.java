package it.raffomafr.tetris.model.mattoncini;

import org.apache.log4j.Logger;

import it.raffomafr.tetris.enumeration.MattonciniString;

public class MattoncinoI extends Mattoncino
{
	private static final Logger				log			= Logger.getLogger(MattoncinoI.class);
	private static final MattonciniString	mattoncino	= MattonciniString.I;

	public MattoncinoI()
	{
		super(mattoncino);
	}

}
