package it.raffomafr.tetris.utility;

public class Costanti
{
	public static float sizeFont = 15;

	public class TavoloDaGioco
	{
		public static final int	LARGHEZZA_GIOCO	= 15;
		public static final int	ALTEZZA_GIOCO	= 20;
		public static final int	LARGHEZZA		= LARGHEZZA_GIOCO + 2;
		public static final int	ALTEZZA			= ALTEZZA_GIOCO + 1;
	}

	public class Sketch
	{
		public static final int	ALTEZZA_HEADER	= 100;
		public static final int	ALTEZZA_FOOTER	= 50;
		public static final int	LARGHEZZA_CELLA	= 30;
		public static final int	ALTEZZA_CELLA	= 30;
		public static final int	FRAME_LIVELLO_0	= 30;
		public static final int	LARGHEZZA		= TavoloDaGioco.LARGHEZZA * LARGHEZZA_CELLA;
		public static final int	ALTEZZA			= TavoloDaGioco.ALTEZZA * ALTEZZA_CELLA;
	}

	public class Statistiche
	{
		public static final int	LARGHEZZA			= 400;
		public static final int	ALTEZZA				= Sketch.ALTEZZA;
		public static final int	INTERVALLO_BARRETTE	= 50;
		public static final int	DELTA_BARRETTA		= 10;
	}

	public class RigheAbbattute
	{
		public static final int DELTA_BARRETTA = 10;
	}

}
