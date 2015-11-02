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
import org.jlcf.core.annotation.Receptacle;

/**
 * @author Petros Pissias
 * 
 */
public class CalculatorUser {

	private final INumberCalculator calculator;

	private final Logger logger = Logger.getLogger(getClass());

	public CalculatorUser(
			@Receptacle(name = "calculator") INumberCalculator calculatorComponent) {
		calculator = calculatorComponent;
	}

	@InitMethod
	public void init() {
		// start a thread that performs calculations periodically
		new Thread() {
			public void run() {
				int i = 1;
				while (!interrupted()) {
					try {
						Thread.sleep(6000);
					} catch (InterruptedException e) {

					}
					try {
						logger.info("CalculatorUser: performing calculation:1+1");
						calculator.doCalculation((i++) + "+1");
					} catch (Exception e) {

					}
				}
			}
		}.start();
	}
}
