package it.raffomafr.tetris.model.mattoncini;

import org.apache.log4j.Logger;

import it.raffomafr.tetris.astratti.Mattoncino;
import it.raffomafr.tetris.controller.Matrice;
import it.raffomafr.tetris.enumeration.MattonciniString;
import it.raffomafr.tetris.enumeration.Rotazioni;

public class MattoncinoI extends Mattoncino
{
	private static final Logger	log	= Logger.getLogger(MattoncinoI.class);

	private int					altezza;
	private int					larghezza;
	private int[][]				matrice;
	private int					posx;
	private int					posy;
	private MattonciniString	mattoncino;

	public MattoncinoI()
	{
		log.info("Genero mattoncino I");
		this.mattoncino = MattonciniString.I;
		// genoro la matrice associata ad I a partire dalla stringa
		this.matrice = super.generaMatrice(this.mattoncino.getStringa(), this.mattoncino.getLarghezza(),
				this.mattoncino.getAltezza());

		// genero posizione casuale al TOP lungo l'asse x
		this.posx = super.generaPosizioneIniziale().getX();
		this.posy = super.generaPosizioneIniziale().getY();

		// genero una rotazione casuale
		int numeroRotazioniDx = this.generaRotazioneIniziale();
		// setto larghezza prima di rotazione
		this.larghezza = this.mattoncino.getLarghezza();

		// setto altezza prima di rotazione
		this.altezza = this.mattoncino.getAltezza();

		// mattoncino originale
		this.stampa();
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

		// mattoncino ruotato
		this.stampa();

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

	@Override
	public void stampa()
	{
		log.info("Larghezza : " + this.larghezza);
		log.info("Altezza : " + this.altezza);
		for (int y = 0; y < this.altezza; y++)
		{
			for (int x = 0; x < this.larghezza; x++)
			{
				System.out.print(this.matrice[x][y]);

			}
			System.out.println();
		}
	}

	@Override
	public int[][] getMatrice()
	{
		return this.matrice;
	}

	@Override
	public void info()
	{
		log.info("Nome      : " + this.getClass().getSimpleName());
		log.info("Stringa   : " + this.mattoncino.getStringa());
		log.info("Larghezza : " + this.mattoncino.getLarghezza());
		log.info("Altezza   : " + this.mattoncino.getAltezza());
	}

}
