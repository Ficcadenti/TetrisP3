package it.raffo.tetris.enumeration;

public enum Mattoncini
{
	M1("111010", 3, 2), M2("1111", 1, 0);

	private String	stringaMattoncino;
	private int		larghezza;
	private int		altezza;

	private Mattoncini(String stringaMattoncino, int larghezza, int altezza)
	{
		this.stringaMattoncino = stringaMattoncino;
		this.larghezza = larghezza;
		this.altezza = altezza;
	}

	public String getStringaMattoncino()
	{
		return this.stringaMattoncino;
	}

	public void setStringaMattoncino(String stringaMattoncino)
	{
		this.stringaMattoncino = stringaMattoncino;
	}

	public int getLarghezza()
	{
		return this.larghezza;
	}

	public void setLarghezza(int larghezza)
	{
		this.larghezza = larghezza;
	}

	public int getAltezza()
	{
		return this.altezza;
	}

	public void setAltezza(int altezza)
	{
		this.altezza = altezza;
	}

}
