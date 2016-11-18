package com.sensia.tools.client.swetools.editors.sensorml.controller;

import com.sensia.relaxNG.RNGGrammar;

public interface IController {

	void parse(RNGGrammar grammar);
	
	void parse(String xmlDoc);
}
