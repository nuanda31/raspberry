package fr.nsoft.application.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

@RestController
public class BaseController {
	
	private GpioPinDigitalOutput gpio01Pin = null;
	private ButtonController buttonController = null;

	@RequestMapping("/")
	public String index() {
		return "Rapsberry Pi 3 services";
	}
	
	@RequestMapping("/led")
	public String lightLed() {
		GpioController controller = null;
		try {
//			if (gpio01Pin == null) {
				controller = GpioFactory.getInstance();
				gpio01Pin = controller.provisionDigitalOutputPin(RaspiPin.GPIO_01, "PIN_LED", PinState.HIGH);
//			}
			gpio01Pin.setShutdownOptions(true, PinState.LOW);
			Thread.sleep(2000);
			gpio01Pin.toggle();
			Thread.sleep(2000);
			gpio01Pin.toggle();
			Thread.sleep(2000);
			gpio01Pin.low();
		} catch (InterruptedException e) {
			System.out.println(e.getMessage());
		} finally {
			if (controller != null) {
				controller.shutdown();
				controller.unprovisionPin(gpio01Pin);
			}
		}
		return "OK";
	}
	
	@RequestMapping("/button")
	public String updateButton() {
		String buttonStat = "Button : Off";
		
		if (buttonController == null) {
			buttonController = new ButtonController();
			buttonStat = "Button : On";
		} else {
			buttonController.shutdown();
			buttonController = null;
			buttonStat = "Button : Off";
		}
		
		return buttonStat;
	}
	
}
