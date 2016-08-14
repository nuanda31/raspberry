package fr.nsoft.application.controller;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

public class ButtonController {

	private GpioPinDigitalOutput gpio01Pin = null;
	private GpioPinDigitalInput gpio02Pin = null;
	private GpioController controller = null;

	public ButtonController() {
		controller = GpioFactory.getInstance();
		gpio01Pin = controller.provisionDigitalOutputPin(RaspiPin.GPIO_01, "PIN_LED", PinState.HIGH);
		gpio01Pin.setShutdownOptions(true, PinState.LOW);
		gpio02Pin = controller.provisionDigitalInputPin(RaspiPin.GPIO_02, PinPullResistance.PULL_DOWN);
		gpio02Pin.setShutdownOptions(true);
		gpio02Pin.addListener(new GpioPinListenerDigital() {
			
			@Override
			public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent arg0) {
				try {
					gpio01Pin.toggle();
					Thread.sleep(2000);
					gpio01Pin.toggle();
					Thread.sleep(2000);
					gpio01Pin.low();
				} catch (InterruptedException e) {
					System.out.println(e.getMessage());
				}
			}
		});
	}
	
	public void shutdown() {
		if (controller != null) {
			controller.shutdown();
			if (gpio01Pin != null) {
				controller.unprovisionPin(gpio01Pin);
			}
			if (gpio02Pin != null) {
				controller.unprovisionPin(gpio02Pin);
			}
		}
	}
}
