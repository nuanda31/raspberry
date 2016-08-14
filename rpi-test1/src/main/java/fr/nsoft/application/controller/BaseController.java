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

	@RequestMapping("/")
	public String index() {
		return "Rapsberry Pi 3 services";
	}
	
	@RequestMapping("/led")
	public String lightLed() {
		GpioController controller = null;
		try {
			controller = GpioFactory.getInstance();
			GpioPinDigitalOutput digitalOutput = controller.provisionDigitalOutputPin(RaspiPin.GPIO_01, "PIN_LED", PinState.HIGH);
			digitalOutput.setShutdownOptions(true, PinState.LOW);
			Thread.sleep(2000);
			digitalOutput.toggle();
			Thread.sleep(2000);
			digitalOutput.toggle();
			Thread.sleep(2000);
			digitalOutput.low();
		} catch (InterruptedException e) {
			System.out.println(e.getMessage());
		} finally {
			if (controller != null) {
				controller.shutdown();
			}
		}
		return "";
	}
}
