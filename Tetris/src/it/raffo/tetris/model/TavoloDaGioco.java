package it.raffo.tetris.model;

import org.apache.log4j.Logger;

import it.raffo.tetris.enumeration.MattoniBase;
import it.raffo.tetris.utility.Costanti;

public class TavoloDaGioco
{
	private static final Logger		log	= Logger.getLogger(TavoloDaGioco.class);
	private static TavoloDaGioco	istanza;
	private int						altezza;
	private int						larghezza;
	private int[][]					matrice;

	public static TavoloDaGioco getInstance()
	{
		if (istanza == null)
		{
			istanza = new TavoloDaGioco();
			istanza.larghezza = Costanti.TavoloDaGioco.LARGHEZZA;
			istanza.altezza = Costanti.TavoloDaGioco.ALTEZZA;
			istanza.matrice = new int[istanza.larghezza][istanza.altezza];
		}

		return istanza;
	}

	public void azzeraMatrice()
	{
		for (int y = 0; y < this.matrice[0].length; y++)
		{
			for (int x = 0; x < this.matrice.length; x++)
			{
				this.matrice[x][y] = MattoniBase.VUOTO.getValore();
			}
		}
	}

	public void generaLivello(int livello)
	{
		this.azzeraMatrice();
		this.generaMuro(livello);
	}

	public void generaMuro(int livello)
	{
		for (int y = 0; y < this.altezza; y++)
		{
			this.matrice[0][y] = MattoniBase.MURO.getValore();
			this.matrice[this.larghezza - 1][y] = MattoniBase.MURO.getValore();
		}

		for (int x = 0; x < this.larghezza; x++)
		{
			this.matrice[x][this.altezza - 1] = MattoniBase.MURO.getValore();
		}
	}

	public void stampaTavolo()
	{
		for (int y = 0; y < this.matrice[0].length; y++)
		{
			for (int x = 0; x < this.matrice.length; x++)
			{
				System.out.print(this.matrice[x][y]);
			}
			System.out.println();
		}

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
}
