/**
 * Copyright 2013 Petros Pissias.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jlcf.example.advancedcalculator;

import org.apache.log4j.Logger;
import org.jlcf.core.annotation.InitMethod;
import org.jlcf.core.annotation.Property;
import org.jlcf.core.annotation.Receptacle;

/**
 * @author Petros Pissias
 * 
 */
public class NumberCalculator implements IBatteryEventCallback,
		INumberCalculator {

	private final IMultiplication multComponent;
	private final IAddition addComponent;
	private final IDivision divComponent;
	private final ISubtraction subComponent;
	private final IBattery batteryComponent;

	private final String version;

	private final Logger logger = Logger.getLogger(getClass());

	public NumberCalculator(
			@Receptacle(name = "multiplication") IMultiplication multComponent,
			@Receptacle(name = "addition") IAddition addComponent,
			@Receptacle(name = "division") IDivision divComponent,
			@Receptacle(name = "subtraction") ISubtraction subComponent,
			@Receptacle(name = "battery") IBattery batteryComponent,
			@Property(name = "version") String version)

	{
		this.multComponent = multComponent;
		this.addComponent = addComponent;
		this.divComponent = divComponent;
		this.subComponent = subComponent;
		this.batteryComponent = batteryComponent;

		this.version = version;
	}

	@Override
	public Double doCalculation(String expression) throws Exception {
		// it is assumed that the expression string contains something in the
		// form of X<operator>Y

		if (expression.contains("+")) { // get numbers and call addition
										// component
			// get the numbers
			double a = Double.parseDouble(expression.substring(0,
					expression.indexOf("+")));
			double b = Double.parseDouble(expression.substring(
					expression.indexOf("+") + 1, expression.length()));

			// get the receptacle and call the method on the other component
			return addComponent.add(a, b);

		} else if (expression.contains("-")) { // get numbers and call
												// subtraction component
			// get the numbers
			double a = Double.parseDouble(expression.substring(0,
					expression.indexOf("+")));
			double b = Double.parseDouble(expression.substring(
					expression.indexOf("+") + 1, expression.length()));

			// get the receptacle and call the method on the other component
			return subComponent.subtract(a, b);

		} else if (expression.contains("*")) {// get numbers and call
												// subtraction component
			// get the numbers
			double a = Double.parseDouble(expression.substring(0,
					expression.indexOf("+")));
			double b = Double.parseDouble(expression.substring(
					expression.indexOf("+") + 1, expression.length()));

			// get the receptacle and call the method on the other component
			return multComponent.multiply(a, b);

		} else if (expression.contains("/")) {
			// get the numbers
			double a = Double.parseDouble(expression.substring(0,
					expression.indexOf("+")));
			double b = Double.parseDouble(expression.substring(
					expression.indexOf("+") + 1, expression.length()));

			// get the receptacle and call the method on the other component
			return divComponent.divide(a, b);
		} else {
			throw new Exception("Cannot parse exception");
		}
	}

	@Override
	public void receiveBatteryUpdate(float batteryLevel) {
		logger.info("received battery update:" + batteryLevel);
	}

	@InitMethod
	public void init() {
		logger.info("Init method called. Registering with the battery component");
		batteryComponent.register();
		/*
		 * new Thread() { public void run() { batteryComponent.register(); }
		 * }.start();
		 */
	}
}
