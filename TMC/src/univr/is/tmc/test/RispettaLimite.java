package univr.is.tmc.test;

public class RispettaLimite extends Limite {
		
		public boolean controlla(int velocita){
			if( inizioLimite < velocita && fineLimite > velocita )
				return true;
			else
				return false;
		}
}
