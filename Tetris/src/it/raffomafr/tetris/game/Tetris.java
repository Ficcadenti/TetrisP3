package it.raffomafr.tetris.game;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import it.raffomafr.tetris.controller.GestioneBottoni;
import it.raffomafr.tetris.controller.Statistiche;
import it.raffomafr.tetris.enumeration.BottoniGioco;
import it.raffomafr.tetris.enumeration.ImmaginiGioco;
import it.raffomafr.tetris.enumeration.LabelGioco;
import it.raffomafr.tetris.enumeration.MattonciniString;
import it.raffomafr.tetris.model.TavoloDaGioco;
import it.raffomafr.tetris.model.mattoncini.Mattoncino;
import it.raffomafr.tetris.utility.Costanti;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;
import processing.net.Client;

public class Tetris extends PApplet
{
	private String						clientUUID					= UUID.randomUUID().toString();
	private Mattoncino					mattoncinoCasuale;
	private Mattoncino					prossimoMattoncinoCasuale	= null;
	private int							accellerazione				= Costanti.Sketch.FRAME_LIVELLO_0;
	private static final Logger			log							= Logger.getLogger(Tetris.class);
	private static Map<Integer, PImage>	mapTetrisImg				= new HashMap<Integer, PImage>();
	private static List<Mattoncino>		listMattonciniGameOver		= new ArrayList<>();
	// private static SoundFile file;
	private static int					numRigheAbbattuteTotali;
	private boolean						gameOver					= false;
	private PImage						img_righe					= null;

	private Client						c;

	public static void main(String[] args)
	{
		PApplet.main("it.raffomafr.tetris.game.Tetris");
		PropertyConfigurator.configure("log4j.properties");
	}

	public void generaPuntiHeader(int i)
	{
		this.pushMatrix();
		for (int x = 0; x < i; x++)
		{
			this.stroke(this.random(255), this.random(255), this.random(255));
			this.strokeWeight(this.random(0, 4));
			this.point(this.random(0, Costanti.Sketch.LARGHEZZA), this.random(0, Costanti.Sketch.ALTEZZA_HEADER));
		}
		this.popMatrix();
		this.stroke(0, 0, 0);
		this.strokeWeight(1);
	}

	public void generaPuntiStatistiche(int i)
	{
		this.pushMatrix();
		for (int x = 0; x < i; x++)
		{
			this.stroke(this.random(255), this.random(255), this.random(255));
			this.strokeWeight(this.random(0, 4));
			this.point(Costanti.Sketch.LARGHEZZA + this.random(0, Costanti.Statistiche.LARGHEZZA), this.random(0, Costanti.Sketch.ALTEZZA_HEADER + Costanti.Sketch.ALTEZZA + Costanti.Sketch.ALTEZZA_FOOTER));
		}
		this.popMatrix();
		this.stroke(0, 0, 0);
		this.strokeWeight(1);
	}

	public void drawHeader()
	{
		this.pushMatrix();
		this.noStroke();
		this.fill(0, 0, 0);
		this.rect(0, 0, Costanti.Sketch.LARGHEZZA + Costanti.Statistiche.LARGHEZZA, Costanti.Sketch.ALTEZZA_HEADER);
		this.drawProssimoMattoncino(this.prossimoMattoncinoCasuale);
		this.popMatrix();
	}

	public void drawFooter()
	{
		this.pushMatrix();
		this.noStroke();
		this.fill(0, 0, 0);
		this.rect(0, (Costanti.Sketch.ALTEZZA_HEADER) + Costanti.Sketch.ALTEZZA, Costanti.Sketch.LARGHEZZA + Costanti.Statistiche.LARGHEZZA, Costanti.Sketch.ALTEZZA + Costanti.Sketch.ALTEZZA_FOOTER);
		this.popMatrix();
	}

	public void generaPuntiFooter(int i)
	{
		this.pushMatrix();
		for (int x = 0; x < i; x++)
		{
			this.stroke(this.random(255), this.random(255), this.random(255));
			this.strokeWeight(this.random(0, 4));
			this.point(this.random(0, Costanti.Sketch.LARGHEZZA), Costanti.Sketch.ALTEZZA_HEADER + Costanti.Sketch.ALTEZZA + this.random(0, Costanti.Sketch.ALTEZZA_FOOTER));
		}
		this.popMatrix();
		this.stroke(0, 0, 0);
		this.strokeWeight(1);
	}

	public Mattoncino calcolaProiezione()
	{
		Mattoncino mattoncinoInProiezione = null;
		try
		{
			mattoncinoInProiezione = (Mattoncino) this.mattoncinoCasuale.clone();
			mattoncinoInProiezione = mattoncinoInProiezione.calcolaProiezione();
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
		GestioneBottoni.getInstance().checkHover();
		this.drawHeader();
		this.drawFooter();

		if (this.gameOver == false)
		{

			// String msgInput = this.c.readString();
			// if (msgInput != null)
			// {
			// log.info("Ricevuto dal server: " + msgInput);
			// if (msgInput != null)
			// {
			// String[] listaInput = msgInput.split("::", 2);
			// String serverUUID = listaInput[0];
			//
			// if (!serverUUID.equals(this.clientUUID))
			// {
			// int numeroRigheAvversario = Integer.parseInt(listaInput[1]);
			// log.info("Client : " + serverUUID);
			// log.info("Righe abbattute : " + numeroRigheAvversario);
			//
			// for (int i = 0; i < numeroRigheAvversario; i++)
			// {
			// TavoloDaGioco.getInstance().inserisciRighePiene();
			// }
			// }
			// else
			// {
			// log.info("Il messaggio non è per me !!!!!!!");
			// }
			//
			// }
			// }

			boolean bRet = true;

			this.drawTavoloDaGioco();
			Statistiche.getInstance().drawStatistiche();
			Mattoncino mattoncinoInProiezione = this.calcolaProiezione();
			if (mattoncinoInProiezione != null)
			{
				this.drawProiezione(mattoncinoInProiezione);
			}
			this.drawMattoncinoNelTavolo(this.mattoncinoCasuale);

			if ((this.frameCount % this.accellerazione) == 0)
			{
				bRet = this.mattoncinoCasuale.muoviGiu();
			}

			if (bRet == false)
			{
				Statistiche.getInstance().calcolaStatistiche(this.mattoncinoCasuale);

				if (this.mattoncinoCasuale.getPosy() == 0)
				{
					this.gameOver = true;
				}

				TavoloDaGioco.getInstance().inserisciMattoncino(this.mattoncinoCasuale);

				this.mattoncinoCasuale = this.prossimoMattoncinoCasuale;
				this.prossimoMattoncinoCasuale = TavoloDaGioco.getInstance().generaMattoncino();

				int numeroRigheAbbattute = TavoloDaGioco.getInstance().cancellaRighePiene();
				if (numeroRigheAbbattute > 0)
				{
					numRigheAbbattuteTotali += numeroRigheAbbattute;
					// invia la riga al secondo giocatore
					String msgSend = this.clientUUID + "::" + numeroRigheAbbattute;
					log.info(msgSend);
					this.c.write(msgSend);

				}
				bRet = true;
			}

			this.drawPunteggio();
		}
		else
		{
			this.gameOver();
			this.drawPunteggio();
		}
	}

	public void drawPunteggio()
	{
		this.pushMatrix();
		this.img_righe.resize((this.img_righe.width / 2) + (Costanti.RigheAbbattute.DELTA_BARRETTA * numRigheAbbattuteTotali), this.img_righe.height);
		this.image(this.img_righe, ImmaginiGioco.RIGHEABBATTUTE.getPosX(), ImmaginiGioco.RIGHEABBATTUTE.getPosY());
		this.fill(255);
		this.textAlign(PConstants.LEFT);
		this.text(LabelGioco.RIGHEABBATTUTE.getDesc() + numRigheAbbattuteTotali, LabelGioco.RIGHEABBATTUTE.getPosX(), LabelGioco.RIGHEABBATTUTE.getPosY());
		this.popMatrix();
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
					this.stroke(0, 0, 0);
					this.rect((x * Costanti.Sketch.LARGHEZZA_CELLA), (Costanti.Sketch.ALTEZZA_HEADER) + (y * Costanti.Sketch.ALTEZZA_CELLA), Costanti.Sketch.LARGHEZZA_CELLA, Costanti.Sketch.ALTEZZA_CELLA);
					this.fill(20, 20, 20);
					this.rect((x * Costanti.Sketch.LARGHEZZA_CELLA), (Costanti.Sketch.ALTEZZA_HEADER) + (y * Costanti.Sketch.ALTEZZA_CELLA), Costanti.Sketch.LARGHEZZA_CELLA, Costanti.Sketch.ALTEZZA_CELLA);
				}
				else
				{
					this.image(mapTetrisImg.get(tavolo[x][y]), x * Costanti.Sketch.LARGHEZZA_CELLA, (Costanti.Sketch.ALTEZZA_HEADER) + (y * Costanti.Sketch.ALTEZZA_CELLA));
				}

			}
		}
		this.popMatrix();
	}

	@Override
	public void settings()
	{
		this.size(Costanti.Sketch.LARGHEZZA + Costanti.Statistiche.LARGHEZZA, Costanti.Sketch.ALTEZZA_HEADER + Costanti.Sketch.ALTEZZA + Costanti.Sketch.ALTEZZA_FOOTER);
	}

	public void caricaImg()
	{
		mapTetrisImg = new HashMap<Integer, PImage>();
		listMattonciniGameOver = new ArrayList<>();
		List<Class<?>> listaMattoncini = TavoloDaGioco.getInstance().getLista();
		int cont = 0;

		for (Class<?> m : listaMattoncini)
		{
			try
			{
				Constructor<?> constructor = m.getConstructor(boolean.class);
				Mattoncino clazz = (Mattoncino) constructor.newInstance(false);
				clazz.setPa(this);
				clazz.loadImg();
				clazz.setAltezzaImg(Costanti.GameOver.ALTEZZA_MATTONCINO);
				clazz.setLarghezzaImg(Costanti.GameOver.LARGHEZZA_MATTONCINO);
				clazz.setPosxAssoluta(550);
				clazz.setPosyAssoluta(220 + (cont * 70));
				listMattonciniGameOver.add(clazz);
				cont++;
				mapTetrisImg.put(new Integer(clazz.getMattoncino().getTipo()), clazz.getImg());
			}
			catch (InstantiationException | IllegalAccessException | NoSuchMethodException | SecurityException | IllegalArgumentException | InvocationTargetException e)
			{
				log.info(e.getMessage(), e);
			}
		}

		mapTetrisImg.put(new Integer(MattonciniString.MURO.getTipo()), this.loadImage(MattonciniString.MURO.getNomeImg()));
		mapTetrisImg.put(new Integer(MattonciniString.BLOCCO.getTipo()), this.loadImage(MattonciniString.BLOCCO.getNomeImg()));
		mapTetrisImg.put(new Integer(MattonciniString.PROIEZIONE.getTipo()), this.loadImage(MattonciniString.PROIEZIONE.getNomeImg()));

		Statistiche.getInstance().setMapTetrisImg(mapTetrisImg);
	}

	@Override
	public void setup()
	{
		log.info("Client UUID = " + this.clientUUID);
		// this.c = new Client(this, "192.168.100.5", 9999); // Replace with your
		// server’s IP and port

		this.cursor(CROSS);
		this.textFont(this.createFont("Gugi-Regular.ttf", Costanti.sizeFont, true), Costanti.sizeFont);

		Statistiche.getInstance().setPa(this);

		GestioneBottoni.getInstance().setPa(this);
		GestioneBottoni.getInstance().addBottone(BottoniGioco.SI);
		GestioneBottoni.getInstance().addBottone(BottoniGioco.NO);

		this.gameOver = false;
		numRigheAbbattuteTotali = 0;

		this.img_righe = this.loadImage(ImmaginiGioco.RIGHEABBATTUTE.getDesc());

		this.background(20, 20, 20);

		this.caricaImg(); // carico le immagini base dei mattoncini

		this.caricaSound(); // carico audio

		TavoloDaGioco tetris = TavoloDaGioco.getInstance();
		tetris.generaLivello(0);
		tetris.setPa(this);

		this.mattoncinoCasuale = tetris.generaMattoncino();
		this.prossimoMattoncinoCasuale = tetris.generaMattoncino();

		this.imageMode(CORNER);
		// this.drawTavoloDaGioco();
	}

	private void caricaSound()
	{
		// file = new SoundFile(this, "tileggeronelpensiero.mp3");
		// file.amp(0.1f);
		// file.play();
	}

	@Override
	public void keyPressed()
	{
		this.pausa();
		{
			// forzatura del gameover con tasto G
			final int key = this.keyCode;
			if (key == 'G')
			{
				this.gameOver();
			}
			if (key == 'I')
			{
				if (this.mattoncinoCasuale.getPosy() > 0)
				{
					this.mattoncinoCasuale.setPosy(this.mattoncinoCasuale.getPosy() - 1);
					TavoloDaGioco.getInstance().inserisciRighePiene();
				}
			}
		}

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

	public void gameOver()
	{
		this.gameOver = true;

		// sfondo grigio
		this.pushMatrix();
		this.filter(ERODE);
		this.popMatrix();

		// disegni il tuo gameover molto bello :)
		this.pushMatrix();
		this.fill(0);
		this.noStroke();
		this.rect(Costanti.Sketch.LARGHEZZA, Costanti.Sketch.ALTEZZA_HEADER, Costanti.Statistiche.LARGHEZZA, Costanti.Sketch.ALTEZZA);
		this.image(this.loadImage(ImmaginiGioco.GAMEOVER.getDesc(), ImmaginiGioco.GAMEOVER.getEstensione()), ImmaginiGioco.GAMEOVER.getPosX(), ImmaginiGioco.GAMEOVER.getPosY(), ImmaginiGioco.GAMEOVER.getLarghezza(),
				ImmaginiGioco.GAMEOVER.getAltezza());
		this.fill(0);
		this.rect(110, 500, 300, 150, 20);
		this.fill(255, 255, 0);
		this.centraTestoLabel(LabelGioco.SCRITTAGAMEOVER);
		this.centraTestoBtn(BottoniGioco.SI);
		this.centraTestoBtn(BottoniGioco.NO);
		this.fill(255);
		this.statisticheGameOver();
		GestioneBottoni.getInstance().sceltaGameOver();
		this.popMatrix();
	}

	private void statisticheGameOver()
	{
		this.text("Hai totalizzato : ", 540, 200);

		int cont = 0;
		for (Mattoncino m : listMattonciniGameOver)
		{
			cont++;
			this.drawMattoncinoNelloSkatch(m);
			this.text(Statistiche.getInstance().getStatistiche().get(m.getMattoncino()), 700, 180 + (cont * 70));

		}
	}

	private void centraTestoLabel(LabelGioco testo)
	{
		this.noFill();
		this.text(testo.getDesc(), testo.getPosX() + ((testo.getLarghezza() - this.textWidth(testo.getDesc())) / 2), testo.getPosY() + ((testo.getAltezza() + Costanti.sizeFont) / 2));
		this.stroke(255);
		this.rect(testo.getPosX(), testo.getPosY(), testo.getLarghezza(), testo.getAltezza());
	}

	private void centraTestoBtn(BottoniGioco testo)
	{
		this.noFill();
		this.text(testo.getDesc(), testo.getPosX() + ((testo.getLarghezza() - this.textWidth(testo.getDesc())) / 2), testo.getPosY() + ((testo.getAltezza() + Costanti.sizeFont) / 2));
		this.stroke(255);
		this.rect(testo.getPosX(), testo.getPosY(), testo.getLarghezza(), testo.getAltezza());
	}

	private void pausa()
	{
		final int pausa = this.keyCode;
		if (pausa == 'P')
		{
			if (this.looping)
			{
				this.noLoop();
				this.filter(GRAY);
				this.fill(255);
				this.image(this.loadImage(ImmaginiGioco.PAUSA.getDesc(), ImmaginiGioco.PAUSA.getEstensione()), ImmaginiGioco.PAUSA.getPosX(), ImmaginiGioco.PAUSA.getPosY(), ImmaginiGioco.PAUSA.getLarghezza(), ImmaginiGioco.PAUSA.getAltezza());
				this.text(LabelGioco.SCRITTAPAUSA.getDesc(), LabelGioco.SCRITTAPAUSA.getPosX(), LabelGioco.SCRITTAPAUSA.getPosY());
			}
			else
			{
				this.loop();
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
					this.image(mapTetrisImg.get(MattonciniString.PROIEZIONE.getTipo()), xPosM, (Costanti.Sketch.ALTEZZA_HEADER) + yPosM);
				}
			}
		}

		this.popMatrix();

	}

	public void drawMattoncinoNelTavolo(Mattoncino mattoncino)
	{
		int xPosM;
		int yPosM;
		int mLarghezza = mattoncino.getLarghezza();
		int mAltezza = mattoncino.getAltezza();

		this.pushMatrix();

		for (int y = 0; y < mAltezza; y++)
		{
			yPosM = (mattoncino.getPosy() + y) * mattoncino.getAltezzaImg();
			for (int x = 0; x < mLarghezza; x++)
			{
				if (mattoncino.getMatrice()[x][y] != 0)
				{
					xPosM = (mattoncino.getPosx() + x) * mattoncino.getLarghezzaImg();
					this.image(mattoncino.getImg(), xPosM, (Costanti.Sketch.ALTEZZA_HEADER) + yPosM);
				}
			}
		}

		this.popMatrix();

	}

	public void drawMattoncinoNelloSkatch(Mattoncino mattoncino)
	{
		int xPosM;
		int yPosM;
		int mLarghezza = mattoncino.getLarghezza();
		int mAltezza = mattoncino.getAltezza();

		this.pushMatrix();

		for (int y = 0; y < mAltezza; y++)
		{
			yPosM = mattoncino.getPosyAssoluta() + (y * mattoncino.getAltezzaImg());
			for (int x = 0; x < mLarghezza; x++)
			{
				if (mattoncino.getMatrice()[x][y] != 0)
				{
					xPosM = mattoncino.getPosxAssoluta() + (x * mattoncino.getLarghezzaImg());
					mattoncino.getImg().resize(mattoncino.getLarghezzaImg(), mattoncino.getAltezzaImg());
					this.image(mattoncino.getImg(), xPosM, yPosM);
				}
			}
		}

		this.popMatrix();

	}

	public void drawProssimoMattoncino(Mattoncino mattoncino)
	{
		if (mattoncino == null)
		{
			return;
		}
		int xPosM;
		int yPosM;
		int mLarghezza = mattoncino.getLarghezza();
		int mAltezza = mattoncino.getAltezza();

		this.pushMatrix();

		for (int y = 0; y < mAltezza; y++)
		{
			yPosM = (0 + y) * Costanti.Sketch.ALTEZZA_CELLA;
			for (int x = 0; x < mLarghezza; x++)
			{
				if (mattoncino.getMatrice()[x][y] != 0)
				{
					xPosM = (mattoncino.getPosx() + x) * Costanti.Sketch.LARGHEZZA_CELLA;
					this.image(mattoncino.getImg(), xPosM, 20 + yPosM);
				}
			}
		}
		// this.generaPuntiHeader(20);
		this.popMatrix();
	}

}
