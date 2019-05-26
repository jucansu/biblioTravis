package com.biblitek.service.codigoqr;

import java.util.Base64;
import java.util.List;

import javax.smartcardio.Card;
import javax.smartcardio.CardChannel;
import javax.smartcardio.CardException;
import javax.smartcardio.CardTerminal;
import javax.smartcardio.TerminalFactory;

public class CardReader {
	public static void main(String[] args) throws CardException {

		// show the list of available terminals
		TerminalFactory factory = TerminalFactory.getDefault();
		List<CardTerminal> terminals = factory.terminals().list();
		System.out.println("Terminals: " + terminals);
		// get the first terminal
		CardTerminal terminal = terminals.get(0);
		// establish a connection with the card
		Card card = terminal.connect("T=0");
		System.out.println("card: " + card);
		CardChannel channel = card.getBasicChannel();

		byte[] bytes = card.getATR().getBytes();

		String info = new String(Base64.getEncoder().encode(bytes));
		// ResponseAPDU r = channel.transmit(new CommandAPDU(card.getATR().getBytes()));

		System.out.println("info: " + info);
		// disconnect
		card.disconnect(false);

	}

}
