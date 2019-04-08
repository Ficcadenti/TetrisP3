package it.raffomafr.tetris.astratti;

import java.util.Random;

import org.apache.log4j.Logger;

import it.raffomafr.tetris.controller.Matrice;
import it.raffomafr.tetris.enumeration.MattonciniString;
import it.raffomafr.tetris.enumeration.Rotazioni;
import it.raffomafr.tetris.model.Posizione;
import it.raffomafr.tetris.utility.Costanti;

public abstract class Mattoncino
{
	private static final Logger	log	= Logger.getLogger(Mattoncino.class);
	private MattonciniString	mattoncino;

	public Mattoncino(MattonciniString mattoncino)
	{
		this.mattoncino = mattoncino;
		log.info("-----> " + mattoncino.getDesc());
		// genoro la matrice associata ad T a partire dalla stringa
		this.matrice = this.generaMatrice(mattoncino.getStringa(), mattoncino.getLarghezza(), mattoncino.getAltezza());

		// genero una posizione iniziale TOP=0 sull'asse x
		this.generaPosizioneIniziale();

		// genero una rotazione casuale
		int numeroRotazioniDx = this.generaRotazioneIniziale();
		// setto larghezza prima di rotazione
		this.larghezza = mattoncino.getLarghezza();

		// setto altezza prima di rotazione
		this.altezza = mattoncino.getAltezza();

		log.info("Rotazioni iniziali(" + numeroRotazioniDx + "): " + (90 * numeroRotazioniDx) + "°");

		if (numeroRotazioniDx > 0)
		{
			Matrice.getInstance().setMatrice(this);
			for (int rotazione = 0; rotazione < numeroRotazioniDx; rotazione++)
			{
				Matrice.getInstance().rotazione(Rotazioni.DX);
			}

			this.matrice = Matrice.getInstance().getMatrice();
			// setto larghezza dopo rotazione se necessario
			this.larghezza = Matrice.getInstance().getLarghezza();

			// setto altezza dopo rotazione se necessario
			this.altezza = Matrice.getInstance().getAltezza();
		}
	}

	private int		altezza;
	private int		larghezza;
	private int[][]	matrice;
	private int		posx;
	private int		posy;

	public void stampa()
	{
		for (int y = 0; y < this.altezza; y++)
		{
			for (int x = 0; x < this.larghezza; x++)
			{
				System.out.print(this.matrice[x][y]);

			}
			System.out.println();
		}
	}

	public void info()
	{
		log.info("Nome      : " + this.mattoncino.getDesc());
		log.info("Stringa   : " + this.mattoncino.getStringa());
		log.info("Larghezza : " + this.mattoncino.getLarghezza());
		log.info("Altezza   : " + this.mattoncino.getAltezza());
		log.info("posX      : " + this.getPosx());
		log.info("posY      : " + this.getPosy());
	}

	public int getPosx()
	{
		return this.posx;
	}

	public void setPosx(int posx)
	{
		this.posx = posx;
	}

	public int getPosy()
	{
		return this.posy;
	}

	public void setPosy(int posy)
	{
		this.posy = posy;
	}

	protected int generaRotazioneIniziale()
	{
		Random random = new Random();
		return random.nextInt(4); // da 0 a 3
	}

	protected void generaPosizioneIniziale()
	{
		Random random = new Random();
		Posizione p = new Posizione();
		this.setPosx(random.nextInt(Costanti.TavoloDaGioco.LARGHEZZA_GIOCO));
		this.setPosy(0);
	}

	protected int[][] generaMatrice(String famiglia, int dx, int dy)
	{
		int matrice[][] = new int[dx][dy];
		int pos = 0;
		for (int y = 0; y < dy; y++)
		{
			for (int x = 0; x < dx; x++)
			{
				matrice[x][y] = Integer.parseInt("" + famiglia.charAt(pos++));
			}
		}

		return matrice;
	}

	public int getAltezza()
	{
		return this.altezza;
	}

	public void setAltezza(int altezza)
	{
		this.altezza = altezza;
	}

	public int getLarghezza()
	{
		return this.larghezza;
	}

	public void setLarghezza(int larghezza)
	{
		this.larghezza = larghezza;
	}

	public void setMatrice(int[][] matrice)
	{
		this.matrice = matrice;
	}

	public int[][] getMatrice()
	{
		return this.matrice;
	}

}
