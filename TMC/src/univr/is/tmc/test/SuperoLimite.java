package univr.is.tmc.test;

public class SuperoLimite extends Limite {
		
		public int differenza(int velocita){
			if ( velocita >= fineLimite )
				return velocita - fineLimite;
			return -1;
		}
}