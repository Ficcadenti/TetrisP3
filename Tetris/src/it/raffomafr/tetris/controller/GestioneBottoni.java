package it.raffomafr.tetris.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import it.raffomafr.tetris.enumeration.BottoniGioco;
import processing.core.PApplet;

public class GestioneBottoni
{
	private PApplet					pa;
	private List<BottoniGioco>		listaBottoni	= new ArrayList();
	private static GestioneBottoni	istanza			= null;
	private static final Logger		log				= Logger.getLogger(GestioneBottoni.class);

	private GestioneBottoni()
	{
	}

	public static GestioneBottoni getInstance()
	{
		if (istanza == null)
		{
			istanza = new GestioneBottoni();
		}
		return istanza;
	}

	public void addBottone(BottoniGioco bottone)
	{
		this.listaBottoni.add(bottone);
	}

	public void sceltaGameOver()
	{

		String btnScelto = this.getBottonePremuto();
		if (btnScelto.equals(BottoniGioco.SI.getDesc()))
		{
			this.pa.setup();
		}
		else if (btnScelto.equals(BottoniGioco.NO.getDesc()))
		{
			this.pa.exit();
		}
	}

	public String getBottonePremuto()
	{
		if (this.pa.mousePressed == true)
		{
			for (BottoniGioco b : this.listaBottoni)
			{
				if (this.hover(b))
				{
					return b.getDesc();
				}
			}
		}
		return "";
	}

	private boolean hover(BottoniGioco bottone)
	{
		boolean bRet = false;

		if ((this.pa.mouseX >= bottone.getPosX()) && (this.pa.mouseX <= (bottone.getPosX() + bottone.getLarghezza())) && (this.pa.mouseY >= bottone.getPosY()) && (this.pa.mouseY <= (bottone.getPosY() + bottone.getAltezza())))
		{
			bRet = true;
		}

		return bRet;
	}

	public void checkHover()
	{
		boolean bHover = false;
		for (BottoniGioco b : this.listaBottoni)
		{
			if (this.hover(b))
			{
				bHover = true;
				break;
			}
		}
		if (bHover == true)
		{
			this.pa.cursor(this.pa.HAND);
		}
		else
		{
			this.pa.cursor(this.pa.CROSS);
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
