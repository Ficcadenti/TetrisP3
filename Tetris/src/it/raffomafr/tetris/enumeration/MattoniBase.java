package it.raffomafr.tetris.enumeration;

public enum MattoniBase
{
	VUOTO(0, "vuoto.jpg"), MURO(10, "mattoncinoMuro.jpg");

	private int		valore;
	private String	immagine;

	private MattoniBase(int valore, String immagine)
	{
		this.valore = valore;
		this.immagine = immagine;
	}

	public String getImmagine()
	{
		return this.immagine;
	}

	public void setImmagine(String immagine)
	{
		this.immagine = immagine;
	}

	public int getValore()
	{
		return this.valore;
	}

	public void setValore(int valore)
	{
		this.valore = valore;
	}

}
