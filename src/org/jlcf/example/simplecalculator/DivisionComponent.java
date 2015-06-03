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
package org.jlcf.example.simplecalculator;

/**
 * @author Petros Pissias
 * 
 */
public class DivisionComponent implements IDivision {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.pp.test.calculator.IDivision#divide(java.lang.Double,
	 * java.lang.Double)
	 */
	@Override
	public Double divide(Double a, Double b) {
		return a / b;
	}

}