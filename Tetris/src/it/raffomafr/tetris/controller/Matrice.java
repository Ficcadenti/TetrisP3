package it.raffomafr.tetris.controller;

import org.apache.log4j.Logger;

import it.raffomafr.tetris.astratti.Mattoncino;
import it.raffomafr.tetris.enumeration.Rotazioni;

public class Matrice
{

	private static final Logger	log	= Logger.getLogger(Matrice.class);
	private static Matrice		istanza;
	private int					altezza;
	private int					larghezza;
	private int[][]				matrice;

	public static Matrice getInstance()
	{
		if (istanza == null)
		{
			istanza = new Matrice();
			istanza.larghezza = -1;
			istanza.altezza = -1;
		}

		return istanza;
	}

	private Matrice()
	{
		super();
		this.matrice = null;
	}

	public void azzeraMatrice()
	{
		if ((this.larghezza == -1) || (this.altezza == -1))
		{
			log.info("Dimensioni matrice mancanti !!!");
			return;
		}
		this.matrice = new int[this.larghezza][this.altezza];
		for (int x = 0; x < this.larghezza; x++)
		{
			for (int y = 0; y < this.altezza; y++)
			{

				this.matrice[x][y] = 0;
			}
		}
	}

	public int[][] getMatrice()
	{
		return this.matrice;
	}

	public void dimensioneMatrice()
	{
		log.info("Larghezza : " + this.matrice[0].length);
		log.info("Altezza   : " + this.matrice.length);
	}

	public void stampaMatrice()
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

	public void trasporta()
	{
		int temp;
		int appo[][] = new int[this.altezza][this.larghezza];
		for (int y = 0; y < this.altezza; y++)
		{
			for (int x = 0; x < this.larghezza; x++)
			{
				appo[y][x] = this.matrice[x][y];
			}
		}
		this.matrice = appo;
		temp = this.altezza;
		this.altezza = this.larghezza;
		this.larghezza = temp;
	}

	public void inversioneColonne()
	{
		int temp;
		for (int y = 0; y < this.altezza; y++)
		{
			for (int x = 0; x < (this.larghezza / 2); x++)
			{
				temp = this.matrice[x][y];
				this.matrice[x][y] = this.matrice[(this.larghezza - 1) - x][y];
				this.matrice[(this.matrice.length - 1) - x][y] = temp;
			}
		}

	}

	public void inversioneRiga()
	{
		int temp;
		for (int y = 0; y < (this.altezza / 2); y++)
		{
			for (int x = 0; x < (this.larghezza); x++)
			{
				temp = this.matrice[x][y];
				this.matrice[x][y] = this.matrice[x][(this.altezza - 1) - y];
				this.matrice[x][(this.altezza - 1) - y] = temp;
			}
		}

	}

	public void rotazione(Rotazioni rotazione)
	{
		this.trasporta();
		if (rotazione == Rotazioni.DX)
		{
			this.inversioneColonne();
		}
		else if (rotazione == Rotazioni.SX)
		{
			this.inversioneRiga();
		}
	}

	public void setAltezza(int h)
	{
		this.altezza = h;
	}

	public void setMatrice(int[][] matrice)
	{
		this.larghezza = matrice.length;
		this.altezza = matrice[0].length;
		this.matrice = matrice;
	}

	public void setMatrice(Mattoncino m)
	{
		this.matrice = m.getMatrice();
		this.larghezza = this.matrice.length;
		this.altezza = this.matrice[0].length;

	}

	public void setLarghezza(int w)
	{
		this.larghezza = w;
	}

	public int getAltezza()
	{
		return this.altezza;
	}

	public int getLarghezza()
	{
		return this.larghezza;
	}

}
