package it.raffomafr.tetris.enumeration;

public enum MattonciniString
{
	T("111010", 3, 2, "T"), I("1111", 4, 1, "I"), L("111100", 3, 2, "L"), J("111001", 3, 2, "J"), O("1111", 2, 2,
			"O"), S("011110", 3, 2, "S"), Z("110011", 3, 2, "Z");

	private String	stringa;
	private String	desc;
	private int		larghezza;
	private int		altezza;

	private MattonciniString(String stringaMattoncino, int larghezza, int altezza, String nome)
	{
		this.stringa = stringaMattoncino;
		this.larghezza = larghezza;
		this.altezza = altezza;
		this.desc = nome;
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

}
