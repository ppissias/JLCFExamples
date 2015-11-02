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
import org.apache.log4j.PropertyConfigurator;
import org.jlcf.core.IJLCFContainer;
import org.jlcf.core.JLCFContainer;

/**
 * @author Petros Pissias
 * 
 */
public class AdvancedCalculatorExample {

	private final Logger logger = Logger.getLogger(this.getClass());

	public static void main(String[] args) throws Exception {
		new AdvancedCalculatorExample().process();
	}

	private void process() throws Exception {
		try {
			PropertyConfigurator.configure("resources/log4j.properties");
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.debug("Logger initialized");

		IJLCFContainer runtime = JLCFContainer.getInstance();

		runtime.loadApplication("resources/AdvancedCalculatorExample.xml");

		INumberCalculator numberCalcInterface = runtime
				.getComponentReference("NumberCalculator/INumberCalculator");

		Double res = numberCalcInterface.doCalculation("11+12");

		logger.info(res);

		int i = 0;
		int j = 4;
		while (true) {

			try {
				Thread.sleep(5000);
			} catch (InterruptedException yy) {

			}
			res = numberCalcInterface.doCalculation(i + "+" + j);
			i++;
			j++;
			logger.info(i + "+" + j + "=" + res);
		}
	}

}
