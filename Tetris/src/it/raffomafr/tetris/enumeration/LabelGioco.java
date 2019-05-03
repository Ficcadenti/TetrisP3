package it.raffomafr.tetris.enumeration;

import it.raffomafr.tetris.utility.Costanti;

public enum LabelGioco
{
	SCRITTAPAUSA("Pausa", "", Costanti.Sketch.LARGHEZZA / 2, Costanti.Sketch.ALTEZZA / 2, 0, 0),
	SCRITTAGAMEOVER("Vuoi ricominciare ?", "", Costanti.Sketch.LARGHEZZA / 2, Costanti.Sketch.ALTEZZA - 100, 0, 0),
	RIGHEABBATTUTE("Righe Abbattute : ", "", 540, 130, 0, 0);

	private String	desc;
	private String	info;
	private int		posX;
	private int		posY;
	private int		larghezza;
	private int		altezza;

	private LabelGioco(String desc, String info, int posX, int posY, int larghezza, int altezza)
	{
		this.desc = desc;
		this.info = info;
		this.posX = posX;
		this.posY = posY;
		this.larghezza = larghezza;
		this.altezza = altezza;
	}

	public String getDesc()
	{
		return this.desc;
	}

	public void setdesc(String desc)
	{
		this.desc = desc;
	}

	public String getEstensione()
	{
		return this.info;
	}

	public void setEstensione(String estensione)
	{
		this.info = estensione;
	}

	public int getPosX()
	{
		return this.posX;
	}

	public void setPosX(int posX)
	{
		this.posX = posX;
	}

	public int getPosY()
	{
		return this.posY;
	}

	public void setPosY(int posY)
	{
		this.posY = posY;
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
