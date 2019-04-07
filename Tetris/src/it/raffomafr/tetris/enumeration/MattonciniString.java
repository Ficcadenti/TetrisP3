package it.raffomafr.tetris.enumeration;

public enum MattonciniString
{
	T("111010", 3, 2), 
	I("1111", 4, 1), 
	L("111100", 3, 3), 
	J("111001", 3, 2), 
	O("1111", 2, 2), 
	S("011110", 3,2), 
	Z("110011", 3, 2);

	private String	stringa;
	private int		larghezza;
	private int		altezza;

	private MattonciniString(String stringaMattoncino, int larghezza, int altezza)
	{
		this.stringa = stringaMattoncino;
		this.larghezza = larghezza;
		this.altezza = altezza;
	}

	public String getStringa()
	{
		return this.stringa;
	}

	public void setStringa(String stringaMattoncino)
	{
		this.stringa = stringaMattoncino;
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
