package it.raffo.tetris.model;

import it.raffo.tetris.enumeration.Mattoncini;

public class MattoncinoM1
{
	private int		altezza;
	private int		larghezza;
	private int[][]	matrice;
	private int		posx;
	private int		posy;

	public MattoncinoM1()
	{
		this.generaMatrice(Mattoncini.M1.getStringaMattoncino(), Mattoncini.M1.getLarghezza(),
				Mattoncini.M1.getAltezza());
	}

	private void generaMatrice(String stringaMattoncino, int larghezza2, int altezza2)
	{
		// da fare come il key life

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
