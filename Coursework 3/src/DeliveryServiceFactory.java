public class DeliveryServiceFactory {
	
	private DeliveryService delivery = new MockDeliveryService();
	
	public DeliveryService getDeliveryService() {
		return this.delivery;
	}
}
