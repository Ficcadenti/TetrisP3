package it.raffomafr.tetris.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;

import it.raffomafr.tetris.enumeration.MattonciniString;
import it.raffomafr.tetris.model.mattoncini.Mattoncino;
import it.raffomafr.tetris.model.mattoncini.MattoncinoI;
import it.raffomafr.tetris.model.mattoncini.MattoncinoJ;
import it.raffomafr.tetris.model.mattoncini.MattoncinoL;
import it.raffomafr.tetris.model.mattoncini.MattoncinoO;
import it.raffomafr.tetris.model.mattoncini.MattoncinoS;
import it.raffomafr.tetris.model.mattoncini.MattoncinoT;
import it.raffomafr.tetris.model.mattoncini.MattoncinoZ;
import it.raffomafr.tetris.utility.Costanti;
import processing.core.PApplet;

public class TavoloDaGioco
{
	private static final Logger		log		= Logger.getLogger(TavoloDaGioco.class);
	private static TavoloDaGioco	istanza	= null;
	private int						altezza;
	private int						larghezza;
	private int[][]					matrice;
	private List<Class<?>>			lista;
	private PApplet					pa;

	public static TavoloDaGioco getInstance()
	{
		if (istanza == null)
		{
			istanza = new TavoloDaGioco();
			istanza.larghezza = Costanti.TavoloDaGioco.LARGHEZZA;
			istanza.altezza = Costanti.TavoloDaGioco.ALTEZZA;
			istanza.matrice = new int[istanza.larghezza][istanza.altezza];
			istanza.lista = new ArrayList<>();
			istanza.creaMattoncini();
		}

		return istanza;
	}

	public void creaMattoncini()
	{
		this.lista.add(MattoncinoL.class);// 0
		this.lista.add(MattoncinoI.class);// 1
		this.lista.add(MattoncinoJ.class);// 2
		this.lista.add(MattoncinoS.class);// 3
		this.lista.add(MattoncinoZ.class);// 4
		this.lista.add(MattoncinoO.class);// 5
		this.lista.add(MattoncinoT.class);// 6
	}

	public Mattoncino generaMattoncino()
	{
		Mattoncino clazz = null;
		Random random = new Random();
		int nMattoncino = random.nextInt(this.lista.size());
		try
		{
			clazz = (Mattoncino) this.lista.get(nMattoncino).newInstance();
			clazz.setPa(this.pa);
			clazz.loadImg();
		}
		catch (InstantiationException | IllegalAccessException e)
		{
			log.error(e.getMessage(), e);
		}

		return clazz;
	}

	public List<Integer> checkRighePiene()
	{
		List<Integer> listaRighe = new ArrayList<>();
		int sum = 0;

		for (int y = 0; y < (this.altezza - 1); y++)
		{
			for (int x = 1; x < (this.larghezza - 1); x++)
			{
				if (this.matrice[x][y] > 0)
				{
					sum++;
				}
				// sum = sum + this.matrice[x][y];
			}
			if (sum == Costanti.TavoloDaGioco.LARGHEZZA_GIOCO)
			{
				listaRighe.add(y);
			}
			sum = 0;
		}

		return listaRighe;
	}

	public int cancellaRighePiene()
	{
		int iRet = 0;
		List<Integer> listaRighe = this.checkRighePiene();

		for (int i = 0; i < listaRighe.size(); i++)
		{
			for (int y = listaRighe.get(i); y > 0; y--)
			{
				for (int x = 1; x < (this.larghezza - 1); x++)
				{
					this.matrice[x][y] = this.matrice[x][y - 1];
				}
			}
		}

		if (listaRighe.size() > 0)
		{
			iRet = listaRighe.size();
			for (int x = 1; x < (this.larghezza - 1); x++)
			{
				this.matrice[x][0] = 0;
			}
		}
		return iRet;
	}

	public void azzeraMatrice()
	{
		for (int y = 0; y < this.matrice[0].length; y++)
		{
			for (int x = 0; x < this.matrice.length; x++)
			{
				this.matrice[x][y] = MattonciniString.VUOTO.getTipo();
			}
		}
	}

	public void generaLivello(int livello)
	{
		this.azzeraMatrice();
		this.generaMuro(livello);
	}

	public void generaMuro(int livello)
	{

		for (int y = 0; y < this.altezza; y++)
		{
			this.matrice[0][y] = MattonciniString.MURO.getTipo();
			this.matrice[this.larghezza - 1][y] = MattonciniString.MURO.getTipo();
		}

		for (int x = 0; x < this.larghezza; x++)
		{
			this.matrice[x][this.altezza - 1] = MattonciniString.MURO.getTipo();
		}
	}

	public void stampaTavolo()
	{
		for (int y = 0; y < this.matrice[0].length; y++)
		{
			System.out.print(String.format("Riga %02d ---> ", y));
			for (int x = 0; x < this.matrice.length; x++)
			{
				System.out.print(this.matrice[x][y]);
			}
			System.out.println();
		}

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

	public int[][] getMatrice()
	{
		return this.matrice;
	}

	public void setMatrice(int[][] matrice)
	{
		this.matrice = matrice;
	}

	public void inserisciMattoncino(Mattoncino mattoncino)
	{
		int mPosX = mattoncino.getPosx();
		int mPosY = mattoncino.getPosy();
		int mLarghezza = mattoncino.getLarghezza();
		int mAltezza = mattoncino.getAltezza();

		for (int y = 0; y < mAltezza; y++)
		{
			for (int x = 0; x < mLarghezza; x++)
			{
				this.matrice[mPosX + x][mPosY + y] = mattoncino.getMatrice()[x][y] | this.matrice[mPosX + x][mPosY + y];
			}
		}

	}

	public PApplet getPa()
	{
		return this.pa;
	}

	public void setPa(PApplet pa)
	{
		this.pa = pa;
	}
}
