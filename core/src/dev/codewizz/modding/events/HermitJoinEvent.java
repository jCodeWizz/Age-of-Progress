package dev.codewizz.modding.events;

import dev.codewizz.world.objects.hermits.Hermit;
import dev.codewizz.world.settlement.Settlement;

public class HermitJoinEvent extends Event {

	private Hermit hermit;
	private Settlement settlement;
	
	public HermitJoinEvent(Hermit hermit, Settlement settlement) {
		this.hermit = hermit;
		this.settlement = settlement;
	}

	public Hermit getHermit() {
		return hermit;
	}

	public Settlement getSettlement() {
		return settlement;
	}
}
