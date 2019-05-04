package it.raffomafr.tetris.model.mattoncini;

import org.apache.log4j.Logger;

import it.raffomafr.tetris.enumeration.MattonciniString;

public class MattoncinoJ extends Mattoncino
{
	private static final Logger				log			= Logger.getLogger(MattoncinoJ.class);
	private static final MattonciniString	mattoncino	= MattonciniString.J;

	public MattoncinoJ()
	{
		super(mattoncino);
	}

	public MattoncinoJ(boolean flag)
	{
		super(mattoncino, flag);
	}

}
