package it.raffomafr.tetris.enumeration;

public enum MattonciniString
{
	T("111010", 3, 2, "Mattoncino T", Colore.ROSSO), I("1111", 4, 1, "Mattoncino I", Colore.VERDE), L("111100", 3, 2, "Mattoncino L", Colore.ROSA), J("111001", 3, 2, "Mattoncino J", Colore.GIALLO), O("1111", 2, 2, "Mattoncino O",
			Colore.BLUE), S("011110", 3, 2, "Mattoncino S", Colore.ARANCIONE), Z("110011", 3, 2, "Mattoncino Z", Colore.AZZURRO);

	private String	stringa;
	private String	desc;
	private Colore	colore;
	private int		larghezza;
	private int		altezza;

	private MattonciniString(String stringaMattoncino, int larghezza, int altezza, String desc, Colore colore)
	{
		this.stringa = stringaMattoncino;
		this.larghezza = larghezza;
		this.altezza = altezza;
		this.desc = desc;
		this.colore = colore;
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

	public String getDesc()
	{
		return this.desc;
	}

	public void setDesc(String nome)
	{
		this.desc = nome;
	}

	public Colore getColore()
	{
		return this.colore;
	}

	public void setColore(Colore colore)
	{
		this.colore = colore;
	}

}
