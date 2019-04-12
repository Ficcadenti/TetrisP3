package it.raffomafr.tetris.game;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import it.raffomafr.tetris.enumeration.MattonciniString;
import it.raffomafr.tetris.model.TavoloDaGioco;
import it.raffomafr.tetris.model.mattoncini.Mattoncino;
import it.raffomafr.tetris.utility.Costanti;
import processing.core.PApplet;
import processing.core.PImage;
import processing.sound.SoundFile;

public class Tetris extends PApplet
{

	private Mattoncino					mattoncinoCasuale;
	private int							accellerazione	= Costanti.Sketch.FRAME_LIVELLO_0;
	private static final Logger			log				= Logger.getLogger(Tetris.class);
	private static Map<Integer, PImage>	mapTetrisImg	= new HashMap<Integer, PImage>();
	private static SoundFile			file;

	public static void main(String[] args)
	{
		PApplet.main("it.raffomafr.tetris.game.Tetris");
	}

	public Mattoncino calcolaProiezione()
	{
		Mattoncino mattoncinoInProiezione = null;
		try
		{
			mattoncinoInProiezione = (Mattoncino) this.mattoncinoCasuale.clone();
			mattoncinoInProiezione = mattoncinoInProiezione.calcolaProiezione();
			// this.drawMattoncino(mattoncinoInProiezione);
		}
		catch (CloneNotSupportedException e)
		{
			log.error(e.getCause(), e);
		}
		return mattoncinoInProiezione;
	}

	@Override
	public void draw()
	{
		boolean bRet = true;

		this.drawTavoloDaGioco();

		Mattoncino mattoncinoInProiezione = this.calcolaProiezione();
		if (mattoncinoInProiezione != null)
		{
			this.drawProiezione(mattoncinoInProiezione);
		}
		this.drawMattoncino(this.mattoncinoCasuale);

		if ((this.frameCount % this.accellerazione) == 0)
		{
			bRet = this.mattoncinoCasuale.muoviGiu();
		}

		if (bRet == false)
		{
			TavoloDaGioco.getInstance().inserisciMattoncino(this.mattoncinoCasuale);
			this.mattoncinoCasuale = TavoloDaGioco.getInstance().generaMattoncino();
			TavoloDaGioco.getInstance().cancellaRighePiene();
			bRet = true;
		}
	}

	private void drawTavoloDaGioco()
	{

		TavoloDaGioco tetris = TavoloDaGioco.getInstance();
		int tavolo[][] = tetris.getMatrice();
		this.pushMatrix();
		for (int y = 0; y < Costanti.TavoloDaGioco.ALTEZZA; y++)
		{

			for (int x = 0; x < Costanti.TavoloDaGioco.LARGHEZZA; x++)
			{
				if (tavolo[x][y] == MattonciniString.VUOTO.getTipo())
				{
					this.fill(20, 20, 20);
					this.rect(x * Costanti.Sketch.LARGHEZZA_CELLA, y * Costanti.Sketch.ALTEZZA_CELLA, Costanti.Sketch.LARGHEZZA_CELLA, Costanti.Sketch.ALTEZZA_CELLA);
				}
				else
				{
					this.image(mapTetrisImg.get(tavolo[x][y]), x * Costanti.Sketch.LARGHEZZA_CELLA, y * Costanti.Sketch.ALTEZZA_CELLA);
				}

			}
		}
		this.popMatrix();
	}

	@Override
	public void settings()
	{
		this.size(Costanti.Sketch.LARGHEZZA, Costanti.Sketch.ALTEZZA);
	}

	public void caricaImg() // non mi piace, ma per velocità (contro ogni buon proposit) lo faccio:)
	{
		mapTetrisImg.put(new Integer(MattonciniString.MURO.getTipo()), this.loadImage(MattonciniString.MURO.getNomeImg()));
		mapTetrisImg.put(new Integer(MattonciniString.T.getTipo()), this.loadImage(MattonciniString.T.getNomeImg()));
		mapTetrisImg.put(new Integer(MattonciniString.I.getTipo()), this.loadImage(MattonciniString.I.getNomeImg()));
		mapTetrisImg.put(new Integer(MattonciniString.L.getTipo()), this.loadImage(MattonciniString.L.getNomeImg()));
		mapTetrisImg.put(new Integer(MattonciniString.J.getTipo()), this.loadImage(MattonciniString.J.getNomeImg()));
		mapTetrisImg.put(new Integer(MattonciniString.O.getTipo()), this.loadImage(MattonciniString.O.getNomeImg()));
		mapTetrisImg.put(new Integer(MattonciniString.S.getTipo()), this.loadImage(MattonciniString.S.getNomeImg()));
		mapTetrisImg.put(new Integer(MattonciniString.Z.getTipo()), this.loadImage(MattonciniString.Z.getNomeImg()));
		mapTetrisImg.put(new Integer(MattonciniString.PROIEZIONE.getTipo()), this.loadImage(MattonciniString.PROIEZIONE.getNomeImg()));
	}

	@Override
	public void setup()
	{
		this.background(20, 20, 20);

		this.caricaImg(); // carico le immagini base dei mattoncini

		// this.caricaSound(); // carico audio

		TavoloDaGioco tetris = TavoloDaGioco.getInstance();
		tetris.generaLivello(0);
		tetris.setPa(this);
		PropertyConfigurator.configure("log4j.properties");

		this.mattoncinoCasuale = tetris.generaMattoncino();
		this.mattoncinoCasuale.info();

		// try
		// {
		// this.mattoncinoInProiezione = (Mattoncino) this.mattoncinoCasuale.clone();
		// Mattoncino m = this.mattoncinoInProiezione.calcolaProiezione();
		// m.info();
		// this.drawMattoncino(m);
		// }
		// catch (CloneNotSupportedException e)
		// {
		// log.error(e.getCause(), e);
		// }
		//
		// tetris.inserisciMattoncino(this.mattoncinoInProiezione);
		// tetris.stampaTavolo();

		this.drawTavoloDaGioco();
		// this.drawGriglia();

	}

	private void caricaSound()
	{
		file = new SoundFile(this, "tileggeronelpensiero.mp3");
		file.amp(0.1f);
		file.play();
	}

	@Override
	public void keyPressed()
	{
		{
			if (this.keyCode == UP)
			{
				this.mattoncinoCasuale.ruota();
			}
			else if (this.keyCode == LEFT)
			{
				this.mattoncinoCasuale.muoviSX();
			}
			else if (this.keyCode == RIGHT)
			{
				this.mattoncinoCasuale.muoviDX();
			}
			else if (this.keyCode == DOWN)
			{
				this.accellerazione = 2;
			}
		}
	}

	@Override
	public void keyReleased()
	{
		if (this.keyCode == DOWN)
		{
			this.accellerazione = Costanti.Sketch.FRAME_LIVELLO_0;
		}
	}

	private void drawGriglia()
	{
		this.pushMatrix();
		this.stroke(100, 100, 100);
		for (int x = 0; x <= Costanti.Sketch.LARGHEZZA; x++)
		{
			this.line(x * Costanti.Sketch.LARGHEZZA_CELLA, 0, x * Costanti.Sketch.LARGHEZZA_CELLA, Costanti.Sketch.ALTEZZA);
		}

		for (int y = 0; y <= Costanti.Sketch.ALTEZZA; y++)
		{
			this.line(0, y * Costanti.Sketch.ALTEZZA_CELLA, Costanti.Sketch.LARGHEZZA, y * Costanti.Sketch.ALTEZZA_CELLA);
		}
		this.popMatrix();
	}

	public void drawProiezione(Mattoncino mattoncino)
	{
		int xPosM;
		int yPosM;
		int mLarghezza = mattoncino.getLarghezza();
		int mAltezza = mattoncino.getAltezza();

		this.pushMatrix();

		for (int y = 0; y < mAltezza; y++)
		{
			yPosM = (mattoncino.getPosy() + y) * Costanti.Sketch.ALTEZZA_CELLA;
			for (int x = 0; x < mLarghezza; x++)
			{
				if (mattoncino.getMatrice()[x][y] != 0)
				{
					xPosM = (mattoncino.getPosx() + x) * Costanti.Sketch.LARGHEZZA_CELLA;
					this.image(mapTetrisImg.get(MattonciniString.PROIEZIONE.getTipo()), xPosM, yPosM);
				}
			}
		}

		this.popMatrix();

	}

	public void drawMattoncino(Mattoncino mattoncino)
	{
		int xPosM;
		int yPosM;
		int mLarghezza = mattoncino.getLarghezza();
		int mAltezza = mattoncino.getAltezza();

		this.pushMatrix();

		for (int y = 0; y < mAltezza; y++)
		{
			yPosM = (mattoncino.getPosy() + y) * Costanti.Sketch.ALTEZZA_CELLA;
			for (int x = 0; x < mLarghezza; x++)
			{
				if (mattoncino.getMatrice()[x][y] != 0)
				{
					xPosM = (mattoncino.getPosx() + x) * Costanti.Sketch.LARGHEZZA_CELLA;
					this.image(mattoncino.getImg(), xPosM, yPosM);
				}
			}
		}

		this.popMatrix();

	}

}
