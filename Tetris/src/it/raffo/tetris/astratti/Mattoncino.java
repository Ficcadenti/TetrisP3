package it.raffo.tetris.astratti;

import it.raffo.tetris.model.Posizione;

public abstract class Mattoncino
{
	protected Posizione generaPosizioneIniziale()
	{
		Posizione p = new Posizione();
		p.setX(0);
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
