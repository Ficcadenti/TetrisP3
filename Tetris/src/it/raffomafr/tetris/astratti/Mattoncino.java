package it.raffomafr.tetris.astratti;

import java.util.Random;

import it.raffomafr.tetris.model.Posizione;
import it.raffomafr.tetris.utility.Costanti;

public abstract class Mattoncino
{
	public abstract void stampa();

	public abstract void info();

	public abstract int[][] getMatrice();

	protected int generaRotazioneIniziale()
	{
		Random random = new Random();
		return random.nextInt(4); // da 0 a 3
	}

	protected Posizione generaPosizioneIniziale()
	{
		Random random = new Random();
		Posizione p = new Posizione();
		p.setX(random.nextInt(Costanti.TavoloDaGioco.LARGHEZZA_GIOCO));
		p.setY(0);
		return p;
	}

	protected int[][] generaMatrice(String famiglia, int dx, int dy)
	{
		int matrice[][] = new int[dx][dy];
		int pos = 0;
		for (int y = 0; y < dy; y++)
		{
			for (int x = 0; x < dx; x++)
			{
				matrice[x][y] = Integer.parseInt("" + famiglia.charAt(pos++));
			}
		}

		return matrice;
	}
}
