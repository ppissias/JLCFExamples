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
package org.jlcf.example.performancetest;

import org.apache.log4j.Logger;
import org.jlcf.core.annotation.InitMethod;
import org.jlcf.core.annotation.Property;
import org.jlcf.core.annotation.Receptacle;

/**
 * @author Petros Pissias
 * 
 */
public class ComponentA implements IComponentA {

	private final IComponentB compB;

	private final Logger logger = Logger.getLogger(getClass());

	public ComponentA(@Receptacle(name = "compoB") IComponentB compB)

	{
		this.compB = compB;

	}

	@InitMethod
	public void callme() {
		logger.info("component " + getClass().getName() 
				+ " initialized");
	}


	@Override
	public void beginPerformanceTest() {
		//start test of calling method that does some work
		logger.info("calling working method");

		int repetitions = 5000;
		
		String ret = "";
		long time1 = System.currentTimeMillis();
		for (int i = 0; i < repetitions; i++) {
			ret = compB.doSomeWork("Input String");
		}

		long time2 = System.currentTimeMillis();
		logger.error("doSomeWork: time it took for " + repetitions + " tests is "
				+ (time2 - time1) + " millis");
		
		
		//start test of calling void method
		logger.info("calling void method");
		repetitions = 2500000;
		
		time1 = System.currentTimeMillis();
		for (int i = 0; i < repetitions; i++) {
			compB.doesNothing();
		}

		time2 = System.currentTimeMillis();
		logger.error("doesNothing: time it took for " + repetitions
				+ " tests is " + (time2 - time1) + " millis");		
	}

}
