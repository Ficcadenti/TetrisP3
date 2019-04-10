package it.raffomafr.tetris.enumeration;

public enum Colore
{
	AZZURRO(0, 255, 255), BLUE(0, 2, 173), ARANCIONE(255, 175, 2), GIALLO(255, 255, 0), VERDE(0, 255, 0), ROSA(255, 1, 204), ROSSO(255, 0, 0);

	private int	r;
	private int	g;
	private int	b;

	private Colore(int r, int g, int b)
	{
		this.r = r;
		this.g = g;
		this.b = b;
	}

	public int getR()
	{
		return this.r;
	}

	public void setR(int r)
	{
		this.r = r;
	}

	public int getG()
	{
		return this.g;
	}

	public void setG(int g)
	{
		this.g = g;
	}

	public int getB()
	{
		return this.b;
	}

	public void setB(int b)
	{
		this.b = b;
	}

}
