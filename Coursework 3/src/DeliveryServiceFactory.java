public class DeliveryServiceFactory {
	
	private DeliveryService delivery; 
	
	public DeliveryServiceFactory() {
		
	}
	
	public DeliveryService getDeliveryService() {
		delivery = new MockDeliveryService();
		return this.delivery;
	}
}
