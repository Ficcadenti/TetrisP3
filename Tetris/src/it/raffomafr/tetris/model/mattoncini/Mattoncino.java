package it.raffomafr.tetris.model.mattoncini;

import java.util.Random;

import org.apache.log4j.Logger;

import it.raffomafr.tetris.controller.Matrice;
import it.raffomafr.tetris.enumeration.MattonciniString;
import it.raffomafr.tetris.enumeration.Rotazioni;
import it.raffomafr.tetris.model.Posizione;
import it.raffomafr.tetris.model.TavoloDaGioco;
import it.raffomafr.tetris.utility.Costanti;
import processing.core.PApplet;
import processing.core.PImage;

public abstract class Mattoncino implements Cloneable
{
	private static final Logger	log	= Logger.getLogger(Mattoncino.class);
	private MattonciniString	mattoncino;
	private PImage				img;
	private PApplet				pa;
	private int					altezza;
	private int					larghezza;
	private int[][]				matrice;
	private int					posx;
	private int					posy;
	private int					posxAssoluta;
	private int					posyAssoluta;
	private int					larghezzaImg;
	private int					altezzaImg;

	@Override
	public Object clone() throws CloneNotSupportedException
	{
		return super.clone();
	}

	public void ruota()
	{
		boolean oltreBordo = false;
		// log.info("ruotaSenzaControllo()");

		int matricePreRotazione[][];

		// salvo lo stato attuale della matricetta
		matricePreRotazione = this.matrice;
		int larghezzaPreRotazione = this.larghezza;
		int altezzaPreRotazione = this.altezza;

		// tento la rotazione
		Matrice.getInstance().setMatrice(this);
		Matrice.getInstance().rotazione(Rotazioni.SX);

		// setto la matrice ruotata
		this.matrice = Matrice.getInstance().getMatrice();

		// setto larghezza dopo rotazione se necessario
		this.larghezza = Matrice.getInstance().getLarghezza();

		// setto altezza dopo rotazione se necessario
		this.altezza = Matrice.getInstance().getAltezza();
		//
		// log.info("this.posx : " + this.posx);
		// log.info("this.larghezza : " + this.larghezza);
		// log.info("LARGHEZZA_GIOCO : " + Costanti.TavoloDaGioco.LARGHEZZA_GIOCO);
		if ((this.posx + this.larghezza) > (Costanti.TavoloDaGioco.LARGHEZZA_GIOCO + 1))
		{
			oltreBordo = true;
		}
		if ((this.posy + this.altezza) >= (Costanti.TavoloDaGioco.ALTEZZA_GIOCO + 1))
		{
			oltreBordo = true;
		}

		if (oltreBordo || !this.possoRuotare()) // non posso ruotare
		{
			this.matrice = matricePreRotazione;
			this.larghezza = larghezzaPreRotazione;
			this.altezza = altezzaPreRotazione;
		}

	}

	public void loadImg()
	{
		// carico img del mattoncino
		this.setImg(this.pa.loadImage(this.mattoncino.getNomeImg()));
	}

	public Mattoncino(MattonciniString mattoncino)
	{
		this(mattoncino, true);

	}

	public Mattoncino(MattonciniString mattoncino, boolean flag)
	{
		int numeroRotazioniDx;

		// setto altezza e larghezza mattoncino di default 30x30
		this.setAltezzaImg(Costanti.Sketch.ALTEZZA_CELLA);
		this.setLarghezzaImg(Costanti.Sketch.LARGHEZZA_CELLA);

		// setto il mattoncino
		this.mattoncino = mattoncino;

		// genoro la matrice associata ad T a partire dalla stringa
		this.matrice = this.generaMatrice(mattoncino.getStringa(), mattoncino.getLarghezza(), mattoncino.getAltezza());

		// genero una rotazione casuale
		if (flag == false)
		{
			numeroRotazioniDx = 0;
		}
		else
		{
			numeroRotazioniDx = this.generaRotazioneIniziale();
		}

		// numeroRotazioniDx = 0; // per test
		// setto larghezza prima di rotazione
		this.larghezza = mattoncino.getLarghezza();

		// setto altezza prima di rotazione
		this.altezza = mattoncino.getAltezza();

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

		// genero una posizione iniziale TOP=0 sull'asse x
		this.generaPosizioneIniziale();
	}

	private boolean possoAndareSX()
	{

		int tavolo[][] = TavoloDaGioco.getInstance().getMatrice();
		int coeff = 0;
		int xSuccessivoTavolo = this.posx - 1;
		boolean bRet = true;
		for (int y = 0; y < this.altezza; y++)
		{
			for (int x = 0; x < this.larghezza; x++)
			{
				coeff += this.matrice[x][y] * tavolo[xSuccessivoTavolo + x][this.posy + y]; // 1 è il muro
				if (coeff > 0)
				{
					bRet = false;
					break;
				}

			}
		}

		return bRet;
	}

	private boolean possoAndareDX()
	{
		int tavolo[][] = TavoloDaGioco.getInstance().getMatrice();
		int coeff = 0;
		int xSuccessivoTavolo = this.posx + 1;
		boolean bRet = true;
		for (int y = 0; y < this.altezza; y++)
		{
			for (int x = 0; x < this.larghezza; x++)
			{
				coeff += this.matrice[x][y] * tavolo[xSuccessivoTavolo + x][this.posy + y]; // 1 è il muro
				if (coeff > 0)
				{
					bRet = false;
					break;
				}
			}
		}

		return bRet;
	}

	private boolean possoAndareGiu()
	{
		int tavolo[][] = TavoloDaGioco.getInstance().getMatrice();
		int coeff = 0;
		int ySuccessivoTavolo = this.posy + 1;
		boolean bRet = true;
		for (int y = 0; y < this.altezza; y++)
		{
			for (int x = 0; x < this.larghezza; x++)
			{
				coeff += this.matrice[x][y] * tavolo[this.posx + x][ySuccessivoTavolo + y]; // 1 è il muro
				if (coeff > 0)
				{
					bRet = false;
					break;
				}

			}
		}
		return bRet;
	}

	private boolean possoRuotare()
	{
		int tavolo[][] = TavoloDaGioco.getInstance().getMatrice();
		int coeff = 0;
		int ySuccessivoTavolo = this.posy + 1; // 1 è il muro
		boolean bRet = true;
		for (int y = 0; y < this.altezza; y++)
		{
			for (int x = 0; x < this.larghezza; x++)
			{
				coeff += this.matrice[x][y] * tavolo[this.posx + x][ySuccessivoTavolo + y];
				if (coeff > 1)
				{
					bRet = false;
					break;
				}

			}
		}
		if (bRet == false)
		{
			log.info("Non puoi ruotare...ciaone !!!!");
		}

		return bRet;
	}

	public boolean muoviSX()
	{
		boolean bRet = false;
		if (this.possoAndareSX())
		{
			this.posx--;
			bRet = true;
		}
		else
		{
			bRet = false;
		}
		return bRet;
	}

	public boolean muoviDX()
	{
		boolean bRet = false;
		if (this.possoAndareDX())
		{
			this.posx++;
			bRet = true;
		}
		else
		{
			bRet = false;
		}
		return bRet;
	}

	public Mattoncino calcolaProiezione()
	{
		boolean bRet = this.muoviGiu();
		while (bRet)
		{
			bRet = this.muoviGiu();
		}

		// ora ho la proiezione
		return this;
	}

	public boolean muoviGiu()
	{
		boolean bRet = false;
		if (this.possoAndareGiu())
		{
			this.posy++;
			bRet = true;
		}

		return bRet;
	}

	public void stampa()
	{
		log.info("Larghezza : " + this.larghezza);
		log.info("altezza   : " + this.altezza);
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
		this.setPosx(getRandomNumberInRange(1, Costanti.TavoloDaGioco.LARGHEZZA_GIOCO - this.larghezza));

	}

	private static int getRandomNumberInRange(int min, int max)
	{

		if (min >= max)
		{
			throw new IllegalArgumentException("max must be greater than min");
		}

		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}

	protected int[][] generaMatrice(String famiglia, int dx, int dy)
	{
		int matrice[][] = new int[dx][dy];
		int pos = 0;
		for (int y = 0; y < dy; y++)
		{
			for (int x = 0; x < dx; x++)
			{
				matrice[x][y] = Integer.parseInt("" + famiglia.charAt(pos++)) * this.mattoncino.getTipo();
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

	public MattonciniString getMattoncino()
	{
		return this.mattoncino;
	}

	public void setMattoncino(MattonciniString mattoncino)
	{
		this.mattoncino = mattoncino;
	}

	public PApplet getPa()
	{
		return this.pa;
	}

	public void setPa(PApplet pa)
	{
		this.pa = pa;
	}

	public PImage getImg()
	{
		return this.img;
	}

	public void setImg(PImage img)
	{
		this.img = img;
	}

	public int getPosxAssoluta()
	{
		return this.posxAssoluta;
	}

	public void setPosxAssoluta(int posxAssoluta)
	{
		this.posxAssoluta = posxAssoluta;
	}

	public int getPosyAssoluta()
	{
		return this.posyAssoluta;
	}

	public void setPosyAssoluta(int posyAssoluta)
	{
		this.posyAssoluta = posyAssoluta;
	}

	public int getLarghezzaImg()
	{
		return this.larghezzaImg;
	}

	public void setLarghezzaImg(int larghezzaImg)
	{
		this.larghezzaImg = larghezzaImg;
	}

	public int getAltezzaImg()
	{
		return this.altezzaImg;
	}

	public void setAltezzaImg(int altezzaImg)
	{
		this.altezzaImg = altezzaImg;
	}

}
