package it.raffomafr.tetris.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import it.raffomafr.tetris.controller.GestioneBottoni;
import it.raffomafr.tetris.enumeration.BottoniGioco;
import it.raffomafr.tetris.enumeration.MattonciniString;
import it.raffomafr.tetris.enumeration.UtilityGioco;
import it.raffomafr.tetris.model.TavoloDaGioco;
import it.raffomafr.tetris.model.mattoncini.Mattoncino;
import it.raffomafr.tetris.utility.Costanti;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;
import processing.core.PVector;

public class Tetris extends PApplet
{

	private Mattoncino						mattoncinoCasuale;
	private Mattoncino						prossimoMattoncinoCasuale	= null;
	private int								accellerazione				= Costanti.Sketch.FRAME_LIVELLO_0;
	private static final Logger				log							= Logger.getLogger(Tetris.class);
	private static Map<Integer, PImage>		mapTetrisImg				= new HashMap<Integer, PImage>();
	// private static SoundFile file;
	private static int						numRigheAbbattuteTotali;
	private boolean							gameOver					= false;
	private ParticleSystem					ps;
	private Map<MattonciniString, Integer>	statistiche					= null;
	private PImage							img_stat;
	private PImage							img_righe;

	public static void main(String[] args)
	{
		PApplet.main("it.raffomafr.tetris.game.Tetris");
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

	public void drawFooter()
	{
		this.pushMatrix();
		this.fill(0, 0, 0);
		this.rect(0, (Costanti.Sketch.ALTEZZA_HEADER) + Costanti.Sketch.ALTEZZA, Costanti.Sketch.LARGHEZZA, Costanti.Sketch.ALTEZZA + Costanti.Sketch.ALTEZZA_FOOTER);
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
		GestioneBottoni.getInstance().checkHover();
		this.drawFooter();
		if (this.gameOver == false)
		{
			boolean bRet = true;

			this.drawTavoloDaGioco();
			this.drawStatistiche();
			// this.ps.addParticle();
			// this.ps.run();
			Mattoncino mattoncinoInProiezione = this.calcolaProiezione();
			if (mattoncinoInProiezione != null)
			{
				this.drawProiezione(mattoncinoInProiezione);
			}
			this.drawMattoncino(this.mattoncinoCasuale);
			this.drawProssimoMattoncino(this.prossimoMattoncinoCasuale);

			if ((this.frameCount % this.accellerazione) == 0)
			{
				bRet = this.mattoncinoCasuale.muoviGiu();
			}

			if (bRet == false)
			{
				// this.drawSatistiche();
				this.calcolaStatistiche();

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

	private void calcolaStatistiche()
	{
		Integer p = this.statistiche.get(this.mattoncinoCasuale.getMattoncino());
		if (p == null)
		{
			p = new Integer(1);
		}
		else
		{
			p = p + 1; // incremento le statistiche
		}
		this.statistiche.put(this.mattoncinoCasuale.getMattoncino(), p);
	}

	public void drawPunteggio()
	{
		this.pushMatrix();
		this.img_righe = this.loadImage("mattoncinoBarra.jpg");
		this.img_righe.resize((this.img_righe.width / 2) + (Costanti.RigheAbbattute.DELTA_BARRETTA * numRigheAbbattuteTotali), this.img_righe.height);
		this.image(this.img_righe, Costanti.RigheAbbattute.POS_X_BARRA, Costanti.RigheAbbattute.POS_Y_BARRA);
		this.fill(255);
		this.textAlign(PConstants.LEFT);
		this.text(Costanti.RigheAbbattute.DESC + numRigheAbbattuteTotali, Costanti.RigheAbbattute.POS_X, Costanti.RigheAbbattute.POS_Y);
		this.popMatrix();
	}

	public void drawStatistiche()
	{
		int cont = 1;

		this.pushMatrix();
		this.fill(0, 0, 0);
		this.rect(Costanti.Sketch.LARGHEZZA, 0, Costanti.Statistiche.LARGHEZZA, Costanti.Sketch.ALTEZZA_HEADER + Costanti.Sketch.ALTEZZA + Costanti.Sketch.ALTEZZA_FOOTER);
		// this.generaPuntiStatistiche(20);
		this.fill(255);

		Set<MattonciniString> mattoncini = this.statistiche.keySet();
		for (MattonciniString m : mattoncini)
		{
			try
			{
				this.img_stat = (PImage) mapTetrisImg.get(m.getTipo()).clone();
				this.img_stat.resize(this.img_stat.width, this.img_stat.height + (Costanti.Statistiche.DELTA_BARRETTA * this.statistiche.get(m)));
				this.image(this.img_stat, (Costanti.Sketch.LARGHEZZA - 20) + (Costanti.Statistiche.INTERVALLO_BARRETTE * cont), Costanti.Statistiche.ALTEZZA - (Costanti.Statistiche.DELTA_BARRETTA * this.statistiche.get(m)));
			}
			catch (CloneNotSupportedException e)
			{
				log.info(e.getMessage(), e);
			}
			cont++;
		}
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

	public void caricaImg() // non mi piace, ma per velocit� (contro ogni buon proposit) lo faccio:)
	{
		mapTetrisImg = new HashMap<Integer, PImage>();
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
		PropertyConfigurator.configure("log4j.properties");
		this.textFont(this.createFont("Gugi-Regular.ttf", Costanti.sizeFont, true), Costanti.sizeFont);
		this.azzeraStatistiche();
		this.cursor(CROSS);
		GestioneBottoni.getInstance().setPa(this);
		GestioneBottoni.getInstance().addBottone(BottoniGioco.SI);
		GestioneBottoni.getInstance().addBottone(BottoniGioco.NO);

		this.gameOver = false;
		numRigheAbbattuteTotali = 0;

		// this.ps = new ParticleSystem(new PVector(this.width / 2, 50), this);
		this.background(20, 20, 20);

		this.caricaImg(); // carico le immagini base dei mattoncini

		this.caricaSound(); // carico audio

		TavoloDaGioco tetris = TavoloDaGioco.getInstance();
		tetris.generaLivello(0);
		tetris.setPa(this);

		this.mattoncinoCasuale = tetris.generaMattoncino();
		this.prossimoMattoncinoCasuale = tetris.generaMattoncino();

		this.imageMode(CORNER);
		this.drawTavoloDaGioco();
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
			final int gameover = this.keyCode;
			if (gameover == 'G')
			{
				this.gameOver();
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
		this.pushMatrix();
		this.fill(0);
		this.noStroke();
		this.rect(510, 0, this.width, this.height);
		this.gameOver = true;
		this.image(this.loadImage(UtilityGioco.GAMEOVER.getDesc(), UtilityGioco.GAMEOVER.getEstensione()), UtilityGioco.GAMEOVER.getPosX(), UtilityGioco.GAMEOVER.getPosY(), UtilityGioco.GAMEOVER.getLarghezza(), UtilityGioco.GAMEOVER.getAltezza());
		this.fill(0);
		this.rect(110, 500, 300, 150, 20);
		this.fill(255, 255, 0);
		this.centraTesto(BottoniGioco.SCRITTAGAMEOVER);
		this.centraTesto(BottoniGioco.SI);
		this.centraTesto(BottoniGioco.NO);
		this.fill(255);
		this.statisticheGameOver();
		GestioneBottoni.getInstance().sceltaGameOver();
		this.popMatrix();
	}

	private void statisticheGameOver()
	{
		this.text("Hai totalizzato : ", 540, 250);
		this.image(this.loadImage("gameOver/mattoncino_i.png"), 540, 300, 50, 20);
		this.text(this.statistiche.get(MattonciniString.I), 600, 320);
		this.image(this.loadImage("gameOver/mattoncino_l.png"), 540, 350, 50, 30);
		this.text(this.statistiche.get(MattonciniString.L), 600, 370);
		this.image(this.loadImage("gameOver/mattoncino_j.png"), 540, 400, 50, 30);
		this.text(this.statistiche.get(MattonciniString.J), 600, 420);
		this.image(this.loadImage("gameOver/mattoncino_o.png"), 540, 450, 30, 30);
		this.text(this.statistiche.get(MattonciniString.O), 600, 470);
		this.image(this.loadImage("gameOver/mattoncino_s.png"), 540, 500, 50, 30);
		this.text(this.statistiche.get(MattonciniString.S), 600, 520);
		this.image(this.loadImage("gameOver/mattoncino_t.png"), 540, 550, 50, 30);
		this.text(this.statistiche.get(MattonciniString.T), 600, 570);
		this.image(this.loadImage("gameOver/mattoncino_z.png"), 540, 600, 50, 30);
		this.text(this.statistiche.get(MattonciniString.Z), 600, 620);
	}

	private void centraTesto(BottoniGioco testo)
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
				this.image(this.loadImage(UtilityGioco.PAUSA.getDesc(), UtilityGioco.PAUSA.getEstensione()), UtilityGioco.PAUSA.getPosX(), UtilityGioco.PAUSA.getPosY(), UtilityGioco.PAUSA.getLarghezza(), UtilityGioco.PAUSA.getAltezza());
				this.text(UtilityGioco.SCRITTAPAUSA.getDesc(), UtilityGioco.SCRITTAPAUSA.getPosX(), UtilityGioco.SCRITTAPAUSA.getPosY());
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
					this.image(mapTetrisImg.get(MattonciniString.PROIEZIONE.getTipo()), xPosM, (Costanti.Sketch.ALTEZZA_HEADER) + yPosM);
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
					this.image(mattoncino.getImg(), xPosM, (Costanti.Sketch.ALTEZZA_HEADER) + yPosM);
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

		this.fill(0, 0, 0);
		this.rect(0, 0, Costanti.Sketch.LARGHEZZA, Costanti.Sketch.ALTEZZA_HEADER);

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

class ParticleSystem
{

	ArrayList<Particle>	particles;
	PVector				origin;
	PApplet				pa;

	ParticleSystem(PVector position, PApplet pa)
	{
		this.pa = pa;
		this.origin = position.copy();
		this.particles = new ArrayList<Particle>();
	}

	void addParticle()
	{
		this.particles.add(new Particle(this.origin, this.pa));
	}

	void run()
	{
		for (int i = this.particles.size() - 1; i >= 0; i--)
		{
			Particle p = this.particles.get(i);
			p.run();
			if (p.isDead())
			{
				this.particles.remove(i);
			}
		}
	}
}

class Particle
{

	PVector	position;
	PVector	velocity;
	PVector	acceleration;
	float	lifespan;
	PApplet	pa;

	Particle(PVector l, PApplet pa)
	{
		this.pa = pa;
		this.acceleration = new PVector(0, 0.01f);
		this.velocity = new PVector(this.pa.random(-1, 1), this.pa.random(-2, 0));
		this.position = l.copy();
		this.lifespan = 255.0f;
	}

	void run()
	{
		this.update();
		this.display();
	}

	void update()
	{
		this.velocity.add(this.acceleration);
		this.position.add(this.velocity);
		this.lifespan -= 1.0;
	}

	// Method to display
	void display()
	{
		this.pa.stroke(255, this.lifespan);
		this.pa.fill(255, this.lifespan);
		this.pa.ellipse(this.position.x, this.position.y, 4, 4);
	}

	// Is the particle still useful?
	boolean isDead()
	{
		if (this.lifespan < 0.0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}
