package it.raffomafr.tetris.enumeration;

public enum MattoniBase
{
	VUOTO(0, ""), MURO(1, "muro.png"), ROSSO(1, "rosso.png"), GIALLO(1, "giallo.png"), VERDE(1, "verde.png"), BLU(1,
			"blu.png");

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
