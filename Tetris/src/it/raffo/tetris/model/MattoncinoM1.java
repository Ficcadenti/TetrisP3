package it.raffo.tetris.model;

import org.apache.log4j.Logger;

import it.raffo.tetris.astratti.Mattoncino;
import it.raffo.tetris.enumeration.MattonciniString;

public class MattoncinoM1 extends Mattoncino
{
	private static final Logger	log	= Logger.getLogger(MattoncinoM1.class);

	private int					altezza;
	private int					larghezza;
	private int[][]				matrice;
	private int					posx;
	private int					posy;

	public MattoncinoM1()
	{
		log.info("Genero mattoncino M1");
		this.matrice = new int[MattonciniString.M1.getLarghezza()][MattonciniString.M1.getAltezza()];
		this.matrice = super.generaMatrice(MattonciniString.M1.getStringaMattoncino(),
				MattonciniString.M1.getLarghezza(), MattonciniString.M1.getAltezza());
		this.posx = super.generaPosizioneIniziale().getX();
		this.posy = super.generaPosizioneIniziale().getY();
	}

	public int getAltezza()
	{
		return this.altezza;
	}

	public void setAltezza(int altezza)
	{
		this.altezza = altezza;
	}

	public int getLarghezza()
	{
		return this.larghezza;
	}

	public void setLarghezza(int larghezza)
	{
		this.larghezza = larghezza;
	}

	public int[][] getMatrice()
	{
		return this.matrice;
	}

	public void setMatrice(int[][] matrice)
	{
		this.matrice = matrice;
	}

	public int getPosx()
	{
		return this.posx;
	}

	public void setPosx(int posx)
	{
		this.posx = posx;
	}

	public int getPosy()
	{
		return this.posy;
	}

	public void setPosy(int posy)
	{
		this.posy = posy;
	}

}
