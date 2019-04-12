package it.raffomafr.tetris.utility;

public class Costanti
{
	public class TavoloDaGioco
	{
		public static final int	LARGHEZZA_GIOCO	= 15;
		public static final int	ALTEZZA_GIOCO	= 20;
		public static final int	LARGHEZZA		= LARGHEZZA_GIOCO + 2;
		public static final int	ALTEZZA			= ALTEZZA_GIOCO + 1;
	}

	public class Sketch
	{
		public static final int	LARGHEZZA_CELLA	= 20;
		public static final int	ALTEZZA_CELLA	= 20;
		public static final int	FRAME_LIVELLO_0	= 30;
		public static final int	LARGHEZZA		= TavoloDaGioco.LARGHEZZA * LARGHEZZA_CELLA;
		public static final int	ALTEZZA			= TavoloDaGioco.ALTEZZA * ALTEZZA_CELLA;
	}

}
