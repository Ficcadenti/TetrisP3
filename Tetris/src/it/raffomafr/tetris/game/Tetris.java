package it.raffomafr.tetris.game;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import it.raffomafr.tetris.enumeration.Colore;
import it.raffomafr.tetris.model.TavoloDaGioco;
import it.raffomafr.tetris.model.mattoncini.Mattoncino;
import it.raffomafr.tetris.utility.Costanti;
import processing.core.PApplet;

public class Tetris extends PApplet
{

	private Mattoncino			mattoncinoCasuale;

	private static final Logger	log	= Logger.getLogger(Tetris.class);

	public static void main(String[] args)
	{
		PApplet.main("it.raffomafr.tetris.game.Tetris");
	}

	@Override
	public void draw()
	{

		this.drawTavoloDaGioco();
		this.drawGriglia();

		this.drawMattoncino(this.mattoncinoCasuale);
		boolean bRet = this.mattoncinoCasuale.muoviGiu();

		this.delay(200);
		if (bRet == false)
		{
			TavoloDaGioco.getInstance().inserisciMattoncino(this.mattoncinoCasuale);
			this.mattoncinoCasuale = TavoloDaGioco.getInstance().generaMattoncino();
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
				if (tavolo[x][y] == 0)
				{
					this.fill(0, 0, 0);
				}
				else
				{
					this.fill(0, 255, 0);
				}
				this.rect(x * Costanti.Sketch.LARGHEZZA_CELLA, y * Costanti.Sketch.ALTEZZA_CELLA, Costanti.Sketch.LARGHEZZA_CELLA, Costanti.Sketch.ALTEZZA_CELLA);
			}
		}
		this.popMatrix();
	}

	@Override
	public void settings()
	{
		this.size(Costanti.Sketch.LARGHEZZA, Costanti.Sketch.ALTEZZA);
	}

	@Override
	public void setup()
	{
		this.background(0);

		TavoloDaGioco tetris = TavoloDaGioco.getInstance();
		tetris.generaLivello(0);
		PropertyConfigurator.configure("log4j.properties");

		this.mattoncinoCasuale = tetris.generaMattoncino();
		this.mattoncinoCasuale.info();

		this.drawTavoloDaGioco();
		this.drawGriglia();

	}

	@Override
	public void keyPressed()
	{
		log.info(this.key);
		if (this.key == CODED)
		{
			if (this.keyCode == UP)
			{
				this.mattoncinoCasuale.ruota();
			}
			else if (this.keyCode == LEFT)
			{
				log.info("LEFT");
				this.mattoncinoCasuale.muoviSX();
			}
			else if (this.keyCode == RIGHT)
			{
				log.info("RIGHT");
				this.mattoncinoCasuale.muoviDX();
			}
		}
	}

	private void drawGriglia()
	{
		this.pushMatrix();
		this.stroke(100, 100, 100);
		for (int x = 0; x <= Costanti.Sketch.LARGHEZZA; x++)
		{
			this.line(x * Costanti.Sketch.LARGHEZZA_CELLA, 0, x * Costanti.Sketch.LARGHEZZA_CELLA, Costanti.Sketch.LARGHEZZA);
		}

		for (int y = 0; y <= Costanti.Sketch.ALTEZZA; y++)
		{
			this.line(0, y * Costanti.Sketch.ALTEZZA_CELLA, Costanti.Sketch.LARGHEZZA, y * Costanti.Sketch.ALTEZZA_CELLA);
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
		this.fill(255, 255, 0);

		for (int y = 0; y < mAltezza; y++)
		{
			yPosM = (mattoncino.getPosy() + y) * Costanti.Sketch.ALTEZZA_CELLA;
			for (int x = 0; x < mLarghezza; x++)
			{
				if (mattoncino.getMatrice()[x][y] != 0)
				{
					Colore c = mattoncino.getMattoncino().getColore();
					this.fill(c.getR(), c.getG(), c.getB());
					xPosM = (mattoncino.getPosx() + x) * Costanti.Sketch.LARGHEZZA_CELLA;
					this.rect(xPosM, yPosM, Costanti.Sketch.LARGHEZZA_CELLA, Costanti.Sketch.ALTEZZA_CELLA);
				}
			}
		}

		this.popMatrix();

	}

}
