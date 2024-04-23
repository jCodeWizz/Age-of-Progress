package dev.codewizz.modding.events;

import dev.codewizz.world.settlement.Settlement;

public class CreateSettlementEvent extends Event {

	private Settlement settlement;
	
	public CreateSettlementEvent(Settlement settlement) {
		this.settlement = settlement;
	}
	
	public Settlement getSettlement() {
		return settlement;
	}
}
