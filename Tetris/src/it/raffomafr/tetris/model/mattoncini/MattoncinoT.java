package it.raffomafr.tetris.model.mattoncini;

import org.apache.log4j.Logger;

import it.raffomafr.tetris.enumeration.MattonciniString;

public class MattoncinoT extends Mattoncino
{
	private static final Logger				log			= Logger.getLogger(MattoncinoT.class);
	private static final MattonciniString	mattoncino	= MattonciniString.T;

	public MattoncinoT()
	{
		super(mattoncino);
	}

}
