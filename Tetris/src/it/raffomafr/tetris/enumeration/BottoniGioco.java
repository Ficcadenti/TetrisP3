package it.raffomafr.tetris.enumeration;

import it.raffomafr.tetris.utility.Costanti;

public enum BottoniGioco
{

	SI("SI", "", UtilityGioco.SCRITTAGAMEOVER.getPosX() - UtilityGioco.SCRITTAGAMEOVER.getPosX() / 2, UtilityGioco.SCRITTAGAMEOVER.getPosY() + 50, 50, 50),
	NO("NO", "", UtilityGioco.SCRITTAGAMEOVER.getPosX() + UtilityGioco.SCRITTAGAMEOVER.getLarghezza() + 50, UtilityGioco.SCRITTAGAMEOVER.getPosY() + 50, 50, 50),
	SCRITTAGAMEOVER("VUOI RICOMINCIARE ?", "", Costanti.Sketch.LARGHEZZA / 2, Costanti.Sketch.ALTEZZA - 100, 0, 0);

	private String	desc;
	private String	estensione;
	private int		posX;
	private int		posY;
	private int		larghezza;
	private int		altezza;

	private BottoniGioco(String desc, String estensione, int posX, int posY, int larghezza, int altezza)
	{
		this.desc = desc;
		this.estensione = estensione;
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
		return this.estensione;
	}

	public void setEstensione(String estensione)
	{
		this.estensione = estensione;
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
