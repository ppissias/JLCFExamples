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
/**
 * 
 */
package example;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;
import org.jlcf.core.annotation.InitMethod;
import org.jlcf.core.annotation.Property;
import org.jlcf.core.annotation.Receptacle;

/**
 * @author Petros Pissias
 * 
 */
public class ComponentB {

	private final IDataProcessor dataProcessor;

	public ComponentB(@Receptacle(name = "dataProcessor") IDataProcessor dataProc) {

		this.dataProcessor = dataProc;
		System.out.println("ComponentB: instantiating");
	}

	@InitMethod
	public void init() {
		System.out.println("ComponentB: initializing");
		//call every 5 seconds the service provided by the other component
		Timer t = new Timer();
		t.schedule(new TimerTask() {
			@Override
			public void run() {
				String randomData = new Double(new Random().nextDouble()).toString();	
				System.out.println("ComponentB: Sending Data to be processed");
				boolean ret = dataProcessor.processData(randomData);
				System.out.println("ComponentB: Data processing reply:"+ret);
			}
		}, 0, 5000);
	}
}
