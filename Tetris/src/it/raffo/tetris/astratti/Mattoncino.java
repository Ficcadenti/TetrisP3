package it.raffo.tetris.astratti;

public abstract class Mattoncino
{
	protected void generaPosizioneIniziale()
	{

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
