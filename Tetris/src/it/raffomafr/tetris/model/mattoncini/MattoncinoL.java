package it.raffomafr.tetris.model.mattoncini;

import org.apache.log4j.Logger;

import it.raffomafr.tetris.enumeration.MattonciniString;

public class MattoncinoL extends Mattoncino
{
	private static final Logger				log			= Logger.getLogger(MattoncinoL.class);
	private static final MattonciniString	mattoncino	= MattonciniString.L;

	public MattoncinoL()
	{
		super(mattoncino);
	}

}
