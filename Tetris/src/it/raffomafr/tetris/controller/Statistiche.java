package it.raffomafr.tetris.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import it.raffomafr.tetris.enumeration.MattonciniString;
import it.raffomafr.tetris.model.mattoncini.Mattoncino;
import it.raffomafr.tetris.utility.Costanti;
import processing.core.PApplet;
import processing.core.PImage;

public class Statistiche
{
	private PApplet							pa;
	private static Statistiche				istanza			= null;
	private Map<MattonciniString, Integer>	statistiche		= null;
	private static final Logger				log				= Logger.getLogger(Statistiche.class);
	private PImage							img_stat		= null;
	private Map<Integer, PImage>			mapTetrisImg	= null;

	private Statistiche()
	{
	}

	public static Statistiche getInstance()
	{
		if (istanza == null)
		{
			istanza = new Statistiche();
			istanza.azzeraStatistiche();
		}
		return istanza;
	}

	public void drawStatistiche()
	{
		int cont = 1;

		this.pa.pushMatrix();
		this.pa.fill(0, 0, 0);
		this.pa.rect(Costanti.Sketch.LARGHEZZA, Costanti.Sketch.ALTEZZA_HEADER, Costanti.Statistiche.LARGHEZZA, Costanti.Sketch.ALTEZZA);
		// this.generaPuntiStatistiche(20);
		this.pa.fill(255);

		Set<MattonciniString> mattoncini = this.statistiche.keySet();
		for (MattonciniString m : mattoncini)
		{
			try
			{
				this.img_stat = (PImage) this.mapTetrisImg.get(m.getTipo()).clone();
				this.img_stat.resize(this.img_stat.width, this.img_stat.height + (Costanti.Statistiche.DELTA_BARRETTA * this.statistiche.get(m)));
				this.pa.image(this.img_stat, ((Costanti.Sketch.LARGHEZZA - Costanti.Statistiche.INTERVALLO_BARRETTE) + Costanti.Sketch.LARGHEZZA_CELLA) + (Costanti.Statistiche.INTERVALLO_BARRETTE * cont),
						((Costanti.Sketch.ALTEZZA_HEADER + Costanti.Statistiche.ALTEZZA) - Costanti.Sketch.ALTEZZA_CELLA) - (Costanti.Statistiche.DELTA_BARRETTA * this.statistiche.get(m)));
			}
			catch (CloneNotSupportedException e)
			{
				log.info(e.getMessage(), e);
			}
			cont++;
		}
		this.pa.popMatrix();
	}

	public void calcolaStatistiche(Mattoncino mattoncinoCasuale)
	{
		Integer p = this.statistiche.get(mattoncinoCasuale.getMattoncino());
		if (p == null)
		{
			p = new Integer(1);
		}
		else
		{
			p = p + 1; // incremento le statistiche
		}
		this.statistiche.put(mattoncinoCasuale.getMattoncino(), p);
	}

	public void azzeraStatistiche()
	{
		this.statistiche = new HashMap<>();
		this.statistiche.put(MattonciniString.T, new Integer(0));
		this.statistiche.put(MattonciniString.I, new Integer(0));
		this.statistiche.put(MattonciniString.L, new Integer(0));
		this.statistiche.put(MattonciniString.J, new Integer(0));
		this.statistiche.put(MattonciniString.O, new Integer(0));
		this.statistiche.put(MattonciniString.S, new Integer(0));
		this.statistiche.put(MattonciniString.Z, new Integer(0));
	}

	public PApplet getPa()
	{
		return this.pa;
	}

	public void setPa(PApplet pa)
	{
		this.pa = pa;
	}

	public Map<MattonciniString, Integer> getStatistiche()
	{
		return this.statistiche;
	}

	public void setStatistiche(Map<MattonciniString, Integer> statistiche)
	{
		this.statistiche = statistiche;
	}

	public Map<Integer, PImage> getMapTetrisImg()
	{
		return this.mapTetrisImg;
	}

	public void setMapTetrisImg(Map<Integer, PImage> mapTetrisImg)
	{
		this.mapTetrisImg = mapTetrisImg;
	}
}
