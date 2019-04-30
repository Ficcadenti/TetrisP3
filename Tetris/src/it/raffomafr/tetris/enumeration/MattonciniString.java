package it.raffomafr.tetris.enumeration;

public enum MattonciniString
{
	VUOTO(0, "0", 1, 1, "MURO", Colore.NERO, "mattoncinoMuro.jpg"),
	T(1, "111010", 3, 2, "Mattoncino T", Colore.ROSSO, "mattoncinoT.jpg"),
	I(2, "1111", 4, 1, "Mattoncino I", Colore.VERDE, "mattoncinoI.jpg"),
	L(3, "111100", 3, 2, "Mattoncino L", Colore.ROSA, "mattoncinoL.jpg"),
	J(4, "111001", 3, 2, "Mattoncino J", Colore.GIALLO, "mattoncinoJ.jpg"),
	O(5, "1111", 2, 2, "Mattoncino O", Colore.BLUE, "mattoncinoO.jpg"),
	S(6, "011110", 3, 2, "Mattoncino S", Colore.ARANCIONE, "mattoncinoS.jpg"),
	Z(7, "110011", 3, 2, "Mattoncino Z", Colore.AZZURRO, "mattoncinoZ.jpg"),
	MURO(8, "1", 1, 1, "MURO", null, "mattoncinoMuro.jpg"),
	PROIEZIONE(9, "1", 1, 1, "PROIEZIONE", null, "mattoncinoP.jpg");

	private String	stringa;
	private String	desc;
	private Colore	colore;
	private int		larghezza;
	private int		altezza;
	private String	nomeImg;
	private int		tipo;

	private MattonciniString(int tipo, String stringaMattoncino, int larghezza, int altezza, String desc, Colore colore, String nomeImg)
	{
		this.tipo = tipo;
		this.stringa = stringaMattoncino;
		this.larghezza = larghezza;
		this.altezza = altezza;
		this.desc = desc;
		this.colore = colore;
		this.nomeImg = nomeImg;
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

	public String getNomeImg()
	{
		return this.nomeImg;
	}

	public void setNomeImg(String nomeImg)
	{
		this.nomeImg = nomeImg;
	}

	public int getTipo()
	{
		return this.tipo;
	}

	public void setTipo(int tipo)
	{
		this.tipo = tipo;
	}

}
