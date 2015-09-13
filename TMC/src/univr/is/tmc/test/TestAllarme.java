package univr.is.tmc.test;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class TestAllarme {

	@Test
	public void testIntoLimite(){
		RispettaLimite rispettaLimite = new RispettaLimite();
		rispettaLimite.setLimite(0, 130);
		boolean res = rispettaLimite.controlla(100);
		
		assertEquals(true,res);
	}
	
	@Test
	public void testSuperaLimite(){
		SuperoLimite superoLimite = new SuperoLimite();
		superoLimite.setLimite(0, 130);
		int res = superoLimite.differenza(145);
		
		assertEquals(15,res);
	}
}
